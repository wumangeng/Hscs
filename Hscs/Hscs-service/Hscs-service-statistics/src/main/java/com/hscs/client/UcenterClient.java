package com.hscs.client;

import com.hscs.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Component
@FeignClient("service-usercenter")
public interface UcenterClient {

    /**查询某一天注册人数*/
    @GetMapping("/usercenter/user/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
