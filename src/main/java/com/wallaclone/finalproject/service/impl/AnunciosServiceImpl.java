package com.wallaclone.finalproject.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallaclone.finalproject.dto.ResponseAnuncioDto;
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
	public List<ResponseAnuncioDto> getAnuncios() {
		return anunciosRepository.findAll().stream().map(anuncio -> {
			ResponseAnuncioDto res = modelMapper.map(anuncio, ResponseAnuncioDto.class);
			Optional<Usuario> usuario = usuarioRepository.findById((long) anuncio.getIdUsuario());
			res.setEmailCreador(usuario.get().getEmail());
			return res;
		}).collect(Collectors.toList());
	}

	@Override
	public ResponseAnuncioDto getAnuncio(String id) {
		return modelMapper.map(anunciosRepository.findById(Long.valueOf(id)).get(), ResponseAnuncioDto.class);
	}

}
