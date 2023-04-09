<!-- 员工选择 -->
<el-dialog
        title="员工选择"
        :visible.sync="userDialogVisible"
        :close-on-click-modal="false"
        :lock-scroll="false"
        width="757px"
        center>
    <div class="mb10 pt10">
        <!-- 输入框 -->
        <div>
            员工编号或员工姓名:
            <el-input style="width: 50%;"
                      v-model="userSearchKey"></el-input>
            <el-button type="primary" @click="userDialogQuery(1,10)"><i class="iconfont icon-zoom_x"></i>
                搜索
            </el-button>
        </div>
        <el-table
                :data="userDialogList"
                max-height="295"
                class="table_main_style"
                style="width: 100%">
            <el-table-column
                    label="部门编号"
                    width="100px">
                <template scope="scope">
                    {{scope.row.depno}}
                </template>
            </el-table-column>

            <el-table-column
                    label="部门名"
                    width="200px">
                <template scope="scope">
                    {{scope.row.deptName}}
                </template>
            </el-table-column>
            <el-table-column
                    label="员工编号"
                    width="100px">
                <template scope="scope">
                    {{scope.row.userno}}
                </template>
            </el-table-column>

            <el-table-column
                    label="员工姓名"
                    width="200px">
                <template scope="scope">
                    {{scope.row.cname}}
                </template>
            </el-table-column>

            <el-table-column
                    prop="name"
                    label="操作"
                    width="100px">
                <template scope="scope">
                    <el-button @click="getUser(scope.row)"
                               type="text" size="small">选择
                    </el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="page">
            <el-pagination
                    :current-page="pageIndexUser"
                    :page-size="pageSizeUser"
                    :total="totalCountUser"
                    :page-sizes="[10, 30, 50, 100]"
                    @current-change="pageChangeUser"
                    @size-change="sizeChangeUser"
                    layout="total, sizes, prev, pager, next"
                    class="fr"
                    background>
            </el-pagination>
        </div>
    </div>

    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeUserDialog">关 闭</el-button>
    </span>
</el-dialog>