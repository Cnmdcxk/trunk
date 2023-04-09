(function(win){

    var RZ = new Vue({
        el:'#RZ',
        data:{
            ticket:ticket,
            appId:appId,
            showEditRemark: true,
            frameUrl:'',
            tableData:[],
            banners: [], // ['img/home/banner1.png','img/home/banner2.png'],
        },
        mounted: function(){
            this.doSearch();
            this.getPicture();
            this.frameUrl = 'http://kfk8sn1.yong-gang.cn:30046/?appid='+this.appId+'&ticket='+this.ticket;
            this.showEditRemark=false;
        },

        methods:{
            editRemark: function () {
                this.showEditRemark = true;
            },
            doSearch: function () {
                var self =this;
                ajax(
                    "/api/mall/GoodsListed/getIndexGoodsList/",
                    {
                        top:20
                    },
                    function (resp) {
                        self.tableData = resp;
                        self.$nextTick(function() {   // 初始化swiper
                            self.swiper();          
                        })
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            getBannerSrc: function(cdn, item) {
                return  item;
            },
            getPicture:function () {
                var self=this;
                ajax('/api/mall/advertising/publishAdvertising/',
                    {},
                    function (resp) {
                        self.banners=resp.data;
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            swiper:function(){
                //初始化swiper方法
                var swiper = new Swiper('.swiper-container1', {
                    spaceBetween: 40,
                    slidesPerView : 'auto',
                    observer:true,
                    observeParents:true,
                    navigation: {
                        nextEl: '.swiper-button-next',
                        prevEl: '.swiper-button-prev',
                    },
                    pagination: {
                        el: '.swiper-pagination',
                        clickable: true,
                    },
                });
            },
            goShoppingFilterCategory:function (val1,val2){
                    window.location.href ='/mall/shoppingFilter?categoryname=' + encodeURIComponent(val1)+'&&categorypk='+encodeURIComponent(val2);
            },
            goShoppingFilterOneLevel:function (value) {
                window.location.href = '/mall/shoppingFilter?onelevelclaname=' + encodeURIComponent(value);
            },
            goGoodsDetail: function (val) {
                formForward("/mall/goodsDetail", {'goodsId': val}, "_blank", "get");
            }
        },

    });

})(window || {});

