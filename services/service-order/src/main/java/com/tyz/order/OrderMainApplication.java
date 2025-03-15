package com.tyz.order;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * @program: cloud-demo
 * @description: spring cloud order test
 * @author: tyz
 * @create: 2025-03-07
 */
@EnableDiscoveryClient
// 开启feign注解功能
@EnableFeignClients(basePackages = "com.tyz.order.feign")
@SpringBootApplication
public class OrderMainApplication {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderMainApplication.class);

    public static void main(String[] args) {
        /**
         * 在项目的启动过程中, nacos-discovery 会尝试去读系统内的日志配置文件, 然后刷新配置, 但是如果找不到的话, 会使用自身内部的默认配置, 但是默认的日志配置没有设置日志的 Root 的信息。
         * WARN No Root logger was configured, creating default ERROR-level Root logger with Console appender
         * 后日志都不打印了
         *  com.alibaba.nacos.client.utils.LogUtils
         *  Log4J2NacosLoggingAdapter 会加载默认的配置
         *  nacos 会重新加载日志的配置, 但是需要的配置文件都没有, 后续的默认的配置文件 classpath:nacos-log4j2.xml 也没有设置日志的 Root, 所以报错了。
         */
        System.setProperty("nacos.logging.default.config.enabled", "false");
        SpringApplication.run(OrderMainApplication.class, args);
    }

    /**
     * 启动后执行的方法
     *
     * @return applicationRunner
     */
    @Bean
    ApplicationRunner applicationRunner(NacosConfigManager nacosConfigManager) {
        return (args) -> {
            ConfigService configService = nacosConfigManager.getConfigService();
            configService.addListener("service-order.properties",
                    "DEFAULT_GROUP",
                    new Listener() {
                        /**
                         *  监听是在线程池中来触发的
                         * @return 线程池中
                         */
                        @Override
                        public Executor getExecutor() {
                            return Executors.newFixedThreadPool(1);
                        }

                        @Override
                        public void receiveConfigInfo(String configChg) {
                            LOGGER.info("chg config====>:{}", configChg);
                        }
                    }
            );


        };
    }
}
