<!-- 报名/报价不在投标范围 -->
<el-dialog
        title="友情提醒："
        :visible.sync="tipsDialog"
        width="500px"
        :close-on-click-modal="false"
        center>
    <div class="pt40 tl f14 lh22">
        <#--<p style="text-indent: 2em;">为满足客户需求，改善客户的投标体验，我公司对采购电商平台进行</p>-->

        <#--优化升级，实现多种浏览器兼容运行，增加招投标实时交互、视频讲标等功能。-->
        <#--目前<span class="fb">辅材</span>、<span class="fb">工程</span>与<span class="fb">劳务</span>模块已升级完成，将于2021年3月1日投用，-->
        <#--参与此类项目的客户请通过新网址https://ep2.rizhaosteel.com/进行投标，-->
        <#--其它类别的项目以及3月1日之前发布的辅材、工程与劳务项目仍通过旧网址-->
        <#--<a href="https://ep.rizhaosteel.com" class="c_blue" target="_blank">https://ep.rizhaosteel.com</a>-->
        <#--进行投标，升级版投用时间另行通知。-->
        <#--<p style="text-indent: 2em;">感谢合作！</p>-->


        <#--<p style="text-indent: 2em;">为满足客户需求，改善客户的投标体验，我公司对采购电商平台进行</p>-->
        <#--优化升级，实现多种浏览器兼容运行，增加招投标实时交互、视频讲标等功能。-->
        <#--<span class="fb">辅材</span>、<span class="fb">工程</span>与<span class="fb">劳务</span>模块已于2021年3月1日升级完成并正式投用，-->
        <#--参与此类项目的客户，对发布于3月1日之后的项目，-->
        <#--请通过新网址https://ep2.rizhaosteel.com/进行投标，-->
        <#--发布于3月1日之前的仍通过旧网址<a href="https://ep.rizhaosteel.com" class="c_blue" target="_blank">https://ep.rizhaosteel.com</a>进行投标。-->
        <#--目前<span class="fb">备件</span>、<span class="fb">设备</span>与<span class="fb">合金</span> 模块已于2021年3月14日升级完成并正式投用， 参与此类项目的客户，-->
        <#--对发布于3月14日之后的项目，请通过新网址https://ep2.rizhaosteel.com/进行投标，-->
        <#--发布于3月14日之前的仍通过旧网址 <a href="https://ep.rizhaosteel.com" class="c_blue" target="_blank">https://ep.rizhaosteel.com</a> 进行投标。-->
        <#--新、旧平台升级合并时间将另行通知。-->
        <#--<p style="text-indent: 2em;">感谢合作！</p>-->

            <p style="text-indent: 2em;">为满足客户需求，改善客户的投标体验，我公司对采购电商平台进行</p>
            优化升级，实现多种浏览器兼容运行，增加招投标实时交互、视频讲标等功能。请通过新平台进行投标及供应商注册，
            原有的<span class="fb">进厂原料预报</span>和<span class="fb">采购业务协同</span>功能
            可在新平台登录后通过相应菜单栏跳转登录后进行操作。
            <p style="text-indent: 2em;">感谢合作！</p>


    </div>
    <span slot="footer" class="dialog-footer">
         <el-button @click="tipsDialog = false">OK</el-button>
     </span>
</el-dialog>