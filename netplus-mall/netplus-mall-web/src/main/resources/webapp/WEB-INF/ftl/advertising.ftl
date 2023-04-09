<@override name="title">商城运营管理-商城页面管理</@override>

<@override name="css">
    <style>
        .right_web .swiper-slide{
            width: 154px;
        }
        .right_web_swiper{
            position: relative;
            line-height: 50px;
        }
        .right_web_swiper .el-checkbox__input{
            position: absolute;
            left: 0;
            top: 10px;
        }
        .right_web_swiper .el-checkbox__label{
            padding-left: 0;
            line-height: 34px;
        }
        .right_web_swiper .img{
            width: 154px;
            height: 66px;
        }
        .right_web .swiper-button-prev{
            left: -8px;
            color: #D5D5D5;
        }
        .right_web .swiper-button-next{
            right: -8px;
            color: #D5D5D5;
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
            <span class="c_blue fl ml10">商城运营管理</span>
        </div>
        <div class="mt5">
            <div class="bd_t_dedede">
                <ul class="menu_right">
                    <li><a href="/mall/basicData1">物料号sku关系维护</a></li>
                    <li><a href="/mall/basicData3">物料图片维护</a></li>
                    <li><a href="/mall/basicData4">物料基础信息</a></li>
                    <li class="curr">商城页面管理</li>
                </ul>
            </div>
            <div class="p10">
                <div class="of">
                    <div class="w_65 fl pr">
                        <div class="bg_f5f5f5 p10">
                            <span class="c_blue mr15">广告位1：首页大广告</span>
                            <span class="mr15">广告尺寸：1920*370</span>
                            <span class="mr15">已发布广告数：{{count}}</span>
                        </div>
                        <!-- 轮播 -->
                        <div class="lh40 of pt10">
                            <el-checkbox-group v-model="checkList">
                                <div class="right_web w of gundongLine" style="height: 300px;overflow-y: scroll;">
                                    <div class="right_web_swiper fl mr10" v-for="(item,index) in list" @mouseover="showIndex(index)">
                                        <el-checkbox :label="item.id">
                                            <div class="txt_of pl15 w150" :title="item.imagename">{{index+1}}.{{item.imagename}}</div>
                                            <div class="img_box"><img :src="item.imagepath" alt="" class="img"></div>
                                            <div class="tc">
                                                <div class="c_green" v-if="item.status == '1'">已发布</div>
                                                <div class="c_red" v-else>未发布</div>
                                            </div>
                                        </el-checkbox>
                                    </div>
                                </div>
                            </el-checkbox-group>
                        </div>

                    </div>
                    <div class="ml10 fl mt60" style="width: calc(35% - 10px);">
                        <div class="tc p10">广告预览</div>
                        <img v-if="list.length>0" :src="list[viewIndex].imagepath" alt="" class="w" height="158">
                    </div>
                </div>
                <!-- 操作按钮 -->
                <div class="tc">
                    <el-upload
                            class="upload-demo disib"
                            action="/api/v2/fileupload/doUpload/"
                            :show-file-list="false"
                            :before-upload="beforeUpload"
                            :on-success="successUpload"
                            :on-error="errorUpload"
                            accept=".png,.jpg,.jpeg"
                    >
                        <button class="main_self_btn mr10">新增+</button>
                    </el-upload>
                    <button class="main_fff_self_btn mr10" @click="publishChecked()">发布</button>
                    <button class="main_fff_self_btn mr10" @click="cancelPublishChecked()">取消发布</button>
                    <button class="blue_txt_btn mr10" @click="leftMove()">左移</button>
                    <button class="blue_txt_btn mr10" @click="rightMove()">右移</button>
                    <button class="blue_txt_btn" @click="remove()">删除</button>
                </div>
            </div>
        </div>

    </div>
</div>
</@override>

<@override name="js">
    <script type="text/babel" src="${cdn}/js/mall/advertising.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>