<!-- 部门选择 -->
<el-dialog
        title="部门选择"
        :visible.sync="deptDialogVisible"
        :close-on-click-modal="false"
        :lock-scroll="false"
        width="657px"
        center>
    <div class="mb10 pt10">
        <!-- 输入框 -->
        <div>
            部门编号或部门名:
            <el-input style="width: 50%;" v-model="deptSearchKey"></el-input>
            <el-button type="primary" @click="deptDialogQuery(1,10)">
                <i class="iconfont icon-zoom_x"></i>搜索
            </el-button>
        </div>

        <el-table
                :data="deptDialogList"
                max-height="295"
                class="table_main_style"
                style="width: 100%">
            <el-table-column
                    label="部门编号"
                    width="150px">
                <template scope="scope">
                    {{scope.row.depno}}
                </template>
            </el-table-column>

            <el-table-column
                    label="部门名"
                    width="350px">
                <template scope="scope">
                    {{scope.row.depname}}
                </template>
            </el-table-column>

            <el-table-column
                    prop="name"
                    label="操作"
                    width="100px">
                <template scope="scope">
                    <el-button @click="getDeptUser(scope.row)" type="text" size="small">选择</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="page">
            <el-pagination
                    :current-page="pageIndexDept"
                    :page-size="pageSizeDept"
                    :total="totalCountDept"
                    :page-sizes="[10, 30, 50, 100]"
                    @current-change="pageChangeDept"
                    @size-change="sizeChangeDept"
                    layout="total, sizes, prev, pager, next"
                    class="fr"
                    background>
            </el-pagination>
        </div>
    </div>

    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeUserDeptDialog">关 闭</el-button>
    </span>
</el-dialog>