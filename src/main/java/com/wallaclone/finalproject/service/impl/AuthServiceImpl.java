package com.wallaclone.finalproject.service.impl;

import java.sql.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.RequestLoginDto;
import com.wallaclone.finalproject.dto.RequestNuevoAnuncioDto;
import com.wallaclone.finalproject.dto.RequestSignupDto;
import com.wallaclone.finalproject.dto.ResponseLoginDto;
import com.wallaclone.finalproject.entity.Anuncio;
import com.wallaclone.finalproject.entity.AnuncioTags;
import com.wallaclone.finalproject.entity.Imagen;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.repository.AnunciosRepository;
import com.wallaclone.finalproject.repository.AnunciosTagsRepository;
import com.wallaclone.finalproject.repository.CategoriasRepository;
import com.wallaclone.finalproject.repository.ImagenesRepository;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.service.AuthService;
import com.wallaclone.finalproject.utils.ApplicationConstants;
import com.wallaclone.finalproject.utils.JWTUtils;

import jakarta.transaction.Transactional;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	UsuariosRepository usuariosRepository;
	
	@Autowired
	AnunciosRepository anunciosRepository;
	
	@Autowired
	ImagenesRepository imagenesRepository;
	
	@Autowired
	AnunciosTagsRepository anunciosTagsRepository;
	
	@Autowired
	CategoriasRepository categoriasRepository;
	
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
				.authenticate(new UsernamePasswordAuthenticationToken(request.getApodo(), request.getContrasenia()));
		var user = usuariosRepository.findByApodo(request.getApodo()).orElseThrow();
		var jwt = jwtUtils.generateToken(user);
		response.setToken(jwt);

		return response;
	}

	@Override
	@Transactional
	public void nuevoAnuncio(RequestNuevoAnuncioDto request) {
		Anuncio anuncio = modelMapper.map(request, Anuncio.class);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String apodo = authentication.getName();
		anuncio.setIdUsuario(Integer.valueOf(usuariosRepository.findByApodo(apodo).get().getId().toString()));
		anuncio.setFechaCreacion(new Date(System.currentTimeMillis()));
		anunciosRepository.save(anuncio);
		
		if(request.getImagen() != null && !request.getImagen().isEmpty()) {
			request.getImagen().stream().forEach(img -> {
				Imagen imagen = new Imagen();
				imagen.setImagen(img);
				imagen.setIdAnuncio(Integer.valueOf(anuncio.getId().toString()));
				imagenesRepository.save(imagen);			
			});
		}
		
		request.getListCategoria().stream().forEach(tag -> {
			AnuncioTags anuncioTags = new AnuncioTags();
			anuncioTags.setIdAnuncio(anuncio.getId());
			anuncioTags.setIdCategoria(Long.valueOf(tag)); 
			anunciosTagsRepository.save(anuncioTags);			
		});
		
	}
}
