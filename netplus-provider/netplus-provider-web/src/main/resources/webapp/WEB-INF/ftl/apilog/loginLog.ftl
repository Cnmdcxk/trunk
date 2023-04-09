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
                            <li><a href="/provider/apilog/businessLog">业务日志</a></li>
                            <li class="curr">登录日志</li>
                        </ul>
                    </div>
                    <div class="p10">

                        <div class="search-input">
                            <input type="text" placeholder="操作类型" v-model="searchKey">
                            <i class="iconfont icon-zoom_x" @click="search()"></i>
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
                            <el-table-column  prop="indexNum" label="序号" width="50"></el-table-column>
                            <el-table-column  prop="userNo" label="用户编号"></el-table-column>
                            <el-table-column  prop="ext1" label="用户名称"></el-table-column>
                            <el-table-column  prop="ipAddress" label="IP地址" width="150"></el-table-column>
                            <el-table-column  prop="enterTimeStr" label="记录时间" width="150"></el-table-column>
                            <el-table-column  prop="outTimeStr" label="完成时间" width="150"></el-table-column>
                            <el-table-column  prop="pageUrl" label="访问页面" width="150"></el-table-column>
                            <el-table-column  prop="logTypeStr" label="操作类型" width="75"></el-table-column>
                            <el-table-column  prop="ieVersion" label="浏览器版本"></el-table-column>

                            <#--<el-table-column  label="启用状态" width="100">
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
    <script type="text/babel" src="${cdn}/js/provider/loginLog.js?v=${ver}"></script>

</@override>

<@extends name="/base-rgpur2.ftl"/>