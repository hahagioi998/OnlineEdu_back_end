package com.saodiseng.eduservice.entity.subject;


import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class OneSubject {
    //一级分类
    private String id;
    private String title;

    //多个二级分类
    private List<TwoSubject> children = new ArrayList<>();

}
