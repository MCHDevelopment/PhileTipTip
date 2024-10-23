package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mch.philetiptip.Logic.Adresse;
import com.mch.philetiptip.Logic.Helper;
import com.mch.philetiptip.Logic.MeldungsprozessActivity;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class AdresseingabeActivity extends MeldungsprozessActivity {

    private EditText editTextOrt;
    private EditText editTextPostleitzahl;
    private EditText editTextHausnummer;
    private EditText editTextStrasse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_adresseingabe);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.adresseingabe_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        configureButtons();
        configureTextFields();
    }

    private void configureTextFields() {
        configureStrasse();
        configureHausnummer();
        configurePostleitzahl();
        configureOrt();
    }

    private void configureOrt() {
        editTextOrt = findViewById(R.id.city_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = (PhileTipTipMain) getApplicationContext();
        String tempOrtText = phileTipTipMain.getMeldung().getMeldungAdresse().getOrt();
        if(tempOrtText.isEmpty())
        {
            editTextOrt.setText("");
            editTextOrt.setHint("Ort");
        }else{
            editTextOrt.setText(tempOrtText);
        }
    }

    private void configurePostleitzahl() {
        editTextPostleitzahl = findViewById(R.id.postal_code_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = (PhileTipTipMain) getApplicationContext();
        int tempPostleitzahl = phileTipTipMain.getMeldung().getMeldungAdresse().getPostleitzahl();
        if(tempPostleitzahl <= 0)
        {
            editTextPostleitzahl.setText("");
            editTextPostleitzahl.setHint("Postleitzahl");
        }else{
            editTextOrt.setText(tempPostleitzahl);
        }
    }

    private void configureHausnummer() {
        editTextHausnummer = findViewById(R.id.house_number_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = (PhileTipTipMain) getApplicationContext();
        int tempHausnummer = phileTipTipMain.getMeldung().getMeldungAdresse().getHausNummer();
        if(tempHausnummer <= 0)
        {
            editTextHausnummer.setText("");
            editTextHausnummer.setHint("Hausnummer");
        }else{
            editTextHausnummer.setText(tempHausnummer);
        }
    }

    private void configureStrasse() {
        editTextStrasse = findViewById(R.id.street_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = (PhileTipTipMain) getApplicationContext();
        String tempStrasseText = phileTipTipMain.getMeldung().getMeldungAdresse().getStrasse();
        if(tempStrasseText.isEmpty())
        {
            editTextStrasse.setText("");
            editTextStrasse.setHint("Strasse");
        }else{
            editTextStrasse.setText(tempStrasseText);
        }
    }

    private void configureButtons() {
        configureHomeButton();
        configureWeiterButton();
        configureGPSButton();
        configureZurueckButton();
    }

    private void configureHomeButton(){
        configureHomeButton(R.id.button_home, MenueActivity.class);
    }

    private void configureGPSButton() {
        ImageButton buttonGPS = findViewById(R.id.button_gps);
        buttonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: GPS Daten einholen, Adresse fuellen

                // Toast
                Toast.makeText(AdresseingabeActivity.this, // Android Context
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
                startActivity(new Intent(AdresseingabeActivity.this, FotoActivity.class));
            }
        });
    }

    private void fuelleAdresse(){
        Adresse adresse = new Adresse();
        adresse.setStrasse(editTextStrasse.getText().toString());
        //TODO: Parsen und Validieren der Eingabe

        Integer tempHausnummer = Helper.parseIntOrNull(editTextHausnummer.getText().toString());
        if(tempHausnummer != null){
            adresse.setHausNummer(tempHausnummer);
        }

        Integer tempPostleitzahl = Helper.parseIntOrNull(editTextPostleitzahl.getText().toString());
        if(tempPostleitzahl != null){
            //TODO: Validieren der Eingabe - fuenfstellig
            adresse.setPostleitzahl(tempPostleitzahl);
        }

        adresse.setOrt(editTextOrt.getText().toString());

        PhileTipTipMain phileTipTipMain = (PhileTipTipMain) getApplicationContext();
        phileTipTipMain.getMeldung().setMeldungsAdresse(adresse);
    }


    private void configureZurueckButton() {
        ImageButton buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdresseingabeActivity.this, MeldungActivity.class));
            }
        });
    }
}