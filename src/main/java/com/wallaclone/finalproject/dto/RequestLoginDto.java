package com.wallaclone.finalproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public class RequestLoginDto {

	@Valid
	@NotBlank(message = "El apodo no puede estar en blanco")
	private String apodo;

	@Valid
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,!-_?])(?=\\S+$).{6,}$", message = "Contraseña inválida")
	private String contrasenia;

	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

}
