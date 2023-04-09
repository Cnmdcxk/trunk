(function(win){

    var app = new Vue({
        el:'#RZ',
        data:{
            status:status,
            statusName:'下架确认中',
            needMyCount: 0,
            myCount:0,
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            tableData:[],
            statusList:[],
            showReDialog:false,
            multipleSelection: [],
            filtersConfig: {
                searchKey: {placeholder: '商品名称 / 品牌 / 物料编码 / 物料条码 / 协议号 / 供应商  ', value: ''},
                selects: [
                    {key: 'createDate', type: 'date-range', name: '创建时间', },
                    {key: 'updateDate', type: 'date-range', name: '更新时间', },
                    {key: 'isUpPicture', type: 'single-change', name: '是否上传图片', },
                ],
                lines: [
                    {key: 'categoryPk', type: 'single-change', name: '商品分类', },
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

            auditUser:"",
            updateuser:"",
            showAuditDialog: false,
            rejectReason: "",
            applyReason:"",
            rejectOrAgree: "",
            auditAdvice:""
        },

        mounted: function () {
            this.getCurrentUser();
            console.log(this.status)
            if (this.status !='' && this.status != undefined){
                this.$refs.filters.addParam("statusList", [this.status], this.statusName, true);
            }
            this.search(this.$refs.filters.__getParam());
        },

        methods:{

            getCurrentUser: function () {
                var self = this;
                ajax('/api/provider/getCurrentUser/',
                    {loading: false},
                    function (resp) {

                        self.deptNo = resp.deptNo;
                        self.search()

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

                self.param.cgStatusList = ["35"];

                ajax(
                    "/api/mall/GoodsListed/getGoodsListedList/",
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.categoryPk = resp.resultMap.categoryPkList;
                        self.filters.brand = resp.resultMap.brandList;
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
            openReDialog: function(applyreason,audituser,auditusername){
                this.applyReason=applyreason;
                this.auditUser=audituser;
                this.updateuser=auditusername;
                this.showReDialog = true;
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
                    biz:"B",
                    rejectReason: _this.rejectReason,
                    rejectOrAgree: _this.rejectOrAgree,
                    goodIdList: goodIdList
                }

                ajax("/api/mall/GoodsListed/batchConfirm/", param, function(json){

                    _this.closeAuditDialog();
                    if(_this.rejectOrAgree=="Y"){
                        dialog.success("商品已下架商城", function(){
                            _this.doSearch();
                        });
                    }else {
                        dialog.success("商品下架申请已被驳回", function(){
                            _this.doSearch();
                        });
                    }

                }, function(err){
                    dialog.error(err);
                })
            },

            toPreview: function (goodId) {
                formForward("/mall/goodPreview", {goodsId: goodId}, "_blank", "get");
            },

            exportGoodsAudit:function () {
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                });

                exportFn("/api/mall/GoodsListed/exportConfirm/", _param, "商品下架确认明细导出")

            }

        },

    });

})(window || {})