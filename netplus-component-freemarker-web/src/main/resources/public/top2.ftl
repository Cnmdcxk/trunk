<div id="top2" class="top_menu2 of" v-cloak>
    <div class="w1200 of">
        <div class="fl">
            <img src="${cdn}/img/logo/logo_fff.png" alt="" class="fl" width="198px" height="30" style="margin-top:12px;">
            <a href="/" class="c_fff ml40" v-if="role=='B'">网站首页</a>
            <a v-for="m in menu"
               class="c_fff ml60 "
               v-bind:class="{'white-btn':code==m.code||pageRole==m.code}"
               :href="m.url"
            >{{m.name}}</a>
        </div>
        <div class="fr mt15">
            <el-badge :value="countMessage" class="item mr40">
                <a href="/messaging/index"><i class="iconfont icon-youxiang- f20 c_fff"></i></a>
            </el-badge>
            <span class="lh25 fb mr10 c_fff">{{ currentUser.username}}</span>
            <el-dropdown trigger="click">
                <span class="el-dropdown-link">
                    <img src="${cdn}/img/logo/touxiang.png" alt="" class="disib b_r_50" width="25px" height="25px">
                    <i class="iconfont icon-down c_fff ml10"></i>
                </span>
                <el-dropdown-menu slot="dropdown">
                    <div class="p5">
                        <div class="of pb10" style="border-bottom:1px solid #dcdcdc;">
                            <img src="${cdn}/img/logo/touxiang.png" alt="" class="disib b_r_50 fl mt10 ml10 mr10"
                                 width="25px" height="25px">
                            <div class="fl lh20 pr20">
                                <p class="w150">{{ currentUser.username }}</p>
                                <p class="w150">{{ currentUser.userCode}}</p>
                            </div>
                        </div>
                        <div class="mt10 ml10 f13" style="line-height: 20px;">
                            <a href="/provider/logout" class="mb10 of db">
                                <i class="iconfont icon-tuichu c_999 f16 fl mr30"></i>
                                <span class="disib fl cur">退出</span>
                            </a>


                        </div>
                    </div>
                </el-dropdown-menu>
            </el-dropdown>
        </div>
    </div>
</div>



<script type="text/javascript">

      var top2Vue =  new Vue({
            el: '#top2',
            data: {
                display:true,
                currentUser: {},
                countMessage: 0,
                menu:[],
                currentMenu: "",
                pageCode:PAGE_PRIVILEGE_CODE,
                code:"",
                role:role,
                pageRole:""
            },
            mounted: function () {

                if (IS_LOGIN) {
                    this.getCurrentUser();
                    this.getAllCount();
                    this.getUserMenuList();
                }
            },
            methods: {


                getUserMenuList: function(){
                    var _this = this;

                    ajax("/api/provider/getUserMenuList/", {belongto: "PC"}, function(json){
                        _this.menu = json;
                        var pageCode=_this.pageCode;

                        if(pageCode==''){
                            if(_this.role=='B') {
                                _this.pageRole = 'MD002';
                            }else{
                                _this.pageRole = 'MD001'
                            }
                        }else {
                            $.each(json, function (i) {
                                json[i].childmenulist.forEach(v1 => {
                                    v1.childmenulist.forEach(v2 => {
                                        if (v2.code == pageCode) {
                                            _this.code = json[i].code
                                        }
                                    })
                                })
                            });
                        }
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
                            //dialog.error(err);
                        }
                    );
                },
                changeStyle:function (val) {

                    this.code=val;
                },
                getAllCount: function () {
                    var self = this;
                    ajax('/api/messaging/getAllCount/',
                        {},
                        function (resp) {
                            self.countMessage = resp.sum1 + resp.sum2
                        },
                        function (err) {
                        }
                    );
                }

            }
        });
</script>

