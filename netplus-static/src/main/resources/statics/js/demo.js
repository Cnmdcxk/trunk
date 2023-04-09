$(document).ready(function(){
    // 点击右侧滚动效果
    $(".rigth_scroll li").click(function(){
        console.log($(this).index());
        $(this).addClass('curr').siblings().removeClass("curr");
         //获取当前楼号对应楼层的 top值
        var sTop = $(".bg_blue_title").eq($(this).index()).offset().top - 50;

        //将页面滚走的距离设置为  sTop  
        $("body,html").animate({
            "scrollTop": sTop
        }, 500, function() {
            flag = true;
        });
    });

    $(window).scroll(function(){
        var $st=$(window).scrollTop();//获取滚动条滚动的距离
        $(".bg_blue_title").each(function(i,dom){
            var $offT=$(this).offset().top-50;//获得任何一个step的向上偏移距离
            if($st>=$offT){
                $(".rigth_scroll li").eq(i).addClass("curr").siblings().removeClass("curr");
            }
        })
    })
});