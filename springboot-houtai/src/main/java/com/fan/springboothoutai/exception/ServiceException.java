package com.fan.springboothoutai.exception;

import com.fan.springboothoutai.common.Result;

/**
 * 自定义异常
 */
public class ServiceException extends RuntimeException{

    public ServiceException(Result result){
        super(result.getMessage());
    }
}
