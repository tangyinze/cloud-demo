package com.tyz.rabbitmq.cache;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @program: cloud-demo
 * @description: 缓存请求类
 * @author: tyz
 * @create: 2025-03-25
 */
public class RepeatedlyReadRequestWrapper extends HttpServletRequestWrapper {
    // 缓存请求体信息
    private final byte[] cachedBody;

    public RepeatedlyReadRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        ServletInputStream inputStream = request.getInputStream();
        this.cachedBody = StreamUtils.copyToByteArray(inputStream);
    }

    /**
     * @return is
     * @throws IOException 异常
     */
    @Override
    public ServletInputStream getInputStream() throws IOException {
        // 返回基于缓存的流
        return new CachedServletInputStream(cachedBody);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        // 支持通过 Reader 读取
        return new BufferedReader(new InputStreamReader(this.getInputStream()));
    }
}
