package com.troncodroide.pesoppo.status;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Opcion;
import com.troncodroide.pesoppo.customviews.AwesomeTextView;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.controllers.ClavesController;
import com.troncodroide.pesoppo.database.controllers.InterrupcionesController;
import com.troncodroide.pesoppo.database.controllers.ProyectosController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import java.util.LinkedList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link StatusFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatusFragment extends Fragment {

    private static StatusFragment mFragment;
    private OnStatusFragmentListener mListener;
    private SqlLiteManager manager;
    private Activity mActivity;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment StatusFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatusFragment newInstance() {
        if (mFragment == null)
            mFragment = new StatusFragment();
        return mFragment;
    }

    public StatusFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        GridView v = (GridView) inflater.inflate(R.layout.fragment_status, container, false);

        ProyectosController pc = new ProyectosController(manager);
        ActividadesController ac = new ActividadesController(manager);
        InterrupcionesController ic = new InterrupcionesController(manager);
        ClavesController cc = new ClavesController(manager);

        List<StatusItem> items = new LinkedList<>();

        int numP = pc.getProyectos().size();
        int numA = ac.getActividades().size();
        int numI = ic.getInterrupciones().size();
        int numC = cc.getClaves().size();

        items.add(new StatusItem(numP,"Proyectos",R.string.fa_cubes, Opcion.OPTION_PROJECTS));
        items.add(new StatusItem(numA,"Actividades",R.string.fa_tasks, Opcion.OPTION_ACTIVITIES));
        items.add(new StatusItem(numC,"Claves",R.string.fa_tags, Opcion.OPTION_KEYS));
        items.add(new StatusItem(numI,"Interrupciones",R.string.fa_coffee, Opcion.OPTION_INTERRUPTIONS));

        v.setAdapter(new StatusesAdapter(mActivity, items));

        v.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                mListener.onItemSelected((StatusItem)parent.getItemAtPosition(position));
            }
        });

        return v;
    }

    public void onItemPressed(StatusItem item) {
        if (mListener != null) {
            mListener.onItemSelected(item);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mActivity = activity;
        manager = new SqlLiteManager(activity);
        try {
            mListener = (OnStatusFragmentListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnStatusFragmentListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnStatusFragmentListener {
        // TODO: Update argument type and name
        public void onItemSelected(StatusItem item);
    }

    private class StatusesAdapter extends ArrayAdapter<StatusItem> {

        public StatusesAdapter(Context context, List<StatusItem> objects) {
            super(context, R.layout.status_item_grid, R.id.option_text, objects);
        }

        private class ViewHolder{
            AwesomeTextView icon;
            TextView title,num;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View v =  super.getView(position, convertView, parent);
            ViewHolder vh;
            if (convertView == null){
                convertView = v;
                vh = new ViewHolder();
                vh.icon = (AwesomeTextView)v.findViewById(R.id.option_icon);
                vh.title = (TextView) v.findViewById(R.id.option_text);
                vh.num = (TextView)v.findViewById(R.id.option_num);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder)v.getTag();
            }
            StatusItem item = getItem(position);

            vh.num.setText(Integer.toString(item.getNum()));
            vh.title.setText(item.getLabel());
            vh.icon.setText(item.getResIcon());

            return convertView;
        }
    }

    public static class StatusItem {
        int num;
        String label;
        int resIcon;
        String type;

        public StatusItem(int num, String label, int resIcon, String type) {
            this.num = num;
            this.label = label;
            this.resIcon = resIcon;
            this.type = type;
        }

        public int getNum() {
            return num;
        }

        public String getLabel() {
            return label;
        }

        public int getResIcon() {
            return resIcon;
        }

        public String getType() {
            return type;
        }
    }

}
