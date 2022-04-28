package com.saodiseng.eduservice.controller;


import com.saodiseng.commonutils.R;
import com.saodiseng.eduservice.client.VodClient;
import com.saodiseng.eduservice.entity.EduChapter;
import com.saodiseng.eduservice.entity.EduVideo;
import com.saodiseng.eduservice.service.EduVideoService;
import com.saodiseng.servicebase.exceptionhandler.GuliException;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/eduservice/video")
@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;
    
    //注入vodClient
    @Autowired
    private VodClient vodClient;
    //添加小节'
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo){
        videoService.save(eduVideo);
        return R.ok();
    }

    //删除小节
    //删除小节时，阿里云视频也需要删除（用edu模块调用vod中的方法，微服务）
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id){
        //根据小节id获取视频id，然后删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        if(!StringUtils.isEmpty(videoSourceId)){
            //根据视频id，远程调用实现视频删除
            R result = vodClient.removeAlyVideo(videoSourceId);   //返回R对象
            if (result.getCode() == 20001) {
                throw new GuliException(20001,"删除视频失败，熔断器。。。");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }

    //修改小节

    //先查再改
    @GetMapping("getVideoInfo/{videoId}")
    public R getVideoInfo(@PathVariable String videoId){
        EduVideo eduVideo = videoService.getById(videoId);
        return R.ok().data("chapter",eduVideo);
    }
    //修改
    @PostMapping("updateVideo")
    public R updateVideo(@RequestBody EduVideo eduVideo){
        videoService.updateById(eduVideo);
        return R.ok();
    }


}

