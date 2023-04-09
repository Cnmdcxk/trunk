(function (win) {

    var url = {
        getListService: "/api/provider/getListService/",
        addService:"/api/provider/addServiceConfig",
        updateService:"/api/provider/updateServiceConfig",
        deleteService:"/api/provider/deleteServiceConfig"
    }

    var app=new Vue({
        el:'#RZ',
        data:function(){
            return{
                centerDialogVisible1:false,
                centerDialogVisible2:false,
                radio:'1',
                checked:true,
                addVisible:true,
                value:'',
                centerDialogVisible:true,
                multipleSelection: [],
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalCount: 0,
                Form:{
                    code:'',
                    name:'',
                    val:''
                },
                updateForm: {
                    code:'',
                    name:'',
                    val:''
                },
                //表单校验
                rules:{
                    code:[{ required: true, message: '请输入业务配置代码',trigger: 'blur' }],
                    name:[{ required: true, message: '请输入业务配置名称',trigger: 'blur' }],
                    val: [{ required: true, message: '请输入业务配置参数',trigger: 'blur' }],

                },

                searchService:'',
                asc: false,
                param: {},
            }

        },

        mounted: function () {
            this.search();
        },

        methods:{
            //新增
            addParms:function (addForm) {
                var self = this
                self.$refs[addForm].validate(function (valid) {
                    if (valid) {
                        ajax(
                            url.addService,
                            self.Form,
                            function (response) {
                                dialog.success("新增成功")
                                self.centerDialogVisible1 = false
                                self.Form={}
                                self.searchService=''
                                self.search(self.searchService)
                            },
                            function (error) {
                                dialog.error(error)
                            }
                        )
                    }
                })
            },

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
                self.searchService = var1;
                self.searchCore();
            },

            searchCore: function () {
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    searchService:self.searchService,
                });

                ajax(
                    url.getListService,
                    self.param,
                    function (resp) {
                        console.log(resp);
                        // resp.items.forEach(v=>{
                        //     v.createdate=self.setDateFormat(v.createdate);
                        //     v.createtime=self.setTimeFormat(v.createtime);
                        // });
                        self.dataList=resp.items;
                        self.totalCount = resp.totalCount;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            //关闭dialog重置表单事件
            handleDialogClose:function (addForm) {
                var self = this;
                self.centerDialogVisible1 = false;
                this.$nextTick(() => {
                    this.addForm = addForm;
                })
                self.$refs['addForm'].resetFields();
            },
            updataForms:function (row) {
                this.centerDialogVisible2 = true
                this.updateForm.code=row.code,
                this.updateForm.name=row.name,
                this.updateForm.val=row.val

            },
            //修改角色方法
            update:function (addForm) {
                var self = this;
                self.$refs[addForm].validate(function (valid) {
                    if (valid) {
                        ajax(
                            url.updateService,
                            self.updateForm,
                            function (response) {
                                dialog.success("修改成功")
                                self.centerDialogVisible2 = false;
                                self.search(self.searchService);
                            },
                            function (error) {
                                dialog.error(error)
                            }
                        )
                    }
                })

            },
            //删除
            SubClick: function(item){
                var self = this;
                self.item=item
                self.param = $.extend(self.param, {
                    code:self.item.code,
                });
                if(confirm('确定要删除吗')==true){
                    ajax(
                        url.deleteService,
                        self.param,
                        function () {
                            self.$message('删除成功');
                            self.search(self.searchService);
                        },
                        function (err) {
                            dialog.error(err);
                        }
                    );

                }
            },
            setDateFormat:function (value){

                if (!value || value == '') {
                    return value;
                }else{
                    var y = value.substr(0,4);
                    var m = value.substr(4,2);
                    var d = value.substr(6,2);
                    return  y + '-' + m + '-' + d;
                }
            },
            //时间格式转换
            setTimeFormat:function (value){
                if(!value||value==''){return value}
                else{
                    var h=value.substr(0,2);
                    var m=value.substr(2,2)
                    var s=value.substr(4,2);
                    return h+':'+m+':'+s;
                }
            }

        }
    });

    win.app = app;
})(window);