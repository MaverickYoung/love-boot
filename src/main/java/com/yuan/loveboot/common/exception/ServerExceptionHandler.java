package com.yuan.loveboot.common.exception;

import com.yuan.loveboot.common.enums.ResponseCode;
import com.yuan.loveboot.common.utils.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.support.MissingServletRequestPartException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.time.format.DateTimeParseException;


/**
 * 异常处理器
 *
 * @author Maverick
 */
@Slf4j
@RestControllerAdvice
public class ServerExceptionHandler {
    /**
     * 处理自定义异常
     */
    @ExceptionHandler(ServerException.class)
    public Result<String> handleException(ServerException e) {
        return Result.error(e.getCode(), e.getMsg());
    }

    /**
     * 请求不存在
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(NoResourceFoundException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("404 Not Found: " + e.getResourcePath());
    }


    /**
     * SpringMVC参数绑定，Validator校验不正确
     */
    @ExceptionHandler(BindException.class)
    public Result<String> bindException(BindException e) {
        FieldError fieldError = e.getFieldError();
        assert fieldError != null;
        return Result.error(ResponseCode.VALIDATION_ERROR, fieldError.getDefaultMessage());
    }

    /**
     * SpringMVC参数缺失
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> missingParamException(MissingServletRequestParameterException e) {
        return Result.error(ResponseCode.VALIDATION_ERROR, String.format("缺少必要请求参数 %s", e.getParameterName()));
    }

    /**
     * SpringMVC 请求体缺少文件部分
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public Result<String> missingPartParamException(MissingServletRequestPartException e) {
        return Result.error(ResponseCode.VALIDATION_ERROR, String.format("缺少必要请求文件参数 %s", e.getRequestPartName()));
    }

    /**
     * 时间格式转化异常
     */
    @ExceptionHandler(DateTimeParseException.class)
    public Result<String> handleBindException() {
        return Result.error(ResponseCode.VALIDATION_ERROR, "时间格式错误，请使用 YYYY-MM");
    }

    /**
     * 服务器异常
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return Result.error(ResponseCode.INTERNAL_SERVER_ERROR);
    }

}