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

import com.mch.philetiptip.Logic.PhileTipTipMain;

public class PhileMenue extends AppCompatActivity {
//https://www.youtube.com/watch?v=6RtF_mbHcEc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_menue);

        configureButtons();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.menu_main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configureButtons(){
        configureMeldungButton();
        configureUebersichtButton();
        configureImpressumButton();
        configureKontaktButton();
        configureDatenschutzButton();
    }

    private void configureMeldungButton(){
        Button buttonMeldung = findViewById(R.id.button_meldung_machen);
        buttonMeldung.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                starteMeldungsProzess();
                startActivity(new Intent(PhileMenue.this, NeueMeldung.class));
            }
        });
    }

    private void starteMeldungsProzess(){
        PhileTipTipMain phileTipTipMain = (PhileTipTipMain) getApplication();
        phileTipTipMain.CreateMeldung();
    }

    private void configureUebersichtButton(){
        Button buttonProjekte = findViewById(R.id.button_meldungen_einsehen);
        buttonProjekte.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PhileMenue.this, Uebersicht.class));
            }
        });
    }

    private void configureImpressumButton(){
        Button buttonImpressum = findViewById(R.id.button_impressum);
        buttonImpressum.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PhileMenue.this, ImpressumActivity.class));
            }
        });
    }

    private void configureKontaktButton(){
        Button buttonKontakt = findViewById(R.id.button_kontakt);
        buttonKontakt.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PhileMenue.this, KontaktActivity.class));
            }
        });
    }


    private void configureDatenschutzButton(){
        Button buttonDatenschutz = findViewById(R.id.button_datenschutz);
        buttonDatenschutz.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(PhileMenue.this, DatenschutzActivity.class));
            }
        });
    }
}