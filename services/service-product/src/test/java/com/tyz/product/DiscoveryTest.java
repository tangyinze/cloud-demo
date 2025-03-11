package com.tyz.product;

import com.alibaba.cloud.nacos.discovery.NacosServiceDiscovery;
import com.alibaba.nacos.api.exception.NacosException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;

import java.util.List;

/**
 * @program: cloud-demo
 * @description: test
 * @author: tyz
 * @create: 2025-03-12
 */
@SpringBootTest
public class DiscoveryTest {
    // spring 的标准服务发现的接口
    @Autowired
    DiscoveryClient discoveryClient;
    // nacos 的服务发现的服务类
    @Autowired
    NacosServiceDiscovery nacosServiceDiscovery;

    @Test
    void testDiscoveryClient() {
        List<String> services = discoveryClient.getServices();
        services.stream().forEach(name -> {
            List<ServiceInstance> instances = discoveryClient.getInstances(name);
            instances.stream().forEach(instance -> {
                System.out.println("ip: " + instance.getHost() + " port: " + instance.getPort());
            });
            instances.clear();
        });
        services.clear();
    }

    @Test
    void testNacosServiceDiscovery() throws NacosException {
        List<String> services = nacosServiceDiscovery.getServices();
        services.stream().forEach(name -> {
            List<ServiceInstance> instances = null;
            try {
                instances = nacosServiceDiscovery.getInstances(name);
                instances.stream().forEach(instance -> {
                    System.out.println("ip: " + instance.getHost() + " port: " + instance.getPort());
                });
                instances.clear();
            } catch (NacosException e) {
                throw new RuntimeException(e);
            }
        });
        services.clear();
    }
}
