package com.wallaclone.finalproject.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.wallaclone.finalproject.dto.RequestAnunciosFiltradosDto;
import com.wallaclone.finalproject.dto.ResponseAnuncioDto;
import com.wallaclone.finalproject.dto.ResponseCategoriaDto;

public interface AnunciosService {

	public ResponseAnuncioDto getAnuncio(String id);

	public List<ResponseCategoriaDto> getCategorias();
	
	public Page<ResponseAnuncioDto> getAnunciosFiltrados(RequestAnunciosFiltradosDto request);

}
