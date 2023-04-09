package netplus.mall.service.biz;

import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Tbmqq434;
import netplus.mall.api.pojo.ygmalluser.Tbmqq434Key;
import netplus.mall.api.request.goodCollect.*;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.goodCollect.GoodCollectVo;
import netplus.mall.api.vo.shoppingCart.Tbmqq433Vo;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq433Mapper;
import netplus.mall.service.dao.Tbmqq434Mapper;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class GoodCollectBiz {

    @Autowired
    Tbmqq434Mapper tbmqq434Mapper;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    Tbmqq433Mapper tbmqq433Mapper;

    @Transactional(rollbackFor = Exception.class)
    public void delMyCollect(DelMyCollectRequest request) {
        List<String> ids = request.getGoodIds();
        if(ids == null || ids.size()==0){
            throw new NetPlusException("商品ID不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userNo", loginUserBean.getUserCode());

        int delCount = tbmqq434Mapper.delMyCollect(mapParam);
        if (delCount != ids.size()) {
            throw new NetPlusException("删除失败");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void addMyCollect(AddMyCollectRequest request) {

        if (StringUtils.isEmpty(request.getGoodId())) {
            throw new NetPlusException("商品id不能为空");
        }


        GoodsVo goodsVo = tbmqq430Mapper.getGoodsById(request.getGoodId());
        if (ObjectUtil.isEmpty(goodsVo) || !GoodsStatusEnum.isShelvesCode(goodsVo.getStatus())) {
            throw new NetPlusException("商品id不存在或已下架");
        }


        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        NowDate nowDate = new NowDate();


        String remark=null;
        if ("Y".equals(request.getIsDelCart())) {
            Map<String, Object> mapParam = new HashMap();
            mapParam.put("userno", loginUserBean.getUserCode());
            mapParam.put("goodIdList", Arrays.asList(request.getGoodId()));
            List<Tbmqq433Vo> shoppingCartRemarkList = tbmqq433Mapper.getMyShoppingCartRemark(mapParam);
            Map<String, String> remarkMap = shoppingCartRemarkList.stream().collect(Collectors.toMap(Tbmqq433Vo::getGoodid, t -> StringUtils.isEmpty(t.getRemark())?"":t.getRemark(), (t1, t2) -> t1));
            remark=remarkMap.get(request.getGoodId());
            int delCount = tbmqq433Mapper.delMyShoppingCart(mapParam);
            if (delCount != 1) {
                throw new NetPlusException("购物车商品删除失败");
            }
        }
        Tbmqq434 tbmqq434 = createTbmqq434(loginUserBean, nowDate, goodsVo, remark);
        if (!ObjectUtil.isEmpty(tbmqq434Mapper.selectByPrimaryKey(tbmqq434))) {
            throw new NetPlusException("此商品已收藏!");
        }
        tbmqq434Mapper.insertSelective(tbmqq434);
    }

    public PageBean<GoodCollectVo> getMyCollectList (GetMyCollectListRequest request) {

        PageBean<GoodCollectVo> pageBean = new PageBean();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userNo", loginUserBean.getUserCode());

        List<GoodCollectVo> goodCollectVoList = tbmqq434Mapper.getMyCollectList(mapParam).stream().map(e -> {
            e.setIsFailure(GoodsStatusEnum.isShelvesCode(e.getStatus()) ? "N" : "Y");
            return e;
        }).collect(Collectors.toList());
        int count = tbmqq434Mapper.getCount(mapParam);
        List<KeyValue> categoryNameList = tbmqq434Mapper.getCategoryNameKV(mapParam);

        pageBean.setItems(goodCollectVoList);
        pageBean.setTotalCount(count);
        pageBean.addResultMap("categoryNameList", categoryNameList);

        return pageBean;
    }

    public int getMyCollectCount() {
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = new HashMap<>();
        mapParam.put("userNo", loginUserBean.getUserCode());
        return tbmqq434Mapper.getCount(mapParam);
    }

    @Transactional(rollbackFor = Exception.class)
    public void batchAddMyCollect(BatchAddMyCollectRequest request) {

        if (ObjectUtils.isEmpty(request.getGoodIds())) {
            throw new NetPlusException("商品id不能为空");
        }

        Map<String,Object> param = new HashMap<>();
        param.put("goodIdList",request.getGoodIds());
        List<GoodsVo> goodsVos = tbmqq430Mapper.getGoodByIds(param);
        if (ObjectUtil.isEmpty(goodsVos) || goodsVos.size()<request.getGoodIds().size()) {
            throw new NetPlusException("商品id不存在");
        }

        long errorCount = goodsVos.stream().filter(e -> !GoodsStatusEnum.isShelvesCode(e.getStatus())).count();

        if (errorCount>0) {
            throw new NetPlusException("商品已下架");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        param.put("userNo", loginUserBean.getUserCode());
        List<String> myCollectRepeatGoodsIds = tbmqq434Mapper.getMyCollectListByGoodsIds(param).stream()
                .map(GoodCollectVo::getGoodid)
                .collect(Collectors.toList());

        Map<String, Object> mapParam = new HashMap();
        mapParam.put("userno", loginUserBean.getUserCode());
        mapParam.put("goodIdList", request.getGoodIds());
        List<Tbmqq433Vo> shoppingCartRemarkList = tbmqq433Mapper.getMyShoppingCartRemark(mapParam);
        Map<String, String> remarkMap = shoppingCartRemarkList.stream().collect(Collectors.toMap(Tbmqq433Vo::getGoodid, t -> StringUtils.isEmpty(t.getRemark())?"":t.getRemark(), (t1, t2) -> t1));


        NowDate nowDate = new NowDate();
        List<Tbmqq434> tbmqq434List = goodsVos.stream()
                .filter(goodsVo -> !myCollectRepeatGoodsIds.contains(goodsVo.getGoodid()))
                .map(goodsVo -> createTbmqq434(loginUserBean, nowDate, goodsVo, remarkMap.get(goodsVo.getGoodid())))
                .collect(Collectors.toList());
        if(!ObjectUtils.isEmpty(tbmqq434List)){
            tbmqq434Mapper.batchInsert(tbmqq434List);
        }

        if ("Y".equals(request.getIsDelCart())) {
            int delCount = tbmqq433Mapper.delMyShoppingCart(mapParam);
            if (delCount != request.getGoodIds().size()) {
                throw new NetPlusException("购物车商品删除失败");
            }
        }
    }

    @NotNull
    private Tbmqq434 createTbmqq434(LoginUserBean loginUserBean, NowDate nowDate, GoodsVo goodsVo,String remark) {
        Tbmqq434 tbmqq434 = new Tbmqq434();
        BeanUtils.copyProperties(goodsVo, tbmqq434);
        tbmqq434.setReferdeliverydate(goodsVo.getLeadtimenum());

        tbmqq434.setUserno(loginUserBean.getUserCode());
        tbmqq434.setUsername(loginUserBean.getUsername());
        tbmqq434.setCreatetime(nowDate.getTimeStr());
        tbmqq434.setCreatedate(nowDate.getDateStr());
        tbmqq434.setCreateuser(loginUserBean.getUserCode());
        tbmqq434.setUpdateuser(loginUserBean.getUserCode());
        tbmqq434.setUpdatedate(nowDate.getDateStr());
        tbmqq434.setUpdatetime(nowDate.getTimeStr());
        tbmqq434.setRemark(remark);

        return tbmqq434;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCollectRemark(UpdateCollectRemarkRequest request) {
        if(StringUtils.isEmpty(request.getGoodId())){
            throw new NetPlusException("商品ID不能为空!");
        }
        if(!StringUtils.isEmpty(request.getRemark()) && request.getRemark().length()>500){
            throw new NetPlusException("备注超过上限!");
        }
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userNo", loginUserBean.getUserCode());
        tbmqq434Mapper.updateCollectRemark(mapParam);
        tbmqq433Mapper.updateShoppingCartRemark(mapParam);
    }
}
