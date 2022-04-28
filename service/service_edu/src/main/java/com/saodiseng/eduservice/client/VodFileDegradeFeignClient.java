package com.saodiseng.eduservice.client;

import com.saodiseng.commonutils.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 若VodClient执行发生错误，则执行实现类中对应的方法（服务降级），兜底方法
 */
@Component
public class VodFileDegradeFeignClient implements VodClient{

    @Override
    public R removeAlyVideo(String id) {
        return R.error().message("删除视频出错");
    }

    @Override
    public R deleteBatch(List<String> videoIdList) {
        return R.error().message("删除多个视频出错");
    }
}
