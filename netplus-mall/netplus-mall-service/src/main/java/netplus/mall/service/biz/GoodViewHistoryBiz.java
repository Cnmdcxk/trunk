package netplus.mall.service.biz;

import netplus.component.entity.exceptions.NetPlusException;
import netplus.mall.api.pojo.ygmalluser.Tbmqq437;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq437Mapper;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GoodViewHistoryBiz {

    @Autowired
    Tbmqq437Mapper tbmqq437Mapper;


    @Autowired
    IdentityRest identityRest;

    @Autowired
    Tbmqq430Mapper tbmqq430Mapper;

    public List<Tbmqq437> getUserViewHistory () {
        return tbmqq437Mapper.getUserViewHistory(identityRest.getCurrentUser().getUserCode());
    }

    public void addViewHistory (String goodId) throws NetPlusException {
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = new HashMap();
        mapParam.put("goodId", goodId);
        mapParam.put("userNo", loginUserBean.getUserCode());
        int count = tbmqq437Mapper.getViewHistoryCountByGoodId(mapParam);

        if (count <= 0) {
            GoodsVo goodsVo = tbmqq430Mapper.getGoodsById(goodId);

            if(goodsVo == null) {
                throw new NetPlusException("商品信息[" + goodId + "]不存在！");
            }

            Tbmqq437 tbmqq437 = new Tbmqq437();
            BeanUtils.copyProperties(goodsVo, tbmqq437);
            tbmqq437.setUserno(loginUserBean.getUserCode());

            tbmqq437Mapper.insertSelective(tbmqq437);
        }
    }
}
