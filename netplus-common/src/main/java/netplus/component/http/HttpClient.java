package netplus.component.http;


import com.google.gson.Gson;
import netplus.component.entity.api.ApiLog;
import netplus.component.entity.api.IHttpLog;
import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.net.ssl.*;
import java.security.cert.CertificateException;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Service
public class HttpClient {

    private static Log logger = LogFactory.getLog(HttpClient.class);

    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");
    public static final MediaType FORM = MediaType.parse("application/x-www-form-urlencoded;charset=utf-8");

    @Autowired(required = false)
    private IHttpLog iHttpLog;

    public HttpApiResponse callApi(HttpApiRequest callApiRequest) {
        return callApi(callApiRequest, JSON);
    }

    public HttpApiResponse callApi(HttpApiRequest callApiRequest, MediaType mediaType) {

        if (StringUtils.isEmpty(callApiRequest.getUrl()))
            throw new RuntimeException("url不能为空");

        if (StringUtils.isEmpty(callApiRequest.getApiName()))
            throw new RuntimeException("apiName不能为空");

        String content = StringUtils.isEmpty(callApiRequest.getContent()) ? "" : callApiRequest.getContent();

        // add log
        Integer logId = null;
        if (iHttpLog != null) {
            ApiLog apiLog = new ApiLog();
            apiLog.setApiName(callApiRequest.getApiName());
            apiLog.setContent(content.length() > 1000 ? content.substring(0, 1000) : content);
            apiLog.setCreateTime(new Date());
            apiLog.setDirection(0);
            apiLog.setResponse(null);
            apiLog.setUpdateTime(null);
            iHttpLog.insert(apiLog);
            logId = apiLog.getId();
        }

        OkHttpClient httpClient = new OkHttpClient();

        RequestBody requestBody = RequestBody.create(mediaType == null ? JSON : mediaType, content);

        Request.Builder requestBuilder = new Request.Builder()
                .url(callApiRequest.getUrl())
                .addHeader("serviceId", callApiRequest.getApiName())
                .addHeader("msgSendTime", DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"))
                .post(requestBody);
        callApiRequest.getHeaders().forEach(p -> {
            requestBuilder.addHeader(p.getKey(), p.getValue());
        });
        Request request = requestBuilder.build();

        //
        HttpApiResponse callApiResponse = new HttpApiResponse();
        try {
            Response response = httpClient.newBuilder()
                    .connectTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .build().newCall(request).execute();
            int code = response.code();

            callApiResponse.setStatus(code);
            if (code == HttpStatus.OK.value()) {
                callApiResponse.setData(response.body().string());
            } else {
                callApiResponse.setMessage(response.body().string());
            }
        } catch (Exception ex) {

            callApiResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            if (ex instanceof NullPointerException) {
                callApiResponse.setMessage("空指针异常");
            } else {
                callApiResponse.setMessage(ex.getMessage());
            }
        }

        if (iHttpLog != null) {
            String responseText = new Gson().toJson(callApiResponse);
            ApiLog apiLog = new ApiLog();
            apiLog.setId(logId);
            apiLog.setUpdateTime(new Date());
            apiLog.setResponse(responseText.length() > 1000 ? responseText.substring(0, 1000) : responseText);
            iHttpLog.updateByPrimaryKeySelective(apiLog);
        }

        if (callApiResponse.getStatus() != HttpStatus.OK.value() && callApiRequest.getThrowIfError())
            throw new RuntimeException(callApiResponse.getMessage());

        return callApiResponse;
    }

    //绕过ssl验证
    private static OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Create a trust manager that does not validate certificate chains
            final TrustManager[] trustAllCerts = new TrustManager[]{
                    new X509TrustManager() {
                        @Override
                        public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws CertificateException {
                        }

                        @Override
                        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                            return new java.security.cert.X509Certificate[]{};
                        }
                    }
            };

            // Install the all-trusting trust manager
            final SSLContext sslContext = SSLContext.getInstance("SSL");
            sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
            // Create an ssl socket factory with our all-trusting manager
            final SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.sslSocketFactory(sslSocketFactory);
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            OkHttpClient okHttpClient = builder.build();
            return okHttpClient;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws Exception {
//
//        OkHttpClient httpClient = new OkHttpClient();
//        String msgBody = "{\"COMPANY_CODE\": \"aa\", \"USER_NUM\": \"11\", \"USER_NAME\": \"test\"}";
//        RequestBody requestBody = RequestBody.create(HttpClient.JSON, msgBody);
//        Request request = new Request.Builder()
//                .url("http://10.81.11.99:4934/services/restMessage")
//                .addHeader("sourceAppCode", "DA")
//                .addHeader("serviceId", "DAOM26")
//                .addHeader("msgSendTime",  DateFormatUtils.format(new Date(), "yyyyMMddHHmmssSSS"))
//                .post(requestBody)
//                .build();
//
//        Response response = httpClient.newBuilder()
//                .connectTimeout(60, TimeUnit.SECONDS)
//                .readTimeout(60, TimeUnit.SECONDS)
//                .writeTimeout(60, TimeUnit.SECONDS)
//                .build().newCall(request).execute();
//
//        int code = response.code();


    }

}
