package com.wallaclone.finalproject.service.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.Date;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wallaclone.finalproject.dto.RequestLoginDto;
import com.wallaclone.finalproject.dto.RequestNuevaPasswordDto;
import com.wallaclone.finalproject.dto.RequestNuevoAnuncioDto;
import com.wallaclone.finalproject.dto.RequestSignupDto;
import com.wallaclone.finalproject.dto.ResponseDefaultImagenDto;
import com.wallaclone.finalproject.dto.ResponseLoginDto;
import com.wallaclone.finalproject.dto.ResponseNuevoAnuncioDto;
import com.wallaclone.finalproject.entity.Anuncio;
import com.wallaclone.finalproject.entity.AnuncioTags;
import com.wallaclone.finalproject.entity.Imagen;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.exceptions.CustomException;
import com.wallaclone.finalproject.repository.AnunciosRepository;
import com.wallaclone.finalproject.repository.AnunciosTagsRepository;
import com.wallaclone.finalproject.repository.CategoriasRepository;
import com.wallaclone.finalproject.repository.ImagenesRepository;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.service.AuthService;
import com.wallaclone.finalproject.utils.ApplicationConstants;
import com.wallaclone.finalproject.utils.JWTUtils;

import jakarta.persistence.EntityExistsException;

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

		var user = usuariosRepository.findByApodo(request.getApodo()).orElseThrow(() -> new CustomException(
				"El usuario " + request.getApodo() + " no se encuentra en la base de datos."));
		
		try {
			authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(request.getApodo(), request.getContrasenia()));
		} catch (AuthenticationException e) {
			throw new CustomException("La contraseña no es correcta.");
		}
		
		var jwt = jwtUtils.generateToken(user);
		response.setToken(jwt);

		return response;
	}

	@Override
	@Transactional
	public ResponseNuevoAnuncioDto nuevoAnuncio(RequestNuevoAnuncioDto request) {
		Anuncio anuncio = modelMapper.map(request, Anuncio.class);
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String apodo = authentication.getName();
		anuncio.setUsuario(usuariosRepository.findByApodo(apodo).get());
		anuncio.setFechaCreacion(new Date(System.currentTimeMillis()));
		Anuncio anuncioGuardado = anunciosRepository.save(anuncio);

		if (request.getImagen() != null && !request.getImagen().isEmpty()) {
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

		ResponseNuevoAnuncioDto response = new ResponseNuevoAnuncioDto();
		Long id = anuncioGuardado.getId();
		String titulo = anuncioGuardado.getTitulo();
		response.setId(id);
		response.setTitulo(titulo);
		return response;
	}

	@Override
	public ResponseDefaultImagenDto getDefaultImagen() throws IOException {
		File file = new File(ApplicationConstants.DEFAULT_IMG);
        if (!file.exists()) {
            throw new IOException("La imagen por defecto no existe");
        }
        ResponseDefaultImagenDto response = new ResponseDefaultImagenDto();
        byte[] data = Files.readAllBytes(file.toPath());
        response.setImagen(data);
        return response;
    }

	@Override
	@Transactional
	public void nuevaPassword(RequestNuevaPasswordDto request) {
		
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String apodo = authentication.getName();
		
		Usuario usuario = usuariosRepository.getOneByApodo(apodo)
				.orElseThrow(() -> new CustomException("El usuario "
						+ apodo + " no se encuentra en la base de datos."));
		
		if(usuario != null) {
			String nuevaPassword = passwordEncoder.encode(request.getPassword());
			if(!nuevaPassword.equals(usuario.getContrasenia())) {
				usuario.setContrasenia(nuevaPassword);	
				usuariosRepository.save(usuario);
			} else {
				throw new CustomException("La nueva contraseña no puede ser igual a la anterior.");
			}
		}
	}

	@Override
	@Transactional
	public void borrarAnuncio(String id) {
		Long idAnuncio = Long.valueOf(id);
		if (anunciosRepository.existsById(idAnuncio)) {
			anunciosRepository.customDelete(idAnuncio);
		} else {
			throw new EntityExistsException("El anuncio con el ID " + idAnuncio + "no existe");
		}

	}
}
