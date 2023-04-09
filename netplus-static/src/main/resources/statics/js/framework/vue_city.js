
Vue.component('city-component',{
    props:{
        check:{
            type: String,
            default: '全国'
        },
        title:{
            type: String
        }
    },
    data:function () {
        return {
            cityShow:false,
            provinceList:[],
            cityList:[],
            areaList:[],
            checkProvince:'',
            checkCity:'',
            checkArea:'',
            checkTown:'',
            ckWhat:0,
            provinceCode:'',
            cityCode:'',
            districtCode:'',
            code:'',
            isCity:false,
            isArea:false,
            isLoading:false
        }
    },
    computed: {
        checkDisplay:function () {
            if(this.checkProvince==="" && this.checkCity==="" && this.checkArea===""){
                return this.check;
            }else{
                return this.checkProvince + this.checkCity + this.checkArea;
            }
        }

    },
    template:'<div class="dd" style="line-height: 1.2;"> ' +
    '<div id="store-selector" class="hover store-selector" @click="selectCity" @mouseleave="cityShow = false"> ' +
    '<div class="text choose" :class="{border:cityShow}"> <div title="全国" class="hover" id="cityName">{{checkDisplay}}</div> <b></b> </div>' +
    '<input id="checkProvince" type="hidden" :value="checkProvince">' +
    '<input id="checkCity" type="hidden" :value="checkCity">'+
    '<div class="content hover tl" v-show="cityShow"> ' +
    '<div style="height:25px">选择新地址<b></b>' +
    '</div> ' +
    '<div data-widget="tabs" id="JD-stock" class="m JD-stock"> ' +
    '<div class="mt"> ' +
    '<ul class="tab hover"> ' +
    '<li data-index="0" data-widget="tab-item" :class="{curr:ckWhat==0}" @click="initProvince()"> ' +
    '<a href="#none" class="hover"> <em class="hover">{{checkProvince? checkProvince : "请选择"}}</em> <i class="hover"></i> </a> ' +
    '</li> ' +
    '<li data-index="1" data-widget="tab-item" :class="{curr:ckWhat==1}" v-if="isCity" @click="ckWhat = 1"> ' +
    '<a href="#none" class=""> <em class="hover">{{checkCity}}</em> <i></i> </a> ' +
    '</li> ' +
    '<li data-index="2" data-widget="tab-item" :class="{curr:ckWhat==2}" v-if="isArea" @click="ckWhat = 2"> ' +
    '<a href="#none" class=""> <em class="hover">{{checkArea}}</em> <i></i> </a> ' +
    '</li> ' +
    '</ul> ' +
    '<div class="stock-line"></div> ' +
    '</div> ' +
    '<div data-area="0" data-widget="tab-content" id="stock_province_item" :class="{hover:ckWhat==0}" v-if="ckWhat == 0"> ' +
    '<ul class="area-list" v-if="!isLoading"> ' +
    '<li v-for="p in provinceList" :class="{curr:ckWhat==0}"> ' +
    '<a href="#none" :data-value="p.key" @click.stop="initCity(p);">{{p.value}}</a> ' +
    '</li> ' +
    '</ul> ' +
    '<div class="iloading hover" v-if="isLoading">正在加载中，请稍候...</div> ' +
    '</div> ' +
    '<div data-area="1" data-widget="tab-content" id="stock_city_item" :class="{curr:ckWhat==1}" v-if="ckWhat == 1"> ' +
    '<ul class="area-list" v-if="!isLoading"> ' +
    '<li v-for="c in cityList" :class="{curr:ckWhat==1}"> ' +
    '<a href="#none" :data-value="c.code" @click.stop="initArea(c);">{{c.name}}</a> ' +
    '</li> ' +
    '</ul> ' +
    '<div class="iloading hover" v-if="isLoading">正在加载中，请稍候...</div> ' +
    '</div> ' +
    '<div data-area="2" data-widget="tab-content" id="stock_area_item" :class="{curr:ckWhat==2}" v-if="ckWhat == 2"> ' +
    '<ul class="area-list" v-if="!isLoading"> ' +
    '<li v-for="a in areaList" :class="{curr:ckWhat==2}"> ' +
    '<a href="#none" :data-value="a.code" @click.stop="initShow(a);">{{a.name}}</a> ' +
    '</li> ' +
    '</ul> ' +
    '<div class="iloading hover" v-if="isLoading">正在加载中，请稍候...</div> ' +
    '</div> ' +
    '</div> ' +
    '</div> ' +
    '<div class="close"></div> ' +
    '</div> ' +
    '<div id="store-prompt"><strong></strong></div> ' +
    '</div>',
    methods:{
        selectCity:function () {
            this.cityShow = true;
            this.initProvince();
        },
        initProvince:function () {
            var self = this;
            self.isLoading = true;
            self.ckWhat = 0;

            var param = {province: "province"};
            ajax(
                'http://220.194.33.131/logistics/init-province/',
                param,
                function (resp) {
                    self.provinceList = resp.data;
                    self.isLoading = false;
                },
                function (err) {
                    self.$message({
                        message: err,
                        type: 'error',
                        center: true,
                        duration:2000
                    });
                }
            );
        },
        initCity:function(p){
            var self = this;
            self.isLoading = true;
            self.checkProvince = p.value;
            self.provinceCode = p.key;
            self.code = p.value;
            self.checkCity = '';
            self.checkArea = '';
            self.ckWhat = 1;
            self.cityList = [];
            self.isCity = true;

            var param = {"provinceCode":p.key};
            ajax(
                'http://220.194.33.131/logistics/init-city/',
                param,
                function (resp) {
                    self.cityList = resp.data;
                    self.isLoading = false;
                },
                function (err) {
                    self.$message({
                        message: err,
                        type: 'error',
                        center: true,
                        duration:2000
                    });
                }
            );

        },
        initArea:function (a) {
            var self = this;
            self.isLoading = true;
            self.checkCity = a.name;
            self.cityCode = a.code;
            self.code += a.name;
            self.checkArea = '';
            self.ckWhat = 2;
            self.areaList = [{'name':'管庄镇', 'value':'guanzhuangzheng'}, {'name':'北苑', 'value':'beiyuan'}];
            self.isArea = true;

            var param = {"cityCode":a.code};
            ajax(
                'http://220.194.33.131/logistics/init-area/',
                param,
                function (resp) {
                    self.areaList = resp.data;
                    self.isLoading = false;
                },
                function (err) {
                    self.$message({
                        message: err,
                        type: 'error',
                        center: true,
                        duration:2000
                    });
                }
            );
        },
        initShow:function(t,id){
            var self = this;
            self.checkArea = t.name;
            self.districtCode = t.code;
            self.code += t.name;

            self.cityShow = false;

            var param = {
                checkProvince:self.checkProvince,
                checkCity:self.checkCity,
                checkArea:self.checkArea,
                title:self.title,
                isLoading:true
            };
            self.$emit('end',param);
            // resultVue.checkCityEnd();
        }

    }
});
