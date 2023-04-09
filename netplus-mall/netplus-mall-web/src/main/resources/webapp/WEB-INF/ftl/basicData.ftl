<@override name="title">商城运营管理-ERP物料分类维护</@override>

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
                        <li class="curr">ERP物料分类维护</li>
                        <li><a href="/mall/basicData1">第三方平台商品关系维护</a></li>
                        <li><a href="/mall/basicData3">物料图片维护</a></li>
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
                                    prop="twolevelclaname"
                                    label="二级分类"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.twolevelclaname}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="onelevelclaname"
                                    label="一级分类"
                                    width="130">
                                <template slot-scope="scope">
                                    {{scope.row.onelevelclaname}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="categoryname"
                                    label="大类"
                                    width="120">
                                <template slot-scope="scope">
                                    {{scope.row.categoryname}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="erpclaname"
                                    label="ERP物料分类"
                                    width="130">
                                <template slot-scope="scope">
                                    {{scope.row.erpclaname}}
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="updatedate"
                                    label="更新时间">
                                <template slot-scope="scope">
                                    {{scope.row.updatedate|strDateFormat}} {{scope.row.updatetime|strTimeFormat}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column
                                    prop="" 
                                    label="操作">
                                <template slot-scope="scope">
                                    <button class="blue_txt_btn" @click="modifyDataDialog(scope.row)">修改</button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!-- 分页 -->
                        <div class="page">
                            <div class="fl">
                                <span class="c_blue ml10" @click="exportBasicData">导出明细</span>
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
                            <button class="main_btn mr10" @click="addVisible = true">新增</button>
                            <button class="main_fff_btn" @click="openImportDialog">批量导入</button>
                        </div>

                        <!-- 新增弹框 -->
                        <el-dialog title="新增" :visible.sync="addVisible" width="430px" center :close-on-click-modal="false">
                            <div class="">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="ruleForm" :rules="validators.rules">
                                    <el-form-item label="大类" prop="categoryname">
                                        <el-select v-model="ruleForm.categoryname" placeholder="请输入">
                                            <el-option
                                                    v-for="item in categoryNameList"
                                                    :key="item.key"
                                                    :label="item.value"
                                                    :value="item.key">
                                            </el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="一级分类" prop="onelevelclaname">
                                        <el-input v-model="ruleForm.onelevelclaname" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="二级分类" prop="twolevelclaname">
                                        <el-input v-model="ruleForm.twolevelclaname" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="ERP物料分类" prop="erpclaname">
                                        <el-input v-model="ruleForm.erpclaname" placeholder="请输入"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="addClassifyData('addForm')">保 存</el-button>
                                <el-button @click="addVisible = false">取 消</el-button>
                            </span>
                        </el-dialog>

                        <!-- 修改弹框 -->
                        <el-dialog title="修改" :visible.sync="modifyVisible" width="430px" center :close-on-click-modal="false">
                            <div class="">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="updateForm" :rules="validators.rules">
                                    <el-form-item label="大类" prop="categoryname">
                                        <el-select v-model="updateForm.categoryname" placeholder="请输入">
                                            <el-option
                                                    v-for="item in categoryNameList"
                                                    :key="item.key"
                                                    :label="item.value"
                                                    :value="item.key">
                                            </el-option>
                                        </el-select>
                                        <el-input v-model="updateForm.hidcategoryname" placeholder="请输入" v-show="show1"></el-input>
                                    </el-form-item>
                                    <el-form-item label="一级分类" prop="onelevelclaname">
                                        <el-input v-model="updateForm.onelevelclaname" placeholder="请输入"></el-input>
                                        <el-input v-model="updateForm.hidonelevelclaname" placeholder="请输入" v-show="show2"></el-input>
                                    </el-form-item>
                                    <el-form-item label="二级分类" prop="twolevelclaname">
                                        <el-input v-model="updateForm.twolevelclaname" placeholder="请输入"></el-input>
                                        <el-input v-model="updateForm.hidtwolevelclaname" placeholder="请输入" v-show="show3"></el-input>
                                    </el-form-item>
                                    <el-form-item label="ERP物料分类" prop="erpclaname">
                                        <el-input v-model="updateForm.erpclaname" placeholder="请输入"></el-input>
                                        <el-input v-model="updateForm.hiderpclaname" placeholder="请输入" v-show="show4"></el-input>
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
                    title="ERP物料分类映射导入"
                    :visible.sync="showImportDialog"
                    width="380px"
                    :close-on-click-modal="false"
                    center>
                <div class="pt40">

                    <div>
                        <i class="iconfont c_orange icon-tishi"></i>您可先下载excel模板，填写信息后上传
                        <a class="c_blue" href="${cdn}/excelTemp/ERP物料分类映射导入模板.xlsx">下载模板</a>
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
<script type="text/babel" src="${cdn}/js/mall/basicData.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>