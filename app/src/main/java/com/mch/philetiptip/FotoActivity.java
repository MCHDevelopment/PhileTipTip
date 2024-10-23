package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class FotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_foto);

        configureButtons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.foto_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configureButtons(){
        configureHomeButton();
        configureWeiterButton();
        configureFotoButton();
        configureGalleryButton();
        configureZurueckButton();
    }

    private void configureHomeButton(){
        ImageButton buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(FotoActivity.this, PhileMenue.class));
            }
        });
    }

    private void configureFotoButton(){
        ImageButton buttonFoto = findViewById(R.id.button_camera);
        buttonFoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: Foto machen

                // Toast
                Toast.makeText(FotoActivity.this, // Android Context
                                "onClick für Kamera-Button aufgerufen", // Toast-Nachricht
                                Toast.LENGTH_LONG) // Anzeigedauer
                        .show(); // Toast anzeigen
            }
        });
    }

    private void configureGalleryButton(){
        ImageButton buttonGallerie = findViewById(R.id.button_gallery);
        buttonGallerie.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //TODO: Gallerie oeffnen Bild auswaehlen

                // Toast
                Toast.makeText(FotoActivity.this, // Android Context
                                "onClick für Gallerie-Button aufgerufen", // Toast-Nachricht
                                Toast.LENGTH_LONG) // Anzeigedauer
                        .show(); // Toast anzeigen
            }
        });
    }

    private void configureWeiterButton(){
        ImageButton buttonWeiter = findViewById(R.id.button_continue);
        buttonWeiter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(FotoActivity.this, PruefenActivity.class));
            }
        });
    }

    private void configureZurueckButton(){
        ImageButton buttonWeiter = findViewById(R.id.button_back);
        buttonWeiter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(FotoActivity.this, Adresseingabe.class));
            }
        });
    }
}