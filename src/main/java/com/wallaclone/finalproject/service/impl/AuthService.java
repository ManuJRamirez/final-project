package com.wallaclone.finalproject.service.impl;

import java.util.HashMap;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.LoginResponseDto;
import com.wallaclone.finalproject.dto.SignUpResponseDto;
import com.wallaclone.finalproject.dto.UsuarioDto;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.utils.JWTUtils;

@Service
public class AuthService {

    @Autowired
    private UsuariosRepository usuariosRepository;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
	@Autowired
	ModelMapper modelMapper;
	
    public SignUpResponseDto signUp(UsuarioDto registrationRequest){
    	SignUpResponseDto resp = new SignUpResponseDto();
        try {
            Usuario Usuario = modelMapper.map(registrationRequest, Usuario.class);
            Usuario.setContrasenia(passwordEncoder.encode(registrationRequest.getContrasenia()));
            Usuario.setRole("USER");
            Usuario ourUserResult = usuariosRepository.save(Usuario);
            if (ourUserResult != null && ourUserResult.getId()>0) {
                resp.setMessage("User Saved Successfully");
                resp.setStatusCode(200);
            }
        }catch (Exception e){
            resp.setStatusCode(500);
            resp.setError(e.getMessage());
        }
        return resp;
    }

    public LoginResponseDto signIn(UsuarioDto signinRequest){
    	LoginResponseDto response = new LoginResponseDto();

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signinRequest.getEmail(),signinRequest.getContrasenia()));
            var user = usuariosRepository.findByEmail(signinRequest.getEmail()).orElseThrow();
            System.out.println("USER IS: "+ user);
            var jwt = jwtUtils.generateToken(user);
            var refreshToken = jwtUtils.generateRefreshToken(new HashMap<>(), user);
            response.setStatusCode(200);
            response.setToken(jwt);
        }catch (Exception e){
            response.setStatusCode(500);
            response.setError(e.getMessage());
        }
        return response;
    }

//    public UsuarioDto refreshToken(UsuarioDto refreshTokenReqiest){
//        UsuarioDto response = new UsuarioDto();
//        String ourEmail = jwtUtils.extractUsername(refreshTokenReqiest.getToken());
//        Usuario users = usuariosRepository.findByEmail(ourEmail).orElseThrow();
//        if (jwtUtils.isTokenValid(refreshTokenReqiest.getToken(), users)) {
//            var jwt = jwtUtils.generateToken(users);
//            response.setStatusCode(200);
//            response.setToken(jwt);
//            response.setRefreshToken(refreshTokenReqiest.getToken());
//            response.setExpirationTime("24Hr");
//            response.setMessage("Successfully Refreshed Token");
//        }
//        response.setStatusCode(500);
//        return response;
//    }
}
