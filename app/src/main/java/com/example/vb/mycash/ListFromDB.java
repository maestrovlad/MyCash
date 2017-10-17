package com.example.vb.mycash;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ListFromDB extends AppCompatActivity {

    DataBaseHelper databesehelper = new DataBaseHelper(this);
    Cash cash = new Cash();
    MainActivity mainActivity = new MainActivity();
    //String[] data_expenses = new String[cash.expenses.size()];
    //String[] data_incomes = new String[cash.incomes.size()];
    //String[] data_all_cash = new String[cash.incomes.size()+cash.expenses.size()];
    //ArrayList<Cash> all_cash = new ArrayList<Cash>();
    DialogFragment dialog;
    Bundle args;
    String messageCat="";

    protected void attachBaseContext(Context context) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(context));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_from_db);
        dialog = new DialogDeleteCash();
        args = new Bundle();
        final ListCashAdapter adapter_incomes = new ListCashAdapter(this, mainActivity.incomes);
        final ListCashAdapter adapter_expenses = new ListCashAdapter(this, mainActivity.expenses);
        //final ListCashAdapter adapter_all_cash = new ListCashAdapter(this, NewList());
        ListView listView = (ListView) findViewById(R.id.list_all_balance);
        final Intent intent = new Intent(this, EditCash.class);
        //final Intent intent_dialog = new Intent(this, DialogDeleteCategorie.class);

        String messageFromFragment = getIntent().getStringExtra("putListFromDB");
        if(messageFromFragment.equals("DayListIncomeFromDB") || messageFromFragment.equals("WeekListIncomeFromDB") || messageFromFragment.equals("MounthListIncomeFromDB") || messageFromFragment.equals("YearListIncomeFromDB"))
        {
            listView.setAdapter(adapter_incomes);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Intent intent = new Intent(view.getContext(), EditCash.class);
                    //intent.putExtra("editCash", mainActivity.incomes.get(position).categoriesCash.categorie + " " + mainActivity.incomes.get(position).sum + " " + mainActivity.incomes.get(position).note + " " + mainActivity.incomes.get(position).date);

                    Log.d("bla125", " " + position);
                   // intent.putExtra("editCash_cat_id", mainActivity.incomes.get(position).categoriesCash.ID);
                    intent.putExtra("editCash_cat", adapter_incomes.getItem(position).categoriesCash.categorie);
                    intent.putExtra("editCash_id", position);
                  //  intent.putExtra("editCash_sum", mainActivity.incomes.get(position).sum);
                   // intent.putExtra("editCash_note", mainActivity.incomes.get(position).note);
                    intent.putExtra("list_message", "Income");
                   // intent.putExtra("editCash_date", mainActivity.incomes.get(position).date);
                    Log.d("bla1234qwerttttt", adapter_incomes.getItem(position).categoriesCash.categorie +
                    "  " + adapter_incomes.getItem(position).id +
                    "  " + "Income");
                    startActivity(intent);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.d("bla1234qwerttttt", adapter_incomes.getItem(position).id + "--" + adapter_incomes.getItem(position).sum + "---");

                    args.putString("delete_cash", adapter_incomes.getItem(position).id + "--" + "Income");
                    dialog.setArguments(args);
                    dialog.show(getFragmentManager(), "TAG");

                    return true;
                }
            });
        }
        else if (messageFromFragment.equals("DayLisExpensesFromDB") || messageFromFragment.equals("WeekLisExpensesFromDB") || messageFromFragment.equals("MounthLisExpensesFromDB") || messageFromFragment.equals("YearLisExpensesFromDB"))
        {
            listView.setAdapter(adapter_expenses);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Intent intent = new Intent(view.getContext(), EditCash.class);
                    //intent.putExtra("editCash", mainActivity.expenses.get(position).categoriesCash.categorie + " " + mainActivity.expenses.get(position).sum + " " + mainActivity.expenses.get(position).note + " " + mainActivity.expenses.get(position).date);
                    Log.d("bla125", " " + position);
                  //  intent.putExtra("editCash_categorie_id", mainActivity.expenses.get(position).categoriesCash.ID);
                    intent.putExtra("editCash_categorie", adapter_expenses.getItem(position).categoriesCash.categorie);
                    intent.putExtra("editCash_id", position);
                  //  intent.putExtra("editCash_sum", mainActivity.expenses.get(position).sum);
                  //  intent.putExtra("editCash_note", mainActivity.expenses.get(position).note);
                    intent.putExtra("list_message", "Expenses");
                    Log.d("bla1234qwerttttt", adapter_expenses.getItem(position).categoriesCash.categorie +
                            "  " + adapter_expenses.getItem(position).id +
                            "  " + "Expenses");
                   // intent.putExtra("editCash_date", mainActivity.expenses.get(position).date);
                    startActivity(intent);
                }
            });
            listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                    args.putString("delete_cash", adapter_expenses.getItem(position).id + "--" + "Expenses");
                    dialog.setArguments(args);
                    dialog.show(getFragmentManager(), "TAG");
                    //Log.d("bla1234qwerttttt", adapter_expenses.getItem(position).id + "--" + adapter_expenses.getItem(position).sum + "---");
                    return true;
                }
            });
        }

        else
        {

        }
    }

}
