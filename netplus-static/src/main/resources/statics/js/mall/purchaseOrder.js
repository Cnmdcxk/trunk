(function(win){

    var url = {
        getList: "/api/mall/getOrderList/",
        getTabCount: "/api/mall/getTabCount/",
        getOrderInfo:"/api/mall/getOrderInfo/",
        exportOrder:"/api/mall/exportOrder/",
        exportOrderDetail:"/api/mall/exportOrderDetail/",
        addShoppingCartFromOrder:"/api/mall/addShoppingCartFromOrder/",
        addCart: "/api/mall/addShoppingCart/"
    };

    var app = new Vue({
        el:'#RZ',
        data: {
            status:status,
            statusName:'待审核',



            filtersConfig: {
                searchKey: {placeholder: '商品名称／型规／物料编码／物料条码／协议号', value: ''},

                selects: [
                    {key: 'lineList', type:   'more-change', name: '条线', },
                    {key: 'createTime', type: 'date-range', name: '创建时间', },
                ],

                lines: [
                    {key: 'supplierList', type: 'more-change', name: '供应商', },
                    {key: 'statusList', type: 'more-change', name: '订单状态', },
                ]
            },

            filters:{
                lineList: [],
                createTime: [],
                supplierList: [],
                statusList: []
            },

            selectedTab: "",

            tableData:[],
            totalCount: 0,
            multipleSelection: [],
            pageIndex: 1,
            pageSize: 10,
            param: {}
        },
        mounted:function(){
            if (this.status !='' && this.status != undefined){
                this.$refs.filters.addParam("statusList", [this.status], this.statusName, true);
            }
            this.search(this.$refs.filters.__getParam());
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

            handleSelectionChange:function(val) {
                this.multipleSelection = val;
            },


            search: function(var1){

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
                    page: 'B',
                    status: self.selectedTab
                });

                ajax(
                    url.getList,
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.lineList = resp.resultMap.lineList;
                        self.filters.supplierList = resp.resultMap.supplierList;
                        self.filters.statusList = resp.resultMap.statusList;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            toDetail1: function(approvecode){
                formForward("/approve/approved-detail/approved-order-detail/"+approvecode+"/", {}, "_self", "GET")
            },
            goDetail: function (goodsId) {
                formForward("/mall/goodsDetail", {goodsId: goodsId}, "_blank", "get");
            },
            expandChange: function(row){
                if (row.tbmqq441VoList == null) {
                    ajax(url.getOrderInfo, {orderNo: row.orderno}, function(json){

                        row.tbmqq441VoList = json.tbmqq441VoList;

                    }, function(err){
                        dialog.error(err);
                    })
                }
            },

            //合并列
            arraySpanMethod:function(d){
                if (d.columnIndex === 2) {
                    return [1, 5];
                }else if (d.columnIndex === 2
                    || d.columnIndex === 3
                    || d.columnIndex === 4
                    || d.columnIndex === 5
                    || d.columnIndex === 6 ) {
                    return [0, 0];
                }
            },

            selectTab: function(tab){
                this.selectedTab = tab.key;
                this.searchCore();
            },

            exportOrder: function(){
                var _this = this;

                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                });

                exportFn(url.exportOrder, _param, "采购订单导出")
            },


            exportOrderDetail: function(){

                var _this = this;

                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                });

                exportFn(url.exportOrderDetail, _param, "采购订单明细导出")

            },



            againOrder: function(orderNo){
                var _this = this;
                _this.$confirm(
                    "是否确认再来一单",
                    "提示",
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning',
                        center: true
                    }
                ).then(function(){

                    ajax(url.addShoppingCartFromOrder, {orderNo: orderNo}, function(json){
                        var msg="已成功加入"+json.data.addNum+"条商品";
                        if(json.data.existNum>0){
                            msg=msg+",购物车中已重复"+json.data.existNum+"条商品";
                        }
                        if(json.data.invalidNum>0){
                            msg=msg+","+json.data.invalidNum+"条商品已失效";
                        }
                        dialog.success(msg, function(){
                            formForward("/mall/shoppingCart", {}, "_self", "get")
                        });

                    }, function(err){
                        dialog.error(err);
                    })

                }).catch(function(){

                });
            },

            addCart: function(d){
                ajax(
                    url.addCart,
                    {
                        goodId: d.goodid,
                        isDefaultAdd: "Y"
                    },
                    function(json){
                        d.isAddCart="Y";
                        dialog.success("添加购物车成功");
                    }, function(err){
                        dialog.error(err);
                    }
                )
            },

            toDetail: function(orderNo){
                formForward('/mall/purchaseOrderDetail/'+orderNo, {}, '_blank', 'get')
            }
        },

    });

    win.app = app;

})(window || {})