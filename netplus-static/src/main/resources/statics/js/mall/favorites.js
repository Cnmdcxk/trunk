(function(win){

    var url = {
        delMyCollect:"/api/mall/goodsCollect/delMyCollect/",
        addMyCollect:"/api/mall/goodsCollect/addMyCollect/",
        getMyCollectList:"/api/mall/goodsCollect/getMyCollectList/",
        addCart: "/api/mall/addShoppingCart/",
        updateCollectRemark: "/api/mall/goodsCollect/updateCollectRemark/",
        getMatrlQualityByMatrlId:'/api/mall/basicData/getMatrlQualityByMatrlId/',
    }

    var app = new Vue({
        el: '#RZ',
        data: {

            pageIndex: 1,
            pageSize: 12,
            totalCount: 0,
            checkList: [],
            checkAll: false,
            isIndeterminate: false,
            filtersConfig: {
                searchKey: {
                    placeholder: '商品名／品牌／物料编码／物料条码',
                    value: ''
                },
                lines: [{key: 'categoryNameList', type: 'single-change', name: '商品分类'}
                ]
            },

            filters: {
                categoryNameList: []
            },

            tableData: [],
            editRemarkItem: {},
            showEditRemark: false,
            remark: '',

        },

        mounted: function () {
            this.doSearch();
        },

        methods: {
            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.doSearch(this.param);
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.doSearch(this.param);
            },

            search: function(var1) {
                var self = this;
                self.pageIndex = 1;
                self.pageSize = 12;
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
                this.showEditRemark = !this.showEditRemark
                this.showEditRemark = !this.showEditRemark
                //重新渲染
                console.log('物资定性')
            },

            doSearch: function() {
                var self = this;

                self.param = $.extend(self.param, {
                    pageIndex: self.pageIndex,
                    pageSize: self.pageSize,
                });

                ajax(
                    url.getMyCollectList,
                    self.param,
                    function (resp) {
                        self.tableData = resp.items;
                        self.getMatrlQuality()
                        self.totalCount = resp.totalCount;
                        self.filters.categoryNameList = resp.resultMap.categoryNameList;
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

            handleCheckAllChange(val) {
                let checked=this.tableData.map(item =>{
                    return item.goodid;
                });
                this.checkList = val ? checked : [];
                this.isIndeterminate = false;
            },

            handleCheckedGoodsChange(value) {
                let checkedCount = value.length;
                this.checkAll = checkedCount === this.tableData.length;
                this.isIndeterminate = checkedCount > 0 && checkedCount < this.tableData.length;
            },

            clickDelete: function (){
                var _this = this;
                if (_this.checkList.length == 0) {
                    dialog.error("请选择需要取消收藏的商品!");
                    return false;
                }
                dialog.confirm('确定要删除吗', function(){
                    _this.delMyCollectChecked();
                });
            },

            delMyCollectChecked: function(){
                var _this = this;
                ajax(
                    url.delMyCollect,
                    {
                        goodIds:_this.checkList
                    },
                    function (resp) {
                       dialog.success("取消收藏成功", function(){
                           _this.search();
                           _this.checkList=[];
                           _this.checkAll=false;
                           _this.isIndeterminate=false;
                       });
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            addCart: function(goodId){
                var _this = this;
                ajax(
                    url.addCart,
                    {
                        goodId: goodId,
                        isDefaultAdd: "Y"
                    },
                    function (resp) {
                        _this.search();
                        let index=_this.checkList.indexOf(goodId);
                        if(index>-1){
                            _this.$delete(_this.checkList,index);
                        }
                        _this.handleCheckedGoodsChange(_this.checkList);
                        dialog.success("添加购物车成功");
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );

            },

            toDetail: function (goodId) {
                formForward("/mall/goodsDetail", {goodsId: goodId}, "_blank", "get");
            },

            editRemark: function (item) {
                this.editRemarkItem=item;
                this.remark=item.remark;
                this.showEditRemark=true;
            },

            updateItemRemark: function (){
                var _this = this;
                ajax(
                    url.updateCollectRemark,
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


        },

    });

})(window||{})