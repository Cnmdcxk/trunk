(function () {
    var RZ=new Vue({
        el:'#RZ',
        data:function(){
            return {
                dataList: [],
                pageIndex: 1,
                pageSize: 10,
                totalCount: 0,
                radio:'1',
                checked:true,
                addVisible:false,
                modifyVisible:false,
                value:'',
                centerDialogVisible:false,
                tableData:[{name:'1'}],
                multipleSelection: [],
                filters: {
                    categoryname: [],
                    onelevelclaname:[]
                },
                filtersConfig: {
                    searchKey: {placeholder: '二级分类／ERP物料分类', value: ''},
                    selects: [
                        {key: 'onelevelclaname', type: 'single-change', name: '一级分类', },
                    ],
                    lines: [
                        {key: 'categoryname', type: 'single-change', name: '大类', },
                    ]
                },
                ruleForm:{
                    erpclaname: '',
                    onelevelclaname:'',
                    twolevelclaname:'',
                    categoryname: ''
                },
                updateForm:{
                    erpclaname: '',
                    onelevelclaname:'',
                    twolevelclaname:'',
                    categoryname: '',
                    hiderpclaname: '',
                    hidonelevelclaname:'',
                    hidtwolevelclaname:'',
                    hidcategoryname: ''
                },
                Form:{
                    erpclaname: '',
                    onelevelclaname:'',
                    twolevelclaname:'',
                    categoryname: ''
                },
                validators: {
                    rules: {
                        onelevelclaname: [{required: true, message: '请输入一级分类'}],
                        twolevelclaname: [{required: true, message: '请输入二级分类'}],
                        categoryname: [{required: true, message: '请选择大类'}],
                    }
                },
                orderBy: "",
                asc: false,
                param: {},
                categoryNameList: [],
                show1: false,
                show2: false,
                show3: false,
                show4: false,


                showImportDialog: false,
            }

        },

        mounted: function () {
            this.doSearch();
        },

        methods:{
            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.doSearch(this.param);
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.doSearch(this.param);
            },

            search: function(var1){

                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.param = var1;
                self.doSearch();
            },

            doSearch: function () {
                var self = this;

                self.param = $.extend(self.param, {
                    orderBy: self.orderBy,
                    asc: self.asc,
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                });
                ajax(
                    "/api/mall/basicData/getClassifyList/",
                    self.param,
                    function (resp) {
                        self.dataList = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.categoryname = resp.resultMap.categoryNameList;
                        self.filters.onelevelclaname = resp.resultMap.oneLevelClanameList;
                        self.categoryNameList = resp.resultMap.categoryNameList;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            addClassifyData: function (addForm) {
                var self = this;
                self.$refs[addForm].validate(function (valid) {
                    if (valid) {
                        ajax(
                            "/api/mall/basicData/addClassifyData/",
                            self.ruleForm,
                            function (resp) {
                                dialog.success("数据添加成功");
                                self.addVisible = false;
                                self.ruleForm = {};
                                self.doSearch();
                            },
                            function (err) {
                                dialog.error(err);
                            }
                        );
                    }
                });
            },

            modifyClassifyData: function () {
                var self = this;
                if (self.updateForm.erpclaname == self.Form.erpclaname && self.updateForm.onelevelclaname == self.Form.onelevelclaname
                    && self.updateForm.twolevelclaname == self.Form.twolevelclaname && self.updateForm.categoryname == self.Form.categoryname) {
                    self.upupdateForm = {};
                    dialog.error("数据未修改，请检查!");
                } else {
                    ajax(
                        "/api/mall/basicData/modifyClassifyData/",
                        self.updateForm,
                        function (resp) {
                            dialog.success("数据修改成功!");
                            self.modifyVisible = false;
                            self.doSearch();
                        },
                        function (err) {
                            dialog.error(err);
                        }
                    );
                }
            },

            modifyDataDialog:function(row) {
                var self = this;
                self.modifyVisible = true;
                self.updateForm.hiderpclaname = row.erpclaname;
                self.updateForm.hidonelevelclaname = row.onelevelclaname;
                self.updateForm.hidtwolevelclaname = row.twolevelclaname;
                self.updateForm.hidcategoryname = row.categoryname;
                self.Form.erpclaname = row.erpclaname;
                self.Form.onelevelclaname = row.onelevelclaname;
                self.Form.twolevelclaname = row.twolevelclaname;
                self.Form.categoryname = row.categoryname;
                self.updateForm.erpclaname = row.erpclaname;
                self.updateForm.onelevelclaname = row.onelevelclaname;
                self.updateForm.twolevelclaname = row.twolevelclaname;
                self.updateForm.categoryname = row.categoryname;
                self.updateForm.matrltm = row.matrltm;
            },


            //excel 导入
            openImportDialog: function(){
                this.showImportDialog = true;
            },

            closeImportDialog: function(){
                this.showImportDialog = false;
            },

            beforeUpload: function(file){
                var xls = file.name.substr(file.name.lastIndexOf("."));

                if(xls === '.xls'||xls === '.xlsx'){
                    return file
                }else {
                    dialog.error("上传文件只能是 xls/xlsx 格式!")
                    return false
                }
            },


            upload: function(){

                if (this.$refs.upload.uploadFiles.length > 0) {

                    this.loadingInstance = this.$loading({
                        lock: true,
                        text: "上传中...",
                        background: 'rgba(0, 0, 0, 0.7)'
                    });

                    this.$refs.upload.submit();

                }else{
                    dialog.error("请上传文件")
                }
            },

            handleSuccess: function(resp){

                var _this = this;
                _this.importBatchCode = resp.batchCode;
                _this.loadingInstance.text = "上传成功，保存数据中...";

                ajax("/api/mall/basicData/importErpCla/",
                    {
                        batchCode: resp.batchCode,
                        fileUrl: encodeURI(resp.url),

                    }, function(resp){


                        _this.loadingInstance.text = "保存成功...";
                        _this.loadingInstance.close();
                        _this.$refs.upload.clearFiles();
                        _this.closeImportDialog();
                        _this.search();

                    }, function(err){
                        _this.loadingInstance.close();
                        _this.$refs.upload.clearFiles();
                        dialog.error(err);
                    })

            },

            handleError: function(err){

                var _this = this;
                _this.loadingInstance.close();
                _this.$refs.upload.clearFiles();
                dialog.error("文件上传失败")
            },
            exportBasicData:function () {
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                });
                exportFn("/api/mall/basicData/exportBasicDataClassifyList/", _param, "基础数据导出")

            }
        }
    })
})();