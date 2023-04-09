package netplus.joint.zkh.out.service.biz;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import netplus.cache.api.rest.CacheRest;
import netplus.component.entity.aspect.Tbmqq460Mapper;
import netplus.component.entity.aspect.Tbmqq460WithBLOBs;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.SysCodeEnum;
import netplus.component.http.EasyHttpClient;
import netplus.component.http.HttpApiRequest;
import netplus.component.http.HttpApiResponse;
import netplus.joint.zkh.api.request.out.TokenRequest;
import netplus.joint.zkh.api.response.BaseResponse;
import netplus.joint.zkh.api.response.out.TokenResponse;
import netplus.provider.api.rest.ServiceConfigRest;
import netplus.utils.date.DateUtil;
import netplus.utils.json.JsonUtil;
import netplus.utils.object.ObjectUtil;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.Date;

@Service
public class ZkhTokenBiz {


    private static Log logger = LogFactory.getLog(ZkhTokenBiz.class);


    @Value("${zkh.url}")
    private String url;

    @Value("${zkh.clientId}")
    private String clientId;

    @Value("${zkh.clientSecret}")
    private String clientSecret;

    @Value("${zkh.username}")
    private String username;

    @Value("${zkh.password}")
    private String password;

    @Autowired
    CacheRest cacheRest;

    @Autowired
    EasyHttpClient easyHttpClient;

    @Autowired
    ServiceConfigRest serviceConfigRest;


    @Autowired
    Tbmqq460Mapper tbmqq460Mapper;


    public static final Gson gson = JsonUtil.JsonInstance;

    public String getAccessToken() {
        String zkh = cacheRest.get("zkhToken");
        if (ObjectUtil.nonEmpty(zkh)) {
            return zkh;
        }

        TokenRequest tokenRequest = new TokenRequest();
        tokenRequest.setClient_id(clientId);
        tokenRequest.setClient_secret(clientSecret);
        tokenRequest.setTimestamp(DateUtil.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        tokenRequest.setUsername(username);
        tokenRequest.setPassword(password);

        HttpApiResponse httpApiResponse = getHttpApiResponse("accessToken", "accessToken", gson.toJson(tokenRequest));
        Type type = new TypeToken<BaseResponse<TokenResponse>>() {}.getType();
        BaseResponse<TokenResponse> tokenResponse = gson.fromJson(httpApiResponse.getData(), type);

        if (tokenResponse.getSuccess()) {
            cacheRest.set(
                    "zkhToken",
                    tokenResponse.getResult().getAccess_token(),
                    tokenResponse.getResult().getExpires_in() / 1000L
            );

            return tokenResponse.getResult().getAccess_token();
        } else {
            throw new NetPlusException(tokenResponse.getResultMessage());
        }

    }


    public HttpApiResponse getHttpApiResponse(String urlSuffix, String apiName, String content) {

        logger.info(url + urlSuffix);
        logger.info(String.format("content: %s", content));


        Tbmqq460WithBLOBs tbmqq460WithBLOBs = new Tbmqq460WithBLOBs();
        tbmqq460WithBLOBs.setReqid(UuidUtil.getUuid());
        tbmqq460WithBLOBs.setCallee(SysCodeEnum.ZKH.code);
        tbmqq460WithBLOBs.setCaller(SysCodeEnum.PT.code);
        tbmqq460WithBLOBs.setRequrl(String.format("%s%s", url, urlSuffix));

        tbmqq460WithBLOBs.setReqtime(DateUtil.getTimeStampStr());
        tbmqq460WithBLOBs.setReqname(apiName);
        tbmqq460WithBLOBs.setReqdata(content);

        tbmqq460WithBLOBs.setStatus("INIT");
        tbmqq460WithBLOBs.setTimes(Short.parseShort("0"));
        tbmqq460Mapper.insertSelective(tbmqq460WithBLOBs);

        try {

            HttpApiRequest httpApiRequest = new HttpApiRequest();
            httpApiRequest.setUrl(url + urlSuffix);
            httpApiRequest.setApiName(apiName);
            httpApiRequest.setContent(content);
            HttpApiResponse httpApiResponse = easyHttpClient.post(httpApiRequest);


            logger.info(String.format("result: %s", httpApiResponse.getData()));


            Tbmqq460WithBLOBs t = new Tbmqq460WithBLOBs();
            t.setReqid(tbmqq460WithBLOBs.getReqid());
            t.setCallee(tbmqq460WithBLOBs.getCallee());
            t.setCaller(tbmqq460WithBLOBs.getCaller());
            t.setResptime(DateUtil.getTimeStampStr());
            t.setRespdata(httpApiResponse.getData());

            if (httpApiResponse.getStatus().equals(200)) {
                t.setStatus("OK");
            }else{
                t.setStatus("FAIL");
            }

            tbmqq460Mapper.updateByPrimaryKeySelective(t);
            return httpApiResponse;


        }catch (Exception e) {


            Tbmqq460WithBLOBs t = new Tbmqq460WithBLOBs();
            t.setReqid(tbmqq460WithBLOBs.getReqid());
            t.setCallee(tbmqq460WithBLOBs.getCallee());
            t.setCaller(tbmqq460WithBLOBs.getCaller());
            t.setResptime(DateUtil.getTimeStampStr());
            t.setRespdata(org.apache.commons.lang.exception.ExceptionUtils.getFullStackTrace(e));
            t.setStatus("FAIL");
            tbmqq460Mapper.updateByPrimaryKeySelective(t);

            throw new NetPlusException(e.getMessage());
        }
    }


}
