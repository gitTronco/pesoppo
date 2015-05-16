package com.troncodroide.pesoppo.project;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.activities.ActivitiesFragment;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import java.io.Serializable;
import java.util.List;


public class ProjectShowActivity extends ActionBarActivity implements View.OnClickListener, ActivitiesFragment.OnFragmentInteractionListener {
    DataHolder dh;
    ViewHolder vh;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.add_new_activity: {
                ActivitiesFragment.newInstance((Actividad)view.getTag()).show(getSupportFragmentManager(),ActivitiesFragment.class.getSimpleName());
            }
        }
    }

    @Override
    public void onActivityCreated(Actividad actividad) {
        dh.actividades.add(actividad);
        ((ArrayAdapter)vh.actividades.getAdapter()).notifyDataSetChanged();
    }

    @Override
    public void onActivityUpdate(Actividad actividad) {
        loadActivities();
        ((ArrayAdapter)vh.actividades.getAdapter()).notifyDataSetChanged();
    }

    private void loadActivities() {
        ActividadesController controller = new ActividadesController(new SqlLiteManager(this));
        dh.actividades = controller.getActividades();
    }


    private class ViewHolder {
        ListView actividades;
        Button add;
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

        if (savedInstanceState == null) {
            dh = new DataHolder();
            dh.proyecto = (Proyecto) getIntent().getSerializableExtra(Proyecto.class.getName());
            loadActivities();
        } else {
            dh = (DataHolder) savedInstanceState.getSerializable(DataHolder.class.getName());
        }

        vh = new ViewHolder();
        vh.actividades = (ListView) findViewById(R.id.actividades_proyecto_listview);
        vh.add = (Button) findViewById(R.id.add_new_activity);

        vh.actividades.setAdapter(new ArrayAdapter<Actividad>(this, android.R.layout.simple_list_item_1, dh.actividades));
        vh.add.setTag(new Actividad());
        vh.add.setOnClickListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(dh.proyecto.getNombre());


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
}
