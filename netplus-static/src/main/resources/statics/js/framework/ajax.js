/**
 * ajax调用 2019-09-18 by sq
 * 默认loading显示，不需要显示在param加loading: false
 * eg:
 	ajax(url, param,
        function (resp) {
            console.log(resp);
        },
        function (err) {
            console.log(err);
        }
    );
 */
axios.defaults.headers.post['Content-Type'] = 'application/json';
var instance = axios.create();

var loading;
function ajax(url, data, success, error){
    
	var param = $.extend({
        loading:true,
        msg:'请稍候',
        _source: "PC"
    }, data);
	
	if(param.loading){
		loading = Vue.prototype.$loading({
            lock: true,
            text: param.msg,
            background: 'rgba(255, 255, 255, 0.1)'
        });
    }

    if (!param.timeout)
        instance.timeout = 60 * 1000; // 1 min

    instance.post(url, param).then(function(res){
    	if(param.loading){
    		loading.close();
    	}

        if(res.status == 200){
            if (success)
                success(res.data);
            else if (error)
                error(res.message);
        }
        else{
            if (error)
                error(res.message);
        }
    }).catch(function(err){


        console.log("url: ", url, "err: ", err);

    	if(param.loading){
    		loading.close();
    	}

        if (error){
            var errMsg = "";
            if (err.response){
                if(err.response.status == 403){
                    errMsg = "服务器返回403！";
                }
                else if (err.response.status == 404){
                    errMsg = "请求地址不存在: " + url;
                }
                else if (err.response.status == 500){
                    errMsg = err.response.data.message;
                }
            }else{
                errMsg = err;
            }
            error(errMsg, err);
        }
    });
}

function BatchAjax(msg) {

    this.task = [];
    this.taskCode = [];
    this.msg = msg;
    this.errorFn = function(){}
    this.ir = 0;

}

BatchAjax.prototype.error = function (errorFn) {
    this.errorFn = errorFn;
}

BatchAjax.prototype.add = function(url, data, code){
    var param = {};
    if(typeof(data) !== 'undefined' && data != null && data != "" && data != {}){
        for(var key in data){
            param[key]=data[key];
        }
    }

    this.task.push(instance.post(url, param));
    this.ir = this.ir + 1;
    this.taskCode.push(code || ('ajax' + this.ir));
    return this;
}
BatchAjax.prototype.success = function(successFn){

    var self = this;
    loading = Vue.prototype.$loading({
        lock: true,
        text: self.msg,
        background: 'rgba(0, 0, 0, 0.7)'
    });

    axios.all(this.task)
        .then(axios.spread(function(){

            loading.close();
            var hasError = false;
            var errMsg = "";
            var dataMap = {};
            for (var i = 0; i < arguments.length; i++) {
                var res = arguments[i];

                if(res.status != 200){
                    hasError = true;
                    errMsg = res.message;
                    break;
                }
                dataMap[self.taskCode[i]] = res.data;
            }

            if (hasError) {
                self.errorFn(errMsg);
            } else {
                successFn.call(this, dataMap);
            }
        }))
        .catch(function (err){
            loading.close();
            var errMsg = "服务器错误";

            if (err.response){
                if(err.response.status == 403){
                    errMsg = "服务器返回403！";
                }
                else if (err.response.status == 404){
                    errMsg = "请求地址不存在: " + err.config.url;
                }
                else if (err.response.status == 500){
                    errMsg = err.response.data.message;
                }
            }
            self.errorFn.call(this, errMsg,err);
        });

    return self;
}

/**
 * 全局注入 ajax post 请求，  vue 实例对象中this.$post 能直接访问全局 ajax
 * 此为了方便大家调用ajax 。 以后考虑清理掉全局 ajax 这个函数使用this.$post
 */
Vue.use({
    install: function(_vue){
        _vue.prototype.$post = window.ajax;
    }
})

