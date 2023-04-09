(function () {

    var url = {
        listAdvertising : "/api/mall/advertising/list/",
        getPublishAdvertisingCount : "/api/mall/advertising/publishCount/",
        publishAdvertising : "/api/mall/advertising/publish/",
        cancelPublishAdvertising : "/api/mall/advertising/cancelPublish/",
        removeAdvertising : "/api/mall/advertising/remove/",
        advertisingLeftMoveOne : "/api/mall/advertising/move/left/",
        advertisingRightMoveOne : "/api/mall/advertising/move/right/",
        addAdvertising : "/api/mall/advertising/add/"
    };

    var RZ=new Vue({
        el:'#RZ',
        data:function(){
            return {
                count:0,
                list:[],
                checkList:[],
                viewIndex:0
            }

        },
        mounted:function(){
            this.initPage();
        },
        methods:{
            initPage: function (){
                this.initCount();
                this.initList();
            },
            initCount: function (){
                var self = this;
                ajax(url.getPublishAdvertisingCount,
                    {
                    },
                    function (resp) {
                        if(resp.status == "1"){
                            self.count = resp.data;
                        }else{
                            dialog.error(resp.msg);
                        }
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            initList: function () {
                var self = this;
                ajax(url.listAdvertising,
                    {
                    },
                    function (resp) {
                        if(resp.status == "1"){
                            self.list = resp.data;
                            self.$forceUpdate();
                        }else{
                            dialog.error(resp.msg);
                        }
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            showIndex: function (index){
                this.viewIndex=index;
            },
            publishChecked: function (){
                this.changeStatus(url.publishAdvertising);
            },
            cancelPublishChecked: function (){
                this.changeStatus(url.cancelPublishAdvertising);
            },
            changeStatus: function (url){
                var self = this;
                if(self.checkList.length == 0){
                    dialog.error("请选择需修改的广告!");
                    return false;
                }
                ajax(url,
                    {
                        ids:self.checkList
                    },
                    function (resp) {
                        if(resp.status == "1"){
                            dialog.success("修改成功!");
                            self.initPage();
                            self.checkList=[];
                        }else{
                            dialog.error(resp.msg);
                        }
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
            leftMove: function (){
                this.move(url.advertisingLeftMoveOne);
            },
            rightMove: function (){
                this.move(url.advertisingRightMoveOne);
            },
            move: function (url){
                var self = this;
                if(self.checkList.length == 0){
                    dialog.error("请选择一张广告!");
                    return false;
                }
                if(self.checkList.length > 1){
                    dialog.error("请勿选择多张广告进行移动!");
                    return false;
                }
                var moveId=self.checkList[0];

                var canMove=false;
                $.each(self.list, function(i) {
                    if(self.list[i].id == moveId && self.list[i].status == '1'){
                        canMove=true;
                    }
                });
                if(canMove == true){
                    ajax(url,
                        {
                            id:moveId
                        },
                        function (resp) {
                            if(resp.status == "1"){
                                dialog.success("移动成功!");
                                self.initList();
                            }else{
                                dialog.error(resp.msg);
                            }
                        },
                        function (err) {
                            dialog.error(err);
                        }
                    );
                }else{
                    dialog.error("仅可移动已发布的广告!");
                }
            },
            remove: function (){
                var self = this;
                if(self.checkList.length == 0){
                    dialog.error("请选择需删除的广告!");
                    return false;
                }
                ajax(url.removeAdvertising,
                    {
                        ids:self.checkList
                    },
                    function (resp) {
                        if(resp.status == "1"){
                            dialog.success("删除成功!");
                            self.viewIndex=0;
                            self.checkList=[];
                            self.initList();
                        }else{
                            dialog.error(resp.msg);
                        }
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            beforeUpload: function(file){
                var isPic = file.type == 'image/png' || file.type == 'image/jpg' || file.type == 'image/jpeg';
                if (!isPic) {
                    dialog.error("图片类型不是png, jpg, jpeg");
                    return false;
                }
                var isLt300K = file.size/1024<500;
                if (!isLt300K) {
                    dialog.error("图片大小不能超过500K");
                    return false;
                }
                dialog.success("正在上传,请稍后!");
                return true;
            },

            successUpload: function(response,file,fileList){
                var _this=this;
                ajax(
                    url.addAdvertising,
                    {
                        imageName: file.name,
                        imagePath: encodeURI(response.url)
                    },
                    function(resp){
                        if(resp.status == "1"){
                            dialog.success("新增成功!");
                            _this.initList();
                        }else{
                            dialog.error(resp.msg);
                        }
                    },
                    function(err){
                        dialog.error(err);
                    }
                );
            },

            errorUpload: function(err,file,fileList){
                dialog.error("上传失败");
            }

        },

    })
})();