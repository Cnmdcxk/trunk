(function () {
    var RZ = new Vue({
        el:'#RZ',
        data:{
            supplierCode:'',
            checked:true,
            value:'',
            value2:'',
            value3:''
        },
        created: function() {
            this.supplierCode = $('#supplierCode').val();
        },
        mounted:function(){
        },
        methods:{
        }
    })
})();