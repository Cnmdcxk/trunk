<div id="videoTop">
    <el-dialog
            :visible.sync="centerDialogVisible"
            :close-on-click-modal="false"
            width="346px"
            class="video-animation animated shake"
            center>
        <div class="c_fff tc">
            <div class="f16 pt20">您收到{{projectno}}项目视频讲标会议邀请！</div>
            <div class="ani-box">
                <i class="iconfont icon-shipin"></i>
                <div class="point"></div>
            </div>
            <a class="join" target="_blank" :href=" '/video/trtc?projectno=' + projectno ">立即加入</a>
        </div>
    </el-dialog>

</div>
<script type="text/javascript" src="${cdn}/js/pricing/ws.js?v=${ver}"></script>
<script type="text/javascript">

    (function() {
        new Vue({
            el: '#videoTop',
            data: {
                centerDialogVisible:false,
                companycode: '${companyCode!}',
                projectno: null
            },
            mounted:function(){
                if(typeof (this.companycode) === 'undefined'
                    || this.companycode == null
                    || this.companycode == ''){
                    return;
                }
                var self = this;
                createWebSocket(getWsHost() + '/api/ws/video/' + self.companycode, function(data){
                    var respData = JSON.parse(data);
                    if (respData.msgType == 'call') {
                        self.projectno = respData.projectNo;
                        self.centerDialogVisible = true;
                    }
                    else if (respData.msgType == 'shutdown') {
                        self.centerDialogVisible = false;


                        console.log(leave);

                        // 如果在视频主页面，需要关闭当前视频
                        if (typeof (leave) === 'function') {
                            dialog.warning('主持人已终止视频，页面即将关闭。', function() {
                                window.close();
                            });
                        }
                    }
                    else if (respData.msgType == 'connected') {
                        self.centerDialogVisible = false;
                    }
                });
            },
            methods:{

            },
            destroyed: function() {
                //页面销毁时关闭
                onbeforeunload();
            },
        });

    })();

</script>

