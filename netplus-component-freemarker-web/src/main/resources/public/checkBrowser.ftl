<div id="checkBrowser" class="bg_f3f3f3">
    <el-dialog
            :visible.sync="checkBrowserDialog"
            :close-on-click-modal="false"
            width="520px"
            class="browser_low"
            center>
        <div class="tc pb30 mt80">
            <div class="f20 fb mb10">您当前的浏览器版本过低，需更换浏览器。</div>
            <div class="mb20">为了享受更好的体验，建议您安装使用这些浏览器</div>
            <div>
                <a class="" href="https://www.google.cn/chrome/" target="_blank"><img src="${cdn}/img/home/btn-chrome.png" alt="" class="img"></a>
                <a class="ml20" href="http://www.firefox.com.cn/download/" target="_blank"><img src="${cdn}/img/home/btn-firefox.png" alt="" class="img"></a>
                <a class="ml20" href="https://pc.qq.com/detail/14/detail_12094.html" target="_blank"><img src="${cdn}/img/home/btn-ie.png" alt="" class="img"></a>
            </div>
        </div>
    </el-dialog>

</div>
<script type="text/javascript">

    (function() {
        new Vue({
            el: '#checkBrowser',
            data: {
                checkBrowserDialog: false
            },
            mounted:function(){
                this.checkBrowser();
            },
            methods:{
                checkBrowser: function() {
                    var self = this;
                    var browser = self.getBrowser().browser;
                    var version = self.getBrowser().version;
                    console.log("浏览器版本："+browser+version);
                    if (browser == 'IE' && version < 10){
                        self.checkBrowserDialog = true;
                    }
                },

                getBrowser: function() {
                    var sys = {};
                    var ua = navigator.userAgent.toLowerCase();
                    var s;
                    (s = ua.match(/edge\/([\d.]+)/))
                            ? (sys.edge = s[1])
                            : (s = ua.match(/rv:([\d.]+)\) like gecko/))
                            ? (sys.ie = s[1])
                            : (s = ua.match(/msie ([\d.]+)/))
                            ? (sys.ie = s[1])
                            : (s = ua.match(/firefox\/([\d.]+)/))
                            ? (sys.firefox = s[1])
                            : (s = ua.match(/chrome\/([\d.]+)/))
                            ? (sys.chrome = s[1])
                            : (s = ua.match(/opera.([\d.]+)/))
                            ? (sys.opera = s[1])
                            : (s = ua.match(/version\/([\d.]+).*safari/))
                            ? (sys.safari = s[1])
                            : 0;

                    if (sys.edge) return { browser: 'Edge', version: sys.edge };
                    if (sys.ie) return { browser: 'IE', version: sys.ie };
                    if (sys.firefox) return { browser: 'Firefox', version: sys.firefox };
                    if (sys.chrome) return { browser: 'Chrome', version: sys.chrome };
                    if (sys.opera) return { browser: 'Opera', version: sys.opera };
                    if (sys.safari) return { browser: 'Safari', version: sys.safari };

                    return { browser: '', version: '0' };
                }
            }
        });

    })();

</script>

