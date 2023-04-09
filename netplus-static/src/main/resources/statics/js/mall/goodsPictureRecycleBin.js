(function(win){

    var url = {
        getPicLibList:"/api/mall/picLib/getPicLibList/",
        updatePicLib:"/api/mall/picLib/updatePicLib/",
        delPicLib: "/api/mall/picLib/delPicLib/",
    };


    var app = new Vue({
        el: '#RZ',
        data: {

            count:0,
            searchKey: '',

            isSelectedAll: false,

            tableData: [],
            cz:""
        },

        mounted: function () {
            this.search();
        },
        methods: {

            search: function () {
                var self = this;
                var param = {
                    searchKey: self.searchKey,
                    deleted: "Y"
                };

                ajax(
                    url.getPicLibList,
                    param,
                    function (resp) {
                        self.tableData = resp;
                        self.watchCount();
                        console.log(self.count);
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            removePic: function () {
                var self = this;
                var param = self.getSelectedData();

                ajax(
                    url.delPicLib,
                    {
                        cz:"B",
                        tbmqq429List: param
                    },
                    function (resp) {
                        dialog.success("删除成功", function(){
                            self.search();
                        });
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },

            back: function(){
                var self = this;
                var param = self.getSelectedData();

                $.each(param, function(i, d){
                    d.deleted = "N";
                })

                ajax(
                    url.updatePicLib,
                    {
                        tbmqq429List: param
                    },
                    function (resp) {
                        dialog.success("恢复成功", function(){
                            self.search();
                        });
                    },
                    function (err) {
                        dialog.error(err);
                    }
                );
            },
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
                _this.count=count;
                this.isSelectedAll=false;
            },
            getSelectedData: function(){
                var _this = this;
                var data = [];

                $.each(_this.tableData, function(i, d){

                    $.each(d.tbmqq429VoList, function(ii, dd){
                        if (dd.checked) {
                            data.push({
                                picturenum: dd.picturenum,
                                picturetype: dd.picturetype,
                                matrltm: dd.matrltm,
                                deleted: dd.deleted
                            })
                        }
                    })

                })

                return data;
            },
            toPreview: function(pictureurl) {
                formForward(pictureurl, {}, "_blank", "get");
            },
        },


        watch: {
            isSelectedAll: function(o, n) {
                var _this = this;
                 var count= 0;
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
            }
        }
    });

    win.app = app;

})(window||{})