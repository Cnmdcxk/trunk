package netplus.mall.service.controller;

import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.Urls;
import netplus.mall.api.request.goodsComment.AddGoodsCommentRequest;
import netplus.mall.api.request.goodsComment.GetCommentByOrderInfoRequest;
import netplus.mall.api.request.goodsComment.GetCommentsByGoodsInfoRequest;
import netplus.mall.service.biz.GoodsCommentBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GoodsCommentController {

    @Autowired
    GoodsCommentBiz goodsCommentBiz;

    @PostMapping(Urls.GoodsComment.createComment)
    public ApiResponse createComment(@RequestBody AddGoodsCommentRequest request){
        return goodsCommentBiz.createComment(request);
    }

    @PostMapping(Urls.GoodsComment.getCommentByOrderInfo)
    public ApiResponse getCommentByOrderInfo(@RequestBody GetCommentByOrderInfoRequest request){
        return goodsCommentBiz.getCommentByOrderInfo(request);
    }

    @PostMapping(Urls.GoodsComment.getCommentsByGoodsInfo)
    public ApiResponse getCommentsByGoodsInfo(@RequestBody GetCommentsByGoodsInfoRequest request){
        return goodsCommentBiz.getCommentsByGoodsInfo(request);
    }
}
