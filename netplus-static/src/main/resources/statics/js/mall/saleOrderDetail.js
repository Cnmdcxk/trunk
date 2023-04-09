(function (win) {

    var url = {
        getOrderInfo: "/api/mall/getOrderInfo/",
        exportOrderDetail:"/api/mall/exportOrderDetail/",
        receivingOrder:"/api/mall/receivingOrder/",
        getDeliveryAppointmentUrl:"/api/mall/getDeliveryAppointmentUrl/",
    };

    var app = new Vue({

        el: "#RZ",
        data: {
            display1:true,
            display2:true,
            display3:true,
            add1:false,
            add2:false,
            add3:false,
            reduce1:true,
            reduce2:true,
            reduce3:true,
            orderNo: orderNo,
            orderInfo: {},
            tableData: [],

            totalCount: 0,
            pageIndex: 1,
            pageSize: 10,
            confirmDialogVisible: false,
            deliveryAppointmentUrl: '',
        },

        mounted: function () {
            this.getDeliveryAppointmentUrl();
            this.getOrderInfo();
        },

        methods: {
            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.getOrderInfo();
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.getOrderInfo();
            },

            getOrderInfo: function(){
                var _this = this;
                ajax(
                    url.getOrderInfo,
                    {
                        orderNo: _this.orderNo,
                        pageIndex: _this.pageIndex,
                        pageSize: _this.pageSize
                    },
                    function(json){
                        _this.orderInfo = json;
                        _this.tableData = json.tbmqq441VoList;
                        _this.totalCount = json.totalCount;
                    }, function(err){
                        dialog.error(err);
                    }
                );
            },

            exportOrderDetail: function(){

                var _this = this;

                var _param = $.extend({orderNo: _this.orderNo}, {
                    pageIndex: 1,
                    pageSize: 5000,
                    page: "S"
                });

                exportFn(url.exportOrderDetail, _param, "商城销售订单明细导出")
            },

            receivingOrder: function(){
                var _this = this;
                ajax(
                    url.receivingOrder,
                    {
                        orderNo: _this.orderInfo.orderno
                    },
                    function(json){
                        dialog.success("接单成功", function(){
                            _this.confirmDialogVisible=false;
                            _this.getOrderInfo();
                        });

                    },
                    function(err){
                        dialog.error(err);
                    }
                );
            },

            display:function (val) {
                switch (val){
                    case 1:
                        this.display1=this.display1==true?false:true;
                        this.add1=this.add1==true?false:true;
                        this.reduce1=this.reduce1==false?true:false;
                        break;
                    case 2:
                        this.display2=this.display2==true?false:true;
                        this.add2=this.add2==true?false:true;
                        this.reduce2=this.reduce2==false?true:false;
                        break;
                    case 3:
                        this.display3=this.display3==true?false:true;
                        this.add3=this.add3==true?false:true;
                        this.reduce3=this.reduce3==false?true:false;
                        break;
                }
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
            },

            toExpressDetail: function (expressNo){
                formForward("https://www.kuaidi100.com/chaxun", {nu:expressNo}, '_blank', 'get');
            },

        }

    });

    win.app = app;

})(window||{});