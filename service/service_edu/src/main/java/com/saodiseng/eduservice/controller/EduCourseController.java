package com.saodiseng.eduservice.controller;


import com.saodiseng.commonutils.R;
import com.saodiseng.eduservice.entity.EduCourse;
import com.saodiseng.eduservice.entity.vo.CourseInfoVo;
import com.saodiseng.eduservice.entity.vo.CoursePublishVo;
import com.saodiseng.eduservice.service.EduCourseService;
import lombok.val;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-14
 */
@RestController
@RequestMapping("/eduservice/course")
@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService eduCourseService;

    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){

        String id = eduCourseService.saveCourseInfo(courseInfoVo);
        return R.ok().data("courseId",id);
    }

    //根据课程id查询课程信息
    @GetMapping("getCourse/{id}")
    public R checkCourseInfo(@PathVariable String id){
        CourseInfoVo courseInfoVo = eduCourseService.getCourseInfo(id);
        return R.ok().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        eduCourseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }

    //根据课程id查询课程确认信息
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id){
        CoursePublishVo publishVo = eduCourseService.publishCourseInfo(id);
        return R.ok().data("publishCourse",publishVo);
    }

    //课程最终发布   修改课程状态即可
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id){
        EduCourse course = new EduCourse();
        course.setId(id);
        course.setStatus("Normal");
        eduCourseService.updateById(course);
        return R.ok();
    }

    //课程列表功能
    //TODO 完善条件查询带分页
    @GetMapping
    public R getCourseList(){
        List<EduCourse> list = eduCourseService.list(null);
        return R.ok().data("list",list);
    }
}

