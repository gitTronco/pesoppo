package com.troncodroide.pesoppo.fragments;


import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ActividadesProyectoFragment extends Fragment {
	public static final String name ="ActividadesProyecto";
	private TextView headerProyecto;
	private ListView listview;
	private ProgressBar loading;
	private ArrayAdapter<Actividad> adaptador;

	public void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
	};
	
	@Override
	public void setArguments(Bundle args) {
		// TODO Auto-generated method stub
		super.setArguments(args);
	}
	
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_inicio_tareas,
				container, false);
		
		loading = (ProgressBar) view
				.findViewById(R.id.progressBarNotificationsFragment);
		listview = (ListView) view
				.findViewById(R.id.listViewActividades);

		return view;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
