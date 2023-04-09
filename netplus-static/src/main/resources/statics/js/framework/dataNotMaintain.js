/** 
 * 2020-04-14 pager by  平平
 *  *   eg: <not-maintain :data-not="dataNot" :is-show-pop="isShowPop" @gojh="goJH" @gosj="goSJ" @gocn='goCN'></not-maintain>
   父组件 方法
    goJH(){},
    goSJ(){},
    goCN(){}        
 *   	 
 *   
 */
Vue.component("not-maintain",{
	props: ["dataNot","isShowPop"],
	methods: {
		goJh:function(){
            this.$emit('gojh');
        },
        goSj:function(){
            this.$emit('gosj');
        },
        goCn:function(){
            this.$emit('gocn');
        },
	},
	template:'<el-dialog\
        :visible.sync="isShowPop"\
        :close-on-click-modal="false"\
        width="640px"\
        center>\
        <div class="tc pt10 f16 lh25"> <i class="iconfont icon-tishieps c_blue f20"></i> <span class="fb">您有以下数据未维护，会影响预测预警准确度，请及时维护！</span></div>\
        <div class="gundongLine" style="max-height: 400px;overflow-y: scroll;">\
        <div class="mt20 w550 margin lh30 pb10 tl" v-for="item in dataNot">\
            <div class="tl f15 fb">{{item.companyName}}</div>\
                <div class="of">\
                    <span class="c_blue fl">计划产量：</span>\
                    <div class="fl" style="width: calc(100% - 150px);">\
                        {{item.planOutPutStr}}\
                    </div>\
                    <button class="small_main_btn fl f12 ml10" @click="goJh">去维护</button>\
                </div>\
                <div class="of">\
                    <span class="c_blue fl">实际产量：</span>\
                    <div class="fl" style="width: calc(100% - 150px);">\
                        {{item.actualOutPutStr}}\
                    </div>\
                    <button class="small_main_btn fl f12 ml10" @click="goSj">去维护</button>\
                </div>\
                <div class="of">\
                    <span class="c_blue fl">厂内库存：</span>\
                    <div class="fl" style="width: calc(100% - 150px);">\
                        {{item.actualOutPutStr}}\
                    </div>\
                    <button class="small_main_btn fl f12 ml10" @click="goCn">去维护</button>\
                </div>\
            </div>\
        </div>\
        <span slot="footer" class="dialog-footer"><el-button type="primary" @click="isShowPop = false">确 定</el-button>\
                <el-button @click="isShowPop = false">取 消</el-button>\
        </span>\
    </el-dialog>'
});
