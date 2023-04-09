<@override name="title">注册填写信息</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/login.css?v=${ver}">
    <style>
        .table_fff tr td {
            line-height: 20px;
            padding: 0 0;
        }

        .blankInfo li > div {
            line-height: 28px;
            padding-bottom: 0px;
        }

        .tab_div {
            display: -webkit-box; /* Chrome 4+, Safari 3.1, iOS Safari 3.2+ */
            display: -moz-box; /* Firefox 17- */
            display: -webkit-flex; /* Chrome 21+, Safari 6.1+, iOS Safari 7+, Opera 15/16 */
            display: -moz-flex; /* Firefox 18+ */
            display: -ms-flexbox; /* IE 10 */
            display: flex; /* Chrome 29+, Firefox 22+, IE 11+, Opera 12.1/17/18, Android 4.4+ */
            flex-flow: row nowrap;
            justify-content: space-between;
            align-items: center;
            margin: 0 auto;
        }

        .tab_item {
            cursor: pointer;
            font-weight: normal;
        }
        .el-form-item {
            margin-bottom: 6px;
        }
        .el-input__prefix, .el-input__suffix{
            top:10px;
        }

    </style>
</@override>

<@override name="content">
    <div id="RZ">
        <!-- logo  -->
        <div class="logo">
            <div class="w1200">
                <div class="logo_img">
                    <img src="${cdn}/img/logo/logo_000.png" onclick="javascript:location.href='/'"
                         style="cursor: pointer" alt="" class="fl" height="36">
                    <span class="txt">供应商注册</span>
                </div>
            </div>
        </div>
        <!-- 注册填写信息 -->
        <div class="bg_fff mt5">
            <div class="w1200">
                <div class="" style="width: 1160px;">
                    <!-- 三个切换 -->
                    <div class="login_three">
                        <ul class="ul_li_fl of">
                            <div class="line"></div>
                            <li class="old_curr">
                                <div class="num_box">
                                    <div class="num">1</div>
                                </div>
                                <div>协议阅读并接受</div>
                            </li>
                            <li class="curr">
                                <div class="num_box">
                                    <div class="num">2</div>
                                </div>
                                <div>注册信息填写</div>
                            </li>
                            <li>
                                <div class="num_box">
                                    <div class="num"><i class="iconfont icon-duihao f26 disib mt_5"></i></div>
                                </div>
                                <div>注册完成</div>
                            </li>
                        </ul>
                    </div>
                    <!-- 提示 -->
                    <div class="warn_box" id="warnBox">
                        <i class="fl iconfont icon-tishi c_orange f20 ml15"></i>
                        <span class="fl ml5">红色*号部分为必填项，银行联行号填写正确的行号否则会影响注册和付款</span>
                        <i class="iconfont icon-delete_x  c_orange fr mr5" @click="closeWarn();"></i>
                    </div>
                    <div class="company-info mt5">
                        <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px"
                                 class="demo-ruleForm">
                            <div class="bg_blue_title tab_div">
                                <span>1、公司基本信息</span>
                                <span class="f13 mr10 tab_item" id="foldSpan1" @click="foldI(1);">收起
                                    <i class="iconfont icon-up f13"></i></span>
                                <span class="f13 mr10 tab_item" id="openSpan1" style="display: none;" @click="openI(1)">展开
                                <i class=" iconfont icon-down f13"></i></span>
                            </div>
                            <!-- 表格 -->
                            <div id="tableInfo1">
                                <table class="table_fff w mb30 infoTab mt10">
                                    <colgroup>
                                        <col style="width: 50%;">
                                        <col style="width: 50%;">
                                    </colgroup>
                                    <tr>
<#--                                        <td>
                                            <el-form-item label="统一社会信用代码:" prop="enterpriseCode">
                                                <el-input v-model="ruleForm.enterpriseCode"
                                                          style="width: 90%;"
                                                          @keyup.enter.native="searchByEnterpriseCode()"
                                                        &lt;#&ndash;placeholder="请输入统一社会信用代码后回车"&ndash;&gt;></el-input>
                                            </el-form-item>
                                        </td>-->
                                        <td>
                                            <el-form-item label="企业名称：" prop="enterpriseName">
                                                <div class="sel_bottom fl" style="width:100%;">
                                                    <el-select
                                                            v-model="ruleForm.enterpriseName"
                                                            filterable
                                                            remote
                                                            reserve-keyword
                                                            placeholder="请输入关键词"
                                                            :remote-method="getEnter"
                                                            :loading="loading">
                                                        <el-option
                                                                v-for="item in options"
                                                                :key="item.value"
                                                                :label="item.label"
                                                                :value="item.value">
                                                        </el-option>
                                                    </el-select>
                                                    <el-button type="primary" size="medium" @click="syncMessage">企业相关信息自动填报</el-button>
                                                </div>
                                            </el-form-item>

                                        </td>
                                        <td>
                                            <el-form-item label="统一社会信用代码:" prop="enterpriseCode">
                                                <el-input v-model="ruleForm.enterpriseCode"
                                                          style="width: 90%;"
                                                          @keyup.enter.native="searchByEnterpriseCode()"
                                                        <#--placeholder="请输入统一社会信用代码后回车"-->></el-input>
                                            </el-form-item>
                                        </td>
                                        <#--                                        <td>
                                                                                    <el-form-item label="企业名称: " prop="enterpriseName">
                                                                                        <div class="sel_bottom fl" style="width:100%;">
                                        &lt;#&ndash;                                                    <el-select&ndash;&gt;
                                        &lt;#&ndash;                                                            v-model="ruleForm.enterpriseName"&ndash;&gt;
                                        &lt;#&ndash;                                                            filterable&ndash;&gt;
                                        &lt;#&ndash;                                                            placeholder="请输入关键词"&ndash;&gt;
                                        &lt;#&ndash;                                                            :loading="loading" style="width:90%">&ndash;&gt;
                                        &lt;#&ndash;                                                        <el-option&ndash;&gt;
                                        &lt;#&ndash;                                                                v-for="item in forDataList"&ndash;&gt;
                                        &lt;#&ndash;                                                                :key="item.id"&ndash;&gt;
                                        &lt;#&ndash;                                                                :label="item.name"&ndash;&gt;
                                        &lt;#&ndash;                                                                :value="item.id">&ndash;&gt;
                                        &lt;#&ndash;                                                        </el-option>&ndash;&gt;
                                        &lt;#&ndash;                                                    </el-select>&ndash;&gt;
                                                                                            <el-input @input="getEnter()" v-model="ruleForm.enterpriseName"
                                                                                                      style="width: 90%;"/>
                                        &lt;#&ndash;                                                        &lt;#&ndash;    <el-option&ndash;&gt;
                                        &lt;#&ndash;                                                                    v-for="(item,index) of enterpriseNameArr"&ndash;&gt;
                                        &lt;#&ndash;                                                                    :key="index" prop="enterpriseName"&ndash;&gt;
                                        &lt;#&ndash;                                                                    :label="item.name"&ndash;&gt;
                                        &lt;#&ndash;                                                                    :value="item.id"&ndash;&gt;
                                        &lt;#&ndash;                                                            ></el-option>&ndash;&gt;&ndash;&gt;
                                        &lt;#&ndash;                                                        &lt;#&ndash;<el-option label="区域一" value="shanghai"></el-option>&ndash;&gt;
                                        &lt;#&ndash;                                                        <el-option label="区域二" value="beijing"></el-option>&ndash;&gt;&ndash;&gt;
                                        &lt;#&ndash;                                                    </el-input>&ndash;&gt;
                                                                                            <ul v-show="enterpriseNameShow" style="position: absolute;top: 36px;left: 0px;
                                                                                             background-color: #eee;z-index: 100;width: 360px;">
                                                                                                <li v-for="item in forDataList" :key="item.id" @click.native="setEnterName(item.id)">{{item.name}}</li>
                                                                                            </ul>
                                                                                        </div>
                                                                                    </el-form-item>
                                                                                </td>-->
                                    </tr>
                                    <tr>
                                        <td>
                                            <el-form-item label="所在区域:" required>
                                                <el-col :span="7">
                                                    <div class="sel_bottom fl">
                                                        <el-form-item prop="provinceValue">
                                                            <el-select v-model="ruleForm.provinceValue" placeholder="省"
                                                                       @change="selectProvimce1" style="width:95%;">
                                                                <el-option
                                                                        v-for="(item,index) of provincearr"
                                                                        :key="index"
                                                                        :label="item.name"
                                                                        :value="item.id"
                                                                ></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </div>
                                                </el-col>

                                                <el-col :span="7">
                                                    <div class="sel_bottom fl">
                                                        <el-form-item prop="cityValue">
                                                            <el-select v-model="ruleForm.cityValue" placeholder="市"
                                                                       @change="selectcity1" style="width:95%;">
                                                                <el-option v-for="(item,index) of cityarr" :key="index"
                                                                           :label="item.name"
                                                                           :value="item.id"></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </div>
                                                </el-col>

                                                <el-col :span="8">
                                                    <div class="sel_bottom fl">
                                                        <el-form-item prop="RegionValue">
                                                            <el-select placeholder="区" v-model="ruleForm.RegionValue"
                                                                       style="width:95%;">
                                                                <el-option
                                                                        v-for="(item,index) of regionarr"
                                                                        :key="index"
                                                                        :label="item.name"
                                                                        :value="item.id"
                                                                ></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </div>
                                                </el-col>
                                            </el-form-item>
                                        </td>

                                        <td>
                                            <el-form-item label="企业性质:" prop="enterpriseType">
                                                <div class="sel_bottom fl" style="width:100%;">
                                                    <el-select v-model="ruleForm.enterpriseType" placeholder="请选择企业性质"
                                                               style="width: 90%;">
                                                        <el-option
                                                                v-for="(item,index) of enterpriseTypeArr"
                                                                :key="index"
                                                                :label="item.name"
                                                                :value="item.id"
                                                        ></el-option>
                                                    </el-select>
                                                </div>
                                            </el-form-item>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <#--<el-form-item label="经营方式:" prop="manageWay">
                                                <div class="sel_bottom fl" style="width:100%;">
                                                    <el-select v-model="ruleForm.manageWay" placeholder="请选择经营方式"
                                                               style="width: 90%;">
                                                        <el-option
                                                                v-for="(item,index) of manageWayArr"
                                                                :key="index"
                                                                :label="item.name"
                                                                :value="item.id"
                                                        ></el-option>
                                                    </el-select>
                                                </div>
                                            </el-form-item>-->
                                            <el-form-item label="公司地址:" prop="vendorAddr">
                                                <el-input v-model="ruleForm.vendorAddr" style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>
                                        <td>
                                            <el-form-item label="邮政编码:" prop="postcode">
                                                <el-input v-model="ruleForm.postcode" style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <el-form-item label="注册资金:" prop="regCapital">
                                                <el-input v-model.number="ruleForm.regCapital"
                                                          style="width: 80%;"></el-input>&nbsp;&nbsp;万元
                                            </el-form-item>

                                        </td>
                                        <td>
                                            <el-form-item label="公司电话:" prop="telephone">
                                                <el-input v-model="ruleForm.telephone" style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>
                                    </tr>
                                    <tr>
                                        <#--<td>
                                            <el-form-item label="注册地址:" required>
                                                <el-col :span="7">
                                                    <div class="sel_bottom fl">
                                                        <el-form-item prop="provinceValue2">
                                                            <el-select v-model="ruleForm.provinceValue2" placeholder="省"
                                                                       @change="selectProvimce2" style="width:95%;">
                                                                <el-option
                                                                        v-for="(item,index) of provincearr"
                                                                        :key="index"
                                                                        :label="item.name"
                                                                        :value="item.id"
                                                                ></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </div>
                                                </el-col>

                                                <el-col :span="7">
                                                    <div class="sel_bottom fl">
                                                        <el-form-item prop="cityValue2">
                                                            <el-select v-model="ruleForm.cityValue2" placeholder="市"
                                                                       @change="selectcity2" style="width:95%;">
                                                                <el-option v-for="(item,index) of cityarr2" :key="index"
                                                                           :label="item.name"
                                                                           :value="item.id"></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </div>
                                                </el-col>

                                                <el-col :span="8">
                                                    <div class="sel_bottom fl">
                                                        <el-form-item prop="RegionValue2">
                                                            <el-select placeholder="区" v-model="ruleForm.RegionValue2"
                                                                       style="width:95%;">
                                                                <el-option
                                                                        v-for="(item,index) of regionarr2"
                                                                        :key="index"
                                                                        :label="item.name"
                                                                        :value="item.id"
                                                                ></el-option>
                                                            </el-select>
                                                        </el-form-item>
                                                    </div>
                                                </el-col>
                                            </el-form-item>
                                        </td>-->
                                        <#--<td>
                                            <el-form-item label="公司地址:" prop="vendorAddr">
                                                <el-input v-model="ruleForm.vendorAddr" style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>-->
                                        <td>
                                            <el-form-item label="企业所在省:" prop="registScope">
                                                <div class="sel_bottom fl" style="width:100%;">
                                                    <el-select v-model="ruleForm.registScope" placeholder="请选择企业所在省"
                                                               style="width: 90%;">
                                                        <el-option
                                                                v-for="(item,index) of registScopeArr"
                                                                :key="index"
                                                                :label="item.name"
                                                                :value="item.id"
                                                        ></el-option>
                                                    </el-select>
                                                </div>
                                            </el-form-item>
                                        </td>
                                        <td>

                                        </td>
                                    </tr>
                                </table>
                            </div>

                            <div class="bg_blue_title tab_div">
                                <span>2、重要联系人</span>
                                <span class="f13 mr10 tab_item" id="foldSpan2" @click="foldI(2);">收起
                                    <i class="iconfont icon-up f13"></i></span>
                                <span class="f13 mr10 tab_item" id="openSpan2" style="display: none;"
                                      @click="openI(2);">展开
                                    <i class=" iconfont icon-down f13"></i></span>
                            </div>
                            <div id="tableInfo2">
                                <table class="table_fff w mb30 infoTab mt10">
                                    <colgroup>
                                        <col style="width:50%;">
                                        <col style="width: 50%;">
                                    </colgroup>
                                    <tr>
                                        <td>
                                            <el-form-item label="委托代理人:" prop="agent">
                                                <el-input v-model="ruleForm.agent" style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>
                                        <td>
                                            <el-form-item label="法人代表人:" prop="corporation">
                                                <el-input v-model="ruleForm.corporation" style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <el-form-item label="委托代理人手机:" prop="agentTel">
                                                <el-input v-model="ruleForm.agentTel" style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>
                                        <td>
                                            <el-form-item label="法人代表人电话:" prop="corporationPhone">
                                                <el-input v-model="ruleForm.corporationPhone"
                                                          style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <el-form-item label="委托代理邮箱:" prop="agentMail">
                                                <el-input v-model="ruleForm.agentMail" style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </td>
                                        <td></td>
                                    </tr>
                                </table>
                            </div>

                            <div class="bg_blue_title tab_div">
                                <span>3、银行信息</span>
                                <span class="f13 mr10 tab_item" id="foldSpan3" @click="foldI(3);">收起
                                    <i class="iconfont icon-up f13"></i></span>
                                <span class="f13 mr10 tab_item" id="openSpan3" style="display: none;"
                                      @click="openI(3);">展开
                                    <i class="iconfont icon-down f13"></i></span>
                            </div>
                            <div id="tableInfo3">
                                <div class="pt20 pb30">
                                    <ul class="blankInfo" style="height: 250px;padding-left: 45px;">
                                        <li>
                                            <div class="f14 fb tc">承兑信息</div>
                                            <div class="of">
                                                <el-form-item label="开户银行名称:" prop="bankName1" label-width="130px">
                                                    <el-input v-model="ruleForm.bankName1" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                            <div class="of">
                                                <el-form-item label="开户银行行号:" prop="bankCode1" label-width="130px">
                                                    <el-input v-model="ruleForm.bankCode1" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                            <div class="of">
                                                <el-form-item label="开户银行账号:" prop="cardNum1" label-width="130px">
                                                    <el-input v-model="ruleForm.cardNum1" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                        </li>
                                        <li style="margin: 0 25px;">
                                            <div class="f14 fb tc">电汇信息</div>
                                            <div class="of">
                                                <el-form-item label="开户银行名称:" prop="bankName2" label-width="130px">
                                                    <el-input v-model="ruleForm.bankName2" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                            <div class="of">
                                                <el-form-item label="开户银行行号:" prop="bankCode2" label-width="130px">
                                                    <el-input v-model="ruleForm.bankCode2" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                            <div class="of">
                                                <el-form-item label="开户银行账号:" prop="cardNum2" label-width="130px">
                                                    <el-input v-model="ruleForm.cardNum2" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="f14 fb tc">其他银行信息</div>
                                            <div class="of">
                                                <el-form-item label="开户银行名称:" prop="bankName3" label-width="130px">
                                                    <el-input v-model="ruleForm.bankName3" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                            <div class="of">
                                                <el-form-item label="开户银行行号:" prop="bankCode3" label-width="130px">
                                                    <el-input v-model="ruleForm.bankCode3" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                            <div class="of">
                                                <el-form-item label="开户银行账号:" prop="cardNum3" label-width="130px">
                                                    <el-input v-model="ruleForm.cardNum3" autocomplete="new-password"
                                                              style="width: 90%;"></el-input>
                                                </el-form-item>
                                            </div>
                                        </li>
                                    </ul>
                                </div>
                            </div>

                            <div class="bg_blue_title">
                                <span>4、设置密码</span>
                            </div>

                            <!-- 其他信息 -->
                            <table class="table_fff w mb30 infoTab mt10">
                                <colgroup>
                                    <col style="width: 50%;">
                                    <col style="width: 50%;">
                                </colgroup>
                                <tr>
                                    <td>
                                        <div class="w h50">
                                            <el-form-item label="密码设置:" prop="password" label-width="130px">
                                                <el-input v-model="ruleForm.password" type="password"
                                                          autocomplete="new-password"
                                                          style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </div>
                                        <#--<div class="of ml160">
                                            <i class="c_orange iconfont icon-tishi fl"></i>
                                            <span class="fl">密码不小于6位数</span>
                                        </div>-->
                                    </td>
                                    <td>
                                        <div class="w h50">
                                            <el-form-item label="密码确认:" prop="password2" label-width="130px">
                                                <el-input v-model="ruleForm.password2" type="password"
                                                          autocomplete="new-password"
                                                          style="width: 90%;"></el-input>
                                            </el-form-item>
                                        </div>
                                    </td>
                                </tr>
                            </table>
                            <div class="tc mt60 mb20 pb30">
                                <div class="mb50 ">

                                    <el-button type="primary" size="medium" @click="submitForm('ruleForm')">立即注册</el-button>
                                    <#--<button class="main_btn mr20" @click="submitForm('ruleForm')">立即注册</button>-->
                                    <el-button size="medium"><a href="/portal/index">返回首页</a></el-button>
                                    <#--<button class="main_fff_btn">取消返回首页</button>-->

                                </div>
                                <div class="mb50">
                                    供应商入网审核专业单位：<br>                         
                                    设备处 W6：设备检维修、技改项目<br>
                                    联系方式：18263302348<br>
                                     
                                    电控处 Y6：电气类技改、检修项目<br>
                                    联系方式：18263305600<br>
                                     
                                    工程管理处 V9：工程项目<br>
                                    联系方式：0633-2968570<br>
                                      人力资源处 A1：劳务外包项目<br>
                                    联系方式：0633-2969202<br>
                                     
                                    信息系统处 H5：信息化工程项目 <br> 
                                    联系方式：13562367053<br>
                                     
                                    采购管理处 S1：原辅料、设备及备件或其他项目<br>
                                    联系方式：0633-6188872<br>
                                     
                                    注册答疑咨询电话：0633-2969906/0633-2969312<br>
                                </div>
                            </div>

                            <!-- 右侧滚动按钮 -->
                            <ul class="rigth_scroll">
                                <li class="curr"><span class="yuan"></span><i>1</i></li>
                                <li><span class="yuan"></span><i>2</i></li>
                                <li><span class="yuan"></span><i>3</i></li>
                                <li><span class="yuan"></span><i>4</i></li>
                            </ul>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>

    </div>
</@override>

<@override name="js">
<#--省市区-->
    <script src="${cdn}/js/util/area.js?v=${ver!}"></script>
    <script type="text/babel" src="${cdn}/js/provider/register2.js?v=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>