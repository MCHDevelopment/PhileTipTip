package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.MeldungsprozessActivity;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class PruefenActivity extends MeldungsprozessActivity {

    private TextView textMeldung;
    private TextView textAdresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pruefen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.pruefen_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        configureButtons();
        configureTextviews();
        fuelleInhalte();
    }

    private void configureTextviews() {
        configureMeldungTextview();
        configureAdressTextview();
    }

    private void configureAdressTextview() {
        textAdresse = findViewById(R.id.text_address);
    }

    private void configureMeldungTextview() {
        textMeldung = findViewById(R.id.text_message);
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
        configureHomeButton(R.id.button_home, MenueActivity.class);
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
                startActivity(new Intent(PruefenActivity.this, AdresseingabeActivity.class));
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
                projektEintragen();

                //TODO: Meldung in Datenbank anlegen
                startActivity(new Intent(PruefenActivity.this, MenueActivity.class));
            }
        });
    }

    private void projektEintragen(){
        //TODO: Meldung in Datenbank anlegen

        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        phileTipTipMain.getMeldungsHolder().addMeldung(phileTipTipMain.getMeldung());
    }

    private void fuelleInhalte(){
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        fuelleMeldungsfeld(phileTipTipMain);
        fuelleAdressfeld(phileTipTipMain);
    }

    private void fuelleAdressfeld(PhileTipTipMain phileTipTipMain) {
        Meldung tempMeldung = phileTipTipMain.getMeldung();
        textAdresse.setText(tempMeldung.getMeldungAdresse().getAdressString());
    }

    private void fuelleMeldungsfeld(PhileTipTipMain phileTipTipMain) {
        Meldung tempMeldung = phileTipTipMain.getMeldung();
        textMeldung.setText(tempMeldung.getMeldungstext());
    }
}