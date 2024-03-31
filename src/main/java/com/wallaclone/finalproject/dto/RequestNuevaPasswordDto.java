package com.wallaclone.finalproject.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public class RequestNuevaPasswordDto {

	@Valid
	@NotBlank(message = "La contraseña no puede estar en blanco")
	private String password;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
