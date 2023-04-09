package netplus.mall.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.data.PageBean;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.api.rest.ExportRest;
import netplus.mall.api.enums.GoodsStatusEnum;
import netplus.mall.api.enums.OrderDetailStatusEnum;
import netplus.mall.api.request.mall.GetCountGoodInfoRequest;
import netplus.mall.api.request.mall.GetCountPonoInfoRequest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.order.Tbmqq441Vo;
import netplus.mall.service.dao.Tbmqq430Mapper;
import netplus.mall.service.dao.Tbmqq441Mapper;
import netplus.provider.api.request.GetUserRoleListRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.rest.StaffRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.date.DateUtil;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CountBiz {

    protected Log logger = LogFactory.getLog(CountBiz.class);


    @Autowired
    Tbmqq430Mapper tbmqq430Mapper;

    @Autowired
    Tbmqq441Mapper tbmqq441Mapper;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    StaffRest staffRest;

    @Autowired
    ExportRest exportRest;

    public Map<String, Integer> getTabCount () {



        Map<String, Integer> countMap = new HashMap();

        GetCountPonoInfoRequest getCountPonoInfoRequest = new GetCountPonoInfoRequest();
        getCountPonoInfoRequest.setStatusList(Arrays.asList(
                GoodsStatusEnum.status_10.getCode(), GoodsStatusEnum.status_15.getCode()
        ));
        Map<String, Object> mapParam = getMapParam(getCountPonoInfoRequest);

        int count1  = tbmqq430Mapper.getPonoDetailInfoCount(mapParam);

        getCountPonoInfoRequest.setStatusList(null);
        getCountPonoInfoRequest.setIsOverdue("Y");
        mapParam = getMapParam(getCountPonoInfoRequest);
        int count2  = tbmqq430Mapper.getCountPonoCount(mapParam);


        GetCountGoodInfoRequest getCountGoodInfoRequest = new GetCountGoodInfoRequest();
        getCountGoodInfoRequest.setStatusList(Arrays.asList(OrderDetailStatusEnum.status_15.getCode()));
        mapParam = getMapParam(getCountGoodInfoRequest);
        int count3  = tbmqq441Mapper.getCountGoodCount(mapParam);

        getCountGoodInfoRequest.setStatusList(Arrays.asList(OrderDetailStatusEnum.status_25.getCode()));
        mapParam = getMapParam(getCountGoodInfoRequest);
        int count4  = tbmqq441Mapper.getCountGoodCount(mapParam);

        countMap.put("count1", count1); //商城未上架协议数量
        countMap.put("count2", count2); //商城临超期协议数量
        countMap.put("count3", count3); //商城待接单订单明细数量
        countMap.put("count4", count4); //商城待交货订单明细数量

        return countMap;

    }

    public PageBean<GoodsVo> getCountPonoInfo (GetCountPonoInfoRequest request) {


        logger.info(String.format("GetCountPonoInfoRequest: %s", new Gson().toJson(request)));


        PageBean<GoodsVo> pageBean = new PageBean();

        Map<String, Object> mapParam = getMapParam(request);

        List<GoodsVo> goodsVoList = tbmqq430Mapper.getCountPonoInfo(mapParam);
        int count  = tbmqq430Mapper.getCountPonoCount(mapParam);

        pageBean.setTotalCount(count);
        pageBean.setItems(goodsVoList);

        return pageBean;
    }

    public PageBean<GoodsVo> getPonoDetailInfo(GetCountPonoInfoRequest request) {


        PageBean<GoodsVo> pageBean = new PageBean();
        Map<String, Object> mapParam = getMapParam(request);
        int count  = tbmqq430Mapper.getPonoDetailInfoCount(mapParam);
        List<GoodsVo> goodsVoList = tbmqq430Mapper.getPonoDetailInfo(mapParam);

        pageBean.setItems(goodsVoList);
        pageBean.setTotalCount(count);

        return pageBean;
    }


    public PageBean<Tbmqq441Vo> getCountGoodInfo (GetCountGoodInfoRequest request) {
        PageBean<Tbmqq441Vo> pageBean = new PageBean();

        Map<String, Object> mapParam = getMapParam(request);

        logger.info(String.format("GetCountGoodInfoRequest: %s", new Gson().toJson(request)));


        List<Tbmqq441Vo> tbmqq441VoList = tbmqq441Mapper.getCountGoodInfo(mapParam);
        int count  = tbmqq441Mapper.getCountGoodCount(mapParam);

        pageBean.setTotalCount(count);
        pageBean.setItems(tbmqq441VoList);

        return pageBean;
    }


    public <T> Map<String, Object> getMapParam (T t) {
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(t);
        LoginUserBean loginUserBean = identityRest.getCurrentUser();


        logger.info(String.format("mapParam: %s", new Gson().toJson(mapParam)));

        GetUserRoleListRequest getUserRoleListRequest = new GetUserRoleListRequest();
        getUserRoleListRequest.setUserNo(loginUserBean.getUserCode());
        List<String> roleList = staffRest.getUserRoleList(getUserRoleListRequest);

        if (roleList.contains("S0001")) {
            mapParam.put("isAdmin", true);
        }else{
            mapParam.put("isAdmin", false);
            mapParam.put("agentno", loginUserBean.getUserCode());
        }

        return mapParam;
    }


    public String exportCountPonoInfo (GetCountPonoInfoRequest request) throws Exception {
        PageBean<GoodsVo> pageBean = getCountPonoInfo(request);

        String tempName = "tmp_count_pono_info";
        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(pageBean.getItems());
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出列表");
        String url = exportRest.genExcel(genExcelRequest);

        return url;
    }

    public String exportCountPonoDetailInfo (GetCountPonoInfoRequest request) throws Exception {
        PageBean<GoodsVo> pageBean = getPonoDetailInfo(request);
        String tempName="";
        List<GoodsVo> list=pageBean.getItems();
        if(request.getPage().equals("A")){
            tempName = "tmp_count_pono_detail_info";
        }else {
            tempName= "tmp_count__unlisted_good_info";
            list= list.stream().map(item->{
                GoodsVo vo=new GoodsVo();
                BeanUtils.copyProperties(item,vo);
                vo.setPopricestartdateStr(DateUtil.format(vo.getPopricestartdate(),"yyyyMMdd","yyyy-MM-dd"));
                vo.setPopricedateStr(DateUtil.format(vo.getPopricedate(),"yyyyMMdd","yyyy-MM-dd"));
                return vo;
            }).collect(Collectors.toList());
        }

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(list);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出列表");
        String url = exportRest.genExcel(genExcelRequest);

        return url;
    }


    public String exportCountGoodInfo (GetCountGoodInfoRequest request) throws Exception {
        PageBean<Tbmqq441Vo> pageBean = getCountGoodInfo(request);
        String tempName="";
        List<Tbmqq441Vo> list=pageBean.getItems();
        if(request.getPage().equals("A")){
            tempName= "tmp_count_pending_order_info";
            list=list.stream().map(item->{
                        Tbmqq441Vo vo=new Tbmqq441Vo();
                        BeanUtils.copyProperties(item,vo);
                        vo.setApprovedate(DateUtil.format(vo.getApprovedate()+vo.getApprovetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
                        return vo;
                    }).collect(Collectors.toList());
        }else {
            tempName= "tmp_count_pending_delivery_info";
            list=list.stream().map(item->{
                        Tbmqq441Vo vo=new Tbmqq441Vo();
                        BeanUtils.copyProperties(item,vo);
                        vo.setReceivingdate(DateUtil.format(vo.getReceivingdate(),"yyyyMMdd","yyyy-MM-dd"));
                        vo.setLeaderTimeStr(String.format("%s个工作日",vo.getLeadtimenum()));
                        return vo;
                    }).collect(Collectors.toList());

        }

        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(list);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("导出列表");
        String url = exportRest.genExcel(genExcelRequest);

        return url;
    }


}

