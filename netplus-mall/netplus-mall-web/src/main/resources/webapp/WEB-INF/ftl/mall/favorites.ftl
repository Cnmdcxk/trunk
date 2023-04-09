<@override name="title">收藏夹</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
    <link rel="stylesheet" type="text/css" href="${cdn}/css/shop.css"/>
    <link rel="stylesheet" href="${cdn}/js/3rd/swiper-6.6.2/swiper-bundle.min.css">
    <style type="text/css">
        [v-cloak]{
              display:none;
          }
        .goodsItem {
            position: relative;
        }
        .detail {
            height: 500px
        }
        .goodsItem .el-checkbox__input{
            position: absolute;
            right: 15px;
            top: 10px;
            white-space: normal;
        }
        .goodsItem .el-checkbox__label{
            padding-left:0;
        }
        .goodsItem .el-checkbox__input.is-checked+.el-checkbox__label{
            color: #333;
        }
        #bz .el-dialog__footer {
            padding: 0 20px 10px;
        }
        textarea::-webkit-scrollbar{
            width: 0px;
        }
    </style>
</@override>

<!-- 内容区域 -->
<@override name="content">
    <div class="w1200">
    <!-- 左侧菜单 -->
    <#include "/left-nav.ftl">

    <!-- 右侧内容 -->
    <div class="right_con" id="RZ" v-cloak>
        <div class="right_top">
            <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
            <span class="c_blue fl ml10">收藏夹</span>
        </div>
        <div class="p10">
            <!-- 筛选 -->
            <frame-filter :do-search="search" :data="filters" :conf="filtersConfig">
            </frame-filter>
            <div class="result_style">
                <span class="disib w90 tl c_999 fl">统计结果</span>
                <div class="fl">
                    <el-tooltip effect="dark" placement="top">
                        <div slot="content">
                            <span class="c_red f20 fb lh30">{{totalCount}}</span> 条
                        </div>
                        <span class="c_red f20 fb">{{totalCount}}</span>
                    </el-tooltip> 条
                </div>
            </div>


            <div class="of">
                <el-checkbox-group v-model="checkList" @change="handleCheckedGoodsChange">
                    <div class="goodsItem detail" v-for="(item,index) in tableData">
                        <el-checkbox :label="item.goodid">
                            <div class="imgBox">
                                <img :src="item.pictureurl != null && item.pictureurl.trim() != '' ? item.pictureurl : '/statics/img/shop/noPic.png'" alt="" class="img" @click="toDetail(item.goodid)">
                            </div>

                            <div class="goodsInfo p10">
                                <div class="c_red" style="display: flex;flex-direction: row;align-items: center">
                                    <money-view :money="item.price"></money-view>/{{item.qtyunit}}
                                    <el-tooltip v-if="item.futurePrice!= null" effect="light" placement="bottom" style="width: 5px;height: 5px;display: flex;align-items: center;margin-left: 5px">
                                        <div slot="content" style=";font-size: 13px">
                                            新协议价:<money-view :money="item.futurePrice" style="margin-left: 5px"></money-view>
                                            <span class="c_red">/{{item.qtyunit}}</span>
                                            <br/>
                                            协议生效时间:<span style="margin:10px 0 10px 5px">{{item.futurePoPriceStartDate}}</span>
                                        </div>
                                        <i class="iconfont c_orange icon-tishi1" style="width: 5px;height: 5px"></i>
                                    </el-tooltip>
                                </div>
                                <div class="title mb10 mt5 of" :title="item.productname" style="white-space: normal;">
                                    {{item.productname}}
                                </div>
                                <div class="info" style="user-select:text">
                                    <div class="mb5">物料编码：{{item.matrlno}}</div>
                                    <div class="mb5">物料条码：{{item.matrltm}}</div>
                                    <div class="mb5">交货周期：{{item.referdeliverydate}}个工作日</div>
                                    <div class="mb5">物资定性：{{item.wzdxmc}}</div>
                                    <div style="display: flex;flex-direction: column">
                                        <div style="display: flex;flex-direction: row">
                                            <div>商品备注：</div>
                                            <i class="iconfont c_orange icon-biji" @click="editRemark(item)"></i>
                                        </div>
                                        <textarea disabled readonly placeholder="暂无备注" style="resize:none">{{item.remark}}</textarea>
                                    </div>

                                </div>

                                <div class="footer">
                                    <button v-if="item.isFailure == 'N' && item.isAddCart =='N' " @click="addCart(item.goodid)" class="right_add pr tc w">
                                        <i class="iconfont icon-gouwuche mr5 c_red"></i>
                                        加入购物车
                                    </button>

                                    <button v-if="item.isFailure == 'N' && item.isAddCart =='Y'" class="right_add pr tc w bg_dedede" disabled>
                                        <i class="iconfont icon-gouwuche mr5 c_red"></i>
                                        已加入购物车
                                    </button>

                                    <button v-if="item.isFailure != 'N'" class="right_add pr tc w bg_dedede" disabled>
                                        已失效
                                    </button>
                                </div>
                            </div>
                        </el-checkbox>
                    </div>
                </el-checkbox-group>
            </div>
            <!-- 分页 -->
            <div class="page">
                <div class="fl">
                    <el-checkbox :indeterminate="isIndeterminate" v-model="checkAll" @change="handleCheckAllChange">全选</el-checkbox>
                    <span v-if="checkList.length>0">
                        已选<span class="c_red">{{checkList.length}}</span>个
                        <el-button type="text" @click="clickDelete" class="blue_txt_btn">删除</el-button>
                    </span>
                </div>
                <el-pagination
                        :current-page="pageIndex"
                        :page-size="pageSize"
                        :total="totalCount"
                        :page-sizes="[12, 36, 60, 100]"
                        @current-change="pageChange"
                        @size-change="sizeChange"
                        layout="total, sizes, prev, pager, next"
                        class="fr"
                        background>
                </el-pagination>
            </div>


            <el-dialog title="编辑商品备注"
                       id="bz"
                       :visible.sync="showEditRemark"
                       :close-on-click-modal="false"
                       :lock-scroll="false"
                       width="320px"
                       center>
                <div class="lh30 pt5">
                    <div class="of">
                        <el-input type="textarea" class="fl"
                                  :autosize="{ minRows: 4, maxRows: 5}"
                                  v-model="remark"
                                  maxlength="500"
                                  resize="none"
                                  rows="5"
                                  style="padding: 0 0 35px 0"
                                  show-word-limit>
                        </el-input>
                    </div>
                </div>
                <span slot="footer" class="dialog-footer" style="padding: 0px 20px 20px">
                    <el-button type="primary" @click="updateItemRemark">确定</el-button>
                    <el-button @click="showEditRemark = false">取消</el-button>
                </span>
            </el-dialog>

        </div>
    </div>
</@override>

<@override name="js">
    <script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/mall/favorites.js?ver=${ver!}"></script>
</@override>

<@extends name="/base-rgpur2.ftl"/>