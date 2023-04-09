(function(win){

    var url = {
        getGoodsHistoryList: "/api/mall/getGoodsHistory/"
    };

    var app = new Vue({
        el:'#RZ',
        data: {
            inputType: 'supplierNameList',
            searchParams: {
                suppliername: '', // 供应商名称
                matrltm: '', // 物料条码
                pono: '' // 协议号
            }, // 筛选列表请求参数
            tableData:[],
            pickerDetailInfo: {
                supplierNameList: [],
                ponoList: [],
                matrltmList: []
            }
        },
        mounted:function(){
            this.searchCore();
        },
        methods:{
            /**
             * @description 匹配搜索信息
             * @param queryString {String} 搜索字符串
             * @param callback {Function} 回调函数
             */
            handleSearch(queryString, callback) {
                var filterResult = this.pickerDetailInfo[this.inputType].filter((res) => {
                    return (res.value.toLowerCase().indexOf(queryString.toLowerCase()) !== -1);
                });
                var results = queryString ?  filterResult : this.pickerDetailInfo[this.inputType];
                // 调用 callback 返回建议列表的数据
                callback(results);
            },
            /**
             * @description 输入框聚焦事件
             * @param inputType {String} 输入框类型
             */
            handleFocus(inputType) {
                this.inputType = inputType;
            },
            /**
             * @description 输入框匹配选中
             * @param item {Object} 选中的对象
             */
            handleSelect(item) {
                this.searchCore('search');
            },
            /**
             * @description 清除输入框内容
             * @param inputType {String} 输入框类型
             */
            handleClear(inputType) {
                this.searchParams[inputType] = '';
                this.searchCore('search');
            },
            /**
             * @description 检索商品历史数据
             * @param type {String} 检索类型
             */
            searchCore: function (type) {
                var _this = this;

                ajax(
                    url.getGoodsHistoryList,
                    _this.searchParams,
                    function (resp) {
                        if (type === 'search' && (_this.searchParams.suppliername || _this.searchParams.matrltm || _this.searchParams.pono)) {
                            _this.tableData = resp.items;
                        } else {
                            _this.tableData = [];
                        }
                        _this.totalCount = resp.totalCount;
                        _this.pickerDetailInfo.supplierNameList = resp.resultMap.suppliername;
                        _this.pickerDetailInfo.matrltmList = resp.resultMap.matrltm;
                        _this.pickerDetailInfo.ponoList = resp.resultMap.pono;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            }
        },

    });

    win.app = app;

})(window || {})