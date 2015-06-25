package com.troncodroide.pesoppo.project;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.activities.ActivityFragment;
import com.troncodroide.pesoppo.activities.ActivityShowActivity;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.beans.adapters.ActividadesAdapter;
import com.troncodroide.pesoppo.customviews.AwesomeButton;
import com.troncodroide.pesoppo.customviews.AwesomeTextView;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.controllers.ProyectosController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;
import com.troncodroide.pesoppo.util.ValidateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ProjectShowActivity extends ActionBarActivity implements View.OnClickListener, ActivityFragment.OnFragmentActivityListener {
    DataHolder dh;
    ViewHolder vh;
    SqlLiteManager manager;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.projectshow_add_activity: {
                Actividad act = new Actividad();
                act.setIdProyecto(dh.proyecto.getId());
                ActivityFragment.newInstance(act).show(getSupportFragmentManager(), ActivityFragment.class.getSimpleName());
            }
        }
    }

    public static void startActivity(Activity ac, Proyecto p) {
        Intent intent = new Intent(ac, ProjectShowActivity.class);
        intent.putExtra(Proyecto.class.getName(), p);
        ac.startActivity(intent);
    }

    @Override
    public void onActivityCreated(Actividad actividad) {
        dh.actividades.add(actividad);
        ((ArrayAdapter) vh.actividades.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onActivityUpdate(Actividad actividad) {
        loadActivities();
        ((ArrayAdapter) vh.actividades.getAdapter()).notifyDataSetChanged();
    }

    private void loadActivities() {
        ActividadesController controller = new ActividadesController(new SqlLiteManager(this));
        dh.actividades = controller.getActividades(dh.proyecto.getId());
    }

    private class ViewHolder {
        ListView actividades;
        Button add;
        PieChart pc;
        AwesomeTextView  tiempodedicado, tiempoestimado, tiemporestante, tiempointerrupcion;
    }

    private static class DataHolder implements Serializable {
        Proyecto proyecto;
        List<Actividad> actividades;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DataHolder.class.getName(), dh);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_proyect_show);
        manager = new SqlLiteManager(this);
        if (savedInstanceState == null) {
            dh = new DataHolder();
            dh.proyecto = (Proyecto) getIntent().getSerializableExtra(Proyecto.class.getName());
            loadActivities();
        } else {
            dh = (DataHolder) savedInstanceState.getSerializable(DataHolder.class.getName());
        }

        vh = new ViewHolder();
        vh.pc = (PieChart) findViewById(R.id.chartResumeProject);
        vh.actividades = (ListView) findViewById(R.id.actividades_proyecto_listview);
        vh.tiempodedicado = (AwesomeTextView) findViewById(R.id.projectshow_time_dedication);
        vh.tiempoestimado = (AwesomeTextView) findViewById(R.id.projectshow_time_estimado);
        vh.tiempointerrupcion = (AwesomeTextView) findViewById(R.id.projectshow_time_interruptions);
        vh.tiemporestante = (AwesomeTextView) findViewById(R.id.projectshow_time_rest);
        vh.add = (AwesomeButton) findViewById(R.id.projectshow_add_activity);

        vh.actividades.setAdapter(new ActividadesAdapter(this, dh.actividades));
        vh.add.setTag(new Actividad());
        vh.add.setOnClickListener(this);

        vh.actividades.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Actividad a = (Actividad) parent.getItemAtPosition(position);
                ActivityShowActivity.newInstance(ProjectShowActivity.this, a);
            }
        });

        if (getSupportActionBar() != null) {
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
            ab.setTitle(dh.proyecto.getNombre());
        }

        vh.actividades.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Actividad act = (Actividad) parent.getItemAtPosition(position);
                Snackbar.make(parent, "Acciones:", Snackbar.LENGTH_LONG).setAction("borrar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ActividadesController ac = new ActividadesController(manager);
                        try {
                            ac.delActividad(act);
                        } catch (SqlExceptions.IdNotFoundException e) {
                            e.printStackTrace();
                        }
                        refreshProyecto();
                        loadUIData();
                    }
                }).show();
                return true;
            }
        });
        loadUIData();

    }

    private void refreshProyecto() {
        ProyectosController pc = new ProyectosController(manager);
        dh.proyecto = pc.getProyecto(dh.proyecto.getId());
    }

    private void loadUIData() {
        if (getSupportActionBar() != null) {
            ActionBar ab = getSupportActionBar();
            ab.setTitle(dh.proyecto.getNombre());
        }

        exampleData();

        vh.actividades.setAdapter(new ActividadesAdapter(this, dh.proyecto.getActividades()));
        vh.tiempointerrupcion.setText("Interrupciones: " + ValidateUtil.getValidTime(dh.proyecto.getInterruptionsTime()));
        vh.tiempodedicado.setText("Dedicación: " + ValidateUtil.getValidTime(dh.proyecto.getDedicatedTime()));
        vh.tiempoestimado.setText("Estimación: " + ValidateUtil.getValidTime(dh.proyecto.getEstimedTime()));

        int diference = dh.proyecto.getEstimedTime() - dh.proyecto.getDedicatedTime();
        if (diference >= 0)
            vh.tiemporestante.setText("Restante: " + ValidateUtil.getValidTime(diference));
        else
            vh.tiemporestante.setText("Sobrededicado: " + ValidateUtil.getValidTime(Math.abs(diference)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proyect_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            Proyecto p = dh.proyecto;
            Intent intent = new Intent(this, ProjectActivity.class);
            intent.putExtra(Proyecto.class.getName(), p);
            startActivity(intent);
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    private void exampleData() {
        vh.pc.setUsePercentValues(true);
        vh.pc.setDescription("");

        vh.pc.setDragDecelerationFrictionCoef(0.95f);

        vh.pc.setDrawHoleEnabled(true);
        vh.pc.setHoleColorTransparent(true);

        vh.pc.setTransparentCircleColor(Color.WHITE);

        vh.pc.setHoleRadius(58f);
        vh.pc.setTransparentCircleRadius(61f);

        vh.pc.setDrawCenterText(false);

        vh.pc.setRotationAngle(0);
        // enable rotation of the chart by touch
        vh.pc.setRotationEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //vh.pc.setOnChartValueSelectedListener(this);

        //vh.pc.setCenterText(dh.actividad.getNombre());

        setData();

        vh.pc.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = vh.pc.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void setData() {

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();

        boolean hasInterruptions = (dh.proyecto.getInterruptionsTime() > 0);
        int t_dedicado = dh.proyecto.getDedicatedTime();
        int t_estimado = dh.proyecto.getEstimedTime();
        int diferencia = t_estimado - t_dedicado;

        if (hasInterruptions) {
            t_dedicado -= dh.proyecto.getInterruptionsTime();
        }


        yVals1.add(new Entry(t_dedicado, 0));
        xVals.add("Dedicado");
        colors.add(getResources().getColor(R.color.verde));
        //yVals1.add(new Entry(t_estimado, 1));
        if (diferencia < 0) {
            int sobrestimado = Math.abs(diferencia);
            xVals.add("SobreDedicado");
            yVals1.add(new Entry(sobrestimado, 1));
            colors.add(getResources().getColor(R.color.rojo));
        } else {
            xVals.add("Restante");
            yVals1.add(new Entry(diferencia, 1));
            colors.add(getResources().getColor(R.color.azul_claro));
        }

        if (hasInterruptions) {
            yVals1.add(new Entry(dh.proyecto.getInterruptionsTime(), 2));
            xVals.add("Interrupciones");
            colors.add(getResources().getColor(R.color.naranja));
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        vh.pc.setData(data);

        // undo all highlights
        vh.pc.highlightValues(null);

        vh.pc.invalidate();
    }

}
