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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.wallaclone.finalproject.dto.RequestLoginDto;
import com.wallaclone.finalproject.dto.RequestSignupDto;
import com.wallaclone.finalproject.dto.ResponseLoginDto;
import com.wallaclone.finalproject.service.AuthService;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RequestSignupDto request){
    	authService.signUp(request);
		return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PostMapping("/signin")
    public ResponseEntity<ResponseLoginDto> signIn(@RequestBody RequestLoginDto request){
        return ResponseEntity.ok(authService.signIn(request));
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
