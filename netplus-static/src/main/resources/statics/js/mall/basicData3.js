(function (win) {
    var url = {
        getBasicPicLibList:"/api/mall/basicData/getPicInfoList/",
        createBasicPicLib:"/api/mall/basicData/CreatePicInfoList/",
        searchSupplier:"/api/provider/getManufacturerList/",
        createPicLib:"/api/mall/picLib/createPicLib/",
        getScopeList: "/api/provider/getScopeList/",
        initBasicPic:"/api/mall/picLib/initBasicPic/",
        delBasicPic: "/api/mall/basicData/delBasicPic/"
        };
    var app = new Vue({


        el: "#RZ",
        data: {

            totalCount: 0,
            dialogList:[],
            dataList: [],
            dataList2:[],
            dataList3:[],
            pageIndex: 1,
            errorList:[],
            successCount:0,
            errorCount:0,
            pageSize: 10,
            supplierCount:0,
            multipleSelection:[],
            multipleSelectionLength:0,
            orderBy: "createdate",


            filtersConfig: {
                searchKey: {placeholder: '物料条码', value: ''},
            },


            dataList2:[],
            filtersConfig2: {
                searchKey: {placeholder: '供应商编号/供应商名称', value: ''},
            },
            pageIndex2: 1,
            pageSize2: 10,
            param2: {},
            totalCount2:0,
            showSupplierDialog: false,


            dataList3:[],
            filtersConfig3: {
                searchKey: {placeholder: '供货范围代码', value: ''},
            },
            pageIndex3: 1,
            pageSize3: 10,
            param3: {},
            totalCount3:0,
            showScopeDialog: false,
            showErrorDialog:false,
        },

        mounted: function () {
            this.search();
        },

        methods: {


            sortChange: function(column){
                this.asc = "ascending" == column.order ? true:false;
                this.orderBy = column.prop;
                this.doSearch(this.param);
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
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    asc: self.asc,
                    orderBy: self.orderBy

                });
                ajax(
                    url.getBasicPicLibList,
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

            beforeUpload: function(file){
                this.showErrorDialog=true;
                var isPic = file.type == 'image/png' || file.type == 'image/jpg' || file.type == 'image/jpeg';

                var isLt300K = file.size/1024<300;

                if (!isPic) {
                    this.errorList.push({
                        message:"图片类型不是png, jpg, jpeg",
                        pictureName:file.name
                    });
                    this.errorCount++;
                }
                if (!isLt300K) {
                    this.errorList.push({
                        message:"图片大小不能超过300K",
                        pictureName:file.name
                    });
                    this.errorCount++;
                }
                return isPic && isLt300K;
            },

            successUpload: function(resp, file){
                var _this=this;

                var data = {
                    pictureName: file.name,
                    pictureUrl: encodeURI(resp.url)
                };

                ajax(
                    url.createBasicPicLib,
                    data,
                    function(resp){
                        if(resp.status==0){
                            var obj={
                                message:resp.message,
                                pictureName: resp.data
                            }
                            _this.errorList.push(obj)
                            _this.errorCount++;
                        }
                        if(resp.status==1){
                            _this.successCount++;
                        }
                        _this.search();
                    },
                    function(err){
                        _this.errorList.push({
                            message:err,
                            pictureName:file.name,
                        });
                        _this.errorCount++;
                    }
                );
            },
            toPreview: function(pictureurl) {
                formForward(pictureurl, {}, "_blank", "get");
            },
            errorUpload: function(err,file,fileList){
                var _this=this;
                _this.errorList.push({
                    message:"上传失败",
                    pictureName:file.name,
                });
                _this.errorCount++;
            },
            clear:function () {
                this.errorList=[];
                this.successCount=0;
                this.errorCount=0;
                this.$refs.upload.clearFiles();
            },
            closeDialog:function () {
                this.showErrorDialog=false;
            },
            handleSelectionChange: function(val){
                this.multipleSelection = val;
                this.multipleSelectionLength=this.multipleSelection.length;
            },


            toSelect:function (d, type) {

                var _this = this;
                d.isSelect = "Y";

                var param = {
                    supplierNo: type == 'A' ? d.supplierno: "",
                    scope: type == 'B' ? d.tendarea: "",
                    tbmqq407List: []
                }

                $.each(_this.multipleSelection, function(i, dd){
                    param.tbmqq407List.push(
                        {
                            picturenum: dd.picturenum,
                            picturetype: "1",
                            picturename: dd.picturename,
                            matrlno: dd.matrlno,
                            pictureurl: dd.pictureurl
                        }
                    )
                })

                ajax(
                    url.initBasicPic,
                    param,
                    function (resp) {

                        dialog.success("分配成功");
                        //_this.isSelected(d, type, "Y");
                    },

                    function (err){
                        dialog.error(err);
                        d.isSelect = "N";
                    }
                )
            },

            //
            pageChange2: function (pageIndex) {
                this.pageIndex2 = pageIndex;
                this.searchSupplierCore(this.param2);
            },

            sizeChange2: function (pageSize) {
                this.pageSize3 = pageSize;
                this.searchSupplierCore(this.param2);
            },

            searchSupplier: function(var1){

                var self = this;
                self.pageIndex2 = 1;
                self.pageSize2 = 10;
                self.param2 = var1;
                self.searchSupplierCore();
            },

            searchSupplierCore:function () {
                var self = this;
                self.param = $.extend(self.param2, {
                    pageIndex: self.pageIndex2,
                    pageSize: self.pageSize2,
                });
                ajax(
                    url.searchSupplier,
                    self.param,
                    function (resp) {

                        $.each(resp.items, function(i, d){
                            d.isSelect = "N";
                        })

                        self.dataList2 = resp.items;
                        self.totalCount2= resp.totalCount;
                    }
                )
            },

            openSupplierDialog: function(){

                var _this = this;
                if (_this.multipleSelection.length <= 0) {
                    dialog.error("请选择数据");
                    return;
                }

                _this.showSupplierDialog = true;
                _this.searchSupplier();
            },

            closeSupplierDialog: function(){
                var _this = this;
                _this.showSupplierDialog = false;
                _this.pageIndex2 = 1;
                _this.pageSize2 = 10;
            },


            //
            pageChange3: function (pageIndex) {
                this.pageIndex3 = pageIndex;
                this.searchScopeCore(this.param3);
            },

            sizeChange3: function (pageSize) {
                this.pageSize3 = pageSize;
                this.searchScopeCore(this.param3);
            },

            searchScope: function(var1){

                var self = this;
                self.pageIndex3 = 1;
                self.pageSize3 = 10;
                self.param3 = var1;
                self.searchScopeCore();
            },

            searchScopeCore: function () {

                var self = this;

                self.param3 = $.extend(self.param3, {
                    pageIndex: self.pageIndex3,
                    pageSize: self.pageSize3,
                });
                ajax(
                    url.getScopeList,
                    self.param3,
                    function (resp) {

                        $.each(resp.items, function(i, d){
                            d.isSelect = "N";
                        })

                        self.dataList3 = resp.items;
                        self.totalCount3 =resp.totalCount;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            openScopeDialog: function(){
                var _this = this;

                var _this = this;
                if (_this.multipleSelection.length <= 0) {
                    dialog.error("请选择数据");
                    return;
                }

                _this.showScopeDialog = true;
                _this.searchScope();
            },

            closeScopeDialog: function(){
                var _this = this;
                _this.showScopeDialog = false;
                _this.pageIndex3 = 1;
                _this.pageSize3 = 10;
            },

            delPic: function(){

                var _this = this;

                if (this.multipleSelection.length <= 0) {
                    dialog.error("请选择数据");
                    return;
                }

                var param = {
                    tbmqq407KeyList: []
                }

                $.each(this.multipleSelection, function(i, d){

                    param.tbmqq407KeyList.push(
                        {
                            picturenum: d.picturenum,
                            matrltm:d.matrltm
                        }
                    )
                })


                ajax(
                    url.delBasicPic,
                    param,
                    function () {
                        dialog.success("删除成功", function(){
                            _this.doSearch();
                        });
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            isSelected: function(d, type, isSelect){

                var _this = this;

                if (type == 'A') {

                    $.each(_this.dataList2, function(i, dd){
                        if (dd.supplierno == d.supplierno) {
                            dd.isSelect = isSelect;

                            console.log(dd);
                        }
                    })

                }else{
                    $.each(_this.dataList3, function(i, dd){
                        if (dd.tendarea == d.tendarea) {
                            dd.isSelect = isSelect;
                        }
                    })

                }

            }
        }

    });

    win.app = app
})(window||{});