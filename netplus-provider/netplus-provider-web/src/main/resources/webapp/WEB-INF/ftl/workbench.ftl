<@override name="title">供方工作平台</@override>

<@override name="css">
<link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
<style type="text/css">
    [v-cloak]{
        display:none;
    }
</style>
</@override>

<@override name="content">

    <div class="">
        <!-- 内容区域 -->
        <div class="bg_fff">
            <div class="w1200">
                <#include "/left-nav.ftl">
                <!-- 右侧内容 -->
                <div class="right_con" id="RZ" v-cloak>
                    <div class="right_top">
                        <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                        <span class="c_blue fl ml10">供方工作平台</span>
                    </div>
                    <div class="p10">
                        <div class="bd_gray" >
                            <div class="tl pb20 of mt25">
                               <div>
                                <img src="${cdn}/img/home/banner_img5.png" alt="" width="90px" class="fl ml25 mr25">
                               </div>

                                <div class="fl lh30 pt5" style="margin-top:12.5px">
                                    <div class="fb">{{currentUser.companyName}}</div>
                                    <div class="fb">{{currentUser.userCode}}</div>
                                    <div class="c_blue cur" v-if="currentUser.bizContact == null || currentUser.bizContact.trim() == ''" @click="showBizContactsDialog">完善联系人信息</div>
                                    <div class="of" v-else><span class="mr20">联系人：{{currentUser.bizContact}}</span> <span class="mr20">联系方式：{{currentUser.bizContactPhone}}</span> <span class="c_blue cur" @click="showBizContactsDialog">修改</span> </div>
                                </div>
                            </div>
                        </div>
                        <div class="of mt10">
                            <!-- 消息通知 -->
                            <div class="bd_gray w500 mr10 fl">
                                <div class="forecast-name">
                                    <div class="title">
                                        <div class="ml20 fl"> 消息通知( <span class="c_red">{{countMessage}}</span> )</div>
                                        <a class="fr mr30 c_999" href="/messaging/index">More</a>
                                    </div>
                                    <div class="pl15 pr15 pb20 f10 gundongLine"  style="height:190px;overflow-y:auto;">
                                        <div class="list_two" v-for="item in MessageList">
                                            <div v-if="item.isread == '0'">
                                                <div class="fl W320 txt_of" @click="removeDot(item.id,item.messagegroup)"  style="cursor:pointer">
                                                    <i class="c_red iconfont icon-yuandian disib w7 fb f16 mt_5"></i>
                                                    <span v-html="item.messagecontent" style="display: inline;">{{item.messagecontent}}</span>
                                                </div>
                                                <div class="fr c_999">{{item.receivetime}}</div>
                                            </div>
                                            <div v-else>
                                                <div class="fl W320 txt_of" @click="goMessageIndex(item.messagegroup)"  style="cursor:pointer">
                                                    <i style="width:7px;display: inline-block"></i>
                                                    <span v-html="item.messagecontent" style="display: inline;">{{item.messagecontent}}</span>
                                                </div>
                                                <div class="fr c_999">{{item.receivetime}}</div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- 我的待办 -->
                            <div class="bd_gray w500 fl ">
                                <div class="forecast-name">
                                    <div class="title">
                                        <div class="ml20 fl"> 我的待办</div>
                                    </div>
                                    <div class="pl15 pr15 h210 dealt-with of pb10 pt5" style="height:190px">
                                        <div class="item">
                                            <a href="/mall/supplier/saleOrder?orderStatus=15">
                                            <img src="${cdn}/img/home/banner_icon7.png" alt="">
                                            <div>待接单(<span class="c_red">{{countawait}}</span>)</div>
                                            </a>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                    <!--  完善联系人信息  -->
                    <el-dialog :title="showContactDialogTitle"
                            :visible.sync="showContactsDialog"
                            :close-on-click-modal="false"
                            :lock-scroll="false"
                            width="320px"
                            center>
                         <div class="lh30 pt5">
                            <div class="of">
                                <span class="w80 tr c_666">联系人:</span>
                                <input type="text" v-model="bizContact" placeholder="请输入">
                            </div>
                            <div class="of">
                                <span class="w80 tr c_666">联系方式:</span>
                                <input type="text" v-model="bizContactPhone" placeholder="请输入">
                            </div>
                        </div>

                        <span slot="footer" class="dialog-footer">
                            <el-button type="primary" @click="updateBizContact">确定</el-button>
                            <el-button @click="showContactsDialog = false">取消</el-button>
                        </span>
                    </el-dialog>


                </div>
            </div>
        </div>
    </div>
</@override>

<@override name="js">
<script type="text/babel" src="${cdn}/js/provider/workbench.js?v=${ver}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>