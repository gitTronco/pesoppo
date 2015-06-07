package com.troncodroide.pesoppo.database.sql.helpers;

import java.util.LinkedList;
import java.util.List;

import com.troncodroide.pesoppo.beans.Interrupcion;

import android.content.ContentValues;
import android.database.Cursor;

public class InterrupcionTableHelper {
	public static String tabla = "Interrupcion";
	
	public static String id = "id";
	public static String idActividad= "id_actividad";
	public static String nombre = "nombre";
	public static String descripcion = "descripcion";
	public static String tiempoDedicado = "t_dedicado";
	
	public static String getCreateTableSQL() {
		String sql = "CREATE TABLE IF NOT EXISTS [" + tabla + "] (" 
				+ "[" + id	+ "] INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "[" + idActividad + "] INTEGER NOT NULL," 
				+ "[" + nombre + "] TEXT NOT NULL," 
				+ "[" + descripcion + "] TEXT NOT NULL," 
				+ "[" + tiempoDedicado+ "] TEXT NOT NULL);";
		return sql;
	}
	
	public static String getSelectAllString() {
			String sql = "SELECT * FROM " + tabla + ";";
		return sql;
	}

	public static String getSelectAllByActivity(long idActivity) {
			String sql = "SELECT * FROM " + tabla + " WHERE "
					+ InterrupcionTableHelper.idActividad + " = '" + idActivity+"'";
		return sql;
	}
	
	public static String getSelectById(long id) {
		String sql = "SELECT * FROM " + tabla + " WHERE "
				+ InterrupcionTableHelper.id + " = '" + id+"'";
		return sql;
	}

	public static Interrupcion getFromCursor(Cursor c) {
		Interrupcion obj = null;
		if (c != null && c.getCount() > 0) {
			int idInt = c.getInt(c.getColumnIndex(id));
			int idActividadInt = c.getInt(c.getColumnIndex(idActividad));
			String nombreString = c.getString(c.getColumnIndex(nombre));
			String descripcionString = c.getString(c.getColumnIndex(descripcion));
			String tDedicadoString = c.getString(c.getColumnIndex(tiempoDedicado));
			
			obj = new Interrupcion();
			obj.setDescripcion(descripcionString);
			obj.setId(idInt);
			obj.setIdActividad(idActividadInt);
			obj.setNombre(nombreString);
			obj.setTiempo(tDedicadoString);
		}
		return obj;
	}
	
	public static List<Interrupcion> getListFromCursor(Cursor c) {
		List<Interrupcion> toRet = new LinkedList<Interrupcion>();
		if (c != null && c.moveToFirst()) {
			do {
				Interrupcion obj = InterrupcionTableHelper.getFromCursor(c);
				toRet.add(obj);
			} while (c.moveToNext());
		}

		return toRet;
	}
	

	public static String getUpdateString(Interrupcion obj) {
		String sql = "UPDATE " + tabla + " SET " 
				+ idActividad + " = '" + obj.getIdActividad()+ "'," 
				+ descripcion + " = '" + obj.getDescripcion() + "'," 
				+ nombre + " = '" + obj.getNombre() + "'," 
				+ tiempoDedicado+ " = '" + obj.getTiempo() + "'"
				+ " WHERE " + id + " = '" + obj.getId() + "';";
		return sql;
	}
	
	public static String getDeleteString(Interrupcion e) {
		String sql = " DELETE FROM " + tabla + " WHERE " + id + " = "
				+ e.getId();
		return sql;
	}

	public static ContentValues getInserContentValues(Interrupcion a) {
		ContentValues cv = new ContentValues();
		cv.put(idActividad, a.getIdActividad());
		cv.put(descripcion, a.getDescripcion());
		cv.put(nombre, a.getNombre());
		cv.put(tiempoDedicado, a.getTiempo());
		return cv;
	}

	
	
	
	
}