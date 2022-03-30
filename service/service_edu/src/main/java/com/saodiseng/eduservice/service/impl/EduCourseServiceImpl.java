package com.saodiseng.eduservice.service.impl;

import com.saodiseng.eduservice.entity.EduCourse;
import com.saodiseng.eduservice.entity.EduCourseDescription;
import com.saodiseng.eduservice.entity.vo.CourseInfoVo;
import com.saodiseng.eduservice.mapper.EduCourseMapper;
import com.saodiseng.eduservice.service.EduCourseDescriptionService;
import com.saodiseng.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.saodiseng.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-14
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {


    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {

        //向课程表中添加基本信息
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int insert =  baseMapper.insert(eduCourse);   // 像课程表加 必须是EduCourse这个实体，而传过来的是courseInfoVo ，需要转换一下
        if(insert == 0) {
            throw new GuliException(20001, "添加课程信息失败。。。。");
        }

        //获取到添加之后的课程id
        String cid = eduCourse.getId();

        //向描述表中添加信息，不能直接在此添加
        //需要用到EduCourseDescriptionService来调用插入，需要先注入
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id为课程id 相同
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        EduCourse eduCourse = baseMapper.selectById(id); //无需注入
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(id);
        BeanUtils.copyProperties(eduCourse,courseInfoVo);
        BeanUtils.copyProperties(courseDescription,courseInfoVo);
        return courseInfoVo;
    }
}
