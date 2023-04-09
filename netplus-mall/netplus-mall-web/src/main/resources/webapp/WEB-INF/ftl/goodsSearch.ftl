<@override name="title">商城挂牌查询</@override>

<@override name="css"></@override>


<@override name="content">


            <div class="w1200">
                <!-- 头部菜单 2-->
                <#include "/left-nav.ftl">

                <!-- 右侧内容 -->
                <div class="right_con" id="RZ" v-cloak>
                    <div class="right_top">
                        <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                        <span class="c_blue fl ml10">商品挂牌查询</span>
                    </div>
                    <div class="mt5">
                        <div class="bd_t_dedede">

                            <div class="p10">
                                <!-- 筛选 -->
                                <frame-filter
                                        :do-search="search"
                                        :data="filters"
                                        :conf="filtersConfig">
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
                                <el-table :data="tableData" class="table_main_style" ref="multipleTable">
                                    <el-table-column prop="date" label="商品信息" width="240">
                                        <template slot-scope="scope">
                                            <div class="tl ml10">
                                                <img :src="scope.row.pictureurl?scope.row.pictureurl:'/statics/img/shop/noPic.png'" class="fl mt10 mb10" alt="" width="68px" height="68px">
                                                <div class="tl w140 fl p10">
                                                    <div>{{scope.row.productname}}{{scope.row.brand != null && scope.row.brand != '' ?'/'+scope.row.brand:''}}</div>
                                                    <div class="lh10 c_999 f10">
                                                        <div v-if="scope.row.goodno != null">货号：{{scope.row.goodno}}</div>
                                                        <div>型规：{{scope.row.spec}}</div>
                                                        <div>物料编码：{{scope.row.matrlno}}</div>
                                                        <div>物料条码：{{scope.row.matrltm}}</div>
                                                    </div>
                                                </div>
                                            </div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="协议号" width="100">
                                        <template slot-scope="scope">
                                            <span>{{scope.row.pono}}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="物资采购人" width="100">
                                        <template slot-scope="scope">
                                            <span>{{scope.row.agent}}</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column label="交货周期" width="100">
                                        <template slot-scope="scope">
                                            <span v-if="scope.row.leadtimenum > 0">{{scope.row.leadtimenum}}个工作日</span>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="date" width="100">
                                        <template slot="header">
                                            商城价格
                                            <br />协议价格
                                        </template>
                                        <template slot-scope="scope">
                                            <div class="tc">
                                                <div><i class="c_red">￥{{scope.row.price}}/{{scope.row.qtyunit}}/{{scope.row.tax | formatTax}}%</i></div>
                                                <div>￥{{scope.row.originprice}}/{{scope.row.qtyunit}}/{{scope.row.tax | formatTax}}%</div>
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
                                            <div>{{scope.row.createdate | strDateFormat }}</div>
                                            <div>{{scope.row.createtime | strTimeFormat }}</div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="date" label="更新时间">
                                        <template slot-scope="scope">
                                            <div>{{scope.row.updatedate | strDateFormat }}</div>
                                            <div>{{scope.row.updatetime | strTimeFormat }}</div>
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="date" label="供应商">
                                        <template slot-scope="scope">
                                            {{scope.row.supplierno}}-{{scope.row.suppliername}}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="date" label="状态" >
                                        <template slot-scope="scope">
                                            {{scope.row.statusName}}
                                        </template>
                                    </el-table-column>
                                    <el-table-column prop="date" label="操作" fixed="right" width="100">
                                        <template slot-scope="scope">
                                            <div>
                                                <button class="blue_txt_btn" @click="toPreview(scope.row.goodid)">预览</button>
                                            </div>
                                            <div v-if="scope.row.status =='30'
                                            || scope.row.status =='40'">
                                                <button class="blue_txt_btn" @click="openAuditDialog(scope.row)">驳回原因</button>
                                            </div>
                                        </template>
                                    </el-table-column>
                                </el-table>
                                <!-- 分页 -->
                                <div class="page">

                                    <div class="fl">
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

                                <el-dialog title="商品信息审核" :visible.sync="showAuditDialog" width="340px" :close-on-click-modal="false" center>
                                    <div class="">

                                        <div class="of lh35">
                                            <span class="w80">审批人：</span>
                                            <span>
                                                {{auditusername}}
                                            </span>
                                        </div>

                                        <div class="of lh35">
                                            <span class="w80">审批结论：</span>
                                            <span>
                                                <el-radio disabled v-model="rejectOrAgree" label="Y">同意</el-radio>
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
                                                            placeholder="请输入"
                                                            style="min-height: 60px;"
                                                            v-model="rejectReason" disabled>
                                                    </textarea>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <span slot="footer" class="dialog-footer">
                                        <el-button type="primary" @click="closeAuditDialog">关 闭</el-button>
                                    </span>
                                </el-dialog>

                            </div>
                        </div>
                    </div>
                </div>
            </div>



</@override>

<@override name="js">
    <script src="${cdn}/js/framework/components/filters.js"></script>
    <script type="text/babel" src="${cdn}/js/mall/goodsSearch.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>