package com.wallaclone.finalproject.dto;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;

public class UsuarioDto {
	@Valid
	@NotBlank(message = "El nombre no puede estar en blanco")
	private String nombre;
	@Valid
	@NotBlank(message = "Los apellidos no pueden estar en blanco")
	private String apellidos;
	@Valid
	@NotNull(message = "La fecha de nacimiento no puede estar en blanco")
	@Past(message = "La fecha de nacimiento debe estar en el pasado")
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	private Date fechaNacimiento;
	@Valid
	@NotBlank(message = "El email no puede estar en blanco")
	@Email(message = "El email debe ser válido")
	@Pattern(regexp = "^((?!\\.)[\\w\\-_.]*[^.])(@\\w+)(\\.\\w+(\\.\\w+)?[^.\\W])$", message = "Email incorrecto")
	private String email;

	@Valid
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=.,!-_?])(?=\\S+$).{6,}$", message = "Contraseña inválida")
	private String contrasenia;
	private boolean notificacion;
	private byte[] imgPerfil;

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public void setApellidos(String apellidos) {
		this.apellidos = apellidos;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getContrasenia() {
		return contrasenia;
	}

	public void setContrasenia(String contrasenia) {
		this.contrasenia = contrasenia;
	}

	public boolean isNotificacion() {
		return notificacion;
	}

	public void setNotificacion(boolean notificacion) {
		this.notificacion = notificacion;
	}

	public byte[] getImgPerfil() {
		return imgPerfil;
	}

	public void setImgPerfil(byte[] imgPerfil) {
		this.imgPerfil = imgPerfil;
	}

}
