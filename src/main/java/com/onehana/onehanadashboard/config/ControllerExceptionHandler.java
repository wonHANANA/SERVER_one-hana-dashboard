package com.onehana.onehanadashboard.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.util.Arrays;
import java.util.Objects;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)    // 잘못된 json 정보 처리
    public BaseResponse<Object> HttpMessageNotReadableException(HttpMessageNotReadableException e) {
        BaseResponseStatus status = BaseResponseStatus.INVALID_JSON_REQUEST;
        return new BaseResponse<>(status);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)    // 유효성 검사 실패
    public BaseResponse<Object> MethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.info(Arrays.toString(e.getDetailMessageArguments()));
        BaseResponseStatus status = BaseResponseStatus.INVALID_JSON_REQUEST;
        return new BaseResponse<>(status);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)    // 잘못된 자료형을 처리
    public BaseResponse<Object> MethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        BaseResponseStatus status = BaseResponseStatus.INVALID_VALUE_TYPE;
        return new BaseResponse<>(status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)    // DB 중복오류
    public BaseResponse<Object> DataIntegrityViolationException(DataIntegrityViolationException e) {
        BaseResponseStatus status = BaseResponseStatus.DATABASE_DUPLICATE_VALUE;
        return new BaseResponse<>(status);
    }

    @ExceptionHandler(BaseException.class)
    public BaseResponse<Object> BaseException(BaseException e) {
        BaseResponseStatus status = e.getStatus();
        return new BaseResponse<>(status);
    }

    @ExceptionHandler(Exception.class)
    public BaseResponse<Object> Exception(Exception e) {
        BaseResponseStatus status = BaseResponseStatus.UNKNOWN_SERVER_ERROR;
        e.printStackTrace();
        return new BaseResponse<>(status);
    }
}