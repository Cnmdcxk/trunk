package netplus.mall.service.biz;

import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Tbmqq431;
import netplus.mall.api.request.BatchDeleteGoodsRequest;
import netplus.mall.api.request.DeleteGoodsRequest;
import netplus.mall.api.request.GetGoodsGroupListRequest;
import netplus.mall.api.request.GetGoodsListedRequest;
import netplus.mall.api.request.goodGroup.CreateGroupRequest;
import netplus.mall.api.vo.GoodsGroupVo;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq431Mapper;
import netplus.mall.service.dao.Tbmqq436Mapper;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.serial.api.request.SerialParam;
import netplus.serial.api.rest.SerialRest;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class GoodsGroupBiz {

    @Resource
    Tbmqq431Mapper tbmqq431Mapper;

    @Resource
    Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    SerialRest serialRest;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    Tbmqq436Mapper tbmqq436Mapper;

    /**
     * 查询商品组列表
     *
     * @param request
     * @return
     */
    public PageBean getGoodsGroupList(GetGoodsGroupListRequest request) {
        PageBean<GoodsGroupVo> pageBean = new PageBean<>();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());
        List<GoodsGroupVo> goodsGroupList = tbmqq431Mapper.getGoodsGroupList(mapParam);
        int count = tbmqq431Mapper.getCount(mapParam);

        List<KeyValue> categoryNameListKV = tbmqq431Mapper.getCategoryNameListKV(mapParam);
        List<KeyValue> brandListKV = tbmqq431Mapper.getBrandListKV(mapParam);
        if(categoryNameListKV.size() == 0 && brandListKV.size() == 0){

            for (GoodsGroupVo goodsGroupVo : goodsGroupList) {
                tbmqq431Mapper.deleteGoodsGroup(goodsGroupVo.getGroupno());
            }

        }else{
            pageBean.addResultMap("categoryNameList", categoryNameListKV);
            pageBean.addResultMap("brandList", brandListKV);
        }
        pageBean.setTotalCount(count);
        pageBean.setItems(goodsGroupList);


        return pageBean;
    }


    public List<GoodsVo> getGroupGoodList (GetGoodsListedRequest request) {

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());

        List<GoodsVo> goodsVoList = tbmqq431Mapper.getGroupGoodList(mapParam);
        goodsVoList.forEach( g -> g.setStatusName(GoodsStatusEnum.getName(g.getStatus())));

        return goodsVoList;
    }

    /**
     * 删除商品组
     *
     * @param request
     * @return
     */
    public void deleteGoodsGroup(DeleteGoodsRequest request) {
        if (ObjectUtil.isEmpty(request.getGroupNo())) {
            throw new NetPlusException("商品组编号不能为空");
        } else {
            int i = tbmqq431Mapper.deleteGoodsGroup(request.getGroupNo());
            int j = tbmqq430Mapper.updateGroupNo(request.getGroupNo());
            if (i != 1 || j == 0) {
                throw new NetPlusException("删除商品组失败");
            }
        }
    }

    /**
     * 删除商品组中商品
     *
     * @param request
     * @return
     */

    @Transactional(rollbackFor = Exception.class)
    public void deleteGoodsFromGroup(DeleteGoodsRequest request) {

        if (ObjectUtil.isEmpty(request.getGoodid())) {
            throw new NetPlusException("商品id或商品组id不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());

        GoodsVo updateGood =  new GoodsVo();
        updateGood.setGoodid(request.getGoodid());
        updateGood.setSupplierno(loginUserBean.getCompanyCode());
        updateGood.setGroupno("");

        int updateCount = tbmqq430Mapper.updateGoodInfo(updateGood);
        if (updateCount != 1) {
            throw new NetPlusException("删除商品的组信息失败");
        }

        List<GoodsVo> goodsList = tbmqq431Mapper.getGroupGoodList(mapParam);
        if (ObjectUtil.isEmpty(goodsList)) {
            int delCount = tbmqq431Mapper.deleteGoodsGroup(request.getGroupNo());
            if (delCount != 1) {
                throw new NetPlusException("删除商品组失败");
            }
        }

    }

    /**
     * 批量删除商品组中商品
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public void batchDeleteGoods(BatchDeleteGoodsRequest request) {
        Map<String,Object> mapParam = ObjectUtil.transBeanToMap(request);
        //将前端获取的值拼接成字符串，传给后端
        String groupno = request.getGroupno();
        //通过split方法分割
        String[] split = groupno.split(",");
        //Arrays.asList方法转换成List集合
        List<String> groupnoList = Arrays.asList(split);
        mapParam.put("list",groupnoList);
        if (ObjectUtil.isEmpty(groupno)) {
            throw new NetPlusException("商品id不能为空");
        } else {
            int i = tbmqq430Mapper.countIds(mapParam);
            int i1 = tbmqq430Mapper.updateGroupNoByIds(groupnoList);
            System.out.println(i);
            if (i != i1) {
                throw new NetPlusException("删除商品失败");
            }
            int j = tbmqq431Mapper.batchDeleteGoodsGroup(groupnoList);
            if (j != groupnoList.size()) {
                throw new NetPlusException("删除商品组失败");
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void createGroup(CreateGroupRequest request){

        if (ObjectUtil.isEmpty(request.getGoodIdList())) {
            throw new NetPlusException("商品id不能为空");
        }

        NowDate nowDate = new NowDate();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getGoodByIds(mapParam);
        if (ObjectUtil.isEmpty(goodsVoList) || goodsVoList.size() != request.getGoodIdList().size()) {
            throw new NetPlusException("部分商品不存在");
        }

        for (GoodsVo g: goodsVoList) {
            if (!StringUtils.isEmpty(g.getGroupno())) {
                throw new NetPlusException(String.format("物料编码:%s的商品，已属于其他商品组", g.getMatrlno()));
            }
        }

        tbmqq436Mapper.addGoodLogs(request.getGoodIdList());

        SerialParam serialParam = new SerialParam();
        serialParam.setAppId(request.getAppId());
        serialParam.setPrefix("P");
        serialParam.setSqFormat("easy");

        String groupNo = serialRest.core(serialParam);

        Tbmqq431 tbmqq431 = new Tbmqq431();
        tbmqq431.setGroupno(groupNo);
        tbmqq431.setSupplierno(loginUserBean.getCompanyCode());
        tbmqq431.setSuppliername(loginUserBean.getCompanyName());
        tbmqq431.setCreatedate(nowDate.getDateStr());
        tbmqq431.setCreatetime(nowDate.getTimeStr());
        tbmqq431.setCreateuser(loginUserBean.getUserCode());
        tbmqq431.setUpdatedate(nowDate.getDateStr());
        tbmqq431.setUpdatetime(nowDate.getTimeStr());
        tbmqq431.setUpdateuser(loginUserBean.getUserCode());
        tbmqq431Mapper.insertSelective(tbmqq431);

        GoodsVo updateGood = new GoodsVo();
        updateGood.setGoodIdList(request.getGoodIdList());
        updateGood.setSupplierno(loginUserBean.getCompanyCode());

        updateGood.setGroupno(groupNo);
        updateGood.setUpdatedate(nowDate.getDateStr());
        updateGood.setUpdatetime(nowDate.getTimeStr());
        updateGood.setUpdateuser(loginUserBean.getUserCode());

        int updateCount = tbmqq430Mapper.updateGoodInfo(updateGood);
        if (updateCount != request.getGoodIdList().size()) {
            throw new NetPlusException("创建商品组失败");
        }
    }

}
