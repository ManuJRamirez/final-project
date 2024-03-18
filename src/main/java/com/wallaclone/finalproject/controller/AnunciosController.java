package com.wallaclone.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wallaclone.finalproject.dto.ResponseAnuncioDto;
import com.wallaclone.finalproject.service.AnunciosService;

@RestController
@RequestMapping("/public")
public class AnunciosController {

	@Autowired
	AnunciosService anunciosService;

	@GetMapping("/anuncios")
	public ResponseEntity<List<ResponseAnuncioDto>> getAnuncios() {
		return ResponseEntity.ok(anunciosService.getAnuncios());
	}

	@GetMapping("/anuncio/{id}")
	public ResponseEntity<ResponseAnuncioDto> getAnuncio(@PathVariable String id) {
		return ResponseEntity.ok(anunciosService.getAnuncio(id));
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
