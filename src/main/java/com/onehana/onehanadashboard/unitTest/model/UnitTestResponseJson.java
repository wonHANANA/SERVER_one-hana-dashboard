package com.onehana.onehanadashboard.unitTest.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UnitTestResponseJson {
    private String binding;
    private boolean allSuccess;
    private List<responseTest> localResult;

    @Getter
    @Setter
    public static class responseTest{
        private String testName;
        private boolean result;
        private String message;
    }
}

