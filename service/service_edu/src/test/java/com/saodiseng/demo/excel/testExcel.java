package com.saodiseng.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class testExcel {

    public static void main(String[] args) {

        //实现写操作
        //1. 设置写入文件夹地址和excel文件名称
        String filename = "D:\\write.xlsx";

        //2.调用EasyExcel里的方法来实现写操作
        //write中两个参数 1文件路径名  2实体类class
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());



        //读操作
        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //创建方法返回list
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            DemoData demodata = new DemoData();
            demodata.setSno(i);
            demodata.setSname("make"+i);
            list.add(demodata);
        }
        return list;
    }
}
