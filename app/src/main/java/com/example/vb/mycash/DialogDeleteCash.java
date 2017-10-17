package com.example.vb.mycash;

import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * Created by VB on 07.04.2017.
 */

public class DialogDeleteCash extends DialogFragment implements OnClickListener {
    final String LOG_TAG = "myLogs";
    int position;
    String table;
    DataBaseHelper dataBaseHelper;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        dataBaseHelper = new DataBaseHelper(getActivity());
        getDialog().setTitle("Title!");
        View v = inflater.inflate(R.layout.dialog_delete_cash, null);
        v.findViewById(R.id.btnYes).setOnClickListener(this);
        v.findViewById(R.id.btnCancle).setOnClickListener(this);

        savedInstanceState = getArguments();
        String myValue = savedInstanceState.getString("delete_cash");

        String[] myValueTab = myValue.split("--");
        position = Integer.parseInt(myValueTab[0]);
        table = myValueTab[1];
        return v;    }

    public void onClick(View v) {
        dataBaseHelper.deleteCash(position,table);
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

    public void intent(String tables, Integer id) {
        position = id;
        table = tables;
        Log.d("bla1234qwert", id + " " + tables);
    }
}

