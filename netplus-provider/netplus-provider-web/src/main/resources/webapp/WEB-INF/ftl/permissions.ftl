
<@override name="title">权限管理</@override>

<@override name="css">
<link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
</@override>
<@override name="content">
        <!-- 内容区域 -->
        <div class="bg_fff">
            <div class="w1200">
                <!-- 菜单信息 -->
                <#include "/left-nav.ftl">

                <!-- 右侧内容 -->
                <div id="RZ" class="" v-cloak>
                <div class="right_con">
                    <div class="right_top">
                        <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                        <span class="c_blue fl ml10">权限管理</span>
                    </div>
                    <div class="p20">

                        <frame-filter
                                page-title="权限信息"
                                :do-search="search"
                                :data="filters"
                                :conf="filtersConfig"
                                    ref="filters">
                        </frame-filter>
                        <!-- 统计结果 -->
                        <div class="result_style">
                            <span class="disib w90 tl c_999 fl">统计结果</span>
                            <div class="fl">
                                <el-tooltip effect="dark" placement="top">
                                    <div slot="content">
                                        <span class="                                                                                                                                                                                                                                                                       c_red f20 fb lh30">{{totalCount}}</span> 条
                                    </div>
                                    <span class="c_red f20 fb">{{totalCount}}</span>
                                </el-tooltip> 条
                            </div>
                        </div>
                        <!-- 表格 -->
                        <el-table :data="tableData" class="table_main_style">
                            <el-table-column  prop="name" label="权限名称" width="100"></el-table-column>
                            <el-table-column  prop="code" label="权限代码" width="100"></el-table-column>
                            <el-table-column  prop="privilegedesc" label="权限描述" width="100"></el-table-column>
                            <el-table-column  prop="privilegetype" label="权限类型" width="100"></el-table-column>
                            <el-table-column  prop="parentcode" label="父权限代码" width="100"></el-table-column>
                            <el-table-column  label="创建时间" width="120">
                                <template slot-scope="scope">
                                    {{scope.row.createdate|strDateFormat}} {{scope.row.createtime|strTimeFormat}}
                                </template>
                            </el-table-column>
                            <el-table-column  prop="sort" label="排序" width="60"></el-table-column>
                            <el-table-column  prop="isactive" label="是否激活" width="100" :formatter="formatActionqrcode1"></el-table-column>
                            <el-table-column  prop="belongto" label="权限归属" width="100"></el-table-column>
                            <el-table-column  prop="isdefault" label="是否默认" width="100" :formatter="formatActionqrcode2"></el-table-column>
                            <el-table-column  prop="icon" label="图标" width="80"></el-table-column>
                            <el-table-column  prop="pagevisible" label="是否显示" width="120" :formatter="formatActionqrcode3"></el-table-column>
                            <el-table-column  label="操作" fixed="right">
                                <template slot-scope="scope">
                                    <button class="blue_txt_btn" @click="updataForms(scope.row)">修改</button>
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
                        <div class="tr">
                            <button class="main_btn" @click="centerDialogVisible3 = true">新增</button>
                        </div>

                        <!-- 新增权限 -->
                        <el-dialog title="新增权限" :close-on-click-modal="false" :visible.sync="centerDialogVisible3" width="430px" center @close="handleDialogClose">
                            <div class="w_90 margin">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="ruleForm" :rules="rules">
                                    <el-form-item label="权限代码" prop="code">
                                        <el-input v-model="ruleForm.code" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="权限名称" prop="name">
                                        <el-input v-model="ruleForm.name" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="权限描述" prop="privilegedesc">
                                        <el-input v-model="ruleForm.privilegedesc" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="权限类型" prop="privilegetype">
                                        <el-select @change="onChange" v-model="ruleForm.privilegetype" placeholder="请选择">
                                            <el-option v-for="item in privilegetypeList" :key="item.key" :label="item.value" :value="item.key"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="父权限代码" prop="parentcode" >
                                        <el-select @change="onChange" v-model="ruleForm.parentcode" placeholder="请选择">
                                            <el-option v-for="item in privilegetypeData" :key="item.key" :label="item.value" :value="item.key"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="排序" prop="sort">
                                        <el-input v-model="ruleForm.sort" placeholder="请输入数字" onkeyup="value=value.replace(/[^\d]/g,'')"></el-input>
                                    </el-form-item>
                                    <el-form-item label="是否激活" prop="isactive">
                                        <el-select @change="onChange" v-model="ruleForm.isactive" placeholder="请选择">
                                            <el-option label="是" value="Y"></el-option>
                                            <el-option label="否" value="N"></el-option>
                                        </el-select>
                                    </el-form-item>

                                    <el-form-item label="图标" prop="icon">
                                        <el-input v-model="ruleForm.icon" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="是否显示" prop="pagevisible">
                                        <el-select @change="onChange" v-model="ruleForm.pagevisible" placeholder="请选择">
                                            <el-option label="是" value="Y"></el-option>
                                            <el-option label="否" value="N"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="addParms('addForm')">保 存</el-button>
                                <el-button @click="handleDialogClose('addForm')">取 消</el-button>
                            </span>
                        </el-dialog>

                        <!-- 修改权限 -->
                        <el-dialog title="修改权限" :close-on-click-modal="false" :visible.sync="centerDialogVisible" width="430px" center>
                            <div class="w_90 margin">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="updateForm" :rules="rules">
                                    <el-form-item label="权限代码" prop="code">
                                        <el-input v-model="updateForm.code" placeholder="请输入" readonly="true"></el-input>
                                    </el-form-item>
                                    <el-form-item label="权限名称" prop="name">
                                        <el-input v-model="updateForm.name" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="权限描述" prop="privilegedesc">
                                        <el-input v-model="updateForm.privilegedesc" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="权限类型" prop="privilegetype">
                                        <el-select @change="onChange" @change="onChange" v-model="updateForm.privilegetype" placeholder="请选择">
                                            <el-option v-for="item in privilegetypeList" :key="item.key" :label="item.value" :value="item.key"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item  label="父权限代码" prop="parentcode">
                                        <el-select @change="onChange" v-model="updateForm.parentcode" placeholder="请选择">
                                            <el-option v-for="item in parentCodeList" :key="item.key" :label="item.value" :value="item.key"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="排序" prop="sort">
                                        <el-input v-model="updateForm.sort" placeholder="请输入" onkeyup="value=value.replace(/[^\d]/g,'')"></el-input>
                                    </el-form-item>
                                    <el-form-item label="是否激活" prop="isactive">
                                        <el-select @change="formatActionqrcode1" v-model="updateForm.isactive" placeholder="请选择">
                                            <el-option label="是" value="Y"></el-option>
                                            <el-option label="否" value="N"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="权限归属" prop="belongto">
                                        <el-input v-model="updateForm.belongto" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="是否默认" prop="isdefault">
                                        <el-select @change="formatActionqrcode2" v-model="updateForm.isdefault" placeholder="请选择">
                                            <el-option label="是" value="Y"></el-option>
                                            <el-option label="否" value="N"></el-option>
                                        </el-select>
                                    </el-form-item>
                                    <el-form-item label="图标" prop="icon">
                                        <el-input v-model="updateForm.icon" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="是否显示" prop="pagevisible">
                                        <el-select @change="formatActionqrcode3" v-model="updateForm.pagevisible" placeholder="请选择">
                                            <el-option label="是" value="Y"></el-option>
                                            <el-option label="否" value="N"></el-option>
                                        </el-select>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="update('addForm')">保 存</el-button>
                                <el-button @click="centerDialogVisible = false">取 消</el-button>
                            </span>
                        </el-dialog>
                    </div>

                </div>
            </div>
        </div>
    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/provider/permissions.js?v=${ver}"></script>
</@override>
<@extends name="/base-rgpur2.ftl"/>
