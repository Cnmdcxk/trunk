<@override name="title">商品图片管理</@override>

<@override name="css">
<link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
<link rel="stylesheet" href="${cdn}/css/shop.css">
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
            <div class="bd_t_dedede">
                <ul class="menu_right supplier_menu">
                    <li><a href="/mall/goodsPicture">商品主图</a></li>
                    <li class="curr">回收站</li>
                </ul>
                <div class="p10">
                    <!-- 搜索 -->
                    <div class="of">
                        <div class="fl mt20">
                            <div class="search-input">
                                <input type="text" v-model="searchKey" placeholder="物料条码">
                                <i class="iconfont icon-zoom_x" @click="search"></i>
                            </div>
                        </div>
                    </div>
                    <!-- 全选 -->
                    <div class="blue_line_top mt20">
                        <div class="bd_gray lh50">
                            <el-checkbox v-model="isSelectedAll" class="ml10"></el-checkbox> 全选
                            <span class="ml30">已选 <i class="c_blue">{{count}}</i>个</span>
                            <div class="fr">


                                <button class="main_self_btn w80 mr10" @click="back">恢复</button>
                                <button class="blue_txt_btn w80 mr20" @click="removePic">删除</button>
                            </div>
                        </div>
                    </div>

                    <!-- 图片列表 -->
                    <div class="upload_img_list" v-for="items in tableData">
                        <div class="up_load_title">
                            <span class="fb">{{items.updateDate | strLongDate}}</span>
                            <i class="iconfont icon-right c_999 f12"></i>
                        </div>



                        <el-checkbox v-model="item.checked" v-for="item in items.tbmqq429VoList" @change="watchCount">
                            <div class="img_box">

                                <img :src="item.pictureurl" alt="" class="img" @click="toPreview(item.pictureurl)">

                                 <div class="w txt_of lh32" >
                                    {{item.matrltm}}_{{item.picturenum}}
                                </div>
                            </div>
                        </el-checkbox>


                    </div>


                </div>
            </div>
        </div>

    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/goodsPictureRecycleBin.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>