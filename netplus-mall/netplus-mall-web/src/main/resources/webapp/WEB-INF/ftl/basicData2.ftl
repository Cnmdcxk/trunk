<@override name="title">商城页面管理</@override>

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
                    <li class="curr"><a href="/mall/basicData1">物料号sku关系维护</a></li>
                    <li><a href="/mall/basicData3">物料图片维护</a></li>
                    <li><a href="/mall/advertising">商城页面管理</a></li>
                </ul>
                <div class="p10">
                    <!-- 筛选 -->
                    <frame-filter
                            :do-search="search"
                            :data="filters"
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
                            class="table_main_style"
                            :data="dataList"
                            style="width: 100%"
                            ref="multipleTable">
                        <el-table-column
                                prop="matrlno"
                                label="商城ERP物料号"
                        >
                            <template slot-scope="scope">
                                {{scope.row.matrlno}}
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="productinfo"
                                label="商品信息"
                               >
                            <template slot-scope="scope">
                                {{scope.row.productname}}/{{scope.row.spec}}/{{scope.row.quality}}
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="unit"
                                label="计量单位"
                                >
                            <template slot-scope="scope">
                                {{scope.row.unit}}
                            </template>
                        </el-table-column>
                        <el-table-column
                                prop="twolevelclaname"
                                label="二级分类"
                                >
                            <template slot-scope="scope">
                                {{scope.row.twolevelclaname}}
                            </template>
                        </el-table-column>

                        <el-table-column
                                prop=""
                                label="操作">
                            <template slot-scope="scope">
                                <button class="blue_txt_btn" @click="openUpdateDialog(scope.row)">修改</button>
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

                    <div class="tr clear">
                        <button class="main_fff_btn" @click="openImportDialog">批量导入</button>
                    </div>

                    <!-- 修改弹框 -->
                    <el-dialog title="修改" :visible.sync="showUpdateDialog" width="430px" center :close-on-click-modal="false">
                        <div class="">
                            <el-form ref="updateForm" class="my_form" label-width="130px" :model="updateForm" :rules="validators.rules">

                                <el-form-item label="物料号" prop="matrlno">
                                    <el-input v-model="updateForm.matrlno" disabled></el-input>
                                </el-form-item>
                                <el-form-item label="商品名称" prop = "productname">
                                    <el-input v-model="updateForm.productname"></el-input>
                                </el-form-item>

                                <el-form-item label="型规" prop = "spec">
                                    <el-input v-model="updateForm.spec"></el-input>
                                </el-form-item>

                                <el-form-item label="计量单位" prop="unit">
                                    <el-input v-model="updateForm.unit"></el-input>
                                </el-form-item>

                                <el-form-item label="大类" prop="categoryname">
                                    <el-select v-model="updateForm.categoryname" placeholder="请选择" @change="changeCategory">
                                        <el-option
                                                v-for="item in categorynameList"
                                                :key="item"
                                                :label="item"
                                                :value="item">
                                        </el-option>
                                    </el-select>
                                </el-form-item>

                                <el-form-item label="一级分类" prop="onelevelclaname">
                                    <el-select v-model="updateForm.onelevelclaname" placeholder="请选择" @change="changeOnwLevelCla">
                                        <el-option
                                                v-for="item in onelevelclanameList"
                                                :key="item"
                                                :label="item"
                                                :value="item">
                                        </el-option>
                                    </el-select>
                                </el-form-item>

                                <el-form-item label="二级分类" prop="twolevelclaname">
                                    <el-select v-model="updateForm.twolevelclaname" placeholder="请选择">
                                        <el-option
                                                v-for="item in twolevelclanameList"
                                                :key="item"
                                                :label="item"
                                                :value="item">
                                        </el-option>
                                    </el-select>
                                </el-form-item>
                            </el-form>
                        </div>

                        <span slot="footer" class="dialog-footer">
                           <el-button type="primary" @click="save">保 存</el-button>
                           <el-button @click="closeUpdateDialog">取 消</el-button>
                        </span>
                    </el-dialog>
                </div>
            </div>
        </div>

        <el-dialog
                title="物料二级分类导入"
                :visible.sync="showImportDialog"
                width="380px"
                :close-on-click-modal="false"
                center>
            <div class="pt40">

                <div>
                    <i class="iconfont c_orange icon-tishi"></i>您可先下载excel模板，填写信息后上传
                    <a class="c_blue" href="${cdn}/excelTemp/物料二级分类导入模版.xlsx">下载模板</a>
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
     <script type="text/javascript" src="${cdn}/js/mall/basicData2.js"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>