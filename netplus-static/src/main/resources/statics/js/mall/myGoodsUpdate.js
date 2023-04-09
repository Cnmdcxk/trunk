(function(win){

    var app = new Vue({
        el:'#RZ',
        data: {

            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            needMyCount: 0,
            myCount: 0,
            showDialogUpdateApply1:false,
            tableData:[],
            multipleSelection: [],
            filtersConfig: {
                searchKey: {placeholder: '商品名称 / 品牌 / 货号 / 物料号 / 合同号 / 供应商  ', value: ''},
                selects: [
                    {key: 'createDate', type: 'date-range', name: '创建时间', },
                    {key: 'updateDate', type: 'date-range', name: '更新时间', },
                    {key: 'isUpPicture', type: 'single-change', name: '是否上传图片', },
                    {key: 'suppliername', type: 'single-change', name: '供应商', },
                    {key: 'status', type: 'single-change', name: '状态', },
                    {key: 'taxException', type: 'single-change', name: '价税异常', },
                ],
                lines: [
                    {key: 'categoryName', type: 'single-change', name: '商品分类', },
                    {key: 'brand', type: 'single-change', name: '品牌', },
                ]
            },
            filters:{
                categoryName: [],
                brand: [],
                isUpPicture: [{key: "Y",value: "是"},{key: "N",value: "否"}],
                taxException: [{key: "ZC",value: "正常"},{key: "YC",value: "异常"}],
                suppliername: [],
                status: []
            },

            deptNo: "",

            rejectReason: "",
            showAuditDialog: false,
            rejectOrAgree: "N",

            showDialogUpdateApply: false,
            applyReason: "",
            auditUser:"",
            updateuser:"",
            showReDialog:false,


        },
        mounted: function () {
            this.getCurrentUser();
        },

        methods:{
            getCurrentUser: function () {
                var self = this;
                ajax('/api/provider/getCurrentUser/',
                    {loading: false},
                    function (resp) {

                        self.deptNo = resp.deptNo;
                        self.search();
                        self.getTabCount();

                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            audit: function(){

                var _this = this;
                var goodIdList = [];

                if (_this.rejectOrAgree == "") {
                    dialog.error("请选择驳回或同意");
                    return;
                }

                if (_this.rejectOrAgree == 'N' && _this.rejectReason == "") {
                    dialog.error("驳回原因不能为空");
                    return;
                }
                $.each(_this.multipleSelection, function(i, d){
                    goodIdList.push(d.goodid);
                });

                var param = {
                    rejectReason: _this.rejectReason,
                    rejectOrAgree: _this.rejectOrAgree,
                    auditRole: "A",
                    biz:"B",
                    goodIdList: goodIdList
                }

                ajax("/api/mall/GoodsListed/batchAudit/", param, function(json){
                    _this.closeAuditDialog();
                    dialog.success("审核成功", function(){
                        _this.doSearch();
                    });

                }, function(err){
                    dialog.error(err);
                })
            },

            audit1: function(){

                var _this = this;
                var goodIdList = [];

                if (_this.rejectOrAgree == "") {
                    dialog.error("请选择驳回或同意");
                    return;
                }

                if (_this.rejectOrAgree == 'N' && _this.rejectReason == "") {
                    dialog.error("驳回原因不能为空");
                    return;
                }
                goodIdList.push(this.goodid);

                var param = {
                    rejectReason: _this.rejectReason,
                    rejectOrAgree: _this.rejectOrAgree,
                    auditRole: "A",
                    biz:"B",
                    goodIdList: goodIdList
                }

                ajax("/api/mall/GoodsListed/batchAudit/", param, function(json){
                    console.log("213")
                    _this.closeAuditDialog1();
                    dialog.success("审核成功", function(){
                        _this.doSearch();
                    });

                }, function(err){
                    dialog.error(err);
                })
            },

            audit1: function(){

                var _this = this;
                var goodIdList = [];

                if (_this.rejectOrAgree == "") {
                    dialog.error("请选择驳回或同意");
                    return;
                }

                if (_this.rejectOrAgree == 'N' && _this.rejectReason == "") {
                    dialog.error("驳回原因不能为空");
                    return;
                }
                goodIdList.push(this.goodid);

                var param = {
                    rejectReason: _this.rejectReason,
                    rejectOrAgree: _this.rejectOrAgree,
                    auditRole: "A",
                    biz:"B",
                    goodIdList: goodIdList
                }

                ajax("/api/mall/GoodsListed/batchAudit/", param, function(json){
                    console.log("213")
                    _this.closeAuditDialog1();
                    dialog.success("审核成功", function(){
                        _this.search();
                    });

                }, function(err){
                    dialog.error(err);
                })
            },
            handleSelectionChange:function(val) {
                this.multipleSelection = val;
            },
            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.doSearch(this.param);
            },
            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.doSearch(this.param);
            },
            search:function(var1){
                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.param = var1;

                self.doSearch();
            },

            doSearch:function(){
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    page: "B"
                });


                self.param.cgStatusList = ["ZYSHTG", "ZYSHBH", "ZZSHTG", "ZZSHBH","ZYSHZ"];


                ajax(
                    "/api/mall/GoodsListed/getGoodsListedList/",
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.categoryName = resp.resultMap.categoryNameList;
                        self.filters.brand = resp.resultMap.brandList;
                        self.filters.status = resp.resultMap.statusList;
                        self.filters.suppliername = resp.resultMap.supplierList;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },


             getTabCount: function(){
                 var _this = this;
                 ajax(
                     "/api/mall/GoodsListed/getTabCount/",
                     {page: "B"},
                     function (resp) {

                         _this.needMyCount = resp.TOTALCOUNT8;
                         _this.myCount = resp.TOTALCOUNT9;
                    },

                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            openAuditDialog: function(){

                if (this.multipleSelection.length <= 0) {
                    dialog.error("请选中数据");
                }

                for(var v=0;v<this.multipleSelection.length;v++) {

                    if(this.multipleSelection[v].status !='ZYSHZ') {
                        dialog.error("只有组员审核中状态才能审核");
                        return;
                    }
                }

                this.showAuditDialog = true;
            },
            closeAuditDialog: function(){
                this.showAuditDialog = false;
                this.rejectReason = "";
            },
            openReDialog: function(d,e,v){
                this.rejectReason=d;
                this.auditUser=e;
                this.updateuser=v;
                this.showReDialog = true;
            },
            lookApplyReason1: function(w, d,z){
                this.rejectReason=w;
                this.auditUser = d;
                this.updateuser=z;
                this.showDialogUpdateApply1= true;
            },
            toPreview: function (goodId) {
                formForward("/mall/goodPreview", {goodsId: goodId}, "_blank", "get");
            },

            exportExcel:function () {
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                    page: "B"
                });
                exportFn("/api/mall/GoodsListed/exportGroupGoodsUpdate/", _param, "已审核商品修改审核明细导出")
            },

            lookApplyReason: function(d,w){
                this.applyReason = d;
                this.auditUser=w;
                this.showDialogUpdateApply = true;
            },


        },

    });

    win.app = app;

})(window || {})