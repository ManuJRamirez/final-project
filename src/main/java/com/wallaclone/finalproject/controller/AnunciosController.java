package com.wallaclone.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallaclone.finalproject.dto.AnuncioDto;
import com.wallaclone.finalproject.service.AnunciosService;

@RestController
public class AnunciosController {

	@Autowired
	AnunciosService anunciosService;

	@GetMapping("/anuncios")
	public List<AnuncioDto> getAnuncios() {
		return anunciosService.getAnuncios();
	}

}
