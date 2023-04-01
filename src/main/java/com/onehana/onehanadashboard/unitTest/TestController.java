package com.onehana.onehanadashboard.unitTest;

import com.onehana.onehanadashboard.unitTest.model.*;
import com.onehana.onehanadashboard.config.BaseResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


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


}
