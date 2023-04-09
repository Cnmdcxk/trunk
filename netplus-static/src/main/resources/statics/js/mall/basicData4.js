(function () {
    var RZ=new Vue({
        el:'#RZ',
        data:{
            dataList: [],
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,

            filters: {
                twolevelclanameList:[],
            },
            filtersConfig: {
                searchKey: {placeholder: '二级分类/ERP物料号/物料描述', value: ''},
                selects: [
                    {key: 'twolevelclanameList', type: 'more-change', name: '二级分类', },
                ],
            },
        },


        mounted: function () {
            this.doSearch();
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
                    orderBy: self.orderBy,
                    asc: self.asc,
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                });
                ajax(
                    "/api/mall/basicData/getMaterialList/",
                    self.param,
                    function (resp) {
                        console.log(resp);
                        self.dataList = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.twolevelclanameList = resp.resultMap.twoLevelClaNameKV;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

        }
    })
})();