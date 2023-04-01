package com.onehana.onehanadashboard.config;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    /**
     * 100번대 에러 : 상태가 괜찮음
     */


    /**
     * 200번대 에러 : 요청이 성공적임
     */
    SUCCESS(true, "200-00-01", "요청에 성공하였습니다."),

    /**
     * 300번대 에러 : 요청에 대해 하나 이상의 응답이 가능함.
     */

    /**
     * 400번대 에러 : 클라이언트의 잘못된 요청으로 인한 에러들
     */
    UNIT_TEST_ENDPOINT_EMPTY(false, "500-00-01", "end-point는 반드시 입력되어야 합니다."),

    /**
     * 500번대 에러 : 서버 에러 등 서버 프로그래밍 잘못으로 인한 에러들
     */
    DATABASE_CONNECTION_ERROR(false, "500-00-01", "DB관련 에러 발생.");

    private final boolean isSuccess;
    private final String code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, String code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
