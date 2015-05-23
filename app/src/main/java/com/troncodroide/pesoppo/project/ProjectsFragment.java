package com.troncodroide.pesoppo.project;

import android.animation.LayoutTransition;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.controllers.ProyectosController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import java.io.Serializable;
import java.util.List;

public class ProjectsFragment extends Fragment {
	public static final String name ="Proyectos";
    private static ProjectsFragment mFragment;
    DataHolder dataHolder;
	ViewHolder viewHolder;

    public static ProjectsFragment newInstance() {
        if (mFragment==null){
            mFragment = new ProjectsFragment();
        }
        return mFragment;
    }

    private static class DataHolder implements Serializable{
        List<Proyecto> proyectos;
        int position;
    }

    private class ViewHolder{
        private ListView listview;
        private ProgressBar loading;
        private Button addProyect;
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
            dataHolder = (DataHolder)savedInstanceState.getSerializable(DataHolder.class.getName());
        }
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_inicio_proyectos,
				container, false);
        viewHolder = getViewHolder();
		viewHolder.loading = (ProgressBar) view
				.findViewById(R.id.progressBarNotificationsFragment);
        viewHolder.listview = (ListView) view.findViewById(R.id.listViewProyectos);
        viewHolder.addProyect = (Button) view.findViewById(R.id.add_new_project);

        if (savedInstanceState==null){
            dataHolder = getDataHolder();
            if(dataHolder.proyectos == null ){
                ProyectosController controller = new ProyectosController(new SqlLiteManager(getActivity()));
                dataHolder.proyectos = controller.getProyectos();
            }
        }
        viewHolder.listview.setAdapter(new ProjectsAdapter(getActivity(), dataHolder.proyectos));
        viewHolder.listview.setScrollY(dataHolder.position);
        viewHolder.listview.setLayoutTransition(new LayoutTransition());
        viewHolder.listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Proyecto p = (Proyecto)adapterView.getItemAtPosition(i);
                Intent intent = new Intent(getActivity(),ProjectShowActivity.class);
                intent.putExtra(Proyecto.class.getName(),p);
                startActivity(intent);
            }
        });

        viewHolder.addProyect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ProjectActivity.class);
                startActivityForResult(intent, 232);
            }
        });

		return view;
	}

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        dataHolder.position = viewHolder.listview.getScrollY();
        outState.putSerializable(DataHolder.class.getName(),dataHolder);
    }

    @Override
	public void onDestroy() {
		super.onDestroy();
	}

    private class ProjectsAdapter extends ArrayAdapter<Proyecto> {

        public ProjectsAdapter(Context context, List<Proyecto> proyectos) {
            super(context, android.R.layout.simple_list_item_1, proyectos);
        }

        class ViewHolder{
            LinearLayout wrapperResume, colorwrapper;
            TextView activities,lastactivity,criticalactivity,titulo;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            Proyecto p = getItem(position);
            final ViewHolder vh;
            if (p.getActividades() == null || p.getActividades().size() == 0){
                ActividadesController ac = new ActividadesController(new SqlLiteManager(getContext()));
                List<Actividad> actividads = ac.getActividades(p.getId());
                p.setActividades(actividads);
            }
            if (convertView == null){
                vh = new ViewHolder();
                convertView = LayoutInflater.from(getActivity()).inflate(R.layout.pessopo_item_listview, null);
                vh.activities = (TextView)convertView.findViewById(R.id.proyecto_item_activities);
                vh.colorwrapper = (LinearLayout)convertView.findViewById(R.id.proyecto_item_color);
                vh.criticalactivity = (TextView)convertView.findViewById(R.id.proyecto_item_critical_activity);
                vh.lastactivity = (TextView)convertView.findViewById(R.id.proyecto_item_last_activity);
                vh.wrapperResume = (LinearLayout)convertView.findViewById(R.id.proyecto_item_resume_wrapper);
                vh.titulo = (TextView)convertView.findViewById(R.id.proyecto_item_title);
                convertView.setTag(vh);
            }else{
                vh = (ViewHolder)convertView.getTag();
            }
            vh.activities.setText(Integer.toString(p.getActividades().size()));
            vh.titulo.setText(getItem(position).getNombre());

            vh.colorwrapper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (vh.wrapperResume.getVisibility()==View.GONE){
                        vh.wrapperResume.setVisibility(View.VISIBLE);
                    }else{
                        vh.wrapperResume.setVisibility(View.GONE);
                    }
                }
            });

            return convertView;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 232){
            if (resultCode == ProjectActivity.RESULT_PROJECT_OK){
                ProyectosController controller = new ProyectosController(new SqlLiteManager(getActivity()));
                dataHolder.proyectos = controller.getProyectos();
                viewHolder.listview.setAdapter(new ProjectsAdapter(getActivity(), dataHolder.proyectos));
            }

        }
    }
}
