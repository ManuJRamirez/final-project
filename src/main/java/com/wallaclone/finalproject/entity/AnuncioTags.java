package com.wallaclone.finalproject.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "anuncios_tags")
public class AnuncioTags {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)

	private Long id;
	private int idAnuncios;
	private int idCategoria;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIdAnuncios() {
		return idAnuncios;
	}

	public void setIdAnuncios(int idAnuncios) {
		this.idAnuncios = idAnuncios;
	}

	public int getIdCategoria() {
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria) {
		this.idCategoria = idCategoria;
	}

}
