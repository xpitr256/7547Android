package com.example.tallerdyp2.client;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tallerdyp2.client.Entities.City;

import java.util.List;

/**
 * Created by Sebastian on 14/3/2017.
 */

public class CitiesAdapter extends ArrayAdapter<City> {
    public CitiesAdapter(Context context, List<City> cities) {
        super(context, 0, cities);
    }

    private char contInicial = 'A';

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Obtener inflater.
        LayoutInflater inflater = (LayoutInflater) getContext()
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        ViewHolder holder;

        // ¿Ya se infló este view?
        if (null == convertView) {
            //Si no existe, entonces inflarlo con image_list_view.xml
            convertView = inflater.inflate(
                    R.layout.cities_item_list,
                    parent,
                    false);

            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Client actual.
        String name = getItem(position).getName();
        contInicial++;

        holder.name.setText(name);

        return convertView;
    }

    static class ViewHolder {
        TextView name;
    }
}
