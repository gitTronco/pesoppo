package com.troncodroide.pesoppo.interruptions;

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
import com.troncodroide.pesoppo.beans.Interrupcion;
import com.troncodroide.pesoppo.beans.adapters.InterrupcionesAdapter;
import com.troncodroide.pesoppo.database.controllers.InterrupcionesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link InterruptionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterruptionsFragment extends Fragment {
    private static InterruptionsFragment mFragment;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    private List<Interrupcion> data;

    private Activity mActivity;
    private SqlLiteManager manager;
    private ListView view;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment InterruptionsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static InterruptionsFragment newInstance() {
        if (mFragment==null){
            mFragment = new InterruptionsFragment();
        }
        return mFragment;
    }

    public InterruptionsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = (ListView)inflater.inflate(R.layout.fragment_interruptions, container, false);
        view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Interrupcion i = (Interrupcion)parent.getItemAtPosition(position);
                InterruptionFragment.newInstance(i).show(getChildFragmentManager(),"InterruptionFragment");
            }
        });
        view.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Interrupcion interrupcion = (Interrupcion) parent.getItemAtPosition(position);
                Snackbar.make(parent, "Acciones:", Snackbar.LENGTH_LONG).setAction("borrar", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        InterrupcionesController ic = new InterrupcionesController(manager);
                        try {
                            ic.delInterrupcion(interrupcion);
                            loadInterruptions();
                        } catch (SqlExceptions.IdNotFoundException e) {
                            e.printStackTrace();
                        }
                    }
                }).show();
                return true;
            }
        });

        return view;
    }

    private void loadInterruptions() {
        InterrupcionesController controller = new InterrupcionesController(manager);
        view.setAdapter(new InterrupcionesAdapter(mActivity, controller.getInterrupciones()));

    }

    @Override
    public void onResume() {
        super.onResume();
        loadInterruptions();
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
}
