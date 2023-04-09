package netplus.component.feignclient;

import com.google.gson.Gson;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Reader;

@Component
public class FeignErrorDecoder implements ErrorDecoder {

    private static Log logger = LogFactory.getLog(FeignErrorDecoder.class);

    private final ErrorDecoder errorDecoder = new Default();

    private final Gson gson = new Gson();

    @Override
    public Exception decode(String methodKey, Response response) {

        logger.error(String.format("FeignError: %s - %s", response.request().url(), response.status()));

        try {

            // 处理401, 403
            if (response.status() == HttpStatus.UNAUTHORIZED.value() ||
                    response.status() == HttpStatus.FORBIDDEN.value()) {

                throw new RuntimeException(String.format("服务调用返回%s: %s",
                        response.status(), StringUtils.isEmpty(response.reason()) ? "" : response.reason()));

            } else {

                Reader reader = response.body().asReader();
                String respText = Util.toString(reader);

                FeignError feignError = null;
                try {
                    feignError = gson.fromJson(respText, FeignError.class);
                }
                catch (Exception ex){
                    throw new RuntimeException(respText);
                }

                String error = feignError.getMessage().equalsIgnoreCase("no message available") ? "服务调用异常" : feignError.getMessage();
                throw new RuntimeException(error);
            }
        }
        catch (IOException e) { // nothing
        }

        return errorDecoder.decode(methodKey, response);
    }


    @Getter
    @Setter
    private class FeignError{
        private String timestamp;
        private int status;
        private String error;
        private String message;
        private String path;
    }
}
