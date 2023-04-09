<@override name="title">商城</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/shop.css" />
    <link rel="stylesheet" href="${cdn}/css/car.css" />
</@override>


<@override name="content">

    <div id="RZ" class="" v-cloak>
        <div class="logo">
            <div class="w1200">
                <div class="logo_img">
                    <a href="/"><img src="${cdn}/img/logo/logo_000.png" alt="" class="fl" width="252x"></a>

                    <div class="of fr">
                        <div class="car_step">
                            <div class="car_step_item old">1. 加入购物车</div>
                            <div class="car_step_item curr">2. 确定订单信息</div>
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

                <div class="bg_blue_title mb20">采购主信息</div>
                <el-form class="my-ruleForm" label-width="140px">

                    <div class="of">
                        <el-form-item label="采购单位：">
                            <div>{{companyName}}</div>
                        </el-form-item>

                        <el-form-item label="采购部门：">
                            <div>{{deptName}}</div>
                        </el-form-item>

                        <el-form-item label="创建人：">
                            <div>{{username}}</div>
                        </el-form-item>

                    </div>


                    <div class="of">

                        <el-form-item label="部门条线：" required>
                            <el-select v-model="selectedLine" @change="changeLine">
                                <el-option v-for="l in lineList" :label="l.bmtxmc" :key="l.bmtxbm_pk" :value="l.bmtxbm_pk"></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="一级审批人：" required>
                            <el-select v-model="selectedOne">
                                <el-option v-for="l in sprOne" :label="l.sprnameone" :key="l.sprcodeone" :value="l.sprcodeone"></el-option>
                            </el-select>
                        </el-form-item>

                        <el-form-item label="二级审批人：" required>
                            <el-select v-model="selectedTwo" :disabled="sprTwoDisabled">
                                <el-option v-for="l in sprTwo" :label="l.sprnametwo" :key="l.sprcodetwo" :value="l.sprcodetwo"></el-option>
                            </el-select>
                        </el-form-item>

                    </div>

                    <div class="of" v-if="showCostDept">
                        <el-form-item label="费用预算部门：" required>
                            <el-select v-model="selectedCostDept">
                                <el-option v-for="l in costDept" :label="l.mc" :key="l.bmbm_pk" :value="l.bmbm_pk"></el-option>
                            </el-select>
                        </el-form-item>
                    </div>

                </el-form>

                <div class="bg_blue_title">收货信息</div>

                <el-form class="my-ruleForm" label-width="140px">

                    <div class="of pt20" v-if="selectedAddr.addrtype != '1' ">

                        <el-form-item label="收货人：" required>
                            <div>
                                <el-select
                                        v-model="selectedConsignee"
                                        <#--filterable-->
                                        <#--remote-->
                                        placeholder="模糊查询"
                                        <#--:remote-method="getConsignee"-->
                                        @change="changeConsignee"
                                >
                                    <el-option
                                            v-for="item in consigneeList"
                                            :key="item.userno"
                                            :label="item.userno +'-'+ item.name"
                                            :value="item.userno"
                                    >
                                    </el-option>
                                </el-select>
                            </div>
                        </el-form-item>

                        <el-form-item label="收货人联系方式：" required>
                            <div><el-input v-model="phone" placeholder="请输入"></el-input></div>
                        </el-form-item>

                    </div>

                </el-form>

                <el-form class="my-ruleForm" label-width="140px">
                    <el-form-item label="收货地址：" required class="w clear mt20">
                        <div class="address gundongLine fl">
                            <div class="address_item"
                                 v-for="c in consigneeAddr"
                                 @click="changeAddr(c)"
                                 v-bind:class="{'curr': c.code == selectedAddr.code}"
                            >
                                <div>
                                    {{c.province}}{{c.city}}{{c.consigneeaddr}}
                                    <span v-if="c.addrtype == '1'">（总库）</span>
                                    <span v-if="c.addrtype == '2'">（快递点）</span>
                                </div>
                                <i class="iconfont icon-dui1"></i>
                            </div>
                        </div>
                    </el-form-item>
                </el-form>



                <div class="bg_blue_title mb20">
                    商品信息<a class="x_title ml10" @click="toCart">返回我的购物车修改</a>
                </div>

                <!-- table -->
                <table class="w tc mb20 f14">
                    <colgroup>
                        <col style="width: 300px;">
                        <col style="width: 100px;">
                        <col style="width: 110px;">
                        <col style="width: 50px;">
                        <col style="width: 100px;">
                        <col style="width: 180px;">
                        <col style="width: 180px;">
                        <col style="width: 100px;">
                        <col style="width: 100px;">
                        <col style="width: 80px;">
                        <col style="width: 50px;">
                    </colgroup>

                    <tr class="fb car_bg_blue">
                        <td><div>商品信息</div></td>
                        <td><div>单价（元）</div></td>
                        <td><div>同类最低价</div></td>

                        <td><div>数量</div></td>
                        <td><div>是否需要图纸</div></td>
                        <td><div>工程项目单</div></td>

                        <td>
                            <div class="of">新增设备申请单</div>
                            <div>工装设备简号</div>
                        </td>


                        <td><div>高价物资说明</div></td>
                        <td><div>商品备注</div></td>
                        <td><div>小计（元）</div></td>
                        <td><div>操作</div></td>
                    </tr>

                </table>

                <table class="w tc mb20 f14">
                    <colgroup>
                        <col style="width: 290px;">
                        <col style="width: 100px;">
                        <col style="width: 110px;">
                        <col style="width: 60px;">
                        <col style="width: 150px;">
                        <col style="width: 190px;">
                        <col style="width: 180px;">
                        <col style="width: 130px;">
                        <col style="width: 130px;">
                        <col style="width: 80px;">
                        <col style="width: 50px;">
                    </colgroup>
                    <tbody v-for="(g, i) in goodList">

                    <tr>
                        <td colspan="5">
                            <div class="tl lh40 pt10">
                                <i class="iconfont icon-shangcheng c_blue"></i>
                                {{g.suppliername}}
                            </div>
                        </td>
                    </tr>


                    <tr class="table_blue2" v-for="(gg, ii) in g.tbmqq433VoList">
                        <td>
                            <div class="of">
                                <img :src="gg.pictureurl" alt="" width="68" height="68" class="fl ml10">
                                <div class="w200 lh24 fl tl ml10">
                                    {{gg.productname}}／{{gg.spec}}
                                    <div>货号：{{gg.goodno}} </div>
                                    <div>物料条码：{{gg.matrltm}}</div>
                                    <div>物料编码：{{gg.matrlno}}</div>
                                </div>
                            </div>
                        </td>

                        <td>
                            <div style="display: flex;flex-direction: row;align-items: center">
                                <span>￥{{gg.price}}/{{gg.qtyunit}}</span>
                                <el-tooltip v-if="gg.futurePrice!= null"
                                            effect="light" placement="bottom"
                                            style="width: 5px;height: 5px;display: flex;align-items: center;margin-left: 5px">
                                    <div slot="content"  v-if="gg.futurePrice > gg.price" style="color: crimson">
                                        <span style="margin:10px 0 0 0;font-size: 13px">现有协议价格有效期在{{gg.futurePoPriceStartDate}}截止，</span>
                                        <br/>
                                        <span style="margin:10px 0;font-size: 13px">请尽快审核通过。</span>
                                    </div>
                                    <div slot="content" v-else>
                                        <span style="margin:10px 0 0 0;font-size: 13px">新协议价格{{gg.futurePrice}}元，</span>
                                        <br/>
                                        <span style="margin:10px 0;font-size: 13px">在{{gg.futurePoPriceStartDate}}生效。</span>
                                    </div>
                                    <i class="iconfont c_orange icon-tishi1" style="width: 5px;height: 5px"></i>
                                </el-tooltip>
                            </div>
                        </td>
                        <td>
                            <span class="f16 c_green fb">
                                ￥{{gg.lowPrice}}/{{gg.lowQtyUnit}}
                            </span>
                        </td>

                        <td>
                            {{gg.qty}}
                            <el-tooltip effect="dark" placement="top">
                                <div slot="content">
                                    <div class="c_red lh25">超出库存上限, 剩余可用库存{{gg.remainQty}}</div>
                                </div>
                                <span class="c_red iconfont icon-tishi" v-show="gg.isOutQty == '1'"></span>
                            </el-tooltip>

                        </td>
                        <td v-if="gg.isneedpic == 'Y'">是</td>
                        <td v-if="gg.isneedpic != 'Y'">否</td>
                        <td>{{gg.projectNameAndNo}}</td>


                        <td>
                            <div class="of">
                                <el-input v-model="gg.deviceapplyno" disabled style="width: 80%;margin-bottom:5px"></el-input>
                                <el-input v-model="gg.devicesimpleno" disabled style="width: 80%;"></el-input>
                            </div>
                        </td>
<#--                        <td>-->
<#--                            <el-input v-model="gg.deviceapplyno" disabled></el-input>-->
<#--                        </td>-->
<#--                        <td>-->
<#--                            <el-input v-model="gg.devicesimpleno" disabled></el-input>-->
<#--                        </td>-->
                        <td><el-input v-model="gg.remark" :disabled="gg.price==gg.lowPrice"></el-input></td>
                        <td>
                            <button class="c_333 blue_txt_btn" @click="editRemark(gg, ii, i)">查看</button>
                        </td>
                        <td>
                            {{gg.amt}}
                        </td>
                        <td><button class="c_333 blue_txt_btn" @click="delGood(gg.goodid)">删除</button></td>
                    </tr>

                    <tr class="table_blue3">
                        <td colspan="11">
                            <div class="of">
                                <span class="fl mt20 ml10">给卖家留言：</span>
                                <el-input
                                        type="textarea"
                                        class="fl w_50"
                                        placeholder="选填，对本次交易的补充说明（所填内容建议已经和卖家达成一致意见）"
                                        maxlength="85"
                                        show-word-limit
                                        v-model="g.buyerNote"
                                >
                                </el-input>
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>

                <div class="tr pb30">
                    <div class="mb20">{{goodList.length}}件商品 总计： <span class="c_red">￥{{totalAmt}}</span></div>
                    <div class="mb20">应付金额： <span class="c_red f24">￥{{totalAmt}}</span></div>
                    <div class="">
                        <button class="submit_btn" @click="createOrder">提交订单</button>
                    </div>
                </div>

            </div>
        </div>

        <el-dialog title="提示" :visible.sync="showCreateOrderDialog" width="240px" :close-on-click-modal="false" center :close-on-click-modal="false">
            <div class="pb10">
                <div class="tc f16"><i class="iconfont c_green icon-duihao1 f16 fb"></i> 您成功生成{{orderNoList.length}}个订单 </div>
                <div class="mt10 w_80 margin">
                    <div class="of lh25" v-for="o in orderNoList">
                        <span>{{o}}</span>
                        <a href="/mall/purchaseOrder" class="c_blue fr f12">查看</a>
                    </div>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="toShoppingCart">确定</el-button>
            </span>
        </el-dialog>
        <el-dialog title="编辑商品备注"
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
                              show-word-limit>
                    </el-input>
                </div>
            </div>
            <span slot="footer" class="dialog-footer" style="padding: 0px 20px 20px">
                    <el-button type="primary" @click="handleSave()">确定</el-button>
                    <el-button @click="showEditRemark = false">取消</el-button>
                </span>
        </el-dialog>

        <el-dialog
                title="提示"
                :visible.sync="showWarningDialog"
                width="380px"
                :close-on-click-modal="false"
                center>
            <div class="pt40">
                <i class="iconfont c_orange icon-tishi"></i>
                {{warningMessage}}
            </div>

            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="closeWarningDialog" >关 闭</el-button>
            </span>
        </el-dialog>

    </div>
</@override>

<@override name="js">
    <script>
        var goodIdList='${goodIdList!}';
        var companyName='${companyName!}';
        var deptNo = '${deptNo!}';
        var deptName='${deptName!}';
        var username='${username!}';
        var cdn = '${cdn!}';
    </script>

    <script type="text/babel" src="${cdn}/js/mall/checkout.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>