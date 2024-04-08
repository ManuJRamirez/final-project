package com.wallaclone.finalproject.dto;

import java.util.Date;

public class RequestActualizarUsuarioDto {

	private String apodo;
	private String nombre;
	private String apellidos;
	private String email;
	private Date fechaNacimiento;
	private boolean notificacion;
	
	public String getApodo() {
		return apodo;
	}

	public void setApodo(String apodo) {
		this.apodo = apodo;
	}

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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getFechaNacimiento() {
		return fechaNacimiento;
	}

	public void setFechaNacimiento(Date fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}

	public boolean isNotificacion() {
		return notificacion;
	}

	public void setNotificacion(boolean notificacion) {
		this.notificacion = notificacion;
	}

}
