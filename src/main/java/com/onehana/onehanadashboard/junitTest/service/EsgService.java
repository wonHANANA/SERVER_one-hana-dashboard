package com.onehana.onehanadashboard.junitTest.service;

import com.onehana.onehanadashboard.junitTest.entity.Esg;
import com.onehana.onehanadashboard.junitTest.repository.EsgRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EsgService {

    private final EsgRepository esgRepository;

    public void addEsg(Esg esg) {
        esgRepository.findByKeyword(esg.getKeyword()).ifPresent(it -> {
            throw new RuntimeException("이미 있는 키워드에요");
        });

        esgRepository.save(esg);
    }
}
