package com.tyz.rabbitmq.vo;

/**
 * @program: cloud-demo
 * @description: Log vo 日志事件
 * @author: tyz
 * @create: 2025-05-01
 */
public class LogVO {
    private String logContent;
    public String getLogContent() {
        return logContent;
    }

    public void setLogContent(String logContent) {
        this.logContent = logContent;
    }

    @Override
    public String toString() {
        return "LogVO{" +
                "logContent='" + logContent + '\'' +
                '}';
    }
}
