(function () {
    var urls = {
        // 注册信息填写页面
        register2: "/provider/register2",
    }

    var RZ = new Vue({
        el: '#RZ',
        data: {
            checked: false,
            centerDialogVisible1:false,
            centerDialogVisible2:false,
            centerDialogVisible3:false
        },
        mounted: function () {
        },
        methods: {
            //同意协议
            agreement: function () {
                if (this.checked) {
                    setTimeout(function () {
                        //跳转到注册信息填写页面
                        window.location.href = urls.register2;
                    }, 500);
                } else {
                    dialog.error("请先同意用户协议");
                }
            },
            showDialog: function (dialog) {
                if(dialog == 1) {
                    this.centerDialogVisible1 = true;
                }
                if(dialog == 2) {
                    this.centerDialogVisible2 = true;
                }
                if(dialog == 3) {
                    this.centerDialogVisible3 = true;
                }

            },
        }
    })
})();