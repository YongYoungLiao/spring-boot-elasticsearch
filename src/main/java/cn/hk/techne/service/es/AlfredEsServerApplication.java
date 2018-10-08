package cn.hk.techne.service.es;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

import java.util.concurrent.ExecutionException;

@SpringBootApplication
@EnableEurekaClient
public class AlfredEsServerApplication {

	public static void main(String[] args) throws ExecutionException, InterruptedException {

		System.setProperty("es.set.netty.runtime.available.processors", "false");

		SpringApplication.run(AlfredEsServerApplication.class, args);

		Logger logger = LoggerFactory.getLogger(AlfredEsServerApplication.class);
		logger.info("===>>>>>>>>>>>>>>>>>>>>>>>");
		logger.info("                 now  es run");
		logger.info("===>>>>>>>>>>>>>>>>>>>>>>>");

		logger.info("===>>>>>>>>>>>>>>>>>>>>>>>");
		logger.info("===>>>>>>>>>>>>>>>>>>>>>>>");



	}
}