<!-- 招标范围-->
<el-dialog
        title="选择供应商"
        :visible.sync="showSupplierDialog"
        :close-on-click-modal="false"
        :lock-scroll="false"
        width="800px"
        center>
    <div class="mb10 pt10">
        <frame-filter :do-search="searchSupplier" :conf="filtersConfig2"></frame-filter>

        <el-table :data="dataList2" style="width: 100%">
            <el-table-column label="供应商编码">
                <template scope="scope">
                    {{scope.row.supplierno}}
                </template>
            </el-table-column>
            <el-table-column label="供应商名称" >
                <template scope="scope">
                    {{scope.row.fullname}}
                </template>
            </el-table-column>

            <el-table-column
                    prop="name"
                    label="操作"
                    width="100px">
                <template scope="scope">
                    <el-button v-if="scope.row.isSelect !='Y'"  type="text" size="small" @click="toSelect(scope.row, 'A')">选择</el-button>
                    <el-button v-if="scope.row.isSelect =='Y'"  type="text" size="small" >已选</el-button>
                </template>
            </el-table-column>
        </el-table>

        <div class="page">
            <el-pagination
                    :current-page="pageIndex2"
                    :page-size="pageSize2"
                    :total="totalCount2"
                    :page-sizes="[10, 30, 50, 100]"
                    @current-change="pageChange2"
                    @size-change="sizeChange2"
                    layout="total, sizes, prev, pager, next"
                    class="fr"
                    background>
            </el-pagination>
        </div>
    </div>

    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="closeSupplierDialog">关 闭</el-button>
    </span>
</el-dialog>