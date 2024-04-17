package com.wallaclone.finalproject.dto;

import java.util.Date;

public class ChatDto {
	private Long id;
	private Long idAnuncio;
	private String tituloAnuncio;
	private String participante;
	private Date fechaUltimoMensaje;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}	
	public Long getIdAnuncio() {
		return idAnuncio;
	}
	public void setIdAnuncio(Long idAnuncio) {
		this.idAnuncio = idAnuncio;
	}
	public String getTituloAnuncio() {
		return tituloAnuncio;
	}
	public void setTituloAnuncio(String tituloAnuncio) {
		this.tituloAnuncio = tituloAnuncio;
	}
	public String getParticipante() {
		return participante;
	}
	public void setParticipante(String participante) {
		this.participante = participante;
	}
	public Date getFechaUltimoMensaje() {
		return fechaUltimoMensaje;
	}
	public void setFechaUltimoMensaje(Date fechaUltimoMensaje) {
		this.fechaUltimoMensaje = fechaUltimoMensaje;
	}

}
