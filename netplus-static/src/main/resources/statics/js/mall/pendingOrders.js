(function () {
    var RZ=new Vue({
        el:'#RZ',
        data:{
            dataList: [],
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            count1:0,
            count2:0,
            count3:0,
            count4:0,
            param: {},
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
                    statusList:['15']
                });
                ajax(
                    "/api/mall/count/getCountGoodInfo/",
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
            exportExcel:function () {
                var _this = this;
                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                    orderBy: _this.orderBy,
                    asc: _this.asc,
                    statusList:['15'],
                    page:'A',
                });
                exportFn("/api/mall/count/exportCountGoodInfo/", _param, "待接单明细导出")
            },
        }
    })
})();