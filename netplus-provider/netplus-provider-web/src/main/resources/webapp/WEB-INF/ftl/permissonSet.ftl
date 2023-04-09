<@override name="title">权限设置</@override>

<@override name="css"></@override>

<@override name="content">

<div class="bg_fff">

    <div class="w1200">
         <#include '/left-nav.ftl'>

        <div class="right_con" id="app" v-cloak>

            <div class="mt10 bg_fff of">
                <div class="w_50 fl">
                    <#--<div class="f14 mb10 p10 ">设置网页端</div>-->
                    <div class="mt10 w_90 ml20">
                        <button class="small_main_btn mb10" @click="openPrivilegeDialog">设置页面权限</button>
                        <div class="lh30 bd_ccc tc of">
                            <div class="of">
                                <p class="bg_f5f5f5 lh30 h30 tc w_50 fl">页面</p>
                                <p class="bg_f5f5f5 lh30 h30 tc w_50 fl">操作权限</p>
                            </div>
                            <div class="gundongLine" style="overflow-y: scroll;max-height: 300px;">
                                <div v-for="p in ownRolePrivilegeList">
                                    <p class="tc w_50 fl">{{p.name}}</p>
                                    <p class="w_50 fl">
                                        <span>暂无</span>
                                    </p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>

            <div class="clear pt25 mb20">
                <div class="db dis_f margin w200">
                    <button class="main_btn" @click="back()">返回</button>
                </div>
            </div>


            <!-- 设置页面权限 -->
            <el-dialog
                    title="设置页面权限"
                    :visible.sync="showPrivilegeDialog"
                    :close-on-click-modal="false"
                    width="630px"
                    center>

                <div class="bd-dc setCompanyType" style="width: 550px;max-height: 220px;overflow-y: auto;box-sizing: border-box;">
                    <table class="ml20 mt10 mb10" style="width: 485px;" v-for="p in rolePrivilegeList">
                        <tr>
                            <td class="w100" ><span>{{p.parentname}}:</span></td>
                            <td>
                                <div class="w mt10 f12">
                                    <el-checkbox class="w_30 mb15" v-for="pp in p.childprivilegelist" v-model="pp.isCheck">
                                        {{pp.name}}
                                    </el-checkbox>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>

                <div class="mt10 ">
                    <label class="c_blue"><input type="checkbox" v-model="isSelectAll"><i></i>全选</label>
                </div>

            <span slot="footer" class="dialog-footer">
               <el-button type="primary" @click="save">保存</el-button>
               <el-button @click="showPrivilegeDialog = false">取消</el-button>
            </span>

            </el-dialog>

        </div>
    </div>
</div>


</@override>

<@override name="js">
     <script>
         var roleCode="${roleCode!''}";
     </script>
     <script type="text/javascript" src="${cdn}/js/provider/permissonSet.js?v=${ver}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>