<@override name="title">商品组管理</@override>

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
    <div class="right_con" id="RZ" v-cloak>
        <div class="right_top">
            <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
            <span class="c_blue fl ml10">商品组管理</span>
        </div>
        <div class="mt5">
            <div class="">

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
                    <div class="pr">
<#--                        <el-checkbox v-model="isCheckAll" @change="handleCheckAllChange" class="pa" style="left: 20px;top: 14px;z-index: 1000;"></el-checkbox>-->
                        <el-table
                                :data="tableData"
                                class="table_main_style expand_table"
                                @selection-change="handleSelectionChange"
                                @expand-change="expandChange"
                        >
                            <el-table-column type="expand" width="50" >
                                <template slot-scope="props" >
                                    <div class="of tc table_expand"  v-for="(item,index) in props.row.goodslist" :key="index">
                                        <div class="w50">
                                        </div>
                                        <div class="tl fl w280" >
                                            <img :src="item.pictureurl?item.pictureurl:'/statics/img/shop/noPic.png'" class="fl mt10 mb10" alt="" width="68px" height="68px">
                                            <div class="tl w140 fl p10">
                                                <div>{{item.productname}}</div>
                                                <div class="lh10 c_999 f10">
                                                    <div>货号：{{item.goodno}} </div>
                                                    <div>型规：{{item.spec}}</div>
                                                    <div>{{item.matrlno}}</div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="w200 fl">
                                            {{item.leadtimenum}}个工作日
                                        </div>
                                        <div class="fl w200">
                                            <div><i class="c_red">￥{{item.price}}</i>/组</div>
                                            <div class="f10 c_999">
                                                <div>未税：￥{{item.notaxprice}}/组</div>
                                                <div>税率：{{item.tax|formatTax}}%</div>
                                            </div>
                                        </div>
                                        <div class="w150 fl">
                                            {{item.statusName}}
                                        </div>
                                        <div class="w140 fl">
                                            <div><button class="blue_txt_btn" @click="deleteFromGroup(item.goodid,item.groupno)">删除</button></div>
                                        </div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column type="selection" width="30"></el-table-column>
                            <el-table-column  prop="date" label="商品信息" width="250">
                                <template slot-scope="scope">
                                        商品组号：{{scope.row.groupno}}
                                </template>
                            </el-table-column>
                            <el-table-column  label="交货周期" width="200">
                                <template slot-scope="scope">
                                    <div>创建时间： {{scope.row.createdate | strDateFormat }} {{scope.row.createtime | strTimeFormat}}</div>
                                </template>
                            </el-table-column>
                            <el-table-column  prop="date" label="价格" width="200"></el-table-column>
                            <el-table-column  prop="date" label="状态" width="150"></el-table-column>
                            <el-table-column  prop="date" label="操作">
                                <template slot-scope="scope">
                                    <div><button class="blue_txt_btn" @click="deleteGroup(scope.row.groupno)">
                                            删除</button> </div>
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                    <!-- 分页 -->
                    <div class="page">
                        <div class="fl">
                            <#--                            <el-checkbox v-model="isCheckAll" @change="handleCheckAllChange" class="ml10"></el-checkbox>-->
                            已选 <span class="c_blue">{{multipleSelection.length}}</span> 条
                            <span class="c_blue ml10 cur" @click="batchDeleteGoods(multipleSelection)" style="cursor: pointer">删除</span>
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


                </div>
            </div>
        </div>

    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/javascript" src="${cdn}/js/mall/goodsGroup.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>