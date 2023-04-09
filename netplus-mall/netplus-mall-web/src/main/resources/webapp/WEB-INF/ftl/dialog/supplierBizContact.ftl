<el-dialog title="供应商联系方式"
           :visible.sync="showSupplierBizContact"
           :close-on-click-modal="false"
           :lock-scroll="false"
           width="320px"
           center>
    <div class="lh30">
        <div>联系人：{{supplierBizContact.BIZCONTACT}}</div>
        <div>联系方式：{{supplierBizContact.BIZCONTACTPHONE}}</div>
    </div>

    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="showSupplierBizContact = false">关 闭</el-button>
    </span>
</el-dialog>