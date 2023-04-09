package netplus.joint.zkh.out.service.biz;


import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.OneOrZeroEnum;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.joint.zkh.api.pojo.GoodsImageVo;
import netplus.mall.api.pojo.ygmalluser.Tbmqq430;
import netplus.mall.api.pojo.ygmalluser.Tbmqq435;
import netplus.mall.api.request.good.GetGoodBySupplierNoRequest;
import netplus.mall.api.request.good.UpdateZkhPicRequest;
import netplus.mall.api.rest.GoodsPictureRest;
import netplus.mall.api.rest.GoodsRest;
import netplus.mall.api.vo.GoodsVo;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ZkhPicSyncBiz {

    private static Log logger = LogFactory.getLog(ZkhPicSyncBiz.class);

    @Autowired
    ZkhMessageBiz zkhMessageBiz;

    @Autowired
    ZkhOutBiz zkhOutBiz;

    @Autowired
    GoodsPictureRest goodsPictureRest;

    @Autowired
    GoodsRest goodsRest;

    @Autowired
    ServiceConfigRest serviceConfigRest;


    public void picSync () {

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);
        String supplierNo = tbmqq461.getVal();
        NowDate nowDate = new NowDate();

        GetGoodBySupplierNoRequest getGoodBySupplierNoRequest = new GetGoodBySupplierNoRequest();
        getGoodBySupplierNoRequest.setSupplierNo(supplierNo);
        List<GoodsVo> goodsVoList = goodsRest.getGoodBySupplierNo(getGoodBySupplierNoRequest);

        Map<String, String> skuAndGoodIdMap = goodsVoList.stream().collect(Collectors.toMap(GoodsVo::getGoodno, g -> g.getGoodid()));
        if (ObjectUtil.nonEmpty(goodsVoList)) {

            List<String> skuList = new ArrayList(skuAndGoodIdMap.keySet());

            int pageSize = 20;
            int totalPage = skuList.size() / pageSize;
            int remain = skuList.size() % pageSize;
            if (remain > 0) {
                totalPage  = totalPage + 1;
            }

            for (int i = 1; i <= totalPage; i++) {

                int success = 0;
                int start = (i - 1) * pageSize;
                int end = i * pageSize;

                if (end > skuList.size()) {
                    end = skuList.size();
                }

                int totalCount = end - start;
                List<String> subSkuList = skuList.subList(start, end);

                logger.info(String.format("震坤行商品图片同步第%d页: 共%d个", i, totalCount));

                List<GoodsImageVo> goodsImageVoList = zkhOutBiz.getGoodsImage(subSkuList);

                goodsImageVoList = goodsImageVoList
                        .stream()
                        .filter( g -> ObjectUtil.nonEmpty(g.getSkuPic()) && g.getSkuPic().size() > 0)
                        .collect(Collectors.toList());

                if (ObjectUtil.nonEmpty(goodsImageVoList)) {

                    Map<String, GoodsImageVo> goodsImageVoMap = goodsImageVoList
                            .stream()
                            .collect(Collectors.toMap(GoodsImageVo::getSku, g -> g));

                    for (String sku: goodsImageVoMap.keySet()) {

                        String goodId = skuAndGoodIdMap.get(sku);
                        GoodsImageVo goodsImageVo = goodsImageVoMap.get(sku);
                        String mainPicPath = goodsImageVo
                                .getSkuPic()
                                .stream()
                                .filter(p -> OneOrZeroEnum.One.getValue2() == p.getIsPrimary().intValue())
                                .collect(Collectors.toList())
                                .get(0)
                                .getFullPath();

                        UpdateZkhPicRequest updateZkhPicRequest = new UpdateZkhPicRequest();
                        Tbmqq430 tbmqq430 = new Tbmqq430();
                        tbmqq430.setSupplierno(supplierNo);
                        tbmqq430.setGoodid(goodId);
                        tbmqq430.setPictureurl(mainPicPath);
                        tbmqq430.setUpdateuser(SysCodeEnum.JOB.code);
                        tbmqq430.setUpdatedate(nowDate.getDateStr());
                        tbmqq430.setUpdatetime(nowDate.getTimeStr());

                        List<Tbmqq435> tbmqq435List = goodsImageVo.getSkuPic().stream().map( g -> {

                            Tbmqq435 tbmqq435 = new Tbmqq435();
                            tbmqq435.setGoodid(goodId);
                            tbmqq435.setSupplierno(supplierNo);
                            tbmqq435.setPicturename(" ");
                            tbmqq435.setPictureurl(g.getFullPath());
                            tbmqq435.setPicturenum(OneOrZeroEnum.One.getValue().equals(g.getIsPrimary()) ? "0": String.valueOf(g.getOrderSort()));
                            tbmqq435.setCreateuser(SysCodeEnum.JOB.code);
                            tbmqq435.setCreatedate(nowDate.getDateStr());
                            tbmqq435.setCreatetime(nowDate.getTimeStr());
                            tbmqq435.setUpdateuser(SysCodeEnum.JOB.code);
                            tbmqq435.setUpdatedate(nowDate.getDateStr());
                            tbmqq435.setUpdatetime(nowDate.getTimeStr());

                            return tbmqq435;

                        }).collect(Collectors.toList());

                        updateZkhPicRequest.setTbmqq430(tbmqq430);
                        updateZkhPicRequest.setTbmqq435List(tbmqq435List);

                        try {
                            int updateCount = goodsRest.updateZkhPic(updateZkhPicRequest);
                            success = success + updateCount;
                        }catch (Exception e) {
                            logger.info(String.format("sku: %s图片同步失败", sku));
                        }
                    }

                    logger.info(String.format("震坤行商品图片同步第%d页: 共%d个, 成功%d个, 失败%d个",  i,totalCount, success, totalCount - success));

                }else{

                    logger.info(String.format("震坤行商品图片同步第%d页: 查询不到数据", i));

                }
            }

        }else{
            logger.info(String.format("暂无震坤行商品需要同步"));
        }
    }

}
