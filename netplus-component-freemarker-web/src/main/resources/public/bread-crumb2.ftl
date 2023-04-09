<div id="breadCrumb2">
     <div class="logo">
         <div class="w1200">
             <div class="logo_img">
                 <a href="/">
                     <img src="${cdn}/img/logo/logo_000.png" alt="" class="fl" width="252x">
                 </a>
                 <div class="fr home-search">
                     <input type="text" v-model="good" class="bd_no ml5 w300 fl mt5" placeholder="请输入商品名称、物料条码、物料编码、商品型规">
                     <button class="red_btn fl" @click="doTopSearch"><i class="iconfont icon-fangdajing f13 mr5"></i>搜索</button>
                 </div>
             </div>
         </div>
     </div>

     <!-- 全部商品分类 -->
     <div class="bg_fff bd_b_red2" @mouseleave="bannerLeave">
         <div class="w1200 pr">

            <div class="banner_menu">

                <div class="banner_menu_left" @mouseover="bannerMouseOver">
                    <div class="title cur"><i class="iconfont icon-fenlei mr5 f20 vm"></i>商品分类</div>

                    <div class="banner_menu_left_bg" id="banner_menu_left_bg">
                        <div class="" :style="isMore?'height:auto;':'height: 329px;overflow: hidden;'">
                            <div class="banner_menu_left_item" :class="menuIndex==index?'curr':''" v-for="(item,index) in categoryList" @mouseover="menuMouseOver(index)" >
                                <div class="of">
                                    <span class="icon_left"><i class="iconfont" :class="item.icon"></i></span>
                                    <span class="line_title"  @click="goShoppingFilterCategory(item.name,item.id)">{{item.name}}</span>
                                </div>
                            </div>
                        </div>

                        <div class="bg_f4f4f4 tc lh40 w220" @click="moreMenu" v-if="categoryList.length>8">
                            <span v-if="isMore">收起 <i class="iconfont icon-up"></i></span>
                            <span v-else>更多 <i class="iconfont icon-down"></i></span>
                        </div>
                    </div>
                </div>

                <div class="right_menu_con_box gundongLine" v-if="isBannerMouse" :style="{height:rightHeight}">
                    <div class="right_menu_con" v-for="(item,index) in categoryList" >
                        <div class="right_menu_item" v-for="c in item.children" v-if="menuIndex==index">
                            <div class="right_menu_item_title" style="cursor: pointer;" @click="goShoppingFilterOneLevel(c.name,c.id)">{{c.name}} <i class="iconfont icon-right f12"></i> </div>
                            <div class="right_menu_item_list">
                                <a class="right_menu_item_list_name" style="cursor: pointer;" @click="goShoppingFilter(d.name,d.id)" v-for="d in c.children">{{d.name}}</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>

            <!-- 登录 -->
            <div class="banner_right">
                <div class="tc f16 pt20 pb20 fb" v-if="!isLogin||role=='B'">欢迎来到永钢易购商城</div>
                <div v-if="!isLogin" class="tc pb20" >
                    <a class="bg_red c_fff lh32 w85 h32 b_r_5" href="/provider/login">立即登录</a> 
                </div>
                <div v-if="isLogin&&role=='B'" class="tl pb20 of">
                    <img src="${cdn}/img/home/banner_img3.png" alt="" width="66px" class="fl ml25 mr20">
                    <div class="fl f10 lh22 pt5 w140">

                        <div>{{currentUser.userCode}}</div>

                        <div>{{currentUser.username}}</div>

                        <div >
                            <a class="c_blue" href="/provider/pur-workbench">买方工作台
                                <i class="iconfont icon-right f10 disib" style="margin-top: -1px;"></i>
                            </a>
                        </div>

                    </div>
                </div>
                <div class="quick_list" >
                    <div class="line"></div>
                    <div class="of pt15">
                        <div class="of fl quick_item">
                            <div class="item">
                                <img src="/statics/img/home/banner_icon4.png" style="cursor: pointer" alt="" @click="goOrder"> <div>采购订单</div>
                            </div>
                            <div class="item" v-if="myItems.PG0022">
                                <img src="/statics/img/home/banner_icon5.png" style="cursor: pointer" alt="" @click="upShief"> <div>上架确认</div>
                            </div>
                            <div class="item" v-if="myItems.PG0044">
                                <img src="/statics/img/home/banner_icon6.png" style="cursor: pointer" alt="" @click="downShief"> <div>下架确认</div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
         </div>
     </div>
</div>

<script>
    var breadCrumb2 = new Vue({

        el: '#breadCrumb2',
        data: {

            isBannerMouse:false,
            menuIndex:'',
            searchKey:'',
            isMore:false,
            categoryList:[],
            isLogin:IS_LOGIN,
            role:role,
            currentUser:{},
            good: '',
            channel: '',
            rightHeight:'368px',
            myItems:{
                'PG0022':true,
                'PG0044':true,
            },
        },

        mounted: function(){
            if (IS_LOGIN) {
                this.myItems={
                    'PG0022':false,
                    'PG0044':false,
                };
                this.getCurrentUser();
                this.getUserMenuList();
            }
            this.getAllCategory();

        },

        methods: {
            getUserMenuList: function (){
                var _this = this;
                ajax("/api/provider/getUserMenuList/", {belongto: "PC"}, function(json){
                    _this.showMyItems(json);
                }, function(err){
                    dialog.error(err);
                });
            },
            showMyItems: function (menuList){
                var _this = this;
                $.each(menuList, function(i, d){
                    if(d.childmenulist != null){
                        _this.showMyItems(d.childmenulist);
                    }

                    Object.keys(_this.myItems).forEach((key)=>{
                        if(d.code == key){
                            _this.myItems[key]=true;
                        }
                    });
                });
            },

            doTopSearch: function () {
                var self=this;
                if(self.good=='') window.location.href = '/mall/shoppingFilter';
                window.location.href = '/mall/shoppingFilter?searchKey='+encodeURIComponent(self.good);
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
            bannerMouseOver:function(){
                this.isBannerMouse = true;
            },
            bannerLeave:function(){
                this.isBannerMouse = false
            },
            menuMouseOver:function(val){
                this.menuIndex = val
            },
            moreMenu:function(){
                let self = this;
                this.isMore = !this.isMore;
                this.$nextTick(()=>{
                    let height = document.getElementById("banner_menu_left_bg").offsetHeight + 'px'
                    self.rightHeight = height;
                })
            },
            goOrder:function () {
                window.location.href='/mall/purchaseOrder';
            },
            getAllCategory: function(){
                var self =this;
                ajax(
                    "/api/mall/getAllCategory/",
                    {},
                    function (resp) {
                        self.categoryList = resp;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            upShief:function () {
                window.location.href='/mall/goodsAudit';
            },
            downShief:function () {
                window.location.href='/mall/goodsUpdate';
            },
            goShoppingFilterCategory:function (val1,val2){
                window.location.href = '/mall/shoppingFilter?categoryname=' + encodeURIComponent(val1)+'&&categorypk='+encodeURIComponent(val2);
            },
            goShoppingFilterOneLevel:function (val1,val2) {
                window.location.href = '/mall/shoppingFilter?onelevelclaname=' + encodeURIComponent(val1)+'&&onelevelclapk='+encodeURIComponent(val2);
            },
            goShoppingFilter: function (val1,val2) {
                window.location.href = '/mall/shoppingFilter?twolevelclaname=' + encodeURIComponent(val1)+'&&twolevelclapk='+encodeURIComponent(val2);

            }
        }
    });
</script>