package netplus.mall.service.dao;

import netplus.mall.api.pojo.ygmalluser.Goodscommentimg;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface GoodscommentimgMapper {
    int insert(Goodscommentimg record);

    int insertSelective(Goodscommentimg record);

    int batchInsertImg(List<Goodscommentimg> imgList);
}