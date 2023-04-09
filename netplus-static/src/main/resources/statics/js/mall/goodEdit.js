(function (win) {
    var url = {

        getSupplierGoodEditInfo: "/api/mall/getSupplierGoodEditInfo/",
        save: "/api/mall/good/save/"

    };

    var app = new Vue({

        el: "#app",
        data: {

            cdn: cdn,

            goodId: goodId,

            value:'',
            checked:false,

            rules:{
                price:[
                    { required: true, message: '价格必填', trigger: 'blur' },
                ],

            },

            currImgIndex:0,
            topStyle:{transform:''},
            r_img: {},
            topShow:false,
            rShow:false,
            mainPicList: [],
            isIntTimePurchaseCheck: false,
            isDecimalPurchaseCheck: false,
            isDisabled: false,

            goodInfo: {},

            editor: "",
        },

        mounted: function () {
            var _this = this;
            
            var E = win.wangEditor
            var editor = new E('#editor');

            editor.config.uploadImgServer = '/api/v2/fileupload/doUpload/';
            editor.config.uploadImgMaxLength = 1;
            editor.config.uploadImgMaxSize = 3 * 1024 * 1024;
            editor.config.uploadFileName = 'file';

            editor.config.uploadImgHooks = {
                before: function (xhr, editor, files) {
                    // 图片上传之前触发
                    // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，files 是选择的图片文件

                    // 如果返回的结果是 {prevent: true, msg: 'xxxx'} 则表示用户放弃上传
                    // return {
                    //     prevent: true,
                    //     msg: '放弃上传'
                    // }



                   _this.picName = files[0].name;

                },

                success: function (xhr, editor, result) {
                    // 图片上传并返回结果，图片插入成功之后触发
                    // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
                },

                fail: function (xhr, editor, result) {

                    dialog.error("图片插入失败")

                    // 图片上传并返回结果，但图片插入错误时触发
                    // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象，result 是服务器端返回的结果
                    _this.picName = "";
                },

                error: function (xhr, editor) {

                    dialog.error("上传失败")
                    _this.picName = "";
                    // 图片上传出错时触发
                    // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
                },

                timeout: function (xhr, editor) {

                    dialog.error("图片上传超时")
                    _this.picName = "";
                    // 图片上传超时时触发
                    // xhr 是 XMLHttpRequst 对象，editor 是编辑器对象
                },

                // 如果服务器端返回的不是 {errno:0, data: [...]} 这种格式，可使用该配置
                // （但是，服务器端返回的必须是一个 JSON 格式字符串！！！否则会报错）
                customInsert: function (insertImg, result, editor) {
                    // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
                    // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

                    // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
                    var url = result.url;
                    insertImg(url);
                    // result 必须是一个 JSON 格式字符串！！！否则报错
                }
            }

            editor.onchange = function(){
                var imgs = editor.$txt.find('img');
                console.log(imgs);
            },

            editor.create();

            this.editor = editor;
            this.getSupplierGoodEditInfo();

        },

        methods: {
            // 鼠标进入原图空间函数
            enterHandler:function() {
                // 层罩及放大空间的显示
                if(this.mainPicList.length!=0){
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
                this.topShow = false;
                this.rShow = false;
            },
            getSupplierGoodEditInfo: function(){
                var _this = this;

                ajax(url.getSupplierGoodEditInfo, {goodId: _this.goodId}, function(json){
                    _this.goodInfo = json;
                    _this.goodInfo.tax2 = json.tax * 100 + "%";
                    _this.mainPicList = json.goodPicList || [];
                    _this.$nextTick(()=>{
                        var imgZoomBox = new Swiper('.swiper-container1', {
                            slidesPerView: 4,
                            spaceBetween: 10,
                            slidesPerView : 'auto',
                            navigation: {
                                nextEl: '.swiper-button-next',
                                prevEl: '.swiper-button-prev',
                            },
                            observer:true,//修改swiper自己或子元素时，自动初始化swiper
                            observeParents:true//修改swiper的父元素时，自动初始化swiper
                        });
                    })
                    _this.isIntTimePurchaseCheck = json.isinttimepurchase == 'Y';
                    _this.isDecimalPurChaseCheck = json.isdecimalpurchase == 'Y';

                    if (_this.goodInfo.minbuyqty <= 0) {
                        _this.isDisabled = true;
                    }

                    _this.editor.txt.html(json.content)

                }, function(err){
                    dialog.error(err);
                })

            },
            imgZoomClick:function(e){
                this.currImgIndex = e;
            },
            nextBtn:function(){
                this.currImgIndex++;
                if(this.currImgIndex>this.mainPicList.length){
                    this.currImgIndex=0
                }
            },
            preBtn:function(){
                this.currImgIndex--;
                if(this.currImgIndex<0){
                    this.currImgIndex=0
                }
            },
            inputKeyUp: function(){
                var price = this.goodInfo.price;
                var tax = this.goodInfo.tax;

                price = regExFourDec(price);


                if (price != "") {
                    var noTaxPrice = divAmt(price, (1+tax));
                    this.goodInfo.notaxprice = noTaxPrice;
                }else{
                    this.goodInfo.notaxprice = "";
                }

                this.goodInfo.price = price;
            },

            inputKeyUp2: function(){
                this.goodInfo.minbuyqty = regExFourDec(this.goodInfo.minbuyqty);
                if (this.goodInfo.minbuyqty == '' || this.goodInfo.minbuyqty <= 0) {
                    this.isDisabled = true;
                    this.isIntTimePurchaseCheck = false;
                }else{
                    this.isDisabled = false;
                }
            },

            inputKeyUp3: function(key){
                this.goodInfo[key] = regExZeroDec(this.goodInfo[key]);
            },

            //上传图片
            beforeUpload: function(file){

                var isPic = file.type == 'image/png' || file.type == 'image/jpg' || file.type == 'image/jpeg';
                var isLt300K = file.size / 1024 <= 300;
                var isLte10 = this.mainPicList.length < 10

                if (!isPic) {
                    this.$message.error('图片类型只能是png, jpg, jpeg');
                }

                if (!isLt300K) {
                    this.$message.error('图片大小不超过300K');
                }

                if (!isLte10) {
                    this.$message.error('最多上传10张图片');
                }


                return isPic && isLt300K && isLte10;
            },

            successUpload: function(resp){

                this.mainPicList.push({
                    picturenum: this.mainPicList.length + 1,
                    goodid: goodId,
                    pictureurl: resp.url,
                    picturename: " ",
                });

                this.$refs.upload.clearFiles();
            },

            errorUpload: function(err){
                dialog.error(err);
                this.$refs.upload.clearFiles();
            },

            delPic: function(){
                this.mainPicList.splice(this.currImgIndex, 1);
                this.currImgIndex = 0;
            },

            //保存
            save: function() {
                var _this = this;

                _this.$refs.addForm.validate(function(valid){
                    if (valid) {

                        var param = {
                            goodId: _this.goodInfo.goodid,
                            price: _this.goodInfo.price,
                            packThick: _this.goodInfo.packthick,
                            packWidth: _this.goodInfo.packwidth,
                            packHeight: _this.goodInfo.packheight,
                            packUnit: _this.goodInfo.packunit,
                            content: _this.editor.txt.html(),
                            minBuyQty: _this.goodInfo.minbuyqty,
                            isIntTimePurchase: _this.isIntTimePurchaseCheck ? "Y": "N",
                            mainPicList: _this.mainPicList,
                        }

                        ajax(url.save, param, function(resp){

                            dialog.success("修改成功", function(){
                                formForward("/mall/goodsListed", {}, '_self', 'GET')
                            });

                        }, function(err){
                            dialog.error(err);
                        })

                    } else {
                        return false;
                }});
            },
        }

    });

    win.app = app;

})(window || {});
