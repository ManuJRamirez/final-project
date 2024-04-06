package com.wallaclone.finalproject.dto;

public class ResponseUsuarioDto {

	private String nombre;
	private String apellidos;
	private String email;
	private String apodo;	
	private String fechaNacimiento;	
	private boolean notificacion;
	private String token;
	
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
	public String getApodo() {
		return apodo;
	}
	public void setApodo(String apodo) {
		this.apodo = apodo;
	}
	public String getFechaNacimiento() {
		return fechaNacimiento;
	}
	public void setFechaNacimiento(String fechaNacimiento) {
		this.fechaNacimiento = fechaNacimiento;
	}
	public boolean isNotificacion() {
		return notificacion;
	}
	public void setNotificacion(boolean notificacion) {
		this.notificacion = notificacion;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	
	
}
