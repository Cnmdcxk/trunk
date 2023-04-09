(function (win) {

    var url = {
        getList:"/api/provider/getBusinessLogPage",
        exportDetail:"/api/provider/exportBusinessLog",
    }

    var app = new Vue({
        el:'#RZ',
        data:{

            dataList: [],
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,

            filters:{
                logType:[],
                createDate:[],
            },
            filtersConfig: {
                searchKey: {placeholder: '描述/业务操作人', value: ''},
                selects: [
                    {key: 'createDate', type: 'date-range', name: '创建时间', },

                ],
                lines: [
                    {key: 'logType', type: 'single-change', name: '日志类型'},
                ],
            },
            param: {},

        },

        mounted: function () {
            console.log("11111111111111111111");
            this.search();
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

            search: function(var1){
                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.param = var1;
                self.searchCore();
            },

            searchCore: function (){
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                });

                ajax(
                    url.getList,
                    self.param,
                    function (resp) {
                        console.log(resp);
                        self.dataList = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.logType = resp.resultMap.typeKeyValues;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            exportDetail: function (){
                console.log("exportDetail");
                var self = this;

                var _param = $.extend(self.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                });

                exportFn(url.exportDetail, _param, "业务日志明细导出")
            },


        }
    });

    win.app = app;
})(window);