<@override name="title">商城</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/home.css">
    <link rel="stylesheet" href="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.css">
    <link rel="stylesheet" href="${cdn}/css/shop.css"/>
</@override>


<@override name="content">


    <div>
        <!-- logo -->
        <#include '/bread-crumb.ftl'>

        <!-- 内容区域 -->
        <div id="RZ" class="bg_fff" v-cloak>
            <div class="w1200">
                <div class="pt10">
                    <!-- 筛选 -->
                    <frame-filter
                            :do-search="search"
                            :data="filters"
                            :conf="filtersConfig"
                            ref="filters">

                    </frame-filter>
                    <div class="result_style">
                        <span class="disib w90 tl c_999 fl">统计结果</span>
                        <div class="fl">
                            <el-tooltip effect="dark" placement="top">
                                <div slot="content">
                                    <span class="c_red f20 fb lh30">{{totalCount}}</span> 条
                                </div>
                                <span class="c_red f20 fb">{{totalCount}}</span>
                            </el-tooltip> 条
                        </div>
                    </div>

                    <div class="tap_goods of mtb20">
                        <div class="w100 disib tc" v-bind:class="{'tap_xuanzhong': orderBy == 't1.shelvesdate||t1.shelvestime'}">默认
                            <span @click="onOrderBy('t1.shelvesdate||t1.shelvestime',!asc)" v-bind:class="{'ascending': orderBy == 't1.shelvesdate||t1.shelvestime' && asc, 'descending': orderBy == 't1.shelvesdate||t1.shelvestime' && !asc}">
                                <span class="caret-wrapper">
                                    <i class="sort-caret ascending"></i>
                                    <i class="sort-caret descending"></i>
                                </span>
                            </span>
                        </div>

                        <div class="w100 disib tc" v-bind:class="{'tap_xuanzhong': orderBy == 't438.qty'}">销量
                            <!-- 默认不加class 上三角点亮 ascending  上三角点亮 descending -->
                            <span @click="onOrderBy('t438.qty', !asc)" v-bind:class="{'ascending': orderBy == 't438.qty' && asc, 'descending': orderBy == 't438.qty' && !asc}">
                                <span class="caret-wrapper">
                                    <i class="sort-caret ascending"></i>
                                    <i class="sort-caret descending"></i>
                                </span>
                            </span>
                        </div>

                        <div class="w100 disib tc" v-bind:class="{'tap_xuanzhong': orderBy == 't1.price'}" >价格
                            <span @click="onOrderBy('t1.price',!asc)" v-bind:class="{'ascending': orderBy == 't1.price' && asc, 'descending': orderBy == 't1.price' && !asc}">
                                <span class="caret-wrapper">
                                    <i class="sort-caret ascending" ></i>
                                    <i class="sort-caret descending"></i>
                                </span>
                            </span>
                        </div>
                    </div>

                    <div class="of">

                        <div class="goodsItem" v-for="(item,index) in tableData">
                            <div class="imgBox" @click="goDetail(item.goodid)"style="cursor: pointer">
                                <img :src="item.pictureurl?item.pictureurl:'/statics/img/shop/noPic.png'" alt="" class="img">
                            </div>
                            <div class="goodsInfo p10">
                                <div v-if="isLogin&&role=='B'" class="c_red" style="display: flex;flex-direction: row;align-items: center">
                                    <money-view :money="item.price"></money-view>/{{item.qtyunit}}
                                    <el-tooltip v-if="item.futurePrice!= null" effect="light" placement="bottom" style="width: 5px;height: 5px;display: flex;align-items: center">
                                        <div slot="content" style=";font-size: 13px">
                                            新协议价:<money-view :money="item.futurePrice" style="margin-left: 5px"></money-view>
                                            <span class="c_red">/{{item.qtyunit}}</span>
                                            <br/>
                                            协议生效时间:<span style="margin:10px 0 10px 5px">{{item.futurePoPriceStartDate}}</span>
                                        </div>
                                        <i class="iconfont c_orange icon-tishi1" style="width: 5px;height: 5px;margin-left: 5px"></i>
                                    </el-tooltip>
                                </div>
                                <div class="title mb5 mt5 h50 of" :title="item.productname">
                                    {{item.productname}}{{item.spec != null && item.spec !='' ? '/'+item.spec : ''}}
                                </div>
                                <div class="info">
                                    <div class="info">
                                        <div class="mb5">物料编码：{{item.matrlno}}</div>
                                        <div class="mb5">物料条码：{{item.matrltm}}</div>
                                        <div>交货周期：{{item.leadtimenum}}个工作日</div>
                                        <div>物资定性：{{item.wzdxmc}}</div>
                                    </div>
                                <div class="footer">
                                    <button class="left_shou fl" v-if="item.isCollect == 'N'" @click="addCollect(item)">
                                        <i class="iconfont mr5 icon-shoucang c_red" class="icon-shoucang" ></i>收藏
                                    </button>

                                    <button class="left_shou fl" v-if="item.isCollect == 'Y'"  @click="delMyCollect(item)">
                                        <i class="iconfont mr5 icon-shoucang2 c_red" class="icon-shoucang2 c_red"></i>取消收藏
                                    </button>

                                    <button v-if="item.isAddCart != 'Y'" @click.stop="addShoppingCart($event, item)" class="right_add pr">
                                        <i class="iconfont icon-gouwuche mr5 c_red"></i>加入购物车
                                    </button>

                                    <button v-if="item.isAddCart == 'Y'"  class="right_add pr bg_dedede">
                                        <i class="iconfont icon-gouwuche mr5 c_red"></i>已加入购物车
                                    </button>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!-- 分页 -->
                    <div class="page" style="margin-top: -30px;" v-if="totalCount!==0">

                        <div class="page">
                            <el-pagination
                                    :current-page="pageIndex"
                                    :page-size="pageSize"
                                    :total="totalCount"
                                    :page-sizes="[10, 30, 50, 100]"
                                    @current-change="pageChange"
                                    @size-change="sizeChange"
                                    layout="total, sizes, prev, pager, next"
                                    class="fr"
                                    background>
                            </el-pagination>
                        </div>

                    </div>
                </div>
            </div>

            <el-dialog :visible.sync="showCollectDialog" width="370px" center :close-on-click-modal="false">

                <div class="w_90 margin">
                    <div class="tc mb15 tan_head_text">
                        <i class="iconfont icon-duihao1 c_green f24" style="vertical-align: middle;"></i>
                        <span class="tc mt0 f16 fb"> 商品收藏成功</span>
                    </div>
                    <div class="tc">您已收藏1个商品！您可以 <a class="c_blue" href="/mall/favorites">查看收藏夹</a> </div>
                </div>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="showCollectDialog = false">确定</el-button>
            </span>
            </el-dialog>
        </div>
    </div>

    <#include "/toolbox.ftl">
</@override>

<@override name="js">
    <script>
        var searchKey = '${searchKey!}';
        var twolevelclaname = '${twolevelclaname!}';
        var twolevelclapk = '${twolevelclapk!}';
        var categoryname = '${categoryname!}';
        var categorypk = '${categorypk!}';
        var onelevelclaname='${onelevelclaname!}';
        var onelevelclapk='${onelevelclapk!}';
        var productname='${productname!}';
        var role = '${role!}';
        var IS_LOGIN = '${UserID!}' !== '';
        var cdn = '${cdn!}';
    </script>
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script src="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.js?ver=${ver!}"></script>
    <script src="${cdn}/js/framework/components/money.js?ver=${ver!}"></script>
    <script src="${cdn}/js/framework/pager.js?ver=${ver!}"></script>
    <script src="${cdn}//js/3rd/jquery/jquery.fly.min.js"></script>
    <script type="text/babel" src="${cdn}/js/mall/shoppingFilter.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>