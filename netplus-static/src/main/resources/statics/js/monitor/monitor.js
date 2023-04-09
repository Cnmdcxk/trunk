(function (win) {

    var url = {
        getInterFaceDataList: "/api/monitor/getInterFaceDataList",
        restartReq: "/api/monitor/restartReq",
    }

    var app=new Vue({
        el:'#RZ',
        data:function(){
            return{
                centerDialogVisible1:false,
                dataList: [
                ],
                reqdata:'',
                respdata:'',
                requrl:'',
                reqid:'',
                pageIndex: 1,
                pageSize: 10,
                totalCount: 0,
                param: {},
                filters: {
                    reqnameList:[
                        {
                            "key": "JK0024",
                            "value": "JK0024",
                            "total": null
                        },
                        {
                            "key": "JK0010",
                            "value": "JK0010",
                            "total": null
                        },
                        {
                            "key": "JK0028",
                            "value": "JK0028",
                            "total": null
                        },
                        {
                            "key": "JK0002",
                            "value": "JK0002",
                            "total": null
                        },
                        {
                            "key": "JK0006",
                            "value": "JK0006",
                            "total": null
                        },
                        {
                            "key": "JK0031",
                            "value": "JK0031",
                            "total": null
                        },
                        {
                            "key": "JK0003",
                            "value": "JK0003",
                            "total": null
                        },
                        {
                            "key": "JK0007",
                            "value": "JK0007",
                            "total": null
                        },
                        {
                            "key": "accessToken",
                            "value": "accessToken",
                            "total": null
                        },
                        {
                            "key": "JK0001",
                            "value": "JK0001",
                            "total": null
                        },
                        {
                            "key": "JK0005",
                            "value": "JK0005",
                            "total": null
                        },
                        {
                            "key": "JK0015",
                            "value": "JK0015",
                            "total": null
                        },
                        {
                            "key": "JK0011",
                            "value": "JK0011",
                            "total": null
                        },
                        {
                            "key": "JK0025",
                            "value": "JK0025",
                            "total": null
                        },
                        {
                            "key": "getDetail",
                            "value": "getDetail",
                            "total": null
                        },
                        {
                            "key": "submitOrder",
                            "value": "submitOrder",
                            "total": null
                        },
                        {
                            "key": "JK0012",
                            "value": "JK0012",
                            "total": null
                        },
                        {
                            "key": "JK0017",
                            "value": "JK0017",
                            "total": null
                        },
                        {
                            "key": "JK0030",
                            "value": "JK0030",
                            "total": null
                        },
                        {
                            "key": "JK0008",
                            "value": "JK0008",
                            "total": null
                        },
                        {
                            "key": "JK0014",
                            "value": "JK0014",
                            "total": null
                        },
                        {
                            "key": "get",
                            "value": "get",
                            "total": null
                        },
                        {
                            "key": "JK0019",
                            "value": "JK0019",
                            "total": null
                        },
                        {
                            "key": "JK0016",
                            "value": "JK0016",
                            "total": null
                        },
                        {
                            "key": "JK0009",
                            "value": "JK0009",
                            "total": null
                        },
                        {
                            "key": "JK0027",
                            "value": "JK0027",
                            "total": null
                        },
                        {
                            "key": "JK0013",
                            "value": "JK0013",
                            "total": null
                        },
                        {
                            "key": "JK0004",
                            "value": "JK0004",
                            "total": null
                        },
                        {
                            "key": "JK0023",
                            "value": "JK0023",
                            "total": null
                        },
                        {
                            "key": "JK0022",
                            "value": "JK0022",
                            "total": null
                        },
                        {
                            "key": "JK0032",
                            "value": "JK0032",
                            "total": null
                        },
                        {
                            "key": "getSellPrice",
                            "value": "getSellPrice",
                            "total": null
                        },
                        {
                            "key": "delete",
                            "value": "delete",
                            "total": null
                        },
                        {
                            "key": "JK0018",
                            "value": "JK0018",
                            "total": null
                        },
                        {
                            "key": "JK0026",
                            "value": "JK0026",
                            "total": null
                        },
                        {
                            "key": "skuImage",
                            "value": "skuImage",
                            "total": null
                        }
                    ],
                    callerList:[
                        {key: "ERP", value: "ERP"},
                        {key: "PT", value: "PT"},
                        {key: "ZKH", value: "ZKH"}
                    ],
                    calleeList:[
                        {key: "ERP", value: "ERP"},
                        {key: "PT", value: "PT"},
                        {key: "ZKH", value: "ZKH"}
                    ],
                    statusList:
                        [{key: 'OK', value: 'OK'}, {key: 'FAIL', value: 'FAIL'}],
                },
                filtersConfig: {
                    searchKey: {
                        placeholder: '接口id/接口名称/接口调用方/接口被调用方',value:''
                    },
                    selects: [
                        {key: 'reqnameList', type: 'more-change', name: '接口名称'},
                        {key: 'callerList', type: 'more-change', name: '调用方'},
                        {key: 'calleeList', type: 'more-change', name: '被调用方'},
                        {key: 'statusList', type: 'more-change', name: '状态'},
                        {key: 'reqTime',type: 'date-range', name: '接口请求时间'}
                    ]

                },


            }
        },

        mounted: function () {

            this.$refs.filters.addParam("reqTime", {min:new Date().dateFormat("yyyy-MM-dd"), max:new Date().dateFormat("yyyy-MM-dd")},'接口请求时间' + ':' + new Date().dateFormat("yyyy-MM-dd") + "~" +new Date().dateFormat("yyyy-MM-dd"),true);
            this.search(this.$refs.filters.__getParam());

        },

        methods: {

            restartReq: function(d){

                var _this = this;

                ajax(url.restartReq, {reqId: d.reqid, caller: d.caller, callee: d.callee}, function(json){

                    dialog.success("重发成功", function(){
                        _this.searchCore();
                    })

                }, function(err){
                    dialog.error(err);
                })

            },

            //下拉框change事件
            onChange: function () {
                this.$forceUpdate()
            },


            pageChange: function (pageIndex) {
                this.pageIndex = pageIndex;
                this.searchCore();
            },

            sizeChange: function (pageSize) {
                this.pageSize = pageSize;
                this.searchCore();
            },

            search: function (var1) {
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
                }),
                    ajax(
                        url.getInterFaceDataList,
                        self.param,
                        function (resp) {
                            console.log(resp);
                            self.dataList = resp.items;
                            self.totalCount = resp.totalCount;
                            //self.filters.callerList = resp.resultMap.callerKV;
                            //self.filters.calleeList = resp.resultMap.calleeKV;
                            //self.filters.reqnameList = resp.resultMap.reqNameKV;
                        },
                        function (err) {
                            dialog.error(err);
                        }
                    );
            },
            dataDisplay:function(data){
                var self =this;
                ajax(
                    "/api/monitor/getRespData",
                    {reqid:data},
                    function (resp) {
                        self.reqdata = resp.reqdata;
                        self.respdata = resp.respdata;
                        self.requrl = resp.requrl;
                        self.centerDialogVisible1 = true;
                        console.log(data);
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );

            }

                }
            }
        );

    win.app = app;
})(window);