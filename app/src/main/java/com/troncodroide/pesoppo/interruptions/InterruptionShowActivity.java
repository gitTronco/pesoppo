package com.troncodroide.pesoppo.interruptions;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.PercentFormatter;
import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.activities.ActivityFragment;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Interrupcion;
import com.troncodroide.pesoppo.customviews.AwesomeTextView;
import com.troncodroide.pesoppo.customviews.ExpandedListView;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;
import com.troncodroide.pesoppo.util.ValidateUtil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link InterruptionShowActivity#newInstance} factory method to
 * create an instance of this fragment.
 */
public class InterruptionShowActivity extends ActionBarActivity implements OnClickListener{

    private DataHolder dh;
    private ViewHolder vh;
    private SqlLiteManager manager;

    public static final int SHOW_INTERRUPTION_REQUEST = 1320;

    private View parent;

    @Override
    public void onClick(View v) {

    }

    private class ViewHolder {
        PieChart pc;
        AwesomeTextView interrupciones, tiempodedicado,tienmpoestimado;
    }

    private static class DataHolder implements Serializable {
        Interrupcion interrupcion;
    }

    public static void newInstance(Activity act,Interrupcion interrupcion) {
        Intent args = new Intent(act,InterruptionShowActivity.class);
        args.putExtra(Interrupcion.class.getName(), interrupcion);
        act.startActivityForResult(args, SHOW_INTERRUPTION_REQUEST);
    }

    public InterruptionShowActivity() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_activity_show);
        manager = new SqlLiteManager(this);
        dh = new DataHolder();
        if (getIntent() != null) {
            dh.interrupcion = (Interrupcion) getIntent().getSerializableExtra(Interrupcion.class.getName());
        }
        if (getSupportActionBar()!=null) {
            ActionBar ab = getSupportActionBar();
            ab.setDisplayHomeAsUpEnabled(true);
        }
        vh = new ViewHolder();
        vh.pc= (PieChart) findViewById(R.id.chartResumeActivity);

        loadUIData();
    }

    private void loadUIData() {
        if (getSupportActionBar()!=null) {
            ActionBar ab = getSupportActionBar();
            ab.setTitle(dh.interrupcion.getNombre());
        }

        exampleData();

    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        this.parent = super.onCreateView(parent, name, context, attrs);
        return this.parent;
    }

    /*
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Dialog d = super.onCreateDialog(savedInstanceState);
            d.getWindow().requestFeature(Window.FEATURE_NO_TITLE);
            d.getWindow().setBackgroundDrawableResource(android.R.color.darker_gray);
            return d;
        }
    */
    @Override
    public void onResume() {
        super.onResume();

    }

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

        vh.pc.setCenterText(dh.interrupcion.getNombre());

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

        ArrayList<String> xVals = new ArrayList<String>();
        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();


        /*int t_dedicado = ValidateUtil.getValidTime(dh.actividad.getTiempoDedicacion());
        if (dh.actividad.getInterrupciones()!=null && dh.actividad.getInterrupciones().size()>0){
            t_dedicado -= dh.actividad.getInterruptionTime();
        }

        int t_estimado = ValidateUtil.getValidTime(dh.actividad.getTiempoEstimado());
        int diferencia = t_estimado-t_dedicado;
        yVals1.add(new Entry(t_dedicado, 0));
        xVals.add("Dedicado");
        colors.add(getResources().getColor(R.color.verde));
        //yVals1.add(new Entry(t_estimado, 1));
        if (diferencia<0){
            int sobrestimado = Math.abs(diferencia);
            xVals.add("SobreDedicado");
            yVals1.add(new Entry(sobrestimado, 1));
            colors.add(getResources().getColor(R.color.rojo));
        }else{
            xVals.add("Restante");
            yVals1.add(new Entry(diferencia, 1));
            colors.add(getResources().getColor(R.color.azul_claro));
        }

        if (dh.actividad.getInterrupciones()!=null && dh.actividad.getInterrupciones().size()>0){
            yVals1.add(new Entry(dh.actividad.getInterruptionTime(), 2));
            xVals.add("Interrupciones");
            colors.add(getResources().getColor(R.color.naranja));
        }
*/
        PieDataSet dataSet = new PieDataSet(yVals1, "");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);

        dataSet.setColors(colors);

        PieData data = new PieData(xVals, dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(12f);
        data.setValueTextColor(Color.BLACK);
        vh.pc.setData(data);

        // undo all highlights
        vh.pc.highlightValues(null);

        vh.pc.invalidate();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_proyect_show, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_edit) {
            InterruptionFragment.newInstance(dh.interrupcion).show(getSupportFragmentManager(),"dialogInterruptionedit");
            return true;
        }
        if (id == android.R.id.home) {
            onBackPressed();
        }

        return super.onOptionsItemSelected(item);
    }


}
