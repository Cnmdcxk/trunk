(function () {
    var RZ=new Vue({
        el:'#RZ',
        data:{
            list:[],
            pageIndex:1,
            pageSize:5,
            totalCount:null,
            countList:{},
            pageCode:pageCode,
            myMsgGroupList:[],
        },
        filters: {
            capitalize: function (value) {
                value = value.toString();
                return value.substr(0, 100);
            }
        },

        mounted:function(){
            this.getTabCount();
            this.getAllCount();
        },

        methods:{

            clickMore:function(page){
                this.pageIndex = page+1;
                if(this.pageIndex <= Math.ceil(this.totalCount/this.pageSize)){
                    this.init(this.pageCode)
                }else {
                    dialog.error("已展示所有消息")
                }
            },

            getTabCount:function(){
                var self = this;
                ajax('/api/messaging/getMyMsgGroupList/',
                    {
                    },
                    function (resp) {
                        self.myMsgGroupList = resp;
                        if(self.pageCode == null || self.pageCode == ''){
                            if(self.myMsgGroupList.length > 0){
                                self.pageCode=self.myMsgGroupList[0].key;
                            }
                        }
                        self.init(self.pageCode);
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            getAllCount:function(){
                var self = this;
                ajax('/api/messaging/getCountWithGroup/',
                    {
                    },
                    function (resp) {
                        self.countList = resp;
                        self.$forceUpdate();
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            init: function (a) {
                var self = this;
                this.pageCode=a;
                ajax('/api/messaging/query/',
                    {
                        typeList:[a],
                        pageIndex:self.pageIndex,
                        pageSize:self.pageSize
                    },
                    function (resp) {
                        self.totalCount = resp.totalCount;
                        self.list = resp.items;
                        self.$forceUpdate();
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            removeDot:function(id,url){
                var self = this;
                ajax('/api/messaging/updateState/',
                    {
                        messagingId:id,
                    },
                    function (resp) {
                        self.init(self.pageCode);
                        self.getAllCount();
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            removeAll:function(){
                var self = this;
                ajax('/api/messaging/updateAllState/',
                    {
                    },
                    function (resp) {
                        self.init(self.pageCode);
                        self.getAllCount();
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            }
        }

    })
})();
