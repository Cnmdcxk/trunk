(function (win) {

    var url = {
        getOrderInfo: "/api/mall/getOrderInfo/",
        exportOrderDetail:"/api/mall/exportOrderDetail/",
        addShoppingCartFromOrder:"/api/mall/addShoppingCartFromOrder/",
        orderCancel: "/api/mall/orderCancel/",
        createComment: "/api/mall/goodsComment/createComment/",
        getCommentByOrderInfo : "/api/mall/goodsComment/getCommentByOrderInfo/",
        viewApproveProgress: "/api/mall/viewApproveProgress/",
        addCart: "/api/mall/addShoppingCart/",
        getSupplierBizContact: "/api/provider/getSupplierBizContact",
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
            uploadList:[],
            urlList:[],
            comment: {},
            commentDialogVisible: false,
            myCommentDialogVisible: false,
            approveProgressList: null,
            approveProgressVisible: false,

            editRemarkItem: {},
            showEditRemark: false,
            remark: '',
            remark2:'',

            totalCount: 0,
            pageIndex: 1,
            pageSize: 10,
            showSupplierBizContact: false,
            supplierBizContact:{}
        },

        mounted: function () {
            this.getOrderInfo();
        },

        methods: {
            editRemark:function (res) {//点击查看
                this.editRemarkItem=res;
                this.remark=res;
                this.showEditRemark=true;
            },
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
                    page: "B"
                });

                exportFn(url.exportOrderDetail, _param, "采购订单明细导出")
            },

            toApproval: function(){
                console.log("toApproval");
            },

            toInvoicePage: function(){
                console.log("toInvoicePage");
            },

            toDeliveryPage: function(){
                console.log("toDeliveryPage");
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

            viewApproveProgress: function(approvecode){
                var _this=this;
                ajax(
                    url.viewApproveProgress,
                    {
                        approveCode:  approvecode
                    },
                    function(res){
                        _this.approveProgressList=res.respData.result;
                        _this.approveProgressVisible=true;
                    }, function(err){
                        dialog.error(err);
                    }
                )
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
            goDetail: function (goodsId) {
                formForward("/mall/goodsDetail", {goodsId: goodsId}, "_blank", "get");
            },
            HarvestDetails:function(erpOrderNo,erporderdetailno){

                window.location.href = "/logistics/deliverynotedetails?erpOrderNo="+erpOrderNo+"&erporderdetailno="+erporderdetailno;
            },
            BillingDetails:function(erpOrderNo,erporderdetailno){
                window.location.href = "/finance/entryPurchaseInvoice2?erpOrderNo="+erpOrderNo+"&erporderdetailno="+erporderdetailno;
            },


            orderCancel: function(){
                var _this = this;
                ajax(url.orderCancel, {orderNo:  _this.orderNo}, function(){
                    dialog.success("订单作废成功", function(){
                        win.location.reload();
                    })
                }, function(err){
                    dialog.error(err);
                })
            },

            writeComment: function (row){
                this.comment={
                    orderno: row.orderno,
                    orderdetailno: row.orderdetailno,
                    pictureurl: row.pictureurl,
                    productname:row.productname,
                    matrlno: row.matrlno,
                    matrltm: row.matrltm,
                    score: 1,
                    commentcontent: '',
                };
                this.urlList=[];
                this.uploadList=[];
                this.commentDialogVisible=true;
            },

            createComment: function (){
                var _this=this;
                ajax(
                    url.createComment,
                    {
                        orderNo: _this.comment.orderno,
                        orderDetailNo: _this.comment.orderdetailno,
                        score: _this.comment.score,
                        commentContent: _this.comment.commentcontent,
                        imgUrl: _this.urlList,
                    },
                    function(resp){
                        if(resp.status == "1"){
                            dialog.success("评价成功!", function(){
                                _this.urlList=[];
                                _this.uploadList=[];
                                _this.commentDialogVisible=false;
                                _this.getOrderInfo();
                            });
                        }else{
                            dialog.error(resp.msg);
                        }
                    }, function(err){
                        dialog.error(err);
                    }
                )
            },

            showMyComment: function (row){
                var _this=this;
                ajax(
                    url.getCommentByOrderInfo,
                    {
                        orderNo: row.orderno,
                        orderDetailNo: row.orderdetailno
                    },
                    function(resp){
                        if(resp.status == "1"){
                            _this.comment=resp.data;
                            _this.myCommentDialogVisible=true;
                        }else{
                            dialog.error(resp.msg);
                        }
                    }, function(err){
                        dialog.error(err);
                    }
                )
            },

            handleChange: function (file, fileList) {
                this.uploadList = fileList;
            },

            handleSuccess:function(res, file, fileList){
                this.urlList.push(res.url);
            },

            beforeUpload:function(file){
                var FileExt = file.name.replace(/.+\./, "");
                if (['jpg','png','jpeg'].indexOf(FileExt.toLowerCase()) === -1){
                    dialog.error('上传文件只能是 jpg、png 格式!')
                    return false;
                }

                var isLt300K = file.size / 1024 <= 300;

                if (!isLt300K) {
                    dialog.error('图片大小不超过300K!')
                    return false;
                }

            },

            removeImg:function(index){
                this.uploadList.splice(index,1);
                this.urlList.splice(index,1);
            },

            toExpressDetail: function (expressNo){
                formForward("https://www.kuaidi100.com/chaxun", {nu:expressNo}, '_blank', 'get');
            },

            showBizContact: function (supplierno){
                var _this=this;
                ajax(
                    url.getSupplierBizContact,
                    {
                        supplierNo:supplierno
                    },
                    function (resp) {
                        _this.supplierBizContact=resp;
                        _this.showSupplierBizContact=true;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

        }


    });

    win.app = app;

})(window||{});