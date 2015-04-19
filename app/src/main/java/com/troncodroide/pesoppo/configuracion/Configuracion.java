package com.troncodroide.pesoppo.configuracion;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.ListView;

/**
 * Created by Tronco on 29/08/13.
 */
public class Configuracion {
	
	public static final String url_error_server_report ="http://un.hostei.com/serverrenfe.php";
    private static final String PRIMERA_VEZ = "preference_primera_vez";
    private static final String FECHA_DIA = "preference_fecha_dia";
    
    
    private static SharedPreferences pref;
    private static Context context;

    @SuppressWarnings("static-access")
    public static void setContext(Context con) {
        pref = con.getSharedPreferences("User", con.MODE_WORLD_READABLE);
        context = con;
    }

    public synchronized static Context getContext() {
        return context;
    }

    public static void setPrimeraVez(Boolean primeraVez) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putBoolean(PRIMERA_VEZ, primeraVez);
        edit.commit();
    }
    
    public static Boolean isPrimeraVez() {
        return pref.getBoolean(PRIMERA_VEZ, true);
    }

    public static void setFechaDia(String fecha) {
        SharedPreferences.Editor edit = pref.edit();
        edit.putString(FECHA_DIA, fecha);
        edit.commit();
    }

    public static String getFechaDia() {
        return pref.getString(FECHA_DIA, "");
    }

}
