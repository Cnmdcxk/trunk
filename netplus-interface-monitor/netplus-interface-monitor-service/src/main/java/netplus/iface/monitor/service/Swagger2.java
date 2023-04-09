package netplus.iface.monitor.service;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
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
    public Docket createRestApi() {

        ApiInfo apiInfo = new ApiInfoBuilder()
                .title("接口文档")
                .version("1.0")
                .build();
        List<Parameter> params = new ArrayList<>();
        ParameterBuilder header1 = new ParameterBuilder();
        ParameterBuilder header2 = new ParameterBuilder();
        header1.name("AccessToken").modelRef(new ModelRef("string")).parameterType("header").required(true);
        header2.name("").modelRef(new ModelRef("string")).parameterType("header").required(false);
        params.add(header1.build());
        params.add(header2.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo)
                .select()
                .apis(RequestHandlerSelectors.basePackage("netplus.iface.monitor.service.controller"))
                .paths(PathSelectors.any())
                .build()
                .globalOperationParameters(params)
                .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("YG_FINANCE_API")  //粗标题
                .version("1.0.0")   //api version
                .termsOfServiceUrl("http://localhost:20029/doc.html")
                .license("LICENSE")   //链接名称
                .licenseUrl("http://localhost:20029/doc.html")   //链接地址
                .build();
    }
}
