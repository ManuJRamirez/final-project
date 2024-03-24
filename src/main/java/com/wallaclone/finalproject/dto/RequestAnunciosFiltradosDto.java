package com.wallaclone.finalproject.dto;

import java.util.List;

import com.wallaclone.finalproject.utils.ApplicationConstants;

import jakarta.validation.constraints.Min;

public class RequestAnunciosFiltradosDto {

	private int pagina = 0;
	
	private List<String> categorias;
	
	private String titulo;
	
	private String orden = ApplicationConstants.ORDEN_RECIENTE;

    @Min(0)
	private int precioMin;

    @Min(0)
	private int precioMax;

	public int getPagina() {
		return pagina;
	}

	public void setPagina(int pagina) {
		this.pagina = pagina;
	}

	public List<String> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<String> categorias) {
		this.categorias = categorias;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getOrden() {
		return orden;
	}

	public void setOrden(String orden) {
		this.orden = orden;
	}

	public int getPrecioMin() {
		return precioMin;
	}

	public void setPrecioMin(int precioMin) {
		this.precioMin = precioMin;
	}

	public int getPrecioMax() {
		return precioMax;
	}

	public void setPrecioMax(int precioMax) {
		this.precioMax = precioMax;
	}
	
}
