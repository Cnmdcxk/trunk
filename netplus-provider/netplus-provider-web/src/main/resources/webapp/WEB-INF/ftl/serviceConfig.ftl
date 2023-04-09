<@override name="title">业务配置</@override>

<@override name="css">
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
                        <span class="c_blue fl ml10">业务配置</span>
                    </div>
                    <div class="p10">
                        <div class="search-input">
                            <input type="text" placeholder="业务配置代码/业务配置名称" v-model="searchService">
                            <i class="iconfont icon-zoom_x" @click="search(searchService)"></i>
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
                        <el-table :data="dataList" class="table_main_style">
                            <el-table-column  prop="code" label="业务配置代码" ></el-table-column>
                            <el-table-column  prop="name" label="业务配置名称" ></el-table-column>
                            <el-table-column  prop="val" label="业务配置参数" ></el-table-column>
                            <el-table-column  prop="createdate createtime" label="创建时间" >
                                <template slot-scope="scope">
                                    {{scope.row.createdate | strLongDate}} {{scope.row.createtime | strLongDate}}
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="操作" fixed="right">
                                <template slot-scope="scope">
                                    <button class="blue_txt_btn" @click="updataForms(scope.row)">修改</button>
                                    <button class="blue_txt_btn" @click="SubClick(scope.row)">删除</button>
                                </template>
                            </el-table-column>
                        </el-table>
                        <!-- 分页 -->
                        <div class="page">
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
                        </div>
                        <div class="tr">
                            <button class="main_btn" @click="centerDialogVisible1 = true">新增</button>
                        </div>

                        <!-- 新增 -->
                        <el-dialog title="新增" :close-on-click-modal="false" :visible.sync="centerDialogVisible1" width="430px" center @close="handleDialogClose">
                            <div class="w_90 margin">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="Form" :rules="rules">
                                    <el-form-item label="业务配置代码" prop="code">
                                        <el-input v-model="Form.code" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="业务配置名称" prop="name" >
                                        <el-input v-model="Form.name" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="业务配置参数" prop="val" >
                                        <el-input v-model="Form.val" placeholder="请输入"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="addParms('addForm')">保 存</el-button>
                                <el-button @click="handleDialogClose('addForm')">取 消</el-button>
                            </span>
                        </el-dialog>

                        <!-- 修改 -->
                        <el-dialog title="修改" :close-on-click-modal="false" :visible.sync="centerDialogVisible2" width="430px" center>
                            <div class="w_90 margin">
                                <el-form ref="addForm" class="my_form" label-width="130px" :model="updateForm" :rules="rules">
                                    <el-form-item label="业务配置代码" prop="code">
                                        <el-input v-model="updateForm.code" placeholder="请输入" :disabled="true"></el-input>
                                    </el-form-item>
                                    <el-form-item label="业务配置名称" prop="name">
                                        <el-input v-model="updateForm.name" placeholder="请输入"></el-input>
                                    </el-form-item>
                                    <el-form-item label="业务配置参数" prop="val">
                                        <el-input v-model="updateForm.val" placeholder="请输入"></el-input>
                                    </el-form-item>
                                </el-form>
                            </div>
                            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="update('addForm')">保 存</el-button>
                                <el-button @click="centerDialogVisible2 = false">取 消</el-button>
                            </span>
                        </el-dialog>
                    </div>
                </div>
            </div>
        </div>
</@override>

<@override name="js">
    <script type="text/babel" src="${cdn}/js/provider/serviceConfig.js?v=${ver}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>