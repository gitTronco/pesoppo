package com.troncodroide.pesoppo.database.sql.helpers;

import java.util.LinkedList;
import java.util.List;

import com.troncodroide.pesoppo.beans.Proyecto;

import android.content.ContentValues;
import android.database.Cursor;

public class ProyectoTableHelper {
	public static String tabla = "Proyectos";
	public static String id = "id";
	public static String fecha = "fecha";
	public static String nombre = "nombre";
	public static String descripcion = "descripcion";
	
	public static String getCreateTableSQL() {
		String sql = "CREATE TABLE IF NOT EXISTS [" + tabla + "] (" 
				+ "[" + id	+ "] INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "[" + fecha + "] TEXT NULL,"
				+ "[" + nombre + "] TEXT NOT NULL," 
				+ "[" + descripcion + "] TEXT NOT NULL);";
		return sql;
	}
	
	public static String getSelectAllString() {
			String sql = "SELECT * FROM " + tabla + ";";
		return sql;
	}	
	
	public static String getSelectById(long id) {
		String sql = "SELECT * FROM " + tabla + " WHERE "
				+ ProyectoTableHelper.id + " = '" + id +"'";
		return sql;
	}
	public static String getSelectByName(String name) {
		String sql = "SELECT * FROM " + tabla + " WHERE "
				+ ProyectoTableHelper.nombre + " LIKE '" + name+ "'";
		return sql;
	}

	public static Proyecto getFromCursor(Cursor c) {
		Proyecto pro = null;
		if (c != null && c.getCount() > 0) {
			long idLong = c.getLong(c.getColumnIndex(id));
			String nombreString = c.getString(c.getColumnIndex(nombre));
			String descripcionString = c.getString(c.getColumnIndex(descripcion));
			String fechaInicioString = c.getString(c.getColumnIndex(fecha));
			
			pro = new Proyecto();
			pro.setFechaInicio(fechaInicioString);
			pro.setId(idLong);
			pro.setNombre(nombreString);
			pro.setDescripcion(descripcionString);
		}
		return pro;
	}
	
	public static List<Proyecto> getListFromCursor(Cursor c) {
		List<Proyecto> toRet = new LinkedList<Proyecto>();
		if (c != null && c.moveToFirst()) {
			do {
				Proyecto esp = ProyectoTableHelper.getFromCursor(c);
				toRet.add(esp);
			} while (c.moveToNext());
		}

		return toRet;
	}
	

	public static String getUpdateString(Proyecto p) {
		String sql = "UPDATE " + tabla + " SET "  
				+ fecha + " = '" + p.getFechaInicio() + "'," 
				+ descripcion + " = '" + p.getDescripcion() + "'," 
				+ nombre + " = '" + p.getNombre() + "'" 
				+ " WHERE " + id + " = " + p.getId() + ";";
		return sql;
	}
	
	public static String getDeleteString(Proyecto p) {
		String sql = " DELETE FROM " + tabla + " WHERE " + id + " = "
				+ p.getId();
		return sql;
	}

	public static ContentValues getInserContentValues(Proyecto p) {
		ContentValues cv = new ContentValues();
		cv.put(fecha, p.getFechaInicio());
		cv.put(descripcion, p.getDescripcion());
		cv.put(nombre, p.getNombre());
		return cv;
	}

	
	
	
	
}