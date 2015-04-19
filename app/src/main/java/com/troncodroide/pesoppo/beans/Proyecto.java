package com.troncodroide.pesoppo.beans;

import java.io.Serializable;

public class Proyecto implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3608736129952896695L;
	/*
	 * ID nombre descripcin fecha inicio
	 */
	private long id;
	private String nombre;
	private String descripcion;
	private String fechaInicio;

	public Proyecto() {
		id = -1;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}

	@Override
	public String toString() {
		return id + ":" + nombre;
	}

}
