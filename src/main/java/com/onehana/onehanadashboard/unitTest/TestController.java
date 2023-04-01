package com.onehana.onehanadashboard.unitTest;

import com.onehana.onehanadashboard.config.BaseException;
import com.onehana.onehanadashboard.unitTest.model.*;
import com.onehana.onehanadashboard.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import static com.onehana.onehanadashboard.config.BaseResponseStatus.*;
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
    public BaseResponse<String> runUnitTest(@RequestBody UnitTestRequestJson request){
        if(request.getEndPoint() == null){return new BaseResponse<>(UNIT_TEST_ENDPOINT_EMPTY);}
//        List<UnitTestRequestJson.TestCode> testCodes = request.getTestCodes();

//        UnitTestResponseJson unitTestResponseJson = TestService.runUnitTest(request);

        return new BaseResponse<>("yes");


    }


}
