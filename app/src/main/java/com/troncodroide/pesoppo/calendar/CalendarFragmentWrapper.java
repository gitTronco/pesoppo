package com.troncodroide.pesoppo.calendar;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.roomorama.caldroid.CaldroidFragment;
import com.roomorama.caldroid.CaldroidListener;
import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.activities.ActivityShowActivity;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.customviews.ExpandedListView;
import com.troncodroide.pesoppo.database.controllers.ProyectosController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.project.ProjectShowActivity;
import com.troncodroide.pesoppo.util.CalendarUtil;
import com.troncodroide.pesoppo.util.ValidateUtil;

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
 * Use the {@link CalendarFragmentWrapper#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragmentWrapper extends Fragment {

    private static CalendarFragmentWrapper fragment;
    private ExpandedListView resume;

    /**
     * Use this factory method to create a new instance of
     *
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragmentWrapper newInstance() {
        if (fragment == null) {
            fragment = new CalendarFragmentWrapper();
        }
        return fragment;
    }

    public CalendarFragmentWrapper() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_calendar, container, false);
        resume = (ExpandedListView) v.findViewById(R.id.wrapper_calendar_detail);
        resume.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object obj = parent.getItemAtPosition(position);
                if (obj instanceof Actividad) {
                    ActivityShowActivity.newInstance(getActivity(),(Actividad) obj);
                } else if (obj instanceof Proyecto) {
                    ProjectShowActivity.startActivity(getActivity(),(Proyecto) obj);
                    inflateResume((Proyecto) obj);
                }
            }
        });
        return v;
    }

    public void inflateResume(Proyecto p) {
        List<Object> l = new LinkedList<>();
        l.add(p);
        resume.setAdapter(new CalendarListAdapter(getActivity(), l));

    }

    public void inflateResume(Actividad a) {
        List<Object> l = new LinkedList<>();
        l.add(a);
        resume.setAdapter(new CalendarListAdapter(getActivity(), l));
    }

    public void inflateList(List l) {
        resume.setAdapter(new CalendarListAdapter(getActivity(),l));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public class CalendarListAdapter extends ArrayAdapter<Object> {
        public CalendarListAdapter(Context context, List<Object> objects) {
            super(context, R.layout.pessopo_simple_item_listview, R.id.pesoppo_item_title, objects);
        }

        private class ViewHolder {
            TextView title, icon, extra;
            View colorwrapper;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v = super.getView(position, convertView, parent);
            final ViewHolder vh;
            if (convertView == null) {
                vh = new ViewHolder();
                convertView = v;
                vh.title = (TextView) convertView.findViewById(R.id.pesoppo_item_title);
                vh.extra = (TextView) convertView.findViewById(R.id.pesoppo_item_extra);
                vh.icon = (TextView) convertView.findViewById(R.id.pesoppo_item_icon);
                vh.colorwrapper = convertView.findViewById(R.id.pesoppo_item_color);
                convertView.setTag(vh);
            } else {
                vh = (ViewHolder) convertView.getTag();
            }
            Object item = getItem(position);
            String extra = "";
            int iconRes = 0;
            if (item instanceof Actividad) {
                iconRes = R.string.fa_tasks;
                extra = ((Actividad) item).getTiempoDedicacion();
            } else if (item instanceof Proyecto) {
                iconRes = R.string.fa_cube;
                extra = ValidateUtil.getValidTime(((Proyecto) item).getDedicatedTime());
            }
            vh.icon.setText(iconRes);
            vh.extra.setText(extra);
            return convertView;
        }
    }
}
