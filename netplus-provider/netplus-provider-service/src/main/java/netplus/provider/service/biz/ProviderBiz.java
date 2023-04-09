package netplus.provider.service.biz;

import netplus.component.entity.enums.ConfigureEnum;
import netplus.component.entity.enums.JK0013Enum;
import netplus.component.entity.enums.YesNoEnum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.joint.erp.api.request.in.JK0013Request;
import netplus.joint.erp.api.request.out.JK0002Request;
import netplus.joint.erp.api.request.out.JK0034Request;
import netplus.joint.erp.api.request.out.JK0035Request;
import netplus.joint.erp.api.response.out.JK0002Response;
import netplus.joint.erp.api.response.out.JK0034.JK0034Response;
import netplus.joint.erp.api.response.out.JK0035.JK0035Response;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.provider.api.enums.UserTypeEnums;
import netplus.provider.api.pojo.ygmalluser.Tbdu01;
import netplus.provider.api.pojo.ygmalluser.Tbmqq422;
import netplus.provider.api.pojo.ygmalluser.Tbmqq422Key;
import netplus.provider.api.pojo.ygmalluser.Tbmqq461;
import netplus.provider.api.request.GetSupplierNoRequest;
import netplus.provider.api.request.LoginRequest;
import netplus.provider.api.request.SyncProviderRequest;
import netplus.provider.api.rest.IdentityRest;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.service.dao.Tbdu01Mapper;
import netplus.provider.service.dao.Tbmqq422Mapper;
import netplus.utils.date.NowDate;
import netplus.utils.object.ObjectUtil;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProviderBiz {

    private Log logger = LogFactory.getLog(ProviderBiz.class);


    @Autowired
    Tbdu01Mapper tbdu01Mapper;

    @Autowired
    IdentityRest identityRest;

    @Autowired
    ErpOutRest erpOutRest;

    @Autowired
    ServiceConfigBiz serviceConfigBiz;

    @Autowired
    Tbmqq422Mapper tbmqq422Mapper;


    @Transactional(rollbackFor = Exception.class)
    public void getYgUser() {

        //调用查询用户信息的接口
        BaseRequest<JK0002Request> re = new BaseRequest();
        re.setReqId(UuidUtil.getUuid());
        re.setReqData(new JK0002Request());
        re.setReqTime(String.valueOf(new Date().getTime()));
        BaseResponse<JK0002Response> response = erpOutRest.JK0002(re);

        //将数据库中已有的userno查出来usertype为B
        List<String> userNoList = tbdu01Mapper.selectAllBUserNo();


        List<Tbdu01> tbdu01Add = new ArrayList<>();
        List<Tbdu01> tbdu01Up = new ArrayList<>();
        List<String> newUserNo = new ArrayList<>();
        NowDate nowDate = new NowDate();

        //遍历接口返回的信息
        response.getRespData().getData().forEach(v -> {

            newUserNo.add(v.getOwnercode());

            Tbdu01 tbdu01 = new Tbdu01();
            tbdu01.setUserno(v.getOwnercode());
            tbdu01.setName(v.getOwnername());
            tbdu01.setUsertype(UserTypeEnums.B.getCode());
            tbdu01.setDepno(v.getBmbm_pk());
            tbdu01.setDepname(v.getMc());
            tbdu01.setDepnum(v.getBmbh());
            tbdu01.setComppk(v.getFgsbm_pk());
            tbdu01.setCompno(v.getDb());
            tbdu01.setCompname(v.getFgsmc());
            tbdu01.setPhone(v.getLxsj());
            tbdu01.setModifyuser(SysCodeEnum.CK.code);
            tbdu01.setModifydate(nowDate.getDateTimeStr3());
            tbdu01.setEmptype(v.getScyhlb());

            if (!userNoList.contains(v.getOwnercode())) {
                tbdu01.setCreateuser(SysCodeEnum.CK.code);
                tbdu01.setCreatedate(nowDate.getDateTimeStr3());
                tbdu01.setIsrole(YesNoEnum.Yes.getValue());
                tbdu01.setIsavailable(YesNoEnum.Yes.getValue());
                tbdu01Add.add(tbdu01);
            } else {
                tbdu01Up.add(tbdu01);
            }

        });

        //计算出需要删除的用户
        List<String> tbdu01Delete = userNoList
                .stream()
                .filter(item -> !newUserNo.contains(item))
                .collect(Collectors.toList());


        //执行删除操作
        if (tbdu01Delete.size() > 0) {

            int count = tbdu01Mapper.batchDelete(tbdu01Delete);
            if (count != tbdu01Delete.size()) {
                throw new NetPlusException("用户删除失败");
            }
        }

        //执行更新操作
        if (tbdu01Up.size() > 0) {
            for (Tbdu01 tbdu01 : tbdu01Up) {
                int count = tbdu01Mapper.batchUpdate(tbdu01);
                if (count != 1) {
                    throw new NetPlusException("用户更新失败");
                }
            }

        }
        //执行新增操作
        if (tbdu01Add.size() > 0) {

            int count = tbdu01Mapper.batchInsert(tbdu01Add);
            if (count != tbdu01Add.size()) {
                throw new NetPlusException("用户开通失败");
            }

            for (Tbdu01 tbdu01: tbdu01Add) {
                defaultRole(ConfigureEnum.ROLE_DEMP.code, nowDate, tbdu01.getUserno());
            }
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public void syncProvider (SyncProviderRequest syncProviderRequest) {
        NowDate nowDate = new NowDate();
        int count = tbdu01Mapper.batchInsert(syncProviderRequest.getTbdu01VoList());

        for (Tbdu01 tbdu01: syncProviderRequest.getTbdu01VoList()) {
            defaultRole(ConfigureEnum.ROLE_PROVIDER.code, nowDate, tbdu01.getUserno());
        }

        if(count !=syncProviderRequest.getTbdu01VoList().size()){
            throw new NetPlusException("供应商同步失败");
        }

        try{

            Tbmqq461 tbmqq461 = serviceConfigBiz.getServiceConfigByCode(ConfigureEnum.IS_SEND_DUANXIN.code);
            if (YesNoEnum.Yes.getValue().equals(tbmqq461.getVal())) {

                //发送短信
                for (Tbdu01 tbdu01: syncProviderRequest.getTbdu01VoList()) {

                    if (!StringUtils.isEmpty(tbdu01.getPhone())) {
                        Map<String, String> params = JK0013Enum.OPEN_ACCOUNT.getParams();
                        params.put("1", tbdu01.getUserno());
                        params.put("2", tbdu01.getPassword());

                        JK0013Request request = new JK0013Request();
                        request.setReceiver(Arrays.asList(tbdu01.getPhone()));
                        request.setTemplateId(JK0013Enum.OPEN_ACCOUNT.getTemplateId());
                        request.setTemplateParas(params);

                        BaseRequest<JK0013Request> jk0013RequestBaseRequest = new BaseRequest();
                        jk0013RequestBaseRequest.setReqId(UuidUtil.getUuid());
                        jk0013RequestBaseRequest.setReqData(request);
                        jk0013RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
                        erpOutRest.JK0013(jk0013RequestBaseRequest);
                    }
                }
            }

        }catch (Exception e) {
            logger.info(String.format("供应商开户发送短信失败: %s", e.getMessage()));
        }
    }


    private void defaultRole (String roleKey, NowDate nowDate, String userNo) {

        Tbmqq461 tbmqq461 = serviceConfigBiz.getServiceConfigByCode(roleKey);

        Tbmqq422Key tbmqq422Key = new Tbmqq422Key();
        tbmqq422Key.setRolecode(tbmqq461.getVal());
        tbmqq422Key.setUserno(userNo);
        Tbmqq422 existTbmqq422 = tbmqq422Mapper.selectByPrimaryKey(tbmqq422Key);

        if (ObjectUtil.isEmpty(existTbmqq422)) {
            Tbmqq422 tbmqq422 = new Tbmqq422();
            tbmqq422.setRolecode(tbmqq461.getVal());
            tbmqq422.setUserno(userNo);
            tbmqq422.setCreateuser(SysCodeEnum.CK.code);
            tbmqq422.setCreatetime(nowDate.getTimeStr());
            tbmqq422.setCreatedate(nowDate.getDateStr());
            tbmqq422.setUpdateuser(SysCodeEnum.CK.code);
            tbmqq422.setUpdatedate(nowDate.getDateStr());
            tbmqq422.setUpdatetime(nowDate.getTimeStr());
            tbmqq422Mapper.insert(tbmqq422);
        }
    }

    public Tbdu01Vo getSupplierNo(GetSupplierNoRequest request) {
        return tbdu01Mapper.getSupplierNo(request.getSupplierNo());
    }



    public Tbdu01Vo getSupplierByName(GetSupplierNoRequest request) {
        return tbdu01Mapper.getSupplierByCompName(request.getCompName());
    }

    public List<Tbdu01Vo> getSupplierNoList(List<String> supplierNoList) {
        return tbdu01Mapper.getSupplierNoList(supplierNoList);
    }

}
