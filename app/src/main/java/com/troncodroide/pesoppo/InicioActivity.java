package com.troncodroide.pesoppo;

import com.troncodroide.pesoppo.beans.Opcion;
import com.troncodroide.pesoppo.fragments.DrawerLeftFragment;
import com.troncodroide.pesoppo.fragments.InicioActividadesFragment;
import com.troncodroide.pesoppo.fragments.ProyectosFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.ActionBarDrawerToggle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

public class InicioActivity extends ActionBarActivity implements DrawerLeftFragment.EventListener{
    DrawerLeftFragment drawerFragment;
    InicioActividadesFragment inicioFragment;
    ProyectosFragment proyectosFragment;
    DrawerLayout drawerLayout;
    ActionBarDrawerToggle mDrawerToggle;

    int paginacion = 0;
    private final int PAGINA_INICIO = 0;
    private final int PAGINA_ACTIVIDADES = 1;
    private final int PAGINA_PROYECTOS = 2;
    private final int PAGINA_INTERRUPCIONES = 3;
    private final int PAGINA_CLAVES = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                R.drawable.ic_launcher,
                R.string.app_name,
                R.string.title_activity_proyect) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                //getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                //getActionBar().setTitle(mDrawerTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };

        // Set the drawer toggle as the DrawerListener
        drawerLayout.setDrawerListener(mDrawerToggle);
        drawerFragment = new DrawerLeftFragment();
        inicioFragment = new InicioActividadesFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, inicioFragment, inicioFragment.name).
                replace(R.id.left_drawer, drawerFragment, drawerFragment.name).commit();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //http://developer.android.com/training/implementing-navigation/nav-drawer.html
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.proyect, menu);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (drawerLayout.isDrawerOpen(Gravity.LEFT)){
                    drawerLayout.closeDrawer(Gravity.LEFT);
                }else {
                    super.onBackPressed();
                }
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onOptionClicked(Opcion op) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if (op.getId().contentEquals(DrawerLeftFragment.OPTION_ACTIVITIES)){

        }else if (op.getId().contentEquals(DrawerLeftFragment.OPTION_INTERRUPTIONS)){

        }else if (op.getId().contentEquals(DrawerLeftFragment.OPTION_KEYS)){

        }else if (op.getId().contentEquals(DrawerLeftFragment.OPTION_PROJECTS)){
            proyectosFragment = (proyectosFragment==null)?new ProyectosFragment():proyectosFragment;
            ft.replace(R.id.content_frame, proyectosFragment, proyectosFragment.name);
        }else if (op.getId().contentEquals(DrawerLeftFragment.OPTION_STATUS)){

        }
        ft.commit();
        drawerLayout.closeDrawer(Gravity.LEFT);

    }
}