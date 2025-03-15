package com.tyz.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * feignClient 调用第三方接口 不通过配置中心 url 配置地址
 *
 * @author tyz
 */
@FeignClient(value = "weather-client", url = "http://aliv18.data.moji.com")
public interface WeathThirdFeignClient {
    @PostMapping("/whapi/json/alicityweather/condition")
    String loadWeather(@RequestHeader("Authorization") String auth,
                       @RequestParam("token") String token,
                       @RequestParam("cityId") String cityId);
}
