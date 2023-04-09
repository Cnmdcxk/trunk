<@override name="title">商品挂牌管理</@override>

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
            <span class="c_blue fl ml10">商品挂牌管理</span>
        </div>
        <div class="mt5">
            <div class="bd_t_dedede">
                <#--<ul class="menu_right supplier_menu">-->
                    <#--<li v-for="t in tabList" v-bind:class="{'curr': tab == t.key }" @click="selectTab(t.key)" style="cursor: pointer">{{t.value}}（{{t.count}}）</li>-->
                <#--</ul>-->

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
                            </el-tooltip>
                            条
                        </div>
                    </div>

                    <!-- 表格 -->
                    <el-table
                            :data="tableData"
                            class="table_main_style"
                            ref="multipleTable"
                            @selection-change="selectionChange">

                    >
                            <el-table-column type="selection" width="30"></el-table-column>

                        <el-table-column prop="date" label="商品信息" width="240">
                            <template slot-scope="scope">
                                <div class="tl">
                                    <img :src="scope.row.pictureurl?scope.row.pictureurl:'/statics/img/shop/noPic.png'" class="fl mt10 mb10" alt="" width="68px" height="68px">
                                    <div class="tl w140 fl p10">
                                        <div>{{scope.row.productname}}</div>
                                        <div class="lh10 c_999 f10">
                                            <div>货号：{{scope.row.goodno}}</div>
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
                                <div>{{scope.row.pono}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="物资采购人" width="100">
                            <template slot-scope="scope">
                                <div>{{scope.row.agent}}</div>
                            </template>
                        </el-table-column>
                        <el-table-column label="交货周期" width="100">
                            <template slot-scope="scope">
                                <span v-if="scope.row.leadtimenum > 0">{{scope.row.leadtimenum}}个工作日</span>
                            </template>
                        </el-table-column>
                        <el-table-column prop="date" label="价格" width="100">
                            <template slot-scope="scope">
                                <div class="tc">
                                    <div>￥{{scope.row.price}}/<span>{{scope.row.qtyunit}}</span></div>
                                    <div class="f10 c_999">
                                        <div>未税：￥{{scope.row.notaxprice}}/<span>{{scope.row.qtyunit}}</span></div>
                                        <div>税率：{{scope.row.tax|formatTax}}%</div>
                                    </div>
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
                        <el-table-column prop="date" label="商品组号">
                            <template slot-scope="scope">
                                {{scope.row.groupno}}
                            </template>
                        </el-table-column>
                        <el-table-column prop="date" label="状态">
                            <template slot-scope="scope">
                                <span>{{scope.row.statusName}}</span>
                            </template>
                        </el-table-column>

                        <el-table-column prop="date" label="操作" fixed="right">
                            <template slot-scope="scope">

                                <div>
                                    <button class="blue_txt_btn" @click="toPreview(scope.row.goodid)">预览</button>
                                </div>

                                <div v-if="scope.row.status =='10'">
                                    <button class="blue_txt_btn" @click="toUpdate(scope.row.goodid)">修改</button>
                                    <button class="blue_txt_btn" @click="signConfirmApproval(scope.row.goodid)">上架申请</button>
                                </div>

                                <div v-if="scope.row.status =='15'">
                                    <button class="blue_txt_btn" @click="toUpdate(scope.row.goodid)">修改</button>
                                    <button class="blue_txt_btn" @click="signConfirmApproval(scope.row.goodid)">上架申请</button>
                                </div>

                                <div v-if="scope.row.status =='25'">
                                    <button class="blue_txt_btn" @click="openSignDialogUpdateApply(scope.row.goodid)">下架申请</button>
                                </div>

                                <div v-if="scope.row.status =='30'">
                                    <button class="blue_txt_btn" @click="toUpdate(scope.row.goodid)">修改</button>
                                    <button class="blue_txt_btn" @click="signConfirmApproval(scope.row.goodid)">上架申请</button>
                                    <button class="blue_txt_btn" @click="openAuditDialog(scope.row)">驳回原因</button>
                                </div>

                                <div v-if="scope.row.status =='40'">
                                    <button class="blue_txt_btn" @click="openSignDialogUpdateApply(scope.row.goodid)">下架申请</button>
                                    <button class="blue_txt_btn" @click="openAuditDialog(scope.row)">驳回原因</button>
                                </div>

                            </template>
                        </el-table-column>
                    </el-table>

                    <!-- 分页 -->
                    <div class="page">
                        <div class="fl">
                            已选 <span class="c_blue">{{selectedItem.length}}</span> 条
                            <span class="c_blue ml10 cur" @click="exportExcel" style="cursor: pointer">导出明细</span>
                            <span class="c_blue ml10 cur" @click="openUpdatePriceDialog">批量调价</span>
                            <span class="c_blue ml10 cur" @click="openImportDialog">导入商品信息</span>
                            <span class="c_blue ml10 cur" @click="openMatchPicDialog">匹配商品图片</span>
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

                    <div class="tr clear">
                        <button class="main_btn ml10" @click="toBatchConfirmApproval">上架申请</button>
                        <button class="main_fff_btn ml10" @click="openBatchDialogUpdateApply">下架申请</button>
                        <button class="main_fff_btn ml10" @click="createGroup">创建商品组</button>
                    </div>


                    <el-dialog title="批量调价" :visible.sync="showUpdatePriceDialog" width="360px" center :close-on-click-modal="false">
                        <div class="of">
                            <div>按统一服务调价</div>
                            <div class="of mt10 lh25">
                                <span class="fl c_999">请输入加减价：</span>

                                <el-select v-model="addOrSub" class="w70 fl">
                                    <el-option label="+" value="1"></el-option>
                                    <el-option label="-" value="2"></el-option>
                                </el-select>

                                <el-input v-model="updatePriceRange" class="w100 fl ml10" @keyup="checkInput"></el-input>
                                <span class="fl ml5">元</span>

                            </div>
                            <div class="c_999 f10 lh25">(例：输入-10元，所选择的商品，将统一减价10元)</div>
                        </div>

                        <span slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="batchUpdatePrice">保 存</el-button>
                            <el-button @click="closeUpdatePriceDialog">取 消</el-button>
                        </span>

                    </el-dialog>

                    <el-dialog
                            title="导入商品信息"
                            :visible.sync="showImportDialog"
                            width="380px"
                            :close-on-click-modal="false"
                            center>
                        <div class="pt40">
                            <div>
                                <i class="iconfont c_orange icon-tishi"></i>您可先下载excel模板，填写信息后上传
                                <a class="c_blue" @click="downloadExportTemp">下载模板</a>
                            </div>
                            <div class="tc mt20 pb25">
                                上传文件：
                                <el-upload
                                        class="upload-demo w100 disib"
                                        style="display:inline;"
                                        ref="upload"
                                        :auto-upload="false"
                                        multiple=false
                                        :limit="1"
                                        accept=".xls,.xlsx"
                                        action="/api/v2/fileupload/doUpload/"
                                        :before-upload="beforeUpload"
                                        :on-success="handleSuccess"
                                        :on-error="handleError"
                                >
                                    <button class="main_fff_self_btn"> <i class="iconfont icon-shangchuan1 f16"></i> 选择文件</button>
                                </el-upload>
                            </div>
                        </div>

                        <span slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="upload" >确 定</el-button>
                            <el-button @click="closeImportDialog">取 消</el-button>
                        </span>
                    </el-dialog>

                    <el-dialog title="匹配商品图片" :visible.sync="showMatchPicDialog" width="360px" center :close-on-click-modal="false">

                        <div class="">
                            <div>请选择匹配方式：</div>
                            <div class="mt10 lh25">
                                <div class="mb20">
                                    <el-radio v-model="isMatch" label="0">不覆盖商品原有图片</el-radio>
                                    <div class="c_999 ml20">此次匹配到的商品图片会补充到商品图片上</div>
                                </div>
                                <div class="mb20">
                                    <el-radio v-model="isMatch" label="1">覆盖商品原有图片</el-radio>
                                    <div class="c_red  ml20">请谨慎选择</div>
                                </div>
                            </div>
                        </div>

                        <span slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="batchMatchPic">保 存</el-button>
                            <el-button @click="closeMatchPicDialog">取 消</el-button>
                        </span>

                    </el-dialog>


                    <el-dialog title="商品信息审核" :visible.sync="showAuditDialog" width="340px" :close-on-click-modal="false" center>
                        <div class="">

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


                    <el-dialog title="下架申请" :visible.sync="showDialogUpdateApply" width="370px" center :close-on-click-modal="false">
                        <div class="w_90 margin">
                            <div class="tc mtb20 tan_head_text">
                                <i class="iconfont icon-wenhao f26 c_blue vm"></i>
                                <span class="fb">已选<span class="c_red">{{applyGoodList.length}}</span>条，是否确认下架申请</span>
                            </div>

                            <el-form
                                    label-position="center"
                                    ref="addForm"
                                    class="my_form mt10"
                                    label-width="100px"
                                    >
                                <el-form-item label="申请理由：">
                                    <textarea rows="3" class="pt10 pl10" placeholder="请输入" v-model="applyReason"></textarea>
                                </el-form-item>
                            </el-form>
                        </div>
                        <span slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="batchUpdateApply">确 认</el-button>
                            <el-button @click="closeDialogUpdateApply">取 消</el-button>
                        </span>
                    </el-dialog>


                </div>
            </div>
        </div>

    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/goodsListed.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>