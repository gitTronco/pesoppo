package com.troncodroide.pesoppo.beans;

import java.io.Serializable;

public class Clave implements Serializable{
	/*
	 * id nombre
	 */
	private long id;
	private String nombre;

	public Clave() {
	}

	public long getId() {
		return id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
