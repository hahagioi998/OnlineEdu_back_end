package com.saodiseng.eduservice.service;

import com.saodiseng.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.saodiseng.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-14
 */
public interface EduChapterService extends IService<EduChapter> {

    List<ChapterVo> getChapterVideoByCourseId(String courseId);
}
