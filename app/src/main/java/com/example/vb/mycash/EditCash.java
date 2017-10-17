package com.example.vb.mycash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Objects;

public class EditCash extends AppCompatActivity {

    EditText add_number_of_cash;
    EditText add_note_of_cash;
    int catID;
    int position_in_list;
    int cashID;
    String catName;
    float cashSum;
    String cashNote;
    String list_mes;
    int defaultValue = 0;
    DataBaseHelper databesehelper = new DataBaseHelper(this);
    Cash cash = new Cash();
    ArrayList<CategoriesCash> list_cat = new ArrayList<CategoriesCash>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_cash);
        MainActivity mainActivity = new MainActivity();
        add_number_of_cash = (EditText) findViewById(R.id.add_number_of_cash);
        add_note_of_cash = (EditText) findViewById(R.id.add_note_of_cash);
        position_in_list = getIntent().getIntExtra("editCash_id",defaultValue);
        catName = getIntent().getStringExtra("editCash_categorie");
        list_mes = getIntent().getStringExtra("list_message");
        Log.d("bla1234qwerttttt", cashID + "--" + catName + "--" + list_mes);
        if(list_mes.equals("Income"))
        {
            catID = mainActivity.incomes.get(position_in_list).categoriesCash.ID;
            cashID = mainActivity.incomes.get(position_in_list).id;
            cashSum = mainActivity.incomes.get(position_in_list).sum;
            cashNote = mainActivity.incomes.get(position_in_list).note;
            Log.d("bla1234qwert", catID + "<>" + catName + "<>" + cashSum + "<>" + cashNote);
        }
        else if(list_mes.equals("Expenses"))
        {
            catID = mainActivity.expenses.get(position_in_list).categoriesCash.ID;
            cashID = mainActivity.expenses.get(position_in_list).id;
            cashSum = mainActivity.expenses.get(position_in_list).sum;
            cashNote = mainActivity.expenses.get(position_in_list).note;
            Log.d("bla1234qwert", catID + "<>" + catName + "<>" + cashSum + "<>" + cashNote);
        }
        else
        {

        }
        add_number_of_cash.setCursorVisible(false);
        add_note_of_cash.setText(cashNote);
        /*add_number_of_cash.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if (!hasFocus) {
                    String userInput = "";

                    if (TextUtils.isEmpty(userInput)) {
                        userInput = "0.00";
                    } else {
                        float floatValue = cashSum;
                        userInput = String.format("%.2f",floatValue);
                    }

                    add_number_of_cash.setText(userInput);
                }
            }
        });*/
        add_number_of_cash.setText(String.valueOf(cashSum));
        Log.d("bla1234qwert", add_number_of_cash.getText().toString());
        AdapterSpinner();

    }

    public void CashEdit(View view) throws ParseException {
        cashSum = Float.parseFloat(add_number_of_cash.getText().toString());
        cashNote = add_note_of_cash.getText().toString();
        databesehelper.editCash(new Cash(new CategoriesCash(catID, catName),cashID,cashSum,cashNote,"none"), list_mes);
        finish();

    }

    //адаптер под спинер
    public void AdapterSpinner()
    {
        final MainActivity mainActivity = new MainActivity();
        if(list_mes.equals("Income"))
        {
            list_cat = mainActivity.categoriesIncomes;
        }
        else if(list_mes.equals("Expenses"))
        {
            list_cat = mainActivity.categoriesExpenses;
        }
        else
        {

        }
        String[] data = new String[list_cat.size()];
        for(int i=0; i<data.length;i++)
        {
            data[i] = list_cat.get(i).categorie;
        }
        // адаптер
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = (Spinner) findViewById(R.id.categories_list);
        spinner.setAdapter(adapter);

        spinner.setSelection(catID);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int position, long id) {
                // Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                catID = position;
                catName = list_cat.get(catID).categorie;
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });
    }

}
