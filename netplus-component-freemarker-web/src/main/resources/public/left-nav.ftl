<div class="nav-box nav side-navbar of" id="leftNav" v-cloak>
    <ul class="nav">
        <li v-for="m in menus" :class="{'curr': m.isUp}">

            <a @click="isUp(m)" class="nav-link">

                <p class="fl">
                    <span class="tc disib">
                        <i class="iconfont mr5" :class="m.icon"></i>
                    </span>
                    <em>{{m.name}}</em>
                </p>

                <i class="fr iconfont f12 mr10" v-bind:class="{'icon-up': m.isUp, 'icon-down': !m.isUp}"></i>
            </a>

            <ul>
                <li v-for="mm in m.childmenulist" :class="{'curr': mm.code==code}" >
                    <a :href="mm.url">{{mm.name}}</a>
                </li>
            </ul>
        </li>
    </ul>
</div>

<script>

    var leftNav = new Vue({
        el: "#leftNav",
        data: {
            code: "",
            menus: []
        },

        mounted: function(){
            var _this = this;
            _this.code = PAGE_PRIVILEGE_CODE;
            _this.getUserMenuList();
        },

        methods: {

            getUserMenuList: function(){
                var _this = this;

                ajax("/api/provider/getUserMenuList/", {belongto: "PC"}, function(json){


                    $.each(json, function(i, d){

                        $.each(d.childmenulist, function(ii, dd){

                            $.each(dd.childmenulist, function(iii, ddd){

                                if (ddd.code == _this.code) {

                                    dd.isUp = true;
                                    _this.menus = d.childmenulist;

                                    return false;
                                }
                            })

                        })

                    })

                }, function(err){
                    dialog.error(err);
                });
            },

            isUp: function(m){
                m.isUp = !m.isUp;
                this.$forceUpdate()
            }

        }
    });
</script>



