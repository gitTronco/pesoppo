package com.troncodroide.pesoppo.beans.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Actividad;

import java.util.List;

/**
 * Created by Tronco on 21/06/2015.
 */
public class ActividadesAdapter extends BasicArrayAdapter<Actividad> {

    public ActividadesAdapter(Context context, List<Actividad> objects) {
        super(context, objects);
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
        Actividad item = getItem(position);
        vh.icon.setText(R.string.fa_tasks);
        vh.extra.setText(item.getTiempoDedicacion());
        return convertView;
    }
}
