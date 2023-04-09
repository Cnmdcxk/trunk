package netplus.mall.service.biz;

import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.response.ApiResponse;
import netplus.mall.api.enums.PictureTypeEnum;
import netplus.mall.api.pojo.ygmalluser.*;
import netplus.mall.api.request.basicData.InitBasicPicRequest;
import netplus.mall.api.request.picLib.GetPicByMatrlTmAndSupplierNoRequest;
import netplus.mall.api.request.picLib.GetPicLibListRequest;
import netplus.mall.api.vo.Tbmqq407Vo;
import netplus.mall.api.vo.picLib.*;
import netplus.mall.service.dao.Tbmqq407Mapper;
import netplus.mall.service.dao.Tbmqq429Mapper;
import netplus.provider.api.request.GetSupplierByScopeRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.LoginUserBean;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class PicLibBiz  {


    protected Log logger = LogFactory.getLog(PicLibBiz.class);

    @Autowired
    Tbmqq429Mapper tbmqq429Mapper;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    BasicDataBiz basicDataBiz;

    @Autowired
    Tbmqq407Mapper tbmqq407Mapper;


    public List<PicLibVo> getPicLibList (GetPicLibListRequest request) {

        List<PicLibVo> picLibVoList = new ArrayList();
        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        Map<String, Object> mapParam = ObjectUtil.transBeanToMap(request);
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());

        List<Tbmqq429Vo> tbmqq429VoList = tbmqq429Mapper.getPicLibList(mapParam);

        if (ObjectUtil.nonEmpty(tbmqq429VoList)) {

            Map<String, List<Tbmqq429Vo>> groupData = tbmqq429VoList
                    .stream()
                    .collect(Collectors.groupingBy(Tbmqq429Vo::getUpdatedate, LinkedHashMap::new, Collectors.toList()));

            for (Map.Entry<String, List<Tbmqq429Vo>> entry: groupData.entrySet()) {

                PicLibVo picLibVo = new PicLibVo();
                picLibVo.setCreateDate(entry.getKey());
                picLibVo.setUpdateDate(entry.getKey());
                picLibVo.setTbmqq429VoList(entry.getValue());

                picLibVoList.add(picLibVo);

            }

        }

        return picLibVoList;
    }

    public void updatePicLib (UpdatePicLibRequest request) {


        if (ObjectUtil.isEmpty(request.getTbmqq429List())) {
            throw new NetPlusException("图片信息不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();


        for (Tbmqq429 t: request.getTbmqq429List()) {

            Map<String, Object> getMatrlNoMap = new HashMap();
            getMatrlNoMap.put("matrlTm", t.getMatrltm());
            getMatrlNoMap.put("pictureType",t.getPicturetype());
            getMatrlNoMap.put("picturenum",t.getPicturenum());
            getMatrlNoMap.put("supplierNo",loginUserBean.getCompanyCode());

            NowDate date=new NowDate();
            //判断一下数据库中是否已存在该物料条码
            List<Tbmqq429Vo> matchMatrlNoPicList = tbmqq429Mapper.getPicByMatrlTmAndSupplierNo(getMatrlNoMap);
            if(matchMatrlNoPicList.size()>1){
                if(t.getDeleted().equals("N")){
                    getMatrlNoMap.put("deleted","N");
                    tbmqq429Mapper.delSame(getMatrlNoMap);
                }
                else{
                    getMatrlNoMap.put("deleted","Y");
                    tbmqq429Mapper.delSame(getMatrlNoMap);
                }
            }

            Tbmqq429 tbmqq429 = new Tbmqq429();
            tbmqq429.setSupplierno(loginUserBean.getCompanyCode());
            tbmqq429.setMatrltm(t.getMatrltm());
            tbmqq429.setPicturetype(t.getPicturetype());
            tbmqq429.setPicturenum(t.getPicturenum());
            tbmqq429.setDeleted(t.getDeleted());
            tbmqq429.setUpdatedate(date.getDateStr());
            tbmqq429.setUpdatetime(date.getTimeStr());
            tbmqq429.setUpdateuser(identityRest.getCurrentUser().getUserCode());
            int updateCount = tbmqq429Mapper.updateByPrimaryKeySelective(tbmqq429);
            if (updateCount != 1) {
                throw new NetPlusException("操作失败");
            }

        }

    }

    public void delPicLib (DelPicLibRequest request) {


        if (ObjectUtil.isEmpty(request.getTbmqq429List())) {
            throw new NetPlusException("图片信息不能为空");
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();
        for (Tbmqq429 t: request.getTbmqq429List()) {

            Tbmqq429Key tbmqq429Key = new Tbmqq429();
            tbmqq429Key.setSupplierno(loginUserBean.getCompanyCode());
            tbmqq429Key.setPicturetype(t.getPicturetype());
            tbmqq429Key.setPicturenum(t.getPicturenum());
            tbmqq429Key.setMatrltm(t.getMatrltm());
            tbmqq429Key.setDeleted(t.getDeleted());

            int deleteCount = tbmqq429Mapper.deleteByPrimaryKey(tbmqq429Key);
            if (deleteCount != 1) {

                throw new NetPlusException("删除失败");

            }

        }
    }


    public ApiResponse createPicLib (CreatePicLibRequest request) {

        ApiResponse response=new ApiResponse();
        int picNameIndex=request.getPictureName().indexOf(".");

        //获取图片名字
        String picName=request.getPictureName().substring(0,picNameIndex);

        //正则表达式
        String pattern=".*_[0-9]*";

        if(!Pattern.matches(pattern,picName)){
            response.setStatus(0);
            response.setData(picName);
            response.setMessage("图片命名不规范");
            return response;
        }
        int index = picName.indexOf("_");
        String  matrlTm =request.getPictureName().substring(0, index);
        String picturenum=request.getPictureName().substring(index+1,picNameIndex);
        LoginUserBean loginUserBean = identityRest.getCurrentUser();

        Map<String, Object> getMatrlNoMap = new HashMap();
        getMatrlNoMap.put("supplierNo", loginUserBean.getCompanyCode());
        getMatrlNoMap.put("matrlTm", matrlTm);
        getMatrlNoMap.put("pictureType",request.getPictureType());
        getMatrlNoMap.put("picturenum",picturenum);
        getMatrlNoMap.put("deleted","N");
        //判断一下数据库中是否已存在该物料条码
        List<Tbmqq429Vo> matchMatrlNoPicList = tbmqq429Mapper.getPicByMatrlTmAndSupplierNo(getMatrlNoMap);
        Tbmqq429 tbmqq429 = new Tbmqq429();

        if(ObjectUtil.nonEmpty(matchMatrlNoPicList)) {
            tbmqq429.setMatrlno(" ");
            tbmqq429.setGoodno(matchMatrlNoPicList.get(0).getGoodno());
            tbmqq429.setMatrlid(matrlTm);
            tbmqq429.setMatrltm(matrlTm);
            //查看同物料下是否已存在该序号
            getMatrlNoMap.put("matrlTm", matchMatrlNoPicList.get(0).getMatrltm());
            List<String> picNumList =tbmqq429Mapper.getMatrlNoPicNum(getMatrlNoMap);

            if(picNumList.contains(picturenum)){
                //如果序号已经存在则更新图片
                tbmqq429Mapper.updatePic(tbmqq429);
                response.setMessage("上传成功");
                response.setStatus(1);
                return response;
            }

        }else {
            tbmqq429.setMatrlno(" ");
            tbmqq429.setGoodno(" ");
            tbmqq429.setMatrltm(matrlTm);
            tbmqq429.setMatrlid(matrlTm);
        }

        Map<String, Object> mapParam =new HashMap<>();
        mapParam.put("supplierNo", loginUserBean.getCompanyCode());
        mapParam.put("matrlTm", matrlTm);

        NowDate nowDate = new NowDate();
        tbmqq429.setSupplierno(loginUserBean.getCompanyCode());
        tbmqq429.setPicturename(picName);
        tbmqq429.setPictureurl(request.getPictureUrl());
        tbmqq429.setDeleted("N");
        tbmqq429.setPicturenum(picturenum);
        tbmqq429.setPicturetype(request.getPictureType());
        tbmqq429.setCreatedate(nowDate.getDateStr());
        tbmqq429.setCreatetime(nowDate.getTimeStr());
        tbmqq429.setCreateuser(loginUserBean.getUserCode());
        tbmqq429.setUpdatedate(nowDate.getDateStr());
        tbmqq429.setUpdatetime(nowDate.getTimeStr());
        tbmqq429.setUpdateuser(loginUserBean.getUserCode());


        int uploadCount =tbmqq429Mapper.insertSelective(tbmqq429);
        if(uploadCount!=1){
            response.setStatus(0);
            response.setData(picName);
            response.setMessage("上传失败");
        }
        response.setStatus(1);
        response.setData(picName);
        response.setMessage("上传成功");
        return response;
    }

    @Transactional(rollbackFor = Exception.class)
    public void initBasicPic (InitBasicPicRequest request) {

        NowDate nowDate = new NowDate();
        List<String> supplierNoList = new ArrayList();
        if (!StringUtils.isEmpty(request.getScope())) {

            GetSupplierByScopeRequest q = new GetSupplierByScopeRequest();
            q.setTendArea(request.getScope());

        }

        if (!StringUtils.isEmpty(request.getSupplierNo())) {
            supplierNoList.add(request.getSupplierNo());
        }

        LoginUserBean loginUserBean = identityRest.getCurrentUser();


        Map<String, List<Tbmqq407>> groupData = new HashMap();


        Map<String, Object> delMap = new HashMap();
        delMap.put("matrlNoList", groupData.keySet());
        delMap.put("supplierNoList", supplierNoList);

        tbmqq429Mapper.delBySupplierNoAndMatrlno(delMap);

        for (Map.Entry<String, List<Tbmqq407>> entry: groupData.entrySet()) {


            List<Tbmqq407> tbmqq407List = entry.getValue();
            for (String supplierNo: supplierNoList) {

                List<Tbmqq429> tbmqq429List = tbmqq407List.stream().map( t-> {

                    Tbmqq429 tbmqq429 = new Tbmqq429();
                    tbmqq429.setMatrlno(t.getMatrlno());
                    tbmqq429.setPicturenum(t.getPicturenum());
                    tbmqq429.setPicturetype(PictureTypeEnum.MAIN.getCode());
                    tbmqq429.setSupplierno(supplierNo);
                    tbmqq429.setGoodno(" ");
                    tbmqq429.setDeleted("N");
                    tbmqq429.setPictureurl(t.getPictureurl());
                    tbmqq429.setPicturename(t.getPicturename());
                    tbmqq429.setCreatedate(nowDate.getDateStr());
                    tbmqq429.setCreatetime(nowDate.getTimeStr());
                    tbmqq429.setCreateuser(loginUserBean.getUserCode());
                    tbmqq429.setUpdatedate(nowDate.getDateStr());
                    tbmqq429.setUpdatetime(nowDate.getTimeStr());
                    tbmqq429.setUpdateuser(loginUserBean.getUserCode());

                    return tbmqq429;
                }).collect(Collectors.toList());


                tbmqq429Mapper.batchInsert(tbmqq429List);

            }
        }
    }

    public List<Tbmqq429Vo> getPicByMatrlTmAndSupplierNo(GetPicByMatrlTmAndSupplierNoRequest request){
        return tbmqq429Mapper.getPicByMatrlTmAndSupplierNo(ObjectUtil.transBeanToMap(request));
    }

    public List<Tbmqq407Vo> getPicByMatrlTm (List<String> matrlTmList) {
        return tbmqq407Mapper.getPicByMatrlTm(matrlTmList);
    }

    public void batchInsert(List<Tbmqq429> list){
        tbmqq429Mapper.batchInsert(list);
    }

    public void insertOne(Tbmqq429 obj){
        tbmqq429Mapper.insert(obj);
    }

}
