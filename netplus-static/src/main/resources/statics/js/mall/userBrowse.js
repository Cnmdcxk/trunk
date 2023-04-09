(function (win) {

    var url = {
        getGoodBrowse: "/api/mall/getGoodBrowse/"
    };

    var app = new Vue({

        el: "#app",
        data: {

            totalCount: 0,
            pageIndex: 1,
            pageSize: 10,
            tableData: [],


            param: {},

            filters: {
                pono:[],
                matrltm: []
            },

            filtersConfig: {

                searchKey: {placeholder: '商品名称', value: ''},
                lines: [
                    {key: "pono", type: "more-change", name:"协议号"},
                    {key: "matrltm", type: "more-change", name:"物料条码"},
                ],
                selects: [
                    {key: "createTime", type: "date-range", name:"创建时间"}
                ]
            }

        },

        mounted: function () {

            this.search();

        },

        methods: {

            pageChange: function(pageIndex){

                this.pageIndex = pageIndex;
                this.search();
            },

            sizeChange: function(pageSize){
                this.pageSize = pageSize;
                this.search();
            },


            searchCore: function(param){

                var _this = this;
                _this.param = param;
                _this.pageIndex = 1;
                _this.search();
            },

            search: function(){

                var _this  = this;

                var p = $.extend(_this.param, {pageSize: _this.pageSize, pageIndex: _this.pageIndex})

                ajax(
                    url.getGoodBrowse,
                    p,
                    function(json){

                        _this.tableData = json.items;
                        _this.filters.pono = json.resultMap.pono;
                        _this.filters.matrltm = json.resultMap.matrltm;
                        _this.totalCount = json.totalCount;

                    },
                    function(err){
                        dialog.success(err);
                    }
                )

            },

        }

    });


})(window || {});