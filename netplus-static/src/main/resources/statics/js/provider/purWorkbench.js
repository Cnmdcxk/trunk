(function (win) {
    var RZ=new Vue({
        el: '#RZ',
        data: {
            pageIndex: 1,
            pageSize: 10,
            List: [],
            countMessage: 0,
            MessageList: [],
            currentUser: {},
            totalCount: 0,
            quickListNum: {
                toApproveNum: 0,
                toEntrustNum: 0
            },
            quickListNumSum: 0,
            param: {},
            myItems:{
                'PG0022':false,
                'PG0044':false,
            },
        },


        mounted:function(){
            this.getUserMenuList();
            this.getCurrentUser();
            this.init();
            this.getAllCount();
        },

        methods:{
            getUserMenuList: function (){
                var _this = this;
                ajax("/api/provider/getUserMenuList/", {belongto: "PC"}, function(json){
                    _this.menu = json;
                    _this.showMyItems(json);
                    _this.getShelfCount();
                }, function(err){
                    dialog.error(err);
                });
            },
            showMyItems: function (menuList){
                var _this = this;
                $.each(menuList, function(i, d){
                    if(d.childmenulist != null){
                        _this.showMyItems(d.childmenulist);
                    }

                    Object.keys(_this.myItems).forEach((key)=>{
                        if(d.code == key){
                            _this.myItems[key]=true;
                        }
                    });
                });
            },

            getShelfCount: function (){
                var self = this;
                ajax('/api/mall/GoodsListed/getShelfCount/',
                    {
                    },
                    function (resp) {
                        if(self.myItems.PG0022 == true){
                            self.quickListNum.toApproveNum=resp.TOTALCOUNT1;
                        }
                        if(self.myItems.PG0044 == true){
                            self.quickListNum.toEntrustNum=resp.TOTALCOUNT2;
                        }
                        self.quickListNumSum=self.quickListNum.toApproveNum+self.quickListNum.toEntrustNum;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            gotoMessageBox: function () {
                window.location.href = '/messaging/index';
            },
            init: function () {
                var self = this;
                ajax('/api/messaging/query/',
                    {
                        pageIndex:self.pageIndex,
                        pageSize:5
                    },
                    function (resp) {
                        self.MessageList = resp.items;
                        self.$forceUpdate();
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            getAllCount: function () {
                var self = this;
                ajax('/api/messaging/getAllCount/',
                    {},
                    function (resp) {
                        self.countMessage = resp.sum1 + resp.sum2
                    },
                    function (err) {
                    }
                );
            },
            getCurrentUser: function () {
                var self = this;
                ajax('/api/provider/getCurrentUser/',
                    {loading: false},
                    function (resp) {
                        self.currentUser = resp;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            removeDot:function(id,pageCode){
                var self = this;
                ajax('/api/messaging/updateState/',
                    {
                        messagingId:id,
                    },
                    function (resp) {
                        self.init(self.pageCode);
                        self.getAllCount();
                        self.goMessageIndex(pageCode);
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            goMessageIndex(pageCode){
                window.location.href="/messaging/index?pageCode="+pageCode;
            },

            Pending:function(){
                window.location.href ='/approve/purchaseApprove-list';
            },
            Listing:function(){
                window.location.href ='/mall/goodsAudit';
            },
            below:function(){
                window.location.href ='/mall/goodsUpdate';
            }


        },
    })
})(window);