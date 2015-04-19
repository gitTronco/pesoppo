package com.troncodroide.pesoppo.database.sql;


import android.content.Context;
import android.database.Cursor;

public interface DBManager {

	public static final String bdName = "PesoppoDB";
	
	public abstract void open_create(Context context);

	public abstract void open_create();

	public abstract void build(boolean ejemplos);

	public abstract boolean query(String sql);

	public abstract Cursor select(String sql);

	public abstract void close();

	public abstract boolean isOpen();
}