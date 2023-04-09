<@override name="title">商品详情预览</@override>

<@override name="css">
<link rel="stylesheet" href="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.css">
<link rel="stylesheet" href="${cdn}/css/home.css">
<link rel="stylesheet" type="text/css" href="${cdn}/css/shop.css"/>
<link rel="stylesheet" href="${cdn}/css/img-zoom.css">
</@override>


<@override name="content">



<div class="">
    <!-- 内容区域 -->
    <div class="bg_fff" id="RZ" v-cloak>
        <div class="w1200">
            <div class="mb20 f14 pt15">首页 > {{goods.categoryname}} > {{goods.onelevelclaname}} > {{goods.twolevelclaname}} > {{goods.productname}}</div>

            <div class="of">
                <!--放大镜  -->
                <div class="w330 fl">
                    <img-zoom :img_list="imgList" class="fl"></img-zoom>
                </div>

                <div class="fl ml30" style="width: 830px;">
                    <div class="f20 mb15">{{goods.productname}}</div>
                    <div class="bg_f8f8f8 p10 c_666 lh30">
                        <div class="of">
                            <span class="w70">价 格：</span>
                                <span class="c_red">
                                    <money-view :money="goods.price"></money-view>/{{goods.qtyunit}}
                                </span>
                        </div>
                        <div class="of">
                            <span class="w70">未税价格：</span>
                            <span>
                                ￥{{goods.notaxprice}}/{{goods.qtyunit}}（税率：{{goods.tax | formatTax}}%）
                            </span>
                        </div>
                    </div>
                    <div class="of lh30 mt10 mb20 pb20 c_666 pl10" style="border-bottom: 1px dashed #DEDEDE;">
                        <div class="fl w_40"><span class="w80">品牌名称：</span>  <span class="c_blue">{{goods.brand}}</span></div>
                        <div class="fl w_40"><span class="w80 fl">商品型规：</span>  <span class="" style="line-height:16px;">{{goods.spec}}</span></div>
                        <div class="fl w_40"><span class="w80">材质：</span>  <span class="">{{goods.qlty}}</span></div>
                        <div class="fl w_40"><span class="w80">起订量：</span>  <span class="" v-if="goods.minbuyqty != null">{{goods.minbuyqty}}/{{goods.qtyunit}}</span></div>
                        <div class="fl w_40"><span class="w80">货号：</span>  <span class="">{{goods.goodno}}</span></div>
                        <div class="fl w_40"><span class="w80">物料条码：</span>  <span class="">{{goods.matrltm}}</span></div>
                        <div class="fl w_40"><span class="w80">物料编码：</span>  <span class="">{{goods.matrlno}}</span></div>
                        <div class="fl w_40"><span class="w80">协议号：</span>  <span class="">{{goods.pono}}</span></div>
                        <div class="fl w_40"><span class="w80">物资采购人：</span>  <span class="">{{goods.agent}}</span></div>
                        <div class="fl w_40"><span class="w80">图纸：</span>  <span class="">{{goods.haspic == '1' ? '有图纸' : '无图纸'}}</span></div>
                    </div>
                    <div class="mt10 c_666 ml10">
                        交货周期:  {{goods.leadtimenum}}个工作日
                    </div>
                </div>

            </div>
        </div>

        <!-- 浏览历史 -->
        <div class="w1200">
            <div class="history">
                <div class="fl history-right">

                    <div class="blue-title mt20">
                        <div class="blue-title-item curr">商品介绍</div>
                    </div>

                    <div class="of lh30 mt20 mb20 pb20 c_666 ml30" style="border-bottom: 1px dashed #DEDEDE;">
                        <div class="fl w_40"><span class="w80">品牌名称：</span>  <span class="c_blue">{{goods.brand}}</span></div>
                        <div class="fl w_50"><span class="w80 fl">商品型规：</span>  <span class="" style="width: 370px;">{{goods.spec}}</span></div>
                        <div class="fl w_40"><span class="w80" >起订量：</span>  <span class="" v-if="goods.minbuyqty != null">{{goods.minbuyqty}}/{{goods.qtyunit}}</span></div>
                        <div class="fl w_40"><span class="w80">货号：</span>  <span class="">{{goods.goodno}}</span></div>
                    </div>

                    <div class="mt30 lh30 ml30 clear">
                        <div>产品特点：</div>
                        <div id="content"></div>
                    </div>

                    <div class="w" v-html="goods.content"></div>

                    

                </div>
            </div>
        </div>

    </div>
</div>
</@override>

<@override name="js">
<script>
    var keys = '${keys!}';
    var goodsId = '${goodsId!}';
    var cdn = '${cdn!}';
</script>

<script src="${cdn}//js/3rd/jquery/jquery.fly.min.js"></script>
<script type="text/babel" src="${cdn}/js/mall/goodPreview.js?ver=${ver!}"></script>
<script src="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.js?ver=${ver!}"></script>
<script src="${cdn}/js/framework/components/money.js?ver=${ver!}"></script>
<script src="${cdn}/js/framework/components/img-zoom.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>