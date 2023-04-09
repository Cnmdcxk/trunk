(function (win) {

    var url = {
        getList:"/api/provider/getLoginLogPage",
        exportDetail:"/api/provider/exportLoginLog",
    }

    var app = new Vue({
        el:'#RZ',
        data:{
            searchKey: '',
            dataList: [],
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,

        },

        mounted: function () {
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

            search: function(){
                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.searchCore();
            },

            searchCore: function (){
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    searchKey:self.searchKey,
                });
                console.log(self.param);

                ajax(
                    url.getList,
                    self.param,
                    function (resp) {
                        console.log(resp);
                        self.dataList = resp.items;
                        self.totalCount = resp.totalCount;
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

                exportFn(url.exportDetail, _param, "登录日志明细导出")
            },

        }
    });

    win.app = app;
})(window);