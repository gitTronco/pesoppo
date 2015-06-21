package com.troncodroide.pesoppo.database.controllers;

import java.util.LinkedList;
import java.util.List;

import android.database.Cursor;

import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Clave;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.database.sql.helpers.ActividadTableHelper;
import com.troncodroide.pesoppo.database.sql.helpers.ClaveTableHelper;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;
import com.troncodroide.pesoppo.util.ValidateUtil;

public class ClavesController {
    private SqlLiteManager manager;

    public ClavesController(SqlLiteManager manager) {
        this.manager = manager;
    }

    public long addClave(Clave c)
            throws SqlExceptions.DuplicatedIdException,
            SqlExceptions.UniqueKeyException {
        Cursor cName = manager.select(ClaveTableHelper.getSelectByName(c
                .getNombre()));
        Cursor cId = manager.select(ClaveTableHelper.getSelectById(c
                .getId()));

        if (cName.getCount() > 0) {
            cName.close();
            cId.close();
            manager.close();
            throw new SqlExceptions.UniqueKeyException();
        } else if (cId.getCount() > 0) {
            cName.close();
            cId.close();
            manager.close();
            throw new SqlExceptions.DuplicatedIdException();
        } else {

            long newId = manager.insert(ClaveTableHelper.tabla,
                    ClaveTableHelper.getInserContentValues(c));
            c.setId(newId);
            cName.close();
            cId.close();
            manager.close();
            return newId;
        }
    }

    public boolean delClave(Clave c)
            throws SqlExceptions.IdNotFoundException {
        return manager.query(ClaveTableHelper.getDeleteString(c));
    }

    public boolean saveClave(Clave c)
            throws SqlExceptions.DuplicatedIdException,
            SqlExceptions.IdNotFoundException, SqlExceptions.UniqueKeyException {
        return manager.query(ClaveTableHelper.getUpdateString(c));
    }

    public Clave getClave(long id)
            throws SqlExceptions.DuplicatedIdException,
            SqlExceptions.IdNotFoundException {
        Cursor c = manager.select(ClaveTableHelper.getSelectById(id));
        c.moveToFirst();
        Clave clave = ClaveTableHelper.getFromCursor(c);
        c.close();
        manager.close();
        return clave;
    }

    public Clave getClave(String name) {
        Cursor c = manager.select(ClaveTableHelper.getSelectByName(name));
        c.moveToFirst();
        Clave p = ClaveTableHelper.getFromCursor(c);
        c.close();
        manager.close();
        return p;
    }

    public List<Clave> getClaves() {
        Cursor c = manager.select(ClaveTableHelper.getSelectAllString());
        List<Clave> claves = ClaveTableHelper.getListFromCursor(c);
        c.close();
        manager.close();
        return claves;
    }

    public List<Clave> getClaves(Proyecto p) {
        Cursor c = manager.select(ClaveTableHelper.getSelectAllString());
        List<Clave> claves = ClaveTableHelper.getListFromCursor(c);
        c.close();
        manager.close();
        return claves;
    }

    public List<Clave> getClaves(Actividad a) {
        Cursor c = manager.select(ClaveTableHelper.getSelectAllString());
        List<Clave> claves = ClaveTableHelper.getListFromCursor(c);
        c.close();
        manager.close();
        return claves;
    }

    private int getTime(Clave item) {
        Cursor c = manager.select(ActividadTableHelper.getSelectAllByClave(item.getId()));
        List<Actividad> actividades= ActividadTableHelper.getListFromCursor(c);
        int estimado = 0;
        int num = 0;
        for (Actividad a :actividades){
            estimado+= ValidateUtil.getValidTime(a.getTiempoDedicacion())/a.getUnidades();
            num++;
        }

        c.close();
        manager.close();
        if (num==0)
            return 0;
        return (estimado / num);
    }

    public String getEstimatedTime(Clave item) {
        int estimado = getTime(item);
        return ValidateUtil.getValidTime(estimado);
    }

    public String getEstimatedTime(Clave item, int units) {
        int estimado = getTime(item);

        return ValidateUtil.getValidTime(estimado * units);
    }
}
