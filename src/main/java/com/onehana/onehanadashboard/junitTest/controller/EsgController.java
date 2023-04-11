package com.onehana.onehanadashboard.junitTest.controller;

import com.onehana.onehanadashboard.junitTest.entity.Esg;
import com.onehana.onehanadashboard.junitTest.service.EsgService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/unitTest")
public class EsgController {

    private final EsgService esgService;

    @PostMapping("/")
    public void addEsg(@RequestBody Esg esg) {
        esgService.addEsg(esg);
    }
}
