<@override name="title">商品历史信息</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/goodsHistory.css" />
</@override>

<@override name="content">

<div class="w1200">

    <#include "/left-nav.ftl">

    <div id="RZ" class="" v-cloak>
        <!-- 内容区域 -->
        <div class="bg_fff">
            <div class="w1200">
                <!-- 右侧内容 -->
                <div class="right_con">
                    <div class="right_top">
                        <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                        <span class="c_blue fl ml10">商品历史信息</span>
                    </div>

                    <div class="p10">
                        <el-form :model="searchParams" ref="queryForm" :inline="true" label-width="85px">
                            <el-form-item label="供应商名称" prop="suppliername">
                                <el-autocomplete
                                        class="inline-input"
                                        v-model="searchParams.suppliername"
                                        :fetch-suggestions="handleSearch"
                                        placeholder="请输入供应商名称"
                                        :trigger-on-focus="false"
                                        @select="handleSelect"
                                        @focus="handleFocus('supplierNameList')"
                                        @clear="handleClear('suppliername')"
                                        @keyup.enter.native="handleSelect"
                                        clearable
                                ></el-autocomplete>
                            </el-form-item>
                            <el-form-item label="物料条码" prop="matrltm">
                                <el-autocomplete
                                        class="inline-input"
                                        v-model="searchParams.matrltm"
                                        :fetch-suggestions="handleSearch"
                                        placeholder="请输入物料条码"
                                        :trigger-on-focus="false"
                                        @select="handleSelect"
                                        @focus="handleFocus('matrltmList')"
                                        @clear="handleClear('matrltm')"
                                        @keyup.enter.native="handleSelect"
                                        clearable
                                ></el-autocomplete>
                            </el-form-item>
                            <el-form-item label="协议号" prop="pono">
                                <el-autocomplete
                                        class="inline-input"
                                        v-model="searchParams.pono"
                                        :fetch-suggestions="handleSearch"
                                        placeholder="请输入协议号"
                                        :trigger-on-focus="false"
                                        @select="handleSelect"
                                        @focus="handleFocus('ponoList')"
                                        @clear="handleClear('pono')"
                                        @keyup.enter.native="handleSelect"
                                        clearable
                                ></el-autocomplete>
                            </el-form-item>
                        </el-form>
                        <!-- 表格 -->
                        <el-table :data="tableData" class="table_main_style">
                            <el-table-column prop="suppliername" label="商品信息" width="240">
                                <template slot-scope="scope">
                                    <div class="tl ml10">
                                        <img :src="scope.row.pictureurl?scope.row.pictureurl:'/statics/img/shop/noPic.png'" class="fl mt10 mb10" alt="" width="68px" height="68px">
                                        <div class="tl w140 fl p10">
                                            <div>{{scope.row.productname}}{{scope.row.brand != null && scope.row.brand != '' ?'/'+scope.row.brand:''}}</div>
                                            <div class="lh10 c_999 f10">
                                                <div v-if="scope.row.goodno != null">货号：{{scope.row.goodno}}</div>
                                                <div>型规：{{scope.row.spec}}</div>
                                                <div>物料编码：{{scope.row.matrlno}}</div>
                                                <div>物料条码：{{scope.row.matrltm}}</div>
                                            </div>
                                        </div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="matrltm" label="协议号" width="100">
                                <template slot-scope="scope">
                                    <span>{{scope.row.pono}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column prop="pono" label="物资采购人" width="100">
                                <template slot-scope="scope">
                                    <span>{{scope.row.agent}}</span>
                                </template>
                            </el-table-column>
                            <el-table-column label="交货周期" width="100">
                                <template slot-scope="scope">
                                    <span v-if="scope.row.leadtimenum > 0">{{scope.row.leadtimenum}}个工作日</span>
                                </template>
                            </el-table-column>
                            <el-table-column width="100">
                                <template slot="header">
                                    商城价格
                                    <br />协议价格
                                </template>
                                <template slot-scope="scope">
                                    <div class="tc">
                                        <div><i class="c_red">￥{{scope.row.price}}/{{scope.row.qtyunit}}/{{scope.row.tax | formatTax}}%</i></div>
                                        <div>￥{{scope.row.originprice}}/{{scope.row.qtyunit}}/{{scope.row.tax | formatTax}}%</div>
                                    </div>
                                </template>
                            </el-table-column>
                            <el-table-column label="合同有效期" width="100">
                                <template slot-scope="scope">
                                    <div>{{scope.row.popricestartdate | strDateFormat }}</div>
                                    <div>{{scope.row.popricedate | strDateFormat }}</div>
                                </template>
                            </el-table-column>
                            <el-table-column label="创建时间" width="90">
                                <template slot-scope="scope">
                                    <div>{{scope.row.createdate | strDateFormat }}</div>
                                    <div>{{scope.row.createtime | strTimeFormat }}</div>
                                </template>
                            </el-table-column>
                            <el-table-column label="更新时间" width="90">
                                <template slot-scope="scope">
                                    <div>{{scope.row.updatedate | strDateFormat }}</div>
                                    <div>{{scope.row.updatetime | strTimeFormat }}</div>
                                </template>
                            </el-table-column>
                            <el-table-column label="供应商" width="90">
                                <template slot-scope="scope">
                                    {{scope.row.supplierno}}-{{scope.row.suppliername}}
                                </template>
                            </el-table-column>
                            <el-table-column label="状态" width="90">
                                <template slot-scope="scope">
                                    {{scope.row.statusName}}
                                </template>
                            </el-table-column>
                        </el-table>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

</@override>

<@override name="js">
    <script>
        var status = '${status!}';
    </script>
    <script type="text/javascript" src="${cdn}/js/mall/goodsHistory.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>