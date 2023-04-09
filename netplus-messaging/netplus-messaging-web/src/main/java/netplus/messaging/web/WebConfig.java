package netplus.messaging.web;

import netplus.component.authbase.interceptor.AuthInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class WebConfig extends WebMvcConfigurationSupport {

    @Override
    protected void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getAuthInterceptor());
        super.addInterceptors(registry);
    }


    @Bean
    public AuthInterceptor getAuthInterceptor(){
        return new AuthInterceptor();
    }

}
