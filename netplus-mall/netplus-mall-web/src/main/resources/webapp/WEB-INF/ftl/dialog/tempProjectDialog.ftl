<!-- 部门选择 -->
<el-dialog
        title="选择工程项目单"
        :visible.sync="showProjectDialog"
        :close-on-click-modal="false"
        :lock-scroll="false"
        width="900px"
        center>
    <div class="mb10 pt10">
        <!-- 输入框 -->
        <!-- 筛选 -->
        <frame-filter
                      :do-search="getProjectNo"
                      :data="filters2"
                      :conf="filtersConfig2">
        </frame-filter>

        <el-table
                :data="projectNoList"
                max-height="295"
                class="table_main_style mt20"
                style="width: 100%">
            <el-table-column
                    label="项目名称"
                    width="100px">
                <template scope="scope">
                    {{scope.row.xmmc}}
                </template>
            </el-table-column>

            <el-table-column
                    label="项目编号"
                    >
                <template scope="scope">
                    {{scope.row.xmbh}}
                </template>
            </el-table-column>

            <el-table-column
                    label="费用/资产"
                    >
                <template scope="scope">
                    {{scope.row.fyzc|getFeeTypeName}}
                </template>
            </el-table-column>

            <el-table-column
                    label="业主单位"
            >
                <template scope="scope">
                    {{scope.row.yzdw_rs}}
                </template>
            </el-table-column>

            <el-table-column
                    label="项目分类"
            >
                <template scope="scope">
                    {{scope.row.glfl|getProjectTypeName}}
                </template>
            </el-table-column>

            <el-table-column
                    prop="name"
                    label="操作"
                    >
                <template scope="scope">
                    <el-button v-if="updateProjectType == 'A'" @click="selectProject(scope.row)" type="text" size="small">选择</el-button>
                    <el-button v-if="updateProjectType == 'B'" @click="batchSelectProject(scope.row)" type="text" size="small">选择</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="page">
            <el-pagination
                    :current-page="projectNoPageIndex"
                    :page-size="projectNoPageSize"
                    :total="projectNoTotalCount"
                    :page-sizes="[10, 30, 50, 100]"
                    @current-change="projectNoPageChange"
                    @size-change="projectNoSizeChange"
                    layout="total, sizes, prev, pager, next"
                    class="fr"
                    background>
            </el-pagination>
        </div>
    </div>

    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeProjectDialog">关 闭</el-button>
    </span>
</el-dialog>