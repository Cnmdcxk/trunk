<@override name="title">账号登录</@override>

<@override name="css">
    <link rel="stylesheet" href="${cdn}/css/login.css?v=${ver}">
</@override>

<@override name="content">
    <div id="RZ">
        <!-- logo  -->
        <div class="logo">
            <div class="w1200">
                <div class="logo_img">
                    <img src="${cdn}/img/logo/logo_000.png" onclick="javascript:location.href='/'"
                         style="cursor: pointer;height: 50px;" alt="" class="fl"
                         height="36">
                </div>
            </div>
        </div>
        <!-- 背景大块 -->
        <div class="login_bg">
            <div class="login_box">
                <div class="login_item">
                    <div class="tc f18">
                        <span><a href="/provider/login2">供应商登录</a></span>
                        <i>/</i>
                        <span class="c_blue">员工登录</span>
                    </div>
                    <div class="login_input mt40">
                        <el-form :model="form" :rules="rules" ref="loginForm">
                            <el-form-item prop="username">
                                <el-input prefix-icon="iconfont icon-yuangong" autocomplete="off" placeholder="请输入账号"
                                          v-model="form.username"></el-input>
                            </el-form-item>
                            <el-form-item prop="password">
                                <el-input type="password" autocomplete="new-password"
                                          prefix-icon="iconfont icon-OA_fuzhi-" placeholder="请输入密码"
                                          v-model="form.password"></el-input>
                            </el-form-item>
                            <el-form-item>
                                <el-col :span="13">
                                    <el-form-item prop="imgCode">
                                        <el-input prefix-icon="iconfont icon-yanzhengma" placeholder="验证码"
                                                  v-model="form.imgCode"></el-input>
                                    </el-form-item>
                                </el-col>
                                <el-col :span="7"><img :src="imgSrc" alt="验证码" class="img_code" width="64px"
                                                       height="28px"></el-col>
                                <el-col :span="4">
                                    <span class="span_link c_blue f10 disib" @click="refreshImgCode();">换一张</span>
                                </el-col>
                            </el-form-item>
                            <el-form-item style="margin-bottom:0">
                                <el-button type="primary" round @click="login('loginForm')">立即登录</el-button>
                            </el-form-item>
                        </el-form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</@override>

<@override name="js">
    <script type="text/javascript">
        var LOGIN_METHOD = "U";
        var returnUrl = '${returnUrl!}';
    </script>
    <script type="text/babel" src="${cdn}/js/provider/login.js?v=${ver!}"></script>
</@override>

<@extends name="/base-rgpur.ftl"/>