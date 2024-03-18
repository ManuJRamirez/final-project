package com.wallaclone.finalproject.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.RequestLoginDto;
import com.wallaclone.finalproject.dto.RequestSignupDto;
import com.wallaclone.finalproject.dto.ResponseLoginDto;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.service.AuthService;
import com.wallaclone.finalproject.utils.ApplicationConstants;
import com.wallaclone.finalproject.utils.JWTUtils;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	UsuariosRepository usuariosRepository;

	@Autowired
	JWTUtils jwtUtils;

	@Autowired
	PasswordEncoder passwordEncoder;

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public void signUp(RequestSignupDto request) {
		Usuario usuario = modelMapper.map(request, Usuario.class);
		usuario.setContrasenia(passwordEncoder.encode(request.getContrasenia()));
		usuario.setRole(ApplicationConstants.USER_ROLE);
		usuariosRepository.save(usuario);
	}

	@Override
	public ResponseLoginDto signIn(RequestLoginDto request) {
		ResponseLoginDto response = new ResponseLoginDto();

		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getContrasenia()));
		var user = usuariosRepository.findByEmail(request.getEmail()).orElseThrow();
		var jwt = jwtUtils.generateToken(user);
		response.setToken(jwt);

		return response;
	}
}
