<el-dialog title="查看申请" :visible.sync="showDialogUpdateApply" width="370px" center :close-on-click-modal="false">
    <div class="w_90 margin">

        <el-form
                label-position="center"
                ref="addForm"
                class="my_form"
                label-width="100px"
        >
            <el-form-item label="申请理由：">
                <textarea rows="3" class="pt10 pl10" v-model="applyReason" disabled></textarea>
            </el-form-item>
        </el-form>
    </div>
    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="showDialogUpdateApply = false">关闭</el-button>
    </span>
</el-dialog>