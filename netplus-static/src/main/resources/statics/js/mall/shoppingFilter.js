(function(win){

    var url = {
        search: "/api/mall/search/",
        addCart: "/api/mall/addShoppingCart/",
        addCollect:"/api/mall/goodsCollect/addMyCollect/",
        delMyCollect: "/api/mall/goodsCollect/delMyCollect/",
        addViewHistory:"/api/mall/GoodsListed/addViewHistory/",
        getMatrlQualityByMatrlId:'/api/mall/basicData/getMatrlQualityByMatrlId/',
    };

    var RZ = new Vue({
        el: '#RZ',
        data: {
            value: "",
            pageIndex: 1,
            pageSize: 30,
            totalCount: 0,
            isLogin:IS_LOGIN,
            role:role,
            searchKey: searchKey,
            twolevelclaname: twolevelclaname,
            twoLevelclapk: twolevelclapk,
            categoryname:categoryname,
            categorypk:categorypk,
            onelevelclaname:onelevelclaname,
            onelevelclapk:onelevelclapk,
            productname:productname,
            orderBy: 't1.shelvesdate||t1.shelvestime',
            asc: false,

            filtersConfig: {
                selects: [{key: 'price', type: 'number-range', name: '价格区间',}],
                lines: [
                    {key: 'categoryNameList', type: 'more-change', name: '一级分类'},
                    {key: 'onelevelClaNameList', type: 'more-change', name: '二级分类'},
                    {key: 'twoLevelClaNameList', type: 'more-change', name: '三级分类'},
                    {key: 'brandList', type: 'more-change', name: '品牌',},
                ]
            },

            filters: {
                twoLevelClaNameList: [],
                categoryNameList:[],
                onelevelClaNameList:[],
                brandList: [],
                price: []
            },

            tableData: [],

            cdn: cdn,

            showCollectDialog: false,
            isCollect: "N",

        },
        mounted: function () {

            if (this.categorypk !='' && this.categorypk != undefined) {
                this.$refs.filters.addParam("categoryNameList", [this.categorypk], this.categoryname, true);
            }
            if (this.onelevelclapk !='' && this.onelevelclapk != undefined) {
                this.$refs.filters.addParam("onelevelClaNameList", [this.onelevelclapk], this.onelevelclaname, true);
            }
            if (this.twoLevelclapk !='' && this.twoLevelclapk != undefined) {
                this.$refs.filters.addParam("twoLevelClaNameList", [this.twoLevelclapk], this.twolevelclaname, true);
            }
            if (this.productname !='' && this.productname != undefined) {
                this.$refs.filters.addParam("productNameList", [this.productname], this.productname, true);
            }

            if (this.searchKey !='' && this.searchKey != undefined) {
                this.$refs.filters.addParam("searchKey", this.searchKey, this.searchKey, true);
            }

            this.search(this.$refs.filters.__getParam());
        },
        methods: {
            __getParam: function () {
                return Vue.tools.filter(this.filters, function (v) {return v});
            },
            addShoppingCart: function(e, item){
                var _this = this;

                ajax("/api/mall/addShoppingCart/", {goodId: item.goodid, isDefaultAdd: "Y"}, function(){

                    item.isAddCart = "Y";
                    toolboxVue.$data.shoppingCartCount = toolboxVue.$data.shoppingCartCount + 1;
                    _this.showAnimation(e.clientX, e.clientY);
                }, function(err){
                    dialog.error(err);
                })
            },

            addCollect: function(item){

                var _this = this;

                ajax(url.addCollect, {goodId: item.goodid}, function(){

                    item.isCollect = "Y";
                    toolboxVue.$data.collectCount = toolboxVue.$data.collectCount + 1;
                    _this.showCollectDialog = true;

                }, function(err){
                    dialog.error(err);
                })
            },

            delMyCollect: function(item){
                console.log(item.goodid)
                var param = {goodIds:[item.goodid]};

                ajax(
                    url.delMyCollect,
                    param,
                    function (resp) {
                        toolboxVue.$data.collectCount = toolboxVue.$data.collectCount - 1;
                        dialog.success("取消收藏成功", function(){
                            item.isCollect = "N";
                        });
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
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
                self.pageSize = 30;
                self.param = var1;
                self.doSearch();
            },

            getMatrlQuality() {
                //物资定性
                let self = this;
                // let matrlIdList = []
                let params = {
                    matrlIdList: []
                }
                self.tableData.forEach((item)=> {
                    params.matrlIdList.push(item.matrlid)
                })
                ajax(
                    url.getMatrlQualityByMatrlId,
                    params,
                    function (data) {
                        self.tableData.forEach((item)=> {
                            data.forEach((ii)=> {
                                if(item.matrlid == ii.wzmcbm_pk) {
                                    item.wzdxmc = ii.wzdxmc
                                }
                            })
                        })
                        console.log('self.tableData',self.tableData)
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
                this.showCollectDialog = !this.showCollectDialog
                this.showCollectDialog = !this.showCollectDialog
                //重新渲染
                console.log('物资定性')
            },

            doSearch: function () {
                var self = this;
                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                    orderBy: self.orderBy,
                    asc: self.asc,
                    page: "A",
                });

                ajax(
                    url.search,
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.getMatrlQuality()
                        self.totalCount = resp.totalCount;
                        self.filters.twoLevelClaNameList = resp.resultMap.twoLevelClaNameList;
                        self.filters.brandList = resp.resultMap.brandList;
                        self.filters.categoryNameList=resp.resultMap.categoryNameList;
                        self.filters.onelevelClaNameList=resp.resultMap.oneLevelClaNameList;
                        self.tableData.forEach((gg, ii)=> {
                            if (gg.futurePoPriceStartDate!=null) {
                                let b = gg.futurePoPriceStartDate.split('')
                                b.splice(4,0,'-')
                                b.splice(7,0,'-')
                                gg.futurePoPriceStartDate = b.join('')
                            }
                        })
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            onOrderBy: function(key, asc){

                this.orderBy = key;
                this.asc = asc;

                console.log(key, asc);
                this.doSearch();

            },

            goDetail: function (goodId) {
                formForward("/mall/goodsDetail", {goodsId: goodId}, '_blank', 'get');
                ajax(url.addViewHistory, {goodId: goodId},function(){
                    
                }, function(err){
                    dialog.error(err)
                })
            },
        },

    });

})(window || {});