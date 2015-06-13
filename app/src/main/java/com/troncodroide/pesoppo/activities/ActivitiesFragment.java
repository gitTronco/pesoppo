package com.troncodroide.pesoppo.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ActivitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivitiesFragment extends Fragment {

    private static ActivitiesFragment fragment;
    private DataHolder dh;
    private SqlLiteManager manager;

    private Activity mActivity;
    private ListView view;
    private List<Actividad> actividads;

    private OnActivitiesEventListener mListener;


    private static class DataHolder implements Serializable {
        List<Actividad> actividades;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment ActivitiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivitiesFragment newInstance() {
        if (fragment == null)
            fragment = new ActivitiesFragment();
        return fragment;
    }

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (ListView) inflater.inflate(R.layout.fragment_activities, container, false);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Actividad a = (Actividad) parent.getItemAtPosition(position);
                mListener.onActivityShowResume(a);
            }
        });
        return view;
    }

    private void loadActivities() {
        ActividadesController controller = new ActividadesController(manager);
        actividads = controller.getActividades();
        view.setAdapter(new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1, actividads));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        manager = new SqlLiteManager(activity);
        mListener = (OnActivitiesEventListener)activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onResume() {
        super.onResume();
        loadActivities();
    }

    public interface OnActivitiesEventListener {
        void onActivityShowResume(Actividad a);
    }
}
