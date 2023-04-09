package netplus.component.http;

import okhttp3.*;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class EasyHttpClient {

    private static Log logger = LogFactory.getLog(EasyHttpClient.class);



    public static final MediaType JSON = MediaType.parse("application/json;charset=utf-8");

    public HttpApiResponse post (HttpApiRequest callApiRequest) {


        if (StringUtils.isEmpty(callApiRequest.getUrl()))
            throw new RuntimeException("url不能为空");

        if (StringUtils.isEmpty(callApiRequest.getApiName()))
            throw new RuntimeException("apiName不能为空");

        String content = StringUtils.isEmpty(callApiRequest.getContent()) ? "" : callApiRequest.getContent();

        OkHttpClient httpClient = new OkHttpClient();
        RequestBody requestBody = RequestBody.create(JSON, content);

        Request.Builder requestBuilder = new Request.Builder()
                .url(callApiRequest.getUrl())
                .post(requestBody);

        callApiRequest.getHeaders().forEach(p -> {
            requestBuilder.addHeader(p.getKey(), p.getValue());
        });

        Request request = requestBuilder.build();
        HttpApiResponse callApiResponse = new HttpApiResponse();

        try {
            Response response = httpClient
                    .newBuilder()
                    .connectTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .build()
                    .newCall(request)
                    .execute();

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
                callApiResponse.setMessage(ExceptionUtils.getFullStackTrace(ex));
            }
        }

        return callApiResponse;
    }

    public HttpApiResponse get (HttpApiRequest callApiRequest) {

        if (StringUtils.isEmpty(callApiRequest.getUrl()))
            throw new RuntimeException("url不能为空");

        if (StringUtils.isEmpty(callApiRequest.getApiName()))
            throw new RuntimeException("apiName不能为空");

        OkHttpClient httpClient = new OkHttpClient();

        Request.Builder requestBuilder = new Request.Builder()
                .url(callApiRequest.getUrl());

        callApiRequest.getHeaders().forEach(p -> {
            requestBuilder.addHeader(p.getKey(), p.getValue());
        });


        Request request = requestBuilder.build();
        HttpApiResponse callApiResponse = new HttpApiResponse();

        try {

            Response response = httpClient
                    .newBuilder()
                    .connectTimeout(600, TimeUnit.SECONDS)
                    .readTimeout(600, TimeUnit.SECONDS)
                    .writeTimeout(600, TimeUnit.SECONDS)
                    .build()
                    .newCall(request)
                    .execute();

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
                callApiResponse.setMessage(ExceptionUtils.getFullStackTrace(ex));
            }
        }

        return callApiResponse;
    }
}
