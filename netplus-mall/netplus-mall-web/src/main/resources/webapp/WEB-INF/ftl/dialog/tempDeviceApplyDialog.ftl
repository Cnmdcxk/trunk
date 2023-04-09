<!-- 部门选择 -->
<el-dialog
        title="选择新增设备申请单"
        :visible.sync="showDeviceDialog"
        :close-on-click-modal="false"
        :lock-scroll="false"
        width="900px"
        center>
    <div class="mb10 pt10">

        <frame-filter
                :do-search="deviceSearch"
                :data="filters3"
                :conf="filtersConfig3">
        </frame-filter>

        <el-table
                :data="deviceInfoList"
                max-height="295"
                class="table_main_style"
                style="width: 100%">

            <el-table-column
                    label="新增设备申请单号"
                    width="120px">
                <template scope="scope">
                    {{scope.row.xzsbsqdbh}}
                </template>
            </el-table-column>

            <el-table-column
                    label="设备名称"
                    >
                <template scope="scope">
                    {{scope.row.sbmc}}
                </template>
            </el-table-column>

            <el-table-column
                    label="型号规格"
                   >
                <template scope="scope">
                    {{scope.row.ggxh}}
                </template>
            </el-table-column>

            <el-table-column
                    label="申报单位"
                    >
                <template scope="scope">
                    {{scope.row.sbdwmc}}
                </template>
            </el-table-column>

            <el-table-column
                    label="使用单位"
                    >
                <template scope="scope">
                    {{scope.row.sydwmc}}
                </template>
            </el-table-column>

            <el-table-column
                    label="资产/费用"
                    >
                <template scope="scope">
                    {{scope.row.zbxzcbz|getFlagName}}
                </template>
            </el-table-column>

            <el-table-column
                    label="OA审批状态"
            >
                <template scope="scope">
                    {{scope.row.shzt|getOAStatusName}}
                </template>
            </el-table-column>

            <el-table-column
                    label="是否已使用"
            >
                <template scope="scope">
                    {{scope.row.sfxz|getUseName}}
                </template>
            </el-table-column>

            <el-table-column
                    prop="name"
                    label="操作"
                    >
                <template scope="scope">
                    <el-button @click="selectDevice(scope.row)" type="text" size="small">选择</el-button>
                </template>
            </el-table-column>
        </el-table>
    </div>

    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeDeviceDialog">关 闭</el-button>
    </span>
</el-dialog>