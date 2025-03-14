package com.tyz.order.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @program: cloud-demo
 * @description: nacos 配置中心的数据集dataId
 * @author: tyz
 * @create: 2025-03-14
 */
@Component
// 配置绑定nacos可以无需@RefreshScope就可以自动刷新 也推荐使用这种方式
@ConfigurationProperties(prefix = "order")
public class OrderProperties {
    private String timeout;

    private String autoConfirm;

    public String getTimeout() {
        return timeout;
    }

    public void setTimeout(String timeout) {
        this.timeout = timeout;
    }

    public String getAutoConfirm() {
        return autoConfirm;
    }

    public void setAutoConfirm(String autoConfirm) {
        this.autoConfirm = autoConfirm;
    }

    @Override
    public String toString() {
        return "OrderProperties{" +
                "timeout='" + timeout + '\'' +
                ", autoConfirm='" + autoConfirm + '\'' +
                '}';
    }
}
