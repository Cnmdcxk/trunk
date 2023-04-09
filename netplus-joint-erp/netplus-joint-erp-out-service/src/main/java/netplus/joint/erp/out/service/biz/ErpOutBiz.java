package netplus.joint.erp.out.service.biz;

import com.google.gson.Gson;
import netplus.component.entity.data.KeyValue;
import netplus.component.entity.exceptions.NetPlusException;
import netplus.component.entity.iface.BaseRequest;
import netplus.component.entity.iface.BaseResponse;
import netplus.component.http.EasyHttpClient;
import netplus.component.http.HttpApiRequest;
import netplus.component.http.HttpApiResponse;
import netplus.joint.erp.api.request.in.JK0013Request;
import netplus.joint.erp.api.request.out.*;
import netplus.joint.erp.api.request.out.JK0010.JK0010Request;
import netplus.joint.erp.api.request.out.JK0011.JK0011Request;
import netplus.joint.erp.api.request.out.JK0012.JK0012Request;
import netplus.joint.erp.api.request.out.JK0018.JK0018Request;
import netplus.joint.erp.api.request.out.JK0018.JK0018SubRequest;
import netplus.joint.erp.api.request.out.JK0032.JK0032Request;
import netplus.joint.erp.api.response.out.JK0034.JK0034Response;
import netplus.joint.erp.api.response.out.JK0035.JK0035Response;
import netplus.joint.erp.api.response.out.*;
import netplus.joint.erp.api.response.out.JK0003.JK0003Response;
import netplus.joint.erp.api.response.out.JK0004.JK0004Response;
import netplus.joint.erp.api.response.out.JK0005.JK0005Response;
import netplus.joint.erp.api.response.out.JK0006.JK0006Response;
import netplus.joint.erp.api.response.out.JK0007.JK0007Response;
import netplus.joint.erp.api.response.out.JK0010.JK0010Response;
import netplus.joint.erp.api.response.out.JK0012.JK0012Response;
import netplus.joint.erp.api.response.out.JK0013.JK0013Response;
import netplus.joint.erp.api.response.out.JK0015.JK0015Response;
import netplus.joint.erp.api.response.out.JK0016.JK0016Response;
import netplus.joint.erp.api.response.out.JK0016.JK0016SubResponse;
import netplus.joint.erp.api.response.out.JK0017.JK0017Response;
import netplus.joint.erp.api.response.out.JK0017.JK0017SubResponse;
import netplus.joint.erp.api.response.out.JK0017.NodeList;
import netplus.joint.erp.api.response.out.JK0017.WorkFlowList;
import netplus.joint.erp.api.response.out.JK0022.JK0022Response;
import netplus.joint.erp.api.response.out.JK0024.JK0024Response;
import netplus.joint.erp.api.response.out.JK0028.JK0028Response;
import netplus.joint.erp.api.response.out.JK0031.JK0031Response;
import netplus.joint.erp.api.response.out.JK0033.JK0033Response;
import netplus.utils.json.JsonUtil;
import netplus.utils.uuid.UuidUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@Service
public class ErpOutBiz {

    protected Log logger = LogFactory.getLog(ErpOutBiz.class);


    public static final Gson gson = JsonUtil.JsonInstance;

    @Autowired
    EasyHttpClient easyHttpClient;

    @Value("${yg.jk0001}")
    private String jk0001;

    @Value("${yg.jk0002}")
    private String jk0002;

    @Value("${yg.jk0003}")
    private String jk0003;

    @Value("${yg.jk0004}")
    private String jk0004;

    @Value("${yg.jk0005}")
    private String jk0005;

    @Value("${yg.jk0006}")
    private String jk0006;

    @Value("${yg.jk0007}")
    private String jk0007;

    @Value("${yg.jk0013}")
    private String jk0013;

    @Value("${yg.jk0009}")
    private String jk0009;

    @Value("${yg.jk0010}")
    private String jk0010;

    @Value("${yg.jk0011}")
    private String jk0011;

    @Value("${yg.jk0012}")
    private String jk0012;

    @Value("${yg.jk0015}")
    private String jk0015;

    @Value("${yg.jk0016}")
    private String jk0016;

    @Value("${yg.jk0017}")
    private String jk0017;

    @Value("${yg.jk0018}")
    private String jk0018;

    @Value("${yg.jk0019}")
    private String jk0019;

    @Value("${yg.jk0022}")
    private String jk0022;

    @Value("${yg.jk0024}")
    private String jk0024;

    @Value("${yg.jk0027}")
    private String jk0027;

    @Value("${yg.jk0028}")
    private String jk0028;


    @Value("${yg.jk0030}")
    private String jk0030;

    @Value("${yg.jk0031}")
    private String jk0031;

    @Value("${yg.jk0032}")
    private String jk0032;

    @Value("${yg.jk0033}")
    private String jk0033;

    @Value("${yg.jk0034}")
    private String jk0034;

    @Value("${yg.jk0035}")
    private String jk0035;

    public BaseResponse<JK0001Response> JK0001(BaseRequest<JK0001Request> request) {

        String url= jk0001 + String.format(
                    "?username=%s&password=%s&SWCODE=ygck",
                    request.getReqData().getUsername(),
                    request.getReqData().getPassword()
                );

        HttpApiResponse httpResponse=getHttpApiResponse(url,"JK0001",gson.toJson(request));


        JK0001Response jk0001Response = gson.fromJson(httpResponse.getData(),JK0001Response.class);

        if (jk0001Response.getFlag().equals("0")) {

            return BaseResponse.success(request.getReqId(), jk0001Response);

        }else{
            return BaseResponse.fail(request.getReqId(), jk0001Response.getMessage(), jk0001Response);
        }

    }

    public BaseResponse<JK0002Response> JK0002(BaseRequest<JK0002Request> request){

        HttpApiResponse httpResponse=getHttpApiResponse(jk0002,"JK0002",gson.toJson(request));
        JK0002Response  response=gson.fromJson(httpResponse.getData(),JK0002Response.class);
        logger.info(String.format("httpResponse:%s",httpResponse.getData()));
        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }
    public BaseResponse<JK0003Response> JK0003(BaseRequest<JK0003Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0003, "JK0003", gson.toJson(request.getReqData()));
        JK0003Response response =gson.fromJson(httpResponse.getData(),JK0003Response.class);

        if (response.getCode().equals("200") && response.getData().size() > 0) {

            if (response.getData().size() > 0) {

                return BaseResponse.success(request.getReqId(), response);
            }else{
                return BaseResponse.fail(request.getReqId(),"返回数据为空", response);

            }

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0004Response> JK0004(BaseRequest<JK0004Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0004,"JK0004",gson.toJson(request.getReqData()));
        JK0004Response  response=gson.fromJson(httpResponse.getData(),JK0004Response.class);
        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0005Response> JK0005(BaseRequest<JK0005Request> request) {


        String url= jk0005 + String.format(
                "?pageNum=%s&pageSize=%s",
                request.getReqData().getPageIndex(),
                request.getReqData().getPageSize()
        );

        HttpApiResponse httpResponse=getHttpApiResponse(url,"JK0005",gson.toJson(request.getReqData()));
        JK0005Response  response=gson.fromJson(httpResponse.getData(),JK0005Response.class);
        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0006Response> JK0006(BaseRequest<JK0006Request> request) {
        HttpApiResponse httpResponse=getHttpApiResponse(jk0006,"JK0006",gson.toJson(request.getReqData()));
        JK0006Response response = gson.fromJson(httpResponse.getData(), JK0006Response.class);

        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0007Response> JK0007(BaseRequest<JK0007Request> request) {


        String url= jk0007 + String.format(
                "?pageNum=%s&pageSize=%s",
                request.getReqData().getPageIndex(),
                request.getReqData().getPageSize()
        );

        HttpApiResponse httpResponse=getHttpApiResponse(url,"JK0007",gson.toJson(request.getReqData()));
        JK0007Response  response=gson.fromJson(httpResponse.getData(),JK0007Response.class);
        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0009Response> JK0009(BaseRequest<JK0009Request> request) {


        HttpApiResponse httpResponse=getHttpApiResponse(jk0009,"JK0009",gson.toJson(request.getReqData()));
        JK0009Response  response=gson.fromJson(httpResponse.getData(),JK0009Response.class);
        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0010Response> JK0010(BaseRequest<JK0010Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0010,"JK0010",gson.toJson(request.getReqData()));
        JK0010Response response=gson.fromJson(httpResponse.getData(),JK0010Response.class);

        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0011Response> JK0011(BaseRequest<JK0011Request> request) {


        HttpApiResponse httpResponse=getHttpApiResponse(jk0011,"JK0011",gson.toJson(request.getReqData()));
        JK0011Response  response = gson.fromJson(httpResponse.getData(),JK0011Response.class);

        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }

    }

    public BaseResponse<JK0012Response> JK0012(BaseRequest<JK0012Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0012,"JK0012",gson.toJson(request.getReqData()));
        JK0012Response  response=gson.fromJson(httpResponse.getData(),JK0012Response.class);
        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0013Response> JK0013(BaseRequest<JK0013Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0013,"JK0013",gson.toJson(request.getReqData()));
        JK0013Response response=gson.fromJson(httpResponse.getData(),JK0013Response.class);
        if (response.getCode().equals("000000")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getDescription(), response);
        }
    }



    public BaseResponse<JK0015Response> JK0015(BaseRequest<JK0015Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0015,"JK0015",gson.toJson(request.getReqData()));
        JK0015Response  response=gson.fromJson(httpResponse.getData(),JK0015Response.class);
        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }


    public BaseResponse<JK0016Response> JK0016(BaseRequest<JK0016Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0016,"JK0016",gson.toJson(request.getReqData()));
        JK0016Response  response=gson.fromJson(httpResponse.getData(),JK0016Response.class);
        if (response.getCode().equals("200")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0017Response> JK0017(BaseRequest<JK0017Request> request) {

        HttpApiRequest httpApiRequest = new HttpApiRequest();
        httpApiRequest.setUrl(jk0017);
        httpApiRequest.setApiName("JK0017");
        HttpApiResponse httpApiResponse = easyHttpClient.get(httpApiRequest);

        JK0017Response response = gson.fromJson(httpApiResponse.getData(),JK0017Response.class);
        if (response.getCode().equals("0000")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMessage(), response);
        }
    }


    public BaseResponse<JK0018Response> JK0018(BaseRequest<JK0018Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0018,"JK0018",gson.toJson(request.getReqData()));
        JK0018Response response=gson.fromJson(httpResponse.getData(),JK0018Response.class);
        if (response.getCode().equals("0000")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMessage(), response);
        }
    }


    public BaseResponse<JK0019Response> JK0019(BaseRequest<JK0019Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0019,"JK0019",gson.toJson(request.getReqData()));
        JK0019Response response=gson.fromJson(httpResponse.getData(),JK0019Response.class);
        if (response.getCode().equals("0000")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMessage(), response);
        }
    }



    public BaseResponse<JK0022Response> JK0022(BaseRequest<JK0022Request> request) {


        HttpApiRequest httpApiRequest = new HttpApiRequest();
        httpApiRequest.setUrl(jk0022 + request.getReqData().getBusinessId());
        httpApiRequest.setApiName("JK0022");
        HttpApiResponse httpApiResponse = easyHttpClient.get(httpApiRequest);

        JK0022Response response = gson.fromJson(httpApiResponse.getData(),JK0022Response.class);
        if (response.getCode().equals("0000")) {

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getMessage(), response);
        }
    }


    public BaseResponse<JK0024Response> JK0024(BaseRequest<JK0024Request> request) {

        HttpApiResponse httpResponse=getHttpApiResponse(jk0024,"JK0024",gson.toJson(request.getReqData()));
        JK0024Response response = gson.fromJson(httpResponse.getData(),JK0024Response.class);
        if (response.getResultCode().equals("1")) {

//            String jk0024SubResponseStr = new Gson().toJson(response.getResultData());
//            logger.info("jk0024SubResponseStr: " + jk0024SubResponseStr);
//
//            JK0024SubResponse jk0024SubResponse = new Gson().fromJson(jk0024SubResponseStr, JK0024SubResponse.class);
//            response.setResultData2(jk0024SubResponse);
//
//
//            logger.info("response: " + new Gson().toJson(response));

            return BaseResponse.success(request.getReqId(), response);

        }else{
            return BaseResponse.fail(request.getReqId(), response.getResultMsg(), response);
        }
    }


    public void oaTest (String businessId) {


        JK0016Request jk0016Request = new JK0016Request();
        jk0016Request.setBmbm_pk("1098BD9AB15640A2E0530A0A052640A2");
        jk0016Request.setBmtxbm_pk("8001D2CC93A290B4E0530A0A052590B4");

        BaseRequest<JK0016Request> jk0016RequestBaseRequest = new BaseRequest();
        jk0016RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0016RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0016RequestBaseRequest.setReqData(jk0016Request);

        BaseResponse<JK0016Response> jk0016ResponseBaseResponse = JK0016(jk0016RequestBaseRequest);
        JK0016SubResponse jk0016SubResponse = jk0016ResponseBaseResponse.getRespData().getData().get(0);
        logger.info(String.format("查询审批人接口：%s", new Gson().toJson(jk0016ResponseBaseResponse)));


        BaseRequest<JK0017Request> jk0017Request = new BaseRequest();
        jk0017Request.setReqId(UuidUtil.getUuid());
        jk0017Request.setReqTime(String.valueOf(new Date().getTime()));
        BaseResponse<JK0017Response> jk0017Response = JK0017(jk0017Request);
        JK0017SubResponse jk0017SubResponse = jk0017Response.getRespData().getResult();
        List<NodeList> nodeListList = jk0017SubResponse.getNodeList();
        List<WorkFlowList> workFlowListList = jk0017SubResponse.getWorkFlowList();
        logger.info(String.format("查询审批流程节点信息接口：%s", new Gson().toJson(jk0017Response)));


        JK0018Request jk0018Request = new JK0018Request();
        jk0018Request.setBusinessId(businessId);
        jk0018Request.setCreaterCode("022007");
        List<JK0018SubRequest> jk0018SubRequestList = new ArrayList();

        JK0018SubRequest jk0018SubRequest1 = new JK0018SubRequest();
        JK0018SubRequest jk0018SubRequest2 = new JK0018SubRequest();

        jk0018SubRequest1.setLineId(nodeListList.get(0).getLineId());
        jk0018SubRequest1.setNodeIndex(nodeListList.get(0).getNodeIndex());
        jk0018SubRequest1.setNodeName(nodeListList.get(0).getNodeName());
//        jk0018SubRequest1.setDefaultApproveCode(jk0016SubResponse.getSprcodeone());
//        jk0018SubRequest1.setDefaultApproveName(jk0016SubResponse.getSprnameone());

        jk0018SubRequest1.setDefaultApproveCode("022007");
        jk0018SubRequest1.setDefaultApproveName("周琦");



        jk0018SubRequest2.setLineId(nodeListList.get(1).getLineId());
        jk0018SubRequest2.setNodeIndex(nodeListList.get(1).getNodeIndex());
        jk0018SubRequest2.setNodeName(nodeListList.get(1).getNodeName());
//        jk0018SubRequest2.setDefaultApproveCode(jk0016SubResponse.getSprcodetwo());
//        jk0018SubRequest2.setDefaultApproveName(jk0016SubResponse.getSprnametwo());

        jk0018SubRequest2.setDefaultApproveCode("022007");
        jk0018SubRequest2.setDefaultApproveName("周琦");

        jk0018SubRequestList.add(jk0018SubRequest1);
        jk0018SubRequestList.add(jk0018SubRequest2);
        jk0018Request.setNodeList(jk0018SubRequestList);

        BaseRequest<JK0018Request> jk0018RequestBaseRequest = new BaseRequest();
        jk0018RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0018RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0018RequestBaseRequest.setReqData(jk0018Request);
        BaseResponse<JK0018Response> jk0018ResponseBaseResponse = JK0018(jk0018RequestBaseRequest);
        logger.info(String.format("保存申请接口返回结果：%s", new Gson().toJson(jk0018ResponseBaseResponse)));


        JK0019Request jk0019Request = new JK0019Request();
        jk0019Request.setBusinessId(businessId);
        jk0019Request.setBusinessNo(businessId);
        jk0019Request.setBusinessQueryUrl("http://10.36.8.34/api/mall/getApproveDetail?businessId=");
        jk0019Request.setBusinessUpdateUrl("http://10.36.8.34/api/mall/afterApproveCancel?businessId=");
        jk0019Request.setApprovalCategory("1");
        jk0019Request.setApprovalTitle("永钢商城测试审批流");
        jk0019Request.setCreatorCode("022007");
        jk0019Request.setWorkFlowType(workFlowListList.get(0).getWorkFlowType());


        BaseRequest<JK0019Request> jk0019RequestBaseRequest = new BaseRequest();
        jk0019RequestBaseRequest.setReqId(UuidUtil.getUuid());
        jk0019RequestBaseRequest.setReqTime(String.valueOf(new Date().getTime()));
        jk0019RequestBaseRequest.setReqData(jk0019Request);
        BaseResponse<JK0019Response> jk0019ResponseBaseResponse = JK0019(jk0019RequestBaseRequest);
        logger.info(String.format("提交流程接口：%s", new Gson().toJson(jk0019ResponseBaseResponse)));
    }



    private HttpApiResponse getHttpApiResponse(String urlSuffix, String apiName) {
        return this.getHttpApiResponse(urlSuffix, apiName, null);
    }

    private HttpApiResponse getHttpApiResponse(String urlSuffix, String apiName, String content) {

        try {

            HttpApiRequest httpApiRequest = new HttpApiRequest();
            httpApiRequest.setUrl(urlSuffix);
            httpApiRequest.setApiName(apiName);
            httpApiRequest.setContent(content);
            HttpApiResponse httpApiResponse = easyHttpClient.post(httpApiRequest);

            logger.info(String.format("url: %s, param: %s, data: %s", urlSuffix, content, httpApiResponse.getData()));
            return httpApiResponse;

        }catch (Exception e) {

            throw new NetPlusException(e.getMessage());
        }
    }

    public BaseResponse<JK0027Response> JK0027(BaseRequest<JK0027Request> request) {
        String url=jk0027+"?code="+request.getReqData().getCode();
        HttpApiResponse httpResponse=getHttpApiResponse(url,"JK0027");
        JK0027Response response = gson.fromJson(httpResponse.getData(),JK0027Response.class);
        if(response.getCode().equals("0000")){
            return BaseResponse.success(request.getReqId(), response);
        }else{
            return BaseResponse.fail(request.getReqId(), response.getMessage(), response);
        }
    }

    public BaseResponse<JK0028Response> JK0028(BaseRequest<JK0028Request> request) {
        String url=jk0028+"?token="+request.getReqData().getToken();
        HttpApiResponse httpResponse=getHttpApiResponse(url,"JK0028");
        JK0028Response response = gson.fromJson(httpResponse.getData(),JK0028Response.class);
        if(response.getCode().equals("0000")){
            return BaseResponse.success(request.getReqId(), response);
        }else{
            return BaseResponse.fail(request.getReqId(), response.getMessage(), response);
        }
    }

    public BaseResponse<JK0030Response> JK0030(BaseRequest<JK0030Request> request) {

        try {


            KeyValue keyValue = new KeyValue();
            keyValue.setKey("Authorization");
            keyValue.setValue("Bearer " + request.getReqData().getTicket());

            HttpApiRequest httpApiRequest = new HttpApiRequest();
            httpApiRequest.setUrl(jk0030);
            httpApiRequest.setApiName("JK0030");
            httpApiRequest.setHeaders(Arrays.asList(keyValue));
            HttpApiResponse httpApiResponse = easyHttpClient.get(httpApiRequest);

            logger.info(String.format("url: %s, param: %s, data: %s", jk0030, new Gson().toJson(request.getReqData()), httpApiResponse.getData()));

            JK0030Response response = gson.fromJson(httpApiResponse.getData(),JK0030Response.class);
            if(!StringUtils.isEmpty(response.getUsername())){
                return BaseResponse.success(request.getReqId(), response);
            }else{
                return BaseResponse.fail(
                        request.getReqId(),
                        StringUtils.isEmpty(response.getError()) ? response.getMessage(): response.getError(),
                        response
                );
            }

        }catch (Exception e) {
            throw new NetPlusException(e.getMessage());
        }

    }

    public BaseResponse<JK0031Response> JK0031(BaseRequest<JK0031Request> request) {
        HttpApiResponse httpResponse=getHttpApiResponse(jk0031, "JK0031", gson.toJson(request.getReqData()));
        JK0031Response response =gson.fromJson(httpResponse.getData(),JK0031Response.class);
        if (response.getCode().equals("200") && response.getData().size() > 0) {
            if (response.getData().size() > 0) {
                return BaseResponse.success(request.getReqId(), response);
            }else{
                return BaseResponse.fail(request.getReqId(),"返回数据为空", response);
            }
        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0032Response> JK0032(BaseRequest<JK0032Request> request) {
        HttpApiResponse httpResponse=getHttpApiResponse(jk0032, "JK0032", gson.toJson(request.getReqData()));
        JK0032Response response =gson.fromJson(httpResponse.getData(),JK0032Response.class);
        if(response.getCode().equals("200")){
            return BaseResponse.success(request.getReqId(), response);
        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }


    public BaseResponse<JK0033Response> JK0033(BaseRequest<JK0033Request> request) {
        HttpApiResponse httpResponse=getHttpApiResponse(jk0033, "JK0033", gson.toJson(request.getReqData()));
        JK0033Response response =gson.fromJson(httpResponse.getData(),JK0033Response.class);
        if(response.getCode().equals("200")){
            return BaseResponse.success(request.getReqId(), response);
        }else{
            return BaseResponse.fail(request.getReqId(), response.getMsg(), response);
        }
    }

    public BaseResponse<JK0034Response> JK0034(BaseRequest<JK0034Request> request) {
        HttpApiResponse httpResponse=getHttpApiResponse(jk0034, "JK0034", gson.toJson(request.getReqData()));
        JK0034Response response =gson.fromJson(httpResponse.getData(),JK0034Response.class);
        if(response.isSuccess()){
            return BaseResponse.success(request.getReqId(), response);
        }else{
            return BaseResponse.fail(request.getReqId(), response.getError(), response);
        }
    }

    public BaseResponse<JK0035Response> JK0035(BaseRequest<JK0035Request> request) {

        String url= jk0035 + String.format("?accessToken=%s", request.getReqData().getAccessToken());
        HttpApiResponse httpResponse=getHttpApiResponse(url, "JK0035", gson.toJson(request.getReqData()));
        JK0035Response response =gson.fromJson(httpResponse.getData(),JK0035Response.class);
        if(response.isSuccess()){
            return BaseResponse.success(request.getReqId(), response);
        }else{
            return BaseResponse.fail(request.getReqId(), response.getError(), response);
        }
    }
}