package com.troncodroide.pesoppo.fragments;

import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import com.troncodroide.pesoppo.R;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class NotificationsFragment extends Fragment {

	private LinearLayout content;
	private TextView text;
	private ProgressBar loading;
	private Timer timer = new Timer();

	public static class SimpleMessage implements Serializable {
		private static final long serialVersionUID = 1L;
		public String text;
		public int type;
		public boolean persistence;
		public int seconds;
		public SimpleMessage(String text, int type, boolean persistence, int seconds) {
			super();
			this.text = text;
			this.type = type;
			this.persistence = persistence;
			this.seconds = seconds;
		}
	}

	public static final String data = "dataObject";
	public static final String intentFilter = "com.troncodroide.pesoppo.notifications";
	public static final int Notification_Error = 0;
	public static final int Notification_Ok = 1;
	public static final int Notification_Loading = 2;
	public static final int Notification_Warning = 3;
	public static final int Notification_Close = 4;

	BroadcastReceiver notificationsReceiver = new BroadcastReceiver() {

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			Toast.makeText(context, "Notification received", Toast.LENGTH_SHORT).show();
			SimpleMessage message = (SimpleMessage) intent.getSerializableExtra(data);
			text.setText(message.text);
			if (!message.persistence) {
				timer.schedule(new TimerTask() {
					@Override
					public void run() {
						handler.sendEmptyMessage(Notification_Close);
					}
				}, message.seconds * 1000);
			}
			typeProcedure(message.type);
		}
	};

	private void typeProcedure(int tipo) {
		try{
		Animation anim1 = AnimationUtils.loadAnimation(getActivity(),
				R.anim.fadeinnotification);
		switch (tipo) {
		case Notification_Error:
			loading.setVisibility(View.GONE);
			content.setBackgroundColor(getResources().getColor(
					R.color.rojo_claro));
			break;
		case Notification_Ok:
			loading.setVisibility(View.GONE);
			content.setBackgroundColor(getResources().getColor(
					R.color.verde_claro));

			break;
		case Notification_Loading:
			loading.setVisibility(View.VISIBLE);
			content.setBackgroundColor(getResources().getColor(
					R.color.azul_claro));
			break;
		case Notification_Warning:
			loading.setVisibility(View.GONE);
			content.setBackgroundColor(getResources().getColor(
					R.color.naranja_claro));

			break;
		case Notification_Close:
			anim1 = AnimationUtils.loadAnimation(getActivity(), R.anim.fadeout);
			break;

		default:
			break;
		}
		if (content.getVisibility() == View.GONE) {
			content.startAnimation(anim1);
			content.setVisibility(View.VISIBLE);
		} else {
			if (tipo == Notification_Close) {
				anim1.setAnimationListener(new AnimationListener() {

					@Override
					public void onAnimationStart(Animation animation) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onAnimationRepeat(Animation animation) {
						// TODO Auto-generated method stub
					}

					@Override
					public void onAnimationEnd(Animation animation) {
						content.setVisibility(View.GONE);

					}
				});
				content.startAnimation(anim1);
			} else {
				anim1.setAnimationListener(null);
			}
		}}catch (Exception ex){
			
		}
	}

    public static void sendNotificationMessage(Activity act,SimpleMessage message){
        Intent intent = new Intent();
        intent.setAction(NotificationsFragment.intentFilter);
        intent.putExtra(data,message);
        act.sendBroadcast(intent);
    }

	public void onCreate(Bundle savedInstanceState) {
		IntentFilter mainFilter = new IntentFilter(
				NotificationsFragment.intentFilter);
		getActivity().registerReceiver(notificationsReceiver, mainFilter);
		Log.i("NotificationFragment", "Create");
		super.onCreate(savedInstanceState);
	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("NotificationFragment", "CreateView");
		View view = inflater.inflate(R.layout.notifications_fragment_layout,
				container, false);

		text = (TextView) view.findViewById(R.id.textViewNotificationsFragment);
		content = (LinearLayout) view
				.findViewById(R.id.viewNotificationsContent);
		loading = (ProgressBar) view
				.findViewById(R.id.progressBarNotificationsFragment);
		content.setVisibility(View.GONE);
		return view;
	}

	@Override
	public void onDestroy() {
		Log.i("NotificationFragment", "Destroy");
		getActivity().unregisterReceiver(notificationsReceiver);
		super.onDestroy();
	}

	Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			typeProcedure(msg.what);
			super.handleMessage(msg);
		}
	};

}
