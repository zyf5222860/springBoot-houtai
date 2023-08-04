package com.fan.springboothoutai.exception;

import com.fan.springboothoutai.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GolbalExceptionHandler {
    /**
     * 全局异常处理器，针对业务异常
     * @param se
     * @return
     */
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result handle(ServiceException se){
        return Result.fail();
    }
}
