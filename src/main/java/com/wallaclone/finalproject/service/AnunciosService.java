package com.wallaclone.finalproject.service;

import java.util.List;

import com.wallaclone.finalproject.dto.ResponseAnuncioDto;

public interface AnunciosService {

	public List<ResponseAnuncioDto> getAnuncios();

	public ResponseAnuncioDto getAnuncio(String id);

}
