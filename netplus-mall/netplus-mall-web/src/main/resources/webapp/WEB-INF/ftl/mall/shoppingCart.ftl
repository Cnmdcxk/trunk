<@override name="title">购物车</@override>

<@override name="css">
<link rel="stylesheet" href="${cdn}/css/car.css">

<style>
    .w_90 .el-input .el-input__inner{
        height:32px;
    }
    .table_bd_top{
        line-height:50px;
    }
    #bz .el-dialog__footer {
        padding: 0 20px 10px;
    }
    textarea::-webkit-scrollbar{
        width: 0px;
    }
</style>
</@override>


<@override name="content">

<div id="RZ" class="" v-cloak>
    <div class="logo">
        <div class="w1200">
            <div class="logo_img">
                <a href="/"><img src="${cdn}/img/logo/logo_000.png" alt="" class="fl" width="252x"></a>

                <div class="of fr">
                    <div class="car_step">
                        <div class="car_step_item curr">1. 加入购物车</div>
                        <div class="car_step_item">2. 确定订单信息</div>
                        <div class="car_step_item">3. 生成订单</div>
                        <div class="car_step_item">4. 完成</div>
                    </div>
                </div>

            </div>
        </div>
    </div>

    <!--	内容  -->
    <div class="bg_fff">
        <div class="w1200" style="margin-top: -10px;">

            <!-- 展开折叠 -->
            <div class="tr mb10" @click="changeOpen">
                <span class="c_blue cur f12" v-if="isOpen">收起筛选 <i class="iconfont icon-up"></i></span>
                <span class="c_blue cur f12" v-else>展开搜索 <i class="iconfont icon-down"></i></span>
            </div>

            <!-- 筛选 -->
            <frame-filter v-show="isOpen"
                          :do-search="search"
                          :data="filters"
                          :conf="filtersConfig">
            </frame-filter>
            <span  style="margin-left: 170px;font-size: 18px;color: #FF0000">数量：指物资库存上限扣除仓库库存、二级库库存以及已报计划未到货的部分，若需要调整上限，请联系设备处徐红。</span>
            <div class="pb50">
                <table class='w tc mt10' style="border-collapse:separate; border-spacing:0px 10px;">
                    <colgroup>
                        <col style="width: 150px;">
                        <col style="width: 200px;">
                        <col style="width: 90px;">
                        <col style="width: 90px;">
                        <col style="width: 120px;">
                        <col style="width: 120px;">
                        <col style="width: 120px;">
                        <col style="width: 110px;">
                        <col style="width: 100px;">
                        <col style="width: 100px;">
                    </colgroup>
                    <tr class="fb table_bd_top">
                        <td class="tl">
                            <el-checkbox v-model="isSelectedAll" class="ml10" @change="selectAll"></el-checkbox> 全选
                        </td>
                        <td>商品信息</td>

                        <td>单价（元）</td>
                        <td>同类最低价</td>

                        <td><span class="c_red">*</span>数量</td>
                        <td>是否需要图纸</td>

                        <td><span class="c_red">*</span>工程项目单</td>
                        <td>
                            <div>新增设备申请单</div>
                            <div>工装设备简号</div>
                        </td>

                        <td>小计</td>
                        <td>操作</td>
                    </tr>
                </table>
                <div class="w gundongLine" style="overflow-y: scroll;-ms-overflow-y:scroll;height:600px;">

                    <table  class="tc lh30 f14" style="border-collapse:separate; border-spacing:0px 10px;" v-for="s in shoppingCartList">
                        <colgroup>
                            <col style="width: 150px;">
                            <col style="width: 200px;">
                            <col style="width: 90px;">
                            <col style="width: 90px;">
                            <col style="width: 120px;">
                            <col style="width: 120px;">
                            <col style="width: 120px;">
                            <col style="width: 110px;">
                            <col style="width: 100px;">
                            <col style="width: 100px;">
                        </colgroup>
                        <tr>
                            <td colspan="14">
                                <div class="tl lh30 mt10">
                                    <el-checkbox v-model="s.checked"  class="ml10" @change=selectGroup(s)></el-checkbox>
                                    <i class="iconfont icon-shangcheng  c_blue"></i> {{s.suppliername}}
                                </div>
                            </td>
                        </tr>

                        <!-- 蓝色 -->
                        <tr class="car_bg_blue"
                            :class="{'car_bg_fail': ss.cartGoodStatus == 'N'}"
                            v-for="ss in s.tbmqq433VoList"
                        >

                            <td>
                                <div class="tl">
                                    <span v-if="ss.cartGoodStatus == 'N'" class="shixiao fl">失效</span>
                                    <el-checkbox v-if="ss.cartGoodStatus != 'N'" v-model="ss.checked" @change=selectSingle(ss) class="ml10 fl mt30"></el-checkbox>
                                    <img style="cursor: pointer" :src="ss.pictureurl" @click="goDetail(ss.goodid)" class="w100 h100 ml10 fl">
                                </div>
                            </td>

                            <td>
                                <div class="f14 w200 tl">
                                    <div class="tl lh20 of" style="max-height:60px;">{{ss.productname}}／{{ss.spec}}</div>
                                    <div class="mt15">物料条码：{{ss.matrltm}}</div>
                                    <div class="mt15">物料编码：{{ss.matrlno}}</div>
                                    <div class="mt15">商品备注：{{ss.remark}}
                                        <i class="iconfont c_orange icon-biji" @click="editRemark(ss)"></i>
                                    </div>
                                </div>
                            </td>
                            <td>￥{{ss.price}}/{{ss.qtyunit}}</td>

                            <td>
                                <span class="f16 c_green fb">￥{{ss.lowPrice}}/{{ss.lowQtyUnit}}</span>
                            </td>
                            <td>
                                <div class="pr">
                                        <el-input-number
                                                size="small"
                                                v-model="ss.qty"
                                                class="w120"
                                                size="small"
                                                :min="ss.minbuyqty > 0 ? ss.minbuyqty: 1"
                                                @change="changeQty(ss)"
                                                :step-strictly="ss.isinttimepurchase == 'Y'"
                                                :step="ss.minbuyqty > 0 ? ss.minbuyqty: 1"
                                                :disabled="ss.cartGoodStatus == 'N'"
                                        >
                                        </el-input-number>

                                        <span class="c_red" v-if="ss.remainQty>0">可申报上限{{ss.remainQty}}</span>
                                    <span class="c_red" v-else>可申报上限0</span>
                                </div>
                            </td>

                            <td>
                                <div class="w_90 tc">
                                    <el-select v-if="ss.haspic == '1'"  placeholder="请选择" v-model="ss.isneedpic" @change="changePic($event, ss)">
                                        <el-option v-for="item in selectPicList" :label="item.value" :value="item.key" :key="item.key"></el-option>
                                    </el-select>

                                    <span v-if="ss.haspic != '1'">无图纸</span>
                                </div>
                            </td>

                            <td>
                                <div class="w_90 tc">
                                    <el-input @clear="clear(ss)" v-model="ss.projectNameAndNo" @focus="openProjectDialog($event, ss.goodid)" placeholder="请选择"></el-input>
                                </div>
                            </td>

                            <td>
                                <div class="w_90 tc">
                                    <el-input @clear="clear2(ss)" :disabled="ss.projectno != 'SC00' " v-model="ss.deviceapplyno" style="margin-bottom:5px" @focus="openDeviceDialog($event, ss.goodid)"></el-input>
                                    <el-input @clear="clear2(ss)" @blur.native.capture="updateDeviceSimpleNo(ss)" v-model="ss.devicesimpleno"></el-input>
                                </div>
                            </td>

                            <td>
                                <span class="f16 c_red fb">￥{{ss.amt}}</span>
                            </td>
                            <td>
                                <button class="c_333" v-if="ss.cartGoodStatus != 'N'" @click="addMyCollect(ss.goodid)">移入收藏夹</button> <br>
                                <button class="c_333" @click="delGood([ss.goodid])">删除</button> <br>
                                <button class="c_333" v-if="ss.cartGoodStatus != 'N'" @click="getPriceCompare(ss)">同类比价</button>
                            </td>

                        </tr>
                    </table>
                </div>
                <!-- 去结算 -->
                <table table class="w tc lh30 f14" style="border-collapse:separate; border-spacing:0px 10px;">
                    <colgroup>
                        <col style="width: 600px;">
                        <col style="width: 600px;">
                    </colgroup>

                    <tr class="car_bottom">
                        <td class="tl" style="width: 800px">
                            <el-checkbox v-model="isSelectedAll" @change="selectAll" class="ml10"></el-checkbox>  全选
                            <a class="ml10 c_blue" @click="delSelected">删除选中的商品</a>
                            <a class="ml10 c_blue" @click="delInvalid">清除失效商品</a>
                            <a class="ml10 c_blue" @click="openImportDialog">excel导入商品</a>
                            <a class="ml10 c_blue" @click="openProjectDialog2">选择工程项目单</a>
                            <a class="ml10 c_blue" @click="checkQty">校验库存</a>
                            <a class="ml10 c_blue" @click="exportExcel">信息Excel导出</a>
                        </td>

                        <td>
                            <div class="fr">
                                <span class="">已选 {{selectedData.length}}行，{{selectedData.length}}件商品</span>
                                <span class="ml30">含税总价</span>
                                <span class="f24 c_red">￥{{selectedTotalAmt}}</span>
                                <button class="btn_orange f22 fr ml30" @click="toSettle">去结算</button>
                            </div>
                        </td>
                    </tr>
                </table>


            </div>
        </div>
    </div>

    <el-dialog
            title="购物车excel导入商品"
            :visible.sync="showImportDialog"
            width="380px"
            :close-on-click-modal="false"
            center>
        <div class="pt40">
            <div>
                <i class="iconfont c_orange icon-tishi"></i>您可先下载excel模板，填写信息后上传
                <a class="c_blue" href="${cdn}/excelTemp/购物车导入模版.xlsx">下载模板</a>
            </div>
            <div class="tc mt20 pb25">
                上传文件：
                <el-upload
                        class="upload-demo w100 disib"
                        style="display:inline;"
                        ref="upload"
                        :auto-upload="false"
                        multiple=false
                        :limit="1"
                        accept=".xls,.xlsx"
                        action="/api/v2/fileupload/doUpload/"
                        :before-upload="beforeUpload"
                        :on-success="handleSuccess"
                        :on-error="handleError"
                >
                    <button class="main_fff_self_btn"> <i class="iconfont icon-shangchuan1 f16"></i> 选择文件</button>
                </el-upload>
            </div>
        </div>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="upload" >确 定</el-button>
                <el-button @click="closeImportDialog">取 消</el-button>
            </span>
    </el-dialog>


    <el-dialog
            title="提示"
            :visible.sync="showImportWarningDialog"
            width="420px"
            :close-on-click-modal="false"
            center>
        <div class="pt40">
            <i class="iconfont c_orange icon-tishi"></i>
            您共导入{{importTotalCount}}条商品，其中{{importErrCount}}条商品信息异常(<a class="c_blue" @click="exportErrInfo">下载报错明细</a>)
        </div>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="continueSave" >继 续</el-button>
                <el-button @click="showImportWarningDialog = false">取 消</el-button>
            </span>
    </el-dialog>

    <el-dialog title="编辑商品备注"
               :visible.sync="showEditRemark"
               :close-on-click-modal="false"
               :lock-scroll="false"
               width="320px"
               id="bz"
               center>
        <div class="lh30 pt5">
            <div class="of">
                <el-input type="textarea" class="fl"
                          :autosize="{ minRows: 4, maxRows: 5}"
                          v-model="remark"
                          maxlength="500"
                          resize="none"
                          rows="5"
                          style="padding: 0 0 35px 0"
                          show-word-limit>
                </el-input>
            </div>
        </div>
        <span slot="footer" class="dialog-footer" style="padding: 0px 20px 20px">
            <el-button type="primary" @click="updateItemRemark">确定</el-button>
            <el-button @click="showEditRemark = false">取消</el-button>
        </span>
    </el-dialog>

    <#include '../dialog/tempProjectDialog.ftl'>
    <#include '../dialog/tempDeviceApplyDialog.ftl'>
    <#include '../dialog/priceComparisonDialog.ftl'>

</div>
</@override>

<@override name="js">
<script>
    var cdn = '${cdn!}';
</script>
<script src="${cdn}/js/framework/components/filters.js"></script>
<script src="${cdn}/js/mall/shoppingCart.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>