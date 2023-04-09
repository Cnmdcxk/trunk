<!-- 招标范围-->
<el-dialog
        title="选择招标范围"
        :visible.sync="showScopeDialog"
        :close-on-click-modal="false"
        :lock-scroll="false"
        width="800px"
        center>
    <div class="mb10 pt10">

        <frame-filter :do-search="searchScope" :conf="filtersConfig3"> </frame-filter>

        <el-table :data="dataList3" style="width: 100%">

            <el-table-column label="供货范围">
                <template scope="scope">
                    {{scope.row.tendarea}}
                </template>
            </el-table-column>


            <el-table-column
                    prop="name"
                    label="操作"
                    width="100px">
                <template scope="scope">
                    <el-button v-if="scope.row.isSelect !='Y'"  type="text" size="small" @click="toSelect(scope.row, 'B')">选择</el-button>
                    <el-button v-if="scope.row.isSelect =='Y'"  type="text" size="small" >已选</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="page">
            <el-pagination
                    :current-page="pageIndex3"
                    :page-size="pageSize3"
                    :total="totalCount3"
                    :page-sizes="[10, 30, 50, 100]"
                    @current-change="pageChange3"
                    @size-change="sizeChange3"
                    layout="total, sizes, prev, pager, next"
                    class="fr"
                    background>
            </el-pagination>
        </div>
    </div>

    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeScopeDialog">关 闭</el-button>
    </span>
</el-dialog>