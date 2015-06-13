package com.troncodroide.pesoppo.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Clave;
import com.troncodroide.pesoppo.beans.Proyecto;
import com.troncodroide.pesoppo.customviews.CustomToast;
import com.troncodroide.pesoppo.database.controllers.ActividadesController;
import com.troncodroide.pesoppo.database.controllers.ClavesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.exceptions.SqlExceptions;
import com.troncodroide.pesoppo.fragments.DatePickerFragment;
import com.troncodroide.pesoppo.util.ValidateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Activities that contain this fragment must implement the
 * {@link ActivityShowFragment.OnFragmentActivityListener} interface
 * to handle interaction events.
 * Use the {@link ActivityShowFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActivityShowFragment extends Fragment implements OnClickListener {

    private DataHolder dh;
    private ViewHolder vh;
    private SqlLiteManager manager;

    private OnFragmentActivityListener mListener;

    @Override
    public void onClick(View v) {

    }
    private class ViewHolder {
        PieChart pc;
        EditText nombre, descripcion, estimacion, asigacion, unidades;
        TextView fecha;
        AutoCompleteTextView claves;
        Button addActivity;
        CheckBox terminado;
    }

    private static class DataHolder implements Serializable {
        Actividad actividad;
        List<Clave> claves;
        Clave selectedKey;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param actividad Parameter 1.
     * @return A new instance of fragment ActivitiesFragment.
     */

    public static ActivityShowFragment newInstance(Actividad actividad) {
        ActivityShowFragment fragment = new ActivityShowFragment();
        Bundle args = new Bundle();
        args.putSerializable(Actividad.class.getName(), actividad);
        fragment.setArguments(args);
        return fragment;
    }

    public ActivityShowFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dh = new DataHolder();
        if (getArguments() != null) {
            dh.actividad = (Actividad) getArguments().getSerializable(Actividad.class.getName());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_activity_show, container, false);
        vh = new ViewHolder();
        vh.pc= (PieChart)view.findViewById(R.id.chartResumeActivity);
        exampleData();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected String[] mParties = new String[] {
            "Party A", "Party B", "Party C", "Party D", "Party E", "Party F", "Party G", "Party H",
            "Party I", "Party J", "Party K", "Party L", "Party M", "Party N", "Party O", "Party P",
            "Party Q", "Party R", "Party S", "Party T", "Party U", "Party V", "Party W", "Party X",
            "Party Y", "Party Z"
    };
    private void exampleData(){
        vh.pc.setUsePercentValues(true);
        vh.pc.setDescription("");

        vh.pc.setDragDecelerationFrictionCoef(0.95f);

        vh.pc.setDrawHoleEnabled(true);
        vh.pc.setHoleColorTransparent(true);

        vh.pc.setTransparentCircleColor(Color.WHITE);

        vh.pc.setHoleRadius(58f);
        vh.pc.setTransparentCircleRadius(61f);

        vh.pc.setDrawCenterText(true);

        vh.pc.setRotationAngle(0);
        // enable rotation of the chart by touch
        vh.pc.setRotationEnabled(true);

        // mChart.setUnit(" €");
        // mChart.setDrawUnitsInChart(true);

        // add a selection listener
        //vh.pc.setOnChartValueSelectedListener(this);

        vh.pc.setCenterText(dh.actividad.getNombre());

        setData();

        vh.pc.animateY(1500, Easing.EasingOption.EaseInOutQuad);
        // mChart.spin(2000, 0, 360);

        Legend l = vh.pc.getLegend();
        l.setPosition(Legend.LegendPosition.RIGHT_OF_CHART);
        l.setXEntrySpace(7f);
        l.setYEntrySpace(0f);
        l.setYOffset(0f);
    }

    private void setData() {

        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        yVals1.add(new Entry(ValidateUtil.getValidTime(dh.actividad.getTiempoDedicacion()), 0));
        yVals1.add(new Entry(ValidateUtil.getValidTime(dh.actividad.getTiempoEstimado()), 1));

        ArrayList<String> xVals = new ArrayList<String>();

        xVals.add(dh.actividad.getTiempoDedicacion());
        xVals.add(dh.actividad.getTiempoEstimado());

        if (dh.actividad.getInterrupciones()!=null && dh.actividad.getInterrupciones().size()>0){
            String intString = "20M";
            yVals1.add(new Entry(1300, 2));
            xVals.add(intString);
        }

        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<Integer>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.WHITE);
        vh.pc.setData(data);

        // undo all highlights
        vh.pc.highlightValues(null);

        vh.pc.invalidate();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        manager = new SqlLiteManager(activity);
        /*try {
            mListener = (OnFragmentActivityListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentActivityListener {
        public void onActivityCreated(Actividad actividad);

        public void onActivityUpdate(Actividad actividad);
    }

}
