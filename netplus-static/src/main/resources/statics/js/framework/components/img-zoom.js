/*
    图片放大器 使用
    <img-zoom :img_list="imgList" class="fl"></img-zoom>
    比如数组：imgList:[{img:'http://demo.lanrenzhijia.com/2015/jqzoom0225/images/01_mid.jpg'}]
*/
Vue.component("img-zoom", {
    template: `
    <div class="img_zoom img-zoom-box">
        <div class="left">
            <img class="leftImg" v-if="imgList.length!==0" :src="imgList[currImgIndex].img" alt="">
            <img class="leftImg" v-else src="/statics/img/shop/noPic.png" alt="">
            <!-- 鼠标层罩 -->
            <div v-show="topShow" class="top" :style="topStyle"></div>
            <!-- 最顶层覆盖了整个原图空间的透明层罩 -->
            <div class="maskTop"
                @mouseenter="enterHandler"
                @mousemove="moveHandler"
                @mouseout="outHandler">
            </div>
        </div>
        <div v-show="rShow" class="right" v-if="imgList.length!==0">
            <img :style="r_img" class="rightImg" :src="imgList[currImgIndex].img" alt="">
        </div>
        <!-- 轮播 -->
        <div class="img_zoom_bottom" v-if="imgList.length!==0">
            <div class="swiper-container1" ref="swiperTop">
                <div class="swiper-wrapper">
                    <div class="swiper-slide" v-for="(item ,index) in imgList" @click="imgZoomClick(index)">
                        <div class="img_zoom_swiper" :class="index==currImgIndex?'curr':''" >
                            <img :src="item.img" alt="" class="img">
                        </div>
                    </div>
                </div>
                <div class="swiper-button-next" @click="next"><i class="iconfont icon-right f16"></i> </div>
                <div class="swiper-button-prev" @click="prev"><i class="iconfont icon-left f16"></i> </div>
            </div>
        </div>		
    </div>
    `,
    props: {
        img_list:{
            type:Array,
            default:[]
        }
    },
    data: function () {
       return{
            imgList:[],
            topStyle:{transform:''},
            r_img: {},
            topShow:false,
            rShow:false,
            currImgIndex:0,

       }
    },
    mounted: function(){
        this.imgList = this.img_list;
    },
    methods: {
        prev(){
            this.$refs.swiperTop.$swiper.slidePrev();
        },
        next(){
            this.$refs.swiperTop.$swiper.slideNext();
        },
       // 鼠标进入原图空间函数
        enterHandler:function() {
            // 层罩及放大空间的显示
            if(this.img_list.length!=0){
                this.topShow = true;
                this.rShow = true;
            }
        },
        // 鼠标移动函数
        moveHandler:function(event) {
            // 鼠标的坐标位置
            let x = event.offsetX
            let y = event.offsetY
            // 层罩的左上角坐标位置，并对其进行限制：无法超出原图区域左上角
            let topX = (x-100) < 0 ? 0:(x-100)
            let topY = (y-100) < 0 ? 0:(y-100)
            // 对层罩位置再一次限制，保证层罩只能在原图区域空间内
            if(topX>165) {
                topX = 165
            }
            if(topY>165) {
                topY = 165
            }
            // 通过 transform 进行移动控制
            this.topStyle.transform = `translate(${topX}px,${topY}px)`
            this.r_img.transform = `translate(-${2*topX}px,-${2*topY}px)`
        },
        // 鼠标移出函数
        outHandler:function() {
            // 控制层罩与放大空间的隐藏
            this.topShow = false
            this.rShow = false
        },
        imgZoomClick:function(e){
            this.currImgIndex = e;
        },
        nextBtn:function(){
            this.currImgIndex++;
            if(this.currImgIndex>this.imgList.length){
                this.currImgIndex=0
            }
        },
        preBtn:function(){
            this.currImgIndex--;
            if(this.currImgIndex<0){
                this.currImgIndex=0
            }
        },
    },
    watch:{
        img_list:function(newVal){
            this.imgList =newVal;
        }
    }
});