package com.troncodroide.pesoppo.interruptions;

import android.app.Activity;
import android.app.Dialog;
import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Interrupcion;
import com.troncodroide.pesoppo.database.controllers.InterrupcionesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;
import com.troncodroide.pesoppo.util.ValidateUtil;

public class InterruptionFragment extends DialogFragment implements View.OnClickListener {
    private Interrupcion data;
    private ViewHolder vh;

    private OnInterruptionsEventListener mListener;
    private SqlLiteManager manager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InterruptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterruptionFragment newInstance(Interrupcion interrupcion) {
        InterruptionFragment fragment = new InterruptionFragment();
        Bundle b = new Bundle();
        b.putSerializable(Interrupcion.class.getName(), interrupcion);
        fragment.setArguments(b);
        return fragment;
    }

    public InterruptionFragment() {
        // Required empty public constructor
    }

    @Override
    public void onClick(View v) {

        if (validateInterruption()) {
            InterrupcionesController controller = new InterrupcionesController(manager);
            try {
                if (data.getId() == 0) {
                    controller.addInterrupcion(data);
                    mListener.onInterruptionUpdated();
                } else {
                    controller.saveInterrupcion(data);
                    mListener.onInterruptionUpdated();
                }
                dismiss();
            } catch (SqlExceptions.IdNotFoundException e) {
                e.printStackTrace();
            } catch (SqlExceptions.DuplicatedIdException e) {
                e.printStackTrace();
            } catch (SqlExceptions.UniqueKeyException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean validateInterruption() {

        String name = vh.nombre.getText().toString().trim();
        String descripcion = vh.descripcion.getText().toString().trim();
        String time = vh.tiempo.getText().toString().trim();

        int validate = 0;

        validate += validate(vh.nombre, name);
        validate += validate(vh.descripcion, descripcion);
        validate += validateTime(vh.tiempo, time);

        if (validate == 0) {
            data.setNombre(name);
            data.setDescripcion(descripcion);
            data.setTiempo(time);
        }

        return validate == 0;
    }

    private int validate(TextView v, String text) {
        if (text == null || text.trim().length() <= 0) {
            v.setError("Campo vacío");
            return 1;
        }
        return 0;
    }

    private int validateTime(TextView v, String text) {
        if (text == null || text.trim().length() <= 0) {
            v.setError("Campo vacío");
            return 1;
        }
        if (!ValidateUtil.isValidTime(text)) {
            v.setError("Formato de fecha incorrecto, 1h 20m, 30m, 2h");
            return 1;
        }
        return 0;
    }


    private class ViewHolder {
        EditText nombre, descripcion, tiempo;
        Button add;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            data = (Interrupcion) getArguments().getSerializable(Interrupcion.class.getName());
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        return d;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_interruption, container, false);
        vh = new ViewHolder();
        vh.nombre = (EditText) v.findViewById(R.id.interruption_name);
        vh.descripcion = (EditText) v.findViewById(R.id.interruption_description);
        vh.tiempo = (EditText) v.findViewById(R.id.interruption_time);
        vh.add = (Button) v.findViewById(R.id.interruption_add);
        vh.add.setOnClickListener(this);
        if (data.getId() != 0) {
            vh.nombre.setText(data.getNombre());
            vh.descripcion.setText(data.getDescripcion());
            vh.tiempo.setText(data.getTiempo());
            vh.add.setText("Modificar");
        } else {
            vh.add.setText("Añadir");
        }
        return v;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        manager = new SqlLiteManager(activity);
        try {
            mListener = (OnInterruptionsEventListener) activity;
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

    public interface OnInterruptionsEventListener {
        void onInterruptionUpdated();
    }

}
