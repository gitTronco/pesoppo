package com.troncodroide.pesoppo.util;


import java.util.LinkedList;
import java.util.List;

public class SQLLiteUtil {

	public static class SQLBuilder{
	List<String> selects;
	List<String> tables;
	List<String> wheres;

	public SQLBuilder() {
		selects = new LinkedList<String>();
		tables = new LinkedList<String>();
		wheres = new LinkedList<String>();
	}

	public void addSelect(String select) {
		selects.add(select);

	}

	public void addTable(String table) {
		tables.add(table);
	}

	public void addWhere(String where) {
		wheres.add(where);
	}
	public void clear(){
		selects.clear();
		tables.clear();
		wheres.clear();
	}

	public String buildSelect() {
		List<String> selectList = new LinkedList<String>(selects);
		String selectString = "";
		if (selectList.size() != 0) {
			selectString= " " + selectList.get(0);
			selectList.remove(0);
			for (String string : selectList) {
				selectString += " , " + string;
			}
		}else{
			selectString = " * ";
		}

		List<String> tableList = new LinkedList<String>(tables);
		String tableString = "";
		if (tableList.size() != 0) {
			tableString= " " + tableList.get(0);
			tableList.remove(0);
			for (String string : tableList) {
				tableString += " , " + string;
			}
		}
		
		List<String> whereList = new LinkedList<String>(wheres);
		String whereString ="";
		if (whereList.size() != 0) {
			whereString = " WHERE " + whereList.get(0);
			whereList.remove(0);
			for (String string : whereList) {
				whereString += " AND " + string;
			}
		}
		
		String sql = "SELECT " + selectString + " FROM "+ tableString + whereString;

		return sql;
	}}
}
