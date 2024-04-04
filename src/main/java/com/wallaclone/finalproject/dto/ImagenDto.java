package com.wallaclone.finalproject.dto;

public class ImagenDto {
	private Long id;
	private String nombre;
	private byte[] imagen;
	private byte[] imagenResize;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public byte[] getImagen() {
		return imagen;
	}

	public void setImagen(byte[] imagen) {
		this.imagen = imagen;
	}

	public byte[] getImagenResize() {
		return imagenResize;
	}

	public void setImagenResize(byte[] imagenResize) {
		this.imagenResize = imagenResize;
	}

}
