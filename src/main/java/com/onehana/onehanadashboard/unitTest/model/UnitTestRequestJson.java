package com.onehana.onehanadashboard.unitTest.model;

import lombok.*;

import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnitTestRequestJson {
    private String endPoint;
    private String binding;
    private List<TestCode> testCodes;

    @Getter
    public static class TestCode {
        private String test;
        private String method;
        private String route;
        private Map<String, Object> body;
        private Map<String, Object> expect;
    }
}
