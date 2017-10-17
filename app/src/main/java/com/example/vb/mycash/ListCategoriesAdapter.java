package com.example.vb.mycash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by VB on 14.03.2017.
 */

public class ListCategoriesAdapter extends ArrayAdapter<CategoriesCash> {
    public ListCategoriesAdapter(Context context, ArrayList<CategoriesCash> categorieInc) {
        super(context, 0, categorieInc);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        CategoriesCash categoriesCash = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list_categories, parent, false);
        }
        // Lookup view for data population
        TextView name = (TextView) convertView.findViewById(R.id.name_from_categories);
        // Populate the data into the template view using the data object
        name.setText(categoriesCash.categorie);
        // Return the completed view to render on screen
        return convertView;
    }
}
