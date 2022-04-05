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
import com.saodiseng.servicebase.exceptionhandler.GuliException;
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

    //删除章节的方法   章节下有小节不让删， 没有了才能删
    @Override
    public boolean deleteChapter(String chapterId) {
        //根据章节Id查小节         如果查询出数据 不删除
        QueryWrapper<EduVideo> wrapper = new QueryWrapper<>();
        wrapper.eq("chapter_id",chapterId);
        int count = eduVideoService.count(wrapper);  //只想知道对应的id 的小节中有没有数据
        if (count > 0) {
            return false;
        } else {
            int result = baseMapper.deleteById(chapterId);
            return result > 0;
        }
    }

    //根据课程id删除章节
    @Override
    public void removeChapterByCourseId(String courseId) {
        QueryWrapper<EduChapter> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id",courseId);
        baseMapper.delete(wrapper);
    }
}
