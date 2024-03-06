package com.wallaclone.finalproject.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wallaclone.finalproject.dto.PruebaDto;
import com.wallaclone.finalproject.service.SaludoService;

@RestController
public class HelloWorldController {

	@Autowired
	SaludoService saludoService;

	@GetMapping("/saludo")
	public List<PruebaDto> getSaludo() {

		return saludoService.getSaludo();
	}
}
