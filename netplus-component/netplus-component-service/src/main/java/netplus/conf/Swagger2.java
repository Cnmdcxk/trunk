package netplus.conf;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class Swagger2 {
    @Bean
    public Docket createOldserviceApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Access接口文档")
                .select()
                .apis(RequestHandlerSelectors.basePackage("netplus.access.oldservice.controller"))
//                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket createUploadApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Upload接口文档")
                .select()
                .apis(RequestHandlerSelectors.basePackage("netplus.upload.service.controller"))
//                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket createSerialApi() {

        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Serial接口文档")
                .select()
                .apis(RequestHandlerSelectors.basePackage("netplus.serial.service.controller"))
//                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo());
    }

//    @Bean
//    public Docket createProviderApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("Provider接口文档")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("netplus.provider.service.controller"))
////                .paths(PathSelectors.any())
//                .build()
//                .globalOperationParameters(setHeaderToken())
//                .apiInfo(apiInfo());
//    }

//    @Bean
//    public Docket createMessagingApi() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("Messaging接口文档")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("netplus.messaging.service.controller"))
////                .paths(PathSelectors.any())
//                .build()
//                .globalOperationParameters(setHeaderToken())
//                .apiInfo(apiInfo());
//    }

    @Bean
    public Docket createCaptchaApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Captcha接口文档")
                .select()
                .apis(RequestHandlerSelectors.basePackage("netplus.captcha.service.controller"))
//                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo());
    }

    @Bean
    public Docket createCacheApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Cache接口文档")
                .select()
                .apis(RequestHandlerSelectors.basePackage("netplus.cache.service.controller"))
//                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(setHeaderToken())
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("YG_COMPONENT_API")  //粗标题
                .version("1.0.0")   //api version
                .termsOfServiceUrl("http://localhost:20028/doc.html")
                .license("LICENSE")   //链接名称
                .licenseUrl("http://localhost:20028/doc.html")   //链接地址
                .build();
    }

    private List<Parameter> setHeaderToken() {
        List<Parameter> params = new ArrayList<>();
        ParameterBuilder header1 = new ParameterBuilder();
        ParameterBuilder header2 = new ParameterBuilder();
        header1.name("AccessToken").modelRef(new ModelRef("string")).parameterType("header").required(true);
        header2.name("").modelRef(new ModelRef("string")).parameterType("header").required(false);
        params.add(header1.build());
        params.add(header2.build());

        return params;
    }
}
