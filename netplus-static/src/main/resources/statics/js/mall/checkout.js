(function(win){
    var url = {
        getAddrList:" /api/provider/getAddrList/",
        getCurrentUser:"/api/provider/getCurrentUser/",
        getMyShoppingCartList:"/api/mall/getMyShoppingCartList/",
        createOrder:"/api/mall/createOrder/",
        getLineList:"/api/mall/getLineList/",
        getConsignee: "/api/provider/getConsignee/",
        getSprInfo:"/api/mall/getSprInfo/",
        getCheckOrderConfig:"/api/mall/basicData/getCheckOrderConfig/",
        getCostDept:"/api/mall/basicData/getCostDept/",
    };

    var app = new Vue({
        el: '#RZ',
        data: {

            goodIdList: goodIdList.split("-"),
            goodList: [],
            totalAmt: 0.00,

            username: username,
            companyName: companyName,
            deptName: deptName,
            deptNo: deptNo,

            selectedLine: "",
            lineList: [],

            consigneeList: [],
            selectedConsignee: "",
            phone: "",

            consigneeAddr: [],
            selectedAddr: "",

            showCreateOrderDialog: false,

            orderNoList: [],


            showWarningDialog: false,
            warningMessage: "",

            editRemarkItem: {},
            showEditRemark: false,
            remark: '',
            remark2:'',
            remarkIndex:'',
            remarkData:'',
            goodListIndex:'',


            sprOne: [],
            sprTwo: [],
            selectedOne: "",
            selectedTwo: "",
            sprTwoDisabled: false,

            currentUser: [],
            checkOrderConfig: [],
            showCostDept: false,
            costDept:[],
            selectedCostDept: "",
        },
        mounted: function() {
            this.getLineList();
            this.getConsignee("");
            this.getAddrList();
            this.getMyShoppingCartList();
        },

        methods: {
            editRemark:function (gg,ii,i) {//点击查看
                this.remarkIndex = ii;
                this.goodListIndex = i
                this.remarkData = gg.remark2;
                this.editRemarkItem = gg;
                this.remark = gg.remark2;
                this.showEditRemark = true;
                // console.log( '点击查看' )
                // console.log( gg.remark2 )
                // console.log( this.goodList )
            },
            handleSave:function () {//点击确定
                var _this = this;
                $.each(_this.goodList, function(i ,d){
                    if (_this.goodListIndex == i) {
                        $.each(d.tbmqq433VoList, function(ii, dd){
                            if (ii == _this.remarkIndex) {
                                dd.remark2= _this.remark;
                                // console.log( '存入' )
                                // console.log( dd.remark2 )
                            }
                        });
                    }
                });
                this.showEditRemark=false;
                // this.remark = this.remark2;
            },

            getLineList: function(){

                var _this = this;
                ajax(url.getLineList, {loading: false}, function(json){
                    _this.lineList = json;
                }, function(err){
                    dialog.error(err);
                });

            },

            changeLine: function(d){

                var _this = this;
                var param = {
                    deptNo: _this.deptNo,
                    lineNo: d,
                    loading: false
                };

                ajax(url.getSprInfo, param, function(json){
                    _this.sprOne = json.sprone;
                    _this.sprTwo = json.sprtwo || [];
                    _this.selectedOne="";
                    _this.selectedTwo="";

                    _this.sprTwoDisabled = _this.sprTwo.length <= 0;
                }, function(err){

                    _this.sprOne = [];
                    _this.sprTwo = [];
                    _this.selectedOne="";
                    _this.selectedTwo="";


                    dialog.error(err);
                });

            },

            getConsignee: function(keyword){

                var _this = this;
                ajax(url.getConsignee, {
                    keyword: keyword,
                    loading: false,
                    deptNo: _this.deptNo
                }, function(json){
                    _this.consigneeList = json;
                }, function(err){
                    dialog.error(err);
                });
            },

            changeConsignee: function(d){

                var phone = "";

                $.each(this.consigneeList, function(i, c){

                    if (d == c.userno) {
                        phone = c.phone;
                    }

                });

                if (phone != null && phone !=" " && phone !="") {
                    this.phone = phone;
                }

            },
            getMyShoppingCartList:function(){

                var _this = this;
                var totalAmt = 0.00;

                ajax(url.getMyShoppingCartList, {goodIdList: _this.goodIdList}, function(json){

                    $.each(json.items, function(i, d){

                        d.buyerNote = "";


                        $.each(d.tbmqq433VoList, function(ii, dd){
                            dd.pictureurl = strIsEmpty(dd.pictureurl) ? cdn + "/img/shop/noPic.png": dd.pictureurl
                            dd.amt = mulAmt(dd.price, dd.qty);
                            totalAmt = addAmt(totalAmt, dd.amt);
                            dd.remark2=dd.remark;
                            dd.remark='';

                        })
                    })

                    _this.totalAmt = totalAmt;
                    _this.goodList = json.items;


                    _this.getCurrentUser().then((msg) => {
                        _this.getCheckOrderConfig().then((msg) => {
                            if(_this.currentUser.deptNo === _this.checkOrderConfig.deptNo){
                                console.log(_this.goodList);
                                for (let i = 0; i < _this.goodList.length; i++) {
                                    for (let j = 0; j < _this.goodList[i].tbmqq433VoList.length; j++) {
                                        if(_this.goodList[i].tbmqq433VoList[j].twolevelclapk === _this.checkOrderConfig.category){
                                            _this.showCostDept=true;
                                            _this.getCostDept();
                                        }
                                    }
                                }
                            }
                        });
                    });

                    _this.goodList.forEach((g,i)=> {
                         g.tbmqq433VoList.forEach((gg, ii)=> {
                             if (gg.futurePoPriceStartDate!=null) {
                                 let b = gg.futurePoPriceStartDate.split('')
                                 b.splice(4,0,'-')
                                 b.splice(7,0,'-')
                                 gg.futurePoPriceStartDate = b.join('')
                             }
                         })
                    })
                }, function(err){
                    dialog.error(err);
                });
            },

            getCurrentUser: function() {
                var _this=this;
                return new Promise((resolve, reject) => {
                    ajax('/api/provider/getCurrentUser/',
                        {loading: false},
                        function (resp) {
                            _this.currentUser = resp;
                            resolve("success");
                        },
                        function (err) {
                            dialog.error(err);
                            reject(err);
                        }
                    );
                });
            },

            //获取下单特殊校验配置
            getCheckOrderConfig: function() {
                var _this=this;
                return new Promise((resolve, reject) => {
                    ajax(url.getCheckOrderConfig, {}, function(json){
                        _this.checkOrderConfig=json;
                        resolve("success");
                    }, function(err){
                        dialog.error(err);
                        reject(err);
                    })
                });
            },

            //获取费用预算部门
            getCostDept: function (){
                var _this=this;
                ajax(url.getCostDept, {}, function(json){
                    _this.costDept = json;
                    console.log(_this.costDept);
                }, function(err){
                    dialog.error(err);
                })
            },


            getAddrList: function(){
                var _this = this;

                ajax(url.getAddrList, {loading: false}, function(json){

                    _this.consigneeAddr = json;

                }, function(err){
                    dialog.error(err);
                })
            },

            toCart: function(){
                win.location.href = "/mall/shoppingCart";
            },

            changeAddr: function(c){
                var _this = this;
                _this.selectedAddr = c;

                if (_this.selectedAddr.addrtype == '1') {
                    _this.selectedConsignee = "";
                    _this.phone = "";
                }
            },

            createOrder: function(){

                var _this = this;

                if (_this.selectedLine == "") {
                    dialog.error("请选择部门条线");
                    return;
                }

                if (_this.selectedOne == "") {
                    dialog.error("请选择一级审批人");
                    return;
                }


                if (_this.sprTwo.length > 0) {
                    if (_this.selectedTwo == "") {
                        dialog.error("请选择二级审批人");
                        return;
                    }
                }

                if(_this.showCostDept && _this.selectedCostDept == ""){
                    dialog.error("请选择费用预算部门");
                    return;
                }

                if (_this.selectedAddr == "") {
                    dialog.error("请选择收货信息");
                    return;
                }


                if (_this.selectedAddr.addrtype == '2') {

                    if (_this.selectedConsignee == "") {
                        dialog.error("请选择收货人");
                        return;
                    }

                    if (_this.phone == "") {
                        dialog.error("请填写收货人联系方式");
                        return;
                    }
                }

                var orderDetail = [];
                $.each(_this.goodList, function(i ,d){

                    var goodList = [];
                    $.each(d.tbmqq433VoList, function(ii, dd){

                        goodList.push({
                            goodId: dd.goodid,
                            remark: dd.remark,
                            remark2: dd.remark2,
                        });
                        // console.log('提交的备注')
                        // console.log( dd.remark2 )
                    });

                    var od = {
                        ponopk: d.ponopk,
                        buyerNote: d.buyerNote,
                        goodList: goodList,
                    };

                    orderDetail.push(od);
                });

                if (orderDetail.length <= 0) {
                    dialog.error("暂无结算商品信息");
                    return;
                }

                var deptLineName = "";

                $.each(_this.lineList, function(i, d){

                    if (d.bmtxbm_pk == _this.selectedLine) {
                        deptLineName = d.bmtxmc;
                    }
                })

                var costDeptName = "";
                var costDeptNum = "";
                $.each(_this.costDept, function(i, d){

                    if (d.bmbm_pk == _this.selectedCostDept) {
                        costDeptName = d.mc;
                        costDeptNum = d.bmbh;
                    }
                })

                var param = {

                    sprCodeOne: _this.selectedOne,
                    sprCodeTwo: _this.selectedTwo,
                    deptLinePk: _this.selectedLine,
                    deptLineName: deptLineName,
                    consigneeUserNo: _this.selectedConsignee,
                    consigneePhone: _this.phone,
                    consigneeCode: _this.selectedAddr.code,
                    orderDetail: orderDetail,
                    costDeptPK: _this.selectedCostDept,
                    costDeptName: costDeptName,
                    costDeptNum: costDeptNum
                }
                ajax(url.createOrder, param, function(json){

                    if (json.status == -1) {

                        $.each(_this.goodList, function(i, d){

                            $.each(json.data.tbmqq441VoList, function(ii, dd){

                                if (d.ponopk == dd.ponopk) {

                                    $.each(d.tbmqq433VoList, function(iii, ddd){

                                        if (ddd.goodid == dd.goodid) {
                                            ddd.isOutQty = dd.isOutQty;
                                            ddd.remainQty = dd.remainQty;
                                        }

                                    })
                                }
                            })
                        });

                        _this.showWarningDialog = true;
                        _this.warningMessage  = "您的订单商品超出库存，无法下单!";


                    }else if (json.status == -2) {

                        _this.showWarningDialog = true;
                        _this.warningMessage  = "您的订单超过部门预算，无法下单!";

                    }else{
                        _this.orderNoList = json.data.orderNoList;
                        _this.showCreateOrderDialog = true;
                    }

                }, function(err){
                    dialog.error(err);
                });
            },

            closeWarningDialog: function(){
                this.showWarningDialog = false;
                this.warningMessage = "";
            },

            toOrder: function(){
                win.location.href = "/mall/purchaseOrder";
            },

            toShoppingCart: function(){
                formForward("/mall/shoppingCart", {}, '_self', 'GET');
            },
            delGood:function (goodid) {
                $.each(this.goodList, function(i, d){
                    var index = d.tbmqq433VoList.findIndex(item =>{
                        if(item.goodid==goodid){
                            return true
                        }
                    })
                    if(index>-1){
                        d.tbmqq433VoList.splice(index,1)
                    }
                })
            },
        }
    });

    win.app = app;

})(window||{})