package com.hscs.client;

import com.hscs.commonutils.ordervo.UserCenterOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-usercenter")
public interface UcenterClient {

    /**根据用户id获取用户信息*/
    @PostMapping("/usercenter/user/getUserInfoOrder/{id}")
    UserCenterOrder getUserInfoOrder(@PathVariable("id") String id);
}
