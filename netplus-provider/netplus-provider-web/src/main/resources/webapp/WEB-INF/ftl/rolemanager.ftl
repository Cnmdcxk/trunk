
<@override name="title">权限信息</@override>

<@override name="css">
    <#--<link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">-->
    <#--<style type="text/css">-->
        <#--[v-cloak]{-->
            <#--display:none;-->
        <#--}-->
    <#--</style>-->
</@override>

<@override name="content">
        <!-- 内容区域 -->
        <div class="bg_fff">

            <div class="w1200">
                <#include "/left-nav.ftl">


                <!-- 右侧内容 -->
                <div class="right_con" id="RZ" v-cloak>
                    <div class="right_top">
                        <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                        <span class="c_blue fl ml10">角色管理</span>
                    </div>

                    <div class="p10">

                        <div class="search-input">
                            <input type="text" placeholder="角色名称/角色代码"  v-model="rolename">
                            <i class="iconfont icon-zoom_x" @click="search(rolename)"></i>
                        </div>

                        <!-- 统计结果 -->
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
                        <el-table :data="tableData" class="table_main_style">
                            <el-table-column  prop="rolecode" label="角色代码" width="120"></el-table-column>
                            <el-table-column  prop="rolename" label="角色名字" width="120"></el-table-column>
                            <el-table-column  prop="roledesc" label="描述" width="220"></el-table-column>
                            <el-table-column  label="角色权限" width="130" >
                                <template slot-scope="scope">
                                    <div class="dis_f">
                                        <div v-if="scope.row.rolepermission == '1'" class="mr5">已设置</div>
                                        <div v-if="scope.row.rolepermission == '0'" class="mr5">未设置</div>

                                        <div v-if="scope.row.rolepermission == '0'"><a class="blue_txt_btn" :href="'/provider/permissonSet/?roleCode='+scope.row.rolecode" >设置</a></div>
                                        <div v-if="scope.row.rolepermission == '1'"><a class="blue_txt_btn" :href="'/provider/permissonSet/?roleCode='+scope.row.rolecode" >查看</a></div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column   label="创建时间">
                                <template slot-scope="scope">
                                    <div>
                                        <span>{{scope.row.createdate | strLongDate}}</span>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column  label="操作">

                                <template slot-scope="scope">
                                    <button class="blue_txt_btn" @click="updataForms(scope.row)">修改</button>
                                    <button class="blue_txt_btn" @click="SubClick(scope.row)">删除</button>
                                </template>

                            </el-table-column>
                        </el-table>
                        <#--新增-->
                        <el-dialog title="新增角色" :visible.sync="centerDialogVisible3" width="430px" center :close-on-click-modal="false">
                            <div class="w_90 margin">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="ruleForm" :rules="rules">
                                    <el-form-item label="角色代码" prop="rolecode">
                                        <el-input v-model="ruleForm.rolecode" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="角色名称" prop="rolename">
                                        <el-input v-model="ruleForm.rolename" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="角色描述" prop="roledesc">
                                        <el-input v-model="ruleForm.roledesc" placeholder="请输入"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="addParms('addForm')">保 存</el-button>
                                <el-button @click="centerDialogVisible3 = false">取 消</el-button>
                            </span>
                        </el-dialog>
                        <#-- 修改-->
                        <el-dialog title="修改角色" :visible.sync="centerDialogVisible" width="430px" center :close-on-click-modal="false">
                            <div class="w_90 margin">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="updateForm" :rules="rules">
                                    <el-form-item label="角色代码" prop="rolecode">
                                        <el-input v-model="updateForm.rolecode" :disabled="true"></el-input>
                                    </el-form-item>
                                    <el-form-item label="角色名称" prop="rolename">
                                        <el-input v-model="updateForm.rolename"></el-input>
                                    </el-form-item>
                                    <el-form-item label="角色描述" prop="roledesc">
                                        <el-input v-model="updateForm.roledesc" ></el-input>
                                    </el-form-item>

                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="update('addForm')">保 存</el-button>
                                <el-button @click="centerDialogVisible = false">取 消</el-button>
                            </span>
                        </el-dialog>
                        <!-- 分页 -->
                        <div class="page mt10 mb10 of">
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
                            <button class="main_btn" @click="centerDialogVisible3 = true">新增角色</button>
                        </div>
                    </div>
                </div>
            </div>
      </div>
</@override>

<@override name="js">
    <script src="${cdn}/js/provider/rolemanager.js?v=${ver!}"></script>
</@override>
<@extends name="/base-rgpur2.ftl"/>
