package com.troncodroide.pesoppo.beans.adapters;

import android.content.Context;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;

import java.util.List;

/**
 * Created by Tronco on 24/06/2015.
 */
public class BasicArrayAdapter<T> extends ArrayAdapter<T> {

    public BasicArrayAdapter(Context context, List objects) {
        super(context, R.layout.pessopo_simple_item_listview, R.id.pesoppo_item_title, objects);
    }

    protected class ViewHolder{
        TextView title,icon,extra;
        View colorwrapper;
    }
}
