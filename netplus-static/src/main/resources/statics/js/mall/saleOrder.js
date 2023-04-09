(function(win){

    var url = {
        getList: "/api/mall/getOrderList/",
        getTabCount: "/api/mall/getTabCount/",
        getOrderInfo:"/api/mall/getOrderInfo/",
        exportOrder:"/api/mall/exportOrder/",
        exportOrderDetail:"/api/mall/exportOrderDetail/",
        receivingOrder:"/api/mall/receivingOrder/",
        getDeliveryAppointmentUrl:"/api/mall/getDeliveryAppointmentUrl/",
    };

    var app = new Vue({
        el:'#RZ',
        data: {

            filtersConfig: {
                searchKey: {placeholder: '商品名称／型规／物料编码／物料条码／协议号', value: ''},

                selects: [
                    {key: 'deptNoList', type: 'more-change', name: '部门', },
                    {key: 'createTime', type: 'date-range', name: '创建时间', },
                ],

                lines: [
                    {key: 'buyerNoList', type: 'more-change', name: '采购单位', },
                    {key: 'statusList', type: 'more-change', name: '订单状态', },
                ]
            },

            filters:{
                deptNoList: [],
                createTime: [],
                buyerNoList: [],
                statusList: []
            },

            tab: [
                {key: "", value: "全部", count: 0},
                //{key: "DJD", value: "待接单", count: 0},
            ],
            selectedTab: "",

            tableData:[],
            totalCount: 0,
            multipleSelection: [],
            pageIndex: 1,
            pageSize: 10,
            param: {},
            confirmDialogVisible: false,
            confirmOrderNo: "",
            orderStatus: orderStatus,
            orderStatusName: orderStatusName,
            deliveryAppointmentUrl: '',
        },

        mounted: function(){
            this.getDeliveryAppointmentUrl();
            if (this.orderStatus !='' && this.orderStatus != undefined) {
                this.$refs.filters.addParam("statusList", [this.orderStatus], this.orderStatusName, true);
            }
            this.search(this.$refs.filters.__getParam());
        },

        methods:{
            __getParam: function () {
                return Vue.tools.filter(this.filters, function (v) {return v});
            },

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
                    page: 'S',
                    status: self.selectedTab
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
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
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

            exportOrder: function(){
                var _this = this;

                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                });

                exportFn(url.exportOrder, _param, "销售订单导出")
            },

            exportOrderDetail: function(){

                var _this = this;

                var _param = $.extend(_this.param, {
                    pageIndex: 1,
                    pageSize: 5000,
                });

                exportFn(url.exportOrderDetail, _param, "商城销售订单明细导出")

            },

            orderConfirm: function(orderNo){
                this.confirmDialogVisible=true;
                this.confirmOrderNo=orderNo;
            },

            receivingOrder: function(){
                var _this = this;
                ajax(
                    url.receivingOrder,
                    {
                        orderNo: _this.confirmOrderNo
                    },
                    function(json){
                        dialog.success("接单成功", function(){
                            _this.confirmDialogVisible=false;
                            _this.search(_this.$refs.filters.__getParam());
                        });

                    },
                    function(err){
                        dialog.error(err);
                    }
                );
            },

            toDetail: function(orderNo){
                formForward('/mall/saleOrderDetail/'+orderNo, {}, '_blank', 'get')
            },

            getDeliveryAppointmentUrl: function (){
                var _this = this;
                ajax(
                    url.getDeliveryAppointmentUrl,
                    {},
                    function(json){
                        _this.deliveryAppointmentUrl=json;
                    },
                    function(err){
                        dialog.error(err);
                    }
                );
            },

            deliveryAppointment: function (detail){
                formForward(this.deliveryAppointmentUrl, {}, '_blank', 'get')
            }

        }

    });

    win.app = app;

})(window || {})