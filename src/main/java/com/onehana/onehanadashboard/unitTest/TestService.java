package com.onehana.onehanadashboard.unitTest;

import com.onehana.onehanadashboard.unitTest.model.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class TestService{
    public static UnitTestResponseJson runUnitTest(UnitTestRequestJson request)  throws ParseException {
        Boolean allSuccess = true;
        List<UnitTestRequestJson.TestCode> testCodes = request.getTestCodes();
        List<UnitTestResponseJson.responseTest> responseTestList = new ArrayList<>();
        RestTemplate restTemplate = new RestTemplate();

        for(UnitTestRequestJson.TestCode testCode : testCodes){
            //각각의 테스트 응답 내역 저장
            UnitTestResponseJson.responseTest localResponseTest = new UnitTestResponseJson.responseTest();
            localResponseTest.setTestName(testCode.getTest());

            String url = request.getEndPoint() + testCode.getRoute();

            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            HttpHeaders httpHeaders = new HttpHeaders();
            HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);

            for(String key : testCode.getBody().keySet()){
                body.add(key, testCode.getBody().get(key));
            }

            if(testCode.getMethod().toLowerCase().equals("post")){
                try{
                    HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

                    JSONParser parser = new JSONParser();
                    JSONObject jsonObject = (JSONObject) parser.parse(response.getBody());
                    if(jsonObject.get(testCode.getExpect().get("where")).equals(testCode.getExpect().get("toBe"))){
                        localResponseTest.setResult(true);
                        localResponseTest.setMessage("테스트에 성공하였습니다.");
                    }
                    else{
                        allSuccess = false;
                        localResponseTest.setResult(false);
                        System.out.println(jsonObject);
                        localResponseTest.setMessage((String) jsonObject.get("message"));
                    }
                } catch (HttpClientErrorException e){
                    allSuccess = false;
                    localResponseTest.setResult(false);
                    localResponseTest.setMessage(e.getMessage().split(":")[0]);
                }
            }
            else if(testCode.getMethod().toLowerCase().equals("get")){
                if(body != null){
                    allSuccess = false;
                    localResponseTest.setResult(false);
                    localResponseTest.setMessage("GET 요청에 Body가 감지되었습니다.");
                }
            }
            responseTestList.add(localResponseTest);
        }
        UnitTestResponseJson responseJson = new UnitTestResponseJson(request.getBinding(), allSuccess, responseTestList);

        return responseJson;
    }

}