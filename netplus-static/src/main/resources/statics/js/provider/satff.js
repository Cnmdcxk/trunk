(function (win) {

    var url = {
        getList: "/api/provider/getStaffList/",
        updateRole: "/api/provider/updateRole/",
        getRoleList: "/api/provider/getRoleList/",
        updateActive: "/api/provider/updateActive/",
        exportStaff:"/api/provider/exportStaff/",
        getUserOwnRoleByUserNo: "/api/provider/getUserOwnRoleByUserNo/"
    }

    var app = new Vue({
        el:'#RZ',
        data:{

            dataList: [],
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,

            filters:{
                userType:[
                    {value:'员工', key: "B"},
                    {value:'供应商', key: "S"}
                ],
                isRole:[
                    {value:'未设置', key: "N"},
                    {value:'已设置', key: "Y"}
                ],
                createDate:[],
            },
            filtersConfig: {
                searchKey: {placeholder: '账号/名称/手机号码', value: ''},
                selects: [
                    {key: 'userType', type: 'single-change', name: '账号类型'},
                    {key: 'createDate', type: 'date-range', name: '创建时间', },

                ],
                lines: [
                    {key: 'isRole', type: 'single-change', name: '是否设置角色'},
                ],
            },
            param: {},


            showSetRoleDialog: false,
            roleList: [],
            selectedUserNo: "",
        },

        mounted: function () {
            this.search();
        },

        methods:{

            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.searchCore();
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.searchCore();
            },

            search: function(var1){
                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.param = var1;
                self.searchCore();
            },

            searchCore: function () {
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                });

                ajax(
                    url.getList,
                    self.param,
                    function (resp) {
                        self.dataList = resp.items;
                        self.totalCount = resp.totalCount;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },


            openSetRoleDialog: function(userNo){

                var _this = this;


                ajax(
                    url.getUserOwnRoleByUserNo,
                    {userNo:userNo},
                    function(resp){

                        $.each(resp, function(i, d){
                            d.isCheck = d.isOwn == 'Y' ? true : false;
                        });

                        _this.roleList = resp;
                        _this.showSetRoleDialog = true;
                        _this.selectedUserNo = userNo;

                    },
                    function(err){
                        dialog.error(err);
                    }
                )

            },

            closeSetRoleDialog: function(){
                this.roleList = [];
                this.showSetRoleDialog = false;
                this.selectedUserNo = "";
            },

            saveRole: function(){

                var _this= this;
                var param = {
                    userNo: _this.selectedUserNo,
                    roleCodeList: [],
                }

                $.each(_this.roleList, function(i, d){

                    if (d.isCheck) {
                        param.roleCodeList.push(d.rolecode);
                    }

                })

                ajax(url.updateRole, param, function(){
                    dialog.success("修改角色成功", function(){
                        _this.closeSetRoleDialog();
                        _this.searchCore();
                    });

                }, function(err){
                    dialog.error(err);
                })

            },


            updateActive: function(userNo, isActive){
                var _this = this;
                ajax(url.updateActive, {userNo: userNo, isActive: isActive}, function(){

                    dialog.success("操作成功", function(){
                        _this.searchCore();
                    });

                }, function(err){
                    dialog.error(err);
                })
            },

            exportStaff: function(){
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                });

                exportFn(url.exportStaff, self.param, "账号导出明细")
            },

        }
    });

    win.app = app;
})(window);