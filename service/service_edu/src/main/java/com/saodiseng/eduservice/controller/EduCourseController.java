package com.saodiseng.eduservice.controller;


import com.saodiseng.commonutils.R;
import com.saodiseng.eduservice.entity.vo.CourseInfoVo;
import com.saodiseng.eduservice.service.EduCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}

