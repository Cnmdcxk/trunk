package netplus.joint.erp.out.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication                              //直接启动
@EnableEurekaClient                                 //允许注册到Eureka
@EnableFeignClients(basePackages = "netplus.*")     //像调用本地方法一样调用REST接口
@MapperScan({"netplus.joint.erp.in.service.dao", "netplus.component.entity.aspect"})          	//生成mapper的代理类
@ComponentScan(basePackages = "netplus.*")          //扫描指定的pack的对象
@Slf4j
public class Application {

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(Application.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		if (StringUtils.isEmpty(path)) {
			path = "";
		}
		log.info("\n----------------------------------------------------------\n\t" +
				"Application  is running! Access URLs:\n\t" +
				"Local访问网址: \thttp://localhost:" + port + path + "\n\t" +
				"Swagger访问网址: http://" + ip + ":" + port + path + "/doc.html\n" +
				"----------------------------------------------------------");
	}

}
