(function (win) {

    var app=new Vue({
        el:'#RZ',
        data:{
            password: '',
            mockUsername: '',
        },

        methods:{

            mockLogin: function(){

                var _this = this;

                if (_this.password == "") {
                    dialog.error("你的密码不能为空");
                    return;
                }

                if (_this.mockUsername == "") {
                    dialog.error("模拟账号不能为空");
                    return;
                }

                ajax(
                    "/provider/mockLogin/",
                    {password: _this.password, mockUsername: _this.mockUsername, source: "PC"},
                    function(resp){

                        console.log(resp);
                        win.location.href = '/';

                    },
                    function(err){

                        dialog.error(err);

                    }
                );

            }

        }
    });

    win.app = app;
})(window);