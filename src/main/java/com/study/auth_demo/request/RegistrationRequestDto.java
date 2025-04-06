package com.study.auth_demo.request;

public record RegistrationRequestDto(
        String username,
        String email,
        String password
){

}
