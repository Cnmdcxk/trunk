<@override name="title">商品下架确认</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
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
                <span class="c_blue fl ml10">商品下架确认</span>
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
                        <el-table :data="tableData" class="table_main_style" ref="multipleTable" @selection-change="handleSelectionChange">
                            <el-table-column type="selection" width="30"></el-table-column>
                            <el-table-column prop="date" label="商品信息" width="240">
                                <template slot-scope="scope">
                                    <div class="tl">
                                        <img :src="scope.row.pictureurl?scope.row.pictureurl:'/statics/img/shop/noPic.png'" class="fl mt10 mb10" alt="" width="68px" height="68px">
                                        <div class="tl w140 fl p10">
                                            <div>{{scope.row.productname}}</div>
                                            <div class="lh10 c_999 f10">
                                                <div>货号：{{scope.row.goodno}} </div>
                                                <div>型规：{{scope.row.spec}}</div>
                                                <div>物料条码:{{scope.row.matrltm}}</div>
                                                <div>物料编码:{{scope.row.matrlno}}</div>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </el-table-column>

                            <el-table-column label="交货周期" width="100">
                                <template slot-scope="scope">
                                    <span>{{scope.row.leadtimenum}}个工作日</span>
                                </template>
                            </el-table-column>

                            <el-table-column label="协议号" width="100">
                                <template slot-scope="scope">
                                    <span>{{scope.row.pono}}</span>
                                </template>
                            </el-table-column>

                            <el-table-column prop="date" width="100">
                                <template slot="header">
                                    <div class="tc">
                                        <div> 商城价格</div>
                                        <div>协议价格</div>
                                    </div>
                                </template>
                                <template slot-scope="scope">
                                    <div class="tc">
                                        <div class="c_red">￥{{scope.row.price|toFixed(2)}}/{{scope.row.qtyunit}}/{{scope.row.tax|formatTax}}%</div>
                                        <div class="f10 c_999">￥{{scope.row.originprice|toFixed(2)}}/{{scope.row.qtyunit}}/{{scope.row.tax|formatTax}}%</div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="合同有效期" width="100">
                                <template slot-scope="scope">
                                    <div>{{scope.row.popricestartdate | strDateFormat }}</div>
                                    <div>{{scope.row.popricedate | strDateFormat }}</div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="创建时间">
                                <template slot-scope="scope">
                                    {{scope.row.createdate | strDateFormat }} {{scope.row.createtime | strTimeFormat}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="更新时间">
                                <template slot-scope="scope">
                                    {{scope.row.updatedate|strDateFormat}} {{scope.row.updatetime|strTimeFormat}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="供应商">
                                <template slot-scope="scope">
                                    {{scope.row.supplierno}} {{scope.row.suppliername}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="状态">
                                <template slot-scope="scope">
                                    {{scope.row.statusName}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="操作" fixed="right">
                                <template slot-scope="scope">
                                    <div>
                                        <button class="blue_txt_btn" @click="toPreview(scope.row.goodid)">预览</button>
                                    </div>
                                    <div>
                                        <button class="blue_txt_btn" @click="openReDialog(scope.row.applyreason,scope.row.supplierno,scope.row.suppliername)">查看申请理由</button>
                                    </div>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!-- 分页 -->
                        <div class="page">
                            <div class="fl">
                                已选 <span class="c_blue">{{multipleSelection.length}}</span> 条
                                <span class="c_blue ml10" @click="exportGoodsAudit" style="cursor: pointer">导出明细</span>
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
                            <button class="main_btn mr10" @click="openAuditDialog">下架确认</button>
                        </div>
                    </div>
                </div>
            </div>
            <el-dialog title="查看申请理由" :visible.sync="showReDialog" width="340px" :close-on-click-modal="false" center>
                <div class="">
                    <div class="of mt20">
                        <span class="w80 tr fl">申请人：</span>
                        <span style="width: calc(100% - 80px);">{{auditUser}} {{updateuser}}</span>
                    </div>
                    <div class="of mt20">
                        <span class="w80 tr fl">申请理由：</span>
                        <textarea type="textarea" autosize
                            style="width: calc(100% - 80px);min-height: 60px;"
                            v-model="applyReason"
                            disabled
                        > </textarea>
                    </div>
                </div>

                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="showReDialog=false">关 闭</el-button>
                </span>
            </el-dialog>
            <el-dialog title="下架确认" :visible.sync="showAuditDialog" width="340px" :close-on-click-modal="false" center>
                <div class="">
                    <div class="lh35">已选 <span class="c_red">{{multipleSelection.length}}</span>条商品</div>

                    <div class="of lh35">
                        <span class="w80">审批结论：</span>
                        <span>
                            <el-radio v-model="rejectOrAgree" label="Y">同意</el-radio>
                            <el-radio v-model="rejectOrAgree" label="N">驳回</el-radio>
                        </span>
                    </div>

                    <div class="of mt10">
                        <span class="w80 fl">审批意见：</span>
                        <div class="fl">
                            <div class="w200">
                                    <textarea
                                            type="textarea"
                                            autosize
                                            placeholder="请输入"
                                            style="min-height: 60pxa;"
                                            v-model="rejectReason">
                                    </textarea>
                            </div>
                        </div>
                    </div>
                </div>

                <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="audit">确 认</el-button>
                    <el-button @click="closeAuditDialog">取 消</el-button>
                </span>
            </el-dialog>

        </div>
    </div>
</@override>

<@override name="js">
    <script>
        var status = '${status!}';
    </script>
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/goodsUpdate.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>