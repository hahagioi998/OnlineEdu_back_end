package com.saodiseng.servicebase.exceptionhandler;



import com.saodiseng.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
@Slf4j //将异常信息输出到日志中
public class GlobalExceptionHandler {

    //指定出现什么异常执行这个方法
    @ExceptionHandler(Exception.class)
    @ResponseBody  //为了能够返回数据   因为不在controller层
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody  //为了能够返回数据   因为不在controller层
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了ArithmeticException异常处理");
    }

    //自定义异常
    @ExceptionHandler(GuliException.class)
    @ResponseBody  //为了能够返回数据   因为不在controller层
    public R error(GuliException e){
        log.error(e.getMessage());//将错误写到日志（error、info等）中
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }

}
