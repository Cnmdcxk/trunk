(function (win) {

    var url = {
        batchUpdatePrice: "/api/mall/GoodsListed/batchUpdatePrice/",
        getImportGoodTemp:"/api/mall/GoodsListed/getImportGoodTemp/",
        importGoodInfo: "/api/mall/GoodsListed/importGoodInfo/",
        batchApproval: "/api/mall/GoodsListed/batchApproval/",
        createGroup: "/api/mall/goodsGroup/createGroup/",
        matchPic: "/api/mall/GoodsListed/batchMatchPic/",
        getGoodsListedList:"/api/mall/GoodsListed/getGoodsListedList/",
        batchUpdateApply: "/api/mall/GoodsListed/batchUpdateApply/"
    };

    var app = new Vue({
        el: '#RZ',
        data: {

            tableData: [],
            totalCount: 0,

            tabList: [
                {key: "", value:"", count: 0},
                {key: "", value:"", count: 0},
                {key: "", value:"", count: 0}
            ],

            tab: "",

            filtersConfig: {
                searchKey: {placeholder: '商品名称／物料编码／物料条码／品牌／型规', value: ''},
                selects: [
                    {key: 'createDate', type: 'date-range', name: '创建时间'},
                    {key: 'updateDate', type: 'date-range', name: '更新时间'},
                    {key: 'hasPic', type: 'single-change', name: '是否上传图片'},
                    {key: 'statusList', type: 'more-change', name: '状态'},
                ],
                lines: [
                    {key: 'categoryPk', type: 'single-change', name: '商品分类', },
                    {key: 'brandList', type: 'more-change', name: '品牌',},
                ]
            },

            filters: {
                categoryList: [],
                brandList: [],
                hasPic: [{key:"Y",value: "是"},{key:"N",value: "否"}],
                suppliername: [],
                status: []
            },

            param: {},
            pageIndex: 1,
            pageSize: 10,

            selectedItem: [],

            showUpdatePriceDialog: false,
            updatePriceRange: "",
            addOrSub: "1",

            showImportDialog: false,
            importBatchCode: "",

            showMatchPicDialog: false,
            isMatch: '0',

            rejectReason: "",
            showAuditDialog: false,
            rejectOrAgree: "N",

            applyGoodList: [],
            applyReason: "",
            showDialogUpdateApply: false,


        },
        mounted: function () {
            this.doSearch();
            this.getTabCount();
        },

        methods: {

            getTabCount: function(){
                var _this = this;
                ajax(
                    "/api/mall/GoodsListed/getTabCount/",
                    {page: "S"},
                    function (resp) {

                        _this.tabList[0].count = resp.TOTALCOUNT;
                        _this.tabList[1].count = resp.TOTALCOUNT1;
                        _this.tabList[2].count = resp.TOTALCOUNT2;
                    },

                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            selectTab: function(d){

                this.tab = d;
                this.$refs.filters.clearParam();
                this.search();
            },

            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.doSearch();
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.doSearch();
            },

            search: function (var1) {
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
                    page: "S",
                    status:self.status,
                    tapStatus:self.tab
                });

                ajax(
                    url.getGoodsListedList,
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.categoryPk = resp.resultMap.categoryPkList;
                        self.filters.brandList = resp.resultMap.brandList;
                        self.filters.statusList = resp.resultMap.statusList;
                        self.filters.suppliername = resp.resultMap.supplierList;
                        self.selectedItem = [];

                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },


            toUpdate: function(goodId){
                formForward("/mall/goodEdit/"+goodId, {}, "_self", "get")
            },

            //单个提交审核确认
            signConfirmApproval: function(goodId){
                var _this = this;
                _this.batchApproval([goodId]);
            },


            selectionChange: function(d){
                this.selectedItem = d;
            },

            // 批量修改价格
            openUpdatePriceDialog: function(){
                var _this = this;

                if (_this.selectedItem.length <= 0 ) {
                    dialog.error("请选中数据");
                    return;
                }

                for (var i=0; i < _this.selectedItem.length; i++) {

                    var item = _this.selectedItem[i];

                    if (!(item.status == '10' || item.status=='15' || item.status == '30')) {
                        dialog.error("只有草稿、未上架、上架驳回状态的商品可以调价");
                        return;
                    }
                }

                _this.showUpdatePriceDialog = true;
            },

            closeUpdatePriceDialog: function(){
                this.addOrSub = "1";
                this.updatePriceRange = "";
                this.showUpdatePriceDialog = false;
            },

            checkInput: function(){

                this.updatePriceRange = (this.updatePriceRange.toString().match(/^\d*(\.?\d{0,4})/g)[0]) || '';
            },

            batchUpdatePrice: function(){
                var _this = this;
                var goodIdList = [];

                $.each(_this.selectedItem, function(i, d){
                    goodIdList.push(d.goodid);
                });

                ajax(url.batchUpdatePrice,
                    {
                        goodIdList: goodIdList,
                        addOrSub: _this.addOrSub,
                        updatePriceRange: _this.updatePriceRange
                    }, function(){

                    dialog.success("调价成功", function(){
                        _this.closeUpdatePriceDialog();
                        _this.doSearch();
                    })

                }, function(err){
                    dialog.error(err);
                })
            },

            //导入商品信息
            downloadExportTemp: function(){

                var _this = this;
                var _param = $.extend(_this.param, {
                    pageSize: 5000,
                    pageIndex: 1,
                    page: "S",
                    statusList: ['10', '15', '30']
                });

                exportFn(url.getImportGoodTemp, _param, "导入商品明细模版");

            },

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

                ajax(url.importGoodInfo,
                    {
                        batchCode: resp.batchCode,
                        fileUrl: encodeURI(resp.url),

                    }, function(resp){

                        _this.loadingInstance.text = "保存成功...";
                        _this.loadingInstance.close();
                        _this.$refs.upload.clearFiles();
                        _this.closeImportDialog();
                        _this.doSearch();


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

            //
            openMatchPicDialog: function(){
                var _this = this;

                if (_this.selectedItem.length <= 0 ) {
                    dialog.error("请选中数据");
                    return;
                }

                //for (var i=0; i < _this.selectedItem.length; i++) {
                //
                //    var item = _this.selectedItem[i];
                //
                //    if (!(item.status == '10'
                //        || item.status=='15'
                //        || item.status == '30'
                //        || item.status == '40'
                //        )) {
                //
                //        dialog.error("部分状态商品不能匹配图片");
                //        return;
                //    }
                //}

                _this.showMatchPicDialog = true;
            },

            closeMatchPicDialog: function(){
                this.showMatchPicDialog = false;
            },

            batchMatchPic: function(){

                var _this = this;
                var goodIdList = [];

                for (var i=0; i < _this.selectedItem.length; i++) {
                    goodIdList.push(_this.selectedItem[i].goodid);
                };

                ajax(url.matchPic, {goodIdList: goodIdList, cover: _this.isMatch}, function(){

                    dialog.success("匹配图片成功", function(){

                        _this.closeMatchPicDialog();
                        _this.doSearch();
                    });

                }, function(err){
                    dialog.error(err);
                })



            },


            //导出明细
            exportExcel: function(){
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                    page:'S'
                });
                exportFn("/api/mall/GoodsListed/exportGoodsListedList/", _param, "销售商品挂牌明细导出")
            },


            //创建商品组
            createGroup: function(){

                var _this = this;
                var goodIdList = [];

                if (_this.selectedItem.length <= 0 ) {
                    dialog.error("请选中数据");
                    return;
                }

                for (var i=0; i < _this.selectedItem.length; i++) {

                    var item = _this.selectedItem[i];

                    if (item.groupno != null) {
                        dialog.error("已有商品组号不可在创建商品组");
                        return;
                    }

                    goodIdList.push(_this.selectedItem[i].goodid);
                }

                dialog.confirm(
                    "已选"+_this.selectedItem.length+"条商品，是否确认生成商品组",
                    function(){
                        ajax(url.createGroup, {goodIdList: goodIdList}, function(){

                            dialog.success("创建商品组成功", function(){
                                _this.doSearch();
                            })

                        }, function(err){
                            dialog.error(err);
                        });
                    }
                );
            },

            //批量提交审核确认
            toBatchConfirmApproval: function(){
                var _this = this;
                var goodIdList = [];

                if (_this.selectedItem.length <= 0 ) {
                    dialog.error("请选中数据");
                    return;
                }

                for (var i=0; i < _this.selectedItem.length; i++) {

                    var item = _this.selectedItem[i];

                    if (!(item.status == '10'
                        || item.status=='15'
                        || item.status == '30'
                        )) {

                        dialog.error("只有草稿，未上架，上架驳回才可以上架申请");
                        return;
                    }

                    goodIdList.push(_this.selectedItem[i].goodid);
                }

                _this.batchApproval(goodIdList);
            },

            openSignDialogUpdateApply: function(goodId){

                this.applyGoodList = [goodId];
                this.showDialogUpdateApply = true;

            },

            openBatchDialogUpdateApply: function(){
                var _this = this;
                var goodIdList = [];

                if (_this.selectedItem.length <= 0 ) {
                    dialog.error("请选中数据");
                    return;
                }

                for (var i=0; i < _this.selectedItem.length; i++) {

                    var item = _this.selectedItem[i];


                    if (! (item.status == '25' || item.status=='40') ) {
                        dialog.error("只有已上架和下架驳回才可以下架申请");
                        return;
                    }

                    goodIdList.push(_this.selectedItem[i].goodid);
                }


                _this.applyGoodList = goodIdList;
                _this.showDialogUpdateApply = true;
            },

            closeDialogUpdateApply: function(){
                this.showDialogUpdateApply = false;
                this.applyReason = "";
                this.applyGoodList = [];

            },

            //下架申请
            batchUpdateApply: function(){

                var _this = this;
                ajax(url.batchUpdateApply, {goodIdList: _this.applyGoodList, applyReason: _this.applyReason}, function(){

                    dialog.success("下架申请成功", function(){
                        _this.closeDialogUpdateApply();
                        _this.doSearch();
                    });

                }, function(err){
                    dialog.error(err);
                })
            },

            //上架申请
            batchApproval: function(goodIdList){

                var _this = this;

                dialog.confirm(
                    "是否确认上架申请",

                    function(){

                        ajax(url.batchApproval, {goodIdList: goodIdList}, function(){

                            dialog.success("上架申请成功", function(){
                                _this.doSearch();
                            });

                        }, function(err){
                            dialog.error(err);
                        })

                    }
                )
            },

            toPreview: function (goodId) {
                formForward("/mall/goodPreview", {goodsId: goodId}, "_blank", "get");
            },

            openAuditDialog: function(d){
                this.rejectReason = d.rejectreason;
                this.showAuditDialog = true;
            },

            closeAuditDialog: function(){
                this.showAuditDialog = false;
                this.rejectReason = "";
            },


        },

    })

    window.app = app;
})(window || {});