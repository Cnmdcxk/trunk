<@override name="title">接口管理</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
    <style type="text/css">
        [v-cloak]{
            display:none;
        }
    </style>
</@override>

<@override name="content">
    <!-- 内容区域 -->
    <div class="bg_fff">
        <div class="w1200">
            <#include "/left-nav.ftl">
            <!-- 右侧内容 -->
            <div  class="right_con" id="RZ"  v-cloak>
                <div class="right_top">
                    <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                    <span class="c_blue fl ml10">接口管理</span>
                </div>
                <div class="p10">
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
                                    <span class="c_red f20 fb lh30">{{totalCount}}</span> 条
                                </div>
                                <span class="c_red f20 fb">{{totalCount}}</span>
                            </el-tooltip> 条
                        </div>
                    </div>
                    <!-- 表格 -->
                    <el-table :data="dataList" class="table_main_style">
                        <el-table-column  prop="reqid" label="接口id" ></el-table-column>
                        <el-table-column  prop="reqname" label="接口名称" ></el-table-column>
                        <el-table-column  prop="callee" label="接口被调用方" ></el-table-column>
                        <el-table-column  prop="reqtime" label="接口请求数据时间" width="140" >
                            <template slot-scope="scope">
                                <div>
                                    <span>{{scope.row.reqtime|timestampToDate}}</span>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column  prop="caller" label="接口调用方" ></el-table-column>
                        <el-table-column  prop="resptime" label="接口返回数据时间" width="140" >
                            <template slot-scope="scope">
                                <div>
                                    <span>{{scope.row.resptime|timestampToDate}}</span>
                                </div>
                            </template>
                        </el-table-column>
                        <el-table-column  prop="status" label="状态" ></el-table-column>
                        <el-table-column prop="date" label="操作" fixed="right">
                            <template slot-scope="scope">
                                <a style="color:#0F83E6" @click="dataDisplay(scope.row.reqid)">查看</a>

                                <a v-if="scope.row.status !='OK' && scope.row.times < 10" style="color:#0F83E6" @click="restartReq(scope.row)">重发</a>
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

                    <el-dialog :visible.sync="centerDialogVisible1" width="800px" center :close-on-click-modal="false">
                        <label>请求地址: {{requrl}}</label>
                        <br/>
                        <label>请求数据</label>
                        <el-input
                                            v-model="reqdata"
                                            :disabled="true"
                                            type="textarea"
                                            style="height:200px"
                                    >
                                    </el-input>

                        <label>返回数据</label>
                                    <el-input
                                            v-model="respdata"
                                            :disabled="true"
                                            type="textarea"
                                            style="height:200px"
                                    >
                                    </el-input>
                    </el-dialog>
                </div>
            </div>
        </div>
    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?v=${ver}"></script>
    <script type="text/javascript" src="${cdn}/js/monitor/monitor.js?v=${ver}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>