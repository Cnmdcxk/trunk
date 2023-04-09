<@override name="title">找回密码-成功</@override>

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
                         style="cursor: pointer" alt="" class="fl" height="36">
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
                        <li class="old_curr">
                            <div class="num_box">
                                <div class="num">2</div>
                            </div>
                            <div>设置密码</div>
                        </li>
                        <li class="curr">
                            <div class="num_box">
                                <div class="num"><i class="iconfont icon-duihao f26 disib mt_5"></i></div>
                            </div>
                            <div>完成</div>
                        </li>

                    </ul>
                </div>
                <div class="mt100">
                    <div class="lh40 tc">
                        <div class="w450 tc of margin h40 lh40">
                            <i class="iconfont icon-duihao1 c_green f36 fl"></i>
                            <span class="ml5 f26 fl">找回密码成功，请牢记修改密码!</span>
                        </div>
                    </div>
                </div>
                <div class="tc mt100 pt30 pb100">
                    <el-button type="primary" class="login_btn" @click="toLogin()">去登录</el-button>

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
                return {
                    checked: true,
                }

            },

            methods: {
                toLogin() {
                    setTimeout(function () {
                        window.location.href = '/provider/login';
                    }, 0);
                }
            }

        })
    </script>
</@override>

<@extends name="/base-rgpur.ftl"/>