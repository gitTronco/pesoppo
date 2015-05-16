package com.troncodroide.pesoppo.activities;

import android.app.Activity;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Clave;
import com.troncodroide.pesoppo.beans.Interrupcion;

import java.io.Serializable;
import java.util.List;

/**
 *
 * Activities that contain this fragment must implement the
 * {@link ActivitiesFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivitiesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivitiesFragment extends DialogFragment {

    private DataHolder dh;
    private ViewHolder vh;

    private OnFragmentInteractionListener mListener;


    private class ViewHolder {
        EditText nombre, descripcion, estimacion;
        TextView fecha;
        AutoCompleteTextView claves;
        Button addClave;
    }

    private static class DataHolder implements Serializable {
        Actividad actividad;
        List<Clave> claves;
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
            dh.actividad = (Actividad)getArguments().getSerializable(Actividad.class.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activities, container, false);
        vh = new ViewHolder();
        vh.claves = (AutoCompleteTextView) view.findViewById(R.id.activities_keys_autocomplete);
        vh.addClave = (Button) view.findViewById(R.id.activities_keys_add);
        vh.nombre = (EditText) view.findViewById(R.id.activities_keys_name);
        vh.fecha = (TextView) view.findViewById(R.id.activities_keys_date);
        vh.descripcion = (EditText) view.findViewById(R.id.activities_keys_description);
        vh.estimacion = (EditText) view.findViewById(R.id.activities_keys_estimated_time);

        return view;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        Dialog dialog =  super.onCreateDialog(savedInstanceState);
        dialog.setTitle("Actividad");
        return dialog;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
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
