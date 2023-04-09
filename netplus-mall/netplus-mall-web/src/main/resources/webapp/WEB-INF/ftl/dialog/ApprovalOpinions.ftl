<el-dialog title="查看审批意见" :visible.sync="showDialogUpdateApply1" width="370px" center :close-on-click-modal="false">
  <div class="w_90 margin">
    <el-form
      label-position="center"
      ref="addForm"
      class="my_form"
      label-width="100px"
    >
      <el-form-item style="margin-left: -100px;">
        <div class="pb10 lh34">{{auditUser}}-{{updateuser}} 的审批意见</div>
        <textarea rows="2" class="pt10 pl10 fl" v-model="rejectReason" disabled></textarea>
      </el-form-item>
    </el-form>
  </div>
  <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="showDialogUpdateApply1 = false">关闭</el-button>
    </span>
</el-dialog>