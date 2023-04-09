<@override name="title">商城运营管理-物料条码sku关系维护</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
    <style type="text/css">
        [v-cloak]{
            display:none;
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
                <span class="c_blue fl ml10">商城运营管理</span>
            </div>
            <div class="mt5">
                <div class="bd_t_dedede">
                    <ul class="menu_right supplier_menu">
                        <#--<li><a href="/mall/basicData">ERP物料分类维护</a></li>-->
                        <li class="curr">物料条码sku关系维护</li>
                        <li><a href="/mall/basicData3">物料图片维护</a></li>
                        <li><a href="/mall/basicData4">物料基础信息</a></li>
                        <li><a href="/mall/advertising">商城页面管理</a></li>
                    </ul>
                    <div class="p10">
                        <!-- 筛选 -->
                        <frame-filter
                                :do-search="search"
                                :data="filters"
                                :conf="filtersConfig"
                                ref="filters">
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
                                class="table_main_style"
                                :data="dataList"
                                style="width: 100%"
                                ref="multipleTable">
                            <el-table-column
                                    prop="matrltm"
                                    label="内部物料条码"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.matrltm}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="productinfo"
                                    label="内部商品信息"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.productname}}/{{scope.row.spec}}/{{scope.row.quality}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="unit"
                                    label="内部计量单位"
                                    width="80">
                                <template slot-scope="scope">
                                    {{scope.row.unit}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="twolevelclaname"
                                    label="物料分类"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.categoryname}}/{{scope.row.onelevelclaname}}/{{scope.row.twolevelclaname}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="suppliername"
                                    label="供应商"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.suppliername}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="thrplatgoodno"
                                    label="第三方平台货号"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.thrplatgoodno}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="thrplatproductinfo"
                                    label="外部商品信息"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.thrplatproductname}}/{{scope.row.thrplatspec}}/{{scope.row.thrplatquality}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="thrplatunit"
                                    label="外部计量单位"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.thrplatunit}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="isactive"
                                    label="状态"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.isactive=="Y"&&"启用"||scope.row.isactive=="N"&&"禁用"}}
                                </template>
                            </el-table-column>

                            <el-table-column
                                    prop="updatedate"
                                    label="更新时间"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.updatedate|strDateFormat}} {{scope.row.updatetime|strTimeFormat}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop=""
                                    label="操作"
                                    fixed="right"
                            >
                                <template slot-scope="scope">
                                    <button class="blue_txt_btn" @click="modifyDataDialog(scope.row)">修改</button>
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
                        <div class="tr clear">
                            <button class="main_fff_btn" @click="openImportDialog">批量导入</button>
                        </div>

                        <!-- 修改弹框 -->
                        <el-dialog title="修改" :visible.sync="modifyVisible" width="430px" center :close-on-click-modal="false">
                            <div class="">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="updateForm" :rules="validators.rules">
                                    <el-form-item label="物料条码：">
                                        {{updateForm.matrltm}}
                                        <el-input v-model="updateForm.matrltm" placeholder="请输入" v-show="show1"></el-input>
                                    </el-form-item>
                                    <el-form-item label="物料描述：">
                                        {{updateForm.matrldesc}}
                                    </el-form-item>
                                    <el-form-item label="商品信息：">
                                        {{updateForm.productname}}/{{updateForm.spec}}/{{updateForm.quality}}
                                    </el-form-item>
                                    <el-form-item label="计量单位：">
                                        {{updateForm.unit}}
                                    </el-form-item>
                                    <el-form-item label="供应商：">
                                        {{updateForm.suppliername}}
                                        <el-input v-model="updateForm.suppliercode" placeholder="请输入" v-show="show1"></el-input>
                                    </el-form-item>
                                    <el-form-item label="二级分类" prop="twolevelclaname">
                                        <el-input v-model="updateForm.twolevelclaname" placeholder="请输入"></el-input>
                                        <el-input v-model="updateForm.hidtwolevelclaname" placeholder="请输入" v-show="show1"></el-input>
                                    </el-form-item>
                                    <el-form-item label="第三方平台货号" prop="thrplatgoodno">
                                        <el-input v-model="updateForm.thrplatgoodno" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="外部商品信息：">
                                        {{updateForm.thrplatproductname}}/{{updateForm.thrplatspec}}/{{updateForm.thrplatquality}}
                                    </el-form-item>
                                    <el-form-item label="外部计量单位：">
                                        {{updateForm.thrplatunit}}
                                    </el-form-item>
                                    <el-form-item label="状态：">
                                        <el-checkbox-group v-model="updateForm.isactive" >
                                            <el-checkbox label="可用"></el-checkbox>
                                        </el-checkbox-group>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="modifyClassifyData">保 存</el-button>
                                <el-button @click="modifyVisible = false">取 消</el-button>
                            </span>
                        </el-dialog>

                    </div>
                </div>
            </div>

            <el-dialog
                    title="物料号sku关系导入"
                    :visible.sync="showImportDialog"
                    width="380px"
                    :close-on-click-modal="false"
                    center>
                <div class="pt40">

                    <div>
                        <i class="iconfont c_orange icon-tishi"></i>您可先下载excel模板，填写信息后上传
                        <a class="c_blue" href="${cdn}/excelTemp/物料号sku关系维护导入模板.xlsx">下载模板</a>
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
        </div>
    </div>
</@override>

<@override name="js">
<script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
<script type="text/babel" src="${cdn}/js/mall/basicData1.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>