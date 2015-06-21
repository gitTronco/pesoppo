package com.troncodroide.pesoppo.beans;

import android.util.Log;

import java.io.Serializable;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import com.troncodroide.pesoppo.util.CalendarUtil;
import com.troncodroide.pesoppo.util.ValidateUtil;

public class Actividad implements Serializable{
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
	private List<Interrupcion> interrupciones;
	
	public Actividad() { this.fechaInicio = CalendarUtil.getDateString(Calendar.getInstance(), CalendarUtil.patternDate); }

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

	public void setTiempoDedicacion(String tiempoDedicacion) { this.tiempoDedicacion = tiempoDedicacion; }

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

	public List<Interrupcion> getInterrupciones() { return interrupciones; }

	public void setInterrupciones(List<Interrupcion> interrupciones) { this.interrupciones = interrupciones; }

    public void addInterrucion(Interrupcion i){
        if (interrupciones==null){
            interrupciones = new LinkedList<>();
        }
        interrupciones.add(i);
    }

	public int getInterruptionTime(){
		int time = 0;
		if (interrupciones!=null)
		for (Interrupcion i :interrupciones){
			time += ValidateUtil.getValidTime(i.getTiempo());
		}
		return time;
	}

	@Override
	public String toString() {
		return nombre;
	}
}
