package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Adresse;
import com.mch.philetiptip.Logic.Helper;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class AdresseingabeFragment extends Fragment {

    private EditText editTextOrt;
    private EditText editTextPostleitzahl;
    private EditText editTextHausnummer;
    private EditText editTextStrasse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate das Fragment-Layout
        View view = inflater.inflate(R.layout.fragment_adresseingabe, container, false);

        configureButtons(view);
        configureTextFields(view);

        return view;
    }

    void configureTextFields(View view) {
        configureStrasse(view);
        configureHausnummer(view);
        configurePostleitzahl(view);
        configureOrt(view);
    }

    private void configureOrt(View view) {
        editTextOrt = view.findViewById(R.id.city_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        String tempOrtText = phileTipTipMain.getMeldung().getMeldungAdresse().getOrt();
        if(tempOrtText.isEmpty() || tempOrtText.equals("keine Eingabe"))
        {
            editTextOrt.setText("");
            editTextOrt.setHint("Ort");
        }else{
            editTextOrt.setText(tempOrtText);
        }
    }

    private void configurePostleitzahl(View view) {
        editTextPostleitzahl = view.findViewById(R.id.postal_code_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        int tempPostleitzahl = phileTipTipMain.getMeldung().getMeldungAdresse().getPostleitzahl();
        if(tempPostleitzahl <= 0)
        {
            editTextPostleitzahl.setText("");
            editTextPostleitzahl.setHint("Postleitzahl");
        }else{
            editTextOrt.setText(tempPostleitzahl);
        }
    }

    private void configureHausnummer(View view) {
        editTextHausnummer = view.findViewById(R.id.house_number_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        int tempHausnummer = phileTipTipMain.getMeldung().getMeldungAdresse().getHausNummer();
        if(tempHausnummer <= 0)
        {
            editTextHausnummer.setText("");
            editTextHausnummer.setHint("Hausnummer");
        }else{
            editTextHausnummer.setText(tempHausnummer);
        }
    }

    private void configureStrasse(View view) {
        editTextStrasse = view.findViewById(R.id.street_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        String tempStrasseText = phileTipTipMain.getMeldung().getMeldungAdresse().getStrasse();
        if(tempStrasseText.isEmpty() || tempStrasseText.equals("keine Eingabe"))
        {
            editTextStrasse.setText("");
            editTextStrasse.setHint("Strasse");
        }else{
            editTextStrasse.setText(tempStrasseText);
        }
    }

    private void configureButtons(View view) {
        configureGPSButton(view);
    }

    private void configureGPSButton(View view) {
        ImageButton buttonGPS = view.findViewById(R.id.button_gps);
        buttonGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: GPS Daten einholen, Adresse fuellen

                // Toast
                /*
                Toast.makeText(AdresseingabeFragment.this, // Android Context
                                "onClick für GPS-Button aufgerufen", // Toast-Nachricht
                                Toast.LENGTH_LONG) // Anzeigedauer
                        .show(); // Toast anzeigen
                 */
            }
        });
    }

    public void fuelleAdresse(){
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

        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        phileTipTipMain.getMeldung().setMeldungsAdresse(adresse);
    }
}