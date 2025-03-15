package com.tyz.order.feign.decoder;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Optional;

/**
 * 通过实现RequestInterceptor接口实现拦截方式，仅能获取接口参数，
 * 无法获取接口返回值，
 * 因此采用实现Decoder接口方式对接口的入参及返回值进行记录。
 *
 * @program: cloud-demo
 * @description: FeignLogDecoder 来记类参数和返回
 * @author: tyz
 * @create: 2025-03-15
 */
public class FeignLogDecoder implements Decoder {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignLogDecoder.class);
    final Decoder delegate;

    public FeignLogDecoder(Decoder delegate) {
        Objects.requireNonNull(delegate, "Decoder must not be null. ");
        this.delegate = delegate;
    }

    /**
     * @param response 返回
     * @param type 类型
     * @return obj
     * @throws IOException 异常
     * @throws DecodeException 解码异常
     * @throws FeignException feign 异常
     */
    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        String resultStr = IOUtils.toString(response.body().asInputStream(), StandardCharsets.UTF_8);
        byte[] reqBody = Optional.ofNullable(response.request().body()).orElse(new byte[0]);
        String url = response.request().url();
        LOGGER.info("feign {}：参数-->{}", url, new String(reqBody, StandardCharsets.UTF_8));
        LOGGER.info("feign {}：返回-->{}", url, resultStr);
        // 回写body,因为response的流数据只能读一次，这里回写后重新生成response
        return delegate.decode(response.toBuilder().body(resultStr, StandardCharsets.UTF_8).build(), type);
    }
}
