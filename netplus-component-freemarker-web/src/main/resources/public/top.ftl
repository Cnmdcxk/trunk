<div class="top_menu of" id="top">
    <div class="w1200">
        <ul class="ul_li_fl of fr">
<#--            <li v-if="!isLogin"><a href="/provider/login">登录</a><i>|</i></li>-->
            <li v-if="!isLogin">IT运维服务热线：0512-58619577(59577)</li>
            <li v-else>
                <el-dropdown trigger="click">
                    <span class="el-dropdown-link cur">
                        <a>{{ currentUser.username }} <span class="iconfont icon-down"></span></a><i>|</i>
                    </span>
                    <el-dropdown-menu slot="dropdown">
                        <div class="p5">
                            <div class="of pb10 w200" style="border-bottom:1px solid #dcdcdc;">
                                <img src="${cdn}/img/home/banner_img3.png" alt="" class="disib b_r_50 fl mt10 ml10 mr10"
                                     width="25px" height="25px">
                                <div class="fl lh20">
                                    <p class="w150">{{ currentUser.userCode }}</p>
                                    <p class="w150">{{ currentUser.username }}</p>
                                </div>
                            </div>
                            <div class="mt10 ml10 f13" style="line-height: 20px;">
                                <a class="mb10 of db" href="/provider/logout">
                                    <i class="iconfont icon-tuichu c_999 f16 fl w20 mr15 disib"></i>
                                    <span class="disib fl cur">退出</span>
                                </a>
                            </div>
                        </div>
                    </el-dropdown-menu>
                </el-dropdown>
            </li>

            <li v-for="m in menu">
                <el-dropdown class="home_dropdown">

                    <span class="el-dropdown-link">
                        <a :href="m.url">{{m.name}}</a><i class="el-icon-arrow-down el-icon--right"></i>
                    </span>

                    <el-dropdown-menu slot="dropdown">
                        <el-dropdown-item v-for="(item,index) in m.childmenulist" @click.native="toPage(item.url)">
                            {{item.name}}
                        </el-dropdown-item>
                    </el-dropdown-menu>

                </el-dropdown>
            </li>


<#--            <li><a href="/#help">帮助中心</a><i>|</i></li>-->
<#--            <li><a href="/#contact">联系我们</a></li>-->

        </ul>
    </div>
</div>


<script type="text/javascript">
    var top = new Vue({
        el: "#top",
        data: {
            isLogin: IS_LOGIN,
            role:role,
            currentUser: {},
            menu:[],
        },
        mounted: function () {
            if (IS_LOGIN) {
                this.getCurrentUser();
                this.getUserMenuList();
            }
        },
        methods: {

            getUserMenuList: function(){
                var _this = this;

                ajax("/api/provider/getUserMenuList/", {belongto: "PC"}, function(json){
                    _this.menu = json;
                }, function(err){
                    dialog.error(err);
                });
            },

            getCurrentUser: function () {
                var self = this;
                ajax('/api/provider/getCurrentUser/',
                    {loading: false},
                    function (resp) {

                        self.currentUser = resp;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            toPage: function(url) {

                console.log(url);
                window.location.href = url;
            }
        }

    })
</script>