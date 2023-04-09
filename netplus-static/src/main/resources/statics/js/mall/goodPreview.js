(function(win){


    var url = {
        getGoodPicList:"/api/mall/GoodsPicture/getGoodPicList/",
        getGoodsDetail: "/api/mall/GoodsListed/getGoodsDetail/"

    };

    var app = new Vue({
        el: '#RZ',
        data: {
            goodsId: goodsId,
            viewHistory: [],
            imgList: [],

            value:'',
            searchKey: '',
            pageIndex: 1,
            pageSize: 10,
            totalCount: 0,
            goods:[],
            nums: 1,
            goodsGroup: [],
            tableData:[],
            cdn: cdn
        },
        mounted: function(){
            this.getGoodsDetail();
            this.getGoodPicList();
        },

        methods: {

            getGoodsDetail: function(){
                var self =this;
                ajax(
                    url.getGoodsDetail,
                    {goodsId: self.goodsId},
                    function (resp) {
                        self.goods=resp[0];

                        if (self.goods.minbuyqty > 0) {
                            self.nums = self.goods.minbuyqty;
                        }else{
                            self.nums = 1;
                        }

                        $('#content').html(resp.content);
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
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
        }
    });

    win.app = app;

})(window||{})