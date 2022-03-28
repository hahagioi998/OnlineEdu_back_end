package com.saodiseng.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodiseng.eduservice.entity.EduChapter;
import com.saodiseng.eduservice.entity.EduVideo;
import com.saodiseng.eduservice.entity.chapter.ChapterVo;
import com.saodiseng.eduservice.entity.chapter.VideoVo;
import com.saodiseng.eduservice.mapper.EduChapterMapper;
import com.saodiseng.eduservice.service.EduChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saodiseng.eduservice.service.EduVideoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-14
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {


    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //1. 根据课程id查课程所有章节
        QueryWrapper<EduChapter> chapterQueryWrapperWrapper = new QueryWrapper<>();
        chapterQueryWrapperWrapper.eq("course_id",courseId);
        List<EduChapter> eduChapters = baseMapper.selectList(chapterQueryWrapperWrapper);

        //2. 根据课程id查询课程所有小结
        QueryWrapper<EduVideo> videoQueryWrapperWrapper = new QueryWrapper<>();
        videoQueryWrapperWrapper.eq("course_id",courseId);
        List<EduVideo> eduVideoList = eduVideoService.list(videoQueryWrapperWrapper);

        //创建list集合，用于最终封装数据
        List<ChapterVo> finalList = new ArrayList<>();
        //3.遍历查询章节list集合进行封装
        //遍历查询章节list集合
        for (int i = 0; i < eduChapters.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapters.get(i);
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(eduChapter,chapterVo);
            finalList.add(chapterVo);

            //创建集合 用于封装章节小节
            List<VideoVo> videofinalList = new ArrayList<>();
            //4. 遍历查询小节list集合，进行封装
            for (int i1 = 0; i1 < eduVideoList.size(); i1++) {
                //每个小节
                EduVideo eduVideo = eduVideoList.get(i1);
                //判断
                if(eduChapters.get(i).getId().equals(eduVideo.getChapterId())){ //id 相等
                    //进行封装
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    videofinalList.add(videoVo);
                }
            }
            //把封装之后小节list集合，放到章节对象中去
            chapterVo.setChildren(videofinalList);
        }
        //4. 遍历查询小节list集合，进行封装
        return finalList;
    }
}
