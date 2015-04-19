package com.troncodroide.pesoppo.beans;

import java.io.Serializable;

public class Interrupcion implements Serializable{
	/*
	 * id
	 * nombre
	 * descripcion
	 * tiempo
	 * actividad
	 * */
	
	private long id;
	private String nombre;
	private String descripcion;
	private String tiempo;
	private long idActividad;
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
	public String getTiempo() {
		return tiempo;
	}
	public void setTiempo(String tiempo) {
		this.tiempo = tiempo;
	}
	public long getIdActividad() {
		return idActividad;
	}
	public void setIdActividad(long idActividad) {
		this.idActividad = idActividad;
	}
	
	@Override
	public String toString() {
		return Interrupcion.class.getSimpleName()+":"+id+"@"+nombre;
	}
}
