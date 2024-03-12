package com.wallaclone.finalproject.dto;

import java.util.Date;

public class ChatDto {
	private int idAnuncio;
	private int idUsuario;
	private String mensaje;
	private Date fechaUltimoMensaje;

	public int getIdAnuncio() {
		return idAnuncio;
	}

	public void setIdAnuncio(int idAnuncio) {
		this.idAnuncio = idAnuncio;
	}

	public int getIdUsuario() {
		return idUsuario;
	}

	public void setIdUsuario(int idUsuario) {
		this.idUsuario = idUsuario;
	}

	public String getMensaje() {
		return mensaje;
	}

	public void setMensaje(String mensaje) {
		this.mensaje = mensaje;
	}

	public Date getFechaUltimoMensaje() {
		return fechaUltimoMensaje;
	}

	public void setFechaUltimoMensaje(Date fechaUltimoMensaje) {
		this.fechaUltimoMensaje = fechaUltimoMensaje;
	}

}
