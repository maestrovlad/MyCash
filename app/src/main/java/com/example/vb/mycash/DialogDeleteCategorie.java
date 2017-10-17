package com.example.vb.mycash;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

/**
 * Created by VB on 21.03.2017.
 */

public class DialogDeleteCategorie extends DialogFragment implements OnClickListener{
    final String LOG_TAG = "myLogs";
    int position;
    String table;
    String column;
    DataBaseHelper dataBaseHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBaseHelper = new DataBaseHelper(getActivity());
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_delete_categorie, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.btnCancle).setOnClickListener(this);

        savedInstanceState = getArguments();
        String myValue = savedInstanceState.getString("delete_categories");

        String[] myValueTab = myValue.split("--");
        position = Integer.parseInt(myValueTab[0]);
        table = myValueTab[1];
        column = myValueTab[2];
        return v;    }

    public void onClick(View v) {
        dataBaseHelper.deleteCategorie(position,table,column);
        dismiss();
    }

    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        Log.d("bla1234qwert", "Dialog 1: onDismiss");
    }

    public void onCancel(DialogInterface dialog) {
        super.onCancel(dialog);
        Log.d("bla1234qwert", "Dialog 1: onCancel");
    }
}
