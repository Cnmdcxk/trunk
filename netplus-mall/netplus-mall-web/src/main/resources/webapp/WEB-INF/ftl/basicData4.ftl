<@override name="title">商城运营管理-物料基础信息</@override>

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
                        <li><a href="/mall/basicData1">物料条码sku关系维护</a></li>
                        <li><a href="/mall/basicData3">物料图片维护</a></li>
                        <li class="curr">物料基础信息</li>
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
                        <el-table class="table_main_style"
                                :data="dataList"
                                style="width: 100%"
                                ref="multipleTable">


                            <el-table-column  prop="matrlno" label="商城ERP物料号"></el-table-column>
                            <el-table-column label="商品信息">
                                <template slot-scope="scope">
                                    {{scope.row.productname}}/{{scope.row.spec}}
                                </template>
                            </el-table-column>
                            <el-table-column  prop="matrldesc" label="物料描述"></el-table-column>
                            <el-table-column  prop="unit" label="计量单位"></el-table-column>
                            <el-table-column  prop="twolevelclaname" label="二级分类"></el-table-column>
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
                    </div>
                </div>
            </div>
        </div>
    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/basicData4.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>