package com.saodiseng.servicebase.exceptionhandler;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data    //get set 方法 自动生成
@AllArgsConstructor   //生成有参构造方法
@NoArgsConstructor   //生成无参数构造方法
public class GuliException extends RuntimeException {

    private Integer code;//状态码

    private String msg;//异常信息


}
