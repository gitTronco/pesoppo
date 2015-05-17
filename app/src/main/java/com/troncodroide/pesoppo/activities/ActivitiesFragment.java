package com.troncodroide.pesoppo.activities;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
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
import com.troncodroide.pesoppo.database.controllers.ClavesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;

import java.io.Serializable;
import java.util.List;

/**
 * Activities that contain this fragment must implement the
 * {@link ActivitiesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivitiesFragment extends DialogFragment implements OnClickListener {

    private DataHolder dh;
    private ViewHolder vh;
    private SqlLiteManager manager;

    private OnFragmentInteractionListener mListener;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activities_keys_add: {
                //Crear la key añadirla o modificarla.
                validateClave();

            }
        }
    }

    private void validateClave() {

        if (vh.claves.getListSelection() == ListView.INVALID_POSITION){
            if (vh.claves.getText().toString().trim().length()==0){
                vh.claves.setError("Campo vacío");
            }else{
                dh.selectedKey = new Clave();
                dh.selectedKey.setNombre(vh.claves.getText().toString().trim());
                ClavesController controller  = new ClavesController(manager);
                try {
                    controller.addClave(dh.selectedKey);
                } catch (SqlExceptions.DuplicatedIdException e) {
                    e.printStackTrace();
                } catch (SqlExceptions.UniqueKeyException e) {
                    e.printStackTrace();
                }
            }
        }else{
            dh.selectedKey = (Clave)vh.claves.getAdapter().getItem(vh.claves.getListSelection());
        }
    }


    private class ViewHolder {
        EditText nombre, descripcion, estimacion;
        TextView fecha;
        AutoCompleteTextView claves;
        Button addActivity;
    }

    private static class DataHolder implements Serializable {
        Actividad actividad;
        List<Clave> claves;
        Clave selectedKey;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param actividad Parameter 1.
     * @return A new instance of fragment ActivitiesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActivitiesFragment newInstance(Actividad actividad) {
        ActivitiesFragment fragment = new ActivitiesFragment();
        Bundle args = new Bundle();
        args.putSerializable(Actividad.class.getName(), actividad);
        fragment.setArguments(args);
        return fragment;
    }

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dh = new DataHolder();
        if (getArguments() != null) {
            dh.actividad = (Actividad) getArguments().getSerializable(Actividad.class.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        vh = new ViewHolder();
        vh.claves = (AutoCompleteTextView) view.findViewById(R.id.activities_keys_autocomplete);
        vh.addActivity = (Button) view.findViewById(R.id.activities_keys_add);
        vh.nombre = (EditText) view.findViewById(R.id.activities_keys_name);
        vh.fecha = (TextView) view.findViewById(R.id.activities_keys_date);
        vh.descripcion = (EditText) view.findViewById(R.id.activities_keys_description);
        vh.estimacion = (EditText) view.findViewById(R.id.activities_keys_estimated_time);

        vh.addActivity.setOnClickListener(this);

        ClavesController controller = new ClavesController(manager);
        dh.claves = controller.getClaves();

        vh.claves.setAdapter(new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dh.claves));

        if (dh.actividad.getId() > 0) {
            vh.addActivity.setText("Modificar");
        } else {
            vh.addActivity.setText("Añadir");
        }
        vh.claves.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus){
                    vh.claves.showDropDown();
                }
            }
        });

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog = super.onCreateDialog(savedInstanceState);
        if (dh.actividad.getId() > 0) {
            dialog.setTitle(dh.actividad.getNombre());
        } else {
            dialog.setTitle("Actividad");
        }
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        manager = new SqlLiteManager(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        public void onActivityCreated(Actividad actividad);

        public void onActivityUpdate(Actividad actividad);
    }

}
