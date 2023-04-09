<el-dialog title="同类商品"
           :visible.sync="showSimilarDialog"
           :close-on-click-modal="false"
           :lock-scroll="false"
           width="1000px"
           center>
    <div>
        <el-table :data="goodsData" class="table_main_style">
            <el-table-column label="商品信息">
                <template slot-scope="scope">
                    <img style="cursor: pointer" :src="scope.row.pictureurl" @click="goDetail(scope.row.goodid)" class="w100 h100 ml10 fl">
                </template>
            </el-table-column>
            <el-table-column label="供应商">
                <template slot-scope="scope">
                    <div>{{scope.row.suppliername}}</div>
                </template>
            </el-table-column>

            <el-table-column label="交货周期">
                <template slot-scope="scope">
                    <div>{{scope.row.leadtimenum}}个工作日</div>
                </template>
            </el-table-column>

            <el-table-column label="含税单价">
                <template slot-scope="scope">
                    <div>￥{{scope.row.price}}/{{scope.row.qtyunit}}</div>
                </template>
            </el-table-column>
            <el-table-column label="订购数量" >
                <template slot-scope="scope">

                    <el-input-number
                            size="small"
                            v-model="scope.row.qty"
                            class="w100"
                            :min="scope.row.minbuyqty > 0 ? scope.row.minbuyqty: 1"
                            :step-strictly="scope.row.isinttimepurchase == 'Y'"
                            :step="scope.row.minbuyqty > 0 ? scope.row.minbuyqty: 1"
                    >

                    </el-input-number>
                </template>
            </el-table-column>

            <el-table-column label="操作" >

                <template slot-scope="scope">
                    <button class="main_btn_red ml10" @click.stop="addCart(scope.row)">
                        <i class="iconfont icon-gouwuche"></i> 加入购物车
                    </button>
                </template>

            </el-table-column>
        </el-table>
    </div>

    <span slot="footer" class="dialog-footer">
        <el-button type="primary" @click="showSimilarDialog = false">关 闭</el-button>
    </span>
</el-dialog>