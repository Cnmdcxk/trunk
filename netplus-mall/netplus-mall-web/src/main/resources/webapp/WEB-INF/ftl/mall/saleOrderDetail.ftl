<@override name="title">商城销售订单详情</@override>

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
                <span class="c_blue fl ml10"><a class="c_blue fl ml10" href="/mall/supplier/saleOrder" style="cursor:pointer">商城销售订单</a><i class="iconfont icon-right"></i> 销售订单详情</span>
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
                            <div><span class="c_999 w100">订单类型：</span><span>商城订单</span></div>
                            <div><span class="c_999 w100">未税金额：</span><span>￥ {{orderInfo.notaxtotalamt|toFixed(2)}}</span></div>
                            <div><span class="c_999 w100">含税金额：</span> <span class="c_red f15">￥ {{orderInfo.totalamt}}</span></div>
                            <div><span class="c_999 w100">创建时间：</span><span>{{orderInfo.createdate|strDateFormat}} {{orderInfo.createtime|strTimeFormat}}</span></div>
                        </div>
                    </div>

                    <!-- 右边 -->
                    <div class="fr w800 mt20" style="border-left: 1px solid #dedede;">
                        <div class="process_detail">
                            <div class="process_detail_item curr">
                                <i class="iconfont icon-hetong-1"></i>
                                <span class="right_icon">&gt;</span>
                                <div class="txt">待接单</div>
                            </div>

                            <div class="process_detail_item" v-bind:class="{'curr': orderInfo.status >= 25}">
                                <i class="iconfont icon-93shouhuo"></i>
                                <span class="right_icon">&gt;</span>
                                <div class="txt">已接单</div>
                            </div>

                            <div class="process_detail_item" v-bind:class="{'curr': orderInfo.status >= 30}">
                                <i class="iconfont icon-hedui"></i>
                                <span class="right_icon">&gt;</span>
                                <div class="txt">已完结</div>
                            </div>
                        </div>

                        <div class="mt20 tc">
                            <button class="blue_txt_btn" @click="exportOrderDetail">导出订单明细</button>
                            <button class="c_blue ml10 blue_txt_btn f14" v-if="orderInfo.status == '15'" @click="confirmDialogVisible = true">确认接单</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 合同主信息 -->
            <div class="base-info bg_fff mb10">
                <div class="base-title color2 f16">
                    <i v-bind:class="{'icon-add_bd':add1,'icon-reduce_bd':reduce1}" class="cur iconfont  c_blue f16 ml10" @click="display(1)"></i>
                    <span class="ml10">订单主信息</span>
                </div>
                <div class="of pb20" v-if="display1">
                    <div class="fl w_33">
                        <div class="of lh30 ml20">
                            <div><span class="c_999 w100">采购单位：</span><span>{{orderInfo.buyername}} </span></div>
                            <div><span class="c_999 w100">供应商：</span><span>{{orderInfo.suppliername}}</span></div>
                            <div class="of">
                                <span class="c_999 w100 fl">买方留言：</span>
                                <span class="fl w270">{{orderInfo.buyernote}}</span>
                            </div>
                        </div>
                    </div>
                    <div class="fl w_33">
                        <div class="of lh30 ml20">
                            <div><span class="c_999 w100">采购部门：</span><span>{{orderInfo.deptname}}</span></div>
                            <div><span class="c_999 w100">协议号：</span> <span>{{orderInfo.pono}}</span></div>
                            <div><span class="c_999 ">第三方平台订单号：</span> <span>{{orderInfo.thrplatorderno}}</span></div>
                        </div>
                    </div>
                    <div class="fl w_33">
                        <div class="of lh30 ml20">
                            <div><span class="c_999 w100">创建人：</span><span>{{orderInfo.createUserName}} </span></div>
                            <div><span class="c_999 w100">合同号：</span><span>{{orderInfo.erporderno}}</span></div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 其他信息 -->
            <div class="base-info bg_fff mb10">
                <div class="base-title color3 f16">
                    <i v-bind:class="{'icon-add_bd':add2,'icon-reduce_bd':reduce2}" class="cur iconfont  c_blue f16 ml10" @click="display(2)"></i>
                    <span class="ml10">其他信息</span>
                </div>
                <div class="of pb20" v-if="display2">
<#--                    <div class="fl w_33">-->
<#--                        <div class="of lh30 ml20">-->
<#--                            <div><span class="c_999 w100">收货人：</span><span>{{orderInfo.consigneename}}</span></div>-->
<#--                            <div><span class="c_999 w100">收货电话：</span><span> {{orderInfo.consigneephone}}</span></div>-->
<#--                            <div class="of"><span class="c_999 w100 fl">收货地址：</span> <span class="w270">{{orderInfo.consigneeaddr}}</span>-->
<#--                            </div>-->
<#--                        </div>-->
<#--                    </div>-->
                    <div class="fl w_33">
                        <div class="of lh30 ml20">
                            <div><span class="c_999 w105">收货人：</span><span>{{orderInfo.consigneename}}</span></div>
                            <div><span class="c_999 w105">收货人联系方式：</span><span>{{orderInfo.consigneephone}}</span></div>
                            <div class="of"><span class="c_999 w105 fl">收货地址：</span> <span class="w270">{{orderInfo.consigneefulladdr}}</span></div>
                        </div>
                    </div>
                    <div class="fl w_33">
                        <div class="of lh30 ml20">
                            <div><span class="c_999 w100">发票抬头：</span><span>{{orderInfo.invoicetitle}}</span></div>
                            <div><span class="c_999 w100"> 纳税人识别号：</span><span>{{orderInfo.suppliertaxno}}</span></div>
                            <div class="of"><span class="c_999 w100 fl">地址、电话：</span> <span class="fl w270">{{orderInfo.supplieraddr}} {{orderInfo.supplierphone}}</span>
                            </div>
                            <div class="of"><span class="c_999 w100 fl">开户行及账号：</span> <span class="fl w270">{{orderInfo.supplierbankname}} {{orderInfo.suppliercardno}}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <!-- 商品明细 -->
            <div class="base-info bg_fff mb10">
                <div class="base-title color4 f16">
                    <i v-bind:class="{'icon-add_bd':add3,'icon-reduce_bd':reduce3}" class="cur iconfont  c_blue f16 ml10"  @click="display(3)"></i>
                    <span class="ml10">商品明细</span>
                </div>
                <div class="p10" v-if="display3">
                    <!-- 表格 -->
                    <el-table :data="tableData" class="table_main_style" >
                        <el-table-column label="商品信息" width="240">
                            <template slot-scope="scope">
                                <div class="tl">

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

                        <el-table-column prop="date" label="快递单号">
                            <template slot-scope="scope">
                                <button class="blue_txt_btn" @click="toExpressDetail(expressNo)" v-for="expressNo in scope.row.expressNoList">{{expressNo}}</button>
                            </template>
                        </el-table-column>

                        <el-table-column prop="date" label="操作">
                            <template slot-scope="scope">
                                <div v-if="scope.row.status == '25'"><button class="blue_txt_btn" @click="deliveryAppointment(scope.row)">交货预约</button></div>
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

            <el-dialog
                    :visible.sync="confirmDialogVisible"
                    width="290px"
                    title="确认接单"
                    :close-on-click-modal="false"
                    center>
                <div class="p20">
                    <div class="f18 tc of w200 margin">
                        <i class="f30 iconfont c_blue icon-wenhao  fl ml30" style="margin-top:-2px;"></i>
                        <span class="fl"> 是否确认接单</span>
                    </div>
                </div>
                <span slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="receivingOrder">确 定</el-button>
                        <el-button @click="confirmDialogVisible = false">取 消</el-button>
                    </span>
            </el-dialog>
        </div>
    </div>

</div>
</@override>


<@override name="js">
<script>var orderNo = '${orderNo!}'</script>
<script type="text/javascript" src="${cdn}/js/mall/saleOrderDetail.js"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>