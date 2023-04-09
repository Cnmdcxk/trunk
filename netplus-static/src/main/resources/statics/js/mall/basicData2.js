(function (win) {
    var url = {
        importTwoLevelCla:"/api/mall/basicData/importTwoLevelCla/",
        getCategoryNameList :"/api/mall/basicData/getCategoryNameList/",
        getOneLevelClaNameList :"/api/mall/basicData/getOneLevelClaNameList/",
        getTwoLevelClaNameList : "/api/mall/basicData/getTwoLevelClaNameList/",
        modifyMaterialData:"/api/mall/basicData/modifyMaterialData/"

};

    var app = new Vue({

        el: "#RZ",
        data: {

            totalCount: 0,
            dataList: [],
            pageIndex: 1,
            pageSize: 10,

            showUpdateDialog: false,
            showImportDialog: false,

            categorynameList:[],
            onelevelclanameList:[],
            twolevelclanameList:[],

            updateForm: {},

            validators: {
                rules: {
                    twolevelclaname: [{required: true, message: '请选择二级分类'}],
                    onelevelclaname: [{required: true, message: '请选择一级分类'}],
                    categoryname: [{required: true, message: '请选择大类'}]
                }
            },

            filters: {
                twolevelclanameList:[],
            },

            filtersConfig: {
                searchKey: {placeholder: '二级分类／ERP物料号/物料描述', value: ''},
                selects: [
                    {key: 'twolevelclanameList', type: 'more-change', name: '二级分类'},
                ],
            },
        },

        mounted: function () {
            this.search();
            this.getCategoryNameList();
        },
        methods: {

            getCategoryNameList: function(){
                var _this = this;
                ajax(url.getCategoryNameList, {}, function(json){

                    _this.categorynameList = json;

                }, function(err){
                    dialog.error(err);
                })
            },

            getOneLevelClaNameList: function(d){

                var _this = this;
                ajax(url.getOneLevelClaNameList, {categoryName: d}, function(json){

                    _this.onelevelclanameList = json;

                }, function(err){
                    dialog.error(err);
                })
            },

            getTwoLevelClaNameList: function(d){

                var _this = this;
                ajax(url.getTwoLevelClaNameList, {oneLevelClaName: d}, function(json){

                    _this.twolevelclanameList = json;

                }, function(err){
                    dialog.error(err);
                })
            },

            changeCategory: function(d){
                var _this = this;
                _this.updateForm.onelevelclaname ="";
                _this.updateForm.twolevelclaname ="";
                _this.getOneLevelClaNameList(d);

            },

            changeOnwLevelCla: function(d){

                var _this = this;
                _this.updateForm.twolevelclaname ="";
                _this.getTwoLevelClaNameList(d);

            },

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
                    "/api/mall/basicData/getMaterialList/",
                    self.param,
                    function (resp) {
                        self.dataList = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.twolevelclanameList = resp.resultMap.twoLevelClaNameKV;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },


            save: function(){
                var _this = this;

                _this.$refs['updateForm'].validate(function(valid){
                    if (valid) {

                        ajax(url.modifyMaterialData, _this.updateForm, function(json){

                            dialog.success("修改成功", function(){
                                _this.closeUpdateDialog();
                                _this.doSearch();
                            })

                        }, function(err){
                            dialog.error(err);
                        });

                    }else{
                        return false;
                    }
                });
            },

            openUpdateDialog: function(d){
                this.updateForm = d;
                this.showUpdateDialog = true;
            },

            closeUpdateDialog: function(){
                this.updateForm = {};
                this.showUpdateDialog = false;
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

                ajax(url.importTwoLevelCla,
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

        }

    });

})(window);