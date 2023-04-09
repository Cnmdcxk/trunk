package netplus.mall.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.OneOrZeroEnum;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.joint.erp.api.response.out.JK0006.JK0006SubResponse;
import netplus.joint.erp.api.response.out.JK0009SubResponse;
import netplus.joint.erp.api.response.out.JK0010.JK0010SubResponse;
import netplus.joint.erp.api.response.out.JK0033.JK0033SubResponse;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.mall.api.enums.AddrTypeEnum;
import netplus.mall.api.enums.OrderDetailStatusEnum;
import netplus.mall.api.enums.OrderStatusEnum;
import netplus.mall.api.pojo.ygmalluser.Approve;
import netplus.mall.api.request.good.GetPriceCompareRequest;
import netplus.mall.api.request.iface.GetGoodLatestPriceRequest;
import netplus.mall.api.request.mall.GetDeviceRequest;
import netplus.mall.api.request.order.CreateOrderDetailRequest;
import netplus.mall.api.request.order.CreateOrderRequest;
import netplus.mall.api.request.order.CreateOrderSubDetailRequest;
import netplus.mall.api.request.shoppingCart.DelMyShoppingCartRequest;
import netplus.mall.api.vo.GoodsVo;
import netplus.mall.api.vo.basicData.CheckOrderConfigVo;
import netplus.mall.api.vo.iface.GoodLatestPriceVo;
import netplus.mall.api.vo.order.CreateOrderResultVo;
import netplus.mall.api.vo.order.Tbmqq440Vo;
import netplus.mall.api.vo.order.Tbmqq441Vo;
import netplus.mall.service.dao.ApproveMapper;
import netplus.mall.service.dao.Tbmqq440Mapper;
import netplus.mall.service.dao.Tbmqq441Mapper;
import netplus.mall.service.dao.Tbmqq443Mapper;
import netplus.messaging.api.rest.MessagingRest;
import netplus.provider.api.pojo.ygmalluser.Department;
import netplus.provider.api.pojo.ygmalluser.Tbmqq439;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.request.GetAddrListRequest;
import netplus.provider.api.request.GetConsigneeRequest;
import netplus.provider.api.request.department.GetDepartmentRequest;
import netplus.provider.api.rest.*;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.serial.api.rest.SerialRest;
import netplus.utils.date.DateUtil;
import netplus.utils.date.NowDate;
import netplus.utils.number.NumberUtil;
import netplus.utils.object.ObjectUtil;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CreateOrderBiz {


    protected Log logger = LogFactory.getLog(CreateOrderBiz.class);

    @Autowired
    CommonRest commonRest;

    @Autowired
    ShoppingCartBiz shoppingCartBiz;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    StaffRest staffRest;

    @Autowired
    DepartmentRest departmentRest;

    @Autowired
    SerialRest serialRest;

    @Autowired
    Tbmqq441Mapper tbmqq441Mapper;

    @Autowired
    Tbmqq440Mapper tbmqq440Mapper;

    @Autowired
    MessagingRest messagingRest;


    @Autowired
    Tbmqq443Mapper tbmqq443Mapper;

    @Autowired
    ServiceConfigRest serviceConfigRest;

    @Autowired
    ApproveMapper approveMapper;

    @Autowired
    ErpOutRest erpOutRest;

    @Autowired
    GoodsListedBiz goodsListedBiz;

    @Autowired
    IfaceBiz ifaceBiz;

    @Autowired
    BasicDataBiz basicDataBiz;

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse<CreateOrderResultVo> createOrder (CreateOrderRequest request) {


        if (StringUtils.isEmpty(request.getDeptLinePk())) {
            throw new NetPlusException("部门条线不能为空");
        }

        if (StringUtils.isEmpty(request.getConsigneeCode())) {
            throw new NetPlusException("收货地址不能为空");
        }

        if (ObjectUtil.isEmpty(request.getOrderDetail()) || request.getOrderDetail().size() <= 0) {

            throw new NetPlusException("商品分组信息不能为空");

        }else{

            for (CreateOrderDetailRequest detailRequest: request.getOrderDetail()) {

                if (ObjectUtil.isEmpty(detailRequest.getGoodList())) {
                    throw new NetPlusException("商品明细不能为空");
                }

                if (StringUtils.isEmpty(detailRequest.getPonopk())) {
                    throw new NetPlusException("协议PK不能为空");
                }
            }
        }


        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        ApiResponse<CreateOrderResultVo> resp = ApiResponse.success();
        CreateOrderResultVo resultVo = new CreateOrderResultVo();


        //获取收货地址信息
        GetAddrListRequest getAddrListRequest = new GetAddrListRequest();
        getAddrListRequest.setCode(request.getConsigneeCode());
        List<Tbmqq439> tbmqq439List = commonRest.getAddrList(getAddrListRequest);
        if (ObjectUtil.isEmpty(tbmqq439List)) {
            throw new NetPlusException("收货信息不存在");
        }

        Tbmqq439 consigneeInfo = tbmqq439List.get(0);
        Tbdu01Vo consigneeUser = new Tbdu01Vo();
        if (consigneeInfo.getAddrtype().equals(AddrTypeEnum.KDD.getCode())) {

            if (StringUtils.isEmpty(request.getConsigneeUserNo())) {
                throw new NetPlusException("收货人不能为空");
            }

            if (StringUtils.isEmpty(request.getConsigneePhone())) {
                throw new NetPlusException("收货人联系方式不能为空");
            }

            if (loginUserBean.getUserCode().equals(request.getConsigneeUserNo())) {
                throw new NetPlusException(String.format("收货人和下单人不能一样", request.getConsigneeUserNo()));
            }

            GetConsigneeRequest getConsigneeRequest = new GetConsigneeRequest();
            getConsigneeRequest.setUserNo(request.getConsigneeUserNo());
            consigneeUser = staffRest.getConsigneeByUserNo(getConsigneeRequest);
            if (ObjectUtil.isEmpty(consigneeUser)) {
                throw new NetPlusException(String.format("收货人编码%s不存在", request.getConsigneeUserNo()));
            }
        }


        GetDepartmentRequest getDepartmentRequest = new GetDepartmentRequest();
        getDepartmentRequest.setDepno(loginUserBean.getDeptNo());
        Department department = departmentRest.getDepartment(getDepartmentRequest);

        Map<String, String> ponopkAndBuyerNoteMap = request
                .getOrderDetail()
                .stream()
                .collect(Collectors.toMap(CreateOrderDetailRequest::getPonopk, q -> q.getBuyerNote()));


        //获取商品最新信息
        Map<String, String[]> goodIdMap = getGoodIdMap(request.getOrderDetail());
        List<GoodsVo> settleGoodInfo = shoppingCartBiz.getSettleGoodInfo(loginUserBean.getUserCode(), goodIdMap.keySet());
        if (settleGoodInfo.size() != goodIdMap.keySet().size()) {
            throw new NetPlusException("部分商品已下架或不存在");
        }

        //获取物资性质并校验是否允许下单
        List<String> matrlIds = settleGoodInfo.stream().map(GoodsVo::getMatrlid).collect(Collectors.toList());
        List<JK0033SubResponse> matrlQualityByMatrlId = basicDataBiz.getMatrlQualityByMatrlId(matrlIds);
        if(!basicDataBiz.checkMatrlQualityAndDept(loginUserBean,matrlQualityByMatrlId)){
            throw new NetPlusException("常备件可直接领用，若无库存，请联系物资总库下单");
        }
        Map<String, JK0033SubResponse> matrlQualityMap = matrlQualityByMatrlId.stream().collect(Collectors.toMap(JK0033SubResponse::getWzmcbm_pk, Function.identity(), (e1, e2) -> e1));


        CheckOrderConfigVo checkOrderConfig = basicDataBiz.getCheckOrderConfig();
        boolean hasConfigCategory=false;
        boolean hasOtherCategory=false;

        for (GoodsVo goodsVo: settleGoodInfo) {
            if (!checkOverdue(goodsVo.getPopricestartdate(), goodsVo.getPopricedate())) {
                throw new NetPlusException(String.format("物料条码%s的商品，不在协议有效期内，无法购买", goodsVo.getMatrltm()));
            }

            if (StringUtils.isEmpty(goodsVo.getProjectno())) {
                throw new NetPlusException(String.format("物料条码%s的商品，工程项目单不能为空", goodsVo.getMatrltm()));
            }else{

                if (!goodsVo.getProjectno().equals("SC00") && !StringUtils.isEmpty(goodsVo.getDeviceapplyno())) {
                    throw new NetPlusException(String.format("物料条码%s的商品，工程项目单是生产上用SC00时，才能选择新增设备申请单", goodsVo.getMatrltm()));
                }
            }

            if(goodsVo.getTwolevelclapk().equals(checkOrderConfig.getCategory())){
                hasConfigCategory=true;
            }else {
                hasOtherCategory=true;
            }
        }

        if(!StringUtils.isEmpty(checkOrderConfig.getDeptNo())
                && checkOrderConfig.getDeptNo().equals(loginUserBean.getDeptNo())
                && hasConfigCategory){
            if(StringUtils.isEmpty(request.getCostDeptPK())){
                throw new NetPlusException("费用预算部门不能为空");
            }
            if(hasOtherCategory){
                throw new NetPlusException("特殊分类下的商品需单独下单");
            }
        }

        //获取商城条线占用预算金额
        BigDecimal lineOrderAmount = tbmqq440Mapper.getLineOrderAmount(request.getDeptLinePk(), loginUserBean.getDeptNo());
        BigDecimal currApproveAmount = BigDecimal.ZERO;

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);

        Tbmqq461 tbmqq461Two = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_SPEC_USER.code);
        List<String> specUserList = new Gson().fromJson(tbmqq461Two.getVal(), ArrayList.class);
        boolean isNeedApprove  = !specUserList.contains(loginUserBean.getUserCode());

        //组装数据后保存
        NowDate nowDate = new NowDate();
        List<Tbmqq440Vo> tbmqq440VoList = new ArrayList();
        List<Tbmqq441Vo> tbmqq441VoList = new ArrayList();
        List<String> orderList = new ArrayList();

        //根据协议PK分单
        Map<String, List<GoodsVo>> groupData = settleGoodInfo.stream().collect(Collectors.groupingBy( g -> g.getPonopk()));
        String approveCode = UuidUtil.getUuid2();


        //自有库存校验
        Map<String, JK0010SubResponse> goodQtyMap = getCheckQtyResult(settleGoodInfo);

        for (Map.Entry<String, List<GoodsVo>> entry: groupData.entrySet()) {

            String ponopk = entry.getKey();
            List<GoodsVo> val = entry.getValue();

            Tbmqq440Vo tbmqq440Vo = new Tbmqq440Vo();
            GoodsVo g = val.get(0);

            BigDecimal totalAmt = BigDecimal.ZERO;
            BigDecimal noTaxTotalAmt = BigDecimal.ZERO;
            BigDecimal totalQty = BigDecimal.ZERO;
            String pono = g.getPono();
            String supplierNo = g.getSupplierno();
            String supplierName = g.getSuppliername();
            String supplierPk = g.getSupplierpk();

            String isMallOutProvider = supplierNo.equals(tbmqq461.getVal()) ? YesNoEnum.Yes.getValue(): YesNoEnum.No.getValue();

            Map<String, BigDecimal> goodPriceMap = getGoodPriceMap(ponopk, isMallOutProvider, val);

            //获取订单号
            String orderNo = serialRest.get("MD");
            orderList.add(orderNo);

            //组装数据
            int index = 1;
            for (GoodsVo v: val) {

                Tbmqq441Vo tbmqq441Vo = new Tbmqq441Vo();
                BeanUtils.copyProperties(v, tbmqq441Vo);

                BigDecimal price = getPrice(v, goodPriceMap, isMallOutProvider);

                tbmqq441Vo.setOrderno(orderNo);
                tbmqq441Vo.setPonopk(ponopk);
                tbmqq441Vo.setOrderdetailno(String.format("%04d",index));
                tbmqq441Vo.setOrderdetailid(String.format("%s-%s", orderNo, tbmqq441Vo.getOrderdetailno()));
                tbmqq441Vo.setQty(v.getCartQty());
                tbmqq441Vo.setPrice(price);
                tbmqq441Vo.setNotaxprice(NumberUtil.noTaxPrice(price,v.getTax()));
                tbmqq441Vo.setAmt(NumberUtil.mul2Amt(price, v.getCartQty()));
                tbmqq441Vo.setNotaxamt(NumberUtil.mul2Amt(tbmqq441Vo.getNotaxprice(), v.getCartQty()));
                tbmqq441Vo.setDeliqty(BigDecimal.ZERO);
                tbmqq441Vo.setInvoiceqty(BigDecimal.ZERO);
                tbmqq441Vo.setTakedeliqty(BigDecimal.ZERO);
                tbmqq441Vo.setStatus(isNeedApprove ? OrderDetailStatusEnum.status_10.getCode(): OrderDetailStatusEnum.status_15.getCode());
                String[] remarks = goodIdMap.get(v.getGoodid());
                tbmqq441Vo.setRemark(remarks[0]);
                tbmqq441Vo.setRemark2(remarks[1]);
                tbmqq441Vo.setProjectno(v.getProjectno());
                tbmqq441Vo.setProjectname(v.getProjectname());
                tbmqq441Vo.setDeviceapplypk(v.getDeviceapplypk());
                tbmqq441Vo.setDeviceapplyno(v.getDeviceapplyno());
                tbmqq441Vo.setLeadtimenum(v.getLeadtimenum());
                tbmqq441Vo.setDevicesimpleno(v.getDevicesimpleno());
                tbmqq441Vo.setLinename(request.getDeptLineName());
                tbmqq441Vo.setLinepk(request.getDeptLinePk());

                JK0033SubResponse matrlQuality = matrlQualityMap.get(tbmqq441Vo.getMatrlid());
                if(ObjectUtil.isEmpty(matrlQuality)){
                    throw new NetPlusException(String.format("物料条码%s的商品未查询到物资定性", tbmqq441Vo.getMatrltm()));
                }
                tbmqq441Vo.setMatrlqualitypk(matrlQuality.getWzdxbm_pk());
                tbmqq441Vo.setMatrlqualityname(matrlQuality.getWzdxmc());

                JK0010SubResponse jk0010SubResponse = goodQtyMap.get(tbmqq441Vo.getMatrlid());
                if (ObjectUtil.isEmpty(jk0010SubResponse)) {
                    throw new NetPlusException(String.format("物料条码%s的商品未查询到仓库库存", tbmqq441Vo.getMatrltm()));
                }

                tbmqq441Vo.setCkqty(jk0010SubResponse.getKcsl());
                tbmqq441Vo.setQtyupper(jk0010SubResponse.getKcslsx());
                tbmqq441Vo.setIsOutQty(jk0010SubResponse.getSfckc());
                tbmqq441Vo.setRemainQty(jk0010SubResponse.getRemainQty());

                tbmqq441Vo.setCreatedate(nowDate.getDateStr());
                tbmqq441Vo.setCreatetime(nowDate.getTimeStr());
                tbmqq441Vo.setCreateuser(loginUserBean.getUserCode());
                tbmqq441Vo.setUpdatedate(nowDate.getDateStr());
                tbmqq441Vo.setUpdatetime(nowDate.getTimeStr());
                tbmqq441Vo.setUpdateuser(loginUserBean.getUserCode());

                if (OneOrZeroEnum.One.getValue().equals(v.getHasPic())) {
                    tbmqq441Vo.setIsneedpic(v.getIsneedpic());
                }else{
                    tbmqq441Vo.setIsneedpic(OneOrZeroEnum.Zero.getValue());
                }

                totalAmt = NumberUtil.add2Amt(totalAmt, tbmqq441Vo.getAmt());
                noTaxTotalAmt = NumberUtil.add2Amt(noTaxTotalAmt, tbmqq441Vo.getNotaxamt());
                totalQty = NumberUtil.add2Weight(totalQty, tbmqq441Vo.getQty());

                index ++;
                tbmqq441VoList.add(tbmqq441Vo);
            }

            //订单基本信息
            tbmqq440Vo.setOrderno(orderNo);
            tbmqq440Vo.setStatus(isNeedApprove ? OrderStatusEnum.status_10.getCode():OrderStatusEnum.status_15.getCode());
            tbmqq440Vo.setPono(pono);
            tbmqq440Vo.setPonopk(ponopk);
            tbmqq440Vo.setBuyername(loginUserBean.getCompanyName());
            tbmqq440Vo.setBuyerno(loginUserBean.getCompanyCode());
            tbmqq440Vo.setSupplierno(supplierNo);
            tbmqq440Vo.setSuppliername(supplierName);
            tbmqq440Vo.setSupplierpk(supplierPk);
            tbmqq440Vo.setUserno(loginUserBean.getUserCode());
            tbmqq440Vo.setUsername(loginUserBean.getUsername());
            tbmqq440Vo.setDeptno(loginUserBean.getDeptNo());
            tbmqq440Vo.setDeptname(loginUserBean.getDeptName());
            tbmqq440Vo.setDeptnum(loginUserBean.getDeptNum());
            tbmqq440Vo.setBuyernote(ponopkAndBuyerNoteMap.get(ponopk));
            tbmqq440Vo.setApprovecode(approveCode);
            tbmqq440Vo.setLinepk(request.getDeptLinePk());
            tbmqq440Vo.setLinename(request.getDeptLineName());

            //收货相关信息
            tbmqq440Vo.setConsigneefulladdr(String.format(
                    "%s%s%s",
                    consigneeInfo.getProvince(),
                    consigneeInfo.getCity(),
                    consigneeInfo.getConsigneeaddr())
            );

            tbmqq440Vo.setConsigneeaddr(consigneeInfo.getConsigneeaddr());
            tbmqq440Vo.setConsigneename(consigneeUser.getName());
            tbmqq440Vo.setConsigneeno(consigneeUser.getUserno());
            tbmqq440Vo.setConsigneephone(request.getConsigneePhone());
            tbmqq440Vo.setConsigneeaddrtype(consigneeInfo.getAddrtype());

            //发票抬头相关信息
            tbmqq440Vo.setInvoicetitle(department.getCompname());
            tbmqq440Vo.setSupplierbankname(department.getBank());
            tbmqq440Vo.setSuppliercardno(department.getBanknum());
            tbmqq440Vo.setSuppliertaxno(department.getTax());
            tbmqq440Vo.setSupplieraddr(department.getCompaddr());
            tbmqq440Vo.setSupplierphone(department.getCompphone());

            //统计信息默认为0
            tbmqq440Vo.setTakedelitotalqty(NumberUtil.ZORE);
            tbmqq440Vo.setInvoicedtotalqty(BigDecimal.ZERO);
            tbmqq440Vo.setDelitotalqty(NumberUtil.ZORE);
            tbmqq440Vo.setTotalamt(totalAmt);
            tbmqq440Vo.setNotaxtotalamt(noTaxTotalAmt);
            tbmqq440Vo.setTotalqty(totalQty);

            //时间
            tbmqq440Vo.setCreatedate(nowDate.getDateStr());
            tbmqq440Vo.setCreatetime(nowDate.getTimeStr());
            tbmqq440Vo.setCreateuser(loginUserBean.getUserCode());
            tbmqq440Vo.setUpdatedate(nowDate.getDateStr());
            tbmqq440Vo.setUpdatetime(nowDate.getTimeStr());
            tbmqq440Vo.setUpdateuser(loginUserBean.getUserCode());

            //费用预算部门
            tbmqq440Vo.setCostdeptpk(request.getCostDeptPK());
            tbmqq440Vo.setCostdeptname(request.getCostDeptName());
            tbmqq440Vo.setCostdeptnum(request.getCostDeptNum());

            tbmqq440VoList.add(tbmqq440Vo);

            currApproveAmount = NumberUtil.add2Amt(tbmqq440Vo.getTotalamt(), currApproveAmount);
        }


        //获取同类最低价, 校验备注
        setLowPriceAndUnit(tbmqq441VoList);
        for (Tbmqq441Vo tbmqq441Vo: tbmqq441VoList) {
            if (YesNoEnum.No.getValue().equals(tbmqq441Vo.getIsLowPrice())
                    && StringUtils.isEmpty(tbmqq441Vo.getRemark())) {
                throw new NetPlusException(String.format("物料条码:%s的商品不是同类最低价商品，备注必填", tbmqq441Vo.getMatrltm()));
            }
        }

        //校验是否超库存
        long outQtyCount = tbmqq441VoList.stream().filter( t -> OneOrZeroEnum.One.getValue().equals(t.getIsOutQty())).count();
        if (outQtyCount > 0) {
            resp.setStatus(-1);
            resultVo.addTbmqq441VoList(tbmqq441VoList);
            resp.setData(resultVo);
            return resp;
        }

        //校验是否超预算
        BigDecimal totalCheckAmount = NumberUtil.add2Amt(currApproveAmount, lineOrderAmount);
        JK0009SubResponse jk0009SubResponse = ifaceBiz.getBudgetCheckResult(loginUserBean.getDeptNo(), request.getDeptLinePk(), totalCheckAmount);
        if (OneOrZeroEnum.Zero.getValue().equals(jk0009SubResponse.getNum())) {
            resp.setStatus(-2);
            return resp;
        }

        //校验新增设备单号
        checkDeviceApply(tbmqq441VoList);

        //保存订单数据
        Approve approve = new Approve();
        approve.setApprovecode(approveCode);
        approve.setApproveamt(currApproveAmount);
        approve.setLineorderamt(lineOrderAmount);
        approve.setRemainbudget(jk0009SubResponse.getJhsyje());
        approve.setCreatedate(nowDate.getDateStr());
        approve.setCreatetime(nowDate.getTimeStr());
        approve.setIsneedapprove(isNeedApprove ? YesNoEnum.Yes.getValue(): YesNoEnum.No.getValue());
        approve.setSprcodeone(request.getSprCodeOne());
        approve.setSprcodetwo(request.getSprCodeTwo());

        //校验审批人，同时获取审批人名称
        ifaceBiz.addSprName(loginUserBean.getDeptNo(), request.getDeptLinePk(), approve);

        approveMapper.insertSelective(approve);
        tbmqq440Mapper.batchInsert(tbmqq440VoList);
        tbmqq441Mapper.batchInsert(tbmqq441VoList);

        //删除购物车数据
        DelMyShoppingCartRequest delMyShoppingCartRequest = new DelMyShoppingCartRequest();
        delMyShoppingCartRequest.setGoodIdList(new ArrayList(goodIdMap.keySet()));
        shoppingCartBiz.delMyShoppingCart(delMyShoppingCartRequest);

        //判断是否需要走审批流
        if (isNeedApprove) {

            //提交永刚OA
            ifaceBiz.commitOa(approve, loginUserBean.getUserCode());
        }

        resultVo.addOrderNo(orderList);
        resp.setData(resultVo);

        return resp;
    }


    private Map<String, String[]> getGoodIdMap (List<CreateOrderDetailRequest> orderDetail) {
        Map<String, String[]> goodIdMap = new HashMap();
        int count = 0;

        for (CreateOrderDetailRequest q: orderDetail) {

            count = count + q.getGoodList().size();

            for (CreateOrderSubDetailRequest qq: q.getGoodList()) {
                String[] remarks=new String[2];
                remarks[0]=qq.getRemark();
                remarks[1]=qq.getRemark2();
                goodIdMap.put(qq.getGoodId(), remarks);

            }
        }

        if (count != goodIdMap.keySet().size()) {
            throw new NetPlusException("结算商品信息异常：存在重复商品");
        }

        return goodIdMap;
    }

    private Map<String, BigDecimal> getGoodPriceMap (String ponopk, String isMallOutProvider, List<GoodsVo> goodsVoList) {

        Map<String, String> matrlIdAndSkuMap = new HashMap();
        goodsVoList.forEach(g -> matrlIdAndSkuMap.put(g.getMatrlid(), g.getGoodno()));

        GetGoodLatestPriceRequest getGoodLatestPriceRequest = new GetGoodLatestPriceRequest();
        getGoodLatestPriceRequest.setPonopk(ponopk);
        getGoodLatestPriceRequest.setMatrlIdAndSkuMap(matrlIdAndSkuMap);
        getGoodLatestPriceRequest.setIsMallOutProvider(isMallOutProvider);
        List<GoodLatestPriceVo> goodLatestPriceVoList = ifaceBiz.getGoodLatestPrice(getGoodLatestPriceRequest);

        Map<String, BigDecimal>  goodLatestPriceVoMap = goodLatestPriceVoList
                .stream()
                .collect(Collectors.
                        toMap(GoodLatestPriceVo::getMatrlIdOrSku, t -> t.getPrice()));

        return goodLatestPriceVoMap;
    }

    private BigDecimal getPrice(GoodsVo goodsVo, Map<String, BigDecimal> goodPriceMap, String isMallOutProvider) {

        BigDecimal price;

        if (YesNoEnum.Yes.getValue().equals(isMallOutProvider)) {
            price = goodPriceMap.get(goodsVo.getGoodno());

            if (ObjectUtil.isEmpty(price)) {
                throw new NetPlusException(String.format("物料条码:%s的商品震坤行已下架，获取不到价格", goodsVo.getMatrltm()));
            }

        }else{
            price = goodPriceMap.get(goodsVo.getMatrlid());

            if (ObjectUtil.nonEmpty(price)) {

                if (NumberUtil.gt(price, goodsVo.getPrice())) {
                    price = goodsVo.getPrice();
                }

            }else  {
                throw new NetPlusException(String.format("物料条码:%s的商品获取不到仓库最新价格", goodsVo.getMatrltm()));
            }
        }


        return price;

    }

    private List<Tbmqq441Vo> setLowPriceAndUnit (List<Tbmqq441Vo> tbmqq441VoList) {

        //获取同类最低价
        Set<String> matrlTmSet = tbmqq441VoList
                .stream()
                .map( g -> g.getMatrltm())
                .collect(Collectors.toSet());

        GetPriceCompareRequest getPriceCompareRequest = new GetPriceCompareRequest();
        getPriceCompareRequest.setMatrltmList(new ArrayList(matrlTmSet));
        List<GoodsVo> goodsVoList = goodsListedBiz.getPriceCompare(getPriceCompareRequest);

        Map<String, GoodsVo> goodGroupData = goodsVoList
                .stream()
                .collect(Collectors.toMap(
                        g -> g.getMatrltm(),
                        Function.identity(),
                        BinaryOperator.minBy(Comparator.comparing(GoodsVo :: getPrice))
                ));


        for (Tbmqq441Vo tbmqq441Vo: tbmqq441VoList) {

            if (goodGroupData.keySet().contains(tbmqq441Vo.getMatrltm())) {
                GoodsVo lowGoodsVo = goodGroupData.get(tbmqq441Vo.getMatrltm());

                //这里需要和当前价格在做一次比对，当前价格是最新的
                if (NumberUtil.lt(lowGoodsVo.getPrice(), tbmqq441Vo.getPrice())) {
                    tbmqq441Vo.setLowprice(lowGoodsVo.getPrice());
                    tbmqq441Vo.setLowqtyunit(lowGoodsVo.getQtyunit());
                    tbmqq441Vo.setIsLowPrice(YesNoEnum.No.getValue());
                }else{
                    tbmqq441Vo.setLowprice(tbmqq441Vo.getPrice());
                    tbmqq441Vo.setLowqtyunit(tbmqq441Vo.getQtyunit());
                    tbmqq441Vo.setIsLowPrice(YesNoEnum.Yes.getValue());
                }
            }
        }

        return tbmqq441VoList;
    }

    private void checkDeviceApply (List<Tbmqq441Vo> tbmqq441VoList) {

        List<String> deviceApplyNoList = tbmqq441VoList
                .stream()
                .filter( t -> !StringUtils.isEmpty(t.getDeviceapplyno()))
                .map( t-> t.getDeviceapplyno())
                .collect(Collectors.toList());

        if (deviceApplyNoList.size() > 0) {

            List<String> usedDeviceApplyPkList = tbmqq441Mapper.getUsedDeviceApply(deviceApplyNoList);
            if (usedDeviceApplyPkList.size() > 0) {
                throw new NetPlusException(String.format("新增设备申请单编号：%s商城已使用", String.join(",", usedDeviceApplyPkList)));
            }

            List<String> usedDeviceApplyNoList = new ArrayList();
            for (String deviceApplyNo: deviceApplyNoList) {

                GetDeviceRequest getDeviceRequest = new GetDeviceRequest();
                getDeviceRequest.setSearchKey(deviceApplyNo);
                PageBean<JK0006SubResponse> pageBean =  ifaceBiz.getDevice(getDeviceRequest);
                List<JK0006SubResponse> jk0006SubResponseList = pageBean.getItems();
                if (ObjectUtil.isEmpty(jk0006SubResponseList)) {
                    throw new NetPlusException(String.format("新增设备申请单编号%s不存在", deviceApplyNo));
                }else{

                    JK0006SubResponse jk0006SubResponse = jk0006SubResponseList.get(0);
                    if (!(OneOrZeroEnum.Zero.getValue().equals(jk0006SubResponse.getSfxz())
                            && "2".equals(jk0006SubResponse.getShzt()))) {

                        usedDeviceApplyNoList.add(deviceApplyNo);
                    }
                }
            }

            if (usedDeviceApplyNoList.size() > 0) {
                throw new NetPlusException(String.format("新增设备申请单编号：%s仓库已使用或审批中", String.join(",", usedDeviceApplyPkList)));
            }
        }
    }


    private boolean checkOverdue (String popriceStartDate, String popriceEndDate) {
        long nowLong = Long.valueOf(DateUtil.format(new Date(), "yyyyMMdd"));
        long popriceStartDateLong = Long.valueOf(popriceStartDate);
        long popriceEndDateLong = Long.valueOf(popriceEndDate);

        if (nowLong >= popriceStartDateLong && nowLong <= popriceEndDateLong) {
            return true;
        }else{
            return false;
        }
    }


    private Map<String, JK0010SubResponse> getCheckQtyResult (List<GoodsVo> goodsVoList) {

        //同物料id要合并统计,计算库存
        Map<String, BigDecimal> result = goodsVoList
                .stream()
                .collect(Collectors.groupingBy(
                            GoodsVo::getMatrlid,
                            Collectors.mapping(
                                GoodsVo::getCartQty,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                            )
                        )
                );


        List<GoodsVo> checkQtyGood = new ArrayList();
        for (Map.Entry<String, BigDecimal> entry: result.entrySet()) {

            GoodsVo goodsVo = new GoodsVo();
            goodsVo.setMatrlid(entry.getKey());
            goodsVo.setCartQty(entry.getValue());
            checkQtyGood.add(goodsVo);

        }

        return ifaceBiz.getQtyCheckResult(checkQtyGood);
    }
}
