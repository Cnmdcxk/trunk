<@override name="title">修改密码</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/login.css?v=${ver}">
    <link rel="stylesheet" href="${cdn}/css/retrieve.css?v=${ver}">
</@override>

<@override name="content">
    <div id="RZ" class="">
        <!-- logo  -->
        <div class="logo">
            <div class="w1200">
                <div class="logo_img">
                    <img src="${cdn}/img/logo/logo_000.png" 
                         alt="" class="fl" height="36px">
                    <span class="txt">修改密码</span>
                </div>
            </div>
        </div>
        <!-- 修改密码 -->
        <div class="mt10">
            <div class="w1200 bg_fff">
                <el-form class="w420 margin pt80 lh40" :model="form" :rules="rules" ref="updatePwdForm" label-width="150px">
                    <el-form-item label="账号:">
                        <input type="text" class="w250 h40" disabled v-model="form.account">
                    </el-form-item>
                    <el-form-item label="姓名："> 
                        <span>{{form.accountName}}</span>
                    </el-form-item>
                    <el-form-item prop="hisPassword" label="原密码：">
                        <input type="password" class="w250 h40" v-model="form.hisPassword" placeholder="请输入您的密码"></input>
                    </el-form-item>
                    <el-form-item prop="password" label="新密码：">
                        <input type="password" class="w250 h40" v-model="form.password" placeholder="请再次输入您的密码">
                    </el-form-item>
                    <el-form-item prop="confirmPassword" label="再次输入新密码：">
                        <input type="password" class="w250 h40" v-model="form.confirmPassword" placeholder="请再次输入您的密码">
                    </el-form-item>
                    <div class="tc pb100 mt50">
                        <button class="main_fff_btn mr20" type="button" @click="confirm('updatePwdForm')">确认修改</button>
                        <button class="main_fff_btn" type="button" @click="resetForm('updatePwdForm')">重置</button>
                    </div>
                </el-form> 
            </div>
        </div>
    </div>
</@override>

<@override name="js">
    <script src="${cdn}/js/provider/updatePassword.js?v=${ver!}"  type="text/babel"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>