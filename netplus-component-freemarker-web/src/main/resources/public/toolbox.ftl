<div id="toolbox" class="rz-toolbar" v-cloak>
    <ul>
        <li v-if="role == 'B'">
            <div class="title" @click="goShoppingCart" id="car">
                <div class="lh30"><i class="iconfont icon-gouwuche f26"></i></div>
                <div>购物车</div>
                <span class="el-badge__content pa" style="top: 10px; right:5px;">{{shoppingCartCount}}</span>
            </div>
            <div class="left_title">
                <div>购物车</div>
            </div>
        </li>

        <li v-if="role == 'B'">
            <div class="title" @click="goFavorites">
                <div class="lh30"><i class="iconfont icon-shoucangjia f26"></i></div>
                <div>收藏夹</div>
                <span class="el-badge__content pa" style="top: 10px; right:5px;">{{collectCount}}</span>
            </div>
            <div class="left_title">
                <div>收藏夹</div>
            </div>
        </li>

        <li v-if="role != null && role != ''">
            <div class="title" @click="gotoMessageBox();">
                <div class="lh30"><i class="iconfont icon-xiaoxi f26"></i></div>
                <div>消息中心</div>
                <span class="el-badge__content pa" style="top: 10px; right:5px;">{{countMessage}}</span>
            </div>
            <div class="left_title">
                消息中心
            </div>
        </li>
        <li v-if="role==null||role==''">
            <div class="title">
                <div class="lh30"><i class="iconfont icon-yidongduan f26"></i></div>
                <div>移动端</div>
            </div>
            <div class="left_title">
                <img src="${cdn}/img/home/foot_3.png" alt="" width="150px" height="150px">
                <div class="tc">请用手机扫码下载客商APP</div>
            </div>
        </li>



        <!--
        <li v-if="scrollTop > 500" @click="gotoTop();">
            <div class="title">
                <div class="lh30"><i class="iconfont icon-dingbu f26"></i></div>
                <div>回到顶部</div>
            </div>
            <div class="left_title">
                回到顶部
            </div>
        </li>
        -->
    </ul>

    <!-- 内容区域 -->
    <div class="bg_f3f3f3">

        <!-- 意见箱 -->
        <el-dialog
                :visible.sync="centerDialogVisible"
                :close-on-click-modal="false"
                width="660px"
                class="opinion-dialog"
                center>
            <div class="">
                <div class="pt20 tc pb20 f18 fb" style="letter-spacing: 15px;">意见箱</div>
                <div class="w570 ml20">
                    <div class="h140 lh28">
                        <div>尊敬的用户：</div>
                        <div style="text-indent: 20px;">对平台操作还满意吗？</div>
                        <div style="text-indent: 20px;">为了向您提供更好的服务，我们希望收集您的宝贵意见，收到反馈后我们会及时联系您，谢谢支持</div>

                    </div>
                    <div class="">
                        <div class="mb15">
                            <span class="c_999">姓名：</span>
                            <input type="text" class="w170" v-model="addDialogData.name">
                            <span class="c_999" style="margin-left: 91px;">联系方式：</span>
                            <input type="text" class="w170" v-model="addDialogData.phoneContact">
                        </div>
                        <textarea placeholder="请留下您宝贵的意见" style="width: 560px;height: 85px;" v-model="addDialogData.suggestion"></textarea>
                    </div>
                </div>
            </div>
            <span slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submit()">提交</el-button>
            </span>
        </el-dialog>
    </div>
</div>

<script type="text/javascript">

    var toolboxVue = new Vue({
        el: '#toolbox',
        data: {
            frameUrl:'',
            scrollTop: 0,
            shoppingCartCount: 0,
            centerDialogVisible: false,
            countMessage:0,
            collectCount:0,
            addDialogData:{},
            role: role
        },
        mounted:function(){
            if (IS_LOGIN) {
                this.getAllCount();
                this.getShoppingCartCount();
                this.getCollectCount();
            }
        },
        methods: {

            getShoppingCartCount: function(){
                var _this = this;
                ajax("/api/mall/getShoppingCartCount/", {}, function(resp){
                    _this.shoppingCartCount = resp;
                })
            },

            goShoppingCart: function () {
                formForward("/mall/shoppingCart", {}, "_blank", "get");
            },

            goFavorites: function () {
                formForward("/mall/favorites", {}, "_blank", "get");
            },

            gotoMessageBox: function () {
                window.location.href = '/messaging/index';
            },

            showSuggestBox: function(){
                this.centerDialogVisible = true;
                this.addDialogData.name =  this.addDialogData.phoneContact = this.addDialogData.suggestion = '';
            },
            gotoTop: function () {
                $('html,body').animate({scrollTop: '0px'}, 'slow');
            },
            getCollectCount:function () {
                var _this = this;
                ajax(
                    "/api/mall/goodsCollect/getMyCollectList/", {},function(resp){
                        _this.collectCount = resp.totalCount;
                    })
            },
            getAllCount:function(){
                var self = this;
                ajax('/api/messaging/getAllCount/',
                        {
                        },
                        function (resp) {
                            self.countMessage = resp.sum1 + resp.sum2 + resp.sum3
                                    + resp.sum4  + resp.sum5 + resp.sum6
                                    + resp.sum7  + resp.sum8 + resp.sum9;
                        },
                        function (err) {
                        }
                );
            },
            submit:function(){
                var self = this;
                var data = this.addDialogData;
                if (IS_LOGIN) {
                    ajax('/api/mail/sendMail/',
                            {
                                userName:data.name,
                                phoneContact:data.phoneContact,
                                content:data.suggestion
                            },
                            function (resp) {
                                dialog.success('发送成功！');
                                self.centerDialogVisible = false
                            },
                            function (err) {
                                dialog.error('发送失败！');
                                self.centerDialogVisible = false
                            }
                    );
                }
            }
        }
    });

    $(window).scroll(function() {
        toolboxVue.scrollTop = $(window).scrollTop();
    });

</script>