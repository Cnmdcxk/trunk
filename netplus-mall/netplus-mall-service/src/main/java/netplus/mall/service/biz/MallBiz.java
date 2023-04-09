package netplus.mall.service.biz;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.request.goodsInventory.GoodsInventoryPageRequest;
import netplus.mall.api.request.mall.GetGoodsHistoryRequest;
import netplus.mall.api.request.mall.SearchRequest;
import netplus.mall.api.vo.GoodsHistoryVo;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.category.CategoryVo;
import netplus.mall.api.vo.category.Tbmqq403Vo;
import netplus.mall.service.dao.Tbmqq403Mapper;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq436Mapper;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.object.ObjectUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class MallBiz {

    @Autowired
    private Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    private IdentityRest identityRest;

    @Autowired
    Tbmqq403Mapper tbmqq403Mapper;

    @Autowired
    Tbmqq436Mapper tbmqq436Mapper;

    public List<CategoryVo> getAllCategory () {
        List<CategoryVo> categoryVoList = new ArrayList();
        List<Tbmqq403Vo> tbmqq403List = tbmqq403Mapper.getAllCategory();

        if (ObjectUtil.isEmpty(tbmqq403List)) {
            throw new NetPlusException("分类未配置");
        }

        Map<String, List<Tbmqq403Vo>> categoryGroup = tbmqq403List
                .stream()
                .collect(Collectors.groupingBy(t -> t.getCategoryname()));

        for (Map.Entry<String, List<Tbmqq403Vo>> entry: categoryGroup.entrySet()) {

            CategoryVo categoryVo = new CategoryVo();
            List<CategoryVo> oneLevelClaNameList = new ArrayList();

            String categoryName = entry.getKey();
            String icon = entry.getValue().get(0).getIcon();
            String id=entry.getValue().get(0).getCategorypk();
            categoryVo.setName(categoryName);
            categoryVo.setIcon(icon);
            categoryVo.setId(id);

            Map<String, List<Tbmqq403Vo>> oneLevelClaNameGroup = entry.getValue()
                    .stream()
                    .collect(Collectors.groupingBy(t -> t.getOnelevelclaname()));


            for (Map.Entry<String, List<Tbmqq403Vo>> oneEntry: oneLevelClaNameGroup.entrySet()) {

                CategoryVo oneLevelClaNameVo = new CategoryVo();
                List<CategoryVo> twoLevelClaNameList = new ArrayList();

                String oneLevelClaName = oneEntry.getKey();
                String oneId=oneEntry.getValue().get(0).getOnelevelclapk();
                oneLevelClaNameVo.setName(oneLevelClaName);
                oneLevelClaNameVo.setId(oneId);
                for (Tbmqq403Vo vo: oneEntry.getValue()) {

                    CategoryVo twoLevelClaNameVo = new CategoryVo();
                    twoLevelClaNameVo.setName(vo.getTwolevelclaname());
                    twoLevelClaNameVo.setId(vo.getTwolevelclapk());
                    twoLevelClaNameList.add(twoLevelClaNameVo);
                }

                oneLevelClaNameVo.setChildren(twoLevelClaNameList);
                oneLevelClaNameList.add(oneLevelClaNameVo);
            }

            categoryVo.setChildren(oneLevelClaNameList);
            categoryVoList.add(categoryVo);
        }

        return categoryVoList;
    }


    public PageBean<GoodsVo> search (SearchRequest request) {

        PageBean<GoodsVo> pageBean = new PageBean();
        if(request.getSearchKey()!=null)
        request.setSearchKey(request.getSearchKey().replace(" ",""));
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("userNo", loginUserBean.getUserCode());

        List<GoodsVo> goodsVoList = tbmqq430Mapper.search(mapParam);
        int count = tbmqq430Mapper.searchCount(mapParam);
        List<KeyValue> twoLevelClaNameList = tbmqq430Mapper.searchTwoLevelClaNameKV(mapParam);
        List<KeyValue> oneLevelClaNameList = tbmqq430Mapper.searchOneLevelClaNameKV(mapParam);
        List<KeyValue> categoryNameList = tbmqq430Mapper.searchCategoryNameKV(mapParam);
        List<KeyValue> brandList = tbmqq430Mapper.searchBrandKV(mapParam);


        pageBean.setItems(goodsVoList);
        pageBean.setTotalCount(count);
        pageBean.addResultMap("oneLevelClaNameList", oneLevelClaNameList);
        pageBean.addResultMap("twoLevelClaNameList", twoLevelClaNameList);
        pageBean.addResultMap("categoryNameList", categoryNameList);
        pageBean.addResultMap("brandList", brandList);

        return pageBean;
    }

    public Tbmqq430 getGoodsInfoByMatrlTM(String matrlTM){
        List<Tbmqq430> list = tbmqq430Mapper.selectList(new LambdaQueryWrapper<Tbmqq430>().eq(Tbmqq430::getMatrltm, matrlTM));
        if(ObjectUtils.isEmpty(list)){
            return null;
        }
        return list.get(0);
    }

    public List<KeyValue> getGoodsMatrlInfo(GoodsInventoryPageRequest request){
        return tbmqq430Mapper.getGoodsMatrlInfo(request);
    }

    public int getGoodsMatrlCount(GoodsInventoryPageRequest request){
        return tbmqq430Mapper.getGoodsMatrlCount(request);
    }

    public PageBean<GoodsHistoryVo> getGoodsHistory (GetGoodsHistoryRequest request) {

        PageBean<GoodsHistoryVo> pageBean = new PageBean<>();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        List<GoodsHistoryVo> goodsHistoryVoList = tbmqq436Mapper.getGoodsHistoryList(mapParam);
        goodsHistoryVoList.forEach(g -> g.setStatusName(GoodsStatusEnum.getName(g.getStatus())));
        List<KeyValue> suppliername = tbmqq436Mapper.getSupplierName(mapParam);
        List<KeyValue> matrltm = tbmqq436Mapper.getMatrltm(mapParam);
        List<KeyValue> pono = tbmqq436Mapper.getPono(mapParam);

        pageBean.setItems(goodsHistoryVoList);
        pageBean.addResultMap("suppliername", suppliername);
        pageBean.addResultMap("matrltm", matrltm);
        pageBean.addResultMap("pono", pono);

        return pageBean;
    }
}
