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
import com.troncodroide.pesoppo.beans.Opcion;
import com.troncodroide.pesoppo.calendar.CalendarFragment;
import com.troncodroide.pesoppo.interruptions.InterruptionsFragment;
import com.troncodroide.pesoppo.keys.KeisFragment;
import com.troncodroide.pesoppo.project.ProjectsFragment;

public class InicioActivity extends ActionBarActivity
        implements NavigationDrawerFragment.NavigationDrawerCallbacks {

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
                mTitle = "Actividades";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ActivitiesFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_CALENDAR: {
                mTitle = "Actividades";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, CalendarFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_CONFIGURATION: {
                break;
            }
            case Opcion.OPTION_INTERRUPTIONS: {
                mTitle = "Interrupciones";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, InterruptionsFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_KEYS: {
                mTitle = "Tags";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, KeisFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_PROJECTS: {
                mTitle = "Proyecto";
                fragmentManager.beginTransaction()
                        .replace(R.id.container, ProjectsFragment.newInstance())
                        .commit();
                break;
            }
            case Opcion.OPTION_STATUS: {
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
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            //getMenuInflater().inflate(R.menu.main2, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

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
        if (!getSupportFragmentManager().popBackStackImmediate()) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Salir");
            builder.setMessage("¿Desea salir?");
            builder.setNegativeButton("No",null);
            builder.setPositiveButton("Si", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });
            builder.create().show();
        } else {
            super.onBackPressed();
        }
    }
}
