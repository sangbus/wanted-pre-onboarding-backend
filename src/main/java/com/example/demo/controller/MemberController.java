package com.example.demo.controller;

import com.example.demo.Service.MemberService;
import com.example.demo.domain.user.Member;
import com.example.demo.dto.MemberLoginRequestDto;
import com.example.demo.dto.TokenInfo;
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
@RequestMapping("/members")
public class MemberController {

    private final MemberService memberService;

    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody MemberLoginRequestDto memberLoginRequestDto) {
        log.info("login(post)에 접속");
        if (!isValidEmail(memberLoginRequestDto.getEmail())) {
            return new ResponseEntity<>("이메일 형식에 적합하지 않습니다.", HttpStatus.BAD_REQUEST);
        }

        if (!isValidPassword(memberLoginRequestDto.getPassword())) {
            return new ResponseEntity<>("비밀번호 형식에 적합하지 않습니다. 8자리 이상 설정해주세요.", HttpStatus.BAD_REQUEST);
        }
        String email = memberLoginRequestDto.getEmail();
        String password = encryptPassword(memberLoginRequestDto.getPassword());
        TokenInfo tokenInfo = memberService.login(email, password);
        log.info("login(post) 끝");
        String result = "GrantType : "+tokenInfo.getGrantType()+"\n"+
                "RefreshToken : " + tokenInfo.getRefreshToken()+"\n"+
                "AccessToken : " + tokenInfo.getAccessToken();
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/login")
    public ModelAndView login(@ModelAttribute("member") Member member){
        log.info("login(get)에 접속");
        return new ModelAndView("/login");
    }

    @GetMapping("/logout")
    public ModelAndView logout(@ModelAttribute Member member) {
        log.info("Logout");
        return new ModelAndView("/login");
    }

    private String encryptPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedPassword = md.digest(password.getBytes());
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("암호화 중 에러가 발생하였습니다.", e);
        }
    }

    private boolean isValidEmail(String email) {
        return EMAIL_PATTERN.matcher(email).matches();
    }

    //비밀번호 8자리 이상
    private boolean isValidPassword(String password) {
        return password.length() >= 8;
    }
}
