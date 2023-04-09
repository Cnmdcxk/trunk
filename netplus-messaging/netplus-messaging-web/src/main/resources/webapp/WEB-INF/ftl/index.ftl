<@override name="title">消息中心</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/login.css?v=${ver}">
<style type="text/css">
    [v-cloak]{
        display:none;
    }
</style>
</@override>

<@override name="content">
<div class="w1200">
    <!-- 左侧菜单 -->
    <#include "/left-nav.ftl">
    <!-- 右侧内容 -->
    <div class="right_con" id="RZ">
        <div class="right_top">
            <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
            <span class="c_blue fl ml10">消息中心</span>
            <span class="fr c_blue mr10"  @click="removeAll()" style="cursor:pointer"><i class="iconfont icon-qingchu"></i>一键已读</span>
        </div>
        <!-- 消息中心 -->
        <div class="p10">
            <ul class="w290 fl message_center">
                <li v-for="groupKV in myMsgGroupList" :class="(pageCode == groupKV.key ? 'curr' : '')">
                    <div class="w270">
                        <img src="${cdn}/img/message/img-1.png" alt="" width="26px" height="26px" class="fl ml15 mr10">
                        <span class="disib w180 f15 fl" @click="init(groupKV.key)" style="cursor:pointer">{{groupKV.value}}<i class="bg_red_num ml10" v-if="countList[groupKV.key] != null && countList[groupKV.key] != 0">{{countList[groupKV.key]}}</i></span>
                    </div>
                </li>
                <div class="clear"></div>
            </ul>
            <div class="fl ml5" style="min-height:550px;width: calc(100% - 296px);border-left: 1px solid #DEDEDE;">
                <div class="mt20" v-for="item in list">
                    <p class="tc f12 c_666 mb15">{{item.receivetime}}</p>
                    <div class="of">
                        <img src="${cdn}/img/message/img-1.png" alt="" width="26px" height="26px" class="fl ml15 mr10">
                        <div class="fl message_right" v-if="item.isread == '0'">
                            <i class="c_red iconfont icon-yuandian disib w7 fl fb f16 mt_5"></i>
                            <div @click="removeDot(item.id,item.url)">
                                <p class="c_blue f15" @click="removeDot(item.id)">{{item.messagetypeStr}}</p>
                                <p v-html="item.messagecontent"></p>
                            </div>
                        </div>
                        <div class="fl message_right" v-else>
                            <p class="c_blue f15">{{item.messagetypeStr}}</p>
                            <p v-html="item.messagecontent"></p>
                        </div>
                    </div>
                </div><br/>
                <button @click="clickMore(pageIndex)" style="display:block;margin:0 auto" class="tc f12 c_666 mb15">查看更多</button>
            </div>
            <div class="clear"></div>
        </div>

    </div>


</div>
</@override>

<@override name="js">
    <script>
        var pageCode = '${pageCode!}';
    </script>
    <script src="${cdn}/js/messaging/index.js?v=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>