package net.sppan.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

@SpringBootApplication
public class Application extends SpringBootServletInitializer {

    private static Logger logger = LoggerFactory.getLogger(Application.class);

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }

    /**
     */
    public static void main(String[] args) {
        //System.setProperty("spring.devtools.restart.enabled","false");//关闭热部署，解决quartz的queryExecutingJobList()中类名相同但加载器不同导致无法转换的问题
        //已在properties配置文件中关闭
        SpringApplication.run(Application.class, args);
        logger.debug("启动成功");
    }

}
