package com.wallaclone.finalproject.dto;

import java.util.List;
import java.util.Map;

import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class RequestNuevoAnuncioDto {
	@Valid
	@NotBlank(message = "El título no puede estar en blanco")
	private String titulo;
	@Valid
	@NotBlank(message = "Añade una descripción")
	private String descripcion;
	@Valid
	@DecimalMin(value = "0.0", message = "El precio debe ser mayor o igual a 0")
	private double precio;
	@NotNull(message = "Elige una de estas opciones")
	private boolean transacion;
	private Map <String, byte[]> imagenes;
	@NotEmpty(message = "Elige al menos un tag de la lista")
	private List<String> listCategoria;
	

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public double getPrecio() {
		return precio;
	}

	public void setPrecio(double precio) {
		this.precio = precio;
	}

	public boolean isTransacion() {
		return transacion;
	}

	public void setTransacion(boolean transacion) {
		this.transacion = transacion;
	}

	public Map<String, byte[]> getImagenes() {
		return imagenes;
	}

	public void setImagenes(Map<String, byte[]> imagenes) {
		this.imagenes = imagenes;
	}

	public List<String> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<String> listCategoria) {
		this.listCategoria = listCategoria;
	}

}
