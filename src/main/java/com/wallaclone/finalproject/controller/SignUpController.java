package com.wallaclone.finalproject.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.wallaclone.finalproject.dto.UsuarioDto;
import com.wallaclone.finalproject.service.UsuariosService;

@RestController
public class SignUpController {

	@Autowired
	UsuariosService usuariosService;

	@PostMapping("/signup")

	public ResponseEntity<String> signUp(@RequestBody UsuarioDto signUpBodyDto) {
		try {
			usuariosService.signUp(signUpBodyDto);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

}
