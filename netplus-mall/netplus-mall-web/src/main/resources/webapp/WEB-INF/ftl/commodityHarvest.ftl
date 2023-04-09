
<@override name="title">收货地址管理</@override>

<@override name="css">
<link rel="stylesheet" href="${cdn}/css/home.css?v=${ver}">
</@override>
<@override name="content">
<!-- 内容区域 -->
<div class="bg_fff">
  <div class="w1200">
    <!-- 菜单信息 -->
    <#include "/left-nav.ftl">

    <!-- 右侧内容 -->
    <div id="RZ" class="right_con" v-cloak>
      <div class="right_con" style="width: 100%;">
        <div class="right_top">
          <i class="iconfont icon-triangle-tright c_blue ml10 fl"></i>
          <span class="c_blue fl ml10">收货地址管理</span>
        </div>
        <div class="p20">

          <frame-filter
                  page-title="收货人管理"
                  :do-search="search"
                  :data="filters"
                  :conf="filtersConfig"
                  ref="filters">
          </frame-filter>
          <!-- 统计结果 -->
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
          <!-- 表格 -->
          <el-table :data="dataList" class="table_main_style">
<#--            <el-table-column  type="index" label="id" width="50"></el-table-column>-->
            <el-table-column  prop="code" label="收货地址代码" width="150"></el-table-column>
            <el-table-column  prop="addrtype" label="类型" width="150"></el-table-column>
            <el-table-column  prop="province" label="省" width="60"></el-table-column>
            <el-table-column  prop="city" label="市" width="60"></el-table-column>
            <el-table-column  prop="consigneeaddr" label="收货地址" width="180"></el-table-column>
<#--            <el-table-column  prop="createdate" label="创建日期" width="60"></el-table-column>-->
            <el-table-column  prop="createdate" label="创建时间" width="140" ></el-table-column>
<#--            <el-table-column  prop="updatedate" label="修改日期" width="120"></el-table-column>-->
            <el-table-column  prop="updatedate" label="修改时间" width="140" ></el-table-column>
            <el-table-column   label="操作" width="100"  fixed="right">
              <template slot-scope="scope">
                <div>
                  <button class="blue_txt_btn" @click="updataForms(scope.row)">修改</button>
                  <button class="blue_txt_btn" @click="deleteAddress(scope.row.code)">删除</button>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页 -->
          <div class="page">
            <el-pagination
                    :current-page="pageIndex"
                    :page-size="pageSize"
                    :total="totalCount"
                    :page-sizes="[10, 1, 50, 100]"
                    @current-change="pageChange"
                    @size-change="sizeChange"
                    layout="total, sizes, prev, pager, next"
                    class="fr"
                    background>
            </el-pagination>
          </div>
          <div class="tr">
            <button class="main_btn" @click="centerDialogVisible3 = true">新增</button>
          </div>

          <!-- 新增权限 -->
          <el-dialog title="新增收货地址" :visible.sync="centerDialogVisible3" width="430px" center :close-on-click-modal="false">
            <div class="w_90 margin">
              <el-form ref="addForm" class="my_form" label-width="130px" :rules="rulesForm" :model="ruleForm">
                <el-form-item label="收货地址代码" prop="code">
                  <el-input v-model="ruleForm.code" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="类型" prop="addrtype">
                  <el-select  v-model="ruleForm.addrtype" placeholder="请选择类型" >
                    <el-option v-for="item in addrtype" :key="item.value" :label="item.label" :value="item.value"/>
                  </el-select>
                </el-form-item>
                <el-form-item label="省份" prop="province">
                  <el-input v-model="ruleForm.province" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="市" prop="city">
                  <el-input v-model="ruleForm.city" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="详细地址" prop="consigneeaddr">
                  <el-input v-model="ruleForm.consigneeaddr" placeholder="请输入"></el-input>
                </el-form-item>
              </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="addParams('addForm')">保 存</el-button>
                            </span>
          </el-dialog>
          <el-dialog title="修改收货地址" :visible.sync="centerDialogVisible" width="430px" center :close-on-click-modal="false">
            <div class="w_90 margin">
              <el-form ref="addForm" class="my_form" label-width="130px" :model="updateForm">
                <el-form-item label="收货地址代码" prop="code">
                  {{updateForm.code}}
                </el-form-item>
                <el-form-item label="类型" prop="addrtype">
                  <el-select  v-model="updateForm.addrtype" placeholder="请选择类型" >
                    <el-option v-for="item in addrtype" :key="item.value" :label="item.label" :value="item.value"/>
                  </el-select>
                </el-form-item>
                <el-form-item label="省份" prop="province">
                  <el-input v-model="updateForm.province" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="市" prop="city">
                  <el-input v-model="updateForm.city" placeholder="请输入"></el-input>
                </el-form-item>
                <el-form-item label="详细地址" prop="consigneeaddr">
                  <el-input v-model="updateForm.consigneeaddr" placeholder="请输入"></el-input>
                </el-form-item>
              </el-form>
            </div>
            <span slot="footer" class="dialog-footer">
                                <el-button type="primary" @click="update('addForm')">保 存</el-button>
                            </span>
          </el-dialog>
        </div>
        </div>

      </div>
    </div>
  </div>
</div>
</@override>

<@override name="js">
<script type="text/javascript" src="${cdn}/js/framework/components/filters.js?ver=${ver!}"></script>
<script type="text/javascript" src="${cdn}/js/mall/commodityHarvest.js?v=${ver}"></script>
</@override>
<@extends name="/base-rgpur2.ftl"/>
