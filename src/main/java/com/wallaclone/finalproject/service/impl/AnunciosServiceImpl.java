package com.wallaclone.finalproject.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.AnuncioDto;
import com.wallaclone.finalproject.entity.Usuario;
import com.wallaclone.finalproject.repository.AnunciosRepository;
import com.wallaclone.finalproject.repository.UsuariosRepository;
import com.wallaclone.finalproject.service.AnunciosService;

@Service
public class AnunciosServiceImpl implements AnunciosService {

	@Autowired
	AnunciosRepository anunciosRepository;
	@Autowired
	UsuariosRepository usuarioRepository;

	@Autowired
	ModelMapper modelMapper;

	@Override
	public List<AnuncioDto> getAnuncios() {
		return anunciosRepository.findAll().stream().map(anuncio -> {
			AnuncioDto res =  modelMapper.map(anuncio, AnuncioDto.class);
			Optional<Usuario> usuario = usuarioRepository.findById((long) anuncio.getIdUsuario());
			res.setEmailCreador(usuario.get().getEmail());
			return res;
		}).collect(Collectors.toList());
	}

}
