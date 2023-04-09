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
            successCount:0,
            errorCount:0,
            tableData: [],
            fileList:[],
            errorList:[],
            uploadCount:0,
            showErrorDialog:false,
            cz:""
        },

        mounted: function () {
            this.search();
        },
        methods: {
            watchCount:function () {
                var count=0;
                var _this=this;
                _this.isSelectedAll=false;
                $.each(_this.tableData, function(i, d){

                    $.each(d.tbmqq429VoList, function(ii, dd){
                        if(dd.checked ){
                            count++;
                        }
                    })

                })
                this.count=count;


            },
            selectedAll: function(o, n) {
                var _this = this;
                var count=0;
                $.each(_this.tableData, function(i, d){

                    $.each(d.tbmqq429VoList, function(ii, dd){
                        dd.checked = _this.isSelectedAll;
                        if (_this.isSelectedAll) {
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
                    pictureType: "1",
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
                    {
                        tbmqq429List: param
                    },
                    function (resp) {
                        dialog.success("图片移动至回收站成功", function(){
                            location.reload();
                        });
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            beforeUpload: function(file){

                this.showErrorDialog=true;
                var isPic = file.type == 'image/png' || file.type == 'image/jpg' || file.type == 'image/jpeg';

                var isLt300K = file.size/1024<300;

                if (!isPic) {
                    this.errorList.push({
                        message:"图片类型不是png, jpg, jpeg",
                        pictureName:file.name
                    });
                    this.errorCount++;
                }
                if (!isLt300K) {
                    this.errorList.push({
                        message:"图片大小不能超过300K",
                        pictureName:file.name
                    });
                    this.errorCount++;
                }
                return isPic && isLt300K;
            },

            clear:function () {
                this.errorList=[];
                this.successCount=0;
                this.errorCount=0;
                this.$refs.upload.clearFiles();
            },

            successUpload: function(response,file){
                var _this=this;

                ajax(
                    url.createPicLib,
                    {
                        pictureName: file.name,
                        pictureType: "1",
                        pictureUrl: encodeURI(response.url)
                    },
                    function(resp){

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
                    }
                );

            },
            limitCheck:function () {
                this.$message.warning('超出最大文件上传数量')
            },
            errorUpload: function(err,file,fileList){
                var _this=this;
                _this.errorList.push({
                    message:"上传失败",
                    pictureName:file.name,
                });
                _this.errorCount++;
            },
            closeDialog:function () {
                this.showErrorDialog=false;
            },
            getSelectedData: function(){
                var _this = this;
                var data = [];

                $.each(_this.tableData, function(i, d){

                    $.each(d.tbmqq429VoList, function(ii, dd){
                        if (dd.checked) {
                            data.push({
                                picturenum: dd.picturenum,
                                picturetype: "1",
                                matrltm: dd.matrltm,
                                deleted: dd.deleted,
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


        },

    });

    win.app = app;

})(window||{})