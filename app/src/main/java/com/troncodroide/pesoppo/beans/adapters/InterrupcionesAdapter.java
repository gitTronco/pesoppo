package com.troncodroide.pesoppo.beans.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;
import com.troncodroide.pesoppo.beans.Interrupcion;

import java.util.List;

/**
 * Created by Tronco on 21/06/2015.
 */
public class InterrupcionesAdapter extends ArrayAdapter<Interrupcion> {

    public InterrupcionesAdapter(Context context, List<Interrupcion> objects) {
        super(context, R.layout.pessopo_simple_item_listview, R.id.pesoppo_item_title, objects);
    }

    private class ViewHolder{
        TextView title,icon,extra;
        View colorwrapper;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = super.getView(position, convertView, parent);
        final ViewHolder vh;
        if (convertView == null){
            vh = new ViewHolder();
            convertView = v;
            vh.title = (TextView)convertView.findViewById(R.id.pesoppo_item_title);
            vh.extra= (TextView)convertView.findViewById(R.id.pesoppo_item_extra);
            vh.icon = (TextView)convertView.findViewById(R.id.pesoppo_item_icon);
            vh.colorwrapper = convertView.findViewById(R.id.pesoppo_item_color);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        Interrupcion item = getItem(position);
        vh.icon.setText(R.string.fa_coffee);
        vh.extra.setText(item.getTiempo());
        return convertView;
    }
}
