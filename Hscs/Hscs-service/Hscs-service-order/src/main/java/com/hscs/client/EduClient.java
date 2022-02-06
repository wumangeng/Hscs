package com.hscs.client;

import com.hscs.commonutils.ordervo.CourseWebVoOrder;
import com.hscs.commonutils.R;
import com.hscs.client.fallback.EduClientFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component

@FeignClient(value = "service-education",fallback = EduClientFallBack.class)//调用的服务名称
public interface EduClient {

    /**根据课程id查询课程信息*/
    @PostMapping("/edu/coursefront/getCourseInfoOrder/{id}")
    CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);


    /**根据课程id更改销售量*/
    @GetMapping("/edu/edu-course/updateBuyCount/{id}")
    R updateBuyCountById(@PathVariable("id") String id);
}
