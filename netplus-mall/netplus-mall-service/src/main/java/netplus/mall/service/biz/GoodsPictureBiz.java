package netplus.mall.service.biz;

import netplus.component.entity.exceptions.NetPlusException;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;
import netplus.mall.api.request.GoodsPictureListRequest;
import netplus.mall.api.vo.Tbmqq435Vo;
import netplus.mall.service.dao.Tbmqq435Mapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.List;

@Service
public class GoodsPictureBiz {

    @Resource
    private Tbmqq435Mapper Tbmqq435Mapper;


    /**
     * 查询商品组列表
     *
     * @param request
     * @return
     */
    public List<Tbmqq435Vo> getGoodPicList(GoodsPictureListRequest request) {

        if (StringUtils.isEmpty(request.getGoodId())) {
            throw new NetPlusException("商品id不能为空");
        }


        List<Tbmqq435Vo> tbmqq435VoList = Tbmqq435Mapper.getGoodPicList(request.getGoodId());
        return tbmqq435VoList;
    }


    /**
     * 添加商品图片
     *
     * @param request
     * @return
     */
    @Transactional
    public int addGoodsPictureList(List<Tbmqq435> request) {
        return Tbmqq435Mapper.batchInsert(request);
    }
}