package com.tyz.rabbitmq.common;

import com.lmax.disruptor.dsl.Disruptor;
import com.tyz.rabbitmq.vo.LogVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;


/**
 * @program: cloud-demo
 * @description: LogProducer
 * @author: tyz
 * @create: 2025-05-01
 */
@Component
public class LogProducer {
    @Autowired
    @Qualifier("logDisruptor")
    //@Resource(name = "logDisruptor")
    private Disruptor<LogVO> disruptor;

    public void publishLog(String content) {
        disruptor.publishEvent((event, sequence) -> event.setLogContent(content));
    }

   /* public void publishLogs(List<String> contents) {
        // 批量发布 10 个事件
        disruptor.publishEvents(
                (event, sequence, batchSize) -> {
                    for (int i = 0; i < batchSize; i++) {
                        event.setLogContent(contents.get(i));
                    }
                },
                contents.stream().toArray());
    }*/


}

