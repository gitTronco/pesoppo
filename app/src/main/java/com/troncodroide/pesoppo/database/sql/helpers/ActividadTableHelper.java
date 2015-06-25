package com.troncodroide.pesoppo.database.sql.helpers;

import java.util.LinkedList;
import java.util.List;

import com.troncodroide.pesoppo.beans.Actividad;

import android.content.ContentValues;
import android.database.Cursor;

public class ActividadTableHelper {
	public static String tabla = "Actividad";
	public static String id = "id";
	public static String idProyecto= "id_proyecto";
	public static String idClave = "id_clave";
	public static String fecha = "fecha";
	public static String nombre = "nombre";
	public static String descripcion = "descripcion";
	public static String tiempoDedicacion = "t_dedicacion";
	public static String tiempoEstimado = "t_estimado";
	public static String unidades = "unidades";
	public static String terminado = "terminado";
	
	public static String getCreateTableSQL() {
		String sql = "CREATE TABLE IF NOT EXISTS [" + tabla + "] (" 
				+ "[" + id	+ "] INTEGER PRIMARY KEY AUTOINCREMENT," 
				+ "[" + idProyecto + "] INTEGER NOT NULL," 
				+ "[" + fecha + "] TEXT NULL,"
				+ "[" + idClave + "] INTEGER NOT NULL,"
				+ "[" + nombre + "] TEXT NOT NULL," 
				+ "[" + descripcion + "] TEXT NOT NULL," 
				+ "[" + tiempoDedicacion + "] TEXT NOT NULL,"
				+ "[" + tiempoEstimado + "] TEXT NOT NULL,"
				+ "[" + unidades + "] INTEGER NULL,"
				+ "[" + terminado + "] INTEGER NOT NULL);";
		return sql;
	}
	
	public static String getSelectAllString() {
			String sql = "SELECT * FROM " + tabla + ";";
		return sql;
	}
    public static String getSelectAllByProject(long idProject) {
			String sql = "SELECT * FROM " + tabla + " WHERE "+ ActividadTableHelper.idProyecto + " = '" +idProject+ "'" ;
		return sql;
	}

    public static String getSelectAllByClave(long idClave) {
			String sql = "SELECT * FROM " + tabla + " WHERE "+ ActividadTableHelper.idClave+ " = '" +idClave+ "'";
		return sql;
	}

	public static String getSelectById(long id) {
		String sql = "SELECT * FROM " + tabla + " WHERE "
				+ ActividadTableHelper.id + " = '" + id + "'";
		return sql;
	}
	public static String getSelectByName(String name) {
		String sql = "SELECT * FROM " + tabla + " WHERE "
				+ ActividadTableHelper.nombre + " = '" + name + "'";
		return sql;
	}
	public static Actividad getFromCursor(Cursor c) {
		Actividad act = null;
		if (c != null && c.getCount() > 0) {
			int idInt = c.getInt(c.getColumnIndex(id));
			int idProyectoInt = c.getInt(c.getColumnIndex(idProyecto));
			int idClaveInt = c.getInt(c.getColumnIndex(idClave));
			String nombreString = c.getString(c.getColumnIndex(nombre));
			String descripcionString = c.getString(c.getColumnIndex(descripcion));
			String fechaInicioString = c.getString(c.getColumnIndex(fecha));
			String tDedicacionString = c.getString(c.getColumnIndex(tiempoDedicacion));
			String tEstimadoString = c.getString(c.getColumnIndex(tiempoEstimado));
			int unidadesInt= c.getInt(c.getColumnIndex(unidades));
			int terminadoInt = c.getInt(c.getColumnIndex(terminado));
			
			act = new Actividad();
			act.setDescripcion(descripcionString);
			act.setFechaInicio(fechaInicioString);
			act.setId(idInt);
			act.setIdClave(idClaveInt);
			act.setIdProyecto(idProyectoInt);
			act.setNombre(nombreString);
			act.setTerminado((terminadoInt==1)?true:false);
			act.setTiempoDedicacion(tDedicacionString);
			act.setTiempoEstimado(tEstimadoString);
			act.setUnidades(unidadesInt);
		}
		return act;
	}
	
	public static List<Actividad> getListFromCursor(Cursor c) {
		List<Actividad> toRet = new LinkedList<Actividad>();
		if (c != null && c.moveToFirst()) {
			do {
				Actividad esp = ActividadTableHelper.getFromCursor(c);
				toRet.add(esp);
			} while (c.moveToNext());
		}

		return toRet;
	}
	

	public static String getUpdateString(Actividad a) {
		String sql = "UPDATE " + tabla + " SET " 
				+ idClave + " = '" + a.getIdClave()+ "'," 
				+ idProyecto + " = '" + a.getIdProyecto() + "'," 
				+ fecha + " = '" + a.getFechaInicio() + "'," 
				+ descripcion + " = '" + a.getDescripcion() + "'," 
				+ nombre + " = '" + a.getNombre() + "'," 
				+ terminado + " = '" + ((a.isTerminado())?"1":"0") + "'," 
				+ tiempoDedicacion + " = '" + a.getTiempoDedicacion() + "',"
				+ tiempoEstimado + " = '" + a.getTiempoEstimado() + "',"
				+ unidades + " = '" + a.getUnidades() + "'" 
				+ " WHERE " + id + " = " + a.getId() + ";";
		return sql;
	}
	
	public static String getDeleteString(Actividad e) {
		String sql = " DELETE FROM " + tabla + " WHERE " + id + " = "
				+ e.getId();
		return sql;
	}

	public static ContentValues getInserContentValues(Actividad a) {
		ContentValues cv = new ContentValues();
		cv.put(idClave, a.getIdClave());
		cv.put(idProyecto, a.getIdProyecto());
		cv.put(fecha, a.getFechaInicio());
		cv.put(descripcion, a.getDescripcion());
		cv.put(nombre, a.getNombre());
		cv.put(terminado, a.isTerminado());
		cv.put(tiempoDedicacion, a.getTiempoDedicacion());
		cv.put(tiempoEstimado, a.getTiempoEstimado());
		cv.put(unidades, a.getUnidades());
		return cv;
	}

}