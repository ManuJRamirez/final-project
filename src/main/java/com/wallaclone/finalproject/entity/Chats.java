package com.wallaclone.finalproject.entity;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "chats")
public class Chats {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private int idAnuncio;
	private int idUsuario;
	private String mensaje;
	private Date fechaUltimoMensaje;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
