<@override name="title">库存查询</@override>


<@override name="content">

<div class="w1200">

    <#include "/left-nav.ftl">

    <div id="RZ" class="" v-cloak>
        <!-- 内容区域 -->
        <div class="bg_fff">
            <div class="w1200">

                <!-- 右侧内容 -->
                <div class="right_con">

                    <div class="right_top">
                        <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                        <span class="c_blue fl ml10">库存查询</span>
                    </div>

                    <div class="mt5">
                        <div class="bd_t_dedede">
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
                                        class="table_main_style"
                                        ref="multipleTable"
                                >

                                    <el-table-column prop="matrlId" label="物料ID"></el-table-column>
                                    <el-table-column prop="matrlTm" label="物料条码"></el-table-column>
                                    <el-table-column prop="totalInventory" label="商城占用库存+仓库剩余库存" width="190"></el-table-column>
                                    <el-table-column prop="warehouseInventoryMax" label="仓库库存上限" width="130"></el-table-column>
                                    <el-table-column label="商城占用库存" width="130">
                                        <template slot-scope="scope">
                                            <div class="">
                                                <span>{{scope.row.mallInventory}}</span>
                                                <span @click="showMallInventoryDetail(scope.row.matrlId)"><a class="c_blue">查看</a></span>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="warehouseInventory" label="仓库剩余库存" width="130"></el-table-column>
                                    <el-table-column label="查询时间">
                                        <template slot-scope="scope">
                                            <div class="">
                                                <span>{{searchTime}}</span>
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
                                <div class="tr clear pb20 lh25">
                                    <button class="main_btn mr10" @click="searchCore()">查询</button>
                                </div>
                            </div>
                        </div>
                    </div>

                    <el-dialog title="商城库存详情"
                               :visible.sync="showDetail"
                               :close-on-click-modal="false"
                               :lock-scroll="false"
                               width="1000px"
                               center>
                        <div>
                            <el-table :data="mallInventoryDetail.tableData" class="table_main_style">
                                <el-table-column prop="orderNo" label="订单号"></el-table-column>
                                <el-table-column prop="orderDetailNo" label="订单项次号"></el-table-column>
                                <el-table-column prop="matrlTm" label="物料条码"></el-table-column>
                                <el-table-column prop="qty" label="下单数量"></el-table-column>
                                <el-table-column label="下单时间">
                                    <template slot-scope="scope">
                                        <div class="">
                                            <span>{{scope.row.createDate|strDateFormat}} {{scope.row.createTime|strTimeFormat}}</span>
                                        </div>
                                    </template>
                                </el-table-column>
                                <el-table-column prop="statusName" label="状态"></el-table-column>
                                <el-table-column prop="supplierNameStr" label="供应商"></el-table-column>
                            </el-table>

                            <!-- 分页 -->
                            <div class="page">
                                <el-pagination
                                        :current-page="mallInventoryDetail.pageIndex"
                                        :page-size="mallInventoryDetail.pageSize"
                                        :total="mallInventoryDetail.totalCount"
                                        :page-sizes="[10, 30, 50, 100]"
                                        @current-change="mallInventoryDetailPageChange"
                                        @size-change="mallInventoryDetailSizeChange"
                                        layout="total, sizes, prev, pager, next"
                                        class="fr"
                                        background>
                                </el-pagination>
                            </div>
                        </div>
                        <span slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="showDetail = false">关 闭</el-button>
                        </span>
                    </el-dialog>

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
    <script type="text/babel" src="${cdn}/js/mall/goodsInventory.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>