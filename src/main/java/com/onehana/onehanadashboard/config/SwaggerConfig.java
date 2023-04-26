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
                .group("검색 키워드")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi sns() {
        String[] paths = {"/sns/**"};

        return GroupedOpenApi.builder()
                .group("유튜브 댓글")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi relatedKeyword() {
        String[] paths = {"/related-keyword/**"};

        return GroupedOpenApi.builder()
                .group("파생 키워드")
                .pathsToMatch(paths)
                .build();
    }

    @Bean
    public GroupedOpenApi S3Url() {
        String[] paths = {"/s3/**"};

        return GroupedOpenApi.builder()
                .group("S3 url")
                .pathsToMatch(paths)
                .build();
    }
}
