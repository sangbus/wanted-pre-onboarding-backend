package com.example.demo.controller;

import com.example.demo.domain.user.Member;
import com.example.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Collections;
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignupController {
    // 이메일 유효성 검사를 위한 정규식 패턴
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final MemberRepository memberRepository;

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@RequestBody Member member) {
        log.info("signup 호출");
        // 이메일 및 비밀번호 확인
        if (!isValidEmail(member.getEmail())) {
            return new ResponseEntity<>("이메일 형식에 적합하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPassword(member.getPassword())) {
            return new ResponseEntity<>("비밀번호 형식에 적합하지 않습니다. 8자리 이상 설정해주세요.", HttpStatus.BAD_REQUEST);
        }

        // 저장하기 전 비밀번호 암호화
        String encryptedPassword = encryptPassword(member.getPassword());

        // 암호화한 비밀번호와 이메일 저장
        saveMember(member.getEmail(), encryptedPassword);
        log.info("회원 저장");
        return new ResponseEntity<>("회원가입 성공", HttpStatus.OK);
    }

    @GetMapping("/signup")
    public ModelAndView signupNew(@ModelAttribute("member") Member member){
        log.info("signupnew에 접속");
        return new ModelAndView("/sign/helloSign");
    }
    
    //이메일 형식 체크
    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    //비밀번호 8자리 이상
    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }

    //비밀번호 암호화
    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256"); // 비밀번호 SHA-256 암호화
            byte[] hashedPassword = md.digest(password.getBytes()); // 바이트배열로 해쉬를 반환
            return Base64.getEncoder().encodeToString(hashedPassword); // Base64로 인코딩, 바이트 -> String 반환
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 중 에러가 발생하였습니다.", e); // Runtime 에러
        }
    }

    // Your method to save the member data in the database or any storage

    private void saveMember(String email, String encryptedPassword) {
        // Save the member with email and encryptedPassword
        // ...

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encryptedPassword);
        member.setRoles(Collections.singletonList("USER"));
        memberRepository.save(member);
    }

}
