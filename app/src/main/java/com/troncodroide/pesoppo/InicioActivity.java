package com.troncodroide.pesoppo;

import android.content.DialogInterface;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.FragmentManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.widget.DrawerLayout;
import android.view.animation.TranslateAnimation;

import com.troncodroide.pesoppo.activities.ActivitiesFragment;
import com.troncodroide.pesoppo.activities.ActivityFragment;
import com.troncodroide.pesoppo.activities.ActivityShowActivity;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Opcion;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.calendar.CalendarFragment;
import com.troncodroide.pesoppo.calendar.CalendarFragmentWrapper;
import com.troncodroide.pesoppo.interruptions.InterruptionFragment;
import com.troncodroide.pesoppo.interruptions.InterruptionsFragment;
import com.troncodroide.pesoppo.keys.KeisFragment;
import com.troncodroide.pesoppo.project.ProjectsFragment;
import com.troncodroide.pesoppo.status.StatusFragment;

import java.util.List;

public class InicioActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks, CalendarFragment.OnCalendarEventsListener, StatusFragment.OnStatusFragmentListener, ActivityFragment.OnFragmentActivityListener, InterruptionFragment.OnInterruptionsEventListener, ActivitiesFragment.OnActivitiesEventListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;
    private float lastTranslate;
    private View parent_container;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getSupportFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Set up the drawer.
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout), toolbar);

        parent_container = findViewById(R.id.wrappercontainer);
    }

    @Override
    public void onNavigationDrawerItemSelected(Opcion position) {

        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (position.getId()) {
            case Opcion.OPTION_ACTIVITIES: {
                clearBackstack();
                mTitle = "Actividades";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ActivitiesFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_CALENDAR: {
                clearBackstack();
                mTitle = "Calendario";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CalendarFragmentWrapper.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_CONFIGURATION: {
                break;
            }
            case Opcion.OPTION_INTERRUPTIONS: {
                clearBackstack();
                mTitle = "Interrupciones";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, InterruptionsFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_KEYS: {
                clearBackstack();
                mTitle = "Tags";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, KeisFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_PROJECTS: {
                clearBackstack();
                mTitle = "Proyecto";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ProjectsFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_STATUS: {
                clearBackstack();
                mTitle = "Resumen";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, StatusFragment.newInstance())
                        .commit();

                break;
            }
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            //getMenuInflater().inflate(R.menu.main2, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onNavigationDrawerSlide(View drawerView, float slideOffset) {
        float moveFactor = (drawerView.getWidth() * slideOffset);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            parent_container.setTranslationX(moveFactor);
        } else {
            TranslateAnimation anim = new TranslateAnimation(lastTranslate, moveFactor, 0.0f, 0.0f);
            anim.setDuration(0);
            anim.setFillAfter(true);
            parent_container.startAnimation(anim);
            lastTranslate = moveFactor;
        }
    }

    @Override
    public void onBackPressed() {
        try {
            if (!getSupportFragmentManager().popBackStackImmediate()) {
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Salir");
                builder.setMessage("¿Desea salir?");
                builder.setNegativeButton("No", null);
                builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                });
                builder.create().show();
            }
            restoreActionBar();
        /* else {
            super.onBackPressed();
        }*/
        } catch (IllegalStateException ex) {
                clearBackstack();
        }
    }

    private void clearBackstack(){
        FragmentManager fm = getSupportFragmentManager();
        for(int i = 0; i < fm.getBackStackEntryCount(); ++i) {
            fm.popBackStack();
        }
    }

    @Override
    public void onProyectClick(Proyecto p) {
        CalendarFragmentWrapper.newInstance().inflateResume(p);
    }

    @Override
    public void onActivityClick(Actividad a) {
        CalendarFragmentWrapper.newInstance().inflateResume(a);
    }
    @Override
    public void onListClick(List l) {
        CalendarFragmentWrapper.newInstance().inflateList(l);
    }

    @Override
    public void onItemSelected(StatusFragment.StatusItem item) {
        onNavigationDrawerItemSelected(new Opcion("", item.getType()));
        restoreActionBar();
    }

    @Override
    public void onActivityCreated(Actividad actividad) {
        ActivitiesFragment.newInstance().onResume();
    }

    @Override
    public void onActivityUpdate(Actividad actividad) {
        ActivitiesFragment.newInstance().onResume();
    }

    @Override
    public void onInterruptionUpdated() {
        InterruptionsFragment.newInstance().onResume();
    }

    @Override
    public void onActivityShowResume(Actividad a) {
        ActivityShowActivity.newInstance(this, a);
        restoreActionBar();
    }
}
