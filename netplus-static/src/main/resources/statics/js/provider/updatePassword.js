(function () {
    var RZ = new Vue({
        el: '#RZ',
        data: function () {
            // 校验密码
            var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥\\\\……&*（）——|{}【】‘；：”“'。，、？]");
            var checkPwd = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入密码'));
                } else {
                    const reg = /^(.{6,})$/;
                    if(reg.test(value)) {
                        if(pattern.test(value)){
                            return callback(new Error('密码只能输入（A-Za-z0-9），含有非法字符!!'));
                        }else{
                            callback();
                        }
                    }else {
                        console.log(pattern.test(value))
                        return callback(new Error('密码不得低于6位!!'));
                    }
                }
            };
            var checkConfirmPwd = (rule, value, callback) => {
                if (!value) {
                    return callback(new Error('请输入确认密码'));
                } else {
                    let password = this.form.password;
                    if (password && value.trim() !== password.trim()) {
                        return callback(new Error('两次密码不一致'))
                    }
                    if(pattern.test(value)){
                        return callback(new Error('密码只能输入（A-Za-z0-9），含有非法字符!!'));
                    }
                    callback();
                }
            };
            return {
                //表单变量
                form: {
                    account: '',
                    accountName: '',
                    hisPassword: null,
                    password: null,
                    confirmPassword: null,
                },
                rules: {
                    hisPassword: [
                        {required: true, message: '请输入原密码', trigger: 'blur'},
                    ],
                    password: [
                        {required: true, message: '请输入新密码', trigger: 'blur'},
                        {validator: checkPwd, trigger: 'blur'},
                    ],
                    confirmPassword: [
                        {required: true, message: '请输入确认密码', trigger: 'blur'},
                        {validator: checkConfirmPwd, trigger: 'blur'},
                    ],
                },
            }
        },
        mounted: function () {
            let self = this;
            self.getCurrentUser();
        },
        methods: {
            getCurrentUser: function () {
                var self = this;
                ajax('/api/provider/getCurrentUser/',
                    {loading: false},
                    function (resp) {

                        let currentUser = resp;
                        if (currentUser) {
                            self.form.account = currentUser.userCode;
                            self.form.accountName = currentUser.username;
                        }
                    },
                    function (err) {
                        //dialog.error(err);
                    }
                );
            },
            // 确认修改密码
            confirm: function (formName) {
                var _this = this;
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        ajax('/api/provider/updateUserPwd',
                            {
                                userCode: _this.form.account,
                                password: _this.form.hisPassword,
                                newPwd: _this.form.password,
                                confirmPwd: _this.form.confirmPassword,
                            },
                            function (resp) {
                                if (resp.status == "1") {
                                    dialog.success("修改成功，请重新登录");
                                    setTimeout(function () {
                                        window.location.href = "/provider/logout";
                                    }, 500);
                                } else {
                                    dialog.error(resp.msg);
                                }
                            },
                            function (err) {
                                dialog.error(err);
                            }
                        );
                    }else {
                        console.log('验证失败!!');
                        return false;
                    }
                })
            },
            // 重置表单
            resetForm: function (formName) {
                var _this = this;
                _this.form.hisPassword = null;
                _this.form.password = null;
                _this.form.confirmPassword = null;
                _this.$refs[formName].resetFields();
            },
            passwordChange:function(){
                var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥\\\\……&*（）——|{}【】‘；：”“'。，、？]");
                if(pattern.test(this.form.password)){
                    return true
                }else{
                    return false
                }
            },
            confirmPasswordChange:function(){
                var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥\\\\……&*（）——|{}【】‘；：”“'。，、？]");
                if(pattern.test(this.form.confirmPassword)){
                    return true
                }else{
                    return false
                }
            }

        }


    })
})();