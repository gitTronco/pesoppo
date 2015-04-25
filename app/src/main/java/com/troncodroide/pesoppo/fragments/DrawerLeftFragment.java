package com.troncodroide.pesoppo.fragments;

import java.util.LinkedList;
import java.util.List;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Opcion;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class DrawerLeftFragment extends Fragment {

	public final static String name = "DrawerLeft";
	public final static String OPTION_STATUS = "EstatusOption";
	public final static String OPTION_PROJECTS = "ProjectsOption";
	public final static String OPTION_ACTIVITIES = "Activities Option";
	public final static String OPTION_KEYS = "KeysOption";
	public final static String OPTION_INTERRUPTIONS = "InterruptionOption";
	public final static String OPTION_CONFIGURATION = "ConfiguractionOption";
	private ListView listview;
	private EventListener eventListener;
	private View loading;
	private TextView tittle;
	private Button bottonButton;
	private String tittleString = "Proyectos";

	private ArrayAdapter<Opcion> adapter;

	private OnClickListener listener;

    public interface EventListener{
        void onOptionClicked(Opcion op);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        eventListener = (EventListener)activity;
    }

    public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
    }

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.drawer_layout, container, false);

		listview = (ListView) view.findViewById(R.id.left_drawer_list);
		tittle = (TextView) view.findViewById(R.id.textViewTop);
		bottonButton = (Button) view.findViewById(R.id.buttonBotton);
		loading = view.findViewById(R.id.leftdrawer_loading);
		tittle.setText(tittleString);
		bottonButton.setOnClickListener(listener);
		/*listview.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				Proyecto p = (Proyecto)parent.getItemAtPosition(position);
				Intent i = new Intent(getActivity(),ProyectActivity.class);
				i.putExtra("proyecto", p);
				startActivityForResult(i, 1024);
				return false;
			}
		});*/
        Log.i(DrawerLeftFragment.name,"OnCreateView");
        listview.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.i("OnItemClick","Pos:"+i);
                eventListener.onOptionClicked((Opcion)adapterView.getItemAtPosition(i));
            }
        });
		return view;
	}

	@Override
	public void onResume() {
		loading.setVisibility(View.VISIBLE);
        if (adapter!=null)
			listview.setAdapter(adapter);
		else{
            List<Opcion> opciones = new LinkedList<>();
            opciones.add(new Opcion("Mi estado",OPTION_STATUS));
            opciones.add(new Opcion("Actividades",OPTION_ACTIVITIES));
            opciones.add(new Opcion("Claves",OPTION_KEYS));
            opciones.add(new Opcion("Interrupciones",OPTION_INTERRUPTIONS));
            opciones.add(new Opcion("Proyectos",OPTION_PROJECTS));
            adapter = new ArrayAdapter<Opcion>(getActivity(), android.R.layout.simple_list_item_1, opciones);
            listview.setAdapter(adapter);
            listview.setLayoutTransition(new LayoutTransition());
		}
		if (listener!=null)
		    bottonButton.setOnClickListener(listener);
		else{
			listener = new OnClickListener() {
				@Override
				public void onClick(View v) {
                    //TODO start activity conf
                    Log.i("Click","bottonbutton");
                    NotificationsFragment.sendNotificationMessage(getActivity(),new NotificationsFragment.SimpleMessage("Configuracion",NotificationsFragment.Notification_Ok,false,2));
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
