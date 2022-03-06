package com.saodiseng.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.saodiseng.commonutils.R;
import com.saodiseng.eduservice.entity.EduTeacher;
import com.saodiseng.eduservice.entity.vo.TeacherQuery;
import com.saodiseng.eduservice.service.EduTeacherService;
import com.saodiseng.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-11
 */

@Api(tags="讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin//RestController 作用一个是交给springboot进行管理，一个是能够返回JSON数据
public class EduTeacherController {

    //访问地址   http://localhost:8001/eduservice/teacher/findAll     RequestMapping



    //首先按照开发流程，Controller需要注入service
    @Autowired
    private EduTeacherService teacherService;

    //1 查询讲师表中的所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")  //表示查询用Get方法
    public R findAllTeacher(){    //查找所有用户，返回的肯定是List     加入规范数据后直接统一为R
        //调用service的方法实现查询所有操作
        List<EduTeacher> list = teacherService.list(null);
        return R.ok().data("items",list);  //在服务器上是将list转换成JSON数据进行显示
    }


    // 2讲师逻辑删除功能   首先是插件，其次在实体类中加注解
    @ApiOperation(value = "根据ID逻辑删除讲师")
    @DeleteMapping("{id}") //rest风格中删除用Delete    参数表示按照id删除  通过路径传id值
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){     //注解的意思为 通过路径获取参数
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }


    //3 分页查询讲师的方法
    //current当前页
    //limit每页记录数
    @ApiOperation(value = "分页查询讲师")
    @GetMapping("pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable long current,@PathVariable long limit){

        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);

        try{
            int i = 10/0;
        }catch (Exception e){
            //执行自定义异常
            throw new GuliException(20001,"自定义异常处理Guli。。。");
        }


        //调用方法实现分类
        //调用方法时，低层封装，把分页所有数据封装到pageTeacher对象里
        teacherService.page(pageTeacher,null);
        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords();// 返回数据的list集合

        //两种返回方式
//        Map<String, Object> map = new HashMap<>();
//        map.put("total",total);
//        map.put("rows",records);
//        return R.ok().data(map);

        return R.ok().data("total",total).data("rows",records);

    }

    //4 条件查询带分页的方法
    @ApiOperation(value = "多条件分页查询讲师")
    @PostMapping("pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable long current,@PathVariable long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){  //以对象形式得到条件 将数据封装为Json格式到对象中
                                    //false表示required值可以没有  但是要将Get改为Post才能请求到
        //创建page对象
        Page<EduTeacher> pageTeacher = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        //多条件组合查询
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //判断条件是否为空，不为null则拼接条件
        if (!StringUtils.isEmpty(name)){
            //构建条件
            wrapper.like("name",name);
        }
        if (!StringUtils.isEmpty(level)){
            wrapper.eq("level",level);
        }
        if (!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if (!StringUtils.isEmpty(end)){
            wrapper.le("gmt_modified",end);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现查询带分页功能
        teacherService.page(pageTeacher,wrapper);

        long total = pageTeacher.getTotal(); //总记录数
        List<EduTeacher> records = pageTeacher.getRecords();// 返回数据的list集合

        return R.ok().data("total",total).data("rows",records);

    }

    //添加讲师接口的方法    首先应该想到某些字段要自动填充      添加使用Post方法提交-参数是对象
    @PostMapping("addTeacher")
    @CrossOrigin
    public R addTeacher(@RequestBody EduTeacher eduTeacher){

        boolean save = teacherService.save(eduTeacher);
        if(save){
            return R.ok();
        }else {
            return R.error();
        }

    }

    //讲师修改功能     先根据ID查询，实现修改
    @GetMapping("getTeacher/{id}")
    public R getTeacher(@PathVariable Long id){
        EduTeacher eduTeacher = teacherService.getById(id);
        return R.ok().data("teacher",eduTeacher);
    }

    //讲师修改
    @PostMapping("updateTeacher")   //参数是对象
    public R updateTeacher(@RequestBody EduTeacher eduTeacher){
        boolean b = teacherService.updateById(eduTeacher);
        if(b){
            return R.ok();
        } else {
            return R.error();
        }
    }






}

