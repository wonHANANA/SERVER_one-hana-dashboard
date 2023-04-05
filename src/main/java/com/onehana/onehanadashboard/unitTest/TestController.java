package com.onehana.onehanadashboard.unitTest;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.unitTest.model.*;
import com.onehana.onehanadashboard.config.BaseResponse;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.onehana.onehanadashboard.config.BaseResponseStatus.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("test/")
public class TestController {
    @Autowired
    private TestService testService;

    public TestController(TestService testService){
        this.testService = testService;
    }

    @ResponseBody
    @GetMapping("only/for/string")
    public BaseResponse<String> onlyForStringTest(){
        return new BaseResponse<>("String만 반환하면 이런 모습입니다.");
    }

    @ResponseBody
    @PostMapping("unit")
    public BaseResponse<UnitTestResponseJson> runUnitTest(@RequestBody UnitTestRequestJson request){
        if(request.getEndPoint() == null){return new BaseResponse<>(TEST_ENDPOINT_EMPTY);}
        if(request.getBinding() == null){return new BaseResponse<>(TEST_BINDING_EMPTY);}
        if((request.getTestCodes() instanceof ArrayList<UnitTestRequestJson.TestCode>) == false){return new BaseResponse<>(TEST_TESTCODE_IS_NOT_OBJECT);}
        List<UnitTestRequestJson.TestCode> testCodes = request.getTestCodes();
        for(UnitTestRequestJson.TestCode testCode : testCodes){
            if(testCode.getTest() == null){return new BaseResponse<>(TEST_INNERTEST_NAME_EMPTY);}
            if(testCode.getMethod() == null){return new BaseResponse<>(TEST_INNERTEST_METHOD_EMPTY);}
            if(testCode.getRoute() == null){return new BaseResponse<>(TEST_INNERTEST_ROUTE_EMPTY);}
            if(testCode.getExpect() == null){return new BaseResponse<>(TEST_INNERTEST_EXPECT_EMPTY);}
            if(testCode.getExpect().get("where") == null){return new BaseResponse<>(TEST_INNERTEST_WHERE_EMPTY);}
            if(testCode.getExpect().get("toBe") == null){return new BaseResponse<>(TEST_INNERTEST_TOBE_EMPTY);};
        }
        try {
            UnitTestResponseJson unitTestResponseJson = TestService.runUnitTest(request);

            return new BaseResponse<>(unitTestResponseJson);
        } catch (ParseException e) {
            e.printStackTrace();
            return new BaseResponse<>(PARSE_EXCEPTION_ERROR);
        }

    }


}
