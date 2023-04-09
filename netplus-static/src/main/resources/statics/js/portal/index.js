(function () {
    var RZ = new Vue({
        el: '#RZ',
        data: {
            cdn: cdn,
            value: "",
            isLogin: IS_LOGIN,
            currentUser: {},
            tipsDialog: true,
            frameUrl:'',
            goodsList:[],
            page:[],
        },
        mounted: function () {
            if (IS_LOGIN) {
                this.getCurrentUser();
            }
            this.getGoods();
            this.getPicture();
            this.showEditRemark = false;
        },
        methods: {
            swiper:function(){
                var swiper = new Swiper('.swiper-container1', {
                    spaceBetween: 40,
                    slidesPerView : 'auto',
                    autoplay : true,
                    observer:true,
                    observeParents:true,
                    showEditRemark: true,
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
            getUserMenuList: function(){
                var _this = this;
                var _page = [];

                ajax("/api/provider/getUserMenuList/", {belongto: "PC"}, function(json){

                    $.each(json, function(i, d){

                        $.each(d.childmenulist, function(ii, dd){

                            $.each(dd.childmenulist, function(iii, ddd){

                                _page.push(ddd.url);

                            })

                        })

                    });

                    _this.page = _page;

                }, function(err){
                    dialog.error(err);
                });
            },
            checkUsuallyPage: function(page){
                if (this.page.indexOf(page) >= 0) {
                    return true;
                }else{
                    return false;
                }
            },

            getCurrentUser: function () {
                var self = this;
                ajax('/api/provider/getCurrentUser/',
                    {loading: false},
                    function (resp) {
                        self.currentUser = resp;

                        self.getUserMenuList();
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },


            // // 格式化日期
            // getDateFormat:function(date) {
            //     return date ? date.replace(/^(\d{4})(\d{2})(\d{2})$/, '$1-$2-$3') : '';
            // },
            // // 格式化时间
            // getTimeFormat:function(time) {
            //     return time ? time.toString().length == 4 ? time.replace(/^(\d{2})(\d{2})$/, '$1:$2') : time.replace(/^(\d{2})(\d{2})(\d{2})$/, '$1:$2:$3') : '';
            // },

            // toDecimal:function(num) {
            //     var num = num / 100000000;
            //     var f = parseFloat(num);
            //     if (isNaN(f)) {
            //         return num;
            //     }
            //     f = Math.round(num * 100)/100;
            //     return f;
            // },
            getGoods: function(){
                var self = this;

                ajax(
                    "/api/mall/GoodsListed/getSiteTop/",
                    {
                        top:2
                    },
                    function (resp) {
                        self.goodsList = resp;
                        self.$nextTick(function() {   // 初始化swiper
                            self.swiper();          
                        })
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            goDetail: function(val){
                formForward("/mall/goodsDetail", {goodsId: val}, "_blank", "get");
            },

            toShop: function(){
                formForward("/mall/shopping", {}, "_self", "get");
            }

        }
    })
})();
