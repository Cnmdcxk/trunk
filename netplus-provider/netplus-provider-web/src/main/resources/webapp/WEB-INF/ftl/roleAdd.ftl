<@override name="title">权限设置</@override>

<@override name="css">
</@override>
<@override name="content">

    <!-- 头部菜单 2-->
    <!-- 内容区域 -->
    <div class="bg_fff">
        <div class="w1200">
            <!-- 左侧菜单 -->
            <#include '/left-nav.ftl'>
            <!-- 右侧内容 -->
            <div class="right_con" id="RZ" v-cloak>

                    <div class="mt10 bg_fff of">
                        <div class="w_50 fl">
                            <div class="f14 mb10 p10 ">2、设置网页端</div>
                            <div class="mt10 w_90 ml20">
                                <button class="small_main_btn mb10"
                                        @click="centerDialogVisible = true">设置页面权限</button>
                                <div class="lh30 bd_ccc tc of">
                                    <div class="of">
                                        <p class="bg_f5f5f5 lh30 h30 tc w_50 fl">页面</p>
                                        <p class="bg_f5f5f5 lh30 h30 tc w_50 fl">操作权限</p>
                                    </div>
                                    <div class="gundongLine" style="overflow-y: scroll;max-height: 300px;">
                                        <div v-for="item in 10">
                                            <p class="tc w_50 fl">页面名称1</p>
                                            <p class="w_50 fl">
                                                <span>暂无</span>
                                                <button @click="centerDialogVisible1 = true" class="blue_txt_btn ml10">设置</button>
                                            </p>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="clear pt25 mb20">
                        <div class="db dis_f margin w200">
                            <button class="main_btn mr10">保存</button>
                            <button class="main_fff_btn">取消</button>
                        </div>
                    </div>

                </div>


                <!-- 设置页面权限 -->
                <el-dialog
                        title="设置页面权限"
                        :visible.sync="centerDialogVisible"
                        :close-on-click-modal="false"
                        width="630px"
                        center>

                    <div class="bd-dc setCompanyType" style="width: 550px;max-height: 220px;overflow-y: auto;box-sizing: border-box;">
                        <table class="ml20 mt10 mb10" style="width: 485px;" v-for="item in tableData" :key="item.code">
                            <tr>
                                <td class="w90" ><span>{{item.name}}</span></td>
                                <td>
                                    <div class="w mt10 f12">
                                        <el-checkbox
                                                class="w_30 mb15"
                                                v-model="subitem.isCheck"
                                                v-if="item.children&&item.children.length"
                                                v-for="(subitem,i) in item.children"
                                                @change="changeSelect(subitem)"
                                                :key="i"
                                        >
                                            {{subitem.name}}

                                        </el-checkbox>
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>

                    <div class="mt10 ">
                        <label class="c_blue"><input type="checkbox"><i></i>全选</label>
                    </div>

                    <span slot="footer" class="dialog-footer">
                        <el-button type="primary" @click="centerDialogVisible1 = false">保存</el-button>
                        <el-button @click="centerDialogVisible1 = false">取消</el-button>
                    </span>
                    
                </el-dialog>
            </div>
        </div>
</div>
</@override>
<@override name="js">
    <script src="${cdn}/js/provider/roleAdd.js?v=${ver!}"></script>
</@override>
<@extends name="/base-rgpur2.ftl"/>


