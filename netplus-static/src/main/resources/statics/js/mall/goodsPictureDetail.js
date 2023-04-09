(function(win){

    var url = {
        getPicLibList:"/api/mall/picLib/getPicLibList/",
        updatePicLib:"/api/mall/picLib/updatePicLib/",
        createPicLib:"/api/mall/picLib/createPicLib/"
    };


    var app = new Vue({
        el: '#RZ',
        data: {

            searchKey: '',
            isSelectedAll: false,
            count:0,
            testCount:0,
            successCount:0,
            errorCount:0,
            tableData: [],
            fileList:[],
            errorList:[],
            uploadCount:0,
            showErrorDialog:false
        },

        mounted: function () {
            this.search();
        },
        methods: {
            watchCount:function () {
                var count=0;
                var _this=this;
                $.each(_this.tableData, function(i, d){

                    $.each(d.tbmqq429VoList, function(ii, dd){
                        if(dd.checked ){
                            count++;
                        }
                    })

                })
                this.count=count;
            },
            search: function () {
                var self = this;
                var param = {
                    searchKey: self.searchKey,
                    pictureType: "2",
                    deleted: "N"
                };

                ajax(
                    url.getPicLibList,
                    param,
                    function (resp) {
                        self.tableData = resp;
                        self.watchCount();
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            removePic: function () {
                var self = this;
                var param = self.getSelectedData();


                $.each(param, function(i, d){
                    d.deleted = "Y";
                })

                ajax(
                    url.updatePicLib,
                    {tbmqq429List: param},
                    function (resp) {
                        dialog.success("图片移动至回收站成功", function(){
                            self.search();
                        });
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            beforeUpload: function(file){
                this.uploadCount++;
                var isPic = file.type == 'image/png' || file.type == 'image/jpg' || file.type == 'image/jpeg';

                var isLt300K = file.size/1024<500;

                if (!isPic) {
                    this.errorList.push({
                        message:"图片类型不是png, jpg, jpeg",
                        pictureName:file.name
                    });
                    this.errorCount++;
                    this.testCount++;
                }
                if (!isLt300K) {
                    this.errorList.push({
                        message:"图片大小不能超过500K",
                        pictureName:file.name
                    });
                    this.errorCount++;
                    this.testCount++;
                }
                return isPic && isLt300K;
            },


            successUpload: function(response,file){
                var _this=this;
                _this.testCount++;
                ajax(
                    url.createPicLib,
                    {
                        pictureName: file.name,
                        pictureType: "2",
                        pictureUrl: encodeURI(response.url)
                    },
                    function(resp){
                        console.log(resp);
                        if(resp.status==0){
                            var obj={
                                message:resp.message,
                                pictureName: resp.data
                            }
                            _this.errorList.push(obj)
                            _this.errorCount++;
                        }
                        if(resp.status==1){
                            _this.successCount++;
                        }
                        _this.search();

                    },
                    function(err){
                        _this.errorList.push({
                            message:err,
                            pictureName:file.name,
                        });
                        _this.errorCount++;
                        _this.$refs.upload.clearFiles();
                    }
                );

            },

            errorUpload: function(err,file,fileList){
                var _this=this;
                _this.testCount++;
                _this.errorList.push({
                    message:"上传失败",
                    pictureName:file.name,
                });
                _this.errorCount++;
                this.$refs.upload.clearFiles();
            },
            closeDialog:function () {
                this.showErrorDialog=false;
            },
            limitCheck:function () {
                this.$message.warning('超出最大文件上传数量')
            },
            getSelectedData: function(){
                var _this = this;
                var data = [];

                $.each(_this.tableData, function(i, d){

                    $.each(d.tbmqq429VoList, function(ii, dd){
                        if (dd.checked) {
                            data.push({
                                picturenum: dd.picturenum,
                                picturetype: "2",
                                matrlno: dd.matrlno,
                                deleted: dd.deleted
                            })
                        }
                    })

                })

                return data;
            },


            toPreview: function(pictureurl) {
                formForward(pictureurl, {}, "_blank", "get");
            }
        },
        watch: {

            isSelectedAll: function(o, n) {
                var _this = this;
                var count=0;
                $.each(_this.tableData, function(i, d){

                    $.each(d.tbmqq429VoList, function(ii, dd){
                        dd.checked = _this.isSelectedAll;
                        if (_this.isSelectedAll) {
                            count++;
                        }else {
                            count=0;
                        }
                    })

                })
                this.count=count;
            },
            testCount:function () {
                var _this = this;
                    _this.showErrorDialog=true;

            }
        },

    });

    win.app = app;

})(window||{})