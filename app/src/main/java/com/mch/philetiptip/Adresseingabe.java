package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Adresseingabe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adresseingabe);

        configureButtons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adresseingabe_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configureButtons(){
        configureHomeButton();
        configureWeiterButton();
        configureGPSButton();
        configureZurueckButton();
    }

    private void configureHomeButton(){
        ImageButton buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Adresseingabe.this, PhileMenue.class));
            }
        });
    }

    private void configureGPSButton(){
        ImageButton buttonGPS = findViewById(R.id.button_gps);
        buttonGPS.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: GPS Daten einholen, Adresse fuellen
            }
        });
    }

    private void configureWeiterButton(){
        ImageButton buttonWeiter = findViewById(R.id.button_continue);
        buttonWeiter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Adresseingabe.this, FotoActivity.class));
            }
        });
    }

    private void configureZurueckButton(){
        ImageButton buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(Adresseingabe.this, NeueMeldung.class));
            }
        });
    }
}