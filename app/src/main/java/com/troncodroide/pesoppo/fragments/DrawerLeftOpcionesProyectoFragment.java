package com.troncodroide.pesoppo.fragments;

import com.troncodroide.pesoppo.proyect.ProyectActivity;
import com.troncodroide.pesoppo.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class DrawerLeftOpcionesProyectoFragment extends Fragment {

	public final static String name = "DrawerLeftOpcionesProyecto";
	private ListView listview;
	private View loading;
	private TextView tittle;
	private Button bottonButton;
	private String tittleString = "";
	private ArrayAdapter<String> adapter;

	private OnClickListener listener;

	public void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.drawer_layout, container, false);

		listview = (ListView) view.findViewById(R.id.left_drawer_list);
		tittle = (TextView) view.findViewById(R.id.textViewTop);
		bottonButton = (Button) view.findViewById(R.id.buttonBotton);
		loading = view.findViewById(R.id.leftdrawer_loading);
		tittle.setText(tittleString);
		if (tittleString.length() == 0) {
			tittle.setVisibility(View.GONE);
		}
		bottonButton.setText("Nueva Tarea");
		bottonButton.setOnClickListener(listener);
		listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO dependiendo de la posiscion muestra una ayuda de cada
				// boton.
				return false;
			}
		});
		listview.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Toast.makeText(getActivity(), "click", 2000).show();
				// TODO abrir la actividad correspondiente al elemento.
			}
		});

		return view;
	}

	public void setArrayAdapter(ArrayAdapter<String> adapter) {
		this.adapter = adapter;
		if (listview != null) {
			listview.setAdapter(adapter);
		}
	}

	public void setOnButtonCLickListener(OnClickListener listener) {
		this.listener = listener;
		if (bottonButton != null)
			bottonButton.setOnClickListener(listener);
	}

	public void setTittle(String tittle) {
		this.tittleString = tittle;
		if (this.tittle != null)
			this.tittle.setText(tittleString);
	}

	@Override
	public void onResume() {
		loading.setVisibility(View.VISIBLE);
		if (adapter != null)
			listview.setAdapter(adapter);
		else {
			// TODO construir el array del menu lateral
		}
		if (listener != null)
			bottonButton.setOnClickListener(listener);
		else {
			listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
					startActivityForResult(new Intent(getActivity(),
							ProyectActivity.class), 1024);
				}
			};
			bottonButton.setOnClickListener(listener);
		}
		loading.setVisibility(View.GONE);
		super.onResume();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}
}
