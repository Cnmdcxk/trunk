(function(win){

    var url = {
        getList: "/api/mall/getOrderList/",
        getTabCount: "/api/mall/getTabCount/",
        getOrderInfo:"/api/mall/getOrderInfo/",
        exportOrder:"/api/mall/exportOrder/",
        exportOrderDetail:"/api/mall/exportOrderDetail/",
        addShoppingCartFromOrder:"/api/mall/addShoppingCartFromOrder/",
        invalidOrder:"/api/mall/invalidOrder/",
    };

    var app = new Vue({
        el:'#RZ',
        data: {
            status:status,
            statusName:'待审核',



            filtersConfig: {
                searchKey: {placeholder: '订单号／下单人／供应商', value: ''},

                selects: [
                    {key: 'deptNoList', type:   'more-change', name: '部门', },
                    {key: 'lineList', type:   'more-change', name: '条线', },
                    {key: 'createTime', type: 'date-range', name: '创建时间', },
                    {key: 'isTimeOutOrder', type: 'single-change', name: '是否超时接单', },
                ],

                lines: [
                    {key: 'supplierList', type: 'more-change', name: '供应商', },
                    {key: 'buyerNoList', type: 'more-change', name: '采购单位', },
                    {key: 'statusList', type: 'more-change', name: '订单状态', },
                ]
            },

            filters:{
                brand: [],
                deptNoList: [],
                createTime: [],
                buyerNoList: [],
                statusList: [],
                isTimeOutOrder: [],
                lineList: [],
                supplierList: [],
            },

            selectedTab: "",

            tableData:[],
            totalCount: 0,
            multipleSelection: [],
            pageIndex: 1,
            pageSize: 10,
            param: {},
            invalidOrderItem: {},
            invalidOrderHandle: false,
            invalidReason: '',
            showInvalidReason: false,
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
                    page: 'all',
                });

                ajax(
                    url.getList,
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.totalCount = resp.totalCount;
                        self.filters.deptNoList = resp.resultMap.deptNoList;
                        self.filters.buyerNoList = resp.resultMap.buyerNoList;
                        self.filters.statusList = resp.resultMap.statusList;
                        self.filters.isTimeOutOrder = resp.resultMap.isTimeOutOrder;
                        self.filters.lineList = resp.resultMap.lineList;
                        self.filters.supplierList = resp.resultMap.supplierList;
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

            toDetail: function(orderNo){
                formForward('/mall/purchaseOrderDetail/'+orderNo, {}, '_blank', 'get')
            },

            showInvalidOrderHandle: function (orderItem){
                this.invalidOrderItem=orderItem;
                this.invalidReason='';
                this.invalidOrderHandle=true;
            },

            invalidOrder: function (){
                var _this = this;
                if(_this.invalidReason === '' || _this.invalidReason === undefined){
                    dialog.error("作废原因不能为空!");
                    return;
                }
                ajax(
                    url.invalidOrder,
                    {
                        orderNo: _this.invalidOrderItem.orderno,
                        invalidReason: _this.invalidReason
                    },
                    function (resp) {
                        _this.invalidOrderHandle=false;
                        dialog.success("作废成功!");
                        _this.search(_this.$refs.filters.__getParam());
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            viewInvalidReason: function (item){
                this.invalidOrderItem=item;
                this.showInvalidReason=true;
            }
        },

    });

    win.app = app;

})(window || {})