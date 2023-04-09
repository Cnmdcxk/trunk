(function () {
    var RZ=new Vue({
        el:'#RZ',
        data:{
            tableData: [],
            multipleSelection: [],
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            count1:0,
            count2:0,
            count3:0,
            count4:0,
            id:0,
            param: {},
            param1:{}
        },


        mounted: function () {
            this.search();
            this.getTapCount();
        },

        methods:{
            getTapCount:function (){
                var self=this;
                ajax(
                    "/api/mall/count/getTabCount/",
                    self.param,
                    function (resp) {
                        self.count1=resp.count3;
                        self.count2=resp.count4;
                        self.count3=resp.count2;
                        self.count4=resp.count1;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
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
                    isOverdue:'Y'
                });
                ajax(
                    "/api/mall/count/getCountPonoInfo/",
                    self.param,
                    function (resp) {
                        resp.items.map(item => {
                            item.goodslist = [];
                        });
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            pageChange1: function (row,pageIndex1) {
                row.pageIndex1 = pageIndex1;
                this.doSearchDetail(row);
            },

            sizeChange1: function (row,pageSize1) {
                row.pageSize1 = pageSize1;
                this.doSearchDetail(row);
            },
            searchDetail:function (row) {
                var self = this;

                row.pageIndex1 = 1;
                row.pageSize1 = 10;
                self.doSearchDetail(row);
            },
            doSearchDetail:function (row) {
                var self=this;
                self.loading=true;
                var param = {
                    pageIndex: row.pageIndex1,
                    pageSize: row.pageSize1,
                    pono:row.pono
                };
                ajax(
                    "/api/mall/count/getPonoDetailInfo/",
                    param,
                    function(json){
                        row.goodslist= json.items;
                        row.totalCount1=json.totalCount;
                    }, function(err){
                        dialog.error(err);
                    })
            },
            expandChange: function(row){
                var self=this;
                if (row.goodslist == null || row.goodslist.length <= 0) {
                    self.searchDetail(row);
                }
            },
            handleSelectionChange:function(val) {
                this.multipleSelection = val;
            },

            exportExcel:function (row) {
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                    orderBy: _this.orderBy,
                    asc: _this.asc,
                    pono:row.pono,
                    page:'A'
                });
                exportFn("/api/mall/count/exportCountPonoDetailInfo/", _param, "明细导出")
            },

        }
    })
})();