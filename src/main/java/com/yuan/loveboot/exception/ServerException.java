package com.yuan.loveboot.exception;

import com.yuan.loveboot.enums.ResponseCode;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 自定义异常
 *
 * @author Maverick
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ServerException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 4952049816057351830L;

    private int code;
    private String msg;

    public ServerException(String msg) {
        super(msg);
        this.code = ResponseCode.VALIDATION_ERROR.getCode();
        this.msg = msg;
    }

    public ServerException(ResponseCode responseCode) {
        super(responseCode.getMsg());
        this.code = responseCode.getCode();
        this.msg = responseCode.getMsg();
    }

    public ServerException(String msg, Throwable e) {
        super(msg, e);
        this.code = ResponseCode.CUSTOM_ERROR.getCode();
        this.msg = msg;
    }

}