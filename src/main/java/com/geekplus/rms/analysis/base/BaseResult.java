package com.geekplus.rms.analysis.base;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.extern.slf4j.Slf4j;

/**
 * @author smz
 * @Description: 返回值统一封装类
 * @date 2017年7月26日
 */
@Slf4j
@ApiModel(description = "返回值统一封装")
public class BaseResult<T extends Object> implements Serializable {


    private static final long serialVersionUID = 1368407471638948451L;
    @ApiModelProperty(value = "是否成功")
    public Boolean success = true;
    @ApiModelProperty(value = "响应内容")
    private T content;
    @ApiModelProperty(value = "说明")
    private String message;
    @ApiModelProperty(value = "响应码")
    private int code = 200;

    public BaseResult() {
    }

    public BaseResult(T content) {
        this.content = content;
        success = true;
    }

    public BaseResult(int code, String message) {
        this.message = message;
        this.code = code;
        success = false;
    }

    public BaseResult(Boolean success, String message) {
        this.message = message;
        this.success = success;
        if(!success){
            this.code =-1;
        }
    }

    public BaseResult(Boolean success, int code, String message) {
        this.message = message;
        this.code = code;
        this.success = success;
    }

    public BaseResult(Boolean success, T content, int code, String message) {
        this.message = message;
        this.code = code;
        this.content = content;
        this.success = success;
    }

    public BaseResult<T> success(String message) {
        this.setSuccess(true);
        this.setMessage(message);
        return this;
    }

    public BaseResult<T> failure(String message) {
        this.setSuccess(false);
        this.setMessage(message);
        return this;
    }

    public BaseResult<T> content(T t) {
        this.setContent(t);
        return this;
    }

    public BaseResult<T> code(int code) {
        this.setCode(code);
        return this;
    }

    public BaseResult<T> successContent(T t) {
        this.content(t);
        this.success = true;
        return this;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}
