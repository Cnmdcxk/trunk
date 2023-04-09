(function(win){

    var url = {
        getPage: "/api/mall/GoodsInventory/getPage/",
        getMallGoodsInventoryDetailPage: "/api/mall/GoodsInventory/getMallGoodsInventoryDetailPage/",
    };

    var app = new Vue({
        el:'#RZ',
        data: {
            tableData:[],
            searchTime: '',
            filtersConfig: {
                searchKey: {placeholder: '物料条码', value: ''},
                selects: [],
                lines: []
            },
            filters:{},
            totalCount: 0,
            pageIndex: 1,
            pageSize: 10,
            param: {},
            showDetail: false,
            mallInventoryDetail:{
                matrlId: '',
                tableData:[],
                totalCount: 0,
                pageIndex: 1,
                pageSize: 10,
            },
        },
        mounted:function(){
            this.searchCore();
        },
        methods:{
            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.searchCore();
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.searchCore();
            },

            search: function(var1) {
                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.param = var1;

                self.searchCore();
            },

            searchCore: function () {
                var self = this;
                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                });
                ajax(
                    url.getPage,
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                        self.searchTime = resp.resultMap.searchTime;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            mallInventoryDetailPageChange: function (pageIndex) {
                this.mallInventoryDetail.pageIndex = pageIndex;
                this.searchMallInventoryDetail();
            },

            mallInventoryDetailSizeChange: function (pageSize) {
                this.mallInventoryDetail.pageSize = pageSize;
                this.searchMallInventoryDetail();
            },

            showMallInventoryDetail: function (matrlId){
                this.mallInventoryDetail={
                    matrlId: matrlId,
                    tableData:[],
                    totalCount: 0,
                    pageIndex: 1,
                    pageSize: 10,
                };
                this.searchMallInventoryDetail();
            },

            searchMallInventoryDetail: function (){
                var self = this;
                ajax(
                    url.getMallGoodsInventoryDetailPage,
                    {
                        matrlId: self.mallInventoryDetail.matrlId,
                        pageIndex: self.mallInventoryDetail.pageIndex,
                        pageSize: self.mallInventoryDetail.pageSize,
                    },
                    function (resp) {
                        self.mallInventoryDetail.tableData = resp.items;
                        self.mallInventoryDetail.totalCount = resp.totalCount;
                        self.showDetail = true;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            }

        },

    });

    win.app = app;

})(window || {})