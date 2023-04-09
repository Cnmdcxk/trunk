package netplus.mall.service.dao;

import netplus.mall.api.pojo.ygmalluser.Goodscomment;
import netplus.mall.api.pojo.ygmalluser.GoodscommentKey;
import netplus.mall.api.request.goodsComment.GetCommentsByGoodsInfoRequest;
import netplus.mall.api.vo.CommentLevelCountVo;
import netplus.mall.api.vo.GoodsCommentVo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface GoodscommentMapper {
    int deleteByPrimaryKey(GoodscommentKey key);

    int insert(Goodscomment record);

    int insertSelective(Goodscomment record);

    Goodscomment selectByPrimaryKey(GoodscommentKey key);

    int updateByPrimaryKeySelective(Goodscomment record);

    int updateByPrimaryKey(Goodscomment record);

    GoodsCommentVo getCommentByOrderInfo(@Param("orderNo") String orderNo, @Param("orderDetailNo") String orderDetailNo);

    List<GoodsCommentVo> getCommentsPageByGoodsInfo(GetCommentsByGoodsInfoRequest request);

    List<CommentLevelCountVo> getCommentsCountByGoodsInfo(GetCommentsByGoodsInfoRequest request);
}