<@override name="title">找回密码-设置密码</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/login.css?v=${ver}">
    <link rel="stylesheet" href="${cdn}/css/retrieve.css?v=${ver}">
</@override>

<@override name="content">
    <div id="RZ" class="">
        <!-- logo  -->
        <div class="logo">
            <div class="w1200">
                <div class="logo_img">
                    <img src="${cdn}/img/logo/logo_000.png" onclick="javascript:location.href='/'"
                         style="cursor: pointer" alt="" class="fl" width="198px">
                    <span class="txt">找回密码</span>
                </div>
            </div>
        </div>
        <!-- 修改密码 -->
        <div class="mt10">
            <div class="w1200 bg_fff">
                <div class="login_three">
                    <ul class="ul_li_fl of">
                        <div class="line"></div>
                        <li class="old_curr">
                            <div class="num_box">
                                <div class="num">1</div>
                            </div>
                            <div>验证身份</div>
                        </li>
                        <li class="curr">
                            <div class="num_box">
                                <div class="num">2</div>
                            </div>
                            <div>设置密码</div>
                        </li>
                        <li>
                            <div class="num_box">
                                <div class="num"><i class="iconfont icon-duihao f26 disib mt_5"></i></div>
                            </div>
                            <div>完成</div>
                        </li>

                    </ul>
                </div>
                <div class="w720 margin pt80">
                    <el-form :model="form"
                             :rules="rules"
                             ref="setPwdForm"
                             label-width="110px"
                             label-position="right">
                        <input type="hidden" value="${sessionId}" id="bindSessionId"/>
                        <el-row>
                            <el-col :span="12" :offset="4">
                                <el-form-item prop="password" label="新密码：">
                                    <el-input autocomplete="false" type="password" id="new_pwd" placeholder="请输入您的密码"
                                              v-model="form.password"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <el-row>
                            <el-col :span="12" :offset="4">
                                <el-form-item prop="confirmPassword" label="确认密码：">
                                    <el-input autocomplete="false" type="password" placeholder="请再次输入您的密码"
                                              v-model="form.confirmPassword"></el-input>
                                </el-form-item>
                            </el-col>
                        </el-row>
                        <div class="pb100 pt80 tc">
                            <el-button plain class="next_btn" @click="nextStep('setPwdForm')">下一步</el-button>
                        </div>
                    </el-form>
                </div>
            </div>
        </div>
    </div>
</@override>

<@override name="js">
    <script type="text/babel">
        var RZ = new Vue({
            el: '#RZ',
            data: function () {
                // 校验密码
                var checkPwd = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入密码'));
                    } else {
                        const reg = /^[A-Za-z0-9-]*$/g;
                        if (reg.test(value)) {
                            callback();
                        } else {
                            return callback(new Error('密码不符合规范(A-Z,a-z,0-9)!!'));
                        }
                    }
                };
                var checkConfirmPwd = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入确认密码'));
                    } else {
                        let password = this.form.password;
                        if (password && value.trim() !== password.trim()) {
                            return callback(new Error('两次密码不一致'))
                        }
                        callback();
                    }
                };
                return {
                    checked: true,
                    form: {
                        password: '',
                        confirmPassword: null,
                    },
                    rules: {
                        password: [
                            {required: true, message: '请输入密码', trigger: 'blur'},
                            {validator: checkPwd, trigger: 'blur'},
                        ],
                        confirmPassword: [
                            {required: true, message: '请输入确认密码', trigger: 'blur'},
                            {validator: checkConfirmPwd, trigger: 'blur'},
                        ],
                    },
                }

            },
            mounted: function () {
            },
            methods: {
                nextStep(formName) {
                    var _this = this;
                    let sessionId = $('#bindSessionId').val();
                    // 验证字段是否输入
                    _this.$refs[formName].validate((valid) => {
                        if (valid) {
                            // TODO:校验手机号 重置密码
                            if (!sessionId) {
                                dialog.error("验证码已失效，请返回重试");
                                return;
                            }
                            ajax('/api/provider/updPassword/',
                                {
                                    sessionId: sessionId,
                                    password: _this.form.password,
                                    confirmPassword: _this.form.confirmPassword,
                                },
                                function (resp) {
                                    if (resp.status == 1) {
                                        // 修改成功
                                        dialog.success("重置成功");
                                        setTimeout(function () {
                                            window.location.href = '/provider/retrievePassword3/';
                                        }, 100)
                                    } else {
                                        dialog.error("密码重置失败，请重试");
                                    }
                                },
                                function (err) {
                                    dialog.error(err);
                                }
                            );

                        }
                    });
                }

            }

        })
    </script>
</@override>

<@extends name="/base-rgpur.ftl"/>