<@override name="title">商品详情</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.css">
    <link rel="stylesheet" href="${cdn}/css/home.css">
    <link rel="stylesheet" type="text/css" href="${cdn}/css/shop.css"/>
    <link rel="stylesheet" href="${cdn}/css/img-zoom.css">
    <style>

    </style>
</@override>


<@override name="content">

    <#--<#include "/bread-crumb.ftl">-->

    <div class="">

        <#include "/bread-crumb.ftl">

        <!-- 内容区域 -->
        <div class="bg_fff" id="RZ" v-cloak>
                <div class="w1200">

                    <div class="mb30 f14 pt15">
                        <a style="color: #0F83E6" class="fl" href="/"><div >首页 ></div></a>
                        <a style="color: #0F83E6" class="fl"><div @click="goShoppingFilter('categoryname',goods.categoryname,'categorypk',goods.categorypk)">{{goods.categoryname}} ></div></a>
                        <a style="color: #0F83E6" class="fl"><div @click="goShoppingFilter('onelevelclaname',goods.onelevelclaname,'onelevelclapk',goods.onelevelclapk)">{{goods.onelevelclaname}} ></div></a>
                        <a style="color: #0F83E6" class="fl"><div @click="goShoppingFilter('twolevelclaname',goods.twolevelclaname,'twolevelclapk',goods.twolevelclapk)">{{goods.twolevelclaname}}</div></a>
                    </div>

                    <div class="of">
                        <!--放大镜  -->
                        <div class="w330 fl">
                            <img-zoom :img_list="imgList" :cdn="cdn" class="fl"></img-zoom>
                        </div>

                        <div class="fl ml30" style="width: 830px;">
                            <div class="f20 mb15">{{goods.productname}}
                            <span @click="editRemark(goods)">
                                <i class="iconfont c_orange icon-liaotian" style="font-size: 30px;"></i>
                            </span>
                            </div>
                            <div class="bg_f8f8f8 p10 c_666 lh30">
                                <div class="of" style="display: flex;flex-direction: row;align-items: center">
                                    <span class="w70">价 格：</span>
                                    <span class="c_red" style="display: flex;flex-direction: row;align-items: center">
                                        <money-view :money="goods.price"></money-view>/{{goods.qtyunit}}
                                        <el-tooltip v-if="goods.futurePrice!= null" effect="light" placement="bottom" style="width: 5px;height: 5px;display: flex;align-items: center;margin: 0 10px 0 5px">
                                            <div slot="content" style=";font-size: 13px">
                                                新协议价:<money-view :money="goods.futurePrice" style="margin-left: 5px"></money-view>
                                                <span class="c_red">/{{goods.qtyunit}}</span>
                                                <br/>
                                                协议生效时间:<span style="margin:10px 0 10px 5px">{{goods.futurePoPriceStartDate}}</span>
                                            </div>
                                            <i class="iconfont c_orange icon-tishi1" style="width: 5px;height: 5px"></i>
                                        </el-tooltip>
                                    </span>
                                    <span @click="openSimilarDialog(goods.matrltm)"><a class="c_blue ml10">同类比价</a></span>
                                </div>

                                <div class="of">
                                    <span class="w70">未税价格：</span>
                                    <span>
                                        ￥{{goods.notaxprice}}/{{goods.qtyunit}}（税率：{{goods.tax | formatTax}}%）
                                    </span>
                                </div>
                            </div>
                            <div class="of lh30 mt10 mb20 pb20 c_666 pl10" style="border-bottom: 1px dashed #DEDEDE;width: 850px">
                                <div class="fl w_40"><span class="w80">品牌名称：</span>  <span class="c_blue">{{goods.brand}}</span></div>
                                <div class="fl w_40"><span class="w80">材质：</span>  <span class="">{{goods.qlty}}</span></div>
                                <div class="fl w_40"><span class="w80">起订量：</span>  <span class="" v-if="goods.minbuyqty != null">{{goods.minbuyqty}}/{{goods.qtyunit}}</span></div>
                                <div class="fl w_40" v-if="goods.goodno!=''&&goods.goodno!==' '"><span class="w80">货号：</span>  <span class="">{{goods.goodno}}</span></div>
                                <div class="fl w_40"><span class="w80">物料条码：</span>  <span class="">{{goods.matrltm}}</span></div>
                                <div class="fl w_40"><span class="w80">物料编码：</span>  <span class="">{{goods.matrlno}}</span></div>
                                <div class="fl w_40"><span class="w80">协议号：</span>  <span class="">{{goods.pono}}</span></div>
                                <div class="fl w_40"><span class="w80">物资采购人：</span>  <span class="">{{goods.agent}}</span></div>
                                <div class="fl w_40"><span class="w80">图纸：</span>  <span class="">{{goods.haspic == '1' ? '有图纸' : '无图纸'}}</span></div>
                                <#--<div class="fl w_40"><span class="w120">供应商联系方式：</span>  <span class="" @click="showBizContact(goods.supplierno)"><a class="c_blue ml10">查看</a></span></div>-->
                                <div class="fl w_40"><span class="w80 fl">商品型规：</span>  <span class="w230 fl">{{goods.spec}}</span></div>
                            </div>
                            <div class="mt10 c_666 ml10">
                                交货周期：{{goods.leadtimenum}}个工作日
                            </div>
                            <div class="mt10 c_666 ml10">
                                物资定性：{{goods.wzdxmc}}
                            </div>

                            <div class="mt50 mb10">
                                <el-input-number
                                        size="small"
                                        :precision="goods.isdecimalpurchase == 'Y' ? 3:0 "
                                        :step="goods.isinttimepurchase == 'Y' && goods.minbuyqty > 0 ? goods.minbuyqty: 1"
                                        v-model="goods.qty"
                                        class="ml10 mt5 fl"
                                        :min="goods.minbuyqty > 0 ? goods.minbuyqty : 1"
                                        :step-strictly="goods.isinttimepurchase == 'Y'"
                                >
                                </el-input-number>
                                <button class="main_btn_red ml10 fl" @click.stop="addCart(goods, $event)">
                                    <i class="iconfont icon-gouwuche"></i> 加入购物车
                                </button>

                                <button class="main_btn_red_line ml10 fl" v-if="goods.isCollect != 'Y'" @click="addCollect">
                                    <i class="iconfont icon-shoucang"></i> 添加收藏
                                </button>

                                <button class="main_btn_red_line ml10 fl" v-if="goods.isCollect == 'Y'" @click="delMyCollect">
                                    <i class="iconfont icon-shoucang2"></i> 取消收藏
                                </button>

                                <div class="clear"></div>
                            </div>
                        </div>
                    </div>
                </div>

            <!-- 浏览历史 -->
                <div class="w1200">
                    <div class="history">
                        <div class="history-left">
                            <div class="title">浏览历史</div>
                            <div class="history-left-box">
                                <div class="history-left-item" v-for="item in viewHistory">
                                    <div class="fl imgbox" @click="goDetail(item.goodid)" style="cursor: pointer" >
                                        <img :src="item.pictureurl != null && item.pictureurl.trim()?item.pictureurl:'/statics/img/shop/noPic.png'" alt="" class="img">
                                    </div>
                                    <div class="fl w140 ml10">
                                        <div class="item-title of" :title="item.productname">{{item.productname}}</div>
                                        <div class="c_red f14"><money-view :money="item.price"></money-view>/{{item.qtyunit}}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="fl history-right" >
                            <div v-if ="groupDisplay">
                                <div class="blue-title">
                                    <div class="blue-title-item curr">同组其他型号</div>
                                </div>
                                <!-- 搜索 -->
                                <div class="mt20 of mb20">
                                    <div class="search-input" style="float: left;width: 330px;">
                                        <input type="text" v-model="searchKey" placeholder="商品名称／品牌／物料条码／物料编码／型规">
                                        <i @click="search"  class="iconfont icon-zoom_x"></i>
                                    </div>
                                </div>
                                <!--表格  -->
                                <el-table :data="goodsGroup" class="table_main_style">
                                    <el-table-column  prop="date" label="商品信息" width="240">
                                        <template slot-scope="scope">
                                            <div class="fl pt10 ml10" @click="goDetail(scope.row.goodid)" style="cursor: pointer">
                                                <img width="80" height="80" :src="scope.row.pictureurl != null && scope.row.pictureurl.trim()?scope.row.pictureurl:'/statics/img/shop/noPic.png'" >
                                            </div>
                                            <div class="fl w140 pl10 ptb10 tl lhno">
                                                <span class="f12 fb">{{scope.row.productname}}</span>
                                                <span class="f10 c_666" v-if="scope.row.goodno!=''&&scope.row.goodno!=' '">货号：{{scope.row.goodno}}</span></br>
                                                <span class="f10 c_666">型规：{{scope.row.spec}}</span></br>
                                                <span class="f10 c_666">物料条码：{{scope.row.matrltm}}</span>
                                                <span class="f10 c_666">物料编码：{{scope.row.matrlno}}</span>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column  prop="date" label="交货周期" width="190">
                                        <template slot-scope="scope">
                                            <div>{{scope.row.leadtimenum}}个工作日</div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column  prop="date" label="含税单价（元）" width="160">
                                        <template slot-scope="scope">
                                            <div class="c_red f14"><money-view :money="scope.row.price"></money-view>/{{scope.row.qtyunit}}</div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column  prop="date" label="订货数量" width="170">
                                        <template slot-scope="scope">
                                            <div>
                                                <el-input-number
                                                        size="small"
                                                        :precision="scope.row.isdecimalpurchase == 'Y' ? 3:0 "
                                                        :step="scope.row.isinttimepurchase == 'Y' && scope.row.minbuyqty > 0 ? scope.row.minbuyqty: 1"
                                                        v-model="scope.row.qty"
                                                        class="ml10"
                                                        :min="scope.row.minbuyqty > 0 ? scope.row.minbuyqty : 1"
                                                        :step-strictly="scope.row.isinttimepurchase == 'Y'"
                                                >
                                                </el-input-number>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column  prop="date" label="操作" width="180">
                                        <template slot-scope="scope">
                                            <div>
                                                <button class="main_btn_red_small ml10" @click.stop="addCart(scope.row,$event)">
                                                    <i class="iconfont icon-gouwuche"></i> 加入购物车
                                                </button>
                                            </div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                                <!-- 分页 -->
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


                            <div class="blue-title">
                                <div style="cursor: pointer" class="blue-title-item " :class="{'curr':isPlayIn}" @click="changeDisplay('1')">商品介绍</div>
                                <div style="cursor: pointer" class="blue-title-item"  :class="{'curr':isPlayAd}" @click="changeDisplay('2')">商品评价</div>
                            </div>

                            <#--商品介绍-->
                            <div v-if="isPlayIn" >
                                <div class="of lh30 mt20 mb20 pb20 c_666 ml30" style="border-bottom: 1px dashed #DEDEDE;" >
                                    <div class="fl w_40"><span class="w80">品牌名称：</span>  <span class="c_blue">{{goods.brand}}</span></div>
                                    <div class="fl w_40"><span class="w80">起订量：</span>  <span class="" v-if="goods.minbuyqty != null">{{goods.minbuyqty}}/{{goods.qtyunit}}</span></div>
                                    <div class="fl w_40" v-if="goods.goodno!=' '&&goods.goodno!=''"><span class="w80">货号：</span>  <span class="">{{goods.goodno}}</span></div>
                                    <div class="fl w_50"><span class="w80 fl">商品型号：</span>  <span class="lh20" style="width: 370px;">{{goods.spec}}</span></div>
                                </div>

                                <div class="fl" v-html="goods.content">
                                </div>
                            </div>

                            <#--商品评价-->
                            <div class="mt20" v-if="isPlayAd">
                                <div class="of lh30 c_666 pl20 pr20 mb15" style="background:rgba(217, 36, 55, 0.06);">
                                    <span class="mr30" v-bind:class="{'c_red':commentPage.pickLevel == 'all'}" @click="getComment('all')" style="cursor:pointer">
                                        全部评价({{commentPage.countInfo['all'] == null ? '0' : commentPage.countInfo['all']}})
                                    </span>
                                    <span class="mr30" v-bind:class="{'c_red':commentPage.pickLevel == '0'}" @click="getComment('0')" style="cursor:pointer">
                                        好评({{commentPage.countInfo['0'] == null ? '0' : commentPage.countInfo['0']}})
                                    </span>
                                    <span class="mr30" v-bind:class="{'c_red':commentPage.pickLevel == '1'}" @click="getComment('1')" style="cursor:pointer">
                                        中评({{commentPage.countInfo['1'] == null ? '0' : commentPage.countInfo['1']}})
                                    </span>
                                    <span class="mr30" v-bind:class="{'c_red':commentPage.pickLevel == '2'}" @click="getComment('2')" style="cursor:pointer">
                                        差评({{commentPage.countInfo['2'] == null ? '0' : commentPage.countInfo['2']}})
                                    </span>
                                </div>
                                <!-- 评价循环开始 -->
                                <div class="mb20 of pb15" v-for="item in commentPage.commentList" style="border-bottom: 1px dashed #DEDEDE;">
                                    <div class="fl tc w100 mr20">
                                        <div class="tc txt_of c_666">{{item.commentuserno}}-{{item.commentusername}}</div>
                                    </div>
                                    <div class="fl" style="width: calc(100% - 120px);">
                                        <el-rate v-model="item.score" void-color="#FDF2F3" disabled :colors="['#D92437','#D92437','#D92437']"></el-rate>
                                        <div class="lh20">
                                            {{item.commentcontent}}
                                        </div>
                                        <div class="of mt10">
                                            <#--<img v-for="imgUrl in item.images" :src="imgUrl" alt="" width="54" height="54" class="mr20 bd">-->
                                            <el-image
                                                    v-for="(imgUrl,index) in item.images"
                                                    style="width: 54px; height: 54px"
                                                    class="mr20 bd"
                                                    :src="imgUrl"
                                                    :preview-src-list="getCommentPreviewImages(item.images,index)"
                                                    z-index="10001"
                                            >
                                            </el-image>
                                        </div>
                                        <div class="tr c_999">{{item.commentdate | strDateFormat}} {{item.commenttime | strTimeFormat}}</div>
                                    </div>
                                </div>
                                <!-- 循环结束 -->

                                <el-pagination
                                        :current-page="commentPage.pageIndex"
                                        :page-size="commentPage.pageSize"
                                        :total="commentPage.totalCount"
                                        :page-sizes="[10, 30, 50, 100]"
                                        @current-change="commentPageChange"
                                        @size-change="commentSizeChange"
                                        layout="total, sizes, prev, pager, next"
                                        class="fr"
                                        background>
                                </el-pagination>

                            </div>

                        </div>

                        <#include '../dialog/priceComparisonDialog.ftl'>
                        <#include '../dialog/supplierBizContact.ftl'>

                        <el-dialog title="即时通讯"
                                   id="tx"
                                   :visible.sync="showEditRemark"
                                   :close-on-click-modal="false"
                                   :lock-scroll="false"
                                   width="900px"
                                   center>
                            <div>
                                <iframe id="communication"
                                        src="http://kfk8sn1.yong-gang.cn:30046"
                                        style="width: 100%;height: 900px"
                                >
                                </iframe>
                            </div>
                            <span slot="footer" class="dialog-footer" style="padding: 0px 20px 20px">
                               <el-button @click="showEditRemark = false">隐藏</el-button>
                            </span>
<#--                            <span slot="footer" class="dialog-footer" style="padding: 0px 20px 20px">-->
<#--                                <el-button type="primary" @click="updateItemRemark">确定</el-button>-->
<#--                                <el-button @click="showEditRemark = false">取消</el-button>-->
<#--                            </span>-->
                        </el-dialog>
                    </div>
                </div>
        </div>
        <#include "/toolbox.ftl">
    </div>



</@override>

<@override name="js">
    <script>
        var keys = '${keys!}';
        var goodsId = '${goodsId!}';
        var cdn = '${cdn!}';
    </script>

    <script src="${cdn}//js/3rd/jquery/jquery.fly.min.js"></script>
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/goodsDetail.js?ver=${ver!}"></script>
    <script src="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.js?ver=${ver!}"></script>
    <script src="${cdn}/js/framework/components/money.js?ver=${ver!}"></script>
    <script src="${cdn}/js/framework/pager.js?ver=${ver!}"></script>
    <script src="${cdn}/js/framework/components/img-zoom.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>