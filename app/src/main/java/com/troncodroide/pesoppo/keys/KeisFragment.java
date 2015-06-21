package com.troncodroide.pesoppo.keys;

import android.app.Activity;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Clave;
import com.troncodroide.pesoppo.beans.Interrupcion;
import com.troncodroide.pesoppo.beans.adapters.ClavesAdapter;
import com.troncodroide.pesoppo.database.controllers.ClavesController;
import com.troncodroide.pesoppo.database.controllers.InterrupcionesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;

import java.io.Serializable;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link KeisFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class KeisFragment extends Fragment {

    private static KeisFragment mfragment;
    private DataHolder dh;

    ListView lv;

    private static class DataHolder implements Serializable {
        List<Clave> claves;
    }

    public static KeisFragment newInstance() {
        if (mfragment == null) {
            mfragment = new KeisFragment();
        }
        return mfragment;
    }

    public KeisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null)
            dh = (DataHolder) (savedInstanceState.getSerializable(DataHolder.class.getName()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DataHolder.class.getName(), dh);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lv = (ListView) inflater.inflate(R.layout.fragment_keis, container, false);
        if (savedInstanceState == null) {
            dh = new DataHolder();
            ClavesController controller = new ClavesController(new SqlLiteManager(getActivity()));
            dh.claves = controller.getClaves();
        }

        lv.setAdapter(new ClavesAdapter(getActivity(), dh.claves));
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Clave clave = (Clave) parent.getItemAtPosition(position);
                Snackbar.make(parent, "Acciones:", Snackbar.LENGTH_LONG).setAction("borrar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClavesController ic = new ClavesController(new SqlLiteManager(getActivity()));
                        try {
                            ic.delClave(clave);
                            loadClaves();
                        } catch (SqlExceptions.IdNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
                return true;
            }
        });

        return lv;
    }

    private void loadClaves() {
        ClavesController controller = new ClavesController(new SqlLiteManager(getActivity()));
        dh.claves = controller.getClaves();
        lv.setAdapter(new ClavesAdapter(getActivity(), dh.claves));

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
