(function () {
    var RZ=new Vue({
        el:'#RZ',
        data:function(){
            return {
                pageIndex: 1,
                pageSize: 10,
                totalCount: 0,
                centerDialogVisible3: false,
                radio:'1',
                checked:true,
                addVisible:true,
                value:'',
                tableData: [],
                multipleSelection: [],
                filters: {
                        parentCodeList: [],
                        isActiveList:
                            [{key: 'Y', value: '是',valuetype: 'value'}, {key: 'N', value: '否'}],
                        isDefaultList:
                            [{key: 'Y', value: '是',valuetype: 'value'}, {key: 'N', value: '否'}],
                        pagevisibleList:
                            [{key: 'Y', value: '是',valuetype: 'value'}, {key: 'N', value: '否'}]
                    },
                filtersConfig: {
                    searchKey: {
                        placeholder: '权限代码/权限名称/权限描述/权限类型', value: ''
                    },
                    selects: [{key: 'isActiveList', type: 'more-change', name: '是否激活'},
                        {key: 'isDefaultList', type: 'more-change', name: '是否默认'},
                        {key: 'parentCodeList', type: 'more-change', name: '父权限代码', value:''}]
                },
                ruleForm:{
                    //权限代码
                    code: '',
                    //权限名称
                    name: '',
                    //权限描述
                    privilegedesc: '',
                    //权限类型
                    privilegetype: '',
                    //父权限代码
                    parentcode: '',

                    createDate:'',
                    //创建时间
                    createtime: '',
                    //排序
                    sort: '',
                    //是否激活
                    isactive: '',
                    //权限归属
                    belongto: '',
                    //是否默认
                    isdefault: '',
                    //图标
                    icon: '',
                    //是否显示
                    pagevisible: ''
                },
                updateForm: {
                    code: '',
                    name: '',
                    privilegedesc: '',
                    privilegetype: '',
                    parentcode: '',
                    createtime: '',
                    sort: '',
                    hidsort: '',
                    isactive: '',
                    belongto: '',
                    isdefault: '',
                    icon: '',
                    pagevisible: ''
                },
                //表单校验
                rules:{
                    code:[{ required: true, message: '请输入权限代码',trigger: 'blur' }],
                    name: [{ required: true, message: '请输入权限名称',trigger: 'blur' }],
                    privilegedesc: [{ required: true, message: '请输入权限描述' ,trigger: 'blur' }],
                    privilegetype: [{ required: true, message: '请选择权限类型'  }],
                    parentcode: [{ required: true, message: '请选择父权限代码'  }],
                    sort: [ { required: true, message: '只能输入数字' ,trigger: 'blur'}],
                            // { digits: true, message: '此字段必须是整数'},
                            // { min: 1, message: '此字段不能小于0' }],
                    isactive: [{ required: true, message: '请选择是否激活'}],
                    icon: [{ required: true, message: '请输入图标',trigger: 'blur' }],
                    pagevisible: [{ required: true, message: '请选择是否显示'}],
                    // createtime:  [{ required: true, message: '请选择创建时间',trigger: 'blur' }],
                    belongto: [{ required: true, message: '请输入权限归属',trigger: 'blur' }],
                    isdefault: [{ required: true, message: '请选择是否默认' }]
                },
                orderBy: "",
                asc: false,
                param: {
                    privilegetype: ''
                },
                privilegetypeData: [],
                parentCodeList:[],
                isActiveList:[],
                isDefaultList:[],
                pagevisibleList:[],
                centerDialogVisible: false,
                privilegetypeList:[]
            }
        },
        mounted: function () {
            this.search();
        },

        methods:{
            //下拉框change事件
            onChange: function () {
                this.$forceUpdate()
            },
            //关闭dialog重置表单事件
            handleDialogClose:function (addForm) {
                var self = this;
                self.centerDialogVisible3 = false;
                this.$nextTick(() => {
                    this.addForm = addForm;
                })
                self.$refs['addForm'].resetFields();
            },
            formatActionqrcode1: function (row,column) {
                return row.isactive == 'Y' ? "是" :row.isactive == 'N' ? "否":""
            },
            formatActionqrcode2: function (row,column) {
                return row.isdefault == 'Y' ? "是" :row.isdefault == 'N' ? "否":""
            },
            formatActionqrcode3: function (row,column) {
                return row.pagevisible == 'Y' ? "是" :row.pagevisible == 'N' ? "否":""
            },
            //权限类型联动父权限代码
            onChange: function (data) {
                if(data === 'MODULE'){
                    this.rules.parentcode[0].required = false
                }
                if(data != 'MODULE') {
                    var self = this;
                    self.param.privilegetype = data;
                    ajax(
                        '/api/provider/getByCodeList/',
                        self.param,
                        function (response) {
                            self.privilegetypeData = response.resultMap.privilegetype
                        }
                    )
                }
            },
            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.search(this.param);
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.search(this.param);
            },
            handleSelectionChange:function(val) {
                this.multipleSelection = val;
            },
            //搜索+筛选
            search: function (param) {
                var self = this;
                self.param = $.extend(param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    orderBy: self.orderBy,
                    asc: self.asc
                });
                ajax(
                    '/api/provider/getAllList/',
                    self.param,
                    function (response) {
                        // response.items.forEach(v=>{
                        //     v.createdate=self.setDateFormat(v.createdate);
                        //     v.createtime=self.setTimeFormat(v.createtime);
                        // });
                        self.tableData = response.items;
                        self.totalCount = response.totalCount;
                        self.filters.parentCodeList = response.resultMap.parentcode;
                        self.parentCodeList = response.resultMap.parentcode;
                        self.privilegetypeList = [{key: "MODULE", value: "模块"},{key: "MENU", value: "菜单"},{key: "PAGE", value: "页面"}];
                    }
                )
            },
            //新增权限方法
            addParms:function (addForm) {
                var self = this
                self.ruleForm.isdefault = 'Y'
                self.$refs[addForm].validate(function (valid) {
                    if (valid) {
                        ajax(
                            '/api/provider/addPrivilege/',
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
            //给from表单赋值
            updataForms:function (row) {
                        this.centerDialogVisible = true,
                        this.$set(this.updateForm, 'code', row.code),
                        this.$set(this.updateForm, 'name', row.name),
                        this.$set(this.updateForm, 'privilegedesc', row.privilegedesc),
                        this.$set(this.updateForm, 'privilegetype', row.privilegetype),
                        this.$set(this.updateForm, 'parentcode', row.parentcode),
                        this.$set(this.updateForm, 'createtime', row.createtime),
                        this.$set(this.updateForm, 'sort', row.sort),
                        this.$set(this.updateForm, 'hidsort', row.sort),
                        this.$set(this.updateForm, 'isactive', row.isactive),
                        this.$set(this.updateForm, 'belongto', row.belongto),
                        this.$set(this.updateForm, 'isdefault', row.isdefault),
                        this.$set(this.updateForm, 'icon', row.icon),
                        this.$set(this.updateForm, 'pagevisible', row.pagevisible)

            },
            //修改权限方法
            update:function (updateForm) {
                var self = this;
                ajax(
                    '/api/provider/modifyPrivilege/',
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
            },
            //日期格式转换
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
        },
    })
})();
