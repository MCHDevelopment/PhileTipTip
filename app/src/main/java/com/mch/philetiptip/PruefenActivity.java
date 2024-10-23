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

public class PruefenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pruefen);

        configureButtons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pruefen_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configureButtons(){
        configureHomeButton();
        configureZurueckButton();
        configureMeldungEditButton();
        configureImageEditButton();
        configureAdressEditButton();
        configureSubmitButton();
    }

    private void configureHomeButton(){
        ImageButton buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PruefenActivity.this, PhileMenue.class));
            }
        });
    }

    private void configureMeldungEditButton(){
        ImageButton buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PruefenActivity.this, FotoActivity.class));
            }
        });
    }

    private void configureZurueckButton(){
        ImageButton buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PruefenActivity.this, FotoActivity.class));
            }
        });
    }

    private void configureAdressEditButton(){
        ImageButton buttonAdressEdit = findViewById(R.id.button_edit_address);
        buttonAdressEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PruefenActivity.this, Adresseingabe.class));
            }
        });
    }

    private void configureImageEditButton(){
        ImageButton buttonImageEdit = findViewById(R.id.button_edit_image);
        buttonImageEdit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PruefenActivity.this, FotoActivity.class));
            }
        });
    }

    private void configureSubmitButton(){
        ImageButton buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: Projekt anlegen
                startActivity(new Intent(PruefenActivity.this, PhileMenue.class));
            }
        });
    }
}