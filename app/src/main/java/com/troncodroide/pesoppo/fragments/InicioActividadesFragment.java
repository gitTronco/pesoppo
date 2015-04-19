package com.troncodroide.pesoppo.fragments;


import com.troncodroide.pesoppo.R;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class InicioActividadesFragment extends Fragment {
	public static final String name ="InicioActividades";
	private ListView listview;
	private ProgressBar loading;


	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	};

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
