package netplus.mall.service.biz;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import netplus.component.entity.data.KeyValue;
import netplus.component.entity.data.PageBean;
import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.OneOrZeroEnum;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.component.entity.response.ApiResponse;
import netplus.excel.api.request.ExcelRequest;
import netplus.excel.api.request.GenExcelRequest;
import netplus.excel.api.rest.ExcelParse;
import netplus.excel.api.rest.ExcelRest;
import netplus.excel.api.rest.ExportRest;
import netplus.excel.api.rest.GetExcelDataRequest;
import netplus.excel.api.vo.ExcelDataVo;
import netplus.excel.api.vo.UploadResultVo;
import netplus.joint.erp.api.request.out.JK0007Request;
import netplus.joint.erp.api.request.out.JK0031Request;
import netplus.joint.erp.api.request.out.JK0033Request;
import netplus.joint.erp.api.response.out.JK0007.JK0007Response;
import netplus.joint.erp.api.response.out.JK0007.JK0007SubResponse;
import netplus.joint.erp.api.response.out.JK0031.JK0031Response;
import netplus.joint.erp.api.response.out.JK0031.JK0031SubResponse;
import netplus.joint.erp.api.response.out.JK0033.JK0033Response;
import netplus.joint.erp.api.response.out.JK0033.JK0033SubResponse;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.mall.api.pojo.ygmalluser.*;
import netplus.mall.api.request.*;
import netplus.mall.api.request.basicData.*;
import netplus.mall.api.vo.CategoryVo;
import netplus.mall.api.vo.Tbmqq405Vo;
import netplus.mall.api.vo.Tbmqq406Vo;
import netplus.mall.api.vo.Tbmqq407Vo;
import netplus.mall.api.vo.basicData.CheckOrderConfigVo;
import netplus.mall.api.vo.basicData.ImportErpClaVo;
import netplus.mall.api.vo.basicData.ImportThrplatProductVo;
import netplus.mall.api.vo.basicData.ImportTwoLevelClaNameVo;
import netplus.mall.api.vo.picLib.CreatePicLibRequest;
import netplus.mall.service.dao.*;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.request.GetSupplierNoRequest;
import netplus.provider.api.rest.CommonRest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.rest.ProvideRest;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.utils.date.DateUtil;
import netplus.utils.date.NowDate;
import netplus.utils.excel.validation.Engine;
import netplus.utils.object.ObjectUtil;
import netplus.utils.pager.Pager;
import netplus.utils.uuid.UuidUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BasicDataBiz {

    @Autowired
    Tbmqq401Mapper  Tbmqq401Mapper;

    @Autowired
    Tbmqq402Mapper Tbmqq402Mapper;

    @Autowired
    Tbmqq403Mapper Tbmqq403Mapper;

    @Autowired
    Tbmqq405Mapper Tbmqq405Mapper;

    @Autowired
    Tbmqq407Mapper tbmqq407Mapper;

    @Autowired
    Tbmqq406Mapper tbmqq406Mapper;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    CommonRest commonRest;

    @Autowired
    ExcelRest excelRest;

    @Autowired
    ExportRest exportRest;

    @Autowired
    private ErpOutRest erpOutRest;

    @Autowired
    private ProvideRest provideRest;

    @Autowired
    private ServiceConfigRest serviceConfigRest;

    /**
     * 查询商品分类列表
     *
     * @param request
     * @return
     */
    public PageBean getClassifyList(GetBasicDataClassifyListRequest request) {
        PageBean pageBean = new PageBean();
        return pageBean;
    }

    /**
     * 添加商品分类列表
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse addClassifyData(AddBasicDataClassifyRequest request) {

        Date now = new Date();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

        // 物料分类没有输入时，查询参数的物料分类设置成空串
        if (ObjectUtil.isEmpty(request.getErpclaname())) {
            mapParam.put("erpclaname", "");
        }
        

        // 二级分类查找
        Tbmqq403 tbmqq403 = Tbmqq403Mapper.getTwoLevelClanameInfo(mapParam);

        // 二级分类不存在时，新增
        if (ObjectUtil.isEmpty(tbmqq403)) {
            // 二级分类添加
            tbmqq403 = new Tbmqq403();
            tbmqq403.setOnelevelclaname(request.getOnelevelclaname());
            tbmqq403.setTwolevelclaname(request.getTwolevelclaname());
            tbmqq403.setIsactive("Y");
            tbmqq403.setCreateuser(loginUserBean.getUserCode());
            tbmqq403.setCreatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq403.setCreatetime(DateUtil.format(now, "HHmmss"));
            tbmqq403.setUpdateuser(loginUserBean.getUserCode());
            tbmqq403.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq403.setUpdatetime(DateUtil.format(now, "HHmmss"));
            int add403count = Tbmqq403Mapper.insert(tbmqq403);
            if (add403count != 1) {
                throw new NetPlusException("二级分类数据添加失败！");
            }
        }

        // 一级分类查找
        Tbmqq402 tbmqq402 = Tbmqq402Mapper.getOneLevelClanameInfo(mapParam);

        // 一级分类不存在时，新增
        if (ObjectUtil.isEmpty(tbmqq402)) {
            // 一级分类添加
            tbmqq402 = new Tbmqq402();
            tbmqq402.setCategoryname(request.getCategoryname());
            tbmqq402.setOnelevelclaname(request.getOnelevelclaname());
            tbmqq402.setIsactive("Y");
            tbmqq402.setCreateuser(loginUserBean.getUserCode());
            tbmqq402.setCreatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq402.setCreatetime(DateUtil.format(now, "HHmmss"));
            tbmqq402.setUpdateuser(loginUserBean.getUserCode());
            tbmqq402.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq402.setUpdatetime(DateUtil.format(now, "HHmmss"));
            int add402count = Tbmqq402Mapper.insert(tbmqq402);
            if (add402count != 1) {
                throw new NetPlusException("一级分类数据添加失败！");
            }
        }
        return ApiResponse.success();
    }

    /**
     * 修改商品分类列表
     *
     * @param request
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public ApiResponse modifyClassifyData(ModifyBasicDataClassifyRequest request) {

        Date now = new Date();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);

        // 一级分类查找
        Tbmqq402 tbmqq402 = Tbmqq402Mapper.getOneLevelClanameInfo(mapParam);
        // 修改后的一级分类不存在时,新增处理执行
        if (ObjectUtil.isEmpty(tbmqq402)) {
            // 一级分类添加
            tbmqq402 = new Tbmqq402();
            tbmqq402.setCategoryname(request.getCategoryname());
            tbmqq402.setOnelevelclaname(request.getOnelevelclaname());
            tbmqq402.setIsactive("Y");
            tbmqq402.setCreateuser(loginUserBean.getUserCode());
            tbmqq402.setCreatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq402.setCreatetime(DateUtil.format(now, "HHmmss"));
            tbmqq402.setUpdateuser(loginUserBean.getUserCode());
            tbmqq402.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq402.setUpdatetime(DateUtil.format(now, "HHmmss"));
            int add402count = Tbmqq402Mapper.insert(tbmqq402);
            if (add402count != 1) {
                throw new NetPlusException("一级分类数据添加失败！");
            }
        }

        // 二级分类查找
        Tbmqq403 tbmqq403 = Tbmqq403Mapper.getTwoLevelClanameInfo(mapParam);
        // 修改后的二级分类不存在时，新增处理执行
        if (ObjectUtil.isEmpty(tbmqq403)) {
            // 二级分类添加
            tbmqq403 = new Tbmqq403();
            tbmqq403.setOnelevelclaname(request.getOnelevelclaname());
            tbmqq403.setTwolevelclaname(request.getTwolevelclaname());
            tbmqq403.setIsactive("Y");
            tbmqq403.setCreateuser(loginUserBean.getUserCode());
            tbmqq403.setCreatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq403.setCreatetime(DateUtil.format(now, "HHmmss"));
            tbmqq403.setUpdateuser(loginUserBean.getUserCode());
            tbmqq403.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
            tbmqq403.setUpdatetime(DateUtil.format(now, "HHmmss"));
            int add403count = Tbmqq403Mapper.insert(tbmqq403);
            if (add403count != 1) {
                throw new NetPlusException("二级分类数据添加失败！");
            }
        }

        // 物料分类没有输入时，查询参数的物料分类设置成空串
        if (ObjectUtil.isEmpty(request.getErpclaname())) {
            mapParam.put("erpclaname", "");
        }
       
        return ApiResponse.success();
    }


    public PageBean getMaterialList(GetBasicDataMaterialListRequest request) {
        PageBean pageBean = new PageBean();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        List<Tbmqq405Vo> tbmqq406List= Tbmqq405Mapper.getMaterialList(mapParam);
        int count = Tbmqq405Mapper.getCount(mapParam);
        List<KeyValue> twoLevelClaNameKV = Tbmqq405Mapper.getTwoLevelClaNameKV(mapParam);

        pageBean.setTotalCount(count);
        pageBean.setItems(tbmqq406List);
        pageBean.addResultMap("twoLevelClaNameKV", twoLevelClaNameKV);

        return pageBean;
    }

    public PageBean getSkuMaterialList(GetBasicDataMaterialListRequest request){
        PageBean pageBean = new PageBean();

        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        List<Tbmqq406Vo> tbmqq406List= tbmqq406Mapper.getSkuMaterialList(mapParam);
        int count = tbmqq406Mapper.getCount(mapParam);
        pageBean.setTotalCount(count);
        pageBean.setItems(tbmqq406List);
        return pageBean;
    }

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse modifyMaterialData(ModifyBasicDataMaterialRequest request) {

        if (StringUtils.isEmpty(request.getMatrltm())) {
            throw new NetPlusException("物料条码不能为空！");
        }

        if (StringUtils.isEmpty(request.getTwolevelclaname())) {
            throw new NetPlusException("二级分类不能为空！");
        }
        Map<String,Object> tMap=new HashMap<>();
        tMap.put("twolevelclaname",request.getTwolevelclaname());
        tMap.put("thrplatgoodno",request.getThrplatgoodno());
        int tCount= Tbmqq405Mapper.selectTwoClaname(tMap);
        if(tCount<1){
            throw new NetPlusException("二级分类不存在，无法修改！");
        }
        int gCount= tbmqq406Mapper.selectGoodNo(tMap);
        if(gCount<1){
            throw new NetPlusException("第三方货号不存在，无法修改！");
        }

        Date now = new Date();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Tbmqq405 tbmqq405 = new Tbmqq405();
        tbmqq405.setMatrltm(request.getMatrltm());
        tbmqq405.setMatrldesc(request.getMatrldesc());
        tbmqq405.setProductname(request.getProductname());
        tbmqq405.setSpec(request.getSpec());
        tbmqq405.setQuality(request.getQuality());
        tbmqq405.setUnit(request.getUnit());
        tbmqq405.setTwolevelclaname(request.getTwolevelclaname());
        tbmqq405.setUpdateuser(loginUserBean.getUserCode());

        tbmqq405.setUpdatedate(DateUtil.format(now, "yyyyMMdd"));
        tbmqq405.setUpdatetime(DateUtil.format(now, "HHmmss"));
        Tbmqq406 tbmqq406=new Tbmqq406();
        tbmqq406.setIsactive(request.getIsactive());
        tbmqq406.setThrplatgoodno(request.getThrplatgoodno());
        tbmqq406.setMatrltm(request.getMatrltm());
        tbmqq406.setSuppliername(request.getSuppliername());

        int modify405count = Tbmqq405Mapper.updateByPrimaryKeySelective(tbmqq405);
        int modif406count=tbmqq406Mapper.update406ByPrimaryKeySelective(tbmqq406);
        if (modify405count != 1&&modif406count!=1) {
            throw new NetPlusException(String.format(
                    "物料条码：%s, 二级分类：%s更新失败",
                    tbmqq405.getMatrltm(), tbmqq405.getTwolevelclaname()
            ));
        }

        return ApiResponse.success();
    }


    @Transactional(rollbackFor = Exception.class)
    public void importErpCla(ImportErpClaRequest request) {

        if (ObjectUtil.isEmpty(request.getBatchCode())) {
            throw new NetPlusException("导入批次好不能为空");

        }

        if (StringUtils.isEmpty(request.getFileUrl())) {
            throw new NetPlusException("导入文件地址不能为空");
        }

        ExcelRequest excelRequest = new ExcelRequest();
        excelRequest.setBatchCode(request.getBatchCode());
        excelRequest.setFilePath(request.getFileUrl());
        excelRequest.setStartRowNum(0);

        UploadResultVo uploadResultVo = excelRest.uploadExcel(excelRequest);
        if (uploadResultVo.getTotal().intValue() <= 0) {
            throw new NetPlusException("excel内容不能为空");
        }

        GetExcelDataRequest getExcelDataRequest = new GetExcelDataRequest();
        getExcelDataRequest.setBatchCode(request.getBatchCode());
        ExcelDataVo excelDataVo = excelRest.getExcelData(getExcelDataRequest);

        List<ImportErpClaVo> vos = ExcelParse.parseItems(excelDataVo, ImportErpClaVo.class);
        vos.forEach(Engine::validate);


        int errCount = vos.stream().filter( v -> !v.getIsValid()).collect(Collectors.toList()).size();
        Set<String> erpClaNameSet = vos.stream().map( v -> v.getErpClaName().getValue()).collect(Collectors.toSet());
        Set<String> twoLevelNameSet = vos.stream().map( v -> v.getTwoLevelClaName().getValue()).collect(Collectors.toSet());
        Set<String> oneLevelNameSet = vos.stream().map( v -> v.getOneLevelClaName().getValue()).collect(Collectors.toSet());
        Set<String> categoryNameSet = vos.stream().map(v -> v.getCategoryName().getValue()).collect(Collectors.toSet());


        if (errCount > 0) {
            throw new NetPlusException("模版数据不正确，请检查填写数据");
        }

        if (erpClaNameSet.size() != vos.size()) {
            throw new NetPlusException("REP物料分类重复");
        }

        if (twoLevelNameSet.size() != vos.size()) {
            throw new NetPlusException("二级分类重复");
        }



        Map<String, Object> getCategoryNameListMap = new HashMap();
        getCategoryNameListMap.put("categoryNameList", categoryNameSet);
        List<String> existCategoryNameList = Tbmqq401Mapper.getCategoryNameList(getCategoryNameListMap);

        if (existCategoryNameList.size() != categoryNameSet.size()) {
            throw new NetPlusException("部分大类不存在");
        }


        NowDate nowDate = new NowDate();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> getErpClaAndTwoLevelClaMap = new HashMap();
        getErpClaAndTwoLevelClaMap.put("erpClaNameList", erpClaNameSet);
       

        //判断二级分类哪些是存在不存在，不存在新增
        Map<String, Object> getTwoLevelClaAndOneLevelClaMap = new HashMap();
        getTwoLevelClaAndOneLevelClaMap.put("twoLevelClaNameList", twoLevelNameSet);
        List<Tbmqq403> existTbmqq403 = Tbmqq403Mapper.getTwoLevelClaAndOneLevelClaList(getTwoLevelClaAndOneLevelClaMap);

        List<Tbmqq403> insert403List = new ArrayList();
        for (ImportErpClaVo vo: vos) {

            boolean isInsert = true;
            for (Tbmqq403 t: existTbmqq403) {

                if (t.getOnelevelclaname().equals(vo.getOneLevelClaName().getValue())
                        && t.getTwolevelclaname().equals(vo.getTwoLevelClaName().getValue())) {

                    isInsert = false;
                    break;
                }

            }

            if (isInsert) {

                Tbmqq403 tbmqq403 = new Tbmqq403();
                tbmqq403.setOnelevelclaname(vo.getOneLevelClaName().getValue());
                tbmqq403.setTwolevelclaname(vo.getTwoLevelClaName().getValue());
                tbmqq403.setIsactive("Y");
                tbmqq403.setCreatedate(nowDate.getDateStr());
                tbmqq403.setCreatetime(nowDate.getTimeStr());
                tbmqq403.setCreateuser(loginUserBean.getUserCode());
                tbmqq403.setUpdatedate(nowDate.getDateStr());
                tbmqq403.setUpdatetime(nowDate.getTimeStr());
                tbmqq403.setUpdateuser(loginUserBean.getUserCode());

                insert403List.add(tbmqq403);
                existTbmqq403.add(tbmqq403);

            }
        }

        //判断一级分类哪些是存在不存在，不存在新增
        Map<String, Object> getOneLevelClaAndCategoryMap = new HashMap();
        getOneLevelClaAndCategoryMap.put("oneLevelClaNameList", oneLevelNameSet);
        List<Tbmqq402> existTbmqq402 = Tbmqq402Mapper.getOneLevelClaAndCategory(getOneLevelClaAndCategoryMap);

        List<Tbmqq402> insert402List = new ArrayList();
        for (ImportErpClaVo vo: vos) {

            boolean isInsert = true;
            for (Tbmqq402 t: existTbmqq402) {

                if (t.getCategoryname().equals(vo.getCategoryName().getValue())
                        && t.getOnelevelclaname().equals(vo.getOneLevelClaName().getValue())) {

                    isInsert = false;
                    break;
                }
            }

            if (isInsert) {
                Tbmqq402 tbmqq402 = new Tbmqq402();
                tbmqq402.setCategoryname(vo.getCategoryName().getValue());
                tbmqq402.setOnelevelclaname(vo.getOneLevelClaName().getValue());
                tbmqq402.setIsactive("Y");
                tbmqq402.setCreatedate(nowDate.getDateStr());
                tbmqq402.setCreatetime(nowDate.getTimeStr());
                tbmqq402.setCreateuser(loginUserBean.getUserCode());
                tbmqq402.setUpdatedate(nowDate.getDateStr());
                tbmqq402.setUpdatetime(nowDate.getTimeStr());
                tbmqq402.setUpdateuser(loginUserBean.getUserCode());

                insert402List.add(tbmqq402);
                existTbmqq402.add(tbmqq402);
            }
        }


        if (insert402List.size() > 0) {
            Tbmqq402Mapper.batchInsert(insert402List);
        }

        if (insert403List.size() > 0) {
            Tbmqq403Mapper.batchInsert(insert403List);
        }

        
    }

    @Transactional(rollbackFor = Exception.class)
    public void importTwoLevelCla(ImportTwoLevelClaNameRequest request) {

        if (ObjectUtil.isEmpty(request.getBatchCode())) {
            throw new NetPlusException("导入批次好不能为空");

        }

        if (StringUtils.isEmpty(request.getFileUrl())) {
            throw new NetPlusException("导入文件地址不能为空");
        }

        ExcelRequest excelRequest = new ExcelRequest();
        excelRequest.setBatchCode(request.getBatchCode());
        excelRequest.setFilePath(request.getFileUrl());
        excelRequest.setStartRowNum(0);

        UploadResultVo uploadResultVo = excelRest.uploadExcel(excelRequest);
        if (uploadResultVo.getTotal().intValue() <= 0) {
            throw new NetPlusException("excel内容不能为空");
        }

        GetExcelDataRequest getExcelDataRequest = new GetExcelDataRequest();
        getExcelDataRequest.setBatchCode(request.getBatchCode());
        ExcelDataVo excelDataVo = excelRest.getExcelData(getExcelDataRequest);

        List<ImportTwoLevelClaNameVo> vos = ExcelParse.parseItems(excelDataVo, ImportTwoLevelClaNameVo.class);
        vos.forEach(Engine::validate);

        int errCount = vos.stream().filter( v -> !v.getIsValid()).collect(Collectors.toList()).size();
        Set<String> matrlnoSet = vos.stream().map( v -> v.getMatrlno().getValue()).collect(Collectors.toSet());

        if (errCount > 0) {
            throw new NetPlusException("模版数据不正确，请检查填写数据");
        }

        if (matrlnoSet.size() != vos.size()) {
            throw new NetPlusException("物料号重复");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        List<Tbmqq403> twoLevelClaList = Tbmqq403Mapper.getAllTwoLevelInfoList();
        List<String> twoLevelClaNameList = twoLevelClaList
                .stream()
                .map( t -> t.getTwolevelclaname())
                .collect(Collectors.toList());


        for (ImportTwoLevelClaNameVo v: vos) {
            if (!twoLevelClaNameList.contains(v.getTwoLevelClaName().getValue())) {
                throw new NetPlusException(String.format(
                        "二级分类：%s不存在",
                        v.getTwoLevelClaName().getValue()
                ));
            }
        }


        List<String> existMatrlNoList = new ArrayList();
        List<Tbmqq405> insertList = new ArrayList();
        List<Tbmqq405> updateList = new ArrayList();
        NowDate nowDate = new NowDate();

        for (ImportTwoLevelClaNameVo v: vos) {
            if (existMatrlNoList.contains(v.getMatrlno().getValue())) {
                Tbmqq405 tbmqq405 = new Tbmqq405();
                tbmqq405.setUpdatedate(nowDate.getDateStr());
                tbmqq405.setUpdateuser(loginUserBean.getUserCode());
                tbmqq405.setProductname(v.getProductName().getValue());
                tbmqq405.setUnit(v.getUnit().getValue());
                tbmqq405.setSpec(v.getSpec().getValue());
                tbmqq405.setUpdatetime(nowDate.getTimeStr());
                tbmqq405.setMatrlno(v.getMatrlno().getValue());
                tbmqq405.setTwolevelclaname(v.getTwoLevelClaName().getValue());

                updateList.add(tbmqq405);
            }else{
                Tbmqq405 tbmqq405 = new Tbmqq405();
                tbmqq405.setMatrlno(v.getMatrlno().getValue());
                tbmqq405.setTwolevelclaname(v.getTwoLevelClaName().getValue());
                tbmqq405.setProductname(v.getProductName().getValue());
                tbmqq405.setUnit(v.getUnit().getValue());
                tbmqq405.setSpec(v.getSpec().getValue());
                tbmqq405.setUpdatedate(nowDate.getDateStr());

                tbmqq405.setUpdateuser(loginUserBean.getUserCode());
                tbmqq405.setUpdatetime(nowDate.getTimeStr());
                tbmqq405.setCreatedate(nowDate.getDateStr());
                tbmqq405.setCreateuser(loginUserBean.getUserCode());
                tbmqq405.setCreatetime(nowDate.getTimeStr());

                try {

                    ObjectUtil.blank(tbmqq405);
                    insertList.add(tbmqq405);

                    existMatrlNoList.add(v.getMatrlno().getValue());
                }catch (Exception e) {
                    throw new NetPlusException("数据转换失败");
                }
            }
        }

        if (insertList.size() > 0) {
            Tbmqq405Mapper.batchInsert(insertList);
        }

        for (Tbmqq405 t: updateList) {
            int count = Tbmqq405Mapper.updateByPrimaryKeySelective(t);
            if (count != 1) {
                throw new NetPlusException(String.format(
                        "物料号：%s, 二级分类：%s更新失败",
                        t.getMatrlno(), t.getTwolevelclaname()
                ));
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void importThrplatProduct(ImportThrplatProductRequest request) {

        if (ObjectUtil.isEmpty(request.getBatchCode())) {
            throw new NetPlusException("导入批次好不能为空");

        }

        if (StringUtils.isEmpty(request.getFileUrl())) {
            throw new NetPlusException("导入文件地址不能为空");
        }

        ExcelRequest excelRequest = new ExcelRequest();
        excelRequest.setBatchCode(request.getBatchCode());
        excelRequest.setFilePath(request.getFileUrl());
        excelRequest.setStartRowNum(0);

        UploadResultVo uploadResultVo = excelRest.uploadExcel(excelRequest);
        if (uploadResultVo.getTotal().intValue() <= 0) {
            throw new NetPlusException("excel内容不能为空");
        }

        GetExcelDataRequest getExcelDataRequest = new GetExcelDataRequest();
        getExcelDataRequest.setBatchCode(request.getBatchCode());
        ExcelDataVo excelDataVo = excelRest.getExcelData(getExcelDataRequest);

        List<ImportThrplatProductVo> vos = ExcelParse.parseItems(excelDataVo, ImportThrplatProductVo.class);
        vos.forEach(Engine::validate);

        int errCount = vos.stream().filter( v -> !v.getIsValid()).collect(Collectors.toList()).size();
        if (errCount > 0) {
            throw new NetPlusException("请检查填写数据");
        }

        NowDate nowDate = new NowDate();
        Map<String, String> supplierMap = new HashMap();

        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.MALL_OUT_SUPPLIER.code);
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        List<Tbmqq405Vo> tbmqq405VoList = Tbmqq405Mapper.getAllMatrlList();

        Map<String, Tbmqq405Vo> tbmqq405VoMap = tbmqq405VoList
                .stream()
                .collect(Collectors.toMap(Tbmqq405Vo::getMatrltm, t -> t));

        for (ImportThrplatProductVo v: vos) {


            if (supplierMap.keySet().contains(v.getSupplierName().getValue())) {

                v.getSupplierName().setValueExt(supplierMap.get(v.getSupplierName().getValue()));

            }else{
                GetSupplierNoRequest getSupplierNoRequest = new GetSupplierNoRequest();
                getSupplierNoRequest.setCompName(v.getSupplierName().getValue());
                Tbdu01Vo tbdu01Vo = provideRest.getSupplierByName(getSupplierNoRequest);

                if (ObjectUtil.isEmpty(tbdu01Vo)) {
                    throw new NetPlusException(String.format("供应商：%s不存在", v.getSupplierName().getValue()));
                }else{
                    v.getSupplierName().setValueExt(tbdu01Vo.getCompno());
                    supplierMap.put(v.getSupplierName().getValue(), tbdu01Vo.getCompno());
                }
            }


            if (!tbmqq461.getVal().equals(v.getSupplierName().getValueExt())) {
                throw new NetPlusException(String.format("供应商：%s不是第三方", v.getSupplierName().getValue()));
            }

            if (!tbmqq405VoMap.keySet().contains(v.getMatrlTm().getValue())) {
                throw new NetPlusException(String.format("物料条码：%s不存在", v.getMatrlTm().getValue()));
            }else{
                //ValueExt是物料id值
                v.getMatrlTm().setValueExt(tbmqq405VoMap.get(v.getMatrlTm().getValue()).getMatrlid());
            }

        }

        List<Tbmqq406Key> tbmqq406KeyList = tbmqq406Mapper.getTbmqq406Key();
        List<Tbmqq406> insertList = new ArrayList();
        List<Tbmqq406> updateList = new ArrayList();

        for (ImportThrplatProductVo v: vos) {

            boolean isInsert = true;

            if(ObjectUtil.nonEmpty(tbmqq406KeyList)) {
                for (Tbmqq406Key k : tbmqq406KeyList) {

                    if (k.getMatrlid().equals(v.getMatrlTm().getValueExt())
                            && k.getSupplierno().equals(v.getSupplierName().getValueExt())) {

                        isInsert = false;
                        break;
                    }
                }
            }
            if (isInsert) {

                Tbmqq405Vo tbmqq405Vo = tbmqq405VoMap.get(v.getMatrlTm().getValue());

                Tbmqq406 tbmqq406 = new Tbmqq406();
                tbmqq406.setSupplierno(v.getSupplierName().getValueExt());
                tbmqq406.setSuppliername(v.getSupplierName().getValue());
                tbmqq406.setThrplatgoodno(v.getGoodNo().getValue());
                tbmqq406.setThrplatunit(v.getUnit().getValue());
                tbmqq406.setThrplatproductname(v.getProductName().getValue());
                tbmqq406.setThrplatquality(v.getQuality().getValue());
                tbmqq406.setThrplatspec(v.getSpec().getValue());
                tbmqq406.setUpdatetime(nowDate.getTimeStr());
                tbmqq406.setUpdateuser(loginUserBean.getUserCode());
                tbmqq406.setUpdatedate(nowDate.getDateStr());
                tbmqq406.setMatrltm(v.getMatrlTm().getValue());
                tbmqq406.setCreatetime(nowDate.getTimeStr());
                tbmqq406.setCreateuser(loginUserBean.getUserCode());
                tbmqq406.setCreatedate(nowDate.getDateStr());
                tbmqq406.setMatrlno(tbmqq405Vo.getMatrlno());
                tbmqq406.setMatrlid(tbmqq405Vo.getMatrlid());
                tbmqq406.setIsactive("Y");
                insertList.add(tbmqq406);
            }else{

                Tbmqq406 tbmqq406 = new Tbmqq406();
                tbmqq406.setSupplierno(v.getSupplierName().getValueExt());
                tbmqq406.setSuppliername(v.getSupplierName().getValue());
                tbmqq406.setMatrlid(v.getMatrlTm().getValueExt());
                tbmqq406.setMatrltm(v.getMatrlTm().getValue());
                tbmqq406.setThrplatgoodno(v.getGoodNo().getValue());
                tbmqq406.setThrplatunit(v.getUnit().getValue());
                tbmqq406.setThrplatproductname(v.getProductName().getValue());
                tbmqq406.setThrplatquality(v.getQuality().getValue());
                tbmqq406.setThrplatspec(v.getSpec().getValue());

                tbmqq406.setUpdatetime(nowDate.getTimeStr());
                tbmqq406.setUpdateuser(loginUserBean.getUserCode());
                tbmqq406.setUpdatedate(nowDate.getDateStr());
                updateList.add(tbmqq406);
            }
        }

        if (insertList.size() > 0) {
            tbmqq406Mapper.batchInsert(insertList);
        }

        for (Tbmqq406 t: updateList) {

            int count = tbmqq406Mapper.updateByPrimaryKeySelective(t);
            if (count != 1) {
                throw new NetPlusException(String.format(
                        "物料条码：%s, 供应商：%, 货号：%s, 更新失败",
                        t.getMatrltm(),
                        t.getSuppliername(),
                        t.getThrplatgoodno()
                ));
            }
        }

    }

    public CategoryVo getCategoryByGoodsno(String goodsNo){
        return tbmqq406Mapper.getCategoryByGoodsno(goodsNo);
    }

    public List<Tbmqq405Vo> getAllMatrlList () {
        return Tbmqq405Mapper.getAllMatrlList();
    }

    public List<Tbmqq405Vo> getMaterialInfo (GetMaterialInfoRequest request) {

        return Tbmqq405Mapper.getMaterialInfo(ObjectUtil.transBeanToMap(request));

    }

    public List<String> getCategoryNameList (GetCategoryNameListRequest request) {
        return Tbmqq401Mapper.getCategoryNameList(ObjectUtil.transBeanToMap(request));
    }

    public List<String> getOneLevelClaNameList (GetCategoryNameListRequest request) {
        return Tbmqq402Mapper.getOneLevelClaNameList(ObjectUtil.transBeanToMap(request));
    }

    public List<String> getTwoLevelClaNameList (GetCategoryNameListRequest request) {
        return Tbmqq403Mapper.getTwoLevelClaNameList(ObjectUtil.transBeanToMap(request));
    }
    public String exportClassifyData(GetBasicDataClassifyListRequest request) throws IOException {
        Map<String, Object> map = ObjectUtil.transBeanToMap(request);
        return "";
    }


    public Tbmqq406Vo getMatrlNoByGoodNo(GetMatrlNoRequest request) {
        return tbmqq406Mapper.getMatrlNoByGoodNo(ObjectUtil.transBeanToMap(request));
    }

    public List<Tbmqq406> getGoodNoByMatrlNo(Map<String,Object> map){
        return tbmqq406Mapper.getMatrlNo(map);
    }

    public List<Tbmqq406> getMatrlNo(Map<String,Object> map){

        return tbmqq406Mapper.getMatrlNo(map);
    }

    public List<Tbmqq405> getMatrl405No(Map<String,Object> map){

        return Tbmqq405Mapper.getMatrlNo(map);
    }
    public String exportThrplatData(GetBasicDataMaterialListRequest request) throws IOException {
        Map<String, Object> map = ObjectUtil.transBeanToMap(request);
        List<Tbmqq406Vo> vos =tbmqq406Mapper.getSkuMaterialList(map);
        String tempName = "tmp_thrplat_data";
        for(Tbmqq406Vo vo:vos){
            if(vo.getUpdatedate()!=null){
                vo.setUpdatedate(DateUtil.format(vo.getUpdatedate()+vo.getUpdatetime(),"yyyyMMddHHmmss","yyyy-MM-dd HH:mm:ss"));
            }
            if(vo.getIsactive()!=null){
                vo.setIsactive(vo.getIsactive().equals("Y")?"启用":"禁用");
            }
        }
        GenExcelRequest genExcelRequest = new GenExcelRequest();
        genExcelRequest.setItems(vos);
        genExcelRequest.setTemplateName(tempName);
        genExcelRequest.setSheetName("第三方平台基础数据导出");
        String url = exportRest.genExcel(genExcelRequest);
        return url;
    }

    public PageBean<Tbmqq407Vo> getBasicPicLib(GetBasicDataMaterialListRequest request){
        PageBean<Tbmqq407Vo> pageBean=new PageBean<>();
        Map<String,Object> map=ObjectUtil.transBeanToMap(request);
        List<Tbmqq407Vo> list=tbmqq407Mapper.selectBasicPicLib(map);
        int count =tbmqq407Mapper.selectPicCount(map);
        pageBean.setItems(list);
        pageBean.setTotalCount(count);
        return  pageBean;
    }

    public ApiResponse createBasicPicLib(CreatePicLibRequest request){

        ApiResponse response=new ApiResponse();

        int picNameIndex=request.getPictureName().indexOf(".");

        String picName=request.getPictureName().substring(0,picNameIndex);

        int index = picName.indexOf("_");

        if(index<0){
            response.setStatus(0);
            response.setData(picName);
            response.setMessage("图片命名不规范");
            return response;
        }

        String matrlTm = request.getPictureName().substring(0, index);//物料条码

        String picturenum=request.getPictureName().substring(index+1,picNameIndex);//序号

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> map = new HashMap();
        map.put("matrlTm", matrlTm);
        map.put("pictureNum",picturenum);

        //查看数据库是否有相同的数据
        List<Tbmqq407Vo> tbmqq407Vos=tbmqq407Mapper.getBasicPicLib(map);
        Tbmqq407 tbmqq407=new Tbmqq407();
        NowDate date=new NowDate();
        if(ObjectUtil.isEmpty(tbmqq407Vos)){
            //如果为空新增数据

            tbmqq407.setPicturenum(picturenum);
            tbmqq407.setMatrltm(matrlTm);
            tbmqq407.setMatrlno(" ");
            tbmqq407.setMatrlid(" ");
            tbmqq407.setCreatedate(date.getDateStr());
            tbmqq407.setCreatetime(date.getTimeStr());
            tbmqq407.setUpdatedate(date.getDateStr());
            tbmqq407.setUpdatetime(date.getTimeStr());
            tbmqq407.setPictureurl(request.getPictureUrl());
            tbmqq407.setPicturename(request.getPictureName());
            tbmqq407.setCreateuser(loginUserBean.getUserCode());
            tbmqq407.setUpdateuser(loginUserBean.getUserCode());
            int count=tbmqq407Mapper.insert(tbmqq407);
            if(count!=1){
                response.setStatus(0);
                response.setData(picName);
                response.setMessage("上传失败");
                return response;
            }
        }else {

            //更新图片
            tbmqq407.setPicturenum(picturenum);
            tbmqq407.setMatrltm(matrlTm);
            tbmqq407.setMatrlno(" ");
            tbmqq407.setMatrlid(" ");
            tbmqq407.setPicturename(request.getPictureName());
            tbmqq407.setCreateuser(tbmqq407Vos.get(0).getCreateuser());
            tbmqq407.setCreatedate(tbmqq407Vos.get(0).getCreatedate());
            tbmqq407.setCreatetime(tbmqq407Vos.get(0).getCreatetime());
            tbmqq407.setPictureurl(request.getPictureUrl());
            tbmqq407.setUpdateuser(loginUserBean.getUserCode());
            tbmqq407.setUpdatedate(date.getDateStr());
            tbmqq407.setUpdatetime(date.getTimeStr());
            int count=tbmqq407Mapper.updateByPrimaryKey(tbmqq407);
            if(count<1){
                response.setStatus(0);
                response.setData(picName);
                response.setMessage("上传失败");
                return response;
            }

        }
        response.setStatus(1);
        response.setData(picName);
        response.setMessage("上传成功");
        return response;
    }


    @Transactional(rollbackFor = Exception.class)
    public void delBasicPic (DelBasicPicRequest request) {

        for (Tbmqq407Key k: request.getTbmqq407KeyList()) {
            int count = tbmqq407Mapper.deleteByPrimaryKey(k);

            if (count !=1) {
                throw new NetPlusException("删除失败");
            }

        }
    }


    @Transactional(rollbackFor = Exception.class)
    public void syncBasicData () {

        NowDate now = new NowDate();
        JK0007Response jk0007Response = getBasicList(1, 1);

        int pageSize = 100;
        int totalPage = jk0007Response.getTotal() / pageSize;
        int remain = jk0007Response.getTotal() % pageSize;
        if (remain > 0) {
            totalPage  = totalPage + 1;
        }


        log.info(String.format("总条数：%d条, 共%d页", jk0007Response.getTotal(), totalPage));


        Map<String,  Tbmqq401> tbmqq401Map = new HashMap();
        Map<String,  Tbmqq402> tbmqq402Map = new HashMap();
        Map<String,  Tbmqq403> tbmqq403Map = new HashMap();
        List<Tbmqq405> tbmqq405List = new ArrayList();

        for (int i = 1; i <= totalPage; i++) {

            JK0007Response resp = getBasicList(pageSize, i);
            List<JK0007SubResponse> jk0007SubResponseList = resp.getRows();

            log.info(String.format("第%d页获取数据：%d条", i, jk0007SubResponseList.size()));


            for (JK0007SubResponse j: jk0007SubResponseList) {

                //物料信息
                Tbmqq405 tbmqq405 = new Tbmqq405();
                tbmqq405.setMatrlid(j.getWzmcbm_pk());
                tbmqq405.setMatrltm(j.getWztm());
                tbmqq405.setMatrlno(j.getWzbm());
                tbmqq405.setMatrldesc(j.getWzmc());
                tbmqq405.setProductname(j.getWzmc());
                tbmqq405.setSpec(j.getGgxh());
                tbmqq405.setQuality(" ");
                tbmqq405.setIsactive(YesNoEnum.Yes.getValue());
                tbmqq405.setUnit(j.getJldw());
                tbmqq405.setTwolevelclaname(j.getLbmc3());
                tbmqq405.setTwolevelclapk(j.getLbbm_pk3());
                tbmqq405.setCreatedate(now.getDateStr());
                tbmqq405.setCreatetime(now.getTimeStr());
                tbmqq405.setCreateuser(SysCodeEnum.CK.code);
                tbmqq405.setUpdatedate(now.getDateStr());
                tbmqq405.setUpdatetime(now.getTimeStr());
                tbmqq405.setUpdateuser(SysCodeEnum.CK.code);
                tbmqq405List.add(tbmqq405);

                //二级分类
                Tbmqq403 tbmqq403 = new Tbmqq403();
                tbmqq403.setTwolevelclapk(j.getLbbm_pk3());
                tbmqq403.setTwolevelclaname(j.getLbmc3());
                tbmqq403.setOnelevelclapk(j.getLbbm_pk2());
                tbmqq403.setOnelevelclaname(j.getLbmc2());
                tbmqq403.setIsactive(YesNoEnum.Yes.getValue());
                tbmqq403.setCreatedate(now.getDateStr());
                tbmqq403.setCreatetime(now.getTimeStr());
                tbmqq403.setCreateuser(SysCodeEnum.CK.code);
                tbmqq403.setUpdatedate(now.getDateStr());
                tbmqq403.setUpdatetime(now.getTimeStr());
                tbmqq403.setUpdateuser(SysCodeEnum.CK.code);
                tbmqq403.setSortnum(j.getLbxh3());
                tbmqq403Map.put(j.getLbbm_pk3(), tbmqq403);


                //一级分类
                Tbmqq402 tbmqq402 = new Tbmqq402();
                tbmqq402.setOnelevelclapk(j.getLbbm_pk2());
                tbmqq402.setOnelevelclaname(j.getLbmc2());
                tbmqq402.setCategoryname(j.getLbmc1());
                tbmqq402.setCategorypk(j.getLbbm_pk1());
                tbmqq402.setIsactive(YesNoEnum.Yes.getValue());
                tbmqq402.setCreatedate(now.getDateStr());
                tbmqq402.setCreatetime(now.getTimeStr());
                tbmqq402.setCreateuser(SysCodeEnum.CK.code);
                tbmqq402.setUpdatedate(now.getDateStr());
                tbmqq402.setUpdatetime(now.getTimeStr());
                tbmqq402.setUpdateuser(SysCodeEnum.CK.code);
                tbmqq402.setSortnum(j.getLbxh2());
                tbmqq402Map.put(j.getLbbm_pk2(), tbmqq402);

                //大类
                Tbmqq401 tbmqq401 = new Tbmqq401();
                tbmqq401.setCategoryname(j.getLbmc1());
                tbmqq401.setIcon(" ");
                tbmqq401.setCategorypk(j.getLbbm_pk1());
                tbmqq401.setIsactive(YesNoEnum.Yes.getValue());
                tbmqq401.setCreatedate(now.getDateStr());
                tbmqq401.setCreatetime(now.getTimeStr());
                tbmqq401.setCreateuser(SysCodeEnum.CK.code);
                tbmqq401.setUpdatedate(now.getDateStr());
                tbmqq401.setUpdatetime(now.getTimeStr());
                tbmqq401.setUpdateuser(SysCodeEnum.CK.code);
                tbmqq401.setSortnum(j.getLbxh1());
                tbmqq401Map.put(j.getLbbm_pk1(), tbmqq401);
            }
        }

        Tbmqq405Mapper.deleteAll();
        Tbmqq403Mapper.deleteAll();
        Tbmqq402Mapper.deleteAll();
        Tbmqq401Mapper.deleteAll();


        if (tbmqq405List.size() > 0) {

            Map<String, List<Tbmqq405>> d = tbmqq405List.stream().collect(Collectors.groupingBy(Tbmqq405::getMatrlid));

            for (Map.Entry<String, List<Tbmqq405>> entry: d.entrySet()) {

                if (entry.getValue().size() >= 2) {
                    throw new NetPlusException(String.format("物料id：%s，明细：%s", entry.getKey(), new Gson().toJson(entry.getValue())));
                }

            }

            Pager<Tbmqq405> pager = new Pager(tbmqq405List, 100);
            for (int i=1; i<= pager.getTotalPage(); i++) {
                Tbmqq405Mapper.batchInsert(pager.getPageData(i));
            }
        }

        if (tbmqq403Map.keySet().size() > 0) {


            List<Tbmqq403> tbmqq403List = tbmqq403Map
                    .keySet()
                    .stream()
                    .map(t -> tbmqq403Map.get(t))
                    .collect(Collectors.toList());



            Pager<Tbmqq403> pager = new Pager(tbmqq403List, 100);
            for (int i=1; i<= pager.getTotalPage(); i++) {
                Tbmqq403Mapper.batchInsert(pager.getPageData(i));
            }
        }

        if (tbmqq402Map.keySet().size() > 0) {

            List<Tbmqq402> tbmqq402List = tbmqq402Map
                    .keySet()
                    .stream()
                    .map(t -> tbmqq402Map.get(t))
                    .collect(Collectors.toList());


            Pager<Tbmqq402> pager = new Pager(tbmqq402List, 100);
            for (int i=1; i<= pager.getTotalPage(); i++) {
                Tbmqq402Mapper.batchInsert(pager.getPageData(i));
            }

        }

        if (tbmqq401Map.keySet().size() > 0) {

            List<Tbmqq401> tbmqq401List = tbmqq401Map
                    .keySet()
                    .stream()
                    .map(t -> tbmqq401Map.get(t))
                    .collect(Collectors.toList());

            Tbmqq401Mapper.batchInsert(tbmqq401List);
        }
    }

    public JK0007Response getBasicList (int pageSize, int pageIndex) {

        BaseRequest<JK0007Request> q = new BaseRequest();
        JK0007Request jk0007Request = new JK0007Request();
        jk0007Request.setPageIndex(pageIndex);
        jk0007Request.setPageSize(pageSize);

        q.setReqData(jk0007Request);
        q.setReqId(UuidUtil.getUuid());
        q.setReqTime(String.valueOf(new Date().getTime()));
        BaseResponse<JK0007Response> resp = erpOutRest.JK0007(q);

        if (resp.getStatus().equals(OneOrZeroEnum.One.getValue())) {
            return resp.getRespData();
        }else{
            throw new NetPlusException(resp.getMessage());
        }

    }

    public List<Tbmqq405Vo> getMatrlByIdsAndSupplierNo (GetMatrlByIdsAndSupplierNoRequest request) {
        return Tbmqq405Mapper.getMatrlByIdsAndSupplierNo(ObjectUtil.transBeanToMap(request));
    }

    public int getPicCountByMatrlTm(String matrlTm){
        Map<String,Object> map=new HashMap<>();
        map.put("matrlTm",matrlTm);
        return tbmqq407Mapper.selectPicCount(map);
    }

    public void batchInsert(List<Tbmqq407> list) {
        tbmqq407Mapper.batchInsert(list);
    }

    public int insert(Tbmqq407 tbmqq407){
        return tbmqq407Mapper.insert(tbmqq407);
    }

    public CheckOrderConfigVo getCheckOrderConfig() {
        CheckOrderConfigVo vo=new CheckOrderConfigVo();

        Tbmqq461 deptNo = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.SPECIAL_CHECK_OUT_DEPT_NO.code);
        if(!ObjectUtils.isEmpty(deptNo)){
            vo.setDeptNo(deptNo.getVal());
        }

        Tbmqq461 category = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.SPECIAL_CHECK_OUT_CATEGORY.code);
        if(!ObjectUtils.isEmpty(category)){
            vo.setCategory(category.getVal());
        }
        return vo;
    }

    public List<JK0031SubResponse> getCostDept() {
        BaseRequest<JK0031Request> baseReq = new BaseRequest();
        JK0031Request jk0031Request = new JK0031Request();
        baseReq.setReqData(jk0031Request);
        baseReq.setReqId(UuidUtil.getUuid());
        baseReq.setReqTime(String.valueOf(new Date().getTime()));
        BaseResponse<JK0031Response> response = erpOutRest.JK0031(baseReq);
        if (response.getStatus().equals("1")) {
            return response.getRespData().getData();
        }else{
            throw new NetPlusException(response.getMessage());
        }
    }

    public List<JK0033SubResponse> getMatrlQualityByMatrlId(GetMatrlQualityByMatrlIdRequest request) {
        return getMatrlQualityByMatrlId(request.getMatrlIdList());
    }

    public List<JK0033SubResponse> getMatrlQualityByMatrlId(List<String> matrlIdList) {
        BaseRequest<JK0033Request> baseReq = new BaseRequest();
        JK0033Request jk0033Request = new JK0033Request();
        jk0033Request.setWzmcbm_pk_list(matrlIdList);
        if(matrlIdList.isEmpty()){
            return new ArrayList<>();
        }
        baseReq.setReqData(jk0033Request);
        baseReq.setReqId(UuidUtil.getUuid());
        baseReq.setReqTime(String.valueOf(new Date().getTime()));
        BaseResponse<JK0033Response> response = erpOutRest.JK0033(baseReq);
        if (response.getStatus().equals("1")) {
            return response.getRespData().getData();
        }else{
            throw new NetPlusException(response.getMessage());
        }
    }

    /**
     * 根据物料ID判断是否可以加入购物车或下单
     * @param user
     * @param matrlIdList
     * @return
     */
    public boolean checkMatrlQualityAndDeptByMatrlId(LoginUserBean user, List<String> matrlIdList){
        if(ObjectUtils.isEmpty(matrlIdList)){
            return true;
        }
        //通过接口获取需要处理的商品的物资定性
        List<JK0033SubResponse> matrlQualityByMatrlId = this.getMatrlQualityByMatrlId(matrlIdList);
        return checkMatrlQualityAndDept(user, matrlQualityByMatrlId);
    }

    public boolean checkMatrlQualityAndDept(LoginUserBean user, List<JK0033SubResponse> matrlQualityByMatrlId){
        //获取配置的物资定性编码
        Tbmqq461 tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.SPECIAL_MATRL_QUALITY_PK.code);
        List<String> matrlQualityPk = Arrays.asList(tbmqq461.getVal().split(","));

        //如果不是配置的物资定性则可以正常处理
        long count = matrlQualityByMatrlId.stream().filter(e -> matrlQualityPk.contains(e.getWzdxbm_pk())).count();
        if(count == 0){
            return true;
        }

        //是配置的物资定性则需要检查是否为配置的部门
        //获取配置的部门
        tbmqq461 = serviceConfigRest.getServiceConfigByCode(ConfigureEnum.SPECIAL_MATRL_QUALITY_DEPT_NO.code);

        //如果是配置的部门则可以正常处理
        List<String> deptNoList = Arrays.asList(tbmqq461.getVal().split(","));
        if(deptNoList.contains(user.getDeptNo())){
            return true;
        }

        return false;
    }


}