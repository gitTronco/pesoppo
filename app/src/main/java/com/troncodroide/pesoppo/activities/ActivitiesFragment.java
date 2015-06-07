package com.troncodroide.pesoppo.activities;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Clave;
import com.troncodroide.pesoppo.beans.Interrupcion;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.controllers.ClavesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link ActivitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivitiesFragment extends Fragment implements OnClickListener {

    private DataHolder dh;
    private SqlLiteManager manager;

    private Activity mActivity;

    @Override
    public void onClick(View v) {
    }


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
        ActivitiesFragment fragment = new ActivitiesFragment();
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
        ListView view = (ListView) inflater.inflate(R.layout.fragment_activities, container, false);
        ActividadesController controller = new ActividadesController(manager);

        List<Actividad> actividads = controller.getActividades();
        view.setAdapter(new ArrayAdapter<>(mActivity, android.R.layout.simple_list_item_1, actividads));
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        manager = new SqlLiteManager(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}
