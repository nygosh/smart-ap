package com.smart.ap.common.advice;

import java.nio.file.AccessDeniedException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.smart.ap.common.constraint.ResEntity;
import com.smart.ap.common.constraint.ResEnum;
import com.smart.ap.common.exception.CommonBizException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@ControllerAdvice(basePackages = "com.zp.point")
public class CommonAdvice {

    /**
     *  javax.validation.Valid or @Validated 으로 binding error 발생시 발생한다.
     *  HttpMessageConverter 에서 등록한 HttpMessageConverter binding 못할경우 발생
     *  주로 @RequestBody, @RequestPart 어노테이션에서 발생
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    protected ResponseEntity<Map<String, Object>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("handleMethodArgumentNotValidException", e);
        ResEnum responseInfo = ResEnum.PARAM_NOT_ALLOWED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getBindingResult().toString());
    }

    /**
     * @ModelAttribut 으로 binding error 발생시 BindException 발생한다.
     * ref https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html#mvc-ann-modelattrib-method-args
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<Map<String, Object>> handleBindException(BindException e) {
        log.error("handleBindException", e);
        ResEnum responseInfo = ResEnum.PARAM_NOT_ALLOWED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getBindingResult().toString());
    }

    /**
     * enum type 일치하지 않아 binding 못할 경우 발생
     * 주로 @RequestParam enum으로 binding 못했을 경우 발생
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    protected ResponseEntity<Map<String, Object>> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("handleMethodArgumentTypeMismatchException", e);
        ResEnum responseInfo = ResEnum.PARAM_NOT_ALLOWED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }

    /**
     * 지원하지 않은 HTTP method 호출 할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<Map<String, Object>> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.error("handleHttpRequestMethodNotSupportedException", e);
        ResEnum responseInfo = ResEnum.UNAUTHORIZED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }

    /**
     * Authentication 객체가 필요한 권한을 보유하지 않은 경우 발생합
     */
    @ExceptionHandler(AccessDeniedException.class)
    protected ResponseEntity<Map<String, Object>> handleAccessDeniedException(AccessDeniedException e) {
        log.error("handleAccessDeniedException", e);
        ResEnum responseInfo = ResEnum.UNAUTHORIZED;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }


    @ExceptionHandler(CommonBizException.class)
    protected ResponseEntity<Map<String, Object>> handleBusinessException(final CommonBizException e) {
        log.error("handleBusinessException", e);
        ResEnum responseInfo = ResEnum.SVC_ERROR;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getResMsg());
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<Map<String, Object>> handleException(Exception e) {
        log.error("handleException", e);
        ResEnum responseInfo = ResEnum.ERROR;
        return new ResEntity<>(new HashMap<String, Object>(), responseInfo, e.getMessage());
    }

}
