// Unix timestamp格式化
Vue.filter("unixTimeFormat", function(value, format){
    if (!value || value == '') return '';
    else return new Date(parseInt(value)).dateFormat(format);
});

// 数字格式化
Vue.filter("round", function(value, ln){
    var _value = parseFloat(value);
    return isNaN(_value) ? 0 : _value.toFixed(ln);
});


// 取固定长度，超过的后面追加...
Vue.filter("trim", function(value, len){
    if (typeof (value) === "string"){
        if (!value) return "";
        else if (value.length <= len) return value;
        else if (value.length > len) return value.substr(0, len) + "...";
    }
    else{
        return value;
    }
});


// 格式话统计重量
Vue.filter("formatTotalWeight", function(value){
    var v = typeof (value) === "string" ? parseFloat(value) : value;
    return v >= 10000 ? ((v / 10000).toFixed(2) + '万') : v;
});


Vue.filter("formatText", function(val){
    return val == '' || val == undefined || val == null ? '--': val;
});

// 针对20201230这种数据格式化显示
Vue.filter("formatDate", function(val){
    return val == '' || val == undefined || val == null
        ? ''
        : val.substr(0, 4) + "-" + val.substr(4, 2) + "-" + val.substr(6, 2);
});

// 针对1230这种数据格式化显示
Vue.filter("formatTime", function(val){
    return val == '' || val == undefined || val == null
        ? ''
        : val.substr(0, 2) + ":" + val.substr(2, 2);
});




