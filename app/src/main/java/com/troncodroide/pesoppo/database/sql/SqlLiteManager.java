package com.troncodroide.pesoppo.database.sql;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.database.sql.helpers.ActividadTableHelper;
import com.troncodroide.pesoppo.database.sql.helpers.ClaveTableHelper;
import com.troncodroide.pesoppo.database.sql.helpers.InterrupcionTableHelper;
import com.troncodroide.pesoppo.database.sql.helpers.ProyectoTableHelper;

public class SqlLiteManager extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "PesoppoDB";
    private static final int DATABASE_VERSION = 2;
    private static SQLiteDatabase db = null;
    private final String TAG = SqlLiteManager.class.getSimpleName();

    public SqlLiteManager(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void build(boolean ejemplos) {
        try {
            // ActividadObservable
            String sql = ActividadTableHelper.getCreateTableSQL();
            query(sql);

            // InterrupcionObservable
            sql = InterrupcionTableHelper.getCreateTableSQL();
            query(sql);

            // ClaveObservable
            sql = ClaveTableHelper.getCreateTableSQL();
            query(sql);

            // ProyectoObservable
            sql = ProyectoTableHelper.getCreateTableSQL();
            query(sql);
            if (ejemplos) {
                Proyecto p = new Proyecto();
                p.setNombre("Proyecto 1");
                p.setDescripcion("Descripcion de ejemplo");
                p.setFechaInicio("01/08/2014");
                Proyecto p2 = new Proyecto();
                p2.setNombre("Proyecto 2");
                p2.setDescripcion("Descripcion de ejemplo2");
                p2.setFechaInicio("30/07/2014");

                insert(ProyectoTableHelper.tabla,
                        ProyectoTableHelper.getInserContentValues(p));
                insert(ProyectoTableHelper.tabla,
                        ProyectoTableHelper.getInserContentValues(p2));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            close();
        }
    }

    public synchronized long insert(String tabla, ContentValues contentValues) {
        long toRet = -1;
        try {
            if (db == null || !db.isOpen()) {
                db = getWritableDatabase();
            }
            toRet = db.insert(tabla, null, contentValues);
            Log.i(TAG, "insert:" + toRet + "@" + contentValues.toString());
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
//            db.close();
        }
        return toRet;
    }

    public synchronized boolean query(String sql) {
        Log.i(TAG, "query:" + sql);
        boolean success = true;

        if (db == null || !db.isOpen()) {
            db = getWritableDatabase();
        }
        db.beginTransaction();
        try {
            db.execSQL(sql);
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
            success = false;
        } finally {
            db.endTransaction();
//            db.close();
        }
        return success;
    }

    public synchronized Cursor select(String sql) {
        Log.i(TAG, "select:" + sql);
        Cursor toRet = null;
        if (db == null || !db.isOpen()) {
            db = getWritableDatabase();
        }
        db.beginTransaction();
        try {
            toRet = db.rawQuery(sql, null);
            db.setTransactionSuccessful();
        } catch (SQLiteException e) {
            e.printStackTrace();
        } finally {
            db.endTransaction();
//            db.close();
        }
        return toRet;
    }


    @Override
    public synchronized void close() {
        // Log.i(TAG, "close");
        if (db != null) {
            db.close();
        } else {
            logNull();
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "Create");
        this.db = db;
        build(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.i(TAG, "Upgrade");
    }

    private void logNull() {
        Log.w(TAG, "BD NULL");
    }
}
