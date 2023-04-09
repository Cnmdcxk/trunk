<@override name="title">商城运营管理-图片物料维护</@override>

<@override name="css"></@override>

<@override name="content">
    <div class="w1200">
        <!-- 左侧菜单 -->
        <#include "/left-nav.ftl">

        <!-- 右侧内容 -->
        <div class="right_con" id="RZ" class="" v-cloak>
            <div class="right_top">
                <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                <span class="c_blue fl ml10">商城运营管理</span>
            </div>
            <div class="mt5">
                <div class="bd_t_dedede">
                    <ul class="menu_right supplier_menu">
                        <#--<li><a href="/mall/basicData">ERP物料分类维护</a></li>-->
                        <li><a href="/mall/basicData1">物料号sku关系维护</a></li>
                        <li class="curr">物料图片维护</li>
                        <li><a href="/mall/basicData4">物料基础信息</a></li>
                        <li><a href="/mall/advertising">商城页面管理</a></li>
                    </ul>
                    <div class="p10">
                        <!-- 筛选 -->
                        <frame-filter
                                :do-search="search"
                                :conf="filtersConfig"
                        >
                        </frame-filter>

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
                                @sort-change="sortChange"
                                class="table_main_style"
                                :data="dataList"
                                style="width: 100%"
                                ref="multipleTable"
                                @selection-change="handleSelectionChange"
                        >
                            <el-table-column type="selection" width="30"></el-table-column>
                            <el-table-column prop="matrlno" label="物料条码" >

                                <template slot-scope="scope">
                                    <div class="tl">
                                        <img :src="scope.row.pictureurl" class="fl mt10 mb10" alt="" width="68px" @click="toPreview(scope.row.pictureurl)" style="cursor: pointer" height="68px">
                                        <div class="tl w140 fl p10">
                                            <div> {{scope.row.matrltm}}</div>
                                        </div>
                                    </div>
                                </template>

                            </el-table-column>

                            <el-table-column prop="picturenum" label="图片序号">
                                <template slot-scope="scope">
                                    {{scope.row.picturenum}}
                                </template>
                            </el-table-column>

                            <el-table-column  prop="createdate||createtime" label="创建时间" sortable="custom">
                                <template slot-scope="scope">
                                    {{scope.row.createdate|strDateFormat}} {{scope.row.createtime|strTimeFormat}}
                                </template>
                            </el-table-column>

                            <el-table-column prop="createuser" label="创建人">
                                <template slot-scope="scope">
                                    {{scope.row.createuser}}
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

                        <div class="tr clear pb20 lh25 fr">
                            <el-upload
                                    ref="upload"
                                    class="upload-demo fl disib"
                                    action="/api/v2/fileupload/doUpload/"
                                    multiple
                                    :show-file-list="false"
                                    :limit="100"
                                    :before-upload="beforeUpload"
                                    :on-success="successUpload"
                                    :on-error="errorUpload"
                                    accept=".png,.jpg,.jpeg"
                            >
                            <el-button size="small" type="primary" style="margin-right: 20px" @click="clear">上传</el-button>
                            </el-upload>
                            <el-button size="small" @click="delPic" >删除</el-button>
                        </div>

                    </div>
                    <el-dialog
                            title="上传结果"
                            :visible.sync="showErrorDialog"
                            :close-on-click-modal="false"
                            :lock-scroll="false"
                            width="500px"
                            center>
                        <div class="mb10 pt10">
                            <div class="mb10">
                                <span>上传成功{{successCount}}条</span>
                                <span>上传失败{{errorCount}}条</span>
                            </div>
                            <div style="overflow-y: auto;height: 400px">
                                <div v-for="error in errorList"  class="mt10">
                                    <span style="color: black">{{error.pictureName}}:</span>
                                    <span style="color:red;">{{error.message}}</span>
                                </div>
                            </div>

                        </div>
                        <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="closeDialog">关 闭</el-button>
                    </span>

                    </el-dialog>
                </div>
            </div>

        </div>
    </div>
</@override>


<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/javascript" src="${cdn}/js/mall/basicData3.js"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>