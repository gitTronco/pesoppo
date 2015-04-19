package com.troncodroide.pesoppo.util;


import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.graphics.Typeface;
import android.text.Editable;
import android.text.Html;
import android.text.Spanned;
import android.text.TextWatcher;
import android.widget.TextView;
import com.troncodroide.pesoppo.customviews.*;

public class TextUtil {
	public static final int CURSIVA = 0;
	public static final int SUBRAYAR = 1;
	public static final int JUSTIFICAR = 2;
	public static final int TAB = 3;
	public static final int STRING = 0;
	public static final int REAL = 1;
	public static final int REAL_POSITIVE = 2;
	public static final int DATE = 3;
	public static final int NUMBER = 4;
	public static final int NUMBER_POSITIVE = 5;

	public static boolean isEmpty(String string) {
		boolean toRet = false;
		if (string != null){
			String aux = string.trim();
			if (aux.length() == 0) {
				toRet = true;
			}
		}else{
			toRet=true;
		}
		return toRet;
	}

	public static Typeface getTitillium(Context context) {
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/titillium.ttf");
		return tf;
	}

	public static Typeface getHelvetica(Context context) {
		Typeface tf = Typeface.createFromAsset(context.getAssets(),
				"fonts/helvetica.ttf");
		return tf;
	}
	
	public static String decimalFormat(double number){	
		return decimalFormat(number,"#0.00");
	}
	
	public static String decimalFormat(double number, String pattern){	
		DecimalFormatSymbols simbolos = new DecimalFormatSymbols();
		simbolos.setDecimalSeparator('.');
		return decimalFormat(number, pattern, simbolos);
	}
	
	public static String decimalFormat(double number, String pattern, DecimalFormatSymbols simbols){
		DecimalFormat df = new DecimalFormat(pattern,simbols);
		return df.format(number);
	}

	public static Spanned applyFormat(String text, int style) {

		String formattedText = "";
		switch (style) {
		case CURSIVA:
			formattedText = "<i>" + text + "</i>";
			break;
		case SUBRAYAR:
			formattedText = "<u>" + text + "</u>";
			break;
		case JUSTIFICAR:
			formattedText = "<span style=\"text-align:justify\">" + text
					+ "</span>";
			break;
		case TAB:
			formattedText = "<h3>" + text + "</h3>";
			break;
		default:
			break;
		}
		Spanned result = Html.fromHtml(formattedText);
		return result;
	}

	public static Spanned getList(List<String> lista) {

		String formattedText = "";
		for (String string : lista) {
			formattedText += string + "<br />";

		}
		Spanned result = Html.fromHtml(formattedText);
		return result;
	}

	public static class TextValidator implements TextWatcher {
		private TextView view;
		private Activity activity;
		private int tipo = -1;

		private String before;

		public TextValidator(TextView v, Activity act, int tipo) {
			view = v;
			activity = act;
			this.tipo = tipo;
		}

		@Override
		public void afterTextChanged(Editable s) {
			//Log.i("TEXTWATCHER_DESPUES", s.toString());
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			before = s.toString();
			//Log.i("TEXTWATCHER_ANTES", s.toString());
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int beforeInt,
				int count) {
			//Log.i("TEXTWATCHER_CAMBIO", s.toString() + " start:" + start
			//		+ " before:" + before + " count:" + count);
			
			
			switch (tipo) {
			case DATE:
				//Log.i("TEXTWATCHER", "DATE");
				break;
			case STRING:
				//Log.i("TEXTWATCHER", "STRING");
				break;
			
			case REAL:
				try {
					//Log.i("TEXTWATCHER", "REAL:" + before + ":" + s);
					if (TextUtil.isEmpty(s.toString())){
						s="0"; 
					}
					if (!isFloat(s.toString())) {
						CustomToast.makeText(activity,
								"Dato no vlido, introduzca un numero vlido",
								CustomToast.TIPO_ADVERTENCIA, 3000).show();
						view.setText(before);
					}

				} catch (Exception e) {

				}
				break;
			
			case REAL_POSITIVE:
				try {
					//Log.i("TEXTWATCHER", "REAL_POSITIVE:" + before + ":" + s);
					if (TextUtil.isEmpty(s.toString())){
						s="0";
					}
					if (isFloat(s.toString())) {
						float f = Float.parseFloat(s.toString());

						if (f < 0) {
							CustomToast
									.makeText(
											activity,
											"Dato no vlido, introduzca un numero positivo",
											CustomToast.TIPO_ADVERTENCIA, 3000)
									.show();
							view.setText(before);
						}
					} else {
						CustomToast.makeText(activity,
								"Dato no vlido, introduzca un numero vlido",
								CustomToast.TIPO_ADVERTENCIA, 3000).show();
						view.setText(before);
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			case NUMBER:
				try {
					//Log.i("TEXTWATCHER", "NUMBER:" + before + ":" + s);
					if (TextUtil.isEmpty(s.toString())){
						s="0";
					}
					if (!isNumber(s.toString())) {
						CustomToast
						.makeText(
								activity,
								"Dato no vlido, introduzca un numero vlido",
								CustomToast.TIPO_ADVERTENCIA, 3000)
						.show();
						view.setText(before);
					}

				} catch (Exception e) {
							
				}
				break;

			case NUMBER_POSITIVE:
				try {
					//Log.i("TEXTWATCHER", "NUMBER_POSITIVE:" + before + ":" + s);
					if (TextUtil.isEmpty(s.toString())){
						s="0";
					}
					if (isNumber(s.toString())) {
						int i = Integer.parseInt(s.toString());

						if (i < 0) {
							CustomToast
									.makeText(
											activity,
											"Dato no vlido, introduzca un numero positivo",
											CustomToast.TIPO_ADVERTENCIA, 3000)
									.show();
							view.setText(before);
						}
					} else {
						CustomToast.makeText(activity,
								"Dato no vlido, introduzca un numero vlido",
								CustomToast.TIPO_ADVERTENCIA, 3000).show();
						view.setText(before);
					}

				} catch (Exception e) {

				}
				break;
			default:
				break;
			}

		}

		private boolean isFloat(String floatString) {
			boolean toRet = true;
			try {
				//Log.i("PARSE_FLOAT", floatString);
				Float.parseFloat(floatString);
			} catch (NumberFormatException e) {
				toRet = false;
			}

			return toRet;
		}

		private boolean isNumber(String numberString) {
			boolean toRet = true;
			try {
				//Log.i("PARSE_NUMBER", numberString);
				Integer.parseInt(numberString);
			} catch (NumberFormatException e) {
				toRet = false;
			}

			return toRet;
		}

	}
}
