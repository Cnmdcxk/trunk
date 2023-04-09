(function () {
    var RZ=new Vue({
        el:'#RZ',
        data:{
            dataList: [],
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            radio:'1',
            checked:true,
            modifyVisible:false,
            value:'',
            centerDialogVisible:true,
            tableData:[{name:'1'}],
            multipleSelection: [],
            filters: {
                supplierattributes:[],
                isactive:[{value:'启用', key: 'Y'},{value:'停用', key: 'N'}],
                isthrplat:[{value:'是', key: 'Y'},{value:'否', key: 'N'}]
            },
            filtersConfig: {
                searchKey: {placeholder: '物料条码/货号', value: ''},
                selects: [
                    {key: 'updateDate', type: 'date-range', name: '更新时间', },
                ],
                lines: [
                    {key: 'isactive', type: 'checkbox', name: '状态'},
                ]
            },
            updateForm:{
                matrlno:'',
                twolevelclaname: '',
                thrplatgoodno:'',
                isactive: []
            },
            validators: {
                rules: {
                    twolevelclaname: [{required: true, message: '请输入二级分类'}],
                }
            },
            orderBy: "",
            asc: false,
            param: {},
            show1: false,

            showImportDialog: false,

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
                    "/api/mall/basicData/getSkuMaterialList/",
                    self.param,
                    function (resp) {
                        self.dataList = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.supplierattributes = resp.resultMap.supplierAttributesList;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            modifyClassifyData: function () {
                var self = this;
                if(self.updateForm.isactive == true) {
                    self.updateForm.isactive = 'Y';
                } else if(self.updateForm.isactive == false) {
                    self.updateForm.isactive = 'N';
                }
                ajax(
                    "/api/mall/basicData/modifyMaterialData/",
                    self.updateForm,
                    function (resp) {
                        dialog.success("数据修改成功");
                        self.modifyVisible = false;
                        self.doSearch();
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            modifyDataDialog:function(row) {
                console.log(row);
                var self = this;
                self.modifyVisible = true;
                self.updateForm.twolevelclaname = row.twolevelclaname;
                self.updateForm.hidtwolevelclaname = row.twolevelclaname;
                self.updateForm.matrltm = row.matrltm;
                self.updateForm.matrldesc = row.matrldesc;
                self.updateForm.productname = row.productname;
                self.updateForm.spec = row.spec;
                self.updateForm.quality = row.quality;
                self.updateForm.unit = row.unit;
                self.updateForm.suppliercode = row.suppliercode;
                self.updateForm.suppliername = row.suppliername;
                self.updateForm.thrplatgoodno = row.thrplatgoodno;
                self.updateForm.thrplatproductname = row.thrplatproductname;
                self.updateForm.thrplatspec = row.thrplatspec;
                self.updateForm.thrplatquality = row.thrplatquality;
                self.updateForm.thrplatunit = row.thrplatunit;
                self.updateForm.isactive = row.isactive;
                if("Y" == self.updateForm.isactive) {
                    self.updateForm.isactive = true;
                } else {
                    self.updateForm.isactive = false;
                }
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

                ajax("/api/mall/basicData/importThrplatProduct/",
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
            exportExcel:function () {
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                    orderBy: _this.orderBy,
                    asc: _this.asc,
                });
                exportFn("/api/mall/basicData1/exportExcel/", _param, "物料号sku关系维护数据明细导出")
            },
            handleError: function(err){

                var _this = this;
                _this.loadingInstance.close();
                _this.$refs.upload.clearFiles();
                dialog.error("文件上传失败")
            },

        }
    })
})();