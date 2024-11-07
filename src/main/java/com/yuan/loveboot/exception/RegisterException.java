package com.yuan.loveboot.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serial;

/**
 * 注册时异常
 *
 * @author Maverick
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class RegisterException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = -8628352765449065587L;
}