package com.fan.springboothoutai.common;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 全局统一返回结果类
 */
@Data
@ApiModel(value = "全局统一返回结果")
public class Result<T> {

    @ApiModelProperty(value = "返回码")
    private Integer code;

    @ApiModelProperty(value = "返回消息")
    private String message;

    @ApiModelProperty(value = "返回数据")
    private T data;

    public Result(){}

    protected static <T> Result<T> build(T data) {
        Result<T> result = new Result<T>();
        if (data != null)
            result.setData(data);
        return result;
    }

    public static <T> Result<T> build(T body, ResultCodeEnum resultCodeEnum) {
        Result<T> result = build(body);
        result.setCode(resultCodeEnum.getCode());
        result.setMessage(resultCodeEnum.getMessage());
        return result;
    }

    public static <T> Result<T> build(Integer code, String message) {
        Result<T> result = build(null);
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    public static<T> Result<T> ok(){
        return Result.ok(null);
    }

    public static<T> Result<T> loginSuccess(){
        return Result.loginSuccess(null);
    }

    public static<T> Result<T> loginError(){
        return Result.loginError(null);
    }

    public static<T> Result<T> registerError(){
        return Result.registerError(null);
    }
    public static<T> Result<T> registerSuccess(){
        return Result.registerSuccess(null);
    }


    /**
     * 操作成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> ok(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.SUCCESS);
    }

    /**
     * 登录成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> loginSuccess(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.LOGIN_SUCCESS);
    }

    /**
     * 注册失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> registerError(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.REGISTER_ERROR);
    }

    /**
     * 注册成功
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> registerSuccess(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.REGISTER_SUCCESS);
    }

    /**
     * 注册失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> loginError(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.LOGIN_ERROR);
    }



    public static<T> Result<T> fail(){
        return Result.fail(null);
    }

    /**
     * 操作失败
     * @param data
     * @param <T>
     * @return
     */
    public static<T> Result<T> fail(T data){
        Result<T> result = build(data);
        return build(data, ResultCodeEnum.FAIL);
    }

    public Result<T> message(String msg){
        this.setMessage(msg);
        return this;
    }

    public Result<T> code(Integer code){
        this.setCode(code);
        return this;
    }

    public boolean isOk() {
        if(this.getCode().intValue() == ResultCodeEnum.SUCCESS.getCode().intValue()) {
            return true;
        }
        return false;
    }
}