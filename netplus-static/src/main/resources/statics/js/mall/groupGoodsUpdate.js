(function(win){

    var app = new Vue({
        el:'#RZ',
        data:{
            needMyCount: 0,
            myCount:0,
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            showDialogUpdateApply1:false,
            tableData:[],
            updateuser:"",
            multipleSelection: [],
            filtersConfig: {
                searchKey: {placeholder: '商品名称 / 品牌 / 货号 / 物料号 / 合同号 / 供应商  ', value: ''},
                selects: [
                    {key: 'createDate', type: 'date-range', name: '创建时间', },
                    {key: 'updateDate', type: 'date-range', name: '更新时间', },
                    {key: 'isUpPicture', type: 'single-change', name: '是否上传图片', },
                    {key: 'suppliername', type: 'single-change', name: '供应商', },
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
                suppliername: [],
                taxException: [{key:'YC',value:"异常"},{key:'ZC',value:"正常"}]
            },


            deptNo: "",
            param: {},

            showAuditDialog: false,
            rejectReason: "",
            rejectOrAgree: "",

            showDialogUpdateApply: false,
            applyReason: "",
            auditUser:"",
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
                    page: "B",
                });

                self.param.dsStatusList = ["ZYSHTG"];
                self.param.isGroup = "Y";

                ajax(
                    "/api/mall/GoodsListed/getGoodsListedList/",
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.categoryName = resp.resultMap.categoryNameList;
                        self.filters.brand = resp.resultMap.brandList;
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
                    {page:'B', isGroup: "Y"},
                    function (resp) {

                        _this.needMyCount =  resp.TOTALCOUNT10;
                        _this.myCount =  resp.TOTALCOUNT11;

                    },

                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            openAuditDialog: function(){
                if (this.multipleSelection.length <= 0) {
                    dialog.error("请选中数据");
                    return;
                }

                this.showAuditDialog = true;
            },

            closeAuditDialog: function(){
                this.showAuditDialog = false;
                this.rejectReason = "";

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
                    auditRole: "B",
                    biz:"B",
                    goodIdList: goodIdList
                }

                ajax("/api/mall/GoodsListed/batchAudit/", param, function(json){

                    _this.closeAuditDialog();
                    dialog.success("审核成功", function(){
                        _this.search();
                    });

                }, function(err){
                    dialog.error(err);
                })
            },

            toPreview: function (goodId) {
                formForward("/mall/goodPreview", {goodsId: goodId}, "_blank", "get");
            },

            exportGroupExcel:function () {
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                    page:'A'
                });

                exportFn("/api/mall/GoodsListed/exportGroupGoodsUpdate/", _param, "待审核商品修改审核明细导出")

            },

            lookApplyReason: function(d){
                this.applyReason = d;
                this.showDialogUpdateApply = true;
            },
            lookApplyReason1: function(w,v,z){
                this.rejectReason = w;
                this.auditUser=v;
                this.updateuser = z;
                this.showDialogUpdateApply1 = true;
            },

        },

    });

})(window || {})