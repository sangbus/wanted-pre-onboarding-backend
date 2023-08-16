package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Builder
@Data
@AllArgsConstructor
/**
 * 클라이언트에 토큰 정보를 보내기 위한 DTO
 * */
public class TokenInfo {
    private String grantType; //JWT에 대한 인증 타입, Bearer 사용
    private String accessToken;
    private String refreshToken;
}
