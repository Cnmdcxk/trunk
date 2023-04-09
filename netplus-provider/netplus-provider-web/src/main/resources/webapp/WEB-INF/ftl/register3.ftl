<@override name="title">注册完成</@override>

<@override name="css">
<link rel="stylesheet" href="${cdn}/css/login.css?v=${ver}">
</@override>

<@override name="content">
    <input hidden type="text" value="${supplierCode}" id="supplierCode" />
<div id="RZ" >
    <!-- logo  -->
    <div class="logo">
        <div class="w1200">
            <div class="logo_img">
                <img src="${cdn}/img/logo/logo_000.png" alt="" class="fl" height="36">
                <span class="txt">供应商注册</span>
            </div>
        </div>
    </div>
    <!-- 注册填写信息 -->
    <div class="bg_fff mt5">
        <div class="w1200">
            <div class="" style="width: 1160px;">
                <!-- 三个切换 -->
                <div class="login_three">
                    <ul class="ul_li_fl of">
                        <div class="line"></div>
                        <li class="old_curr">
                            <div class="num_box">
                                <div class="num">1</div>
                            </div>
                            <div>协议阅读并接受</div>
                        </li>
                        <li  class="old_curr">
                            <div class="num_box">
                                <div class="num">2</div>
                            </div>
                            <div>注册信息填写</div>
                        </li>
                        <li class="curr">
                            <div class="num_box">
                                <div class="num"><i class="iconfont icon-duihao f26 disib mt_5"></i></div>
                            </div>
                            <div>注册完成</div>
                        </li>
                    </ul>
                </div>
                <!-- 成功 -->
                <div class="">
                    <div class="mt100 tc">
                        <i class="iconfont icon-duihao1 c_green f40"></i>
                        <span class="f26">恭喜您注册完成</span>
                    </div>
                    <div class="mt30 tc f16">
                        您的供应商账号是：${supplierCode}
                    </div>
                    <div class="tc mt120 mb20 pb50">
                        <button class="main_btn mr20"><a href="/provider/certification1">去认证</a></button>
                        <button class="main_fff_btn"><a href="/portal/index">去浏览首页</a></button>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
</@override>

<@override name="js">
    <script type="text/babel" src="${cdn}/js/provider/register3.js?v=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>