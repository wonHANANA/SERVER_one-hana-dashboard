package com.onehana.onehanadashboard.config;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.HttpMethod.*;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig implements WebMvcConfigurer {

    private final BCryptPasswordEncoder passwordEncoder;

    @Value("${one-hana.swagger-id}")
    private String swaggerId;

    @Value("${one-hana.swagger-pd}")
    private String swaggerPd;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        BasicAuthenticationEntryPoint basicAuthenticationEntryPoint = new BasicAuthenticationEntryPoint();
        basicAuthenticationEntryPoint.setRealmName("swagger");

        return http
                .cors()
                .and()
                .csrf().disable()
                .formLogin().disable()
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**").authenticated()
                        .anyRequest().permitAll()
                )
                .httpBasic(httpBasic -> httpBasic
                        .authenticationEntryPoint(basicAuthenticationEntryPoint)
                )
                .build();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
        List<UserDetails> userDetailsList = new ArrayList<>();

        userDetailsList.add(User.builder()
                .username(swaggerId)
                .password(passwordEncoder.encode(swaggerPd))
                .roles("ADMIN")
                .build());

        return new InMemoryUserDetailsManager(userDetailsList);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")
                .allowedOrigins("http://localhost:3000", "http://13.124.107.70:80", "http://13.124.107.70:8000", "http://localhost:8000"
                ,"http://localhost:3000", "http://www.i-log-u.site", "http://i-log-u.site", "http://i-log-u.site:8000")
                .allowedHeaders("*")
                .allowedMethods(GET.name(), POST.name(), PUT.name(), DELETE.name())
                .allowCredentials(true);
    }
}