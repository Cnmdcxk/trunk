(function (win) {
    var url = {
        getRolePrivilegeList: "/api/provider/getRolePrivilegeList",
        saveRolePrivilege: "/api/provider/saveRolePrivilege",
        getRoleOwnPrivileges: "/api/provider/getRoleOwnPrivileges"
    };

    var app = new Vue({

        el: "#app",
        data: {
            roleCode: roleCode,
            showPrivilegeDialog: false,
            rolePrivilegeList: [],
            ownRolePrivilegeList: [],

            isSelectAll: false,
        },

        mounted: function(){
            this.getRoleOwnPrivileges();
        },

        methods: {

            openPrivilegeDialog: function(){
                this.getRolePrivilegeList();
            },

            getRoleOwnPrivileges: function(){

                var _this = this;
                ajax(url.getRoleOwnPrivileges, {rolecode: _this.roleCode}, function(json){

                    _this.ownRolePrivilegeList = json;
                }, function(err){
                    dialog.error(err);
                })

            },


            save: function(){
                var _this = this;

                var param = {
                    rolecode: _this.roleCode,
                    privilegecodelist: []
                };

                $.each(_this.rolePrivilegeList, function(i, d){

                    $.each(d.childprivilegelist, function(i, dd) {

                        if (dd.isCheck) {
                            param.privilegecodelist.push(dd.code);
                        }
                    });
                });


                if (param.rolecode == "" || param.rolecode == null || param.rolecode == undefined) {
                    dialog.error("角色代码不存在");
                    return;
                }

                ajax(url.saveRolePrivilege, param, function(){
                    dialog.success("保存成功", function(){
                        win.location.reload();
                    }, function(err){
                        dialog.error(err);
                    })
                })
            },

            getRolePrivilegeList: function(){
                var _this = this;
                ajax(url.getRolePrivilegeList, {rolecode: _this.roleCode}, function(json){



                    $.each(json, function(i, d) {

                        $.each(d.childprivilegelist, function(i, dd) {

                            dd.isCheck = dd.roleisassignment == 'Y';
                        });
                    });

                    _this.rolePrivilegeList = json;
                    _this.showPrivilegeDialog = true;
                }, function(err){
                    dialog.error(err);
                })
            },

            back: function() {
                win.location.href = "/provider/rolemanager"
            }
        },

        watch: {
            isSelectAll: function(n, o){
                var _this = this;

                $.each(_this.rolePrivilegeList, function(i, d){

                    $.each(d.childprivilegelist, function(i, dd) {

                        dd.isCheck = n;
                    });

                })
            }
        }

    });

    win.app = app;

})(window || {});