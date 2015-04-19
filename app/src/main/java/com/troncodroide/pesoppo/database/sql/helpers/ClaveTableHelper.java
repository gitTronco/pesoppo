package com.troncodroide.pesoppo.database.sql.helpers;

import java.util.LinkedList;
import java.util.List;

import com.troncodroide.pesoppo.beans.Clave;

import android.content.ContentValues;
import android.database.Cursor;

public class ClaveTableHelper {
	public static String tabla = "Clave";
	public static String id = "id";
	public static String nombre = "nombre";
	
	public static String getCreateTableSQL() {
		String sql = "CREATE TABLE IF NOT EXISTS [" + tabla + "] (" 
				+ "[" + id	+ "] INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "[" + nombre + "] TEXT NOT NULL);";
		return sql;
	}
	
	public static String getSelectAllString() {
			String sql = "SELECT * FROM " + tabla + ";";
		return sql;
	}	
	
	public static String getSelectById(long id) {
		String sql = "SELECT * FROM " + tabla + " WHERE "
				+ ClaveTableHelper.id + " = " + id;
		return sql;
	}
	
	public static String getSelectByName(String name) {
		String sql = "SELECT * FROM " + tabla + " WHERE "
				+ ClaveTableHelper.nombre + " = " + name;
		return sql;
	}

	public static Clave getFromCursor(Cursor c) {
		Clave clave = null;
		if (c != null && c.getCount() > 0) {
			int idInt = c.getInt(c.getColumnIndex(id));
			String nombreString = c.getString(c.getColumnIndex(nombre));
			
			clave = new Clave();
			clave.setId(idInt);
			clave.setNombre(nombreString);
		}
		return clave;
	}
	
	public static List<Clave> getListFromCursor(Cursor c) {
		List<Clave> toRet = new LinkedList<Clave>();
		if (c != null && c.moveToFirst()) {
			do {
				Clave esp = ClaveTableHelper.getFromCursor(c);
				toRet.add(esp);
			} while (c.moveToNext());
		}

		return toRet;
	}
	

	public static String getUpdateString(Clave a) {
		String sql = "UPDATE " + tabla + " SET " 
				+ nombre + " = '" + a.getNombre() + "'" 
				+ " WHERE " + id + " = " + a.getId() + ";";
		return sql;
	}
	
	public static String getDeleteString(Clave e) {
		String sql = " DELETE FROM " + tabla + " WHERE " + id + " = "
				+ e.getId();
		return sql;
	}

	public static ContentValues getInserContentValues(Clave a) {
		ContentValues cv = new ContentValues();
		cv.put(nombre, a.getNombre());
		return cv;
	}

	
	
	
	
}