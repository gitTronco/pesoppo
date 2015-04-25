package com.troncodroide.pesoppo.database.controllers;

import java.util.List;

import android.database.Cursor;

import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.database.sql.*;
import com.troncodroide.pesoppo.database.sql.helpers.ProyectoTableHelper;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;

public class ProyectosController {
	private SqlLiteManager manager;

	public ProyectosController(SqlLiteManager manager) {
		this.manager = manager;
	}

	public long addProyecto(Proyecto p)
			throws SqlExceptions.DuplicatedIdException,
			SqlExceptions.UniqueKeyException {
		Cursor cName = manager.select(ProyectoTableHelper.getSelectByName(p
				.getNombre()));
		Cursor cId = manager.select(ProyectoTableHelper.getSelectById(p
				.getId()));
		
		if (cName.getCount() > 0) {
			cName.close();
			cId.close();
			manager.close();
			throw new SqlExceptions.UniqueKeyException();
		}else if (cId.getCount() > 0){
			cName.close();
			cId.close();
			manager.close();
			throw new SqlExceptions.DuplicatedIdException();
		}else{

			long newId = manager.insert(ProyectoTableHelper.tabla,
					ProyectoTableHelper.getInserContentValues(p));
			p.setId(newId);
			cName.close();
			cId.close();
			manager.close();
			return newId;
		}
	}

	public boolean delProyecto(Proyecto p)
			throws SqlExceptions.IdNotFoundException {
		return manager.query(ProyectoTableHelper.getDeleteString(p));
	}

	public boolean saveProyecto(Proyecto p)
			throws SqlExceptions.DuplicatedIdException,
			SqlExceptions.IdNotFoundException, SqlExceptions.UniqueKeyException {
		return manager.query(ProyectoTableHelper.getUpdateString(p));
	}

	public Proyecto getProyecto(long id)
			throws SqlExceptions.DuplicatedIdException,
			SqlExceptions.IdNotFoundException {
		Cursor c = manager.select(ProyectoTableHelper.getSelectById(id));
		c.moveToFirst();
		Proyecto p = ProyectoTableHelper.getFromCursor(c); 
		c.close();
		manager.close();
		return p;
	}

	public Proyecto getProyecto(String name)
			throws SqlExceptions.UniqueKeyException {
		Cursor c = manager.select(ProyectoTableHelper.getSelectByName(name));
		c.moveToFirst();

        Proyecto p = ProyectoTableHelper.getFromCursor(c);
        c.close();

        ActividadesController ac = new ActividadesController(manager);
        p.setActividades(ac.getActividades());

        manager.close();
		return p;
	}

	public List<Proyecto> getProyectos() {
		Cursor c = manager.select(ProyectoTableHelper.getSelectAllString());
		List<Proyecto> proyectos = ProyectoTableHelper.getListFromCursor(c);
		c.close();


		manager.close();
		return proyectos;
	}
	
	public List<Actividad> getActividades(Proyecto p){
		//Cursor c  = manager.select(sql);
		return null;
	}
}
