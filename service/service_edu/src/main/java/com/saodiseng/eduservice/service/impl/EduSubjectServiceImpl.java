package com.saodiseng.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.saodiseng.eduservice.entity.EduSubject;
import com.saodiseng.eduservice.entity.excel.SubjectData;
import com.saodiseng.eduservice.entity.subject.OneSubject;
import com.saodiseng.eduservice.entity.subject.TwoSubject;
import com.saodiseng.eduservice.listener.SubjectExcelListener;
import com.saodiseng.eduservice.mapper.EduSubjectMapper;
import com.saodiseng.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-09
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService eduSubjectService) {
        try{
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);

        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperTwo.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        List<OneSubject> finalSubjectList = new ArrayList<>();

        for (int i = 0; i < oneSubjectList.size(); i++) {
            OneSubject oneSubject = new OneSubject();
            //oneSubject.setId(oneSubjectList.get(i).getId());
            //oneSubject.setTitle(oneSubjectList.get(i).getTitle());
            BeanUtils.copyProperties(oneSubjectList.get(i),oneSubject); //将参数1的属性赋值给参数二 简化上面的写法
            finalSubjectList.add(oneSubject);
            List<TwoSubject> twoList = new ArrayList<>();
            for (int j = 0; j < twoSubjectList.size(); j++) {
                if (Objects.equals(twoSubjectList.get(j).getParentId(), oneSubjectList.get(i).getId())){
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(twoSubjectList.get(j),twoSubject);
                    twoList.add(twoSubject);
                }
            }
            oneSubject.setChildren(twoList);   //二级循环全部结束 再加入表中
        }
        return finalSubjectList;
    }
}
