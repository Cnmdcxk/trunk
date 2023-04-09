<@override name="title">找回账号-成功</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/login.css?v=${ver}">
    <style>
        .f34 {
            font-size: 34px;
        }
    </style>
</@override>

<@override name="content">
    <div id="RZ" class="">
        <!-- logo  -->
        <div class="logo">
            <div class="w1200">
                <div class="logo_img">
                    <img src="${cdn}/img/logo/logo_000.png" onclick="javascript:location.href='/'"
                         style="cursor: pointer" alt="" class="fl" height="36">
                    <span class="txt">账号找回</span>
                </div>
            </div>
        </div>
        <!-- 修改密码 -->
        <div class="mt10">
            <div class="w1200 bg_fff">
                <div class="login_three">
                    <ul class="ul_li_fl of w560 margin">
                        <div class="line w460"></div>
                        <li class="old_curr">
                            <div class="num_box">
                                <div class="num">1</div>
                            </div>
                            <div>验证身份</div>
                        </li>
                        <li v-if="acctCount > 0" class="curr">
                            <div class="num_box">
                                <div class="num"><i class="iconfont icon-duihao f26 disib mt_5"></i></div>
                            </div>
                            <div>找回账号成功</div>
                        </li>
                        <li v-else class="curr">
                            <div class="num_box">
                                <div class="num"><i class="iconfont icon-tishi f26 disib mt_5"></i></div>
                            </div>
                            <div>找回账号失败</div>
                        </li>

                    </ul>
                    <input type="hidden" value="${sessionId}" id="bindSessionId"/>
                </div>
                <div class="mt100">
                    <div class="lh40 tc" v-if="acctCount > 0">
                        <div class="w200 tc of margin h40 lh40">
                            <i class="iconfont icon-duihao1 c_green f34 fl"></i>
                            <span class="ml5 f26 fl">找回账号成功</span>
                        </div>
                    </div>
                    <div class="lh40 tc" v-else>
                        <div class="w200 tc of margin h40 lh40">
                            <i class="iconfont icon-tishi c_red f34 fl"></i>
                            <span class="ml5 f26 fl">找回账号失败</span>
                        </div>
                        <div class="w380 tc of margin h40 lh40">
                            <span class="margin f26">{{errMsg}}</span>
                        </div>
                    </div>
                </div>
                <div class="w400 margin mt60 pb80" v-if="acctCount > 0">
                    <div v-for="(item, index) in accounts" :key="index" class="tc">
                        <span class="acct_title">该手机号有{{acctCount}}个账号：{{item.userCode}}</span>
                        <span class="link_btn" @click="toLogin">去登录</span>
                        <span class="link_btn" @click="copyInfo(item)">复制</span>
                    </div>


                </div>
                <div class="w400 margin tc mt60 pb80" v-else>
                    <el-button type="text" class="link_btn" @click="toBack">返回上一页</el-button>

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
                return {
                    accounts: [],
                    acctCount: 0,
                    errMsg: ''

                }
            },
            created: function () {
                console.log("vue created");
                this.init();
            },
            mounted: function () {

            },
            methods: {
                // 显示返回账号
                init() {
                    var _this = this;
                    let sessionId = $('#bindSessionId').val();
                    // 根据sessionId查询手机号账号信息
                    if (sessionId) {
                        ajax('/api/provider/getRetrieveAccounts/',
                            {
                                sessionId: sessionId,
                            },
                            function (resp) {
                                if (resp.status == 1) {
                                    _this.acctCount = 1;
                                    _this.accounts.push(resp.data);
                                } else {
                                    _this.errMsg = resp.msg;
                                    dialog.error(resp.msg);
                                }
                            },
                            function (err) {
                                dialog.error(err);
                            }
                        );
                    }
                },
                toLogin() {
                    setTimeout(function () {
                        window.location.href = '/provider/login';
                    }, 0);
                },
                toBack() {
                    setTimeout(function () {
                        window.location.href = '/provider/retrieveUsername/';
                    }, 0);
                },
                copyInfo(text) {
                    var copyInput = document.createElement('input');
                    copyInput.setAttribute('value', text);
                    document.body.appendChild(copyInput);
                    copyInput.select();
                    try {
                        var copyed = document.execCommand('copy');
                        if (copyed) {
                            document.body.removeChild(copyInput);
                            dialog.success('复制成功');
                        }
                    } catch (error) {
                        handleError(error)
                        dialog.warning('复制失败，请检查浏览器兼容');
                    }
                }
            }
        })
    </script>
</@override>

<@extends name="/base-rgpur.ftl"/>