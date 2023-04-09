(function(win){

    var app = new Vue({
        el:'#RZ',
        data: {

            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            needMyCount: 0,
            showDialogUpdateApply1:false,
            myCount: 0,
            showDisplay:false,
            showReDialog:false,
            goodId:'',
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
            auditUser:"",
            showAuditDialog: false,
            rejectOrAgree: "N",
            updateuser:"",

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

            lookApplyReason1: function(w,v,z){

                console.log(w,v,z)
                this.rejectReason=w;
                this.auditUser=v;
                this.updateuser=z;
                this.showDialogUpdateApply1= true;
            },
            doSearch:function(){
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    page: "B"
                });


                self.param.cgStatusList = ["CGSHTG", "CGSHBH", "YSJ", "YBH","CGSHZ"];


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

                        _this.needMyCount = resp.TOTALCOUNT4;
                        _this.myCount = resp.TOTALCOUNT5;

                    },

                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            openReDialog: function(d,e,v){
                this.rejectReason=d;
                this.auditUser=e;
                this.updateuser=v;
                this.showReDialog = true;
            },
            openAuditDialog: function(){
                var _this=this;
                var flag=true;
                for(var i=0;i<_this.multipleSelection.length;i++){
                    if(_this.multipleSelection[i].status!="CGSHZ") {
                        dialog.error("已经审核的不能再审核");
                        flag=false;
                        break;
                    }

                }
                this.showAuditDialog = flag;
            },

            closeAuditDialog: function(){
                this.showAuditDialog = false;
                this.rejectReason = "";
            },

            toPreview: function (goodId) {
                formForward("/mall/goodPreview", {goodsId: goodId}, "_blank", "get");
            },

            exportExcel:function () {
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                    page: "A"
                });
                exportFn("/api/mall/GoodsListed/exportCheckedGoodsListedList/", _param, "已审核采购商品挂牌查询明细导出")

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

                console.log(_this.multipleSelection.length+"-----"+_this.multipleSelection[0])
                for(var i=0;i<_this.multipleSelection.length;i++){
                    if(_this.multipleSelection[i].status!="CGSHZ") {
                        goodIdList=[];
                        break;
                    }
                    goodIdList.push(_this.multipleSelection[i].goodid);
                }
                if(goodIdList.length==0){
                    dialog.error("所选中的有非采购审核中的状态");
                    return;
                }

                var param = {
                    rejectReason: _this.rejectReason,
                    rejectOrAgree: _this.rejectOrAgree,
                    auditRole: "A",
                    biz:"A",
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
            }
        },

    });

    win.app = app;

})(window || {})