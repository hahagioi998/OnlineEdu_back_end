package com.saodiseng.eduservice.client;

import com.saodiseng.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Component  //spring管理
@FeignClient("service-vod") //调用哪个服务
public interface VodClient {

    //定义调用方法的路径
    //根据视频id删除阿里云视频
    @DeleteMapping("/eduvod/video/removeAlyVideo/{id}")  //完全路径
    public R removeAlyVideo(@PathVariable("id") String id);

    //删除多个阿里云视频
    //参数是多个视频id
    @DeleteMapping("/eduvod/video/delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList);

}
