<@override name="title">买方工作台</@override>

<@override name="css">
<link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
</@override>

<@override name="content">

    <div class="w1200">

            <!-- 左侧菜单 -->
            <#include "/left-nav.ftl">

            <!-- 右侧内容 -->
            <div class="right_con" id="RZ" v-cloak>
                <div class="right_top">
                    <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
                    <span class="c_blue fl ml10">买方工作台</span>
                </div>

                <div class="p10">
                    <div class="bd_gray">
                        <div class="tl pb20 of mt25">
                            <img src="${cdn}/img/home/banner_img3.png" alt="" width="90px" class="fl ml25 mr25">
                            <div class="fl lh30 pt5">
                                <div class="fb">{{currentUser.companyName}} <span>{{currentUser.deptName}}</span></div>
                                <div class="fb">{{currentUser.userCode}}</div>
                                <div class="fb">{{currentUser.username}}</div>
                            </div>
                        </div>
                    </div>

                    <div class="of mt10">
                        <!-- 消息通知 -->
                        <div class="bd_gray w500 mr10 fl">
                            <div class="forecast-name">
                                <div class="title">
<#--                                    ( <span class="c_red">{{countMessage}}</span> )-->
                                    <div class="ml20 fl"> 消息通知( <span class="c_red">{{countMessage}}</span> )</div>
                                    <a class="fr mr30 c_999" @click="gotoMessageBox()">More</a>
                                </div>
                                <div class="pl15 pr15 pb20 f10"  style="height:190px;overflow-y:auto;">
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
                                    <div class="ml20 fl"> 我的待办( <span class="c_red">{{quickListNumSum}}</span> )</div>
                                </div>
                                <div class="pl15 pr15 h210 dealt-with of pb10 pt5" style="height:190px">
                                    <div class="item" v-if="myItems.PG0022">
                                        <a @click="Listing()">
                                            <img src="${cdn}/img/home/banner_icon5.png" alt="">
                                            <div>待上架确认(<span class="c_red">{{quickListNum.toApproveNum}}</span>)</div>
                                        </a>

                                    </div>
                                    <div class="item" v-if="myItems.PG0044">
                                        <a @click="below()">
                                            <img src="${cdn}/img/home/banner_icon6.png" alt="">
                                            <div>待下架确认(<span class="c_red">{{quickListNum.toEntrustNum}}</span>)</div>
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                </div>

            </div>


        </div>


</@override>

<@override name="js">
<script type="text/javascript" src="${cdn}/js/provider/purWorkbench.js?v=${ver}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>