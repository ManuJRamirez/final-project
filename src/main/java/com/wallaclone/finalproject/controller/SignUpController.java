package com.wallaclone.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wallaclone.finalproject.dto.UsuarioDto;
import com.wallaclone.finalproject.service.UsuariosService;

import jakarta.validation.Valid;

@RestController
public class SignUpController {

	@Autowired
	UsuariosService usuariosService;

	@PostMapping("/signup")

	public ResponseEntity<String> signUp(@Valid @RequestBody UsuarioDto signUpBodyDto) throws Exception {
		usuariosService.signUp(signUpBodyDto);

		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<List<String>> handleValidationsException(MethodArgumentNotValidException exception) {
		List<String> errors = new ArrayList<>();
		exception.getBindingResult().getAllErrors().forEach(error -> {
			StringBuilder descripcionError = new StringBuilder();
			descripcionError.append(((org.springframework.validation.FieldError) error).getField());
			descripcionError.append(" ").append(error.getDefaultMessage());
			errors.add(descripcionError.toString());
		});
		return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ResponseStatusException.class)
	public ResponseEntity<String> handleException(ResponseStatusException exception) {

		return new ResponseEntity<String>(exception.getReason(), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<String> handleException(Exception exception) {

		return new ResponseEntity<String>(exception.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
