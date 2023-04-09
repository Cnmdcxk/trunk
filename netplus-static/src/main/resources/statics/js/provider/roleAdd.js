(function (win) {

    var url = {
        getListPermissions: "/api/provider/getListPermissions",
    }
    var app = new Vue({
    el: '#RZ',
    data: function() {
        return {
            subitem:{
                isCheck:false
            },
            id:'',
            centerDialogVisible1: false,
            centerDialogVisible2: false,
            centerDialogVisible3: false,
            checked: false,
            addVisible: false,
            value: '',
            centerDialogVisible: false,
            tableData: [],
            parent:[],
            children:[],
            multipleSelection: [],
            ruleForm: {
                name: '',
                spec: ''
            },
            rules: {
                name: [{
                    required: true,
                    message: ''
                }]
            }
        }

    },
        created: function() {
            var param = {};
            this.search();
        },
        // mounted:function(){
        //     var param = {};
        //     this.search();
        // },
    methods: {
        search: function() {
            this.searchPermissions()
        },
        mounted:function(){
            var param = {};
           // this.searchPermissions()
        },
        doSearch: function() {

        },
        searchPermissions:function (){
            var self = this;
            self.param={

            }
            ajax(
                url.getListPermissions,
                self.param,
                function (resp) {
                   debugger;
                    console.log(resp);
                    self.tableData = resp;
                },
                function (err) {
                    dialog.error(err);
                }
            );
        }
    },
        changeSelect: function(subitem) {
            var self = this;


        },

})
    win.app = app;
})(window);