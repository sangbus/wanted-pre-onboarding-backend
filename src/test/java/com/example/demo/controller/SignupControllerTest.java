package com.example.demo.controller;

import com.example.demo.domain.user.Member;
import com.example.demo.repository.MemberRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
@SpringBootTest
class SignupControllerTest{
    @Autowired
    private MemberRepository memberRepository;

    @AfterEach
    void afterEach() {
        memberRepository.deleteAll();
    }

    @Test
    void signup() {
        //given
        Member member = Member.builder()
                .email("tkdghks4564@naver.com")
                .password("tkdghks12")
                .build();
        //when
        Member savedMember = memberRepository.save(member);

        //then
        Optional<Member> findMember = memberRepository.findByEmail(member.getEmail());
        //이메일 체크
        assertThat(savedMember.getEmail()).isEqualTo(findMember.get().getEmail());
        //비밀번호 체크
        assertThat(savedMember.getPassword()).isEqualTo(findMember.get().getPassword());
    }
}