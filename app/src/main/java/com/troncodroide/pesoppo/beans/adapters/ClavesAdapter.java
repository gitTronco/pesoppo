package com.troncodroide.pesoppo.beans.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.troncodroide.pesoppo.R;
import com.troncodroide.pesoppo.beans.Clave;
import com.troncodroide.pesoppo.beans.Interrupcion;
import com.troncodroide.pesoppo.database.controllers.ClavesController;
import com.troncodroide.pesoppo.database.sql.SqlLiteManager;

import java.util.List;

/**
 * Created by Tronco on 21/06/2015.
 */
public class ClavesAdapter extends BasicArrayAdapter<Clave> {

    SqlLiteManager manager;
    ClavesController cc;

    public ClavesAdapter(Context context, List<Clave> objects) {
        super(context, objects);
        manager =new SqlLiteManager(context);
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
        Clave item = getItem(position);
        vh.icon.setText(R.string.fa_tag);
        cc = new ClavesController(manager);
        String stimated = cc.getEstimatedTime(item);
        vh.extra.setText(stimated);
        return convertView;
    }
}
