package com.tyz.rabbitmq.cache;

import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;

import java.io.ByteArrayInputStream;

/**
 * @program: cloud-demo
 * @description: 自定义 ServletInputStream 实现
 * @author: tyz
 * @create: 2025-03-25
 */
public class CachedServletInputStream extends ServletInputStream {
    private final ByteArrayInputStream buffer;

    public CachedServletInputStream(byte[] content) {
        this.buffer = new ByteArrayInputStream(content);
    }

    @Override
    public int read() {
        return buffer.read();
    }

    @Override
    public boolean isFinished() {
        return buffer.available() == 0;
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(ReadListener listener) {
        throw new UnsupportedOperationException();
    }
}
