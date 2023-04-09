(function () {
    var urls = {
        //初始化下拉框等数据
        initMaps: "/api/provider/initRegisterMaps",
        //根据统一社会信用代码查询公司信息
        searchByEnterpriseCode: "/api/provider/searchByEnterpriseCode",
        //提交注册
        registerSubmit: "/api/provider/registerSubmit",
        // 跳转注册完成页面
        register3: "/provider/register3",

        advSearchDetail: "/provider/advSearchDetail",
        getInfo: "/provider/getInfo",
    }

    var RZ = new Vue({
        el: '#RZ',
        created:function() {
            //从area.js中读取省份
            this.provincearr = area
            //后台获取下拉框的值
            this.getInitData();
        },
        data:function() {
            //密码验证
            var validatePass = (rule, value, callback) => {
                if (value !== this.ruleForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                } else {
                    callback();
                }
            };

            //手机号(目前只支持中国大陆的手机号码)
            var isTelPhone = (rule, value, callback) => {
                const reg = /^1[34578]\d{9}$/;
                var flag = reg.test(value);
                if (!flag) {
                    callback(new Error('输入的手机号格式不对!'));
                } else {
                    callback();
                }
            }

            //手机号(目前只支持中国大陆的手机号码)
            var isTelPhone = (rule, value, callback) => {
                const reg = /^1[34578]\d{9}$/;
                var flag = reg.test(value);
                if (!flag) {
                    callback(new Error('输入的手机号格式不对!'));
                } else {
                    callback();
                }
            }

            //手机和座机号同时验证
            var isMobilePhone = (rule, value, callback) => {
                const reg = /^((0\d{2,3}-\d{7,8})|(1[35847]\d{9}))$/;
                var flag = reg.test(value);
                if (!flag) {
                    callback(new Error('输入的手机或电话格式不对!'));
                } else {
                    callback();
                }
            }

            //邮箱验证
            var isMail = (rule, value, callback) => {
                const reg = /^([A-Za-z0-9_\-\.])+\@([A-Za-z0-9_\-\.])+\.([A-Za-z]{2,4})$/;
                var flag = reg.test(value);
                if (!flag) {
                    callback(new Error('输入的邮箱格式不对!'));
                } else {
                    callback();
                }
            }

            return {
                //企业名称数组
                enterpriseNameArr: [],
                //省份数组
                provincearr: [],
                //城市数组--所在区域
                cityarr: [],
                //城市数组--注册地址
                cityarr2: [],
                //企业性质数组
                enterpriseTypeArr: [],
                //经营方式数组
                manageWayArr: [],
                //区域数组--所在区域
                regionarr: [],
                //区域数组--注册地址
                regionarr2: [],
                //企业所在省
                registScopeArr: [],
                //错误消息
                errorMsg: "",
                //验证规则
                ruleForm: {
                    //企业名称
                    enterpriseName: '',
                    //社会信用代码
                    enterpriseCode: '',
                    //所在区域-省
                    provinceValue: '',
                    //所在区域-市
                    cityValue: '',
                    //所在区域-区
                    RegionValue: '',
                    //企业性质
                    enterpriseType: '',
                    //经营方式
                    manageWay: '',
                    //邮政编码
                    postcode: '',
                    //注册资金
                    regCapital: '',
                    //公司电话
                    telephone: '',
                    //注册范围
                    registScope: '',
                    /* //注册地址-省
                     provinceValue2: '',
                     //注册地址-市
                     cityValue2: '',
                     //注册地址-区
                     RegionValue2: '',*/
                    vendorAddr: '',
                    //代理人
                    agent: '',
                    //法人代表人
                    corporation: '',
                    //代理人手机
                    agentTel: '',
                    //法人代表人电话
                    corporationPhone: '',
                    //代理人邮箱
                    agentMail: '',
                    //开户银行名称
                    bankName1: '',
                    //开户银行行号
                    bankCode1: '',
                    //开户银行账号
                    cardNum1: '',
                    //开户银行名称
                    bankName2: '',
                    //开户银行行号
                    bankCode2: '',
                    //开户银行账号
                    cardNum2: '',
                    //开户银行名称
                    bankName3: '',
                    //开户银行行号
                    bankCode3: '',
                    //开户银行账号
                    cardNum3: '',
                    //密码设置
                    password: '',
                    //密码确认
                    password2: '',
                },
                rules: {
                    enterpriseName: [
                        {required: true, message: '请选择企业名称', trigger: 'change'}
                    ],
                    enterpriseCode: [
                        {required: true, message: '请填写信用代码', trigger: 'blur'}
                    ],
                    provinceValue: [
                        {required: true, message: '请选择省份', trigger: 'change'}
                    ],
                    cityValue: [
                        {required: true, message: '请选择城市', trigger: 'change'}
                    ],
                    enterpriseType: [
                        {required: true, message: '请选择企业性质', trigger: 'change'}
                    ],
                    manageWay: [
                        {required: true, message: '请选择经营方式', trigger: 'change'}
                    ],
                    postcode: [
                        {required: true, message: '请填写邮政编码', trigger: 'blur'}
                    ],
                    regCapital: [
                        {required: true, message: '请填写注册资金', trigger: 'blur'},
                        {type: 'number', message: '注册资金必须为数字值'}
                    ],
                    telephone: [
                        {required: true, message: '请填写公司电话', trigger: 'blur'},
                        {validator: isMobilePhone, trigger: 'blur'}
                    ],
                    /*provinceValue2: [
                        {required: true, message: '请选择省份', trigger: 'change'}
                    ],
                    cityValue2: [
                        {required: true, message: '请选择城市', trigger: 'change'}
                    ],
                    RegionValue2: [
                        {required: true, message: '请选择区域', trigger: 'change'}
                    ],*/
                    registScope: [
                        {required: true, message: '请选择企业所在省', trigger: 'change'}
                    ],
                    agent: [
                        {required: true, message: '请填写代理人', trigger: 'blur'}
                    ],
                    corporation: [
                        {required: true, message: '请填写法人代表人', trigger: 'blur'}
                    ],
                    agentTel: [
                        {required: true, message: '请填写代理人手机', trigger: 'blur'},
                        {validator: isTelPhone, trigger: 'blur'}
                    ],
                    corporationPhone: [
                        {required: true, message: '请填写法人代表人电话', trigger: 'blur'},
                        {validator: isMobilePhone, trigger: 'blur'}
                    ],
                    agentMail: [
                        {required: true, message: '请填写代理人邮箱', trigger: 'blur'},
                        {validator: isMail, trigger: 'blur'}
                    ],
                    bankName1: [
                        {required: true, message: '请填写开户银行名称', trigger: 'blur'}
                    ],
                    bankCode1: [
                        {required: true, message: '请填写开户银行行号', trigger: 'blur'}
                    ],
                    cardNum1: [
                        {required: true, message: '请填写开户银行账号', trigger: 'blur'}
                    ],
                    bankName2: [
                        {required: true, message: '请填写开户银行名称', trigger: 'blur'}
                    ],
                    bankCode2: [
                        {required: true, message: '请填写开户银行行号', trigger: 'blur'}
                    ],
                    cardNum2: [
                        {required: true, message: '请填写开户银行账号', trigger: 'blur'}
                    ],
                    /*bankName3: [
                        {required: true, message: '请填写开户银行名称', trigger: 'blur'}
                    ],
                    bankCode3: [
                        {required: true, message: '请填写开户银行行号', trigger: 'blur'}
                    ],
                    cardNum3: [
                        {required: true, message: '请填写开户银行账号', trigger: 'blur'}
                    ],*/
                    password: [
                        {required: true, message: '请设置密码', trigger: 'blur'},
                        {min: 6, message: '密码不小于6位数', trigger: 'blur'},
                    ],
                    password2: [
                        {required: true, message: '请输入确认密码', trigger: 'blur'},
                        {validator: validatePass, trigger: 'blur'}
                    ],
                },
                forDataList:[],
                enterpriseNameShow:false,
                dataList:{"status":"200","message":"操作成功","sign":"868ade53753aa70ec5a6d528cb996e52","data":{"total":5000,"num":10,"items":[{"name":"山东港口日照港集团有限公司","id":"ec2dc50a-3069-437e-be50-3dc75004401a","start_date":"2004-02-24","oper_name":"张江南","reg_no":"371100018050397","credit_no":"91371100168357011L","matchType":"历史名称","matchItems":"日照港集团有限公司"},{"name":"山东钢铁集团日照有限公司","id":"e31a0995-7440-4ab4-a3bc-185aa645d389","start_date":"2009-02-19","oper_name":"吕铭","reg_no":"370000000000986","credit_no":"91370000685900369J","matchType":"企业名称","matchItems":"山东钢铁集团日照有限公司"},{"name":"广东华日照明有限公司","id":"a1c983ef-1f32-4a00-80d6-890d5bf8e3df","start_date":"2001-04-30","oper_name":"李江华","reg_no":"440682400009459","credit_no":"91440605728758914E","matchType":"历史名称","matchItems":"佛山市南海区华日照明电器有限公司"},{"name":"华能日照热力有限公司","id":"9b50b3ee-0b7a-41b0-a5e2-37f777ec50a6","start_date":"2016-02-02","oper_name":"林兆灵","reg_no":"371123000000260","credit_no":"91371100MA3C6A37XK","matchType":"企业名称","matchItems":"华能日照热力有限公司"},{"name":"国网山东省电力公司日照供电公司","id":"adad4c53-0531-41e1-b2c2-c2857f247840","start_date":"2008-08-05","oper_name":"于安迎","reg_no":"371100100000024","credit_no":"91371100168367084W","matchType":"历史名称","matchItems":"山东电力集团公司日照供电公司"},{"name":"国药控股日照有限公司","id":"710d472d-58d2-45a5-95d9-131b1bf5eeff","start_date":"2007-01-04","oper_name":"徐会中","reg_no":"371100228042957","credit_no":"91371100797319296B","matchType":"历史名称","matchItems":"日照市海力医药有限公司"},{"name":"福建福日照明有限公司","id":"0bace412-e3ae-4a3a-bdc1-f41036d359ee","start_date":"2001-06-30","oper_name":"李万平","reg_no":"350000100019901","credit_no":"9135000072972250XX","matchType":"企业名称","matchItems":"福建福日照明有限公司"},{"name":"中央储备粮日照仓储有限公司","id":"4b7094df-5573-4ebd-8c9b-8ee0ee20a2fb","start_date":"2006-01-18","oper_name":"陈建军","reg_no":"371123018001699","credit_no":"91371100785006348E","matchType":"历史名称","matchItems":"中储粮日照粮油储备库"},{"name":"江苏恒日照明有限公司","id":"6078f783-6f0c-44c2-9898-28d8184439c6","start_date":"1996-11-28","oper_name":"姚金方","reg_no":"321181000038045","credit_no":"913211812537125670","matchType":"历史名称","matchItems":"丹阳市恒日照明有限公司"},{"name":"江苏华日照明器材有限公司","id":"54e47d8d-5e72-455d-a1b6-5ec8c038aee2","start_date":"2002-01-11","oper_name":"张跃庭","reg_no":"321181000041910","credit_no":"91321181733752023N","matchType":"历史名称","matchItems":"丹阳市华日照明器材有限公司"}]}},
                options: [],
                value: [],
                list: [],
                loading: false,
                states: ["Alabama", "Alaska", "Arizona",
                    "Arkansas", "California", "Colorado",
                    "Connecticut", "Delaware", "Florida",
                    "Georgia", "Hawaii", "Idaho", "Illinois",
                    "Indiana", "Iowa", "Kansas", "Kentucky",
                    "Louisiana", "Maine", "Maryland",
                    "Massachusetts", "Michigan", "Minnesota",
                    "Mississippi", "Missouri", "Montana",
                    "Nebraska", "Nevada", "New Hampshire",
                    "New Jersey", "New Mexico", "New York",
                    "North Carolina", "North Dakota", "Ohio",
                    "Oklahoma", "Oregon", "Pennsylvania",
                    "Rhode Island", "South Carolina",
                    "South Dakota", "Tennessee", "Texas",
                    "Utah", "Vermont", "Virginia",
                    "Washington", "West Virginia", "Wisconsin",
                    "Wyoming"]

            }

        },
        mounted: function () {


        },
        methods: {
            remoteMethod(query) {
                var that = this;
                if (query !== '') {
                    that.loading = true;
                    //     setTimeout(() => {
                    that.loading = false;
                    var returnData = that.dataList;
                    var items=returnData.data.items;
                    that.list = items.map(item => {
                        return { value: `${item.id}`, label: `${item.name}` };
                    });
                    that.options = that.list.filter(item => {
                        return item.label.toLowerCase()
                            .indexOf(query.toLowerCase()) > -1;
                });
                    //     }, 200);
                } else {
                    this.options = [];
                }
            },
            syncMessage:function(){
                var that=this;
                var valuesId=that.ruleForm.enterpriseName;
                var dataList=that.options;
                var values='';
                if(valuesId !=''){
                    for(let i=0;i<dataList.length;i++){
                        if(valuesId == dataList[i].value){
                            values=dataList[i].label;
                        }
                    }
                    ajax(urls.getInfo, {'uniformNo': values},
                        function (resp) {
                            debugger;
                            if (resp.status == "200") {
                                //返回数据
                                var returnData = resp.data;
                                var credit_no=returnData.credit_no;//统一代码
                                var address =returnData.address ;//公司地址
                                var city=returnData.city ;//城市编码
                                var telephone=returnData.contact.telephone;//公司电话
                                var oper_name=returnData.oper_name;//法人

                                that.ruleForm.enterpriseCode=credit_no;
                                that.ruleForm.vendorAddr=address;
                                that.ruleForm.postcode=city;
                                that.ruleForm.telephone=telephone;
                                that.ruleForm.corporation=oper_name;

                            } else {
                                dialog.error('获取数据失败！');
                            }
                        },
                        function (err) {
                            dialog.error('获取下拉框数据失败！');
                        }
                    );
                }
            },
            //赋值
            setEnterName:function(value){
                var that=this;
                alert(value);
                that.ruleForm.enterpriseName=value;
                that.enterpriseNameShow=false;
            },
            //模糊查询
            getEnter:function(query){
                // debugger
                // alert(1);
                var that=this;
                if (query !== '') {
                    that.enterpriseNameShow = true;


                    ajax(urls.advSearchDetail, {'enName': query},
                        function (resp) {
                            console.log(resp);
                            debugger;
                            if (resp.status == "200") {
                                //返回数据
                                console.log(resp);
                                console.log(resp.data);
                                console.log(resp.data.items);
                                var items = resp.data.items;
                                that.options = items.map(item => {
                                    return {value: item.name, label: item.name};
                            });
                                //      that.options = that.list;

                            } else {
                                dialog.error('获取下拉框数据失败！');
                            }
                        },
                        function (err) {
                            dialog.error('获取下拉框数据失败！');
                        }
                    );
                }
                /*
                var url="http://api.qixin.com/APIService/v2/search/advSearch?keyword="+values+"&matchType=ename&appkey=6d0f8a85-0783-4d1d-a062-b8999cd5388f&secret_key=7e79fd90-5b96-4c53-8382-7457d613e204&skip=0";
                ajax(url, {'ename': values},
                    function (resp) {
                    debugger;
                        if (resp.status == "1") {
                            //返回数据
                            var returnData = resp.data;
                            var items=returnData.data.items;
                            if(items.length>0){
                                var lis="";
                                for(var i=0;i<items.length;i++){
                                    var item=items[i];
                                    var id=item.id;
                                    var name=item.name;

                                    var li='<li @click="edit(item.numberCode)" id="'+id+'">'+name+'</li>';
                                    lis=lis+li;
                                }
                                $("#names").empty().append(lis);
                                $("#names").show();
                            }
                            //企业名称

                            // 公司地址
                          //  _this.ruleForm.vendorAddr = returnData.vendorAddr;

                        } else {
                            dialog.error('获取下拉框数据失败！');
                        }
                    },
                    function (err) {
                        dialog.error('获取下拉框数据失败！');
                    }
                );
                 */

            },
            //折叠基本信息
            foldI: function (num) {
                document.getElementById('tableInfo' + num).style.display = "none";
                document.getElementById('foldSpan' + num).style.display = "none";
                document.getElementById('openSpan' + num).style.display = "";
            },
            //展开基本信息
            openI: function (num) {
                document.getElementById('tableInfo' + num).style.display = "";
                document.getElementById('foldSpan' + num).style.display = "";
                document.getElementById('openSpan' + num).style.display = "none";
            },
            //关闭提醒
            closeWarn: function (num) {
                document.getElementById('warnBox').style.display = "none";
            },
            //后台获取下拉框的值
            getInitData: function () {
                var _this = this;
                ajax(urls.initMaps, {},
                    function (resp) {
                        if (resp.status == "1") {
                            //所有下拉框map
                            var returnData = resp.data;
                            //企业名称
                            _this.enterpriseNameArr = returnData.enterpriseNameArr;
                            //企业性质
                            _this.enterpriseTypeArr = returnData.enterpriseTypeArr;
                            //经营方式
                            _this.manageWayArr = returnData.manageWayArr;
                            //企业所在省
                            _this.registScopeArr = returnData.registScopeArr;

                        } else {
                            dialog.error('获取下拉框数据失败！');
                        }
                    },
                    function (err) {
                        dialog.error('获取下拉框数据失败！');
                    }
                );
            },
            //选择省份联动市数据--注册地址
            selectProvimce1: function (id) {
                this.cityarr = [];
                this.ruleForm.regionarr = [];
                this.ruleForm.cityValue = '';
                this.ruleForm.RegionValue = '';
                for (let item of this.provincearr) {
                    if (id == item.id) {
                        this.cityarr = item.children
                    }
                }
            },
            //选择省份联动市数据--所属区域
            /*selectProvimce2: function (id) {
                this.cityarr2 = [];
                this.ruleForm.regionarr2 = [];
                this.ruleForm.cityValue2 = '';
                this.ruleForm.RegionValue2 = '';
                for (let item of this.provincearr) {
                    if (id == item.id) {
                        this.cityarr2 = item.children
                    }
                }
            },*/
            //选择市联动区数据--所属区域
            selectcity1: function (id) {
                this.regionarr = [];
                this.ruleForm.RegionValue = '';
                for (let item of this.cityarr) {
                    if (id == item.id) {
                        this.regionarr = item.children
                    }
                }
            },
            //选择市联动区数据--注册地址
            /*selectcity2: function (id) {
                this.regionarr2 = [];
                this.ruleForm.RegionValue2 = '';
                for (let item of this.cityarr2) {
                    if (id == item.id) {
                        this.regionarr2 = item.children
                    }
                }
            },*/
            //根据统一社会信用代码查询
            searchByEnterpriseCode: function () {
                var _this = this;
                ajax(urls.searchByEnterpriseCode, {enterpriseCode: _this.ruleForm.enterpriseCode},
                    function (resp) {
                        if (resp.status == "1") {
                            //返回数据
                            var returnData = resp.data;
                            //企业名称
                            _this.ruleForm.enterpriseName = returnData.enterpriseName;
                            //所在区域--省
                            _this.ruleForm.provinceValue = returnData.provinceValue;
                            //省市联动事件
                            _this.selectProvimce1(returnData.provinceValue);
                            //所在区域--市
                            _this.ruleForm.cityValue = returnData.cityValue;
                            //市区联动事件
                            _this.selectcity1(returnData.cityValue);
                            //所在区域--区
                            _this.ruleForm.RegionValue = returnData.RegionValue;
                            //企业性质
                            _this.ruleForm.enterpriseType = returnData.enterpriseType;
                            //经营方式
                            _this.ruleForm.manageWay = returnData.manageWay;
                            //邮政编码
                            _this.ruleForm.postcode = returnData.postcode;
                            //注册资金
                            _this.ruleForm.regCapital = returnData.regCapital;
                            //公司电话
                            _this.ruleForm.telephone = returnData.telephone;
                            //注册地址--省
                            _this.ruleForm.provinceValue2 = returnData.provinceValue2;
                            //省市联动事件
                            _this.selectProvimce2(returnData.provinceValue2);
                            //注册地址--市
                            _this.ruleForm.cityValue2 = returnData.cityValue2;
                            //市区联动事件
                            _this.selectcity2(returnData.cityValue2);
                            //注册地址--区
                            _this.ruleForm.RegionValue2 = returnData.RegionValue2;
                            // 公司地址
                            _this.ruleForm.vendorAddr = returnData.vendorAddr;

                        } else {
                            dialog.error('获取下拉框数据失败！');
                        }
                    },
                    function (err) {
                        dialog.error('获取下拉框数据失败！');
                    }
                );
            },
            //提交表单
            submitForm: function (formName) {
                var _this = this;
                console.log(_this.ruleForm)
                _this.$refs[formName].validate((valid) => {
                    if (valid) {
                        ajax(urls.registerSubmit, _this.ruleForm,
                            function (resp) {
                                if (resp.status == "1") {
                                    //供应商账号
                                    var supplierCode = resp.data;
                                    setTimeout(function () {
                                        //跳转到注册信息填写页面
                                        window.location.href = urls.register3 + "?supplierCode=" + supplierCode;
                                    }, 500);
                                } else {
                                    _this.errorMsg = resp.msg;
                                    this.$message({
                                        showClose: true,
                                        message: _this.errorMsg,
                                        type: 'error'
                                    });
                                }
                            },
                            function (err) {
                                _this.$message({
                                    showClose: true,
                                    message: _this.errorMsg,
                                    type: 'error',
                                    duration: 0
                                });
                            }
                        );
                    } else {
                        setTimeout(() => {
                    var isError = document.getElementsByClassName("is-error");
                if (isError[0].querySelector('input')) {
                    isError[0].querySelector('input').focus();
                } else if (isError[0].querySelector('textarea')) {
                    isError[0].querySelector('textarea').focus();
                }
            }, 1)
                return false;
            }
            });
            }
        }
    })
})();