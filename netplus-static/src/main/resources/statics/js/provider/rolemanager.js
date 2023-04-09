(function (win) {
    var url = {
        getList: "/api/provider/getList",
        tdelete:"/api/provider/delete",
        islist:"/api/provider/islist",
        addrole:"/api/provider/addrole",
        updaterole: "/api/provider/updaterole",
    }
    var app = new Vue({
        el:'#RZ',
        data:{
            rules:{
                rolecode:[{ required: true, message: '请输入角色代码',trigger: 'blur' }],
                rolename:[{ required: true, message: '请输入角色名称',trigger: 'blur' }],
                roledesc:[{ required: true, message: '请输入角色描述',trigger: 'blur' }],
            },
            display1:true,
            display2:false,
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            rolename:'',
            rolecode:'',
            isbool:'',
            rolepermission:'',
            isb:'',

            ruleForm: {
                //角色代码
                rolecode:'',
                //角色名称
                rolename: '',
                //角色描述
                roledesc:''
            },
            updateForm: {
                //角色代码
                rolecode:'',
                //角色名称
                rolename: '',
                //角色描述
                roledesc:''
            },

                centerDialogVisible1:false,
                centerDialogVisible2: false,
                centerDialogVisible3: false,
                radio:'1',
                checked:true,
                addVisible:true,
                value:'',
                centerDialogVisible:false,
                tableData:[],
                multipleSelection: [],
                filtersConfig: {
                    searchKey: {placeholder: '角色名称', value: ''},
                },
                ruleForm:{
                    name:'',
                    spec:''
                },
                rules:{
                    name:[{ required: true, message: '' }]
                },
            searchKey:'',

        },
        mounted:function(){
            this.search();
        },
        methods:{
            search: function(var1){
                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.searchKey = var1;
                self.searchCore();
            },

            searchCore: function () {
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    searchKey:self.searchKey,
                });
                ajax(
                    url.getList,
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.searchCore();
            },
            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.searchCore();
            },
            SubClick: function(item){
                var self = this;
                self.item=item
                self.param = $.extend(self.param, {
                    rolecode:self.item.rolecode,
                });


                dialog.confirm('确定要删除吗', function(){

                    ajax(
                        url.islist,
                        self.param,
                        function (resp) {
                            var res=resp;
                            if (!res){
                                ajax(
                                    url.tdelete,
                                    self.param,
                                    function () {
                                        dialog.success("删除成功", function(){
                                            self.search();
                                        })
                                    },
                                    function (err) {
                                        dialog.error(err);
                                    }
                                );
                            }else {
                                dialog.error("角色已被用户使用")
                            }
                        },
                        function (err) {
                            dialog.error(err);
                        }
                    )

                });
            },
            //新增角色方法
            addParms:function (addForm) {
                var self=this;
                self.$refs[addForm].validate(function (valid) {
                    if (valid) {
                        ajax(
                            url.addrole,
                            self.ruleForm,
                            function (response) {

                                dialog.success("新增成功", function(){
                                    self.centerDialogVisible3 = false
                                    self.ruleForm = {}
                                    self.search()
                                })
                            },
                            function (error) {
                                dialog.error(error)
                            }
                        )
                    }
                })
            },
            updataForms:function (row) {
                    this.centerDialogVisible = true
                    this.updateForm.rolecode=row.rolecode,
                    this.updateForm.rolename=row.rolename,
                    this.updateForm.roledesc=row.roledesc

            },
            //修改角色方法
            update:function (updateForm) {
                var self = this;
                ajax(
                    url.updaterole,
                    self.updateForm,
                    function (response) {

                        dialog.success("修改成功", function(){
                            self.centerDialogVisible = false;
                            self.search();
                        })
                    },
                    function (error) {
                        dialog.error(error)
                    }
                )
            }
        }
    })

    win.app = app;
})(window);