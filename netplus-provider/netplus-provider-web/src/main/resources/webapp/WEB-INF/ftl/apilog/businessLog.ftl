<@override name="title">系统日志</@override>

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
                <span class="c_blue fl ml10">系统日志</span>
            </div>
            <div class="mt5">
                <div class="bd_t_dedede">
                    <ul class="menu_right">
                        <li class="curr">业务日志</li>
                        <li><a href="/provider/apilog/loginLog">登录日志</a></li>
                    </ul>
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
                        <el-table-column  prop="indexNum" label="序号" width="50"></el-table-column>
                        <el-table-column  prop="description" label="描述"></el-table-column>
                        <el-table-column  prop="createUser" label="业务操作人"></el-table-column>
                        <el-table-column  prop="logTypeStr" label="日志类型"></el-table-column>
                        <el-table-column  prop="requestId" label="请求ip"></el-table-column>
                        <el-table-column  prop="apiName" label="URL"></el-table-column>
                        <el-table-column  prop="content" label="请求参数"></el-table-column>
                        <el-table-column  prop="createUser" label="创建人"></el-table-column>
                        <el-table-column  prop="createDateStr" label="创建时间" width="150"></el-table-column>




                        <#--<el-table-column  label="启用状态">
                            <template slot-scope="scope">
                                <span v-if="scope.row.isavailable=== 'Y' "><i class="iconfont icon-dui1 c_green f20"></i></span>
                                <span v-if="scope.row.isavailable=== 'N' "><i class="iconfont icon-cuo c_red f20"></i></span>
                            </template>
                        </el-table-column>-->


                    </el-table>

                    <!-- 分页 -->
                    <div class="page">
                        <div class="fl">
                            <span class="c_blue ml10" style="cursor: pointer" @click="exportDetail">导出明细</span>
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

                </div>
            </div>
        </div>
    </div>
</div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/provider/businessLog.js?v=${ver}"></script>

</@override>

<@extends name="/base-rgpur2.ftl"/>