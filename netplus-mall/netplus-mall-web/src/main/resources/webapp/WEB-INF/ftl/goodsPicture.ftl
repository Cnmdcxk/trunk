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
            <span class="c_blue fl ml10">商品图片管理</span>
        </div>
        <div class="mt5">
            <div class="bd_t_dedede">
                <ul class="menu_right supplier_menu">
                    <li class="curr" >商品主图</li>
                    <li><a href="/mall/goodsPictureRecycleBin">回收站</a></li>
                </ul>
                <div class="p10">
                    <div class="pt10 c_999 lh20 ml10">
                        <div class=""><i class="iconfont c_orange"></i> 提示：</div>
                        <div class="">1、图片的名称必须与对应商品的物料条码一致； </div>
                        <div class="">2、图片支持jpg、png、jpeg格式，⻓宽⽐1:1，建议尺⼨ 1280px，商品图片大小不超过300k；  </div>
                        <div class="">3、如果商品上有多个图片，在命名图片时，可按照物料条码+下划线+序号的方式命名如 3245737_1，图片会按照序号排列。 </div>
                    </div>
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
                            <el-checkbox v-model="isSelectedAll" @change="selectedAll" class="ml10"></el-checkbox> 全选
                            <span class="ml30">已选 <i class="c_blue">{{count}}</i>个</span>
                            <div class="fr">

                                <el-upload
                                        ref="upload"
                                        class="upload-demo fl disib"
                                        action="/api/v2/fileupload/doUpload/"
                                        :show-file-list="false"
                                        multiple
                                        :limit="50"
                                        :on-exceed="limitCheck"
                                        :before-upload="beforeUpload"
                                        :on-success="successUpload"
                                        :on-error="errorUpload"
                                        accept=".png,.jpg,.jpeg"
                                        >
                                    <el-button size="small" type="primary" @click="clear">上传</el-button>
                                </el-upload>
                                <button class="blue_txt_btn w80 mr20" @click="removePic">删除</button>
                            </div>
                        </div>
                    </div>

                    <!-- 图片列表 -->
                    <div class="upload_img_list" v-for="items in tableData">
                        <div class="up_load_title">
                            <span class="fb">{{items.createDate | strLongDate}}</span>
                            <i class="iconfont icon-right c_999 f12"></i>
                        </div>

                        <el-checkbox v-model="item.checked" v-for="item in items.tbmqq429VoList" @change="watchCount">
                            <div class="img_box">

                                <img :src="item.pictureurl" alt="" class="img" @click="toPreview(item.pictureurl)">
                                <div class="w txt_of lh32" >
                                   {{item.picturename}}
                                </div>
                            </div>
                        </el-checkbox>


                    </div>


                    <el-dialog
                            title="上传结果"
                            :visible.sync="showErrorDialog"
                            :close-on-click-modal="false"
                            :lock-scroll="false"
                            width="500px"
                            center>
                        <div class="mb10 pt10">
                            <div class="mb10">
                                <span>上传成功{{successCount}}条</span>
                                <span>上传失败{{errorCount}}条</span>
                            </div>
                            <div style="overflow-y: auto;height: 400px">
                                <div v-for="error in errorList"  class="mt10">
                                    <span style="color: black">{{error.pictureName}}:</span>
                                    <span style="color:red;">{{error.message}}</span>
                                </div>
                            </div>

                        </div>
                        <span slot="footer" class="dialog-footer">
                    <el-button type="primary" @click="closeDialog">关 闭</el-button>
                    </span>

                    </el-dialog>
                </div>
            </div>
        </div>

    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/goodsPicture.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>