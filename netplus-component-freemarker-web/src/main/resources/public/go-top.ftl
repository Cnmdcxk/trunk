<div class="go-home">
    <div class="lh30 pt5">
        <span class="iconfont icon-dingbu f26"></span>
    </div>
    <div class="f12">回到顶部</div>
<div>

<script type="text/javascript">
    $(window).scroll(function(){
        if ($(window).scrollTop()>400){
            $(".go-home").fadeIn(500);
        }else{
            $(".go-home").fadeOut(500);
        }
    });
    $(".go-home").click(function(){
        $('body,html').animate({scrollTop:0},100);
        return false;
    });
</script>