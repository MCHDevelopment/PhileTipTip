package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mch.philetiptip.Logic.Adresse;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class Adresseingabe extends AppCompatActivity {

    private EditText editTextOrt;
    private EditText editTextPostleitzahl;
    private EditText editTextHausnummer;
    private EditText editTextStrasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adresseingabe);

        configureButtons();
        configureTextFields();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adresseingabe_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configureTextFields() {
        configureStrasse();
        configureHausnummer();
        configurePostleitzahl();
        configureOrt();
    }

    private void configureOrt() {
        editTextOrt = findViewById(R.id.city_input);
    }

    private void configurePostleitzahl() {
        editTextPostleitzahl = findViewById(R.id.postal_code_input);
    }

    private void configureHausnummer() {
        editTextHausnummer = findViewById(R.id.house_number_input);
    }

    private void configureStrasse() {
        editTextStrasse = findViewById(R.id.street_input);
    }

    private void configureButtons() {
        configureHomeButton();
        configureWeiterButton();
        configureGPSButton();
        configureZurueckButton();
    }

    private void configureHomeButton() {
        ImageButton buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adresseingabe.this, PhileMenue.class));
            }
        });
    }

    private void configureGPSButton() {
        ImageButton buttonGPS = findViewById(R.id.button_gps);
        buttonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: GPS Daten einholen, Adresse fuellen

                // Toast
                Toast.makeText(Adresseingabe.this, // Android Context
                                "onClick für GPS-Button aufgerufen", // Toast-Nachricht
                                Toast.LENGTH_LONG) // Anzeigedauer
                        .show(); // Toast anzeigen
            }
        });
    }

    private void configureWeiterButton() {
        ImageButton buttonWeiter = findViewById(R.id.button_continue);
        buttonWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fuelleAdresse();
                startActivity(new Intent(Adresseingabe.this, FotoActivity.class));
            }
        });
    }

    private void fuelleAdresse(){
        Adresse adresse = new Adresse();
        adresse.setStrasse(editTextStrasse.getText().toString());
        //TODO: Parsen und Validieren der Eingabe
        adresse.setHausNummer(42);//editTextHausnummer.getText().toString());
        adresse.setPostleitzahl(65611);//editTextHausnummer.getText().toString());

        PhileTipTipMain phileTipTipMain = (PhileTipTipMain) getApplicationContext();
        phileTipTipMain.getMeldung().setMeldungsAdresse(adresse);
    }


    private void configureZurueckButton() {
        ImageButton buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Adresseingabe.this, NeueMeldung.class));
            }
        });
    }
}