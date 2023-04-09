(function () {
    var RZ = new Vue({
        el: '#RZ',
        data: {
            form: {
                username: null,
                password: null,
                imgCode: null,
                sms: null
            },
            rules: {
                username: [
                    {required: true, message: '请输入账号', trigger: 'blur'},
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                ],
                imgCode: [
                    {required: true, message: '请输入验证码', trigger: 'blur'},
                ],
                sms: [
                    {required: true, message: '请输入短信验证码', trigger: 'blur'},
                ],
            },
            // 2020-12-28 chengl add s
            loginMethod: LOGIN_METHOD,
            returnUrl:returnUrl,
            imgSrc: "",
            showCount: false,
            count: '',
            timer: null,
        },

        mounted: function () {
            this.decodeUri();
            this.refreshImgCode();
        },

        methods: {
            // 登录方法，添加登录验证
            login: function (formName) {
                var _this = this;
                _this.$refs[formName].validate(function(valid) {
                    if (valid) {

                        ajax('/provider/doLogin/',
                            {
                                username: _this.form.username,
                                password: _this.form.password,
                                //imgCode: _this.form.imgCode,
                                loginMethod: _this.loginMethod,
                            },
                            function (resp) {

                                if (resp.code == "OK") {
                                    console.log("returnUrl: ", _this.returnUrl);
                                    document.location.href = _this.returnUrl;
                                } else {
                                    _this.refreshImgCode();
                                    dialog.error(resp.msg);
                                }
                            },
                            function (err) {
                                _this.refreshImgCode();
                                dialog.error(err);
                            }
                        );

                        //// 验证输入图形验证码是否正确
                        //_this.validateCode().then(function(msg) {
                        //    ajax('/provider/doLogin/',
                        //        {
                        //            username: _this.form.username,
                        //            password: _this.form.password,
                        //            //imgCode: _this.form.imgCode,
                        //            loginMethod: _this.loginMethod,
                        //        },
                        //        function (resp) {
                        //
                        //            if (resp.code == "OK") {
                        //                console.log("returnUrl: ", _this.returnUrl);
                        //                document.location.href = _this.returnUrl;
                        //            } else {
                        //                _this.refreshImgCode();
                        //                dialog.error(resp.msg);
                        //            }
                        //        },
                        //        function (err) {
                        //            _this.refreshImgCode();
                        //            dialog.error(err);
                        //        }
                        //    );
                        //}, function(error) {
                        //    _this.refreshImgCode();
                        //    dialog.error(error);
                        //}).catch(
                        //
                        //);

                        return false;
                    } else {

                        return false;
                    }
                });
            },
            decodeUri:function () {
                var url=decodeURIComponent(window.location.href);
                var index =url.indexOf("?");
                if(index!=-1){
                    url=url.substring(url.indexOf("=",url.indexOf("=")+1)+1,url.length);
                    this.returnUrl=url;
                }else {
                    this.returnUrl=returnUrl;
                }
                console.log(this.returnUrl)
            },

            refreshImgCode: function () {
                this.imgSrc = '/api/v2/captcha/img/Login/?' + new Date().getTime();
            },

            validateCode() {
                var _this = this;
                return new Promise((resolve, reject) => {
                    ajax('/api/v2/captcha/checkCode',
                        {
                            imgCode: _this.form.imgCode,
                            imgType: 'Login'
                        },
                        function (resp) {
                            if (resp.status == 1) {
                                resolve("success");
                            } else {
                                reject(resp.msg);
                            }
                        },
                        function (err) {
                            reject("验证请求失败");
                        }
                    );
                });
            },
        }
    })
})();