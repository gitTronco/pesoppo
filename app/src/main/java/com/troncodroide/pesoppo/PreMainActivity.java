package com.troncodroide.pesoppo;

import com.google.gson.Gson;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Interrupcion;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.configuracion.Configuracion;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.controllers.InterrupcionesController;
import com.troncodroide.pesoppo.database.controllers.ProyectosController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import android.os.Bundle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class PreMainActivity<T> extends ActionBarActivity {

    TextView t1;
    TextView t2;
    TextView t3;
    TextView t4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_main);
        t1 = (TextView) findViewById(R.id.TextView03);
        t2 = (TextView) findViewById(R.id.TextView02);
        t3 = (TextView) findViewById(R.id.TextView01);
        t4 = (TextView) findViewById(R.id.textViewNotificarionsFragment);
        Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);
        Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);
        Animation anim3 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);
        Animation anim4 = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fadein);
        anim1.setStartOffset(500);
        anim2.setStartOffset(1000);
        anim3.setStartOffset(1500);
        anim4.setStartOffset(2000);

        t1.startAnimation(anim1);
        t2.startAnimation(anim2);
        t3.startAnimation(anim3);
        t4.startAnimation(anim4);
        Configuracion.setContext(getApplicationContext());

        anim4.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                startActivity(new Intent(PreMainActivity.this, InicioActivity.class));
                PreMainActivity.this.finish();
            }
        });
/*
        SqlLiteManager manager = new SqlLiteManager(this);
        ActividadesController aController = new ActividadesController(manager);
        ProyectosController pController = new ProyectosController(manager);
        Log.i("GetActivities", toString(aController.getActividades()));
        Log.i("GetProjectos", toString(pController.getProyectos()));
        Actividad a = new Actividad();
        a.setNombre("Leer");
        a.setDescripcion("Lecturas para la presnetación del proyecto de tfg");
        a.setIdClave(1);
        a.setFechaInicio("22-04-2015");
        a.setIdProyecto(1);
        a.setTerminado(false);
        a.setTiempoDedicacion("1h");
        a.setTiempoEstimado("1h");
        a.setUnidades(3);
        Log.i("Activity", new Gson().toJson(a));
        Interrupcion i = new Interrupcion();
        i.setId(1);
        i.setTiempo("15m");
        i.setNombre("Cafe");
        i.setDescripcion("Parada para el cafe de la mañana");
        i.setIdActividad(1);
        Interrupcion i2 = new Interrupcion();
        i2.setId(2);
        i2.setTiempo("15m");
        i2.setNombre("Teléfono");
        i2.setDescripcion("Llamada por incidencia con la web");
        i2.setIdActividad(1);

        InterrupcionesController ic = new InterrupcionesController(manager);
        try {
            Log.i("AddInterruption",new Gson().toJson(i));
            ic.saveInterrupcion(i);
            Log.i("AddInterruption",new Gson().toJson(i2));
            ic.saveInterrupcion(i2);
            aController.addActividad(a);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        Log.i("GetInterruptions", new Gson().toJson(ic.getInterrupciones()));
        Log.i("GetActivities", toString(aController.getActividades()));
        Log.i("GetProject1", new Gson().toJson(pController.getProyecto(1)));
        Log.i("GetProject2", new Gson().toJson(pController.getProyecto(2)));
*/
    }

    private String toString(List array) {
        String tString = "[";
        if (array != null)
            for (Object obj : array) {
                if (tString.trim().length() > 1) {
                    tString += ",";
                }
                tString += new Gson().toJson(obj);
            }
        tString += "]";

        return tString;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.pre_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //sendPendingNotification();
        return super.onOptionsItemSelected(item);
    }

    private void sendPendingNotification() {
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
                PreMainActivity.this)
                .setSmallIcon(android.R.drawable.stat_sys_warning)
                .setLargeIcon(
                        (((BitmapDrawable) getResources().getDrawable(
                                R.drawable.ic_launcher)).getBitmap()))
                .setContentTitle("Mensaje de Alerta")
                .setContentText("Ejemplo de notificaci0n.").setContentInfo("4")
                .setTicker("Alerta!");
        Intent notIntent = new Intent(PreMainActivity.this,
                PreMainActivity.class);
        PendingIntent contIntent = PendingIntent.getActivity(
                PreMainActivity.this, 0, notIntent, 0);
        mBuilder.setContentIntent(contIntent);
        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(1, mBuilder.build());

    }

}
