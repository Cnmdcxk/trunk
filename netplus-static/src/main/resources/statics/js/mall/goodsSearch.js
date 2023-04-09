var RZ=new Vue({
    el:'#RZ',
    data:function(){
        return {

            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            tableData:[],

            auditusername: "",
            rejectReason: "",
            showAuditDialog: false,
            rejectOrAgree: "N",

            filtersConfig: {
                searchKey: {placeholder: '商品名称 / 品牌 / 物料条码 / 物料编码 / 协议号', value: ''},
                selects: [
                    {key: 'createDate', type: 'date-range', name: '创建时间'},
                    {key: 'updateDate', type: 'date-range', name: '更新时间'},
                    {key: 'isUpPicture', type: 'single-change', name: '是否上传图片'},
                    {key: 'statusList', type: 'more-change', name: '状态'},
                    {key: 'agentList', type: 'more-change', name: '物资采购人'},

                ],
                lines: [
                    {key: 'categoryPk', type: 'single-change', name: '商品分类',},
                    {key: 'brandList', type: 'more-change', name: '品牌',},
                    {key: 'supplierNoList', type: 'more-change', name: '供应商',},
                ]
            },
            filters:{
                categoryPk: [],
                agentList:[],
                brandList: [],
                isUpPicture: [{key: "Y",value: "是"},{key: "N",value: "否"}],
                supplierNoList: [],
                statusList: []
            }
        }
    },

    mounted: function(){
        this.search();
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
                page: "A"
            });

            ajax(
                "/api/mall/GoodsListed/getGoodsListedList/",
                self.param,
                function (resp) {
                    self.tableData = resp.items;
                    self.totalCount = resp.totalCount;
                    self.filters.categoryPk = resp.resultMap.categoryPkList;
                    self.filters.brandList = resp.resultMap.brandList;
                    self.filters.agentList = resp.resultMap.agent;
                    self.filters.statusList = resp.resultMap.statusList;
                    self.filters.supplierNoList = resp.resultMap.supplierList;

                },
                function (err) {
                    dialog.error(err);
                }
            );
        },

        toPreview: function (goodId) {
            formForward("/mall/goodPreview", {goodsId: goodId}, "_blank", "get");
        },
        exportGoodsAudit:function () {
            var _this = this;
            var _param = $.extend(_this.param, {
                pageIndex: 1,
                pageSize: 20000,
                page: "A"
            });
            var json = JSON.stringify(_param);
            exportDetailForm("/api/mall/GoodsListed/exportNewGoodsListedList/", [["json",json]])
            // window.location.href = "/api/mall/GoodsListed/exportNewGoodsListedList/"
        },
        openAuditDialog: function(d){
            this.rejectReason = d.rejectreason;
            this.auditusername=d.auditusername;
            this.showAuditDialog = true;
        },

        closeAuditDialog: function(){
            this.showAuditDialog = false;
            this.rejectReason = "";
        },


    },

});