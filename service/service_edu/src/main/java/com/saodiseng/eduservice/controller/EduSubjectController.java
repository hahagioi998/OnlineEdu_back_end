package com.saodiseng.eduservice.controller;


import com.saodiseng.commonutils.R;
import com.saodiseng.eduservice.entity.subject.OneSubject;
import com.saodiseng.eduservice.service.EduSubjectService;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-09
 */
@RestController
@RequestMapping("/eduservice/subject")
@CrossOrigin
public class EduSubjectController {
    @Autowired
    private EduSubjectService eduSubjectService;
    //添加课程分类
    //获取到上传过来的文件，把文件内容读取出来
    @PostMapping("addSubject")
    public R addSubject(MultipartFile file){
        //上传过来的excel文件
        eduSubjectService.saveSubject(file,eduSubjectService);
        return R.ok();
    }

    //课程分类的列表功能   树形结构
    @GetMapping("getAllSubject")
    public R getAllSubject(){
        //重点返回数据的格式 封装数据
        //第一步 根据返回数据创建对应实体类     两个实体类  一个一级分类  一个二级分类  分别包括id和名称
        //第二步 在两个实体类之间表示他们之间的关系   一个一级分类  有多个二级分类

        //List集合中的泛型为一级分类，因为一级分类中包含了二级分类
         List<OneSubject> list= eduSubjectService.getAllOneTwoSubject();
        return R.ok().data("list",list);
    }
}

