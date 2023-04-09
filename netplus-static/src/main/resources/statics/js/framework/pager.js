/**
 * 2019-09-18 pager by sq
 *   eg: 
 *   	 <pager v-bind:total=100 v-bind:page-size=10 :search="doSearch"></pager>
 *   doSearch方法在父组件里定义
 *   eg: 
 *       doSearch: function(pageSize, pageNo){
	        console.log(`${pageSize}条，${pageNo}页`);
	     }
 */
Vue.component("pager",{
	data:function(){
		return {
			pageInfo:{
				pageNo: this.pageNo,
	            pageSize: this.pageSize,
	            total: this.total,
	            pageSizes: this.pageSizes
	        }
		}
	},
	props: {
		pageNo: {
			// 当前页
			type: Number,
	      	default: 1
	    }, 
		pageSize: {
			// 每页多少条
			type: Number,
	      	default: 10
	    }, 
		total: {
			// 总条数
			type: Number,
	      	default: 0
	    },
	    pageSizes: {
	    	// 页数
	    	type: Array,
	    	default: [10, 20, 50, 100]
	    },
	    search: {
	    	// 查询方法
	    	type: Function,
	    	default: null
	    }
	},
	methods: {
		sizeChange:function(val){
			if(this.search){
				this.search(val, this.pageInfo.pageNo);
			}
		},
		currentChange:function(val){
			if(this.search){
				this.search(this.pageInfo.pageSize, val);
			}
		}
	},
	template:'<el-pagination\
        class="fr"\
        background\
        @size-change="sizeChange"\
        @current-change="currentChange"\
        :current-page.sync="pageInfo.pageNo"\
        :page-sizes="pageInfo.pageSizes"\
        :page-size="pageInfo.pageSize"\
        layout="sizes, prev, pager, next"\
        :total="pageInfo.total">\
    </el-pagination>'
});
