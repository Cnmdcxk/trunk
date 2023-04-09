<@override name="title">模拟登录</@override>

<@override name="css">
</@override>

<@override name="content">
    <!-- 内容区域 -->
    <div class="bg_fff">
        <div class="w1200">
            <!-- 左侧菜单 -->
            <#include "/left-nav.ftl">
            <!-- 右侧内容 -->
            <div class="right_con" id="RZ"v-cloak>
                <div class="right_top">
                    <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                    <span class="c_blue fl ml10">模拟登陆</span>
                </div>
                <!-- 模拟登陆-->
                <div class="of p20">
                    <div class="fl w_50"><img src="${cdn}/img/member/virtual_login_1.png" alt="" style="display: block;width:80%;">
                    </div>
                    <div class="fl w_40 mt100 pt10">
                        <div class="f15 lh30 virtual_login w290">
                            <p>登陆密码</p>
                            <input type="password" class="w280 disib h40 lh40" v-model="password" placeholder="请输入当前用户登录密码">
                            <p class="mt15">模拟账号</p>
                            <input type="text" class="w280 disib h40 lh40" v-model="mockUsername"  placeholder="请输入要登录用户的用户名">
                            <br>
                            <button class="lh40 tc w280 disib f15 c_fff mt40" @click="mockLogin();"
                                    style="border-radius: 20px;background: #5794FF;">模拟登陆
                            </button>
                        </div>
                    </div>
                    <div class="fr mr40"><img src="${cdn}/img/member/virtual_login_2.png" alt="" width="36px"></div>
                </div>

            </div>
        </div>
    </div>
</@override>

<@override name="js">
    <script type="text/babel" src="${cdn}/js/provider/mock.js?v=${ver}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>