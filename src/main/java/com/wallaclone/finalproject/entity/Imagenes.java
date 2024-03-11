package com.wallaclone.finalproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "imagenes")
public class Imagenes {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private int idAnuncio;
	private byte[] imagen;

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

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}
}
