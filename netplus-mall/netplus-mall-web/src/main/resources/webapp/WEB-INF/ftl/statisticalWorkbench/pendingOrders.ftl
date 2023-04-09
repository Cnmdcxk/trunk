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
        <div class="right_con" id="RZ" class="" v-cloak>
            <div class="right_top">
                <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                <span class="c_blue fl ml10">统计工作台</span>
            </div>
            <div class="mt5">
                <div class="bd_t_dedede">
                    <ul class="menu_right supplier_menu">
                        <li class="curr">待接单({{count1}})</li>
                        <li><a href="/mall/pendingDelivery">待交货({{count2}})</a></li>
                        <li><a href="/mall/expiringContracts">临到期合同({{count3}})</a></li>
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
                        <el-table
                                class="table_main_style"
                                :data="dataList"
                                style="width: 100%"
                                ref="multipleTable">
                            <el-table-column prop="updatedate" label="供应商" width="200">
                                <template slot-scope="scope">
                                    {{scope.row.supplierno}}-{{scope.row.suppliername}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="orderno" label="订单号" width="140"></el-table-column>
                            <el-table-column prop="productname" label="商品名称"></el-table-column>
                            <el-table-column prop="spec" label="规格型号"></el-table-column>
                            <el-table-column prop="qtyunit" label="单位"></el-table-column>
                            <el-table-column prop="qty" label="数量"></el-table-column>
                            <el-table-column label="总金额" width="75">
                                <template slot-scope="scope">
                                    <div class="f14 fb c_red">¥{{scope.row.amt}}</div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="deptname" label="下单部门"></el-table-column>
                            <el-table-column prop="" label="下单人" width="130">
                                <template slot-scope="scope">
                                    {{scope.row.userno}}-{{scope.row.username}}
                                </template>
                            </el-table-column>
                            <el-table-column label="审批完结时间">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.approvedate|strDateFormat}}<br/>{{scope.row.approvetime|strTimeFormat}}</div>
                                </template>
                            </el-table-column>
                            <el-table-column label="物资采购人">
                                <template slot-scope="scope">
                                    {{scope.row.agent}}
                                </template>
                            </el-table-column>
                        </el-table>
                        <!-- 分页 -->
                        <div class="page">
                            <div class="fl">
                                <span class="c_blue ml10" @click="exportExcel" style="cursor: pointer">导出明细</span>
                            </div>
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
</@override>

<@override name="js">
    <script type="text/babel" src="${cdn}/js/mall/pendingOrders.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>