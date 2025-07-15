package com.tyz.rabbitmq.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @program: cloud-demo
 * @description: GlobalExceptionHandler
 * @author: tyz
 * @create: 2025-07-14
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    /**
     * 参数参数验证错误异常
     *
     * @param ex       异常
     * @return ResponseEntity ret
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity<?> error(MethodArgumentNotValidException ex){
        LOGGER.warn(ex.getMessage());
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);

        });
        return ResponseEntity.badRequest().body(errors);
    }
    /**
     * 参数绑定异常
     *
     * @param e       异常
     * @return ResponseEntity ret
     */
    @ExceptionHandler(BindException.class)
    @ResponseBody
    public ResponseEntity<?> error(BindException e){
        LOGGER.warn(e.getMessage());
        StringBuilder sb = new StringBuilder();
        for (ObjectError error : e.getBindingResult().getAllErrors()) {
            sb.append(error.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(sb);
    }
    /**
     * 参数违反约束规则的错误异常
     * <p>
     *     比如某个字段必须大于0，但用户输入了-1。这时也会收集所有错误提示并返回
     * </p>
     *
     * @param e       异常
     * @return Result
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseBody
    public ResponseEntity<?> error(ConstraintViolationException e){
        LOGGER.warn(e.getMessage());
        StringBuilder sb = new StringBuilder();
        for (ConstraintViolation<?> violation : e.getConstraintViolations()) {
            sb.append(violation.getMessage());
        }
        return ResponseEntity.badRequest().body(sb);
    }

    /**
     * 处理其他所有未处理的异常
     *
     * @param e       异常
     * @return Result
     */
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<?> error(Exception e){
        LOGGER.warn(e.getMessage());
        return ResponseEntity.badRequest().body(e.getMessage());
    }

    /**
     * 处理未知异常
     *
     * @param request 请求参数
     * @param e       异常
     * @return Result
     */
    @ExceptionHandler(value = Throwable.class)
    public ResponseEntity<?> unknownExceptionHandler(HttpServletRequest request, Throwable e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
