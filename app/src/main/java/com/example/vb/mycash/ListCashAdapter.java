package com.example.vb.mycash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VB on 17.03.2017.
 */

public class ListCashAdapter extends ArrayAdapter<Cash> {
    public ListCashAdapter(Context context, ArrayList<Cash> cash) {
        super(context, 0, cash);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Cash cash = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_cash, parent, false);
        }
        // Lookup view for data population
        TextView categorie = (TextView) convertView.findViewById(R.id.name_categorie_list_categories);
        TextView cashing = (TextView) convertView.findViewById(R.id.cash_list_categories);
        TextView note = (TextView) convertView.findViewById(R.id.note_list_categories);
        TextView date = (TextView) convertView.findViewById(R.id.date_list_categories);
        // Populate the data into the template view using the data object
        categorie.setText(String.valueOf(cash.categoriesCash.categorie));
        cashing.setText(String.valueOf(cash.sum));
        note.setText(cash.note);
        date.setText(cash.date);

        // Return the completed view to render on screen
        return convertView;
    }
}
