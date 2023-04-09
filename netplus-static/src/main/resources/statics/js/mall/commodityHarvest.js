(function (win) {

    var url = {
        getAllList:"/api/mall/commodityHarves/",
        deleteByCode :"/api/mall/deleteByCode/"
    }

    var app=new Vue({
            el:'#RZ',
            data:function(){
                return{
                    centerDialogVisible3: false,
                    centerDialogVisible: false,
                    dataList: [
                    ],
                    pageIndex: 1,
                    pageSize: 10,
                    totalCount: 0,
                    param: {},
                    filters:{
                        addrtype:[
                            {value:'总库', key: "1"},
                            {value:'快递点', key: "2"}
                        ],
                        createDate:[],
                    },
                    filtersConfig: {
                        searchKey: {
                            placeholder: '省/市/详细地址',value:''
                        },
                        selects: [
                            {key: 'createDate', type: 'date-range', name: '创建时间', },
                            {key: 'addrtype', type: 'single-change', name: '类型'},
                        ]
                    },
                    // 类型
                    addrtype:[
                        {label:"总库",value:"1"},
                        {label:"快递点",value:"2"}
                    ],
                    //下拉框时间
                    // onChange: function () {
                    //     this.$forceUpdate()
                    // },
                    // 新增表单
                    ruleForm:{
                        //收货地址代码
                        code: '',
                        // 类型
                        addrtype: '1',
                        //省
                        province: '',
                        //市
                        city: '',
                        //详细地址
                        consigneeaddr: '',
                    },
                    rulesForm:{
                        code: [{required: true,message: "编码不能为空",trigger: "blur"}],
                        addrtype: [{required: true,message: "请选择类型",trigger: "blur"}],
                        province: [{required: true,message: "省不能为空",trigger: "blur"}],
                        city: [{required: true,message: "市不能为空",trigger: "blur"}],
                        consigneeaddr: [{required: true,message: "详细地址不能为空",trigger: "blur"}],
                    },
                    // 修改表单
                    updateForm:{
                        //收货地址代码
                        code: '',
                        // 类型
                        addrtype: '',
                        //省
                        province: '',
                        //市
                        city: '',
                        //详细地址
                        consigneeaddr: '',
                    },
                }
            },

            mounted: function () {
                this.search();
            },

            methods: {
                // 当前页切换
                pageChange: function (pageIndex) {
                    this.pageIndex = pageIndex;
                    this.searchCore();
                },

                // 设置页大小
                sizeChange: function (pageSize) {
                    this.pageSize = pageSize;
                    this.searchCore();
                },
                dateformat(val,val2){
                    if(val!=null){
                        var year = val.substring(0,4);
                        var month = val.substring(4,6);
                        var day = val.substring(6,8);
                    }
                    if(val2!=null){
                        var hour = val2.substring(0,2);
                        var min = val2.substring(2,4);
                        var sen = val2.substring(4,6);
                    }
                    return  year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sen

                },
                // 条件查询
                search: function (var1) {
                    var self = this;
                    self.pageIndex = 1;
                    self.pageSize = 10;
                    self.param = var1;
                    self.searchCore();
                },
                // 查询全部数据
                searchCore: function () {
                    var self = this;

                    self.param = $.extend(self.param, {
                        pageIndex: self.pageIndex,
                        pageSize: self.pageSize,
                    }),
                        ajax(
                            url.getAllList,
                            self.param,
                            function (resp) {
                                self.dataList = resp.items;
                                resp.items.forEach(function(element){
                                    element.addrtype == '1'?element.addrtype = "总库":element.addrtype = "快递点";
                                    element.createdate = " "+ self.dateformat(element.createdate,element.createtime);
                                    element.updatedate = " "+ self.dateformat(element.updatedate,element.updatetime)

                                })
                                self.totalCount = resp.totalCount;
                            },
                            function (err) {
                                dialog.error(err);
                            }
                        );
                },

                // 添加数据
                addParams:function (addForm) {
                    var self = this
                    self.$refs[addForm].validate(function (valid) {
                        if (valid) {
                            ajax(
                                '/api/mall/addInformation/',
                                self.ruleForm,
                                function (response) {
                                    dialog.success("新增成功")
                                    self.centerDialogVisible3 = false
                                    self.ruleForm = {}
                                    self.search()
                                },
                                function (error) {
                                    dialog.error(error)
                                }
                            )
                        }
                    })
                },

                // 修改弹出框赋值
                updataForms:function (row) {
                    this.centerDialogVisible = true,
                        this.$set(this.updateForm, 'code', row.code),
                        this.$set(this.updateForm, 'province', row.province),
                        this.$set(this.updateForm, 'city', row.city),
                        this.$set(this.updateForm, 'consigneeaddr', row.consigneeaddr)
                        if(row.addrtype == "总库"){
                            this.$set(this.updateForm, 'addrtype', '1')
                        }else{
                            this.$set(this.updateForm, 'addrtype', '2')
                        }
                },
                deleteAddress: function (code) {
                    var self = this;
                    self.$confirm(
                        "是否确认删除地址",
                        "提示",
                        {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning',
                            center: true
                        }
                    ).then(function(){
                        ajax(url.deleteByCode, {code:code}, function(){
                            dialog.success("删除成功", function(){
                                self.search();
                            });
                        }, function(err){
                            dialog.error(err);
                        })
                    }).catch(function(){

                    });
                },
                //修改权限方法
                update:function (updateForm) {
                    var self = this;
                    ajax(
                        '/api/mall/updateInformation/',
                        self.updateForm,
                        function (response) {
                            dialog.success("修改成功")
                            self.centerDialogVisible = false;
                            self.updateForm = {}
                            self.search();
                        },
                        function (error) {
                            dialog.error(error)
                        }
                    )
                }

            }
        }
    );

    win.app = app;
})(window);