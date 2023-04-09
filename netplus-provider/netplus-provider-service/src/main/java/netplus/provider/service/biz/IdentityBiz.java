package netplus.provider.service.biz;

import com.google.gson.Gson;
import netplus.cache.api.rest.CacheRest;
import netplus.component.entity.enums.JK0013Enum;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.entity.response.ApiResponse;
import netplus.joint.erp.api.request.in.JK0013Request;
import netplus.joint.erp.api.request.out.JK0001Request;
import netplus.joint.erp.api.request.out.JK0024Request;
import netplus.joint.erp.api.request.out.JK0034Request;
import netplus.joint.erp.api.request.out.JK0035Request;
import netplus.joint.erp.api.response.out.JK0001Response;
import netplus.joint.erp.api.response.out.JK0013.JK0013Response;
import netplus.joint.erp.api.response.out.JK0024.JK0024Response;
import netplus.joint.erp.api.response.out.JK0024.JK0024SubResponse;
import netplus.joint.erp.api.response.out.JK0034.JK0034Response;
import netplus.joint.erp.api.response.out.JK0035.Data;
import netplus.joint.erp.api.response.out.JK0035.JK0035Response;
import netplus.joint.erp.api.rest.ErpOutRest;
import netplus.provider.api.pojo.ygmalluser.Tbdu01;
import netplus.provider.api.request.*;
import netplus.provider.api.vo.Tbdu01Vo;
import netplus.provider.service.dao.Tbdu01Mapper;
import netplus.utils.date.NowDate;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.util.*;
import java.util.regex.Pattern;

@Service
public class IdentityBiz {

    protected Log logger = LogFactory.getLog(IdentityBiz.class);

    @Autowired
    Tbdu01Mapper tbdu01Mapper;

    @Autowired
    ErpOutRest erpOutRest;

    @Autowired
    private CacheRest cacheRest;

    @Transactional(rollbackFor = Exception.class)
    public ApiResponse updateUserPwd(UpdateUserPwdRequest request) {
        Tbdu01Vo tbdu01Vo = tbdu01Mapper.getUserByUserNoAndPassword(request.getUserCode(), request.getPassword());
        if(ObjectUtils.isEmpty(tbdu01Vo)){
            throw new NetPlusException("账号密码错误，请重试");
        }

        String newPwd = request.getNewPwd();

        if(StringUtils.isEmpty(newPwd)){
            throw new NetPlusException("密码不能为空!");
        }

        String s = "[^A-Za-z0-9]";
        Pattern p = Pattern.compile(s);
        if(p.matcher(newPwd).find()){
            throw new NetPlusException("密码不能使用非法字符!");
        }

        if (!newPwd.equals(request.getConfirmPwd())) {
            throw new NetPlusException("密码与确认密码不一致");
        }

        if(newPwd.length() < 8 || newPwd.length() >16){
            throw new NetPlusException("密码长度必须为8-16个字符");
        }

        if(newPwd.equals(tbdu01Vo.getPassword())){
            throw new NetPlusException("新密码与旧密码重复!");
        }

        Tbdu01 tbdu01 = new Tbdu01();
        tbdu01.setUserno(request.getUserCode());
        tbdu01.setPassword(request.getNewPwd());
        tbdu01.setModifyuser(tbdu01Vo.getName());

        NowDate nowDate = new NowDate();
        tbdu01.setModifydate(nowDate.getDateStr()+nowDate.getTimeStr());

        int updateCount = tbdu01Mapper.updateByPrimaryKeySelective(tbdu01);
        if(updateCount != 1){
            throw new NetPlusException("修改失败,请重试!");
        }
        return ApiResponse.success();
    }

    public ApiResponse sendSmsCode(String mobile,String workType) {
        if(StringUtils.isEmpty(mobile)){
            throw new NetPlusException("手机号不能为空!");
        }

        String key = mobile+ "_" + workType;
        //判断是否错误次数过多
        getErrorTimes(key);

        //检查发送验证码间隔
        String oldCode = cacheRest.get(key);
        if(!StringUtils.isEmpty(oldCode)){
            if(System.currentTimeMillis()-Long.parseLong(oldCode.split("_")[1])<60000){
                throw new NetPlusException("发送验证码太频繁,请稍后重试!");
            }
        }
        //取到随机验证码
        String code = getrank();
        //设置发送短信模板及参数
        JK0013Enum register = JK0013Enum.REGISTER;
        Map<String, String> params = register.getParams();
        params.put("1",code);
        String reqId = UuidUtil.getUuid();

        //构造发送短信请求参数
        BaseRequest<JK0013Request> request = new BaseRequest<JK0013Request>();
        request.setReqId(reqId);
        request.setReqTime(String.valueOf(System.currentTimeMillis()));

        JK0013Request jk0013Request = new JK0013Request();
        jk0013Request.setReceiver(Arrays.asList(mobile));
        jk0013Request.setTemplateId(register.getTemplateId());
        jk0013Request.setTemplateParas(params);
        request.setReqData(jk0013Request);

        //发送短信
        BaseResponse<JK0013Response> response = erpOutRest.JK0013(request);
        //TODO:需验证返回的是不是本次发的请求,待接口调整
        /*if(!reqId.equals(response.getReqId())){
            throw new NetPlusException("验证码发送失败,请稍后重试!");
        }*/

        if(!"1".equals(response.getStatus())){
            throw new NetPlusException("验证码发送失败,请稍后重试!");
        }

        System.out.println("sendSmsCode:"+code);
        cacheRest.set(key,code+"_"+System.currentTimeMillis(),3 * 60L);

        return ApiResponse.success();
    }

    public ApiResponse retrievePasswordSendSmsCode(RetrievePwdSmsCodeRequest request) {
        String mobile = request.getMobile();
        if(StringUtils.isEmpty(mobile)){
            throw new NetPlusException("手机号不能为空!");
        }
        Tbdu01 tbdu01 = tbdu01Mapper.getSupplierByPhone(mobile);

        if(ObjectUtils.isEmpty(tbdu01)){
            throw new NetPlusException("手机号无对应供应商信息!");
        }
        return sendSmsCode(mobile,request.getWorkType());
    }

    /**
     * 生成随机号码
     *
     * @param
     * @return
     */
    public static String getrank() {
        String randomNumberSize = "0123456789";
        String randomNumber = "";
        for (int i = 6; i > 0; i--) {
            randomNumber += randomNumberSize.charAt((int) (Math.random() * 10));
        }
        return randomNumber;
    }

    public ApiResponse retrievePasswordCheckSmsCode(RetrievePwdSmsCodeRequest request) {
        String mobile = request.getMobile();
        if(StringUtils.isEmpty(mobile)){
            throw new NetPlusException("手机号不能为空!");
        }
        if(StringUtils.isEmpty(request.getMobileCode())){
            throw new NetPlusException("验证码不能为空!");
        }
        String key = mobile + "_" + request.getWorkType();

        //获取错误次数
        int errorTimes = getErrorTimes(key);

        String oldCode = cacheRest.get(key);
        if(StringUtils.isEmpty(oldCode)){
            throw new NetPlusException("未发送验证码或验证码已过期!");
        }

        if(!request.getMobileCode().equals(oldCode.split("_")[0])){
            errorTimes++;
            String errorKey = key + "errorTimes";
            cacheRest.set(errorKey,String.valueOf(errorTimes),5 * 60L);
            throw new NetPlusException("短信验证码无效，累计次数为：" + errorTimes);
        }

        //验证通过返回一个令牌用于修改密码
        String uuid = UUID.randomUUID().toString().replace("-", "");
        cacheRest.set(uuid, mobile, 5 * 60L);

        HashMap<String, String> map = new HashMap<>();
        map.put("sessionId",uuid);
        return ApiResponse.success(map);
    }

    private int getErrorTimes(String codeKey){
        String errorKey = codeKey + "errorTimes";
        String errorTimes = cacheRest.get(errorKey);
        int nTimes = Integer.parseInt(errorTimes == null ? "0" : errorTimes);
        if(nTimes>5){
            throw new NetPlusException("验证码错误次数太多,请稍后重试!");
        }
        return nTimes;
    }


    @Transactional(rollbackFor = Exception.class)
    public ApiResponse retrievePasswordUpdPassword(RetrievePwdUpdateRequest request) {
        String password = request.getPassword();
        if(StringUtils.isEmpty(password)){
            throw new NetPlusException("密码不能为空!");
        }

        String s = "[^A-Za-z0-9]";
        Pattern p = Pattern.compile(s);
        if(p.matcher(password).find()){
            throw new NetPlusException("密码不能使用非法字符!");
        }

        if(!password.equals(request.getConfirmPassword())){
            throw new NetPlusException("密码与确认密码不一致");
        }

        if(password.length() < 8 || password.length() >16){
            throw new NetPlusException("密码长度必须为8-16个字符");
        }

        String sessionId = request.getSessionId();

        String mobile = null;
        if(!StringUtils.isEmpty(sessionId)){
            mobile = cacheRest.get(sessionId);
        }

        if(StringUtils.isEmpty(mobile)){
            throw new NetPlusException("验证码已失效，请返回重试!");
        }

        int updateCount = tbdu01Mapper.updateSupplierPasswordByPhone(mobile, password);
        if(updateCount != 1){
            throw new NetPlusException("修改失败，请重试!");
        }
        cacheRest.remove(sessionId);
        return ApiResponse.success();
    }

    public Tbdu01Vo getUserByUserNo(String userNo) {
        return tbdu01Mapper.getUserByUserNo(userNo);
    }


    //永钢统一用户认证
    public void unifiedAuth (String password, String username) {
        JK0001Request jk0001Request = new JK0001Request();
        jk0001Request.setPassword(password);
        jk0001Request.setUsername(username);

        BaseRequest<JK0001Request> baseRequest = new BaseRequest();
        baseRequest.setReqId(UuidUtil.getUuid());
        baseRequest.setReqTime(String.valueOf(new Date().getTime()));
        baseRequest.setReqData(jk0001Request);
        BaseResponse<JK0001Response> resp = erpOutRest.JK0001(baseRequest);

        if (resp.getStatus().equals("0")) {
            throw new NetPlusException(resp.getMessage());
        }
    }

    //交互系统供应商认证
    public String providerAuth (String password, String username) {

        JK0024Request jk0024Request = new JK0024Request();
        jk0024Request.setLoginName(username);
        jk0024Request.setPassword(DigestUtils.md5DigestAsHex(password.getBytes()));

        BaseRequest<JK0024Request> baseRequest = new BaseRequest();
        baseRequest.setReqId(UuidUtil.getUuid());
        baseRequest.setReqTime(String.valueOf(new Date().getTime()));
        baseRequest.setReqData(jk0024Request);
        BaseResponse<JK0024Response> resp = erpOutRest.JK0024(baseRequest);

        if (resp.getStatus().equals("0")) {
            throw new NetPlusException(resp.getMessage());
        }

        String data = new Gson().toJson(resp.getRespData().getResultData());

        JK0024SubResponse jk0024SubResponse = new Gson().fromJson(data, JK0024SubResponse.class);

        return jk0024SubResponse.getCrmid();
    }


    @Transactional(rollbackFor = Exception.class)
    public ApiResponse updateSupplierBizContact(String userNo, UpdateBizContactRequest request) {
        if(StringUtils.isEmpty(request.getBizContact())){
            throw new NetPlusException("联系人不能为空!");
        }
        if(StringUtils.isEmpty(request.getBizContactPhone())){
            throw new NetPlusException("联系方式不能为空!");
        }

        String s = "^[1][3,4,5,6,7,8,9][0-9]{9}$";
        Pattern p = Pattern.compile(s);
        if(!p.matcher(request.getBizContactPhone()).matches()){
            throw new NetPlusException("联系方式请输入正确的手机号!");
        }

        Tbdu01 tbdu01 = new Tbdu01();
        tbdu01.setUserno(userNo);
        tbdu01.setBizcontact(request.getBizContact());
        tbdu01.setBizcontactphone(request.getBizContactPhone());
        int i = tbdu01Mapper.updateByPrimaryKeySelective(tbdu01);
        if(i != 1){
            throw new NetPlusException("修改供应商联系人失败!");
        }
        return ApiResponse.success();
    }

    public Map<String,String> getSupplierBizContact(GetSupplierBizContactRequest request) {
        return tbdu01Mapper.getSupplierBizContact(request);
    }

    public BaseResponse<JK0035Response> chatLogin(LoginRequest request){
        //获取accesstoken

        JK0034Request jk0034Request =new JK0034Request();
        BaseRequest<JK0034Request> baseRequest =new BaseRequest<>();
        jk0034Request.setAppId("500000141");
        jk0034Request.setScope("app");
        jk0034Request.setTimestamp(new Date().getTime());
        jk0034Request.setSecret("M8Nk2OqdVf3ARSStqKaJ");
        baseRequest.setReqId(UuidUtil.getUuid());
        baseRequest.setReqTime(String.valueOf(new Date().getTime()));
        baseRequest.setReqData(jk0034Request);
        BaseResponse<JK0034Response> jk0034ResponseBaseResponse = erpOutRest.JK0034(baseRequest);

        if(jk0034ResponseBaseResponse.getRespData().isSuccess()){
            JK0035Request jk0035Request =new JK0035Request();
            jk0035Request.setAppid(request.getAppId());
            jk0035Request.setTicket(request.getTicket());
            jk0035Request.setAccessToken(jk0034ResponseBaseResponse.getRespData().getData().getAccessToken());
            BaseRequest<JK0035Request> baseRequest1 =new BaseRequest<>();
            baseRequest1.setReqId(UuidUtil.getUuid());
            baseRequest1.setReqTime(String.valueOf(new Date().getTime()));
            baseRequest1.setReqData(jk0035Request);
            BaseResponse<JK0035Response> re = erpOutRest.JK0035(baseRequest1);
            if(!StringUtils.isEmpty(re.getRespData().getData().getAppid())){
                re.getRespData().getData().setRole("B");
            }else {
                re.getRespData().getData().setRole("S");
            }
            if(re.getRespData().isSuccess()){
                return re;
            }
        }
        return  BaseResponse.fail(UuidUtil.getUuid());
    }
}
