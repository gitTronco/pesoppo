package com.troncodroide.pesoppo.customviews;

import com.troncodroide.pesoppo.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class CustomToast{

	public static final int TIPO_ADVERTENCIA = 0;
	public static final int TIPO_ERROR = 1;
	public static final int TIPO_RESULT_OK = 2;
	public static final int TIPO_RESULT_NO = 3;
	/**
	 * Toast Personalizado
	 * */

	public static Toast makeText(Context context, String text, int tipo, int duration){
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.layout_custom_toast, null);
		((TextView)view.findViewById(R.id.custom_toast_text)).setText(text);
		ImageView iv = ((ImageView)view.findViewById(R.id.custom_toast_image));
		LinearLayout ll = ((LinearLayout)view.findViewById(R.id.custom_toast_background));
		switch (tipo) {
		case TIPO_ADVERTENCIA:
			iv.setImageResource(R.drawable.toasttickinfo);
			ll.setBackgroundResource(R.color.naranja_claro);
			break;
		case TIPO_RESULT_OK:
			iv.setImageResource(R.drawable.toasttickok);
			ll.setBackgroundResource(R.color.verde_claro);
			break;
		case TIPO_RESULT_NO:
			iv.setImageResource(R.drawable.toasttickblock);
			ll.setBackgroundResource(R.color.rojo_claro);
			break;
		case TIPO_ERROR:
			iv.setImageResource(R.drawable.toasttickblock);
			ll.setBackgroundResource(R.color.rojo);
			break;

		default:
			break;
		}
		Toast t = Toast.makeText(context, text, duration);
		t.setView(view);
		return t;
	}

}
