package com.hscs.client.fallback;

import com.hscs.commonutils.ordervo.CourseWebVoOrder;
import com.hscs.commonutils.R;
import com.hscs.client.EduClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EduClientFallBack implements EduClient {
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String id) {
        log.error("熔断器被执行");
        return null;
    }

    @Override
    public R updateBuyCountById(String id) {
        log.error("熔断器被执行");
        return R.error();
    }
}
