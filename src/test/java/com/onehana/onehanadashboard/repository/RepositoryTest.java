package com.onehana.onehanadashboard.repository;

import com.onehana.onehanadashboard.junitTest.entity.Esg;
import com.onehana.onehanadashboard.junitTest.repository.EsgRepository;
import org.hibernate.AssertionFailure;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.regex.Pattern;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class RepositoryTest {

    @Autowired
    private EsgRepository esgRepository;

    @Test
    @DisplayName("ESG keyword 삽입 테스트")
    void add_esg_keyword() {
        Esg esg = new Esg();
        esg.setKeyword("친환경");

        String myEsg = esgRepository.save(esg).getKeyword();
        String savedEsg = esgRepository.findById(1L).orElseThrow().getKeyword();

        assertThat(myEsg.length())
                .as("길이가 안맞네??")
                .isGreaterThan(0)
                .isLessThan(10);

        if (!Pattern.matches("^[0-9|a-z|A-Z|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|\s]*$", myEsg)) {
            throw new AssertionFailure("특수문자가 있네??");
        }

        if (!Pattern.matches("^[|ㄱ-ㅎ|ㅏ-ㅣ|가-힣|]*$", myEsg)) {
            throw new AssertionFailure("영어가 있네??");
        }

        assertThat(savedEsg)
                .as("내가 넣은 키워드가 아니네??")
                .isEqualTo("친환경");
    }
}
