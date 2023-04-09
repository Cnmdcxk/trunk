
<div id="breadCrumb">
    <div class="logo">
        <div class="w1200">
            <div class="logo_img">
                <a href="/">
                    <img src="${cdn}/img/logo/logo_000.png" style="cursor: pointer" alt="" class="fl" width="252x">
                </a>
                <div class="fr home-search">
                    <input v-model="good" type="text" class="bd_no ml5 w300 fl mt5" placeholder="请输入商品名称、物料条码、物料编码、商品型规">
                    <button class="red_btn fl" @click="doTopSearch()"><i class="iconfont icon-fangdajing f13 mr5"></i>搜索</button>
                </div>
            </div>
        </div>
    </div>

    <div class="bg_fff bd_b_red2">
        <div class="w1200 pr">
            <div class="banner_menu">
                <div class="banner_menu_left" >

                    <div class="title" @click="showMenu">全部商品分类 <i class="iconfont mr5 f20 vm" :class="isShowMenu?'icon-up':'icon-down'"></i></div>
                    <div class="banner_menu_left_bg" v-show="isShowMenu">
                        <div class="" :style="isMore?'height:auto;':'height: 329px;overflow: hidden;'">
                            <div class="banner_menu_left_item" :class="menuIndex==index?'curr':''" v-for="(item,index) in menuList" @mouseover="menuMouseOver(index)">
                                <div class="of" @click="goShoppingFilterCategory(item.name,item.id)">
                                    <span class="icon_left"><i class="iconfont" :class="item.icon"></i></span>
                                    <span class="line_title" >{{item.name}}</span>
                                </div>
                            </div>
                        </div>
                        <div class="bg_f4f4f4 tc lh40 w220 cur" @click="moreMenu" v-if="menuList.length>8">
                            <span v-if="isMore">收起 <i class="iconfont icon-up"></i></span>
                            <span v-else>更多 <i class="iconfont icon-down"></i></span>
                        </div>
                    </div>
                </div>
                <div class="right_menu_con_box" v-show="isBannerMouse">
                    <div class="right_menu_con" v-for="(item,index) in menuList" >
                        <div class="right_menu_item"  v-for="c in item.children" v-if="menuIndex==index">
                            <div class="right_menu_item_title" style="cursor: pointer" @click="goShoppingFilterOneLevel(c.name,c.id)">{{c.name}} <i class="iconfont icon-right f12"></i> </div>
                            <div class="right_menu_item_list">
                                <a class="right_menu_item_list_name" v-for="d in c.children" @click="goShoppingFilter(d.name,d.id)">{{d.name}}</a>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="clear"></div>
            </div>
        </div>
    </div>
</div>


<script type="text/javascript">

    (function() {

        new Vue({
            el: '#breadCrumb',
            data: {
                keys: '',
                role: role,
                good: '',
                menuList:[],
                isBannerMouse:false,
                isShowMenu:false,
                menuIndex:'',
                isMore:false,
            },
            mounted: function () {
                this.getAllCategory();
            },

            methods: {
                bannerMouseOver:function(){
                    this.isBannerMouse = true;
                    this.isShowMenu = true;
                },
                bannerLeave:function(){
                    this.isBannerMouse = false;
                    this.isShowMenu = false;
                },
                menuMouseOver:function(val){
                    this.menuIndex = val
                },
                moreMenu:function(){
                    this.isMore = !this.isMore;
                    this.isBannerMouse = true;
                },
                showMenu:function(){
                    this.isShowMenu = !this.isShowMenu;
                    this.isBannerMouse = !this.isBannerMouse;
                },
                doTopSearch: function () {
                    var self=this;
                    if(self.good=='') window.location.href = '/mall/shoppingFilter';
                    window.location.href = '/mall/shoppingFilter?searchKey='+encodeURIComponent(self.good);
                },
                goShoppingFilterCategory:function (val1,val2){
                    window.location.href = '/mall/shoppingFilter?categoryname=' + encodeURIComponent(val1)+'&&categorypk='+encodeURIComponent(val2);
                },
                goShoppingFilterOneLevel:function (val1,val2) {
                    window.location.href = '/mall/shoppingFilter?onelevelclaname=' + encodeURIComponent(val1)+'&&onelevelclapk='+encodeURIComponent(val2);
                },
                goShoppingFilter: function (val1,val2) {
                    window.location.href = '/mall/shoppingFilter?twolevelclaname=' + encodeURIComponent(val1)+'&&twolevelclapk='+encodeURIComponent(val2);

                },
                getAllCategory: function(){
                    var self =this;
                    ajax(
                        "/api/mall/getAllCategory/",
                        {},
                        function (resp) {
                            self.menuList=resp;
                        },
                        function (err) {
                            dialog.error(err);
                        }
                    );
                },

            }
        });

    })();

</script>




