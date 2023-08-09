package com.example.demo.dto;

import lombok.Data;

@Data
public class MemberLoginRequestDto {
    private String email;
    private String password;
}