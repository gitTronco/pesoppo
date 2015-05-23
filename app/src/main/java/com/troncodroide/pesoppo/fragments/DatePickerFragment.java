package com.troncodroide.pesoppo.fragments;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;

public class DatePickerFragment extends DialogFragment{

	private OnDateSetListener listener;

	public static DatePickerFragment newInstance(OnDateSetListener listener){
		DatePickerFragment df = new DatePickerFragment();
		df.setOnDateSetListener(listener);
		return df;
	}

	public void setOnDateSetListener(OnDateSetListener listener){
		this.listener = listener;
	}
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		final Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH);
		int day = c.get(Calendar.DAY_OF_MONTH);

		return new DatePickerDialog(getActivity(), listener, year, month, day);
	}
}
