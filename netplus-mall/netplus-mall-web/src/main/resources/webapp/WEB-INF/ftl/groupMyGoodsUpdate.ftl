<@override name="title">全部</@override>

<@override name="css"></@override>

<!-- 内容区域 -->
<@override name="content">
    <div class="w1200">
        <!-- 左侧菜单 -->
        <#include "/left-nav.ftl">

        <!-- 右侧内容 -->
        <div class="right_con" id="RZ" v-cloak>
            <div class="right_top">
                <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                <span class="c_blue fl ml10">商品修改审核(组长)</span>
            </div>

            <div class="mt5">
                <div class="bd_t_dedede">
                    <ul class="menu_right supplier_menu">
                        <li><a href="/mall/groupGoodsUpdate" style="cursor: pointer">待我审核（{{needMyCount}}）</a></li>
                        <li class="curr" style="cursor: pointer">全部 （{{myCount}}）</li>
                    </ul>
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
                        <el-table :data="tableData" class="table_main_style" ref="multipleTable" @selection-change="handleSelectionChange">
                            <el-table-column type="selection" width="30"></el-table-column>
                            <el-table-column prop="date" label="商品信息" width="240">
                                <template slot-scope="scope">
                                    <div class="tl">
                                        <img :src="scope.row.pictureurl" class="fl mt10 mb10" alt="" width="68px" height="68px">
                                        <div class="tl w140 fl p10">
                                            <div>{{scope.row.productname}}</div>
                                            <div class="lh10 c_999 f10">
                                                <div>货号：{{scope.row.goodno}} </div>
                                                <div>型规：{{scope.row.spec}}</div>
                                                <div>{{scope.row.matrlno}}</div>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="参考发货日" width="100">
                                <template slot-scope="scope">
                                    <span v-if="scope.row.referdeliverydate > 0">{{scope.row.referdeliverydate}}个工作日</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" width="100">
                                <template slot="header">
                                    <div class="tc">
                                        <div> 商城价格</div>
                                        <div>长协价格</div>
                                    </div>
                                </template>
                                <template slot-scope="scope">
                                    <div class="tc">
                                        <div class="c_red">￥{{scope.row.price|toFixed(2)}}/{{scope.row.qtyunit}}/{{scope.row.tax}}%</div>
                                        <div class="f10 c_999">￥{{scope.row.originprice|toFixed(2)}}/{{scope.row.qtyunit}}/{{scope.row.tax}}%</div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="合同有效期" width="100">
                                <template slot-scope="scope">
                                    {{scope.row.popricedate | strDateFormat }}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="创建时间">
                                <template slot-scope="scope">
                                    {{scope.row.createdate | strDateFormat }}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="更新时间">
                                <template slot-scope="scope">
                                    {{scope.row.updatedate | strDateFormat}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="供应商">
                                <template slot-scope="scope">
                                    {{scope.row.suppliername}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="状态">
                                <template slot-scope="scope">
                                    {{scope.row.statusName}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="操作">
                                <template slot-scope="scope">
                                    <div>
                                        <button class="blue_txt_btn" @click="toPreview(scope.row.goodid)">预览</button>
                                        <button class="blue_txt_btn" @click="lookApplyReason(scope.row.applyreason)">查看申请理由</button>
                                        <button v-if="scope.row.status=='ZZSHTG' || scope.row.status=='ZYSHTG'" class="blue_txt_btn" @click="lookApplyReason1(scope.row.rejectreason,scope.row.audituser,scope.row.auditusername)">查看审批意见</button>
                                    </div>
                                    <div v-if="scope.row.status == 'ZZSHBH' || scope.row.status=='YBH'"><button class="blue_txt_btn" @click="openReDialog(scope.row.rejectreason,scope.row.audituser,scope.row.auditusername)">驳回原因</button></div>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!-- 分页 -->
                        <div class="page">
                            <div class="fl">
                                已选 <span class="c_blue">{{multipleSelection.length}}</span> 条
                                <span class="c_blue ml10" @click="exportGroupExcel" style="cursor: pointer">导出明细</span>
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
                        <div class="tr clear pb20 lh25">
                            <button class="main_btn mr10" @click="openAuditDialog">审核</button>
                        </div>
                    </div>
                </div>
            </div>

            <el-dialog title="商品信息审核" :visible.sync="showAuditDialog" width="340px" :close-on-click-modal="false" center>
                <div class="">
                    <div class="of lh35">
                        <span class="w80">审批结论：</span>
                        <span>
                            <el-radio  v-model="rejectOrAgree" label="Y">同意</el-radio>
                            <el-radio  v-model="rejectOrAgree" label="N">驳回</el-radio>
                        </span>
                    </div>

                    <div class="of mt10">
                        <span class="w80 fl">审批意见：</span>
                        <div class="fl">
                            <div class="w200">
                                    <textarea
                                            type="textarea"
                                            autosize
                                            style="min-height: 60px;"
                                            v-model="rejectReason">
                                    </textarea>
                            </div>
                        </div>
                    </div>
                </div>

                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="audit">确 认</el-button>
                    <el-button type="primary" @click="closeAuditDialog">关 闭</el-button>
                </span>
            </el-dialog>
            <el-dialog title="查看驳回原因" :visible.sync="showReDialog" width="340px" :close-on-click-modal="false" center>
                <div class="">
                    <div class="fl">
                        <div>{{auditUser}} {{updateuser}} 的审批意见</div>
                    </div>
                    <div class="mt10 w280">
                        <textarea type="textarea"  autosize
                            style="min-height: 60px;"
                            v-model="rejectReason"
                            disabled 
                        ></textarea>
                    </div>
                </div>

                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="showReDialog=false">关 闭</el-button>
                </span>
            </el-dialog>

            <#include "dialog/updateApplyReasonDialog.ftl" />
            <#include "dialog/ApprovalOpinions.ftl" />
        </div>


    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/groupMyGoodsUpdate.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>