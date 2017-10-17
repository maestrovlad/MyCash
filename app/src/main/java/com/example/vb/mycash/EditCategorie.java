package com.example.vb.mycash;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import org.w3c.dom.Text;

public class EditCategorie extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_categorie);
        String messageFromFragment = getIntent().getStringExtra("Id_and_Categorie");
        TextView othertext = (TextView) findViewById(R.id.othertext);
        othertext.setText(messageFromFragment);
    }
}
