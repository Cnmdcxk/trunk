/**
 * 
 * 筛选filter调用
 * eg:
 * <frame-filter :do-search="search"
                :data="filters"
                :conf="filtersConfig"></frame-filter>
 *   search是vue对象里调用的搜索方法，参数是筛选里已选的param
 *   filters是data数据
 *   filtersConfig是每个显示的过滤条件
 *   filtersConfig searchKey指定模糊搜索
 *   filtersConfig searchLeft指定页面标题
 *   filtersConfig selects条件有如下类型：
 *     1、single-change：文本单选（默认）
 *     2、more-change：文本多选
 *     3、delivery-month：交货月
 *     4、spec：规格
 *     5、date-range：时间段查询
 *     6、radio：单选框组件
 *     7、checkbox：多选框组件
 *     8、number-range：数值段查询
 *  eg:
 *  	filters: {
            providerName:[{value:'章三', key: 1}, {value:'里斯', key: 2}, {value:'王伍', key: 3}],
            statusSingle: [{value: '已启用', key: 1, selected: true}, {value: '未启用', key: 10}],
            statusMult: [{value: '未生效', key: 10}, {value: '待付款', key: 20}, {value: '已付款', key: 30}, {value: '已发货', key: 40}, {value: '已完成', key: 50}, {value: '已撤销', key: 90}],
            providerNameLine:[{value:'章三', key: 1}, {value:'里斯', key: 2}, {value:'王伍', key: 3}],
            statusSingleLine: [{value: '已启用', key: 1, selected: true}, {value: '未启用', key: 10}],
            statusMultLine: [{value: '未生效', key: 10}, {value: '待付款', key: 20}, {value: '已付款', key: 30}, {value: '已发货', key: 40}, {value: '已完成', key: 50}, {value: '已撤销', key: 90}],
        },
        filtersConfig: {
            searchKey: {placeholder: '销售订单号/供应商订单号/意向客户', value: '这是一个搜索'},
            tabs: [
                {text: '订单维度', href: '/buy/order-manager', current: true},
                {text: '行项目维度', href: '/buy/order-line-manager', current: false},
                {text: '捆包维度', href: '/buy/order-detail-manager', current: false}
            ],
            selects: [
                {key: 'providerName', type: 'more-change', name: '销售方'},
                {key: 'spec', type: 'spec', name: '规格'},
                {key: 'deliveryMonth', type: 'delivery-month', name: '交货月', value: '2020-01'},
                {key: 'createTime', type: 'date-range', name: '创建时间', value: '2020-01-01,2020-01-15'},
                {key: 'statusSingle', type: 'radio', name: '状态单选'},
                {key: 'statusMult', type: 'checkbox', name: '状态多选'},
                {key: 'weight', type: 'number-range', name: '重量', value: '1,20'},
            ],
            lines: [
                {key: 'providerNameLine', type: 'more-change', name: '销售方'},
                {key: 'specLine', type: 'spec', name: '规格'},
                {key: 'deliveryMonthLine', type: 'delivery-month', name: '交货月', value: '2020-01'},
                {key: 'createTimeLine', type: 'date-range', name: '创建时间', value: '2020-01-01,2020-01-15'},
                {key: 'statusSingleLine', type: 'radio', name: '状态单选'},
                {key: 'statusMultLine', type: 'checkbox', name: '状态多选'},
                {key: 'weightLine', type: 'number-range', name: '重量', value: '1,20'},
            ]
        }
 */
/**
 * 全局工具
 * @type {{map: Vue.tools.map, filter: Vue.tools.filter, cdn: (*|string)}}
 */
Vue.tools = {
    map: function (arr, fn) {
        if (Array.isArray(arr)) {
            var _d = []
            for (var i=0; i < arr.length; i++) {
                _d.push(fn(arr[i], i))
            }
            return _d;
        }

        var m = {};
        for (var k in arr) {
            var _m = fn(arr[k], k);
            if (_m)
                m[_m[0]] = _m[1];
        }
        return m;
    },
    filter: function (arr, fn) {
        if (Array.isArray(arr)) {
            var _d = []
            for (var i=0; i < arr.length; i++) {
                if (fn(arr[i], i)) {
                    _d.push(arr[i])
                }
            }
            return _d;
        }
        var m = {};
        for (var k in arr) {
            if (fn(arr[k], k)) {
                m[k] = arr[k];
            }
        }
        return m;
    },
    cdn: window.cdn || '..'
}

/**
 * 实例化对象
 */
var filterEventVue = new Vue({
    data: {
        filterData: {}
    },
    methods: {
        selected: function(v, code){
            if (v.selected) {
                return true;
            }

            var _f = this.filterData[code];

            if (_f) {
                if (Array.isArray(_f) && _f.includes(v.key)) {
                    return true;
                }

                if (_f == v.key) {
                    return true;
                }
            }

            return false;
        },
        addFilter: function(f){
            this.filterData = f;
        }
    }
});

/**
 * 定义通用属性
 */
var comMixin = {
    props: ['data', 'clear', 'conf', 'isLine'],
    data: function () {
        var _data = {
            key: '',
            code: '',
            name: '',
            title: '',
            isUp: false,
            cdn: window.cdn || '..',
            list: [],
            value: this.data.defaultValue ? this.data.defaultValue : '',
            isLine: this.isLine
        };
        if (this.conf) {
            var _key = this.conf.key;
            var _title;
            var _checkedList = [];
            var _checkedValList = [];
            var list = Vue.tools.map(this.data[_key], function (v) {
                if (v.selected) {
                    _title = v.name;
                    _checkedList.push(v.key);
                    _checkedValList.push(v.value);
                }
                return {
                    code: v.key,
                    name: v.value,
                    selected: filterEventVue.selected(v, _key)
                }
            });
            _data = {
                key: this.conf.key,
                code: this.conf.code,
                name: this.conf.name,
                title: _title,  // || this.conf.name
                isUp: false,
                list: list,
                value: this.conf.defaultValue ? this.conf.defaultValue : '',
                isLine: this.isLine
            }

            var _componentType = this.conf.componentType;
            if(_checkedList.length > 0){
                if (_componentType == 'single') {
                    this.$emit('add-param', _data.key, _checkedList[0], _data.name + '：' + _checkedValList[0]);
                }else if(_componentType == 'more'){
                    this.$emit('add-param', _data.key, _checkedList, _data.name + '：' + _checkedValList.join(","));
                }
            }else if(typeof(_data.value) !== 'undefined' && _data.value != ''){
                if(_componentType == 'minmax'){
                    var _valueList = _data.value.split(',').length < 2 ? [_data.value, ''] : _data.value.split(',');
                    this.$emit('add-param', _data.key, {min: _valueList[0], max: _valueList[1]}, _data.name + '：' + _valueList[0] + "~" + _valueList[1]);
                }else if (_componentType == 'single') {
                    this.$emit('add-param', _data.key, _data.value, _data.name + '：' + _data.value);
                }
            }
        }
        return _data;
    },
    watch: {
        clear: function () {
            Vue.tools.map(this.list, function (v) {v.selected=false});
            this.title = this.name;
            this.value = '';
        },
        data: {
            handler: function (n) {
                var key = this.conf.key;
                var _title;
                this.list = Vue.tools.map(n[this.conf.key], function (v) {
                    if (v.selected) {
                        _title = v.name;
                    }
                    return {
                        code: v.key,
                        name: v.value,
                        selected: filterEventVue.selected(v, key)
                    }
                });
                if (_title) {
                    this.title = _title;
                }
            },
            deep: true
        }
    },
    methods: {
        reset: function () {
            Vue.tools.map(this.list, function (v) {v.selected=false});
            this.title = this.name;
            this.$forceUpdate();
            this.$emit('remove-param', this.key);
        },
        yes: function () {
            var data = [];
            var _name = [];

            Vue.tools.map(this.list, function (v) {
                if (v.selected) {
                    data.push(v.code);
                    _name.push(v.name)
                }
            });

            if (data.length > 0) {
                this.title = this.name + "：" + _name.join(",");
                this.$emit('add-param', this.key, data, this.title);
            }else{
                this.$emit('remove-param', this.key);
                this.title = this.name;
            }
        }
    }
};

Vue.component('frame-more-content', {
    props: ['t', 's', 'c', 'n', 'isYesBtn', 'k', 'isLine'],
    data: function(){
        return {
            key: this.k
        }
    },
    template: 
        '<div class="filter_link" v-if="isLine">'+
        '<slot style="display:inline;float:left;"></slot>'+
        '<div class="fr" v-if="isYesBtn">'+
            '<button class="small_gray_btn mr10" @click="$emit(\'reset\')">重置</button>'+
            '<button class="small_main_btn" @click="$emit(\'yes\'); $emit(\'update:s\',false);">确定</button>'+
        '</div>'+
        '</div>'+
    	'<li :class="{curr:s}" v-else>'+
    		'<a class="select_a" @click="show()">{{n}}<i class=" iconfont" :class="[(s?\'icon-up\':\'icon-down\')]"></i></a>'+
	    	'<div class="select_con">'+
	    		'<slot></slot>'+
		    	'<button class="delete_btn pa" @click="$emit(\'update:s\',false);"><i class="iconfont icon-cuo f20"></i></button>'+
		        '<div class="function_btn" v-if="isYesBtn">'+
		            '<button class="small_gray_btn mr10" @click="$emit(\'reset\')">重置</button>'+
		            '<button class="small_main_btn" @click="$emit(\'yes\'); $emit(\'update:s\',false);">确定</button>'+
		        '</div>'+
	    	'</div>'+
    	'</li>'
        ,
    created: function(){
        var self = this;
        filterEventVue.$on('mouseenter_show', function(key){
            if (self.key != key) {
                self.$emit('update:s', false);
            }else {
                self.$emit('update:s', true);
            }
        });
        
        filterEventVue.$on('remove_check', function(key){
            if (self.key == key) {
                self.$emit('reset');
            }
        });
    },    
    methods: {
        show: function(){
        	filterEventVue.$emit('mouseenter_show', this.key);
        }
    }
});

Vue.component('frame-filter', {
    props: {
        /**
         * 查询回调函数
         * (params) => {}
         */
        doSearch: {
            type: Function,
            default: function (param) {}
        },

        /**
         * 更新反查数据的函数
         */
        data: {
            type: Object,
            default: {}
        },

        /**
         * 指定查询条件和初始值
         */
        conf: {
            type: Object,
            default: {}
        },

        /**
         * 如果此设置有值，则任务配置数据读取远程数据
         */
        confRel: {
            type: String
        },

        /**
         * 页面title
         */
        pageTitle: {
            type: String,
            default: ''
        }
    },

    // 模版
    template: 
    	'<div class="search">\
    		<ul v-if="tabs && tabs.length > 0" class="txt_tab mt5"><li v-for="t in tabs" :class="{curr: t.current}"><p><a :href="t.href">{{ t.text }}</a></p></li></ul>\
		    <slot name="tab"></slot>\
		    <table class="filter_list">\
		        <tbody>\
			    	<tr class="">\
				        <td>已选条件</td>\
				        <td>\
				            <ul class="filter-list-li" v-if="checkList && JSON.stringify(checkList) != \'{}\'">\
				                <li :title="val" v-for="(val,key) in checkList"><span>{{val}}</span> <i @click="checkRomove(key)" class="iconfont icon-delete_x "></i></li>\
    							<button class="blue_txt_btn" v-if="checkList && JSON.stringify(checkList) != \'{}\'" @click="clearParam">清除</button>\
                            </ul>\
                            <frame-search @add-param="addParam" @remove-param="removeParam" @clear-param="clearParam" :clear="clearVer" :pagetitle="searchLeft" :data="searchRight"></frame-search>\
				        </td>\
				    </tr>\
                    <tr  v-if="filterBox" v-for="(b, i) in filterBox">\
                        <td>{{b.name}}</td>\
                        <td>\
                            <component @add-param="addParam"\
                                @remove-param="removeParam"\
                                @add-params="addParams"\
                                :conf="b"\
                                :data="data"\
                                :is="b.componentName"\
                                :clear="clearVer"\
                                :isLine="true"\
                                :key="i">\
                            </component>\
                        </td>\
                    </tr>\
			    	<tr>\
				        <td>搜索筛选</td>\
				        <td>\
				            <ul class="select_down_h" v-if="selectBox">\
						    	<component @add-param="addParam"\
		    						@remove-param="removeParam"\
		    						@add-params="addParams"\
		    						:conf="b"\
						            :data="data"\
						            :is="b.componentName"\
						            :clear="clearVer"\
						            v-for="(b, i) in selectBox" \
                                    :isLine="false"\
						            :key="i">\
						        </component>\
					        </ul>\
					    </td>\
					</tr>\
					<tr v-if="$slots.countResult">\
					    <td>统计结果</td>\
					    <td><slot name="countResult"></slot></td>\
					</tr>\
		    	</tbody>\
		    </table>\
		</div>',
    data: function () {

        var result = {
            searchRight: { is: false, isClearBtn: true, placeholder: '请输入查询条件', value: ''},
            searchLeft: '',
            selectBox: [],
            filters: {},
            checkList: {},
            filterBox: [],
            clearVer: 0,
            tabs: []
        };

        var self = this;

        if (this.conf) {
            var _conf = this.conf;

            if (_conf.searchKey) {
                result.searchRight = {
                    is: true,
                    isClearBtn: true,
                    placeholder: _conf.searchKey.placeholder,
                    value: _conf.searchKey.value
                }
            }

            if (this.pageTitle) {
                result.searchLeft = this.pageTitle;
            }

            if (_conf.tabs){
                result.tabs = _conf.tabs;
            }

            if (_conf.selects) {

                result.selectBox = Vue.tools.map(_conf.selects, function (_b) {
                    return self.showComponent(_b);
                });

                result.selectBox = Vue.tools.filter(result.selectBox, function (v) { return v != null; });
            }

            if (_conf.lines){
                result.filterBox = Vue.tools.map(_conf.lines, function (_b) {
                    return self.showComponent(_b);
                });

                result.filterBox = Vue.tools.filter(result.filterBox, function (v) { return v != null; });
            }
        }

        return result;
    },
    
    methods: {
        showComponent: function(_b){
            // 文本单选组件配置
            if (_b.type === 'single-change' || _b.type === undefined) {
                return {
                    componentName: 'frame-single-change',
                    name: _b.name,
                    key: _b.key,
                    defaultValue: _b.value,
                    cdn: Vue.tools.cdn,
                    componentType: 'single'
                }
            }
            
            // 文本多选配置
            if (_b.type === 'more-change') {
                return {
                    componentName: 'frame-more-change',
                    name: _b.name,
                    key: _b.key,
                    defaultValue: _b.value,
                    cdn: Vue.tools.cdn,
                    componentType: 'more'
                }
            }

            // 交货月
            if (_b.type === 'delivery-month') {
                return {
                    componentName: 'frame-delivery-month',
                    name: _b.name,
                    key: _b.key,
                    cdn: Vue.tools.cdn,
                    defaultValue: _b.value,
                    componentType: 'single'
                }
            }



            // 规格
            if (_b.type === 'spec') {
                return {
                    componentName: 'frame-spec',
                    name: _b.name,
                    key: _b.key,
                    cdn: Vue.tools.cdn,
                    defaultValue: _b.value,
                    componentType: 'spec'
                }
            }
            
            // 时间段查询
            if (_b.type === 'date-range') {
                return {
                    componentName: 'frame-date-range',
                    name: _b.name,
                    key: _b.key,
                    cdn: Vue.tools.cdn,
                    defaultValue: _b.value,
                    componentType: 'minmax'
                }
            }
            
            // 时间段（按月份）查询
            if (_b.type === 'date-month-range') {
                return {
                    componentName: 'frame-date-month-range',
                    name: _b.name,
                    key: _b.key,
                    cdn: Vue.tools.cdn,
                    defaultValue: _b.value,
                    componentType: 'minmax'
                }
            }

            // 单选框组件配置
            if (_b.type === 'radio') {
                return {
                    componentName: 'frame-radio',
                    name: _b.name,
                    key: _b.key,
                    defaultValue: _b.value,
                    cdn: Vue.tools.cdn,
                    componentType: 'single'
                }
            }
            
            // 多选框配置
            if (_b.type === 'checkbox') {
                return {
                    componentName: 'frame-checkbox',
                    name: _b.name,
                    key: _b.key,
                    defaultValue: _b.value,
                    cdn: Vue.tools.cdn,
                    componentType: 'more'
                }
            }
            
            // 数值段查询
            if (_b.type === 'number-range') {
                return {
                    componentName: 'frame-number-range',
                    name: _b.name,
                    key: _b.key,
                    defaultValue: _b.value,
                    cdn: Vue.tools.cdn,
                    componentType: 'minmax'
                }
            }
            return null;
        },

        addParam: function (k, value, title, notSearch) {
            this.filters[k] = value;
            this.checkList[k] = title;
            this.$forceUpdate();
            filterEventVue.addFilter(this.filters);

            if (!notSearch) {
                this.doSearch(this.__getParam());
            }

        },

        addParams: function (vs, title, notSearch) {
            var _f = this.filters;
            Vue.tools.map(vs, function (v, k) {
                _f[k] = v;
            });
            
            var _c = this.checkList;
            Vue.tools.map(title, function (v, k) {
            	_c[k] = v;
            });
            this.$forceUpdate();
            filterEventVue.addFilter(this.filters);
            if (!notSearch) {
                this.doSearch(this.__getParam());
            }
        },

        removeParam: function (k) {
            if (Array.isArray(k)) {
                for (var i=0;i<k.length;i++) {
                    delete this.filters[k[i]];
                    delete this.checkList[k[i]];
                }
            }else{
                delete this.filters[k];
                delete this.checkList[k];
            }

            this.$forceUpdate();
            filterEventVue.addFilter(this.filters);
            this.doSearch(this.__getParam());
        },

        clearParam: function () {
            this.filters = {};
            this.checkList = {};
            this.clearVer += 1;
            filterEventVue.addFilter(this.filters);
            this.doSearch(this.__getParam());
        },

        /**
         * 获取参数
         */
        __getParam: function () {
            return Vue.tools.filter(this.filters, function (v) {return v});
        },
        
        checkRomove: function(key){
        	filterEventVue.$emit('remove_check', key);
        }

    },
    // 内置组件
    components: {

        // 文本单选
        'frame-single-change': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="false" :k="key" :isLine="isLine">\
	            	<div class="select_list">\
		                <a :key="d.code" @click="change(d)" :class="[d.selected ? \'curr\': \'\']" v-for="d in list">{{d.name}}</a>\
		            </div>\
				</frame-more-content>',
            methods: {
                change: function (item) {
                    if (!item.selected) {
                        Vue.tools.map(this.list, function (v) {
                            if (item.code === v.code) {
                                v.selected=true;
                            }else{
                                v.selected=false;
                            }
                        });
                        this.title = this.name + "：" + item.name;
                        this.$emit('add-param', this.key, item.code, this.title);
                    }
                }
            }
        },

        // 文本多选
        'frame-more-change': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="true" :k="key" :isLine="isLine">\
	            	<div class="select_list">\
		                <a :key="d.code" @click="change(d)" :class="[d.selected ? \'curr\': \'\']" v-for="d in list">{{d.name}}</a>\
		            </div>\
    			</frame-more-content>',
            methods: {
                change: function (item) {
                    item.selected = !item.selected;
                }
            }
        },

        // 搜索框
        'frame-search': {
            mixins:[comMixin],
            props:['pagetitle'],
            template: 
            '<div class="fr mt5" v-if=" data.is ">\
		        <div class="search-input">\
		            <input @keyup.enter="search" type="text" v-model="value" :placeholder="data.placeholder">\
		            <i @click="search" class="iconfont icon-zoom_x"></i>\
		        </div>\
		    </div>',
		    data: function(){
		    	return {value: this.data.value}
		    },
		    created: function(){
		    	var self = this;
		    	filterEventVue.$on('remove_check', function(key){
		            if (key == 'searchKey') {
		            	self.$emit('remove-param', 'searchKey');
		            }
		        });
		    	
		    	if(this.value.trim().length > 0){
		    		this.search();
		    	}
		    },
            methods: {
                search: function () {
                    var inputValue = this.value.trim();
                    if (inputValue.length > 0)
                        this.$emit('add-param', 'searchKey', inputValue, inputValue);
                    else
                        this.$emit('remove-param', 'searchKey');
                }
            }
        },

        // 规格
        'frame-spec': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="true" :k="key" :isLine="isLine">\
	            	<div class="select_list">\
		            	<div :class="{mb20: !isLine}">\
			                <span class="w270">\
			                    <i>厚度/直径/高度</i>\
			                    <input type="text" class="w70" v-model="thick.min"> - <input type="text" class="w70" v-model="thick.max">\
			                </span>\
			                <span class="w240">\
			                    <i class="w60 tr">宽度/壁厚</i> \
			                    <input type="text" class="w70" v-model="width.min"> - <input type="text" class="w70" v-model="width.max">\
			                </span>\
			                <span class="w250">\
			                    <i>长度</i> \
			                    <input type="text" class="w70" v-model="length.min"> - <input type="text" class="w70" v-model="length.max">\
			                </span>\
			            </div> \
		            </div>\
				</frame-more-content>',
            data: function () {
                return {
                    thick: {min: '', max: ''},
                    width: {min: '', max: ''},
                    length: {min: '', max: ''}
                }
            },
            watch: {
                clear: function () {
                    this.thick = {min: '', max: ''};
                    this.width = {min: '', max: ''};
                    this.length = {min: '', max: ''};
                    this.title = this.name;
                }
            },
            methods: {
                reset: function () {
                    this.thick = {min: '', max: ''};
                    this.width = {min: '', max: ''};
                    this.length = {min: '', max: ''};
                    this.title = this.name;
                    this.$emit('remove-param', ['thick', 'width', 'length']);
                },
                yes: function () {

                    var g = function (v) {
                        if (v !== '' && $.isNumeric(v)) {
                            return v;
                        }
                        return undefined;
                    }

                    var f = function (v) {
                        return v ? v : '';
                    }

                    var data = {};
                    var name = {};
                    if (g(this.thick.min) || g(this.thick.max)) {
                        data.thick = {
                            min: g(this.thick.min),
                            max: g(this.thick.max)
                        };
                        name.thick = '厚度/直径/高度：' + (f(data.thick.min) + '-' + f(data.thick.max));
                    }else {
                        data.thick = undefined;
                    }

                    if (g(this.width.min) || g(this.width.max)) {
                        data.width = {
                            min: g(this.width.min),
                            max: g(this.width.max)
                        };
                        name.width = '宽度/壁厚：' + (f(data.width.min) + '-' + f(data.width.max));
                    }else {
                        data.width = undefined;
                    }


                    if (g(this.length.min) || g(this.length.max)) {
                        data.length = {
                            min: g(this.length.min),
                            max: g(this.length.max)
                        };
                        name.length = '长度：' + (f(data.length.min) + '-' + f(data.length.max));
                    }else {
                        data.length = undefined;
                    }

                    if (name.length === undefined && name.thick === undefined && name.width === undefined) {
                        this.title = this.name;
                    }else{
                        this.title = name.thick + '...';
                    }
                    this.$emit('add-params', data, name);
                }
            }
        },
        
        // 时间段
        'frame-date-range': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="true" :k="key" :isLine="isLine">\
	            	<div class="updateTime" :class="{mb20: !isLine}">\
			            <span class="w400">\
			                <i class="w90" v-if="!isLine">{{name}}</i>\
			                <span class="w100 disib">\
			                    <el-date-picker\
			                        v-model="datetime.min"\
			                        align="right"\
            						value-format="yyyy-MM-dd"\
			                        type="date">\
			                    </el-date-picker>\
			                </span>\
			                    -  \
			                    <span class="w100 disib">\
			                    <el-date-picker\
			                        v-model="datetime.max"\
			                        align="right"\
            						value-format="yyyy-MM-dd"\
			                        type="date">\
			                    </el-date-picker>\
			                </span>\
			            </span>\
			        </div>\
				</frame-more-content>',
            data: function () {
                return {
                    datetime: {min: (this.conf.defaultValue != undefined && this.conf.defaultValue != '') ? (this.conf.defaultValue.split(',').length > 1 ? this.conf.defaultValue.split(',')[0] : this.conf.defaultValue) : '', 
                        max: (this.conf.defaultValue != undefined && this.conf.defaultValue != '') ? (this.conf.defaultValue.split(',').length > 1 ? this.conf.defaultValue.split(',')[1] : '') : ''}
                }
            },
            watch: {
                clear: function () {
                    this.datetime = {min: '', max: ''};
                    this.title = this.name;
                }
            },
            methods: {
                reset: function () {
                	this.datetime = {min: '', max: ''};
                    this.title = this.name;
                    this.$emit('remove-param', this.key);
                },
                yes: function () {
                    this.title = this.name + "：" + this.datetime.min + "~" + this.datetime.max;
                    this.$emit('add-param', this.key, this.datetime, this.title);
                }
            }
        },
        
        // 时间段(按月份)
        'frame-date-month-range': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="true" :k="key" :isLine="isLine">\
	            	<div class="updateTime" :class="{mb20: !isLine}">\
			            <span class="w400">\
			                <i class="w90" v-if="!isLine">{{name}}</i>\
			                <span class="w100 disib">\
			                    <el-date-picker\
			                        v-model="datetime.min"\
            						align="right"\
            						type="month"\
            						value-format="yyyy-MM"\
            						placeholder="请选择月">\
			                    </el-date-picker>\
			                </span>\
			                    -  \
			                    <span class="w100 disib">\
			                    <el-date-picker\
			                        v-model="datetime.max"\
			                        align="right"\
					            	type="month"\
            						value-format="yyyy-MM"\
									placeholder="请选择月">\
			                    </el-date-picker>\
			                </span>\
			            </span>\
			        </div>\
				</frame-more-content>',
            data: function () {
                return {
                    datetime: {min: '', max: ''}
                }
            },
            watch: {
                clear: function () {
                    this.datetime = {min: '', max: ''};
                    this.title = this.name;
                }
            },
            methods: {
                reset: function () {
                	this.datetime = {min: '', max: ''};
                    this.title = this.name;
                    this.$emit('remove-param', this.key);
                },
                yes: function () {
                    this.title = this.name + "：" + this.datetime.min + "~" + this.datetime.max;
                    this.$emit('add-param', this.key, this.datetime, this.title);
                }
            }
        },

        // 交货月
        'frame-delivery-month': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="true" :k="key" :isLine="isLine">\
	            	<div class="select_Mon" :class="{mb20: !isLine}">\
		                <i class="w60 " v-if="!isLine">{{name}}</i>\
		                <select class="w80 mr15">\
		                    <option v-for="(y,i) in years" :key="i" @click="changeYear(y)">{{y.year}}</option>\
		                </select>\
		                <a v-for="(m,i) in months" @click="changeMonth(m)" :key="i" :class="[m.selected?\'curr\':\'\']"><span>{{m.month}}月</span></a>\
		            </div>\
				</frame-more-content>',
            data: function () {

                var years = [];
                var curYear = new Date().getFullYear();
                var _defaultYear = (this.conf.defaultValue != undefined && this.conf.defaultValue != '') ? parseInt(this.conf.defaultValue.split('-').length > 1 ? this.conf.defaultValue.split('-')[0] : this.conf.defaultValue) : '';
                var _defaultMonth = (this.conf.defaultValue != undefined && this.conf.defaultValue != '') ? parseInt(this.conf.defaultValue.split('-').length > 1 ? this.conf.defaultValue.split('-')[1] : 0) : '';
                for (var y=0; y < 4; y++) {
                    years.push({
                        year: curYear + y,
                        selected: (curYear + y) == _defaultYear ? true : false,
                    });
                }

                var months = [];
                for (var m=1; m < 13; m++) {
                    months.push({
                        month: m,
                        selected: m == _defaultMonth ? true : false
                    });
                }

                return {
                    years: years,
                    months: months
                }
            },
            watch: {
                clear: function () {
                    Vue.tools.map(this.years, function (v) {v.selected=false});
                    Vue.tools.map(this.months, function (v) {v.selected=false});
                    this.title = this.name;
                }
            },
            methods: {
                reset: function () {
                    Vue.tools.map(this.years, function (v) {v.selected=false});
                    Vue.tools.map(this.months, function (v) {v.selected=false});
                    this.title = this.name;
                    this.$emit('remove-param', this.key);
                },
                yes: function () {

                    var year = undefined;
                    Vue.tools.map(this.years, function (v) {
                        if (v.selected) {
                            year = v.year
                        }
                    });

                    if (year !==undefined) {
                        var data = [];
                        Vue.tools.map(this.months, function (v) {
                            if (v.selected) {
                                data.push(v.month);
                            }
                        });

                        if (data.length === 0) {
                            this.title = year + '年';
                            for (var m=1;m<13;m++) {
                                data.push(year + '-' + (m < 10 ? ('0'+ m) : m));
                            }
                        }else{
                            this.title = year + '年' + data[0] + '月' + (data.length > 1?'...' : '');
                            data = Vue.tools.map(data, function (m) {
                                return year + '-' + (m < 10 ? ('0'+m) : m);
                            });
                        }

                        this.$emit('add-param', this.key, data, this.title);
                    }else{
                        this.$emit('remove-param', this.key);
                        this.title = this.name;
                    }
                },
                changeYear: function (y) {
                    var isNew = false;
                    Vue.tools.map(this.years, function (_y) {
                        if (_y.year === y.year && !_y.selected) {
                            _y.selected = true;
                            isNew = true;
                        }else{
                            _y.selected = false;
                        }
                    });
                    if (isNew) {
                        Vue.tools.map(this.months, function (m) {
                            m.selected = false
                        })
                    }
                },
                changeMonth: function (m) {
                    m.selected = !m.selected;
                    var sy = Vue.tools.filter(this.years, function (_y) {
                        return _y.selected;
                    });
                    if (sy.length === 0) {
                        this.years[0].selected = true;
                    }
                }
            }
        },
        
        // 单选框
        'frame-radio': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="false" :k="key" :isLine="isLine">\
	            	<div :class="{mb10: !isLine}">\
						<span v-if="!isLine">{{name}}</span>\
		            	<el-radio-group v-model="rdo" style="vertical-align: baseline;">\
			                <el-radio v-for="d in list" @change="change(d)" :key="d.code" :label="d.code">\
			                  {{d.name}}\
			                </el-radio>\
		                </el-radio-group>\
			        </div>\
				</frame-more-content>',
			data: function(){
				return {rdo: this.key}
			},
            methods: {
                change: function (item) {
                    if (!item.selected) {
                        Vue.tools.map(this.list, function (v) {
                            if (item.code === v.code) {
                                v.selected=true;
                            }else{
                                v.selected=false;
                            }
                        });
                        this.title = item.name;
                        this.$emit('add-param', this.key, item.code, this.title);
                    }
                }
            }
        },

        // 多选框
        'frame-checkbox': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="true" :k="key" :isLine="isLine">\
	            	<div>\
			            <span v-if="!isLine">{{name}}</span>\
			            <label class="mr30" v-for="d in list" :key="d.code"><input type="checkbox" v-model="d.selected"><i></i>{{d.name}}</label>\
			        </div>\
    			</frame-more-content>',
            methods: {
                change: function (item) {
                    item.selected = !item.selected;
                }
            }
        },
        
        // 数值段
        'frame-number-range': {
            mixins:[comMixin],
            template: 
            	'<frame-more-content @reset="reset" @yes="yes" :s.sync="isUp" :n="name" :t="title" :c="conf.cdn" :isYesBtn="true" :k="key" :isLine="isLine">\
	            	<div>\
		            	<span class="w250">\
			                <i v-if="!isLine">{{name}}</i>\
			                <input type="text" v-model="numbers.min" class="w70"> - <input type="text" v-model="numbers.max" class="w70">\
			            </span>\
			        </div>\
				</frame-more-content>',
            data: function () {
                return {
                    numbers: {min: (this.conf.defaultValue != undefined && this.conf.defaultValue != '') ? (this.conf.defaultValue.split(',').length > 1 ? this.conf.defaultValue.split(',')[0] : this.conf.defaultValue) : '', 
                        max: (this.conf.defaultValue != undefined && this.conf.defaultValue != '') ? (this.conf.defaultValue.split(',').length > 1 ? this.conf.defaultValue.split(',')[1] : '') : ''}
                }
            },
            watch: {
                clear: function () {
                    this.numbers = {min: '', max: ''};
                    this.title = this.name;
                }
            },
            methods: {
                reset: function () {
                	this.numbers = {min: '', max: ''};
                    this.title = this.name;
                    this.$emit('remove-param', this.key);
                },
                yes: function () {
                	var g = function (v) {
                        if (v !== '' && $.isNumeric(v)) {
                            return v;
                        }
                        return '';
                    }

                    var f = function (v) {
                        return v ? v : '';
                    }

                    if (g(this.numbers.min) || g(this.numbers.max)) {
                    	this.numbers = {
                            min: g(this.numbers.min),
                            max: g(this.numbers.max)
                        };
                        this.title = this.name + "：" + this.numbers.min + "-" + this.numbers.max;
                    }else {
                        this.title = '';
                    }
                	
                    this.$emit('add-param', this.key, this.numbers, this.title);
                }
            }
        }
        
    }
});