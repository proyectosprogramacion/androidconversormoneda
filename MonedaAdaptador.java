package com.example.aplicacion5_.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

//Importamos nuestras clases que vamos a utilizar
import com.example.aplicacion5_.R;
import com.example.aplicacion5_.models.Moneda;

import java.util.List;

public class MonedaAdaptador extends ArrayAdapter<Moneda> {
    Context context;

    private class ViewHolder {
        TextView name;
        TextView rate;

        private ViewHolder() {
        }
    }

    public MonedaAdaptador(Context context, List<Moneda> items) {
        super(context, 0, items);
        this.context = context;
    }

    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        final Moneda rowItem = (Moneda) getItem(position);
        LayoutInflater mInflater = (LayoutInflater) this.context.getSystemService(context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.moneda_item, null);
            holder = new ViewHolder();
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.rate = (TextView) convertView.findViewById(R.id.rate);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(rowItem.name);
        //No me acepta que rate sea Double ?????
        holder.rate.setText(rowItem.rate);
        return convertView;
    }
}
