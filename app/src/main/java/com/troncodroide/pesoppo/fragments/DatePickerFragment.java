package com.troncodroide.pesoppo.fragments;

import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment{

	private OnDateSetListener listener;
	private Date time;

	public static DatePickerFragment newInstance(OnDateSetListener listener){
		DatePickerFragment df = new DatePickerFragment();
		df.setOnDateSetListener(listener);
		return df;
	}
	public static DatePickerFragment newInstance(Date time,OnDateSetListener listener){
		DatePickerFragment df = new DatePickerFragment();
		df.setTime(time);
		df.setOnDateSetListener(listener);
		return df;
	}
	public void setTime(Date time){
		this.time = time;
	}
	public void setOnDateSetListener(OnDateSetListener listener){
		this.listener = listener;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		if (time!=null)
			c.setTime(time);
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), listener, year, month, day);
	}
}
