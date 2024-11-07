package com.yuan.loveboot.utils;

import com.yuan.loveboot.enums.ResponseCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 响应数据
 *
 * @author Maverick
 */
@Data
@Schema(description = "响应")
@Accessors(chain = true)
public class Result<T> {
    @Schema(description = "响应编码")
    private int code = ResponseCode.OK.getCode();

    @Schema(description = "消息内容")
    private String msg = ResponseCode.OK.getMsg();

    @Schema(description = "响应数据")
    private T data;

    public static <T> Result<T> ok() {
        return ok(null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<T>().setData(data);
    }

    public static <T> Result<T> error() {
        return error(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    public static <T> Result<T> error(String msg) {
        return error(ResponseCode.CUSTOM_ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(ResponseCode responseCode) {
        return error(responseCode.getCode(), responseCode.getMsg());
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<T>().setCode(code).setMsg(msg);
    }
}
