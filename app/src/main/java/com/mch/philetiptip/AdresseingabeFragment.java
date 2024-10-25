package com.mch.philetiptip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Data.Adresse;
import com.mch.philetiptip.Logic.Helper;
import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class AdresseingabeFragment extends Fragment {

    private EditText editTextOrt;
    private EditText editTextPostleitzahl;
    private EditText editTextHausnummer;
    private EditText editTextStrasse;

    private Meldung meldung;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate das Fragment-Layout
        View view = inflater.inflate(R.layout.fragment_adresseingabe, container, false);

        // Meldung aus dem Bundle holen
        if (getArguments() != null) {
            meldung = (Meldung) getArguments().getSerializable("meldung");
        }

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
    }

    private void configurePostleitzahl(View view) {
        editTextPostleitzahl = view.findViewById(R.id.postal_code_input);
    }

    private void configureHausnummer(View view) {
        editTextHausnummer = view.findViewById(R.id.house_number_input);
    }

    private void configureStrasse(View view) {
        editTextStrasse = view.findViewById(R.id.street_input);
    }

    private void configureButtons(View view) {
        configureGPSButton(view);
    }

    private void configureGPSButton(View view) {
        ImageButton buttonGPS = view.findViewById(R.id.button_gps);
        buttonGPS.setOnClickListener(v ->  {
                //TODO: GPS Daten einholen, Adresse fuellen

                // Toast
                /*
                Toast.makeText(AdresseingabeFragment.this, // Android Context
                                "onClick für GPS-Button aufgerufen", // Toast-Nachricht
                                Toast.LENGTH_LONG) // Anzeigedauer
                        .show(); // Toast anzeigen
                 */
        });
    }

    public void fuelleAdresse(){
        Adresse adresse = new Adresse();
        adresse.setStrasse(editTextStrasse.getText().toString());
        //TODO: Validieren der Eingabe

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

        meldung.setMeldungsAdresse(adresse);
    }
}