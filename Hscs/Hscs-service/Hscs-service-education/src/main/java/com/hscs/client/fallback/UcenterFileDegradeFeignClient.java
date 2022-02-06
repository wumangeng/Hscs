package com.hscs.client.fallback;

import com.hscs.client.UcenterClient;
import com.hscs.commonutils.ordervo.UserCenterOrder;
import org.springframework.stereotype.Component;

@Component
public class UcenterFileDegradeFeignClient implements UcenterClient {
    @Override
    public UserCenterOrder getUserInfoOrder(String id) {
        return null;
    }
}
