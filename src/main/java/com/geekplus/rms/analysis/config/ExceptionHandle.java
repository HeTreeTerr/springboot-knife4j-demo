package com.geekplus.rms.analysis.config;

import com.geekplus.rms.analysis.base.BaseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * <p>
 * 异常处理器
 * </p>
 *
 * @author Hss
 * @date 2023-06-21
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandle {

    /**
     * Exception 异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler({java.lang.Exception.class})
    @ResponseBody
    public BaseResult handle(Exception e) {
        log.error("接口错误",e);
        return new BaseResult().failure(StringUtils.isBlank(e.getMessage()) ? "接口错误。" : "接口错误，" + e.getMessage());
    }

    /**
     * BindException 异常处理器
     * @param e
     * @return
     */
    @ExceptionHandler({BindException.class})
    @ResponseBody
    public BaseResult handle(BindException e) {
        log.error("参数校验失败",e);
        String msg = "参数非法";
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            msg = msg + "，" + error.getDefaultMessage();
        }
        return new BaseResult().failure(msg);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public BaseResult handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("参数校验失败",e);
        String msg = "参数非法";
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            msg = msg + "，" + error.getDefaultMessage();
        }
        return new BaseResult().failure(msg);
    }

}
