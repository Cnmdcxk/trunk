<@override name="title">订单管理</@override>

<#--<style>-->
<#--    .bg_fff::-webkit-scrollbar {-->
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
                            <span class="c_blue fl ml10">订单管理</span>
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
                                            @selection-change="handleSelectionChange"
                                    >

                                        <el-table-column prop="orderno" label="订单号"></el-table-column>
                                        <el-table-column label="下单人">
                                            <template slot-scope="scope">
                                                <div class="">{{scope.row.userno}}-{{scope.row.username}}</div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="deptname" label="部门"></el-table-column>
                                        <el-table-column label="供应商">
                                            <template slot-scope="scope">
                                                <div class="">{{scope.row.supplierno}}-{{scope.row.suppliername}}</div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column prop="totalqty" label="订单数量"></el-table-column>
                                        <el-table-column label="下单时间">
                                            <template slot-scope="scope">
                                                <div class="">{{scope.row.createdate|strDateFormat}}<br/>{{scope.row.createtime|strTimeFormat}}</div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="审批完结时间">
                                            <template slot-scope="scope">
                                                <div class="">{{scope.row.approvedate|strDateFormat}}<br/>{{scope.row.approvetime|strTimeFormat}}</div>
                                            </template>
                                        </el-table-column>


                                        <el-table-column label="金额" width="75">
                                            <template slot-scope="scope">
                                                <div class="f14 fb c_red">¥{{scope.row.totalamt}}</div>
                                            </template>
                                        </el-table-column>

                                        <el-table-column label="状态" width="75">
                                            <template slot-scope="scope">
                                                <div class="">{{scope.row.statusName}}</div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="是否超时接单">
                                            <template slot-scope="scope">
                                                <div class="">{{scope.row.isTimeOutOrder == 'Y' ? '是':'否'}}</div>
                                            </template>
                                        </el-table-column>
                                        <el-table-column label="操作">
                                            <template slot-scope="scope">
                                                <div class="">
                                                    <button class="blue_txt_btn" v-if="scope.row.status == 15" @click="showInvalidOrderHandle(scope.row)">作废</button>
                                                    <button class="blue_txt_btn" v-else-if="scope.row.invalidreason != null" @click="viewInvalidReason(scope.row)">查看作废原因</button>
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

                                    <#--订单作废-->
                                    <el-dialog title="作废原因"
                                               :visible.sync="invalidOrderHandle"
                                               :close-on-click-modal="false"
                                               :lock-scroll="false"
                                               width="320px"
                                               center>
                                        <div class="lh30 pt5">
                                            <div class="of">
                                                <el-input type="textarea" class="fl"
                                                          :autosize="{ minRows: 4}"
                                                          v-model="invalidReason"
                                                          maxlength="500"
                                                          show-word-limit>
                                                </el-input>
                                            </div>
                                        </div>
                                        <span slot="footer" class="dialog-footer">
                                        <el-button type="primary" @click="invalidOrder">确定</el-button>
                                        <el-button @click="invalidOrderHandle = false">取消</el-button>
                                    </span>
                                    </el-dialog>

                                    <#--查看作废原因-->
                                    <el-dialog title="作废原因"
                                               :visible.sync="showInvalidReason"
                                               :close-on-click-modal="false"
                                               :lock-scroll="false"
                                               width="320px"
                                               center>
                                        <div class="lh30 pt5">
                                            <div class="of">
                                                <span>作废人:</span>
                                                <span>{{invalidOrderItem.invaliduser}}-{{invalidOrderItem.invalidusername}}</span>
                                            </div>
                                            <div class="of">
                                                <span>作废时间:</span>
                                                <span>{{invalidOrderItem.invaliddate|strDateFormat}}&nbsp;{{invalidOrderItem.invalidtime|strTimeFormat}}</span>
                                            </div>
                                            <div class="of">
                                                <span>作废原因:</span>
                                                <span>{{invalidOrderItem.invalidreason}}</span>
                                            </div>
                                        </div>
                                        <span slot="footer" class="dialog-footer">
                                        <el-button @click="showInvalidReason = false">关闭</el-button>
                                    </span>
                                    </el-dialog>

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
    <script type="text/babel" src="${cdn}/js/mall/orderManage.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>