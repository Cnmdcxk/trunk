package netplus.mall.service.biz;

import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.enums.CommentLevelEnum;
import netplus.mall.api.pojo.ygmalluser.Goodscomment;
import netplus.mall.api.pojo.ygmalluser.Goodscommentimg;
import netplus.mall.api.pojo.ygmalluser.Tbmqq441;
import netplus.mall.api.pojo.ygmalluser.Tbmqq441Key;
import netplus.mall.api.request.goodsComment.AddGoodsCommentRequest;
import netplus.mall.api.request.goodsComment.GetCommentByOrderInfoRequest;
import netplus.mall.api.request.goodsComment.GetCommentsByGoodsInfoRequest;
import netplus.mall.api.vo.CommentLevelCountVo;
import netplus.mall.api.vo.GoodsCommentVo;
import netplus.mall.service.dao.GoodscommentMapper;
import netplus.mall.service.dao.GoodscommentimgMapper;
import netplus.mall.service.dao.Tbmqq441Mapper;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.date.NowDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodsCommentBiz {

    @Autowired
    IdentityRest identityRest;

    @Autowired
    GoodscommentMapper goodscommentMapper;

    @Autowired
    GoodscommentimgMapper goodscommentimgMapper;

    @Autowired
    Tbmqq441Mapper tbmqq441Mapper;

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse createComment(AddGoodsCommentRequest request) {
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        String orderNo = request.getOrderNo();
        if(StringUtils.isEmpty(orderNo)){
            throw new NetPlusException("订单号不能为空!");
        }

        String orderDetailNo = request.getOrderDetailNo();
        if(StringUtils.isEmpty(orderDetailNo)){
            throw new NetPlusException("订单明细ID不能为空!");
        }

        Integer score = request.getScore();
        if(score ==null){
            throw new NetPlusException("评分不能为空!");
        }

        List<String> imgUrl = request.getImgUrl();
        if(ObjectUtils.isEmpty(imgUrl)){
            throw new NetPlusException("图片不能为空!");
        }

        if(imgUrl.size()>3){
            throw new NetPlusException("最多上传三张图片!");
        }

        String commentContent = request.getCommentContent();
        if(StringUtils.isEmpty(commentContent)){
            throw new NetPlusException("评价内容不能为空!");
        }

        Tbmqq441Key key = new Tbmqq441Key();
        key.setOrderno(orderNo);
        key.setOrderdetailno(orderDetailNo);
        Tbmqq441 orderDetail = tbmqq441Mapper.selectByPrimaryKey(key);
        if(ObjectUtils.isEmpty(orderDetail)){
            throw new NetPlusException("订单明细不存在!");
        }

        NowDate nowDate = new NowDate();
        Goodscomment goodscomment = new Goodscomment();
        goodscomment.setGoodid(orderDetail.getGoodid());
        goodscomment.setOrderno(orderNo);
        goodscomment.setOrderdetailno(orderDetailNo);
        goodscomment.setMatrlid(orderDetail.getMatrlid());
        goodscomment.setMatrltm(orderDetail.getMatrltm());
        goodscomment.setMatrlno(orderDetail.getMatrlno());
        goodscomment.setProductname(orderDetail.getProductname());
        goodscomment.setPictureurl(orderDetail.getPictureurl());
        goodscomment.setScore(score);
        goodscomment.setCommentlevel(CommentLevelEnum.getLevelByScore(score).getCode());
        goodscomment.setCommentcontent(commentContent);
        goodscomment.setCommentuserno(loginUserBean.getUserCode());
        goodscomment.setCommentusername(loginUserBean.getUsername());
        goodscomment.setCommentdate(nowDate.getDateStr());
        goodscomment.setCommenttime(nowDate.getTimeStr());

        int insertCount = goodscommentMapper.insert(goodscomment);
        if(insertCount == 0){
            throw new NetPlusException("评价失败,请稍后重试!");
        }

        List<Goodscommentimg> imgList = new ArrayList<>();
        for (int i = 0; i < imgUrl.size(); i++) {
            Goodscommentimg img = new Goodscommentimg();
            img.setOrderno(orderNo);
            img.setOrderdetailno(orderDetailNo);
            img.setUrl(imgUrl.get(i));
            img.setSort(i);
            imgList.add(img);
        }

        int imgInsertCount=goodscommentimgMapper.batchInsertImg(imgList);
        if(imgInsertCount == 0){
            throw new NetPlusException("评价失败,请稍后重试!");
        }
        return ApiResponse.success();
    }

    public ApiResponse getCommentByOrderInfo(GetCommentByOrderInfoRequest request) {
        String orderNo = request.getOrderNo();
        if(StringUtils.isEmpty(orderNo)){
            throw new NetPlusException("订单号不能为空!");
        }

        String orderDetailNo = request.getOrderDetailNo();
        if(StringUtils.isEmpty(orderDetailNo)){
            throw new NetPlusException("订单明细ID不能为空!");
        }

        GoodsCommentVo vo = goodscommentMapper.getCommentByOrderInfo(orderNo,orderDetailNo);
        return ApiResponse.success(vo);
    }

    public ApiResponse getCommentsByGoodsInfo(GetCommentsByGoodsInfoRequest request) {
        if(StringUtils.isEmpty(request.getGoodId()) && StringUtils.isEmpty(request.getMaterialTm()) && StringUtils.isEmpty(request.getMaterialNo())){
            throw new NetPlusException("商品ID、物料条码、物料号不能全为空!");
        }
        PageBean<GoodsCommentVo> pageBean = new PageBean();
        List<GoodsCommentVo> vos = goodscommentMapper.getCommentsPageByGoodsInfo(request);
        List<CommentLevelCountVo> countList = goodscommentMapper.getCommentsCountByGoodsInfo(request);
        Map<String, Integer> countInfo = countList.stream().collect(Collectors.toMap(CommentLevelCountVo::getCommentLevel,CommentLevelCountVo::getTotalCount,(c1,c2) -> c1+c2));
        int count = countInfo.values().stream().mapToInt(t -> t).sum();
        countInfo.put("all",count);

        if(request.getCommentLevel() != null){
            Integer countInteger = countInfo.get(request.getCommentLevel().toString());
            count= countInteger == null ? 0 : countInteger;
        }

        pageBean.setItems(vos);
        pageBean.setTotalCount(count);
        pageBean.addResultMap("countInfo",countInfo);
        return ApiResponse.success(pageBean);
    }
}
