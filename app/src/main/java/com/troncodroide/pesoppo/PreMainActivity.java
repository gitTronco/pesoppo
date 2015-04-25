package com.troncodroide.pesoppo;

import com.troncodroide.pesoppo.configuracion.Configuracion;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import android.os.Bundle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

public class PreMainActivity extends ActionBarActivity{

	TextView t1;
	TextView t2;
	TextView t3;
	TextView t4;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pre_main);
		t1 = (TextView) findViewById(R.id.TextView03);
		t2 = (TextView) findViewById(R.id.TextView02);
		t3 = (TextView) findViewById(R.id.TextView01);
		t4 = (TextView) findViewById(R.id.textViewNotificarionsFragment);
		Animation anim1 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fadein);
		Animation anim2 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fadein);
		Animation anim3 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fadein);
		Animation anim4 = AnimationUtils.loadAnimation(getApplicationContext(),
				R.anim.fadein);
		anim1.setStartOffset(500);
		anim2.setStartOffset(1000);
		anim3.setStartOffset(1500);
		anim4.setStartOffset(2000);

		t1.startAnimation(anim1);
		t2.startAnimation(anim2);
		t3.startAnimation(anim3);
		t4.startAnimation(anim4);
		Configuracion.setContext(getApplicationContext());

		anim4.setAnimationListener(new Animation.AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(PreMainActivity.this,
						InicioActivity.class));
				PreMainActivity.this.finish();
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.pre_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//sendPendingNotification();
		return super.onOptionsItemSelected(item);
	}

	private void sendPendingNotification() {
		NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(
				PreMainActivity.this)
				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setLargeIcon(
						(((BitmapDrawable) getResources().getDrawable(
								R.drawable.ic_launcher)).getBitmap()))
				.setContentTitle("Mensaje de Alerta")
				.setContentText("Ejemplo de notificaci0n.").setContentInfo("4")
				.setTicker("Alerta!");
		Intent notIntent = new Intent(PreMainActivity.this,
				PreMainActivity.class);
		PendingIntent contIntent = PendingIntent.getActivity(
				PreMainActivity.this, 0, notIntent, 0);
		mBuilder.setContentIntent(contIntent);
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, mBuilder.build());

	}

}
