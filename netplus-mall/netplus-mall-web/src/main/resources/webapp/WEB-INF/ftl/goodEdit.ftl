<@override name="title">编辑商品详情</@override>

<@override name="css">
     <link rel="stylesheet" href="${cdn}/css/shop.css"/>
     <link rel="stylesheet" href="${cdn}/css/img-zoom.css">
     <link rel="stylesheet" href="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.css">

    <style type="text/css">
        .input3 {
            border: 1px solid #cccccc;
            border-radius: 2px;
        }
        .swiper-button-prev::after, .swiper-container-rtl .swiper-button-next::after {
            content: '';
        }
    </style>
</@override>

<@override name="content">
<div class="bg_fff">
    <div class="w1200">
        <!-- 右侧内容 -->
        <div class="right_top">
            <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
            <span class="c_blue fl ml10">编辑商品信息</span>
        </div>

        <div class="mt5" id="app" v-cloak>
            <!-- 基本信息 -->
            <div class="bg_blue_title">
                <span>1.基本信息</span>
            </div>
            <div class="mt20">
                <div class="of">
                    <div class="img_zoom img-zoom-box fl">
                        <!-- 图片 -->
                        <div class="shop_upload" v-if="mainPicList.length==0">
                            <el-upload ref="upload" class="upload_box"
                                :show-file-list="false"
                                action="/api/v2/fileupload/doUpload/"
                                :limit="1"
                                :before-upload="beforeUpload"
                                :on-success="successUpload"
                                :on-error="errorUpload"
                                accept=".png,.jpg,.jpeg"
                            >
                                <div class="">
                                    <i class="iconfont icon-tupianshangchuan"></i>
                                    <div class="lh30">点击此处上传图片</div>
                                </div>
                            </el-upload>

                            <div class="w255">
                                至少上传一张商品图片，最多允许上传10张图片； 建议上传尺寸400*400，文件最大不能超过300k 能上传的图片类型：jpg、png、jpeg
                            </div>
                        </div>

                        <div v-else>

                            <div class="left pr" v-if="mainPicList.length!==0">
                                <img class="leftImg" v-if="mainPicList.length!==0" :src="mainPicList[currImgIndex].pictureurl" alt="">
                                <div class="pa right_btn">
                                    <el-upload ref="upload" class="upload_box"
                                        :show-file-list="false"
                                        action="/api/v2/fileupload/doUpload/"
                                        :limit="1"
                                        :before-upload="beforeUpload"
                                        :on-success="successUpload"
                                        :on-error="errorUpload"
                                        accept=".png,.jpg,.jpeg">
                                        <span class="small_btn_gray">上传</span>
                                    </el-upload>
                                    <span class="small_btn_gray" @click="delPic">删除</span>
                                </div>
                            </div>

                            <div v-show="rShow" class="right" v-if="mainPicList.length>0">
                                <img :style="r_img" class="rightImg" :src="mainPicList[currImgIndex].pictureurl" alt="">
                            </div>
                        </div>


                        <!-- 小图 -->
                        <div class="img_zoom_bottom">
                            <div class="swiper-container1">
                                <div class="swiper-wrapper">
                                    <div class="swiper-slide" v-for="(item ,index) in mainPicList" @click="imgZoomClick(index)">
                                        <div class="img_zoom_swiper" :class="index==currImgIndex?'curr':''" >
                                            <img :src="item.pictureurl" alt="" class="img">
                                            <#--<i class="iconfont icon-cuo c_red"></i>-->
                                        </div>
                                    </div>
                                </div>
                                <div class="swiper-button-next"><i class="iconfont icon-right f16"></i> </div>
                                <div class="swiper-button-prev" ><i class="iconfont icon-left f16"></i> </div>
                            </div>
                        </div>
                    </div>
                    

                    <!-- 信息 -->
                    <div class="shop_detail">
                        <el-form ref="addForm" class="my_form" label-width="110px" :model="goodInfo" :rules="rules">
                            <el-form-item label="商品分类：">
                                <span>{{goodInfo.categoryname}}/{{goodInfo.onelevelclaname}}/{{goodInfo.twolevelclaname}}</span>
                            </el-form-item>

                            <el-form-item label="商品名称：">
                                <div>{{goodInfo.productname}}</div>
                            </el-form-item>

                            <el-form-item label="物料条码：">
                                <div class="of">
                                    <div class="w_20 fl">{{goodInfo.matrltm}}</div>
                                    <div class="w_40 fl">物料编号：{{goodInfo.matrlno}} </div>
                                    <div class="w_20 fl">货号: {{goodInfo.goodno}}</div>
                                </div>
                            </el-form-item>

                            <el-form-item label="单位：" >
                                <span>{{goodInfo.qtyunit}}</span>
                            </el-form-item>

                            <el-form-item label="商品价格" prop="price">

                                <input type="text" v-model="goodInfo.price" class="w100 input3 tc" @keyup="inputKeyUp">
                                <span>税率</span> <input type="text" v-model="goodInfo.tax2" class="w100 input3 tc" disabled>
                                <span>未税价格</span> <input type="text" v-model="goodInfo.notaxprice" class="w100 input3 tc" disabled>

                                <span>/{{goodInfo.qtyunit}}</span>
                            </el-form-item>

                            <el-form-item label="型规："  class="w_50 fl">
                                <span>{{goodInfo.spec}}</span>
                            </el-form-item>

                            <el-form-item label="品牌："  class="w_50 fl">
                                <span>{{goodInfo.brand}}</span>
                            </el-form-item>

                            <el-form-item label="最小起订量：" class="w_50 fl">
                                <input type="text" v-model="goodInfo.minbuyqty" class="w100 input3 tc" @keyup="inputKeyUp2">
                                <el-checkbox v-model="isIntTimePurchaseCheck" :disabled="isDisabled"></el-checkbox>
                                <span>按起订量整数倍购买</span>
                            </el-form-item>

                            <el-form-item label="材质：" class="w_50 fl">
                                <span>{{goodInfo.qlty}}</span>
                            </el-form-item>

                            <el-form-item label="交货周期：" prop="referdeliverydate" class="w_50 fl clear">
                                <span>{{goodInfo.leadtimenum}}工作日</span>
                            </el-form-item>

                            <el-form-item label="包装尺寸："class="clear">


                                <span>厚度</span> <input type="text" v-model="goodInfo.packthick" class="w80 input3 tc" @keyup="inputKeyUp3('packthick')">
                                <span>宽度</span> <input type="text" v-model="goodInfo.packwidth" class="w80 input3 tc" @keyup="inputKeyUp3('packwidth')">
                                <span>长度</span> <input type="text" v-model="goodInfo.packheight" class="w80 input3 tc" @keyup="inputKeyUp3('packheight')">

                                <el-select v-model="goodInfo.packunit" placeholder="单位" class="w60">
                                    <el-option label="厘米" value="厘米"></el-option>
                                    <el-option label="毫米" value="毫米"></el-option>
                                    <el-option label="米" value="米"></el-option>
                                </el-select>

                            </el-form-item>
                        </el-form>
                    </div>

                </div>
            </div>

            <div class="bg_blue_title mt50">
                <span>2.商品明细信息</span>
            </div>
            <!-- 切换 -->
            <div class="blue_tap mt20">
                <div class="item curr">商品介绍</div>
                
            </div>

            <!-- 富文本 -->
            <div class="mt20" id="editor"></div>
            
             <!-- 按钮 -->
            <div class="clear tc mt20">
                <button class="main_btn" @click="save">保存</button>
            </div>

            <div class="h100"></div>


        </div>
    </div>
</div>
</@override>


<@override name="js">
     <script>var goodId = '${goodId!}'; var cdn = '${cdn}';</script>


     <script src="${cdn}/js/3rd/wangEditor/wangEditor-4-7-5.min.js"></script>
     <script src="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.js"></script>
     <script src="${cdn}/js/framework/components/img-zoom.js"></script>
     <script type="text/javascript" src="${cdn}/js/mall/goodEdit.js"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>