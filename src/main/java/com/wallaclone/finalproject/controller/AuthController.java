package com.wallaclone.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallaclone.finalproject.dto.LoginResponseDto;
import com.wallaclone.finalproject.dto.SignUpResponseDto;
import com.wallaclone.finalproject.dto.UsuarioDto;
import com.wallaclone.finalproject.service.impl.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<SignUpResponseDto> signUp(@RequestBody UsuarioDto signUpRequest){
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }
    @PostMapping("/signin")
    public ResponseEntity<LoginResponseDto> signIn(@RequestBody UsuarioDto signInRequest){
        return ResponseEntity.ok(authService.signIn(signInRequest));
    }
//    @PostMapping("/refresh")
//    public ResponseEntity<UsuarioDto> refreshToken(@RequestBody UsuarioDto refreshTokenRequest){
//        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
//    }
}
