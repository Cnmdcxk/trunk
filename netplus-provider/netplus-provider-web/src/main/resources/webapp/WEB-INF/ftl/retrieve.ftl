<@override name="title">账号密码找回</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/login.css?v=${ver}">
    <style>
        .findPass {
            cursor: pointer;
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
                    <span class="txt">账号密码找回</span>
                </div>
            </div>
        </div>
        <!-- 协议 -->
        <div class="mt10">
            <div class="w1200 bg_fff">
                <div class="c_999 pt25 ml30 mb50">请选择找回方式：</div>
                <div class="findPass margin" @click="toPage('/provider/retrievePassword/')">
                    <img src="${cdn}/img/login/password.png" alt="" class="ml20" width="70px">
                    <span class="f24 ml20">找回密码</span>
                    <i class="iconfont icon-right fr mr20 c_999 f20"></i>
                </div>
                <div class="findPass margin mt30" @click="toPage('/provider/retrieveUsername/')">
                    <img src="${cdn}/img/login/user.png" alt="" class="ml20" width="70px">
                    <span class="f24 ml20">找回账号</span>
                    <i class="iconfont icon-right fr mr20 c_999 f20"></i>
                </div>
                <div class="w520 margin pt50 pb150">
                    <i class="c_orange iconfont fb icon-tishi"></i>
                    <span>授权委托人手机号无法接受短信，参照</span>
                    <a class="c_blue" href="${cdn}/agreement/重置账号密码申请函.doc" download="重置账号密码申请函">密码重置说明手册</a>
                </div>

            </div>
        </div>
    </div>
</@override>

<@override name="js">
    <script>
        var RZ = new Vue({
            el: '#RZ',
            data: function () {
                return {
                    checked: true,
                }

            },
            methods: {
                toPage: function (url) {
                    setTimeout(function () {
                        //跳转到新增供货范围页面
                        window.location.href = url;
                    }, 500);
                }
            }

        })
    </script>
</@override>

<@extends name="/base-rgpur.ftl"/>