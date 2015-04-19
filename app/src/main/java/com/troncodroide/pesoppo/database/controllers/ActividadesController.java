package com.troncodroide.pesoppo.database.controllers;

import java.util.List;

import android.database.Cursor;

import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import com.troncodroide.pesoppo.database.sql.helpers.ActividadTableHelper;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;

public class ActividadesController {
	private SqlLiteManager manager;

	public ActividadesController(SqlLiteManager manager) {
		this.manager = manager;
	}

	public long addActividad(Actividad a)
			throws SqlExceptions.DuplicatedIdException,
			SqlExceptions.UniqueKeyException {
		Cursor cName = manager.select(ActividadTableHelper.getSelectByName(a
				.getNombre()));
		Cursor cId = manager.select(ActividadTableHelper.getSelectById(a
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

			long newId = manager.insert(ActividadTableHelper.tabla,
					ActividadTableHelper.getInserContentValues(a));
			a.setId(newId);
			cName.close();
			cId.close();
			manager.close();
			return newId;
		}
	}

	public boolean delActividad(Actividad a)
			throws SqlExceptions.IdNotFoundException {
		return manager.query(ActividadTableHelper.getDeleteString(a));
	}

	public boolean saveActividad(Actividad a)
			throws SqlExceptions.DuplicatedIdException,
			SqlExceptions.IdNotFoundException, SqlExceptions.UniqueKeyException {
		return manager.query(ActividadTableHelper.getUpdateString(a));
	}

	public Actividad getActividad(long id)
			throws SqlExceptions.DuplicatedIdException,
			SqlExceptions.IdNotFoundException {
		Cursor c = manager.select(ActividadTableHelper.getSelectById(id));
		c.moveToFirst();
		Actividad p = ActividadTableHelper.getFromCursor(c); 
		c.close();
		manager.close();
		return p;
	}

	public Actividad getActividad(String name)
			throws SqlExceptions.UniqueKeyException {
		Cursor c = manager.select(ActividadTableHelper.getSelectByName(name));
		c.moveToFirst();
		Actividad p = ActividadTableHelper.getFromCursor(c); 
		c.close();
		manager.close();
		return p;
	}

	public List<Actividad> getActividades() {
		Cursor c = manager.select(ActividadTableHelper.getSelectAllString());
		List<Actividad> proyectos = ActividadTableHelper.getListFromCursor(c);
		c.close();
		manager.close();
		return proyectos;
	}
}
