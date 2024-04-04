package com.wallaclone.finalproject.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wallaclone.finalproject.dto.RequestAnunciosFiltradosDto;
import com.wallaclone.finalproject.dto.RequestEnviarEmailDto;
import com.wallaclone.finalproject.dto.ResponseAnuncioDto;
import com.wallaclone.finalproject.dto.ResponseCategoriaDto;
import com.wallaclone.finalproject.dto.ResponseErrorDto;
import com.wallaclone.finalproject.exceptions.CustomException;
import com.wallaclone.finalproject.service.AnunciosService;
import com.wallaclone.finalproject.service.EnviarEmailService;

@RestController
@RequestMapping("/public")
public class PublicController {

	@Autowired
	AnunciosService anunciosService;

	@Autowired
	EnviarEmailService enviarEmailService;

	@GetMapping("/anuncio/{id}")
	public ResponseEntity<ResponseAnuncioDto> getAnuncio(@PathVariable String id) {
		return ResponseEntity.ok(anunciosService.getAnuncio(id));
	}

	@GetMapping("/categorias")
	public ResponseEntity<List<ResponseCategoriaDto>> getCategorias() {
		return ResponseEntity.ok(anunciosService.getCategorias());
	}

	@PostMapping("/anunciosFiltrados")
	public ResponseEntity<Page<ResponseAnuncioDto>> getAnunciosFiltrados(
			@RequestBody RequestAnunciosFiltradosDto request) {
		return ResponseEntity.ok(anunciosService.getAnunciosFiltrados(request));
	}

	@PostMapping("/enviarEmailRecuperacion")
	public ResponseEntity<?> enviarEmailRecuperacion(@RequestBody RequestEnviarEmailDto request) {
		enviarEmailService.enviarEmailRecuperacion(request);
		return ResponseEntity.ok("Correo electrónico enviado correctamente.");
	}

	@ExceptionHandler(value = CustomException.class)
	public ResponseEntity<Object> handleUsuarioNoEncontradoException(CustomException exception) {
		ResponseErrorDto errorResponse = new ResponseErrorDto("Usuario no encontrado", exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	public ResponseEntity<ResponseErrorDto> handleValidationsException(MethodArgumentNotValidException exception) {
		List<String> errors = new ArrayList<>();
		exception.getBindingResult().getAllErrors().forEach(error -> {
			String fieldError = ((FieldError) error).getField() + " " + error.getDefaultMessage();
			errors.add(fieldError);
		});
		ResponseErrorDto errorResponse = new ResponseErrorDto("Error de validación", errors.toString());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = ResponseStatusException.class)
	public ResponseEntity<ResponseErrorDto> handleResponseStatusException(ResponseStatusException exception) {
		ResponseErrorDto errorResponse = new ResponseErrorDto("Error de estado", exception.getReason());
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseErrorDto> handleException(Exception exception) {
		ResponseErrorDto errorResponse = new ResponseErrorDto("Error interno del servidor", exception.getMessage());
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
