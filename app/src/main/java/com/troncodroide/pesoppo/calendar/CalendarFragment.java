package com.troncodroide.pesoppo.calendar;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.database.controllers.ProyectosController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.util.CalendarUtil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends CaldroidFragment {

    private static CalendarFragment fragment;
    private SqlLiteManager manager;
    private HashMap<Date, List<Object>> elements;
    private OnCalendarEventsListener mListener;

    /**
     * Use this factory method to create a new instance of
     *
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance() {
        if (fragment == null) {
            fragment = new CalendarFragment();
            //SetupCalcdroid
            Bundle args = new Bundle();
            Calendar cal = Calendar.getInstance();
            args.putBoolean(CaldroidFragment.SQUARE_TEXT_VIEW_CELL, false);
            args.putInt(CaldroidFragment.MONTH, cal.get(Calendar.MONTH) + 1);
            args.putInt(CaldroidFragment.YEAR, cal.get(Calendar.YEAR));
            args.putBoolean(CaldroidFragment.SHOW_NAVIGATION_ARROWS, true);
            args.putBoolean(CaldroidFragment.ENABLE_SWIPE, true);
            args.putBoolean(CaldroidFragment.SIX_WEEKS_IN_CALENDAR, true);

            fragment.setArguments(args);
        }
        return fragment;
    }

    public CalendarFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mListener = (OnCalendarEventsListener) activity;
        manager = new SqlLiteManager(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        HashMap<Date, Integer> days = new HashMap<>();
        elements = new HashMap<>();
        ProyectosController pc = new ProyectosController(manager);
        List<Proyecto> proyectos = pc.getProyectos();
        for (Proyecto p : proyectos) {
            try {
                Calendar c = CalendarUtil.parseDateString(p.getFechaInicio(), CalendarUtil.patternDateDDMMYYYY);
                for (Actividad a : p.getActividades()) {
                    Calendar ac = CalendarUtil.parseDateString(a.getFechaInicio(), CalendarUtil.patternDateDDMMYYYY);
                    days.put(ac.getTime(), R.color.azul_claro);
                    if (elements.get(ac.getTime()) == null) {
                        elements.put(ac.getTime(), new LinkedList<Object>());
                    }
                    elements.get(ac.getTime()).add(a);

                }
                if (elements.get(c.getTime()) == null) {
                    elements.put(c.getTime(), new LinkedList<Object>());
                }
                elements.get(c.getTime()).add(p);
                days.put(c.getTime(), R.color.azul);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        setBackgroundResourceForDates(days);

        setCaldroidListener(new CaldroidListener() {
            @Override
            public void onSelectDate(Date date, View view) {
                List<Object> ob = elements.get(date);
                if (ob != null) {
                    if (ob.size() == 1) {
                        Object object = ob.get(0);
                        if (object instanceof Proyecto) {
                            Log.i("Proyecto", object.toString());
                            mListener.onProyectClick((Proyecto) object);
                        } else if (object instanceof Actividad) {
                            Log.i("Actividad", object.toString());
                            mListener.onActivityClick((Actividad) object);

                        }
                    } else {
                        mListener.onListClick(ob);
                    }
                }
            }
        });
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public interface OnCalendarEventsListener {
        void onProyectClick(Proyecto p);

        void onActivityClick(Actividad a);

        void onListClick(List lista);
    }
}
