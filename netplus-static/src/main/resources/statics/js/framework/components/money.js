/*
    格式化金钱
    举例：传入money为Number
    <money-view money="12.00"></money-view>
*/
Vue.component("money-view", {
    template: '<span class="money">￥<i class="f24 fb">{{moneyList[0]}}</i><span v-if="moneyList[1] != null">.{{moneyList[1]}}</span> </span>',
    props: {
        money:{
            type:Number,
            default:0
        }
    },
    data: function () {
       return{
           moneyNum:this.money,
           moneyList:[],
       }
    },
    mounted:function(){
        this.moneyFormat();
    },
    methods: {
        moneyFormat:function(){
            var num = this.moneyNum.toString();
            this.moneyList = num.split(".");
        }
    },
    watch:{
        money:{
            handler:function(val){
                this.moneyNum = val;
                this.moneyFormat();
            },
            deep:true
        }
    }
});