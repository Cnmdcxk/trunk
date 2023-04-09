(function () {

    var url = {
        getGoodsGroupList:  "/api/mall/goodsGroup/getGoodsGroupList/",
        getGroupGoodList: "/api/mall/goodsGroup/getGroupGoodList/",
        deleteGoodsGroup: "/api/mall/goodsGroup/deleteGoodsGroup/",
        deleteGoodsFromGroup: "/api/mall/goodsGroup/deleteGoodsFromGroup/",
        batchDeleteGoods: "/api/mall/goodsGroup/batchDeleteGoods/"
    };

    var RZ = new Vue({
        el: '#RZ',
        data: {
            isCheckAll: false,
            isCheckAllGroup: false,
            checked: true,

            tableData: [],

            checkedGoodsId: [],

            allGoodsId: [

            ],

            multipleSelection: [],

            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            filters: {
                categoryNameList: [],
                brandList: []
            },
            filtersConfig: {
                searchKey: {placeholder: '商品名称／品牌／物料条码／ 型规／物料编码', value: ''},
                selects: [
                    {key: 'createTime', type: 'date-range', name: '创建时间'},
                ],
                lines: [
                    {key: 'categoryNameList', type: 'single-change', name: '一级分类'},
                    {key: 'brandList', type: 'more-change', name: '品牌'}
                ]
            },
            param: {}
        },
        mounted: function () {
            this.search();
        },

        methods: {
            handleCheckAllChange: function (val) {
                this.checkedGoodsId = val ? this.allGoodsId : [];
            },

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
                });

                ajax(
                    url.getGoodsGroupList,
                    self.param,

                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.categoryNameList = resp.resultMap.categoryNameList;
                        self.filters.brandList = resp.resultMap.brandList;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            deleteFromGroup: function (goodid,groupno) {
                var self = this;
                console.log(goodid,groupno)
                self.$confirm(
                    "是否确认删除商品组",
                    "提示",
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning',
                        center: true
                    }
                ).then(function(){
                    ajax(url.deleteGoodsFromGroup, {goodid: goodid,groupNo:groupno}, function(){
                        dialog.success("删除成功", function(){
                            window.location.reload();
                            self.search();
                        });
                    }, function(err){
                        dialog.error(err);
                    })
                }).catch(function(){

                });
            },


            deleteGroup: function (groupno) {
                var self = this;
                console.log(groupno)
                dialog.confirm('确定要删除吗', function(){
                    ajax(url.deleteGoodsGroup, {groupNo:groupno}, function(){
                        dialog.success("删除成功", function(){
                            window.location.reload();
                            self.search();
                        });
                    }, function(err){
                        dialog.error(err);
                    })
                });
            },

            batchDeleteGoods: function (multipleSelection) {
                var self = this;
                if (self.multipleSelection.length == 0) {
                    dialog.error("没有选中的数据");
                } else {
                    var group_str= '';
                    for (var i=0;i<multipleSelection.length;i++){
                            group_str = group_str + multipleSelection[i].groupno + ",";
                    }
                    self.$confirm(
                        "是否确认删除商品",
                        "提示",
                        {
                            confirmButtonText: '确定',
                            cancelButtonText: '取消',
                            type: 'warning',
                            center: true
                        }
                    ).then(function(){
                        ajax(url.batchDeleteGoods, {groupno:group_str}, function(){
                            dialog.success("删除成功", function(){
                                window.location.reload();
                                self.search();
                            });
                        }, function(err){
                            dialog.error(err);
                        })
                    }).catch(function(){

                    });
                // },
                    // var param = {
                    //     'goodsIds': ids_str
                    // }
                    // ajax(
                    //     url.batchDeleteGoods,
                    //     param,
                    //     function (resp) {
                    //         dialog.success("批量删除成功");
                    //         self.search();
                    //         self.isCheckAll = false;
                    //
                    //     },
                    //     function (err) {
                    //         dialog.error(err);
                    //     }
                    // );
                }
            },


            expandChange: function(row){
                var _this = this;
                var param = $.extend(_this.param, {groupNo: row.groupno})
                if (row.goodslist == null || row.goodslist.length <= 0) {

                    ajax(url.getGroupGoodList, param, function(json){
                        row.goodslist = json;
                    }, function(err){
                        dialog.error(err);
                    })
                }
            },

            handleSelectionChange:function(val) {
                this.multipleSelection = val;
            },

        },

    })
})();