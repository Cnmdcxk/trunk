<@override name="title">找回密码-验证身份</@override>

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
                        <li class="curr">
                            <div class="num_box">
                                <div class="num">1</div>
                            </div>
                            <div>验证身份</div>
                        </li>
                        <li>
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
                <input type="hidden" value="${msg}" id="errMsg"/>
                <div class="w550 margin pt80 lh40">
                    <el-form :model="form"
                             :rules="rules"
                             ref="checkForm"
                             label-width="190px"
                             label-position="right">
                        <el-form-item prop="phone" label="手机号码：">
                            <el-input autocomplete="off" placeholder="请输入手机号" v-model="form.phone"></el-input>
                        </el-form-item>
                        <el-row>
                            <el-col :span="16">
                                <el-form-item prop="imgCode" label="图形验证码：">
                                    <el-input placeholder="图形验证码" v-model="form.imgCode"></el-input>
                                </el-form-item>
                            </el-col>
                            <el-col :span="6" class="tc"><img :src="imgSrc" alt="验证码" class="img_code"></el-col>
                            <el-col :span="2" class="tc">
                                <span class="span_link c_blue f10 disib" @click="refreshImgCode();">换一张</span>
                            </el-col>
                        </el-row>
                        <el-form-item prop="sms" label="短信验证码：" class="pwd_btn_color">
                            <el-input placeholder="短信验证码" v-model="form.sms">
                                <el-button slot="append" type="success" class="sms_btn" v-if="!showCount"
                                           @click="getSmsCode('checkForm')">点击获取验证码
                                </el-button>
                                <el-button slot="append" class="sms_btn" v-else>{{count}}s后重新获取</el-button>
                            </el-input>
                        </el-form-item>
                        <el-form-item class="pb100 pt80">
                            <el-button plain class="next_btn" v-if="errCount <= 5" @click="nextStep('checkForm')">下一步
                            </el-button>
                            <el-button plain class="next_btn" v-else>稍后重试</el-button>
                        </el-form-item>
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
            data:function() {
                // 校验手机号
                var checkPhone = (rule, value, callback) => {
                    if (!value) {
                        return callback(new Error('请输入手机号'));
                    } else {
                        const reg = /^1[3|4|5|7|8][0-9]\d{8}$/
                        if (reg.test(value)) {
                            callback();
                        } else {
                            return callback(new Error('请输入正确的手机号'));
                        }
                    }
                };
                return {
                    checked: true,
                    form: {
                        phone: '',
                        imgCode: null,
                        sms: null
                    },
                    rules: {
                        phone: [
                            {required: true, message: '请输入手机号', trigger: 'blur'},
                            {validator: checkPhone, trigger: 'blur'},
                        ],
                        imgCode: [
                            {required: true, message: '请输入验证码', trigger: 'blur'},
                        ],
                        sms: [
                            {required: true, message: '请输入短信验证码', trigger: 'blur'},
                        ],
                    },
                    imgSrc: "",
                    showCount: false,
                    count: '',
                    timer: null,
                    errCount: 0,
                }

            },

            mounted: function () {
                this.refreshImgCode();
                let errMsg = $('#errMsg').val();
                if (errMsg) {
                    dialog.error(errMsg);
                }
            },
            methods: {
                // 获取图形验证码
                refreshImgCode: function () {
                    this.imgSrc = '/api/v2/captcha/img/RetrievePwd/?' + new Date().getTime();
                },
                // 获取短信验证码
                getSmsCode: function (formName) {
                    var _this = this;
                    _this.$refs[formName].validateField('phone', error => {
                        if (!error) {
                            _this.$refs[formName].validateField('imgCode', error => {
                                if (!error) {
                                    // 验证输入图形验证码是否正确
                                    _this.validateCode().then((msg) => {
                                        // 获取手机验证码
                                        ajax('/api/provider/retrievePassword/sendSmsCode/',
                                            {
                                                mobile: _this.form.phone,
                                                workType: 3, //密码找回
                                            },
                                            function (resp) {
                                                // 发送成功
                                                if (resp.status == 1) {
                                                    const TIME_COUNT = 60;
                                                    if (!_this.timer) {
                                                        _this.count = TIME_COUNT;
                                                        _this.showCount = true;
                                                        _this.timer = setInterval(() => {
                                                            if (_this.count > 0 && _this.count <= TIME_COUNT) {
                                                                _this.count--;
                                                            } else {
                                                                _this.showCount = false;
                                                                clearInterval(_this.timer);
                                                                _this.timer = null;
                                                            }
                                                        }, 1000)
                                                    }
                                                } else {
                                                    dialog.error("供应商注册信息无此手机号码信息，请确认手机号码是否正确");
                                                }
                                            },
                                            function (err) {
                                                dialog.error(err);
                                            }
                                        );
                                    }, (error) => {
                                        _this.refreshImgCode();
                                        dialog.error(error);
                                    }).catch(res => {
                                        console.log('catch data::', res)
                                    });
                                } else {
                                    console.log('图形验证码验证失败');
                                    return false;
                                }
                            });
                        } else {
                            console.log('手机验证失败');
                            return false;
                        }
                    });
                },
                validateCode() {
                    var _this = this;
                    return new Promise((resolve, reject) => {
                        ajax('/api/v2/captcha/checkCode',
                            {
                                imgCode: _this.form.imgCode,
                                imgType: 'RetrievePwd'
                            },
                            function (resp) {
                                if (resp.status == 1) {
                                    resolve("success");
                                } else {
                                    reject(resp.msg);
                                }
                            },
                            function (err) {
                                reject("验证请求失败");
                            }
                        );
                    });
                },
                // 下一步
                nextStep(formName) {
                    var _this = this;
                    // 验证字段是否输入
                    _this.$refs[formName].validate((valid) => {
                        if (valid) {
                            // 验证输入图形验证码是否正确
                            _this.validateCode().then((msg) => {
                                ajax('/api/provider/retrieve/',
                                    {
                                        mobile: _this.form.phone,
                                        mobileCode: _this.form.sms,
                                        workType: 3,
                                    },
                                    function (resp) {
                                        if (resp.status == 1) {
                                            let sid = resp.data.sessionId;
                                            setTimeout(function () {
                                                window.location.href = '/provider/retrievePassword2/?sessionId=' + sid;
                                            }, 10)
                                        } else {
                                            let errMsg = resp.msg;
                                            if (errMsg.includes("，")) {
                                                _this.errCount = errMsg.substring(errMsg.length - 1);
                                                console.log(_this.errCount)
                                                errMsg = _this.errCount > 5 ? "错误次数太多，请稍后重试" : "短信验证码已失效";
                                            }
                                            dialog.error(errMsg);
                                        }
                                    },
                                    function (err) {
                                        dialog.error(err);
                                    }
                                );

                            }, (error) => {
                                _this.refreshImgCode();
                                dialog.error(error);
                            }).catch(
                                res => {
                                    console.log('catch data::', res)
                                });
                        }
                    });
                }
            }
        })
    </script>
</@override>

<@extends name="/base-rgpur.ftl"/>