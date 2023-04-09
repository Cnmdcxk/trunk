<@override name="title">账号管理</@override>

<@override name="css">
</@override>

<@override name="content">
    <!-- 内容区域 -->
    <div class="bg_fff">
        <div class="w1200">
            <#include "/left-nav.ftl">
            <!-- 右侧内容 -->
            <div class="right_con" id="RZ" style="overflow: hidden;">
                <div style="width: 1060px;overflow: auto;">
                    <div class="right_top">
                        <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                        <span class="c_blue fl ml10">账号管理</span>
                    </div>
                    <div class="p10">
                        <!-- 筛选 -->
                        <frame-filter
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
                                        <span class="c_red f20 fb lh30">{{totalCount}}</span> 条
                                    </div>
                                    <span class="c_red f20 fb">{{totalCount}}</span>
                                </el-tooltip> 条
                            </div>
                        </div>
                        <!-- 表格 -->
                        <el-table :data="dataList" class="table_main_style">
                            <el-table-column  prop="userno" label="账号" width="100"></el-table-column>
                            <el-table-column  prop="usertype" label="账号类型" width="100">

                                <template slot-scope="scope">
                                    <div>
                                        <span v-if="scope.row.usertype == 'B'">员工</span>
                                        <span v-if="scope.row.usertype == 'S'">供应商</span>
                                    </div>
                                </template>

                            </el-table-column>

                            <el-table-column  prop="name" label="姓名" width="120"></el-table-column>
                            <el-table-column  prop="phone" label="手机号码" width="140"></el-table-column>
                            <el-table-column  prop="depname" label="部门名称" width="140"></el-table-column>


                            <el-table-column  label="创建时间" width="140">
                                <template slot-scope="scope">
                                    <div>
                                        <span>{{scope.row.createdate | strLongDate}}</span>
                                    </div>
                                </template>
                            </el-table-column>

                            <el-table-column width="160"  prop="role" label="角色">
                                <template slot-scope="scope">
                                    <div class="of">
                                        <div class="fl w100 tc">
                                            <div v-for="role in scope.row.tbmqq422VoList">{{role.rolename}}</div>
                                        </div>
                                        <button class="blue_txt_btn fl" :class="scope.row.tbmqq422VoList==null?'w':''"  @click="openSetRoleDialog(scope.row.userno)">修改角色</button>
                                    </div>
                                </template>
                            </el-table-column>

                            <el-table-column  label="启用状态" width="100">
                                <template slot-scope="scope">
                                    <span v-if="scope.row.isavailable=== 'Y' "><i class="iconfont icon-dui1 c_green f20"></i></span>
                                    <span v-if="scope.row.isavailable=== 'N' "><i class="iconfont icon-cuo c_red f20"></i></span>
                                </template>
                            </el-table-column>

                        </el-table>
                        <!-- 分页 -->
                        <div class="page">
                            <div class="fl">
                                <span class="c_blue ml10" style="cursor: pointer" @click="exportStaff">导出明细</span>
                            </div>
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


                        <el-dialog
                                title="设置角色"
                                :visible.sync="showSetRoleDialog"
                                :close-on-click-modal="false"
                                width="630px"
                                center>

                            <div class="bd-dc setCompanyType" style="width: 550px;max-height: 220px;overflow-y: auto;box-sizing: border-box;">

                                <table class="ml20 mt10 mb10" style="width: 485px;">
                                    <tr>
                                        <td>
                                            <div class="w mt10 f12">
                                                <el-checkbox class="w_30 mb15" v-for="r in roleList" v-model="r.isCheck">
                                                    {{r.rolename}}
                                                </el-checkbox>
                                            </div>
                                        </td>
                                    </tr>
                                </table>

                            </div>

                            <span slot="footer" class="dialog-footer">
                           <el-button type="primary" @click="saveRole">保存</el-button>
                           <el-button @click="closeSetRoleDialog">取消</el-button>
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
    <script type="text/babel" src="${cdn}/js/provider/satff.js?v=${ver}"></script>

</@override>

<@extends name="/base-rgpur2.ftl"/>