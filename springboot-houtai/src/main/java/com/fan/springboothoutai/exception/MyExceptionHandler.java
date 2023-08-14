package com.fan.springboothoutai.exception;

import com.fan.springboothoutai.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 如果抛出的是ServiceException,则调用该方法
     */


    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.build(se.getCode(),se.getMessage());
}

}

