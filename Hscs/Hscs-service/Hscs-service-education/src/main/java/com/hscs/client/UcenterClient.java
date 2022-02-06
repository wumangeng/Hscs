package com.hscs.client;

import com.hscs.commonutils.ordervo.UserCenterOrder;
import com.hscs.client.fallback.UcenterFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "service-usercenter",fallback = UcenterFileDegradeFeignClient.class) //调用的服务名称
@Component
public interface UcenterClient {

    /**根据用户id获取用户信息*/
    @PostMapping("/usercenter/user/getUserInfoOrder/{id}")
    UserCenterOrder getUserInfoOrder(@PathVariable("id") String id) ;
}
