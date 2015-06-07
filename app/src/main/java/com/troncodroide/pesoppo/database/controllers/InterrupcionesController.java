	package com.troncodroide.pesoppo.database.controllers;

	import java.util.List;

	import android.database.Cursor;

	import com.troncodroide.pesoppo.beans.Interrupcion;
	import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
	import com.troncodroide.pesoppo.database.sql.helpers.InterrupcionTableHelper;
	import com.troncodroide.pesoppo.exceptions.SqlExceptions;

public class InterrupcionesController {

		private SqlLiteManager manager;

			public InterrupcionesController(SqlLiteManager manager) {
				this.manager = manager;
			}

			public long addInterrupcion(Interrupcion i)
					throws SqlExceptions.DuplicatedIdException,
					SqlExceptions.UniqueKeyException {
				Cursor cId = manager.select(InterrupcionTableHelper.getSelectById(i
						.getId()));
				
				if (cId.getCount() > 0){
					cId.close();
					manager.close();
					throw new SqlExceptions.DuplicatedIdException();
				}else{

					long newId = manager.insert(InterrupcionTableHelper.tabla,
							InterrupcionTableHelper.getInserContentValues(i));
					i.setId(newId);
					cId.close();
					manager.close();
					return newId;
				}
			}

			public boolean delInterrupcion(Interrupcion i)
					throws SqlExceptions.IdNotFoundException {
				return manager.query(InterrupcionTableHelper.getDeleteString(i));
			}

			public boolean saveInterrupcion(Interrupcion i)
					throws SqlExceptions.DuplicatedIdException,
					SqlExceptions.IdNotFoundException, SqlExceptions.UniqueKeyException {
				return manager.query(InterrupcionTableHelper.getUpdateString(i));
			}

			public Interrupcion getInterrupcion(long id)
					throws SqlExceptions.DuplicatedIdException,
					SqlExceptions.IdNotFoundException {
				Cursor c = manager.select(InterrupcionTableHelper.getSelectById(id));
				c.moveToFirst();
				Interrupcion i = InterrupcionTableHelper.getFromCursor(c); 
				c.close();
				manager.close();
				return i;
			}

			public List<Interrupcion> getInterrupciones() {
				Cursor c = manager.select(InterrupcionTableHelper.getSelectAllString());
				List<Interrupcion> interrupciones= InterrupcionTableHelper.getListFromCursor(c);
				c.close();
				manager.close();
				return interrupciones;
			}
			public List<Interrupcion> getInterrupciones(long idActivity) {
				Cursor c = manager.select(InterrupcionTableHelper.getSelectAllByActivity(idActivity));
				List<Interrupcion> interrupciones= InterrupcionTableHelper.getListFromCursor(c);
				c.close();
				manager.close();
				return interrupciones;
			}

}
