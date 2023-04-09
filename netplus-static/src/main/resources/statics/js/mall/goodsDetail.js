(function(win){


    var url = {
        getUserViewHistory:"/api/mall/GoodsListed/getUserViewHistory/",
        getGoodPicList:"/api/mall/GoodsPicture/getGoodPicList/",
        searchGood: "/api/mall/search/",
        addShoppingCart:"/api/mall/addShoppingCart/",
        addCollect:"/api/mall/goodsCollect/addMyCollect/",
        delMyCollect: "/api/mall/goodsCollect/delMyCollect/",
        getCommentsByGoodsInfo: "/api/mall/goodsComment/getCommentsByGoodsInfo/",
        getSupplierBizContact: "/api/provider/getSupplierBizContact",
        getPriceCompare: "/api/mall/GoodsListed/priceCompare/",
        getMatrlQualityByMatrlId:'/api/mall/basicData/getMatrlQualityByMatrlId/',
    };

    var app = new Vue({
        el: '#RZ',
        data: {
            goodsId: goodsId,
            viewHistory: [],
            imgList: [],
            groupDisplay:'',
            goodsGroupLength:'',
            value:'',
            searchKey: '',
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            goods:{},
            goodsGroup: [],
            tableData:[],
            cdn: cdn,
            goodsData:[],
            showEditRemark: true,
            showSimilarDialog:false,
            isPlayIn:true,
            isPlayAd:false,
            commentPage:{
                pickLevel: 'all',
                commentList:[],
                pageIndex:1,
                pageSize:10,
                totalCount:0,
                countInfo:{},
            },
            showSupplierBizContact: false,
            supplierBizContact:{}
        },
        mounted: function(){
            this.showEditRemark=false;
            this.getGoodsDetail();
            this.getUserViewHistory();
            this.getGoodPicList();
        },
        methods: {
            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.searchCore();
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.searchCore();
            },
            search:function(){
                var self = this;
                self.pageIndex = 1;
                self.pageSize = 10;
                self.searchCore();
            },
            searchCore:function(){
                var self =this;
                if(self.goods.groupno == null || self.goods.groupno == ''){
                    self.updateGroup();
                    return;
                }
                var param = {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    groupNo: self.goods.groupno.trim(),
                    searchKey: self.searchKey,
                    asc: false,
                    orderBy: "t1.shelvesdate",
                    excludeGoodId: goodsId

                };
                ajax(
                    url.searchGood,
                    param,
                    function (resp) {
                        self.totalCount = resp.totalCount;
                        self.goodsGroup=resp.items;
                        app.goodsGroupLength = self.goodsGroup.length;
                        self.updateGroup();
                        $.each(resp.items, function(i, d){
                            if (d.minbuyqty > 0) {
                                d.qty = d.minbuyqty;
                            }else{
                                d.qty = 1;
                            }
                        })

                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            updateGroup:function () {
                if(app.goodsGroupLength>0 || (this.searchKey != null && this.searchKey != '')){
                    this.groupDisplay=true;
                }else {
                    this.groupDisplay=false;
                }
            },
            updateItemRemark: function (item) {
                /*console.log('item',item)
                this.showEditRemark = true;*/
            },
            editRemark: function () {
                this.showEditRemark = true;
                const iframe = document.getElementById("communication");
                console.log(iframe.contentWindow)
                if (iframe !== null && iframe.contentWindow !== null)
                {
                    console.log(this.goods.supplierPhone,this.goods.suppliername)
                    iframe.contentWindow.postMessage('聊天联系人' + "," + this.goods.supplierPhone + "," + this.goods.suppliername, '*');
                }
            },

            openSimilarDialog:function (matrltm) {

                var _this = this;

                ajax(
                    url.getPriceCompare,
                    {matrltmList: [matrltm]},
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
                /*
                var _this = this;
                ajax(
                    '/api/mall/GoodsListed/getGoodsDetail/',
                    {matrltm: matrltm},
                    function(resp){

                        $.each(resp, function(i, d){
                            d.oldQty = d.qty;
                        });

                        _this.goodsData = resp;
                        _this.showSimilarDialog = true;
                    },
                    function(err){
                        dialog.error(err);
                    }
                )*/

            },

            getMatrlQuality() {
                //物资定性
                let self = this;
                // let matrlIdList = []
                let params = {
                    matrlIdList: []
                }
                params.matrlIdList.push(self.goods.matrlid)

                ajax(
                    url.getMatrlQualityByMatrlId,
                    params,
                    function (data) {
                        self.goods.wzdxmc = data[0].wzdxmc
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
                setTimeout(()=> {
                    this.showMatrlQuality()
                },1500)
            },
            showMatrlQuality: function() {
                this.showSimilarDialog = !this.showSimilarDialog
                this.showSimilarDialog = !this.showSimilarDialog
                //重新渲染
                console.log('物资定性')
            },

            getGoodsDetail: function(){
                var self =this;
                ajax(
                    "/api/mall/GoodsListed/getGoodsDetail/",
                    {goodsId: self.goodsId},
                    function (resp) {
                        self.goods=resp[0];
                        self.getMatrlQuality()
                        if (self.goods.minbuyqty > 0) {
                            self.goods.qty = self.goods.minbuyqty;
                        }else{
                            self.goods.qty = 1;
                        }

                        $('#content').html(resp.content);

                        self.search();
                        console.log('测试',self.goods)
                        if (self.goods.futurePoPriceStartDate!=null) {
                            let b = self.goods.futurePoPriceStartDate.split('')
                            b.splice(4,0,'-')
                            b.splice(7,0,'-')
                            self.goods.futurePoPriceStartDate = b.join('')
                            console.log('测试',self.goods.futurePoPriceStartDate)
                        }
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            getUserViewHistory: function(){
                var self = this;

                ajax(
                    url.getUserViewHistory,
                    {},
                    function (resp) {
                        self.viewHistory=resp;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );

            },
            changeDisplay:function (val) {
                var _this=this;
                switch (val){
                    case "1":
                        _this.isPlayIn=true;
                        _this.isPlayAd=false;
                        break;
                    case "2":
                        _this.getComment(_this.commentPage.pickLevel);
                        _this.isPlayIn=false;
                        _this.isPlayAd=true;
                        break;
                }
            },
            getGoodPicList: function(){
                var self = this;

                ajax(
                    url.getGoodPicList,
                    {goodId: goodsId},
                    function (resp) {

                        var imgList = []

                        $.each(resp, function(i, d){
                            imgList.push({img: d.pictureurl});
                        })

                        self.imgList = imgList;
                        self.$nextTick(()=>{
                            var imgZoomBox = new Swiper('.swiper-container1', {
                                slidesPerView: 4,
                                spaceBetween: 10,
                                slidesPerView : 'auto',
                                navigation: {
                                    nextEl: '.swiper-button-next',
                                    prevEl: '.swiper-button-prev',
                                },
                            });
                        })
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            goDetail: function (goodsId) {
                formForward("/mall/goodsDetail", {goodsId: goodsId}, "_blank", "get");
            },

            goShoppingFilter: function(){
                formForward("/mall/shoppingFilter", {searchKey: this.searchKey}, "_blank", "get");
            },

            addCart : function(goods, e){
                var _this = this;
                ajax(url.addShoppingCart, {goodId: goods.goodid, qty: goods.qty, isAddUp: goods.isAddCart}, function(){
                    if(goods.isAddCart != 'Y'){
                        toolboxVue.$data.shoppingCartCount = toolboxVue.$data.shoppingCartCount + 1;
                        goods.isAddCart='Y'
                        if(goods.goodid == _this.goods.goodid){
                            _this.goods.isAddCart='Y'
                        }
                    }
                    if(e != null){
                        _this.showAnimation(e.clientX, e.clientY);
                    }else{
                        dialog.success("加入购物车成功!",_this.showSimilarDialog=false);
                    }
                }, function(err){
                    dialog.error(err);
                })

            },

            showAnimation: function (clientX, clientY) {
                var _this = this;

                var offset = $("#car").offset();
                var leftX = window.screen.width - 50;
                var leftY = (window.screen.height / 2) - 220;
                var flyer = $('<img class="u-flyer" src="'+ _this.cdn +'/img/home/banner_bg.png" width="30" height="30"  style="z-index:100001">');

                flyer.fly({
                    start: {
                        left: clientX,
                        top: clientY
                    },
                    end: {
                        left: leftX,
						top: leftY,
                        width: 0,
                        height: 0
                    },

                    onEnd: function () {
                        this.destory();
                    }
                });
            },

            addCollect: function(){

                var _this = this;

                ajax(
                    url.addCollect,
                    {goodId: _this.goodsId},
                    function(){
                        _this.goods.isCollect = "Y";
                        toolboxVue.$data.collectCount = toolboxVue.$data.collectCount + 1;
                        dialog.success("收藏成功");
                    },
                    function(err){
                        dialog.error(err);
                    })
            },
            goShoppingFilter:function (item,name,pkName,pkVal) {
                window.location.href = '/mall/shoppingFilter?'+item+'='+encodeURIComponent(name)+'&'+pkName+'='+encodeURIComponent(pkVal);
            },
            delMyCollect: function(){
                var _this = this;
                var param = {goodIds:[_this.goodsId]};

                ajax(
                    url.delMyCollect,
                    param,
                    function (resp) {
                        _this.goods.isCollect = "N";
                        toolboxVue.$data.collectCount = toolboxVue.$data.collectCount - 1;
                        dialog.success("取消收藏成功");
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            getComment: function (pickLevel){
                var _this = this;
                ajax(
                    url.getCommentsByGoodsInfo,
                    {
                        goodId: _this.goodsId,
                        commentLevel: pickLevel == 'all'?null:pickLevel,
                        pageIndex: _this.commentPage.pageIndex,
                        pageSize: _this.commentPage.pageSize
                    },
                    function(resp){
                        if(resp.status == "1"){
                            _this.commentPage.pickLevel=pickLevel;
                            _this.commentPage.commentList=resp.data.items;
                            _this.commentPage.totalCount=resp.data.totalCount;
                            _this.commentPage.countInfo=resp.data.resultMap.countInfo;
                        }else{
                            dialog.error(resp.msg);
                        }
                    },
                    function(err){
                        dialog.error(err);
                    }
                );
            },

            commentPageChange: function (pageIndex) {
                this.commentPage.pageIndex = pageIndex;
                this.getComment(this.commentPage.pickLevel);
            },

            commentSizeChange: function (pageSize) {
                this.commentPage.pageSize = pageSize;
                this.getComment(this.commentPage.pickLevel);
            },

            getCommentPreviewImages: function (images,index){
                if (index == 0) {
                    return images;
                }
                let start = images.slice(index);
                let remain = images.slice(0, index);
                return start.concat(remain);
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

})(window||{})