package com.troncodroide.pesoppo.beans;

import android.app.Activity;

import com.troncodroide.pesoppo.util.CalendarUtil;
import com.troncodroide.pesoppo.util.ValidateUtil;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

public class Proyecto implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -3608736129952896695L;
    /*
     * ID nombre descripcin fecha inicio...
     */
    private long id;
    private String nombre;
    private String descripcion;
    private String fechaInicio;
    private List<Actividad> actividades;

    public Proyecto() {
        id = -1;
        actividades = new LinkedList<>();
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

    public List<Actividad> getActividades() {
        return actividades;
    }

    public void addActividad(Actividad actividad) {
        actividades.add(actividad);
    }

    public void setActividades(List<Actividad> actividades) {
        this.actividades = actividades;
    }

    public List<Actividad> getFinishedActivities() {
        List<Actividad> toRet = new LinkedList<>();
        for (Actividad a : actividades) {
            if (a.isTerminado()) {
                toRet.add(a);
            }
        }
        return toRet;
    }

    public List<Actividad> getOverTimedActivities() {
        List<Actividad> toRet = new LinkedList<>();
        for (Actividad a : actividades) {
            if (ValidateUtil.getValidTime(a.getTiempoDedicacion()) > ValidateUtil.getValidTime(a.getTiempoEstimado())) {
                toRet.add(a);
            }
        }
        return toRet;
    }

    public Actividad getLastActivity() {
        if (actividades.size() > 0)
            return actividades.get(actividades.size() - 1);
        else return null;
    }

    @Override
    public String toString() {
        return id + ":" + nombre;
    }

}
