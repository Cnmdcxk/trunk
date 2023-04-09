<@override name="title">商城采购订单详情</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/shop.css" />
</@override>

<@override name="content">

    <div id="RZ" class="" v-cloak>
        <!-- 内容区域 -->
        <div class="bg_f3f3f3">
            <div class="w1200">
                <div class="right_top" style="border-left: 1px solid #dedede;border-right: 1px solid #dedede;">
                    <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                    <span class="c_blue fl ml10"><a class="c_blue fl ml10" href="/mall/purchaseOrder" style="cursor:pointer">商城采购订单</a>  <i class="iconfont icon-right"></i> 采购订单详情</span>
                </div>
                <!-- 基本信息 -->
                <div class="base-info bg_fff mb10 mt10">
                    <div class="of pb10">
                        <!-- 左 -->
                        <div class="fl pb10 w400">
                            <div class="base-title color1 f16">
                                <span class="ml10">订单号：</span><span class="">{{orderInfo.orderno}}</span>
                                <span class="red_bg_txt">{{orderInfo.statusName}}</span>
                            </div>
                            <div class="of lh30 ml20">
                                <div class="dis_flex"><span class="c_999 w100">订单类型：</span><span>商城订单</span></div>
                                <div class="dis_flex"><span class="c_999 w100">未税金额：</span><span>￥ {{orderInfo.notaxtotalamt|toFixed(2)}}</span></div>
                                <div class="dis_flex"><span class="c_999 w100">含税金额：</span><span class="c_red f15">￥ {{orderInfo.totalamt}}</span></div>
                                <div class="dis_flex"><span class="c_999 w100">创建时间：</span><span>{{orderInfo.createdate|strDateFormat}} {{orderInfo.createtime|strTimeFormat}}</span></div>
                            </div>
                        </div>

                        <!-- 右边 -->
                        <div class="fr w800 mt20" style="border-left: 1px solid #dedede;">
                            <div class="process_detail">

                                <div class="process_detail_item curr">
                                    <i class="iconfont icon-xinzeng"></i>
                                    <span class="right_icon">&gt;</span>
                                    <div class="txt">审核中</div>
                                </div>

                                <div class="process_detail_item curr" v-if="orderInfo.status == 20">
                                    <i class="iconfont icon-weituoshenpi"></i>
                                    <span class="right_icon">&gt;</span>
                                    <div class="txt">已驳回</div>
                                </div>

                                <div class="process_detail_item" v-if="orderInfo.status != 20" v-bind:class="{'curr': orderInfo.status >= 15}">
                                    <i class="iconfont icon-hetong-1"></i>
                                    <span class="right_icon">&gt;</span>
                                    <div class="txt">待接单</div>
                                </div>

                                <div class="process_detail_item" v-if="orderInfo.status != 20" v-bind:class="{'curr': orderInfo.status >= 25}">
                                    <i class="iconfont icon-93shouhuo"></i>
                                    <span class="right_icon">&gt;</span>
                                    <div class="txt">已接单</div>
                                </div>

                                <div class="process_detail_item" v-if="orderInfo.status != 20" v-bind:class="{'curr': orderInfo.status >= 30}">
                                    <i class="iconfont icon-hedui"></i>
                                    <span class="right_icon">&gt;</span>
                                    <div class="txt">已完结</div>
                                </div>

                            </div>
                            <div class="mt20 tc">
                                <button class="blue_txt_btn f14" @click="exportOrderDetail">导出订单明细</button>
                                <button class="c_blue ml10 blue_txt_btn f14" @click="viewApproveProgress(orderInfo.approvecode)">查看审批进度</button>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 合同主信息 -->
                <div class="base-info bg_fff mb10">
                    <div class="base-title color2 f16">
                        <i v-bind:class="{'icon-add_bd':add1,'icon-reduce_bd':reduce1}" class="cur iconfont c_blue f16 ml10" @click="display(1)"></i>
                        <span class="ml10">订单主信息</span>
                    </div>
                    <div class="of pb20" v-if="display1">
                        <div class="fl w_33">
                            <div class="of lh30 ml20">
                                <div class="dis_flex"><span class="c_999 w100">采购单位：</span><span>{{orderInfo.buyername}} </span></div>
                                <div class="dis_flex"><span class="c_999 w100">供应商：</span><span>{{orderInfo.suppliername}}</span></div>
                                <#--<div class="dis_flex"><span class="c_999 w100">付款方式：</span> <span>{{orderInfo.payMethodName}}</span></div>-->
                                <div class="dis_flex"><span class="c_999 w100">合同号：</span><span>{{orderInfo.erporderno}}</span></div>
                            </div>
                        </div>
                        <div class="fl w_33">
                            <div class="of lh30 ml20">
                                <div class="dis_flex"><span class="c_999 w100">采购部门：</span><span>{{orderInfo.deptname}}</span></div>
<#--                                <div class=dis_flex"><span class="c_999 w100">税率：</span> <span>{{orderInfo.tax}}</span></div>-->
                                <div class="dis_flex"><span class="c_999 w100">协议号：</span> <span>{{orderInfo.pono}}</span></div>
                                <div class="dis_flex"><span class="c_999 w100">创建人：</span><span>{{orderInfo.createUserName}} </span></div>
                            </div>
                        </div>
                        <div class="fl w_33">
                            <div class="of lh30 ml20">
                                <#--<div class="dis_flex"><span class="c_999 w100">付款类别：</span><span>{{orderInfo.payTypeName}}</span></div>-->
                                <div class="dis_flex"><span class="c_999 w120">第三方平台订单号：</span> <span>{{orderInfo.thrplatorderno}}</span></div>
                                <#--<div class="dis_flex"><span class="c_999 w120">供应商联系方式：</span> <span @click="showBizContact(orderInfo.supplierno)"><a class="c_blue">查看</a></span></div>-->
                                <div class="of dis_flex">
                                    <span class="c_999 w100 fl">买方留言：</span>
                                    <span class="fl w250">{{orderInfo.buyernote}}</span>
                                </div>
                            </div>
                        </div>

                    </div>
                </div>

                <!-- 其他信息 -->
                <div class="base-info bg_fff mb10">
                    <div class="base-title color3 f16">
                        <i v-bind:class="{'icon-add_bd':add2,'icon-reduce_bd':reduce2}" class="cur iconfont c_blue f16 ml10" @click="display(2)"></i>
                        <span class="ml10">其他信息</span>
                    </div>
                    <div class="of pb20" v-if="display2">
<#--                        <div class="fl w_33">-->
<#--                            <div class="of lh30 ml20">-->
<#--                                <div><span class="c_999 w100">收货人：</span><span>{{orderInfo.consigneename}}</span></div>-->
<#--                                <div><span class="c_999 w100">收货电话：</span><span> {{orderInfo.consigneephone}}</span></div>-->
<#--                                <div class="of"><span class="c_999 w100 fl">收货地址：</span> <span class="w270">{{orderInfo.consigneeaddr}}</span>-->
<#--                                </div>-->
<#--                            </div>-->
<#--                        </div>-->
                        <div class="fl w_33">
                            <div class="of lh30 ml20">
                                <div class="dis_flex"><span class="c_999 w105">收货人：</span><span>{{orderInfo.consigneename}}</span></div>
                                <div class="dis_flex"><span class="c_999 w105">收货人联系方式：</span><span>{{orderInfo.consigneephone}}</span></div>
                                <div class="of dis_flex"><span class="c_999 w105 fl">收货地址：</span> <span class="w270">{{orderInfo.consigneefulladdr}}</span>
                                </div>
                            </div>
                        </div>
                        <div class="fl w_33">
                            <div class="of lh30 ml20">
                                <div class="dis_flex"><span class="c_999 w100">发票抬头：</span><span>{{orderInfo.invoicetitle}}</span></div>
                                <div class="dis_flex"><span class="c_999 w100"> 纳税人识别号：</span><span>{{orderInfo.suppliertaxno}}</span></div>
                                <div class="of dis_flex"><span class="c_999 w100 fl">地址、电话：</span> <span class="fl w270">{{orderInfo.supplieraddr}} {{orderInfo.supplierphone}}</span>
                                </div>
                                <div class="of dis_flex"><span class="c_999 w100 fl">开户行及账号：</span> <span class="fl w270">{{orderInfo.supplierbankname}} {{orderInfo.suppliercardno}}</span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 商品明细 -->
                <div class="base-info bg_fff mb10">
                    <div class="base-title color4 f16">
                        <i v-bind:class="{'icon-add_bd':add3,'icon-reduce_bd':reduce3}" class="cur iconfont c_blue f16 ml10" @click="display(3)"></i>
                        <span class="ml10">商品明细</span>
                    </div>
                    <div class="p10" v-if="display3">
                        <!-- 表格 -->
                        <el-table :data="tableData" class="table_main_style">
                            <el-table-column label="商品信息" width="240">
                                <template slot-scope="scope">
                                    <div @click="goDetail(scope.row.goodid)"  style="cursor: pointer" class="tl">

                                        <img :src="scope.row.pictureurl != null && scope.row.pictureurl.trim() != '' ? scope.row.pictureurl : '/statics/img/shop/noPic.png'" class="fl mt10 ml10 mb10" alt="" width="68px" height="68px">

                                        <div class="tl w140 fl p10">
                                            <div>{{scope.row.productname}}{{scope.row.brand != null && scope.row.brand.trim() != '' ?'/'+scope.row.brand:''}}</div>
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

                            <el-table-column prop="date" label="价格" width="100">
                                <template slot-scope="scope">
                                    <div class="tc">
                                        <div><span class="c_red">￥{{scope.row.price}}/{{scope.row.qtyunit}}</span></div>
                                        <div class="f10 c_999">
                                            <div>未税：￥{{scope.row.notaxprice}}/{{scope.row.qtyunit}}</div>
                                            <div>税率：{{scope.row.tax | formatTax}}%</div>
                                        </div>
                                    </div>
                                </template>
                            </el-table-column>




                            <el-table-column prop="data" label="工程项目单" width="100">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.projectname}}{{scope.row.projectno}}</div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="data" label="新增设备申请单" width="100">
                                <template slot-scope="scope">
<#--                                    <div class="">{{scope.row.deviceapplyno}}</div>-->
                                        <el-input v-model="scope.row.deviceapplyno" disabled></el-input>
                                </template>
                            </el-table-column>
                            <el-table-column label="工装设备简号" width="100" style="margin-right: 5px">
                                <template slot-scope="scope">
<#--                                    <div class="">{{scope.row.devicesimpleno}}</div>-->
                                        <el-input
                                                v-model="scope.row.devicesimpleno"
                                                :disabled="true"
                                                style="width: 80%;">
                                        </el-input>
                                </template>
                            </el-table-column>
                            <el-table-column prop="data" label="交货周期" width="100">
                                <template slot-scope="scope">
                                    <div class="" v-if="scope.row.leadtimenum > 0">{{scope.row.leadtimenum}}个工作日</div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="data" label="是否需要图纸">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.isneedpic == 'Y'?'是':'否'}}</div>
                                </template>
                            </el-table-column>







                            <el-table-column prop="data" label="订单数量">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.qty}}</div>
                                </template>
                            </el-table-column>

                            <#--<el-table-column prop="data" label="收货人" width="100">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.username2}}</div>
                                </template>
                            </el-table-column>

                            <el-table-column prop="data" label="收货人联系方式" width="100">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.userno2phone}}</div>
                                </template>
                            </el-table-column>-->

                            <el-table-column prop="data" label="发货数量">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.deliqty}}</div>
                                </template>
                            </el-table-column>

                            <el-table-column prop="date" label="收货数量">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.takedeliqty}}</div>
                                </template>
                            </el-table-column>

                            <el-table-column prop="date" label="开票数量">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.invoiceqty}}</div>
                                </template>
                            </el-table-column>
                            <el-table-column prop="date" label="金额">
                                <template slot-scope="scope">
                                    <div class="c_red">￥{{scope.row.amt}}</div>
                                </template>
                            </el-table-column>

                            <el-table-column prop="statusName" label="状态">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.statusName}}</div>
                                </template>
                            </el-table-column>

                            <el-table-column prop="agent" label="物资采购人">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.agent}}</div>
                                </template>
                            </el-table-column>

                            <el-table-column prop="date" label="高价物资说明">
                                <template slot-scope="scope">
                                    <div class="">{{scope.row.remark}}</div>
                                </template>
                            </el-table-column>

                            <el-table-column prop="date" label="商品备注">
                                <template slot-scope="scope">
                                    <button class="blue_txt_btn" @click="editRemark(scope.row.remark2)">
                                        查看
                                    </button>
                                </template>
                            </el-table-column>

                            <el-table-column prop="date" label="快递单号">
                                <template slot-scope="scope">
                                    <button class="blue_txt_btn" @click="toExpressDetail(expressNo)" v-for="expressNo in scope.row.expressNoList">{{expressNo}}</button>
                                </template>
                            </el-table-column>

                            <el-table-column prop="date" label="操作">
                                <template slot-scope="scope">
                                    <button class="blue_txt_btn" v-if="scope.row.isAddCart == 'Y'">已加入购物车</button>
                                    <button class="blue_txt_btn" @click="addCart(scope.row)" v-else>加入购物车</button>
                                    <div v-if="scope.row.status == '30'
                                            || scope.row.status == '35'"
                                    >
                                        <button class="blue_txt_btn" @click="writeComment(scope.row)" v-if="scope.row.hasComment != '1'">评价</button>
                                        <button class="blue_txt_btn" @click="showMyComment(scope.row)" v-else>我的评价</button>
                                    </div>
                                </template>
                            </el-table-column>

                        </el-table>


                        <!-- 分页 -->
                        <div class="page">
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

                        <div class="tr clear pb20 lh25">

                            <div>{{totalCount}} 件商品
                                <span class="pl10 pr10">未税金额</span>
                                <span class="f14 ml20 c_red">￥{{orderInfo.notaxtotalamt|toFixed(2)}}</span>
                            </div>

                            <div>
                                <span class="pl10 pr10">含税金额</span>
                                <span class="f14 ml20 c_red">￥{{orderInfo.totalamt}}</span>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- 评价 -->
                <el-dialog
                        title="评价"
                        :visible.sync="commentDialogVisible"
                        width="400px"
                        :close-on-click-modal="false"
                        center>
                    <div class="pt10 pb20">
                        <div class="of">
                            <img :src="comment.pictureurl != null && comment.pictureurl.trim()?comment.pictureurl:'/statics/img/shop/noPic.png'" alt="" width="80" height="80" class="bd mr10 fl">
                            <div style="width: calc(100% - 85);">
                                <div class="lh25 f14">
                                    {{comment.productname}}
                                </div>
                                <div class="mt10">物料编码：{{comment.matrlno}}</div>
                                <div class="mt10">物料条码：{{comment.matrltm}}</div>
                            </div>
                        </div>
                        <div class="of mt30">
                            <span class="w50 tl fl">评分</span>
                            <el-rate class="fl" v-model="comment.score" void-icon-class="iconfont icon-xingxing" void-color="#FDF2F3" :colors="['#D92437','#D92437','#D92437']"></el-rate>
                        </div>
                        <div class="mt20">
                            <span class="w50 tl fl">晒图</span>
                            <span v-for="(imgUrl,index) in urlList" class="w50 h50 bd mr10 fl pr">
                                <img :src="imgUrl" alt="" class="w50 h50">
                                <i @click="removeImg(index)" class="pa iconfont icon-cuowu c_red cur" style="right: -5px;top:-5px;"></i>
                            </span>
                            <el-upload
                                    v-if="uploadList.length<3"
                                    ref="upload"
                                    class="upload-demo change_upload_style2 fl w50 h50 lh50 tc bd bg_f5f5f5"
                                    action="/api/v2/fileupload/doUpload/"
                                    multiple
                                    limit="3"
                                    :on-change="handleChange"
                                    :show-file-list = "true"
                                    :file-list="uploadList"
                                    :before-upload="beforeUpload"
                                    :on-success="handleSuccess"
                                    accept=".png,.jpg,.jpeg"
                            >
                                <i class="iconfont icon-jia"></i>
                            </el-upload>
                            <span class="fl mt30 ml10">{{uploadList.length}}/3</span>
                        </div>
                        <div class="mt10 of clear">
                            <span class="w50 tl fl">评价</span>
                            <el-input type="textarea" class="fl" style="width: calc(100% - 55px);"
                                      :autosize="{ minRows: 4, maxRows: 5}"
                                      v-model="comment.commentcontent"
                                      maxlength="150"
                                      show-word-limit>
                            </el-input>
                        </div>
                    </div>
                    <span slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="createComment">确 定</el-button>
                        <el-button @click="commentDialogVisible = false">取 消</el-button>
                    </span>
                </el-dialog>

                <!-- 我的评价 -->
                <el-dialog
                        title="我的评价"
                        :visible.sync="myCommentDialogVisible"
                        width="400px"
                        :close-on-click-modal="false"
                        center>
                    <div class="pt10 pb20">
                        <div class="of">
                            <img :src="comment.pictureurl != null && comment.pictureurl.trim() ? comment.pictureurl : '/statics/img/shop/noPic.png'" alt="" width="80" height="80" class="bd mr10 fl">
                            <div style="width: calc(100% - 85);">
                                <div class="lh25 f14">
                                    {{comment.productname}}
                                </div>
                                <div class="mt10">物料编码：{{comment.matrlno}}</div>
                                <div class="mt10">物料条码：{{comment.matrltm}}</div>
                            </div>
                        </div>
                        <div class="of mt30">
                            <span class="w50 tl fl">评分</span>
                            <el-rate disabled class="fl" v-model="comment.score" void-icon-class="iconfont icon-xingxing" void-color="#FDF2F3" :colors="['#D92437','#D92437','#D92437']"></el-rate>
                        </div>
                        <div class="of mt20">
                            <span class="w50 tl fl">晒图</span>
                            <img :src="imgUrl" alt="" class="w50 h50 bd mr10 fl" v-for="imgUrl in comment.images">
                        </div>
                        <div class="mt10 of">
                            <span class="w50 tl fl">评价</span>
                            <span class="fl" style="width: calc(100% - 55px);" >
                                    {{comment.commentcontent}}
                            </span>
                        </div>
                    </div>
                    <span slot="footer" class="dialog-footer">
                        <el-button @click="myCommentDialogVisible = false">关 闭</el-button>
                    </span>
                </el-dialog>

                <!-- 查看商品备注 -->
                <el-dialog title="查看商品备注"
                           id="bz"
                           :visible.sync="showEditRemark"
                           :close-on-click-modal="false"
                           :lock-scroll="false"
                           width="320px"
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
                                      disabled
                                      show-word-limit>
                            </el-input>
                        </div>
                    </div>
                    <span slot="footer" class="dialog-footer" style="padding: 0px 20px 20px">
                        <el-button @click="showEditRemark = false">关闭</el-button>
                    </span>
                </el-dialog>

                <!-- 查看审批进度 -->
                <el-dialog
                        :visible.sync="approveProgressVisible"
                        width="540px"
                        title="查看审批进度"
                        :close-on-click-modal="false"
                        center>
                    <div class="p10">
                        <div class="approval-progress">
                            <div class="approval-progress-title of">
                                <span class="w_20">审批人</span>
                                <span class="w_20">审批时间</span>
                                <span style="width: calc(60% - 30px);">审批意见</span>
                            </div>
                            <div class="approval-progress-item" v-bind:class="{'curr': index == 0}" v-for="(item,index) in approveProgressList">
                                <div class="w_20 fl tc mr5">{{item.approveName}}</div>
                                <div class="w_20 fl tc mr5">
                                    {{item.approveTime == null ? '' : item.approveTime.substr(0,10)}}
                                    <div class="f12">
                                        {{item.approveTime == null ? '' : item.approveTime.substr(11,8)}}
                                    </div>
                                </div>
                                <div class="approval-progress-item-icon">
                                    <i class="iconfont icon-up"></i>
                                </div>
                                <div class="fl" style="width: calc(60% - 90px);">
                                    <div class="approval-progress-item-content">
                                        <div class="title lh25">{{item.approveResult}}</div>
                                        <div class="lh20">{{item.approveOpinion}}</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <span slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="approveProgressVisible = false">确 定</el-button>
                        <el-button @click="approveProgressVisible = false">取 消</el-button>
                    </span>
                </el-dialog>
                <#include '../dialog/supplierBizContact.ftl'>
            </div>
        </div>

    </div>
</@override>


<@override name="js">
    <script>var orderNo = '${orderNo!}'</script>
    <script type="text/javascript" src="${cdn}/js/mall/purchaseOrderDetail.js"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>