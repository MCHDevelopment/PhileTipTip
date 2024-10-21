package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Menue extends AppCompatActivity {
//https://www.youtube.com/watch?v=6RtF_mbHcEc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menue);

        configureButtons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configureButtons(){
        configureMeldungButton();
        configureUebersichtButton();
    }

    private void configureMeldungButton(){
        Button buttonMeldung = findViewById(R.id.button_melden);
        buttonMeldung.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Menue.this, NeueMeldung.class));
            }
        });
    }

    private void configureUebersichtButton(){
        Button buttonProjekte = findViewById(R.id.button_projekte);
        buttonProjekte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Menue.this, Uebersicht.class));
            }
        });
    }
}