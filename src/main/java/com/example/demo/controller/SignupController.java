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
import java.util.regex.Pattern;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SignupController {
    // Regular expression pattern for email validation
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final MemberRepository memberRepository;

    @PostMapping(value = "/signup")
    public ResponseEntity<String> signup(@ModelAttribute Member member) {
        log.info("signup 호출");
        // Validate email and password
        if (!isValidEmail(member.getEmail())) {
            return new ResponseEntity<>("이메일 형식에 적합하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPassword(member.getPassword())) {
            return new ResponseEntity<>("비밀번호 형식에 적합하지 않습니다. 8자리 이상 설정해주세요.", HttpStatus.BAD_REQUEST);
        }

        // Encrypt the password before saving
        String encryptedPassword = encryptPassword(member.getPassword());

        // Save the member with the encrypted password
        savemember(member.getEmail(), encryptedPassword);
        log.info("회원 저장");
        return new ResponseEntity<>("Signup successful", HttpStatus.OK);
    }

    @GetMapping("/signup")
    public ModelAndView signupNew(@ModelAttribute("member") Member member){
        log.info("new에 접속");
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

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Error while encrypting password.", e);
        }
    }

    // Your method to save the member data in the database or any storage

    private void savemember(String email, String encryptedPassword) {
        // Save the member with email and encryptedPassword
        // ...

        Member member = new Member();
        member.setEmail(email);
        member.setPassword(encryptedPassword);
        memberRepository.save(member);
    }
}
