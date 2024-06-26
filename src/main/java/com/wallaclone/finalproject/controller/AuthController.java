package com.wallaclone.finalproject.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wallaclone.finalproject.dto.RequestActualizarUsuarioDto;
import com.wallaclone.finalproject.dto.RequestBajaUsuarioDto;
import com.wallaclone.finalproject.dto.RequestLoginDto;
import com.wallaclone.finalproject.dto.RequestNuevaPasswordDto;
import com.wallaclone.finalproject.dto.RequestNuevoAnuncioDto;
import com.wallaclone.finalproject.dto.RequestSignupDto;
import com.wallaclone.finalproject.dto.ResponseDefaultImagenDto;
import com.wallaclone.finalproject.dto.ResponseErrorDto;
import com.wallaclone.finalproject.dto.ResponseMisChats;
import com.wallaclone.finalproject.dto.ResponseNuevoAnuncioDto;
import com.wallaclone.finalproject.dto.ResponseTokenDto;
import com.wallaclone.finalproject.dto.ResponseUsuarioDto;
import com.wallaclone.finalproject.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	AuthService authService;

	@PostMapping("/signup")
	public ResponseEntity<String> signUp(@RequestBody RequestSignupDto request) {
		authService.signUp(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PostMapping("/signin") // login
	public ResponseEntity<ResponseTokenDto> signIn(@RequestBody RequestLoginDto request) {
		return ResponseEntity.ok(authService.signIn(request));
	}

	@PostMapping("/nuevoanuncio")
	public ResponseEntity<ResponseNuevoAnuncioDto> nuevoAnuncio(@RequestBody RequestNuevoAnuncioDto request, HttpServletRequest httpRequest) {
		String refreshedToken = (String) httpRequest.getAttribute("refreshedToken");
		return new ResponseEntity<ResponseNuevoAnuncioDto>(authService.nuevoAnuncio(request, refreshedToken), HttpStatus.CREATED);
	}

	@GetMapping("/defaultimage")
	public ResponseEntity<ResponseDefaultImagenDto>	defaultimagedto() throws IOException {
		return new ResponseEntity<ResponseDefaultImagenDto>(authService.getDefaultImagen(), HttpStatus.OK);
	}

	@PostMapping("/nuevapassword")
	public ResponseEntity<String> nuevaPassword(@RequestBody RequestNuevaPasswordDto request){
		authService.nuevaPassword(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/borraranuncio/{id}")
	public ResponseEntity<ResponseTokenDto> borrarAnuncio(@PathVariable String id, HttpServletRequest httpRequest) {
		String refreshedToken = (String) httpRequest.getAttribute("refreshedToken");
		return new ResponseEntity<ResponseTokenDto>(authService.borrarAnuncio(id, refreshedToken), HttpStatus.OK);
	}

	@PutMapping("/actualizaranuncio/{id}")
	public ResponseEntity<ResponseNuevoAnuncioDto> actualizarAnuncio(@PathVariable String id,
			@RequestBody RequestNuevoAnuncioDto request, HttpServletRequest httpRequest) {
		String refreshedToken = (String) httpRequest.getAttribute("refreshedToken");

		return new ResponseEntity<ResponseNuevoAnuncioDto>(authService.actualizarAnuncio(id, request, refreshedToken),
				HttpStatus.CREATED);
	}

	@GetMapping("/usuarioPorApodo/{apodo}")
	public ResponseEntity<ResponseUsuarioDto> usuarioPorApodo(@PathVariable String apodo, HttpServletRequest httpRequest) throws IOException {
		String refreshedToken = (String) httpRequest.getAttribute("refreshedToken");
		return new ResponseEntity<ResponseUsuarioDto>(authService.obtenerUsuarioPorApodo(apodo, refreshedToken), HttpStatus.OK);
	}

	@PutMapping("/actualizarUsuario")
	public ResponseEntity<ResponseUsuarioDto> actualizarUsuario(@RequestBody RequestActualizarUsuarioDto request, HttpServletRequest httpRequest) {
		String refreshedToken = (String) httpRequest.getAttribute("refreshedToken");
		return new ResponseEntity<ResponseUsuarioDto>(authService.actualizarUsuario(request, refreshedToken), HttpStatus.OK);
	}

	@DeleteMapping("/bajaUsuario")
	public ResponseEntity<String> bajaUsuario(@RequestBody RequestBajaUsuarioDto request){
		authService.bajaUsuario(request);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/misChats")
	public ResponseEntity<ResponseMisChats>	misChats() throws IOException {
		return new ResponseEntity<ResponseMisChats>(authService.misChats(), HttpStatus.OK);
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
