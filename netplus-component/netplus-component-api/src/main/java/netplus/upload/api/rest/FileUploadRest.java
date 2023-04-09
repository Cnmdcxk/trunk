package netplus.upload.api.rest;


import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import netplus.component.feignclient.FeignErrorDecoder;
import netplus.component.feignclient.FeignInterceptor;
import netplus.upload.api.vo.UploadResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@FeignClient(
        value = "netplus-component-service",
        url = "${service.netplus-component-service}",
        contextId = "FileUpload",
        configuration = {FeignInterceptor.class, FeignErrorDecoder.class, FileUploadRest.MultipartSupportConfig.class}
)
public interface FileUploadRest {

    @PostMapping(
            value = Urls.doUpload,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    UploadResult doUpload(MultipartFile file) throws IOException;



    @PostMapping(
            value = Urls.doUpload4Download,
            produces = {MediaType.APPLICATION_JSON_UTF8_VALUE},
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    UploadResult doUpload4Download(MultipartFile file) throws IOException;


    class MultipartSupportConfig {

        @Bean
        public Encoder feignFormEncoder () {
            return new SpringFormEncoder();
        }

    }
}
