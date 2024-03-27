package com.wallaclone.finalproject.dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class ResponseAnuncioDto {
	private int id;
	private String titulo;
	private String descripcion;
	private double precio;
	private boolean transacion;
	private Date fechaCreacion;
	private boolean reservado;
	private boolean vendido;
	private String apodoCreador;
	private List<String> listCategoria;
	private Map<Long, byte[]> mapIdImagenes;
	private Map<Long, byte[]> mapIdImagenesOriginal;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

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

	public Date getFechaCreacion() {
		return fechaCreacion;
	}

	public void setFechaCreacion(Date fechaCreacion) {
		this.fechaCreacion = fechaCreacion;
	}

	public boolean isReservado() {
		return reservado;
	}

	public void setReservado(boolean reservado) {
		this.reservado = reservado;
	}

	public boolean isVendido() {
		return vendido;
	}

	public void setVendido(boolean vendido) {
		this.vendido = vendido;
	}

	public String getApodoCreador() {
		return apodoCreador;
	}

	public void setApodoCreador(String apodoCreador) {
		this.apodoCreador = apodoCreador;
	}

	public List<String> getListCategoria() {
		return listCategoria;
	}

	public void setListCategoria(List<String> listCategoria) {
		this.listCategoria = listCategoria;
	}

	public Map<Long, byte[]> getMapIdImagenes() {
		return mapIdImagenes;
	}

	public void setMapIdImagenes(Map<Long, byte[]> mapIdImagenes) {
		this.mapIdImagenes = mapIdImagenes;
	}

	public Map<Long, byte[]> getMapIdImagenesOriginal() {
		return mapIdImagenesOriginal;
	}

	public void setMapIdImagenesOriginal(Map<Long, byte[]> mapIdImagenesOriginal) {
		this.mapIdImagenesOriginal = mapIdImagenesOriginal;
	}
		
}
