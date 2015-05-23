package com.troncodroide.pesoppo.keys;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Clave;
import com.troncodroide.pesoppo.database.controllers.ClavesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

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

    private static class DataHolder implements Serializable{
        List<Clave> claves;
    }

    public static KeisFragment newInstance() {
        if (mfragment ==null){
            mfragment = new KeisFragment();
            Bundle args = new Bundle();
            mfragment.setArguments(args);
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
            dh = (DataHolder)(savedInstanceState.getSerializable(DataHolder.class.getName()));
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(DataHolder.class.getName(),dh);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        lv = (ListView) inflater.inflate(R.layout.fragment_keis, container, false);
        if (savedInstanceState==null){
            dh =new DataHolder();
            ClavesController controller = new ClavesController(new SqlLiteManager(getActivity()));
            dh.claves = controller.getClaves();
        }

        lv.setAdapter(new ArrayAdapter<>(getActivity(),android.R.layout.simple_list_item_1,dh.claves));

        return lv;
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
