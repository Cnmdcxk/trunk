(function(win){


    Vue.filter("getFeeTypeName", function(value){
        if (value == "1") {
            return "资产";
        } else if (value == "0" || value == "2") {
            return "费用";
        }else {
            return ""
        }
    });

    Vue.filter("getFlagName", function(value){
        if (value == "0") {
            return "资产";
        } else if (value == "1") {
            return "费用";
        }else {
            return ""
        }
    });


    Vue.filter("getOAStatusName", function(value){
        if (value == "2") {
            return "已审批";
        } else {
            return "审核中";
        }
    });

    Vue.filter("getUseName", function(value){
        if (value == "0") {
            return "仓库未使用";
        } else {
            return "仓库已使用";
        }
    });


    Vue.filter("getProjectTypeName", function(value){
        if (value == "0") {
            return "工业项目";
        } else if (value == "1" ) {
            return "民用项目";
        }else if (value == "2") {
            return "信息化项目";
        }else if (value == "3" ) {
            return "科研项目";
        }else if (value == "4") {
            return "大修项目";
        } else {
            return ""
        }
    });

    var url = {

        getMyShoppingCartList:'/api/mall/getMyShoppingCartList/',
        delMyShoppingCart: '/api/mall/delMyShoppingCart/',
        addMyCollect:"/api/mall/goodsCollect/addMyCollect/",
        importShoppingCart:'/api/mall/importShoppingCart/',
        exportErrInfo: '/api/mall/exportErrInfo/',
        updateProject: "/api/mall/updateProject/",
        updateDevice: '/api/mall/updateDevice/',
        updatePic: '/api/mall/updatePic/',
        getProject: '/api/mall/getProject/',
        getDevice: '/api/mall/getDevice/',
        addShoppingCart:'/api/mall/addShoppingCart/',
        getPriceCompare: "/api/mall/GoodsListed/priceCompare/",
        addViewHistory:"/api/mall/GoodsListed/addViewHistory/",
        batchUpdateProject:"/api/mall/batchUpdateProject/",
        checkQty:"/api/mall/checkQty/",
        getCheckOrderConfig: "/api/mall/basicData/getCheckOrderConfig/",
        updateShoppingCartRemark: "/api/mall/updateShoppingCartRemark/",
        updateDeviceSimpleNo: "/api/mall/updateDeviceSimpleNo/",
    };

    var app = new Vue({
        el: '#RZ',
        data: {

            cdn: cdn,
            isOpen:true,

            filtersConfig: {
                searchKey: {placeholder: '商品名称／物料编码／物料条码／品牌', value: ''},
                selects: [
                    {key: 'categoryList', type: 'more-change', name: '商品分类', },
                    {key: 'brandList', type: 'more-change', name: '品牌', },
                ],
            },

            filters:{
                categoryList:[],
                brandList: [],
            },

            isContinue: "",
            shoppingCartList: [],
            selectedData: [],
            goodIds:[],
            isSelectedAll: false,

            showImportDialog: false,
            errInfo: [],
            importTotalCount: 0,
            importErrCount: 0,

            showImportWarningDialog: false,
            importBatchCode: "",
            currGoodId: "",


            showProjectDialog: false,
            projectNoList: [],
            projectNoTotalCount: 0,
            projectNoPageSize: 10,
            projectNoPageIndex: 1,
            filters2: {},
            filtersConfig2: {
                searchKey: {placeholder: '项目工程号／项目工程名称／业主单位', value: ''}
            },

            projectNoParam: {},


            filters3: {},
            filtersConfig3: {
                searchKey: {placeholder: '新增设备申请单编号', value: ''}
            },
            showDeviceDialog: false,
            deviceInfoList: [],
            deviceParam: {},


            selectPicList:[
                {key: "Y", value: "是"},
                {key: "N", value: "否"}
            ],


            showSimilarDialog: false,
            goodsData: [],

            currentUser: [],
            checkOrderConfig: [],
            editRemarkItem: {},
            showEditRemark: false,
            remark: '',
        },

        mounted: function(){
            this.getCurrentUser();
            this.getCheckOrderConfig();
            this.getProjectNo();
            this.search();
        },

        methods: {

            //工程项目单弹框操作
            projectNoPageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.getProjectNoCore();
            },

            projectNoSizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.getProjectNoCore();
            },
            updateTable(){
                // 卸载
                var _this=this;
                _this.tableShow= false
                _this.$nextTick(function(){
                    _this.tableShow= true
                })
            },
            getProjectNo: function(var1){

                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.projectNoParam = var1;

                self.getProjectNoCore();
            },

            getProjectNoCore: function(){
                var _this = this;

                _this.projectNoParam = $.extend(_this.projectNoParam, {
                    pageIndex: _this.pageIndex,
                    pageSize: _this.pageSize,
                });

                ajax(url.getProject, _this.projectNoParam, function(json){

                    _this.projectNoList = json.items;
                    _this.projectNoTotalCount = json.totalCount;

                }, function(err){
                    dialog.error(err);
                })
            },

            selectProject: function(d){

                var _this = this;
                var param = {
                    projectNo: d.xmbh,
                    projectName: d.xmmc,
                    goodId: _this.currGoodId
                };

                ajax(url.updateProject, param, function(json){

                    $.each(_this.shoppingCartList, function(i, dd){

                        $.each(dd.tbmqq433VoList, function(ii, ddd){

                            if (ddd.goodid == _this.currGoodId) {

                                ddd.projectname = d.xmmc;
                                ddd.projectno = d.xmbh;
                                ddd.projectNameAndNo = d.xmmc + d.xmbh;


                                if (d.xmbh != "SC00") {
                                    ddd.deviceapplyno ="";
                                    ddd.deviceapplypk ="";
                                }
                            }

                        })
                    })

                    _this.closeProjectDialog();

                }, function(err){
                    dialog.error(err);
                })
            },


            batchSelectProject: function(d){

                var _this = this;
                var param = {
                    projectNo: d.xmbh,
                    projectName: d.xmmc,
                    goodIdList: _this.selectedData
                };

                ajax(url.batchUpdateProject, param, function(json){

                    $.each(_this.shoppingCartList, function(i, dd){

                        $.each(dd.tbmqq433VoList, function(ii, ddd){

                            if ( _this.selectedData.indexOf(ddd.goodid) > -1 ) {

                                ddd.projectname = d.xmmc;
                                ddd.projectno = d.xmbh;
                                ddd.projectNameAndNo = d.xmmc + d.xmbh;

                                if (d.xmbh != "SC00") {
                                    ddd.deviceapplyno ="";
                                    ddd.deviceapplypk ="";
                                }
                            }
                        })
                    })

                    _this.closeProjectDialog();

                }, function(err){
                    dialog.error(err);
                })
            },


            openProjectDialog: function(e, d){

                var _this = this;
                _this.showProjectDialog = true;
                _this.currGoodId = d;
                _this.updateProjectType = "A";

            },

            openProjectDialog2: function(){

                var _this = this;

                if (_this.selectedData.length <= 0) {
                    dialog.error("请选择商品");
                    return;
                }

                _this.showProjectDialog = true;
                _this.updateProjectType = "B";
            },


            closeProjectDialog: function(){

                var _this = this;
                _this.showProjectDialog = false;
                _this.currGoodId = "";
                _this.updateProjectType = "";
            },

            //新增设备单弹框操作
            openDeviceDialog: function(e, d){

                var _this = this;
                _this.showDeviceDialog = true;
                _this.currGoodId = d;
            },

            closeDeviceDialog: function(){

                var _this = this;
                _this.showDeviceDialog = false;
                _this.currGoodId = "";
            },

            deviceSearch: function(param){

                var _this = this;
                _this.deviceParam = param;

                ajax(url.getDevice, param, function(json){

                    _this.deviceInfoList = json.items;

                }, function(err){
                    dialog.error(err);
                })
            },

            selectDevice: function(d){

                var _this = this;
                var param = {
                    deviceApplyPk: d.xzsbsqdpk,
                    deviceApplyNo: d.xzsbsqdbh,
                    goodId: _this.currGoodId
                };

                ajax(url.updateDevice, param, function(json){

                    $.each(_this.shoppingCartList, function(i, dd){

                        $.each(dd.tbmqq433VoList, function(ii, ddd){

                            if (ddd.goodid == _this.currGoodId) {
                                ddd.deviceapplyno = d.xzsbsqdbh;
                                ddd.deviceapplypk = d.xzsbsqdpk;
                            }
                        })
                    })

                    _this.closeDeviceDialog();

                }, function(err){
                    dialog.error(err);
                })
            },

            //购物车操作
            search:function(param){

                var _this = this;

                ajax(url.getMyShoppingCartList, param, function(json){

                    $.each(json.items, function(i, d){
                        d.checked = false;

                        $.each(d.tbmqq433VoList, function(ii, dd){
                            _this.goodIds.push(dd.goodid)
                            dd.amt = mulAmt(dd.price, dd.qty);
                            dd.checked = false;
                            dd.isOutQty = '0';
                            dd.pictureurl = strIsEmpty(dd.pictureurl) ? cdn + "/img/shop/noPic.png": dd.pictureurl
                        })
                    })

                    _this.shoppingCartList = json.items;
                    _this.filters.categoryList = json.resultMap.categoryList;
                    _this.filters.brandList = json.resultMap.brandList;
                    _this.isSelectedAll = false;
                    _this.errInfo = [];
                    _this.importTotalCount = 0;
                    _this.importErrCount = 0;
                    _this.showImportWarningDialog = false;
                    _this.selectedData = [];
                    _this.userNo2="";
                    _this.deptNo2="";
                    _this.getQty();

                }, function(err){
                    dialog.error(err);
                });
            },

            changeOpen: function(){
                this.isOpen =!this.isOpen;
            },

            selectAll: function(val){

                var selectedData = [];
                var _this = this;

                $.each(_this.shoppingCartList, function(i, d){

                    d.checked = val;
                    $.each(d.tbmqq433VoList, function(i, dd){
                        dd.checked = val;

                        if (dd.cartGoodStatus != 'N') {
                            var index = _this.selectedData.indexOf(d.goodid);
                            if (val && index == -1) {
                                selectedData.push(dd.goodid);
                            }
                        }

                    })

                });

                _this.selectedData = selectedData;
            },

            selectSingle: function(d){
                var _this = this;
                var index = _this.selectedData.indexOf(d.goodid);

                if (d.checked) {

                    if (index == -1) {
                        _this.selectedData.push(d.goodid);
                    }

                }else{
                    _this.selectedData.splice(index,1);
                }

            },

            selectGroup: function(d){
                var _this = this;

                $.each(d.tbmqq433VoList, function(i, dd){

                    if (dd.cartGoodStatus != 'N') {

                        var index = _this.selectedData.indexOf(dd.goodid);
                        dd.checked = d.checked;

                        if (dd.checked) {

                            if (index == -1) {
                                _this.selectedData.push(dd.goodid);
                            }

                        }else{

                            if (index != -1) {
                                _this.selectedData.splice(index, 1);
                            }
                        }

                    }

                });

            },

            delGood: function(goodIdList){

                var _this = this;

                dialog.confirm("是否确认删除商品",function(){
                    ajax(url.delMyShoppingCart, {goodIdList: goodIdList}, function(){
                        dialog.success("删除成功", function(){
                            win.location.reload();
                        });
                    }, function(err){
                        dialog.error(err);
                    })
                })
            },

            delSelected: function(){
                var _this = this;
                if (_this.selectedData.length <= 0) {
                    dialog.error("请选中数据删除");
                    return;
                }else{
                    _this.delGood(_this.selectedData);
                }
            },

            delInvalid: function(){

                var _this = this;
                var invalidGoods = [];

                $.each(_this.shoppingCartList, function(i, d){

                    $.each(d.tbmqq433VoList, function(i, dd){

                        if (dd.cartGoodStatus == 'N') {
                            invalidGoods.push(dd.goodid);
                        }
                    })
                });

                if (invalidGoods.length <= 0) {

                    dialog.error("暂无失效商品信息");
                    return;
                }else{
                    _this.delGood(invalidGoods);
                }
            },

            addMyCollect: function(goodsId){
                ajax(url.addMyCollect, {goodId: goodsId, isDelCart: "Y"}, function(json){

                    dialog.success("已移入收藏夹", function(){
                        win.location.reload();
                    })

                }, function(err){
                    dialog.error(err);
                })
            },

            toSettle: function(){

                var _this = this;

                if (_this.selectedData.length <= 0) {
                    dialog.error("请选中数据")
                    return;
                }

                var userDeptNo=_this.currentUser.deptNo;

                var hasConfigCategory=false;
                var hasOtherCategory=false;

                var categoryName="";

                for (var i=0; i < _this.shoppingCartList.length; i++) {

                    for (var j = 0; j < _this.shoppingCartList[i].tbmqq433VoList.length; j++) {

                        var t = _this.shoppingCartList[i].tbmqq433VoList[j];

                        //选中的数据才需要判断
                        if (_this.selectedData.indexOf(t.goodid) >= 0) {

                            if (t.qty <= 0 || t.qty == " ") {
                                dialog.error("数量必须大于0");
                                return;
                            }

                            if (t.projectno == null || t.projectno == " " ) {
                                dialog.error("工程项目单不能为空");
                                return;
                            }

                            if(t.twolevelclapk == _this.checkOrderConfig.category){
                                hasConfigCategory=true;
                                categoryName=t.twolevelclaname;
                            }else{
                                hasOtherCategory=true;
                            }
                            if ((t.productname.indexOf("轧辊")!=-1 || t.productname.indexOf("辊环")!=-1)&& (t.devicesimpleno == " " ||t.devicesimpleno == null)) {
                                dialog.error("名称含轧辊或辊环的请填写工装设备简号，若确实没有可填写 无!");
                                return;
                            }

                        }
                    }
                }

                if(hasConfigCategory && hasOtherCategory && _this.checkOrderConfig.deptNo==userDeptNo){
                    dialog.error(categoryName+"分类下的商品需单独下单!");
                    return;
                }
                formForward("/mall/checkout?", {goodIdList: this.selectedData.join("-"),}, "_self","post")
            },
            getCurrentUser: function (){
                var _this=this;
                ajax('/api/provider/getCurrentUser/',
                    {loading: false},
                    function (resp) {
                        _this.currentUser = resp;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            //获取下单特殊校验配置
            getCheckOrderConfig: function (){
                var _this=this;
                ajax(url.getCheckOrderConfig, {}, function(json){
                    _this.checkOrderConfig=json;
                }, function(err){
                    dialog.error(err);
                })
            },

            //选择是否需要图纸
            changePic: function(e, d){
                ajax(
                    url.updatePic,
                    {goodId: d.goodid, isNeedPic: d.isneedpic},
                    function(json){},
                    function(err){
                        d.isneedpic = null;
                        dialog.error(err);
                    }
                )
            },

            //excel 导入
            openImportDialog: function(){
                this.showImportDialog = true;
            },

            closeImportDialog: function(){
                this.showImportDialog = false;
            },

            beforeUpload: function(file){
                var xls = file.name.substr(file.name.lastIndexOf("."));

                if(xls === '.xls'||xls === '.xlsx'){
                    return file
                }else {
                    dialog.error("上传文件只能是 xls/xlsx 格式!")
                    return false
                }
            },

            upload: function(isContinue){


                if (this.$refs.upload.uploadFiles.length > 0) {

                    this.loadingInstance = this.$loading({
                        lock: true,
                        text: "上传中...",
                        background: 'rgba(0, 0, 0, 0.7)'
                    });

                    this.isContinue = isContinue;
                    this.$refs.upload.submit();

                }else{
                    dialog.error("请上传文件")
                }

            },

            handleSuccess: function(resp){

                var _this = this;
                _this.importBatchCode = resp.batchCode;
                _this.loadingInstance.text = "上传成功，保存数据中...";

                ajax(url.importShoppingCart,
                    {
                        batchCode: resp.batchCode,
                        fileUrl: encodeURI(resp.url),
                        isContinue: "N"

                    }, function(resp){

                        if (resp.status == 0) {
                            _this.loadingInstance.close();
                            _this.closeImportDialog();
                            _this.$refs.upload.clearFiles();

                            _this.errInfo = resp.data.errInfo;
                            _this.importTotalCount = resp.data.totalCount;
                            _this.importErrCount = resp.data.errCount;
                            _this.showImportWarningDialog = true;

                        }else{
                            _this.loadingInstance.text = "保存成功...";
                            _this.loadingInstance.close();
                            _this.$refs.upload.clearFiles();
                            _this.closeImportDialog();
                            _this.search();
                        }

                    }, function(err){
                        console.log(err);
                        _this.loadingInstance.close();
                        _this.$refs.upload.clearFiles();
                        dialog.error(err);
                    })

            },

            continueSave: function(){
                var _this = this;

                ajax(url.importShoppingCart,
                    {
                        batchCode: _this.importBatchCode,
                        isContinue: "Y"

                    }, function(resp){

                        _this.showImportWarningDialog = false;
                        _this.search();

                    }, function(err){
                        dialog.error(err);
                    })

            },

            handleError: function(err){

                var _this = this;
                _this.loadingInstance.close();
                _this.$refs.upload.clearFiles();
                dialog.error("文件上传失败")
            },

            exportErrInfo: function(){
                var _this = this;
                exportFn(url.exportErrInfo, {errInfo: _this.errInfo} , "报错明细")
            },

            changeQty: function(d){

                if (d.qty == undefined || d.qty <= 0) {
                    return;
                }

                d.amt = mulAmt(d.price, d.qty);
                var param = {
                    goodId: d.goodid,
                    qty: d.qty,
                }

                ajax(
                    url.addShoppingCart,
                    param,
                    function(){},
                    function(err){
                        d.qty = d.oldQty;
                        dialog.error(err);
                    }
                )

            },

            //同类比价

            getPriceCompare: function(d){

                var _this = this;

                ajax(
                    url.getPriceCompare,
                    {matrltmList: [d.matrltm]},
                    function(resp){

                        $.each(resp, function(i, d){
                            d.oldQty = d.qty;
                            d.pictureurl = strIsEmpty(d.pictureurl) ? cdn + "/img/shop/noPic.png": d.pictureurl
                        });

                        _this.goodsData = resp;
                        _this.showSimilarDialog = true;
                    },
                    function(err){
                        dialog.error(err);
                    }
                )
            },

            addCart: function(d){
                var _this = this;

                if (d.qty == undefined || d.qty <= 0) {
                    return;
                }

                var param = {
                    goodId: d.goodid,
                    qty: d.qty,
                    isAddUp: "Y"
                }

                ajax(
                    url.addShoppingCart,
                    param,
                    function(){

                        dialog.success("加入购物车成功", function(){
                            _this.search();
                        })
                    },
                    function(err){
                        d.qty = d.oldQty;
                        dialog.error(err);
                    }
                )

            },

            //util
            setGoodInfo: function(val, key){
                var self = this;

                $.each(self.shoppingCartList, function(i, d){

                    $.each(d.tbmqq433VoList, function(ii, dd){

                        if (self.selectedData.indexOf(dd.goodid) >= 0) {
                            dd[key] = val;
                        }

                    })
                });

            },

            goDetail: function (goodId) {
                formForward("/mall/goodsDetail", {goodsId: goodId}, '_blank', 'get');
                ajax(url.addViewHistory, {goodId: goodId},function(){

                }, function(err){
                    dialog.error(err)
                })
            },

            clear: function(g){

                var param = {
                    projectNo: "",
                    projectName: "",
                    goodId: g.goodid
                };



                ajax(url.updateProject, param, function(json){

                    //$.each(_this.shoppingCartList, function(i, dd){
                    //
                    //    $.each(dd.tbmqq433VoList, function(ii, ddd){
                    //
                    //        if (ddd.goodid == _this.currGoodId) {
                    //
                    //            ddd.projectname = "";
                    //            ddd.projectno = d.xmbh;
                    //            ddd.projectNameAndNo = d.xmmc + d.xmbh;
                    //        }
                    //
                    //    })
                    //})

                    g.projectNameAndNo="";
                    g.projectname="";
                    g.projectno="";
                    g.deviceapplyno="";
                    g.deviceapplypk="";


                }, function(err){
                    dialog.error(err);
                })

            },

            clear2: function(g){

                var param = {

                    deviceApplyPk: "",
                    deviceApplyNo: "",
                    goodId: g.goodid

                };

                ajax(url.updateDevice, param, function(json){

                    //$.each(_this.shoppingCartList, function(i, dd){
                    //
                    //    $.each(dd.tbmqq433VoList, function(ii, ddd){
                    //
                    //        if (ddd.goodid == _this.currGoodId) {
                    //            ddd.deviceapplyno = d.xzsbsqdbh;
                    //            ddd.deviceapplypk = d.xzsbsqdpk;
                    //        }
                    //    })
                    //})


                    g.deviceapplyno="";
                    g.deviceapplypk="";


                }, function(err){
                    dialog.error(err);
                })

            },
            getQty:function () {
                var _this=this;
                var list=_this.shoppingCartList;
                _this.shoppingCartList=[];
                ajax(url.checkQty, {goodIdList: _this.goodIds}, function(json)
                {
                    $.each(list, function(i, d){

                        $.each(d.tbmqq433VoList, function(ii, dd){

                                var qtyResult = json[dd.matrlid];

                                dd.remainQty = qtyResult.remainQty

                        })
                    })
                    _this.shoppingCartList=list;
                })
            },

            checkQty: function(){

                var _this = this;

                if (_this.selectedData.length <= 0) {
                    dialog.error("请选中数据")
                    return;
                }

                ajax(url.checkQty, {goodIdList: _this.selectedData}, function(json){

                    var isPassCheck = true;
                    $.each(_this.shoppingCartList, function(i, d){

                        $.each(d.tbmqq433VoList, function(ii, dd){

                            if (_this.selectedData.indexOf(dd.goodid) >= 0) {

                                var qtyResult = json[dd.matrlid];
                                if (qtyResult.sfckc == '1') {
                                    dd.isOutQty = '1';
                                    isPassCheck = false;
                                }else{
                                    dd.isOutQty = '0';
                                }

                                dd.remainQty = qtyResult.remainQty
                            }
                        })
                    })

                    if (isPassCheck) {
                        dialog.success("库存校验通过，未超库存上限");
                    }


                }, function(err){
                    dialog.error(err);
                })


            },
            exportExcel:function () {
                var self=this;
                var _param=$.extend(self.param,{
                    pageIndex:1,
                    pageSize:5000
                })
                exportFn("/api/mall/exportMyShoppingCartList/",_param,"购物车信息Excel导出")
            },

            editRemark: function (item) {
                this.editRemarkItem=item;
                this.remark=item.remark;
                this.showEditRemark=true;
            },

            updateItemRemark: function (){
                var _this = this;
                ajax(
                    url.updateShoppingCartRemark,
                    {
                        goodId: _this.editRemarkItem.goodid,
                        remark: _this.remark
                    },
                    function (resp) {
                        _this.editRemarkItem.remark=_this.remark;
                        _this.showEditRemark=false;
                        dialog.success("修改成功!");
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            updateDeviceSimpleNo: function (ss){
                var _this = this;
                ajax(
                    url.updateDeviceSimpleNo,
                    {
                        goodId: ss.goodid,
                        deviceSimpleNo: ss.devicesimpleno
                    },
                    function (resp) {
                        dialog.success("修改成功!");
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
        },

        computed: {
            selectedTotalAmt: function(){
                var _this = this;
                var totalAmt = 0.00;

                if (_this.selectedData.length > 0) {

                    $.each(_this.shoppingCartList, function(i, d){

                        $.each(d.tbmqq433VoList, function(ii, dd){

                            if (_this.selectedData.indexOf(dd.goodid) >= 0) {

                                totalAmt = addAmt(totalAmt, dd.amt);
                            }

                        })

                    })

                }

                return totalAmt;

            }
        }


    });

    win.app = app;

})(window||{});