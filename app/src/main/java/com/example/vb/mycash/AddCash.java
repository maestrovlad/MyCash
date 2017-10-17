package com.example.vb.mycash;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddCash extends AppCompatActivity {

    EditText add_number_of_cash;
    EditText add_note_of_cash;
    int cat_id = 0;
    String cat_name;
    DataBaseHelper databesehelper = new DataBaseHelper(this);
    Cash cash = new Cash();
    String oneDD="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cash);
        add_number_of_cash = (EditText) findViewById(R.id.add_number_of_cash);
        add_note_of_cash = (EditText) findViewById(R.id.add_note_of_cash);
        oneDD = getIntent().getStringExtra("onedaydate");
        AdapterSpinner();

    }

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }

    public void CashAdd(View view) throws ParseException {
        float cash = Float.parseFloat(add_number_of_cash.getText().toString());
        String note = add_note_of_cash.getText().toString();
        if(oneDD == null)
        {
            String date;
            Date date1 = new Date();
            SimpleDateFormat dt = new SimpleDateFormat("d-w-M-yyyy");
            date = dt.format(date1);
            oneDD = date;
        }
        Log.d("qrrr3444swypeee", oneDD);
        databesehelper.addIncome(new Cash(new CategoriesCash(cat_id, cat_name),0,cash,note,oneDD));

        finish();

    }

    //адаптер под спинер
    public void AdapterSpinner()
    {
        final MainActivity mainActivity = new MainActivity();
        String[] data = new String[mainActivity.categoriesIncomes.size()];
        for(int i=0; i<data.length;i++)
        {
            data[i] = mainActivity.categoriesIncomes.get(i).categorie;
        }
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.categories_list);
        spinner.setAdapter(adapter);

        spinner.setSelection(0);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
               // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                cat_id = position;
                cat_name = mainActivity.categoriesIncomes.get(cat_id).categorie;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }
}
