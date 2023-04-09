/* 日期格式化函数 */
Date.prototype.dateFormat = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

// eg: var repString = "test{0} test{1}"; repString.format("aa", "bb");
String.prototype.format = function() {
    var e = this, f = arguments.length;
    if (f > 0) {
        for ( var d = 0; d < f; d++) {
            e = e.replace(new RegExp("\\{" + d + "\\}", "g"), arguments[d])
        }
    }
    return e;
};

// 方法冲突了 注释掉
//function isMobile(mobile) {
//    return mobile != null && mobile.length > 0 && /^(13[0-9]|153|156|15[7-9]|18[0-9]|17[0-9])[0-9]{8}$/.test(mobile);
//}

function checkPasswordStrong(password) {
    var modes = 0;
    if (password.length < 6 || password.length > 16) {
        return modes;
    }
    //正则表达式验证符合要求的
    if (/\d/.test(password)) modes++; //数字
    if (/[a-z]/.test(password)) modes++; //小写
    if (/[A-Z]/.test(password)) modes++; //大写
    if (/\W/.test(password)) modes++; //特殊字符
    //逻辑处理
    switch (modes) {
        case 1:
            return 1;
            break;
        case 2:
        case 3:
            return 2;
            break;
        case 4:
            return 3;
            break;
    }
}

/**
 * 表单跳转
 * @param url
 * @param data
 * @param target
 * @param method
 */
function formForward(url,data,target,method){
	var form = $("#submitForm");
	if(form && form.length > 0 ) form.remove();
	if(typeof(target) === 'undefined' || target == ''){
		target = "_blank";
	}
	if(typeof(method) === 'undefined' || method == ''){
		method = "post";
	}
	form = "<form id='submitForm' target='"+ target +"' action='"+url+"' enctype='multipart/form-data' method='"+ method +"'>";
	for(i in data){
		form += "<input type='hidden'  name='"+i+"' value='"+data[i]+"'/>";
	}
	form+= "</form>";
	$("body").append(form);
	$("#submitForm").submit();
}


Vue.filter("formatText", function(v, num){
    if (v == null || v == undefined) {
        return "";
    }

    if (num == null || num == undefined) {
        num = 5;
    }

    return v.substr(0,num)+"..."
})



// 自定义字段控件
Vue.component("column-setting", {
    template: '\
        <div class="fr">\
            <button class="gray_txt_btn mr10">\
                <i class="iconfont icon-shezhi f24" @click="showSettingDialog();"></i>\
            </button>\
            <el-dialog title="自定义筛选条件" :visible.sync="dialogShown" :close-on-click-modal="false" width="678px" center>\
                <div class="lh30 bd_ccc tc f13 lh25" style="width: 626px;">\
                    <div class="fl" style="width: 495px;height:310px;border-right: 1px solid #ccc;">\
                            <p class="bg_f5f5f5 lh30 h30 tl">\
                                <em class="ml10">全部筛选条件</em>\
                            </p>\
                        <div class="m10 tl lh25">\
                            <label class="w110 disib txt_of" v-for="s in sysColumns" :title="s.columnComments">\
                                <input type="checkbox" v-model="s.checked" @change="updateColumn(s);"><i></i>{{ s.columnComments }}\
                            </label>\
                        </div>\
                    </div>\
                    <div class="fl tl" style="width:130px;min-height:150px;">\
                        <p class="bg_f5f5f5 lh30 h30">\
                            <em class="ml20">页面实际筛选</em>\
                        </p>\
                        <dl id="sortable" class="sort_cur ui-sortable pt10" style="line-height:32px;max-height: 270px;overflow-y: auto">\
                            <dt v-for="u in appColumns" style="cursor: move;" :data-id="u.sysColumnId"><span class="fl txt_of w76 disib" :title="u.columnComments">{{ u.columnComments }}</span> \
                                <a class="fr mr10 iconfont icon-delete_x  f12" @click="removeColumn(u);"></a>\
                            </dt>\
                        </dl>\
                    </div>\
                    <div class="clear"></div>\
                </div>\
                <div class="c_blue">\
                    <label class="w110 disib pt5 f12"><input type="checkbox" @change="checkAll();" v-model="isCheckAll"><i></i>全选</label>\
                </div>\
                <span slot="footer" class="dialog-footer">\
                    <el-button @click="saveColumn();" type="primary">保 &nbsp; &nbsp;&nbsp;&nbsp;存</el-button>\
                    <el-button @click="setDefault();">恢复默认</el-button>\
                </span>\
            </el-dialog>\
        </div>\
    ',
    props: {
        table_name: ''
    },
    data: function(){
        return {
            dialogShown: false,
            isCheckAll: false,
            sysColumns: [],
            appColumns: [],
            index: 0
        };
    },
    mounted: function(){
        this.init();
    },
    methods: {

        init: function () {
            var self = this;
            ajax(
                '/api/v2/preference/queryAppColumn',
                {
                    tableName: this.table_name,
                    type: 10
                },
                function (resp) {
                    var appColumns = [];
                    var defaultColumns = [];
                    for (var i = 0; i < resp.length; i++) {
                        var item = resp[i];
                        if (item.appColumnId)
                            appColumns.push(item);
                        if (item.isDefault == 10)
                            defaultColumns.push(item);
                    }
                    
                    // 按appSort排序
                    var sort_fn = function (a, b) {
                        if (a.appSort == null || b.appSort == null) return 0;
                        else return a.appSort - b.appSort;
                    };
                    appColumns.sort(sort_fn);

                    self.$emit('column_init', appColumns.length == 0 ? defaultColumns : appColumns);

                    self.setAppColumns();

                },
                function (err) {
                    self.$message.error(err);
                }
            );
        },

        showSettingDialog: function () {

            var self = this;
            
            this.isCheckAll = false;
            ajax(
                '/api/v2/preference/queryAppColumn',
                {
                    tableName: this.table_name,
                    type: 10
                },
                function (resp) {
                    for (var i = 0; i < resp.length; i++) {
                        resp[i].checked = resp[i].appColumnId != null;
                    }
                    self.sysColumns = resp;
                    self.syncAppColumns();

                    self.dialogShown = true;

                    self.setAppColumns();
                },
                function (err) {
                    self.$message.error(err);
                }
            );
        },

        syncAppColumns: function () {
            var list = [];
            for (var i = 0; i < this.sysColumns.length; i++) {
                if (this.sysColumns[i].checked) {
                    list.push(this.sysColumns[i]);
                }
            }

            // 按appSort排序
            var sort_fn = function (a, b) {
                if (a.appSort == null || b.appSort == null) return 0;
                else return a.appSort - b.appSort;
            };
            list.sort(sort_fn);

            this.appColumns = list;
        },

        checkAll: function () {
            for (var i = 0; i < this.sysColumns.length; i++) {
                this.sysColumns[i].checked = this.isCheckAll;
                this.index += 1;
                this.sysColumns[i].appSort = this.index;
            }
            this.syncAppColumns();
        },
        
        setAppColumns: function() {
            // var self = this;
            // self.$nextTick(function () {
            //     var sortable = Sortable.create(document.getElementById('sortable'), {
            //         onEnd: function (e) {
            //             var clonedItems = self.appColumns.filter(function (item) {
            //                 return item;
            //             });
            //             clonedItems.splice(e.newIndex, 0, clonedItems.splice(e.oldIndex, 1)[0]);
            //             self.appColumns = [];
            //             self.$nextTick(function () {
            //                 self.appColumns = clonedItems;
            //             });
            //         }
            //     });
            // })
            var self = this;
            self.$nextTick(function(){
                $("#sortable").sortable({
                    start: function(e,ui) {
                      $(this).attr('data-previndex',ui.item.index());
                    },
                    stop:function(e,ui){
                      var newIndex = ui.item.index();
                      var oldIndex = $(this).attr('data-previndex');
                      var clonedItems = self.appColumns.filter(function (item) {
                        return item;
                      });
                      clonedItems.splice(newIndex, 0, clonedItems.splice(oldIndex, 1)[0]);
                      self.appColumns = [];
                      self.$nextTick(function () {
                        self.appColumns = clonedItems;
                      });
                    }
                })
            })
        },

        saveColumn: function () {
            var self = this;

            var columnIds = [];
            for (var i = 0; i < this.appColumns.length; i++) {
                if (this.appColumns[i].checked) {
                    columnIds.push(this.appColumns[i].sysColumnId);
                }
            }

            if (columnIds.length == 0) {
                self.$message.error("自定义字段长度不能为0！");
            }
            else {
                ajax(
                    '/api/v2/preference/saveAppColumn',
                    {
                        tableName: this.table_name,
                        type: 10,
                        columnIds: columnIds.join(",")
                    },
                    function (resp) {
                        self.dialogShown = false;
                        self.$emit('column_init', self.appColumns);
                    },
                    function (err) {
                        self.$message.error(err);
                    }
                );
            }
        },

        setDefault: function () {
            var self = this;
            ajax(
                '/api/v2/preference/setDefault',
                {
                    tableName: this.table_name,
                    type: 10
                },
                function (resp) {
                    self.dialogShown = false;

                    // 重新初始化
                    self.init();
                },
                function (err) {
                    self.$message.error(err);
                }
            );
        },

        removeColumn: function (userColumn) {
            // 因为对象是同一个，所以左边的也会改变
            userColumn.checked = false;
            this.syncAppColumns();
        },

        updateColumn: function (userColumn) {
            this.index += 1;
            userColumn.appSort = this.index;
            this.syncAppColumns();
            this.setAppColumns();
        }
    }
});

Vue.filter("tbFormat", function(value, format){
    if (!format || format == '') return value;
    else if (!value || value == '') return '';
    else {
        var fns = {
            'DT': function(){ return new Date(parseInt(value)).dateFormat(format.replace(/DT/g, '')); },
            'OrderCoreStatus': function(){ return value; },
            'lack': function(){ return value.toString() == "1" ? "欠交": "不欠交" }
        };

        var key = format.startsWith('DT') ? 'DT' : format;
        var fn = fns[key];
        return fn ? fn(value, format) : value;

    }
});



function strDateFormat (value){

    if (!value || value == '') {
        return value;
    }else{
        var y = value.substr(0,4);
        var m = value.substr(4,2);
        var d = value.substr(6,2);
        return y + '-' + m + '-' + d;
    }
}

Vue.filter("strDateFormat", strDateFormat);


Vue.filter("timestampToDate", function(value){

    if (!value || value == '') {
        return value;
    }else{
        let date = new Date(Number(value));
        let y = date.getFullYear();
        let MM = date.getMonth() + 1;
        MM = MM < 10 ? ('0' + MM) : MM;
        let d = date.getDate();
        d = d < 10 ? ('0' + d) : d;
        let h = date.getHours();
        h = h < 10 ? ('0' + h) : h;
        let m = date.getMinutes();
        m = m < 10 ? ('0' + m) : m;
        let s = date.getSeconds();
        s = s < 10 ? ('0' + s) : s;
        return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
    }
})


Vue.filter("strLongDate", function(value){
    value = value.replace(/\s*/g,"");
    if (value.length== 8) {
        let y = value.substring(0,4);
        let MM = value.substring(4,6);
        let d = value.substring(6,8);
        return y + '-' + MM + '-' + d;
    }else if(value.length== 6){
        let h = value.substring(0,2);
        let m = value.substring(2,4);
        let s = value.substring(4,6);
        return h + ':' + m + ':' + s
    }else if(value.length== 14){
        let y = value.substring(0,4);
        let MM = value.substring(4,6);
        let d = value.substring(6,8);
        let h = value.substring(8,10);
        let m = value.substring(10,12);
        let s = value.substring(12,14);
        return y + '-' + MM + '-' + d + ' ' + h + ':' + m + ':' + s;
    }else{
        return null;
    }
})



Vue.filter("strTimeFormat", function(value, type){

    if (!value || value == '') {
        return value;
    }else{
        var v = "";
        if (value.length == 2) {
            v = value.substr(0,2);
        }

        if (value.length == 4) {
            v = value.substr(0,2) + ':' +value.substr(2,2);
        }

        if (value.length == 6) {
            v = value.substr(0,2) + ':' +value.substr(2,2) + ":" + value.substr(4,2);
        }
        return v;
    }
})



Vue.filter("toFixed", function(v, num){
    if (!v || v == '') {
        return v;
    }
    return v.toFixed(num);

});


Vue.filter("formatTax", function(v){

    return v * 100;

});


function exportFn(url, data, downloadFileName){

    if (window.isBusy){
        console.log('导出中，请稍后...');
        Vue.prototype.$message.error('导出中，请稍后...');
    }
    else {
        window.isBusy = true;

        var downloadUrl = '';
        ajax(url, data,
            function (resp) {
                window.isBusy = false;
                downloadUrl = resp;
            },
            function (err) {
                window.isBusy = false;
                Vue.prototype.$message.error(err);
            }
        );

        // 等待5分种
        var maxWaitTimes = 500;
        var waiting = function () {
            console.log('maxWaitTimes: ' + maxWaitTimes);

            if (!window.isBusy) {
                if (downloadUrl != '') {

                    if (downloadFileName)
                        downloadUrl += '?attach=' + encodeURIComponent(downloadFileName) + '.xlsx';

                    console.log(downloadUrl)

                    $('body').append('<a id="exp" href="'+downloadUrl+'">');
                    document.getElementById("exp").click();
                    $('#exp').remove();
                }
            }
            else {
                maxWaitTimes--;
                if (maxWaitTimes > 0)
                    setTimeout(waiting, 600);
            }
        };
        waiting();
    }
}


// 消息提示
var dialog={
    "success":function success(text,onclose){
        Vue.prototype.$message({
            message: text,
            type: 'success',
            center: true,
            duration:2000,
            offset:200,
            onClose:onclose
        });
    },
    "warning":function warning(text,onclose){
        Vue.prototype.$message({
            message: text,
            type: 'warning',
            center: true,
            duration:2000,
            offset:200,
            onClose:onclose
        });
    },
    "error":function error(text,onclose){
        Vue.prototype.$message({
            message: text,
            type: 'error',
            center: true,
            duration:2000,
            offset:200,
            onClose:onclose
        });
    },
    "messagetip":function messagetip(text,onclose){
        Vue.prototype.$message({
            message: text,
            center: true,
            duration:2000,
            offset:200,
            onClose:onclose
        });
    },

    "confirm": function (text, onConfirm, onCancel, confirmText) {
        Vue.prototype.$confirm(text, '提示', {
            confirmButtonText: (typeof (confirmText) !== 'undefined') ? confirmText : '确定',
            cancelButtonText: '取消',
            showClose:true,
            closeOnClickModal:false,
            type: 'warning',
            iconClass:"iconfont icon-tishi c_blue",
            customClass:'confirm-dialog'
        }).then(function () {
            if (onConfirm) onConfirm();
        }).catch(function () {
            if (onCancel) onCancel();
        });
    }
}

var _timer={
    init:function(obj){
        var _data = $(obj).attr("data-timer");
        // _data seconds
        if(typeof(_data) !== 'undefined' && _data != ""){
            if(typeof(obj.timer)!=='undefined'){
                clearTimeout(obj.timer);
            }
            function timeFn(){
                var _html = _timer.format(_data);
                $(obj).html(_html);

                if(_data>0){
                    _data--;
                    obj.timer = setTimeout(timeFn, 1000);
                }else{
                    clearTimeout(obj.timer);
                }
            }
            timeFn();
        }
    },
    format:function(_data){
        var d=0;
        var h=0;
        var m=0;
        var s=0;
        var _html ="00分00秒";
        if(_data>0){
            d=Math.floor(_data/60/60/24);
            h=Math.floor(_data/60/60%24);
            m=Math.floor(_data/60%60);
            s=Math.floor(_data%60);

            if(d>0){
                _html = "{0}天{1}时".format(d>9?d:"0"+d,h>9?h:"0"+h);
            }else{
                if(h>0){
                    _html = "{0}时{1}分".format(h>9?h:"0"+h,m>9?m:"0"+m);
                }else{
                    if(m>0){
                        _html = "{0}分{1}秒".format(m>9?m:"0"+m,s>9?s:"0"+s);
                    }else{
                        _html = "{0}秒".format(s>9?s:"0"+s);
                    }
                }
            }
        }
        return _html;
    }
};


var id = 0;

window.exportDetailForm = function(url, data){
    id ++;
    var formId = "export-detail-from-" + id;
    var form = $('<form target="_blank" class="hide" action="'+url+'" id="'+formId+'" method="post"></form>');

    for (var i=0; i<data.length; i++) {
        if(typeof(data[i][1]) != 'undefined' && data[i][1] !== ''){
            var input = $('<input id="input-'+i+'" name="'+data[i][0]+'">');
            input.val(data[i][1]);
            form.append(input);

        }
    }
    $('body').append(form);

    form.submit();

    form.remove();
}


function downloadImg(imgSrc){

    if(!!window.ActiveXObject || ("ActiveXObject" in window)){
        console.log('is IE!');

        // TODO: 判断同源图片

        if ($("#IframeReportImg").length === 0){
            $('<iframe style="display:none;" id="IframeReportImg" name="IframeReportImg" onload="_downloadImgCore();" width="0" height="0" src="about:blank"></iframe>').appendTo("body");
        }

        if ($("#IframeReportImg").attr("src") != imgSrc) {
            $("#IframeReportImg").attr("src", imgSrc);
        } else {
            //如指向图片地址,直接调用下载方法
            _downloadImgCore();
        }
    }
    else{
        var image = new Image();
        // 解决跨域 Canvas 污染问题
        image.setAttribute("crossOrigin", "anonymous");
        image.onload = function() {
            var canvas = document.createElement("canvas");
            canvas.width = image.width;
            canvas.height = image.height;
            var context = canvas.getContext("2d");
            context.drawImage(image, 0, 0, image.width, image.height);
            var url = canvas.toDataURL("image/png"); //得到图片的base64编码数据
            var a = document.createElement("a"); // 生成一个a元素
            var event = new MouseEvent("click"); // 创建一个单击事件
            a.download = imgSrc.substring(imgSrc.lastIndexOf('/') + 1);
            a.href = url; // 将生成的URL设置为a.href属性
            a.dispatchEvent(event); // 触发a的单击事件
        };
        image.src = imgSrc;
    }
}

function _downloadImgCore() {
    //iframe的src属性不为空,调用execCommand(),保存图片
    if ($('#IframeReportImg').src != "about:blank") {
        window.frames["IframeReportImg"].document.execCommand("SaveAs");
    }
}


function getExplore(){
    var Sys = {};
    var ua = navigator.userAgent.toLowerCase();
    var s;
    (s = ua.match(/rv:([\d.]+)\) like gecko/)) ? Sys.ie = s[1] :
        (s = ua.match(/msie ([\d\.]+)/)) ? Sys.ie = s[1] :
            (s = ua.match(/edge\/([\d\.]+)/)) ? Sys.edge = s[1] :
                (s = ua.match(/firefox\/([\d\.]+)/)) ? Sys.firefox = s[1] :
                    (s = ua.match(/(?:opera|opr).([\d\.]+)/)) ? Sys.opera = s[1] :
                        (s = ua.match(/chrome\/([\d\.]+)/)) ? Sys.chrome = s[1] :
                            (s = ua.match(/version\/([\d\.]+).*safari/)) ? Sys.safari = s[1] : 0;
    // 根据关系进行判断
    if (Sys.ie) return 'IE'+Sys.ie;
    if (Sys.edge) return 'EDGE';
    if (Sys.firefox) return 'Firefox';
    if (Sys.chrome) return 'Chrome';
    if (Sys.opera) return 'Opera';
    if (Sys.safari) return 'Safari';
    return 'Unkonwn';
}

var browserVersion = getExplore();


function getFileSize ($file) {

    var f = $file[0];

    if (f.files == undefined) {

        try{
            var fso = new ActiveXObject("Scripting.FileSystemObject");
            f.select();
            f.blur();
            var filePath = document.selection.createRange().text;

            if(fso.FileExists(filePath)){
                var fileObj = fso.GetFile(filePath);
                return fileObj.size;
            }
        }catch (e) {
            alert("请按以下方法配置浏览器：请打开【Internet选项-安全-Internet-自定义级别-ActiveX控件和插件-对未标记为可安全执行脚本的ActiveX控件初始化并执行脚本（不安全）-点击启用-确定】");
            window.location.reload();
        }

    }else{
       return f.files[0].size;
    }
}


function _times(num){
    return Math.pow(10, num)
};

function _ceil (d, num){
    var times = this._times(num);
    return Math.round(d * times) / times;
};

function _mul(a, b, num){
    var times = this._times(num);
    var na = this._ceil(a, num) * times;
    var nb = this._ceil(b, num) * times;
    return na * nb / times / times;
};

function _div (a, b, num){
    var r = this._ceil(a, num) / this._ceil(b, num);
    return r ;
};

function _add(a, b, num){
    var times = this._times(num);
    var na = this._ceil(a, num) * times;
    var nb = this._ceil(b, num) * times;
    return (na + nb) / times;
};

function _sub(a, b, num){
    var times = this._times(num);
    var na = this._ceil(a, num) * times;
    var nb = this._ceil(b, num) * times;
    return (na - nb) / times;
};


function addAmt (v1, v2) {
    return _ceil(_add(v1, v2, 4), 2);
}

function subAmt (v) {
    return _ceil(_sub(v1, v2, 4), 2);
}

function mulAmt (v1, v2) {
    return _ceil(_mul(v1, v2, 4), 2);
}

function divAmt (v1, v2) {
    return _ceil(_div(v1, v2, 4), 2);
}

function addWeight (v1, v2) {
    return _add(v1, v2, 4);
}

function subWeight (v1, v2) {
    return _sub(v1, v2, 4);
}

function mulWeight (v1, v2) {
    return _mul(v1, v2, 4);
}

function divWeight (v1, v2) {
    return _div(v1, v2, 4);
}

function regExThreeDec (val){
    return val.toString().match(/^\d*(\.?\d{0,3})/g)[0] || "";
};

function regExZeroDec (val){
    return val.toString().match(/^\d*/g)[0] || "";
};

function regExTwoDec (val){
    return val.toString().match(/^\d*(\.?\d{0,2})/g)[0] || "";
};

function regExFourDec (val){
    return val.toString().match(/^\d*(\.?\d{0,4})/g)[0] || "";
};



Vue.filter("getIsAgree", function(value){

    if (value == "Y") {
        return "同意";
    }else if (value == "R"){
        return "撤回";
    }else{
        return "驳回";
    }
});

Vue.filter("timestampToDate2", function(value){
    return new Date(value).dateFormat("yyyy-MM-dd");
});

Vue.filter("timestampToTime2", function(value){
    return new Date(value).dateFormat("hh:mm");
});

function strIsEmpty (val) {

    if (val == undefined || val == null || val.trim() == "") {
        return true;
    }else{
        return false;
    }

}




