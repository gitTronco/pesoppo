package com.troncodroide.pesoppo.fragments;


import android.animation.LayoutTransition;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.database.controllers.ProyectosController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import java.io.Serializable;
import java.util.List;

public class ProyectosFragment extends Fragment {
	public static final String name ="Proyectos";
	DataHolder dataHolder;
	ViewHolder viewHolder;

    private class DataHolder implements Serializable{
        List<Proyecto> proyectos;
        int position;
    }

    private class ViewHolder{
        private ListView listview;
        private ProgressBar loading;
    }

    private DataHolder getDataHolder(){
        return (dataHolder==null)?new DataHolder():dataHolder;
    }

    private ViewHolder getViewHolder(){
        return (viewHolder==null)?new ViewHolder():viewHolder;
    }

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            dataHolder = (DataHolder)savedInstanceState.getSerializable("data");
        }
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_inicio_proyectos,
				container, false);
        viewHolder = getViewHolder();
        dataHolder = getDataHolder();
		viewHolder.loading = (ProgressBar) view
				.findViewById(R.id.progressBarNotificationsFragment);
        viewHolder.listview = (ListView) view
				.findViewById(R.id.listViewProyectos);

		return view;
	}

    @Override
    public void onResume() {
        super.onResume();
        dataHolder = getDataHolder();
        if(dataHolder.proyectos == null ){
            ProyectosController controller = new ProyectosController(new SqlLiteManager(getActivity()));
            List<Proyecto> proyectos = controller.getProyectos();
			viewHolder = getViewHolder();
            viewHolder.listview.setAdapter(new ProjectsAdapter(getActivity(), proyectos));
            viewHolder.listview.setLayoutTransition(new LayoutTransition());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("data",dataHolder);
    }

    @Override
	public void onDestroy() {
		super.onDestroy();
	}

    private class ProjectsAdapter extends ArrayAdapter<Proyecto> {

        public ProjectsAdapter(Context context, List<Proyecto> proyectos) {
            super(context, android.R.layout.simple_list_item_1, proyectos);
        }


        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.pessopo_item_listview, null);
            }
            Button button= (Button)convertView.findViewById(R.id.button_text);
            button.setText(getItem(position).getNombre());
            return convertView;
        }
    }
}
