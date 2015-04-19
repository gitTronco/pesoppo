package com.troncodroide.pesoppo.beans;

import java.util.Calendar;

import com.troncodroide.pesoppo.util.CalendarUtil;

public class Actividad {
	/*
	 * id
	 * proyecto
	 * nombre
	 * clave
	 * descipcion
	 * fecha_inicio
	 * Tdedicacion
	 * Testimado
	 * unidades
	 * terminado
	 * */

	private long id;
	private long idProyecto;
	private long idClave;
	private String nombre;
	private String descripcion;
	private String fechaInicio;
	private String tiempoDedicacion;
	private String tiempoEstimado;
	private int unidades;
	private boolean terminado;
	
	public Actividad() {
		this.fechaInicio = CalendarUtil.getDateString(Calendar.getInstance(), CalendarUtil.patternDate);
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getIdProyecto() {
		return idProyecto;
	}

	public void setIdProyecto(long idProyecto) {
		this.idProyecto = idProyecto;
	}

	public long getIdClave() {
		return idClave;
	}

	public void setIdClave(long idClave) {
		this.idClave = idClave;
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

	public String getTiempoDedicacion() {
		return tiempoDedicacion;
	}

	public void setTiempoDedicacion(String tiempoDedicacion) {
		this.tiempoDedicacion = tiempoDedicacion;
	}

	public String getTiempoEstimado() {
		return tiempoEstimado;
	}

	public void setTiempoEstimado(String tiempoEstimado) {
		this.tiempoEstimado = tiempoEstimado;
	}

	public int getUnidades() {
		return unidades;
	}

	public void setUnidades(int unidades) {
		this.unidades = unidades;
	}

	public boolean isTerminado() {
		return terminado;
	}

	public void setTerminado(boolean terminado) {
		this.terminado = terminado;
	}
	
	@Override
	public String toString() {
		return "ActividadObservable: "+this.id+" - "+this.getNombre();
	}
}
