package com.troncodroide.pesoppo.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Clave;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.customviews.CustomToast;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.controllers.ClavesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;
import com.troncodroide.pesoppo.fragments.DatePickerFragment;
import com.troncodroide.pesoppo.fragments.MultiNotificationsFragment;
import com.troncodroide.pesoppo.fragments.NotificationsFragment;
import com.troncodroide.pesoppo.util.ValidateUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Activities that contain this fragment must implement the
 * {@link ActivityFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ActivityFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityFragment extends DialogFragment implements OnClickListener {

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
                if (validateActivity()){

                    ActividadesController ac = new ActividadesController(manager);
                    try {
                        if (dh.actividad.getId()==0)
                        dh.actividad.setId(ac.addActividad(dh.actividad));
                        else
                            try {
                                ac.saveActividad(dh.actividad);
                            } catch (SqlExceptions.IdNotFoundException e) {
                                e.printStackTrace();
                            }
                    } catch (SqlExceptions.DuplicatedIdException e) {
                        e.printStackTrace();
                    } catch (SqlExceptions.UniqueKeyException e) {
                        e.printStackTrace();
                    }
                    if (dh.actividad.getId()!=0){
                        ((Button)v).setText("Modificar");
                    }else{
                        CustomToast.makeText(getActivity(),"Guardado conéxito",CustomToast.TIPO_RESULT_OK, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    private boolean validateActivity() {
        int validacion = 0;
        String descripcion,fecha, nombre,t_estimado,t_dedicado,unidades;

        descripcion = vh.descripcion.getText().toString().trim();
        fecha = vh.fecha.getText().toString().trim();
        nombre = vh.nombre.getText().toString().trim();
        t_estimado = vh.estimacion.getText().toString().trim();
        t_dedicado = vh.asigacion.getText().toString().trim();
        unidades = vh.unidades.getText().toString().trim();

        validacion += validate(vh.descripcion,descripcion);
        validacion += validate(vh.fecha,fecha);
        validacion += validate(vh.nombre,nombre);
        validacion += validate(vh.estimacion,t_estimado);
        validacion += validate(vh.asigacion,t_dedicado);
        validacion += validate(vh.unidades,unidades);

        if (validacion == 0){
            if (dh.actividad==null){
                dh.actividad = new Actividad();
            }
            dh.actividad.setNombre(nombre);
            dh.actividad.setDescripcion(descripcion);
            dh.actividad.setTiempoEstimado(t_estimado);
            dh.actividad.setTiempoDedicacion(t_dedicado);
            dh.actividad.setFechaInicio(fecha);
            dh.actividad.setUnidades(Integer.parseInt(unidades));
            dh.actividad.setIdClave(dh.selectedKey.getId());
            dh.actividad.setIdProyecto(dh.proyecto.getId());
        }

        return validacion == 0;
    }

    private int validate(TextView v, String text){
        if (text == null ||text.trim().length()<=0){
            v.setError("Campo vacío");
            return 1;
        }
        return 0;
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
                    dh.selectedKey.setId(controller.addClave(dh.selectedKey));
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
        EditText nombre, descripcion, estimacion, asigacion, unidades;
        TextView fecha;
        AutoCompleteTextView claves;
        Button addActivity;
    }

    private static class DataHolder implements Serializable {
        Proyecto proyecto;
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

    public static ActivityFragment newInstance(Actividad actividad, Proyecto proyecto) {
        ActivityFragment fragment = new ActivityFragment();
        Bundle args = new Bundle();
        args.putSerializable(Actividad.class.getName(), actividad);
        args.putSerializable(Proyecto.class.getName(), proyecto);
        fragment.setArguments(args);
        return fragment;
    }

    public ActivityFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dh = new DataHolder();
        if (getArguments() != null) {
            dh.actividad = (Actividad) getArguments().getSerializable(Actividad.class.getName());
            dh.proyecto = (Proyecto) getArguments().getSerializable(Proyecto.class.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity, container, false);
        vh = new ViewHolder();
        vh.claves = (AutoCompleteTextView) view.findViewById(R.id.activities_keys_autocomplete);
        vh.addActivity = (Button) view.findViewById(R.id.activities_keys_add);
        vh.nombre = (EditText) view.findViewById(R.id.activities_keys_name);
        vh.fecha = (TextView) view.findViewById(R.id.activities_keys_date);
        vh.descripcion = (EditText) view.findViewById(R.id.activities_keys_description);
        vh.estimacion = (EditText) view.findViewById(R.id.activities_keys_estimated_time);
        vh.unidades= (EditText) view.findViewById(R.id.activities_keys_units);
        vh.asigacion = (EditText) view.findViewById(R.id.activities_keys_asigned_time);

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

        vh.fecha.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerFragment.newInstance(new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String dateText = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        vh.fecha.setText(dateText);
                        if (ValidateUtil.validateDate(dateText)) {
                            vh.fecha.setText(dateText);
                            dh.actividad.setFechaInicio(dateText);
                            vh.fecha.setError(null);
                        } else {
                            vh.fecha.setError("Seleccione una fecha válida");
                        }
                    }
                }).show(getChildFragmentManager(),DatePickerFragment.class.getSimpleName());
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
