<@override name="title">商城采购订单</@override>

<#--<style>-->
<#--    #RZ::-webkit-scrollbar {-->
<#--        width: 0;-->
<#--    }-->
<#--</style>-->
<@override name="content">

    <div class="w1200">

        <#include "/left-nav.ftl">

        <div id="RZ" class="" v-cloak>
            <!-- 内容区域 -->
            <div class="bg_fff" style="height: 100%;  overflow-x: hidden;overflow-y: scroll;">
                <div class="w1200">

                    <!-- 右侧内容 -->
                    <div class="right_con">

                        <div class="right_top">
                            <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                            <span class="c_blue fl ml10">商城采购订单</span>
                        </div>

                        <div class="p10">

                            <!-- 筛选 -->
                            <frame-filter
                                    :do-search="search"
                                    :data="filters"
                                    :conf="filtersConfig"
                                    ref="filters"
                            >
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

                            <!-- 表格 -->
                            <el-table
                                    :data="tableData"
                                    class="table_main_style expand_table"
                                    ref="multipleTable"
                                    @selection-change="handleSelectionChange"
                                    @expand-change="expandChange"
                                    :span-method="arraySpanMethod"
                            >
                                <el-table-column type="expand" width="30">
                                    <template slot-scope="scope">
                                        <div class="of tc table_expand" v-for="t in scope.row.tbmqq441VoList">

                                            <div class="tl fl w300"  @click="goDetail(t.goodid)" style="cursor:pointer" >

                                                <img :src="t.pictureurl != null && t.pictureurl.trim() != '' ? t.pictureurl : '/statics/img/shop/noPic.png'" class="fl mt10 ml20 mb10" alt="" width="68px" height="68px">

                                                <div class="tl w200 fl p10">
                                                    <div>{{t.productname}}{{t.brand != null && t.brand.trim() != '' ?'/'+t.brand:''}}</div>
                                                    <div class="lh10 c_999 f10">
                                                        <div v-if="t.goodno != null">货号：{{t.goodno}}</div>
                                                        <div>型规：{{t.spec}}</div>
                                                        <div>物料编码：{{t.matrlno}}</div>
                                                        <div>物料条码：{{t.matrltm}}</div>
                                                    </div>
                                                </div>
                                            </div>

                                            <div class="fl w100">
                                                <div><i class="c_red">￥{{t.price}}/{{t.qtyunit}}</i></div>
                                                <div class="f10 c_999">
                                                    <div>未税：￥{{t.notaxprice}}/{{t.qtyunit}}</div>
                                                    <div>税率：{{t.tax | formatTax}}%</div>
                                                </div>
                                            </div>

                                            <#--<div class="w80 fl">{{t.username2}}</div>
                                            <div class="w80 fl">{{t.userno2phone}}</div>-->

                                            <div class="w70 fl">{{t.qty}}</div>
                                            <div class="w70 fl">{{t.deliqty}}</div>
                                            <div class="w80 fl">{{t.takedeliqty}}</div>
                                            <div class="w80 fl">{{t.invoiceqty}}</div>

                                            <div class="fl w80">
                                                <div> <i class="c_red">￥{{t.amt}}</i></div>
                                                <div class="f10 c_999">
                                                    <div>未税：￥{{t.notaxamt}}</div>
                                                </div>
                                            </div>

                                            <div class="w90 fl">
                                                {{t.statusName}}
                                            </div>

                                            <div class="w90 fl">
                                                <button class="blue_txt_btn" v-if="t.isAddCart == 'Y'">已加入购物车</button>
                                                <button class="blue_txt_btn" @click="addCart(t)" v-else>加入购物车</button>
                                            </div>
                                        </div>
                                    </template>
                                </el-table-column>

                                <el-table-column label="商品信息" width="270">
                                    <template slot-scope="scope">
                                        <div class="of">
                                            <div class="w_50 fl h25 lh25 tl">
                                                <a :href="'/mall/purchaseOrderDetail/'+scope.row.orderno" class="c_blue">{{scope.row.orderno}}</a>
                                                <#--<span class="red_fff_txt">商城订单</span>-->
                                            </div>
                                            <div class="w_50 fl h25 lh25 ">{{scope.row.linename}}</div>
                                        </div>
                                    </template>
                                </el-table-column>

                                <el-table-column label="价格" width="100">
                                    <template slot-scope="scope">
                                        <div class="of">
                                            <div class="w_50 fl">{{scope.row.suppliername}}</div>
                                            <div class="w_50 fl">{{scope.row.createdate|strDateFormat}} {{scope.row.createtime|strTimeFormat}}</div>
                                        </div>
                                    </template>
                                </el-table-column>

                                <#--<el-table-column label="使用人" width="75"></el-table-column>
                                <el-table-column label="使用人联系方式" width="75"></el-table-column>-->

                                <el-table-column  label="订单数量" width="70"></el-table-column>
                                <el-table-column  label="发货数量" width="70"></el-table-column>
                                <el-table-column  label="收货数量" width="80"></el-table-column>
                                <el-table-column  label="开票数量" width="80"></el-table-column>

                                <el-table-column label="金额" width="80">
                                    <template slot-scope="scope">
                                        <div class="f14 fb c_red">¥{{scope.row.totalamt}}</div>
                                    </template>
                                </el-table-column>

                                <el-table-column label="状态" width="90">
                                    <template slot-scope="scope">
                                        <div class="">{{scope.row.statusName}}</div>
                                    </template>
                                </el-table-column>

                                <el-table-column  label="操作">
                                    <template slot-scope="scope">
                                        <div>
                                            <button class="main_self_btn" @click="toDetail(scope.row.orderno)">查看详情</button>
                                        </div>
                                        <div v-if="scope.row.status == '15' || scope.row.status == '25' || scope.row.status == '30'">
                                            <button class="main_self_btn mt5" @click="againOrder(scope.row.orderno)">再来一单</button>
                                        </div>
                                    </template>
                                </el-table-column>
                            </el-table>

                            <!-- 分页 -->
                            <div class="page">
                                <div class="fl">
                                    <span class="c_blue ml10 cur" @click="exportOrder">导出订单</span>
                                    <span class="c_blue ml10 cur" @click="exportOrderDetail">导出订单明细</span>
                                </div>

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


                </div>
            </div>

        </div>
    </div>

</@override>

<@override name="js">
    <script>
        var status = '${status!}';
    </script>
    <script src="${cdn}/js/framework/components/filters.js"></script>
    <script type="text/babel" src="${cdn}/js/mall/purchaseOrder.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>