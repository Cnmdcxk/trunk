<@override name="title">统计工作台</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
    <style type="text/css">
        [v-cloak]{
            display:none;
        }
        .menu_right li{
            padding:20px 0;
            font-size:16px;
            font-weight: 700;
        }
    </style>
</@override>

<!-- 内容区域 -->
<@override name="content">
    <div class="w1200">

    <!-- 左侧菜单 -->
    <#include "/left-nav.ftl">

    <!-- 右侧内容 -->
    <div class="right_con" id="RZ" v-cloak>
        <div class="right_top">
            <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
            <span class="c_blue fl ml10">统计工作台</span>
        </div>
        <div class="mt5">
            <div class="">
                <ul class="menu_right supplier_menu">
                    <li><a href="/mall/pendingOrders">待接单({{count1}})</a></li>
                    <li><a href="/mall/pendingDelivery">待交货({{count2}})</a></li>
                    <li class="curr">临到期合同({{count3}})</li>
                    <li><a href="/mall/unlistedGoods">未上架商品({{count4}})</a></li>
                </ul>
                <div class="p10">
                    <div class="result_style">
                        <span class="disib w90 tl c_999 fl">统计结果</span>
                        <div class="fl">
                            <el-tooltip effect="dark" placement="top">
                                <div slot="content">
                                    <span class="c_red f20 fb lh30">{{ totalCount }}</span> 条
                                </div>
                                <span class="c_red f20 fb">{{ totalCount }}</span>
                            </el-tooltip> 条
                        </div>
                    </div>
                    <!-- 表格 -->
                    <div class="pr">
                        <el-table
                                :data="tableData"
                                class="table_main_style expand_table"
                                @expand-change="expandChange"

                        >
                            <el-table-column type="expand" width="50" >
                                <template slot-scope="scope" >
                                    <div class="of tc table_expand"  v-for="item in scope.row.goodslist"  >
                                        <div class="w240">
                                        </div>
                                        <div class="tl fl w300" >
                                            <div>{{item.matrltm}}</div>
                                        </div>
                                        <div class="tl fl w440" >
                                            <div>{{item.matrlno}}</div>
                                        </div>
                                        <div class="tl fl w270" >
                                            <div>{{item.productname}}</div>
                                        </div>
                                        <div class="w350 fl">
                                            {{item.spec}}
                                        </div>
                                        <div class="w100 fl">
                                            {{item.qtyunit}}
                                        </div>
                                        <div class="w200 fl">
                                            <div class="f14 fb c_red">¥{{item.price}}</div>
                                        </div>
                                    </div>
                                    <div class="page">
                                        <el-pagination
                                                :current-page="scope.row.pageIndex1"
                                                :page-size="scope.row.pageSize1"
                                                :total="scope.row.totalCount1"
                                                :page-sizes="[10,30,50,100]"
                                                @current-change="pageChange1(scope.row,$event)"
                                                @size-change="sizeChange1(scope.row,$event)"
                                                layout="total, sizes, prev, pager, next"
                                                class="fr"
                                                background>
                                        </el-pagination>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column  prop="date" label="物资条码" width="200" >
                                <template slot-scope="scope">
                                    供应商： {{scope.row.supplierno}}-{{scope.row.suppliername}}
                                </template>
                            </el-table-column>
                            <el-table-column  prop="matrltm" label="物资编码" width="200" >
                                <template slot-scope="scope">
                                    合同编号：{{scope.row.pono}}
                                </template>
                            </el-table-column>
                            <el-table-column  prop="date" label="商品名称" width="200" >
                                <template slot-scope="scope">
                                    <div style="display: flex">
                                        <div>合同有效期：</div>
                                        <div>
                                            <div>{{scope.row.popricestartdate | strDateFormat }}</div>
                                            <div>{{scope.row.popricedate | strDateFormat }}</div>
                                        </div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column  prop="date" label="规格型号"  width="200">
                                <template slot-scope="scope">
                                    物资采购人：{{scope.row.agentno}}-{{scope.row.agent}}
                                </template>
                            </el-table-column>
                            <el-table-column  label="单位" >

                            </el-table-column>
                            <el-table-column  prop="date" label="单价" >
                                <template slot-scope="scope">
                                <div class="fl">
                                    <span class="c_blue ml10" style="cursor: pointer" @click="exportExcel(scope.row)">导出明细</span>
                                </div>
                                </template>
                            </el-table-column>

                        </el-table>
                    </div>
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
            </div>
        </div>

    </div>
</@override>

<@override name="js">
    <script type="text/babel" src="${cdn}/js/mall/expiringContracts.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>