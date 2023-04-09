<@override name="title">商城</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/home.css">
    <link rel="stylesheet" href="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.css">
</@override>


<@override name="content">


<div>
    <!-- 头部菜单 -->

    <#include '/bread-crumb2.ftl'>

    <div id="RZ" v-cloak>
        <!-- 轮播 -->
        <div class="banner shop_banner">
            <el-carousel height="370px" arrow="always">
                <el-carousel-item style="background:url(/statics/img/home/banner_bg.png);"></el-carousel-item>
            </el-carousel>
        </div>

        <!-- 工业品商城 -->
        <div class="bg_f5f5f5 pt20 pb10">
            <div class="w1200">
                <!--    列表循环  四种 色号 color1 color2 color3  color4 -->
                <div class="industrial_goods mb10" v-for="(item,index,key) in tableData" :class="'color'+(index+1)" :key="index">
                    <a class="lh50 cur w">
                        <i class="iconfont icon-mianxingluosiding c_666 ml10 f16"></i>
                        <span class="f18">{{item.categoryname}}</span>
                        <span class="fr mr10 iconfont icon-y-right c_999"></span>
                    </a>
                    <div class="left_img_shop" >
                        <img :src="'/statics/img/shop/shop_'+(index+1)+'.png'" alt="" class="left_img_bottom">
                    </div>
                    <!-- 轮播 -->
                    <div class="right_banner">
                        <div class="swiper-container1 swiper-container">
                            <div class="swiper-wrapper">
                                <div class="swiper-slide" v-for="(item3 ,index3) in item.goodsVoList" :key="index3">
                                    <div class="shop_swiper"  @click="goGoodsDetail(item3.goodid)">
                                        <div class="img_box"><img :src="item3.pictureurl" alt="" class="img"></div>
                                        <div class="title">{{item3.productname}}</div>
                                        <div> <money-view :money="item3.price"></money-view> </div>
                                    </div>
                                </div>
                            </div>
                            <div class="swiper-button-next"><i class="iconfont icon-right f16"></i> </div>
                            <div class="swiper-button-prev"><i class="iconfont icon-left f16"></i> </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <#include "/toolbox.ftl">

</div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script src="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.js?ver=${ver!}"></script>
    <script src="${cdn}/js/framework/components/money.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/shopping.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>