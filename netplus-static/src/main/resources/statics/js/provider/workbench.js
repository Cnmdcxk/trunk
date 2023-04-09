(function () {
    var RZ = new Vue({
        el:'#RZ',
        data:{

            currentUser: {},
            countMessage:0,
            MessageList:[],
            countawait:0,
            pageIndex:1,
            pageSize:5,
            showContactsDialog:false,
            showContactDialogTitle:'完善联系人信息',
            bizContact:'',
            bizContactPhone:'',
        },
        mounted:function(){
            this.getCurrentUser();
            this.init();
            this.searchCore();
            this.getAllCount();

        },
        methods:{
            perfectContacts:function(){

            },
            searchCore: function () {
                var self = this;

                self.param = $.extend(self.param, {
                    statusList: [15]
                });

                ajax(
                    "/api/mall/getMyWaitingTakeOrder/",
                    self.param,
                    function (resp) {
                        self.countawait = resp;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            getCurrentUser:function () {
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
            removeDot: function(id,pageCode){
                var self = this;
                ajax('/api/messaging/updateState/',
                    {
                        messagingId:id,
                    },
                    function (resp) {
                        self.init();
                        self.getAllCount();
                        self.goMessageIndex(pageCode);
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            goMessageIndex: function(pageCode){
                window.location.href="/messaging/index?pageCode="+pageCode;
            },

            showBizContactsDialog: function (){
                this.bizContact=this.currentUser.bizContact;
                this.bizContactPhone=this.currentUser.bizContactPhone;
                this.showContactsDialog=true;
            },

            updateBizContact: function (){
                var self = this;
                if(self.bizContact == null || self.bizContact.trim() == ''){
                    dialog.error("联系人不能为空!");
                    return;
                }

                if(self.bizContactPhone == null || self.bizContactPhone.trim() == ''){
                    dialog.error("联系方式不能为空!");
                    return;
                }

                if(!(/^[1][3,4,5,6,7,8,9][0-9]{9}$/.test(self.bizContactPhone))){
                    dialog.error("联系方式请输入正确的手机号!");
                    return;
                }

                ajax('/api/provider/updateSupplierBizContact',
                    {
                        bizContact:self.bizContact,
                        bizContactPhone:self.bizContactPhone,
                    },
                    function (resp) {
                        self.currentUser.bizContact=self.bizContact;
                        self.currentUser.bizContactPhone=self.bizContactPhone;
                        self.showContactsDialog=false;
                        dialog.success("修改成功!");
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

        }
    })
})();
