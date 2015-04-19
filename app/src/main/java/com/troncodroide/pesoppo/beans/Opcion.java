package com.troncodroide.pesoppo.beans;

public class Opcion {
	private String tittle;
	private String id;

    public Opcion(String tittle, String id) {
        this.tittle = tittle;
        this.id = id;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return tittle;
    }
}
