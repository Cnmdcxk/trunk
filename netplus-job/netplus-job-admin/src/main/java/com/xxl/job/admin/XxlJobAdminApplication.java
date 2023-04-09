package com.xxl.job.admin;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author xuxueli 2018-10-28 00:38:13
 */
@SpringBootApplication                              //直接启动
@EnableEurekaClient                                 //允许注册到Eureka
@ComponentScan(basePackages = "com.xxl.*")          //扫描指定的pack的对象
@Slf4j
public class XxlJobAdminApplication {
	private static final Logger log = LoggerFactory.getLogger(XxlJobAdminApplication.class);

	public static void main(String[] args) throws UnknownHostException {
		ConfigurableApplicationContext application = SpringApplication.run(XxlJobAdminApplication.class, args);
		Environment env = application.getEnvironment();
		String ip = InetAddress.getLocalHost().getHostAddress();
		String port = env.getProperty("server.port");
		String path = env.getProperty("server.servlet.context-path");
		if (StringUtils.isEmpty(path)) {
			path = "";
		}

		log.info("\n----------------------------------------------------------\n\t" +
				"Application  is running! Access URLs:\n\t" +
				"Local访问网址: \thttp://localhost:" + port + path + "/\n\t" +
				"Swagger访问网址: http://" + ip + ":" + port + path + "/\n" +
				"----------------------------------------------------------");
	}

}