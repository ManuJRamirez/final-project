package com.wallaclone.finalproject.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.SignUpResponseDto;
import com.wallaclone.finalproject.dto.UsuarioDto;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.service.UsuariosService;

@Service
public class UsuariosServiceImpl implements UsuariosService {

	@Autowired
	UsuariosRepository usuariosRepository;

	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Override
	public SignUpResponseDto signUp(UsuarioDto signUpBodyDto) throws Exception {

		signUpBodyDto.setContrasenia(passwordEncoder.encode(signUpBodyDto.getContrasenia()));
		usuariosRepository.save(modelMapper.map(signUpBodyDto, Usuario.class));
		return null;
	}

}
