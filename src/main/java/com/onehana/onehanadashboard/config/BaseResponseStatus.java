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
    TEST_ENDPOINT_EMPTY(false, "400-02-01", "end-point를 입력해주세요 예) end-point: one-hana.site:8080."),
    TEST_BINDING_EMPTY(false, "400-02-02","binding을 입력해주세요 예) binding: [회원가입] 회원가입 입력 에러 테스트."),
    TEST_TESTCODES_EMPTY(false, "400-02-03", "testCodes를 입력해주세요 예) testCodes: [회원가입] 회원가입 입력 에러 테스트."),
    TEST_TESTCODE_IS_NOT_OBJECT(false, "400-02-04", "testCodes는 object 형식으로 입력해주세요."),
    TEST_INNERTEST_NAME_EMPTY(false, "400-02-05", "testCodes 안에 있는 test를 입력해주세요. 예) \"test\": \"이메일 형식 확인\""),
    TEST_INNERTEST_ROUTE_EMPTY(false, "400-02-06", "testCodes 안에 있는 route를 입력해주세요. 예) \"route\": \"/user/signin\""),
    TEST_INNERTEST_EXPECT_EMPTY(false, "400-02-07", "testCodes 안에 있는 expect를 입력해주세요. 예) \"expect\": {}"),
    TEST_INNERTEST_WHERE_EMPTY(false, "400-02-08", "testCodes 안에 있는 expect 안에 있는 where를 입력해주세요. 예) \"where\": \"response.body.code\""),
    TEST_INNERTEST_TOBE_EMPTY(false, "400-02-09", "testCodes 안에 있는 expect 안에 있는 toBe를 입력해주세요. 예) \"toBe\": 200"),
    TEST_INNERTEST_METHOD_EMPTY(false, "400-02-10", "testCodes 안에 있는 method를 입력해주세요. 예) \"method\": \"post\""),

    EMPTY_STRING(false, "400-02-11", "빈 문자열을 입력하셨습니다."),
    INVALID_DATE_TYPE(false, "400-02-12", "잘못된 날짜 입력 형식입니다. yyyyMMdd (20230101) 형식으로 입력해주세요."),
    INVALID_VALUE_TYPE(false, "400-02-13", "잘못된 자료형을 입력했습니다."),
    INVALID_JSON_REQUEST(false, "400-02-14", "JSON에 null값이나 잘못된 형식이 포함되어 있습니다."),

    /**
     * 500번대 에러 : 서버 에러 등 서버 프로그래밍 잘못으로 인한 에러들
     */
    DATABASE_CONNECTION_ERROR(false, "500-00-01", "DB관련 에러 발생."),
    PARSE_EXCEPTION_ERROR(false, "500-01-01", "파싱 작업 중 에러 발생"),

    DATABASE_DUPLICATE_VALUE(false, "500-01-02", "DB에 중복된 값이 이미 존재합니다."),
    DATABASE_NOT_FOUND(false, "500-01-03", "DB에서 해당하는 값을 찾을 수 없습니다."),

    NO_SUCH_SESSION_EXCEPTION(false, "500-01-04", "원하는 크롤링 숫자를 넘었습니다."),

    UNKNOWN_SERVER_ERROR(false, "500-00-44", "아직 처리 되지 않은 서버 오류입니다.");

    private final boolean isSuccess;
    private final String code;
    private final String message;

    BaseResponseStatus(boolean isSuccess, String code, String message) { //BaseResponseStatus 에서 각 해당하는 코드를 생성자로 맵핑
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}
