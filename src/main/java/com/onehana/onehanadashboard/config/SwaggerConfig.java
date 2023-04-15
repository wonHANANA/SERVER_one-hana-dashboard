package com.onehana.onehanadashboard.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "전준휘의 비밀 일기장",
                description = "히힛-♥️",
                version = "v1")
)
@RequiredArgsConstructor
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi news() {
        String[] paths = {"/news/**"};

        return GroupedOpenApi.builder()
                .group("네이버 뉴스기사")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi google() {
        String[] paths = {"/google/**"};

        return GroupedOpenApi.builder()
                .group("구글 트렌드")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi keyword() {
        String[] paths = {"/keyword/**"};

        return GroupedOpenApi.builder()
                .group("키워드 정보")
                .pathsToMatch(paths)
                .build();
    }
}
