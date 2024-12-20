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

public class MenueActivity extends AppCompatActivity {
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
        buttonMeldung.setOnClickListener(v -> {
                startActivity(new Intent(MenueActivity.this, MeldungMainActivity.class));
        });
    }

    private void configureUebersichtButton(){
        Button buttonProjekte = findViewById(R.id.button_meldungen_einsehen);
        buttonProjekte.setOnClickListener(v -> {
                startActivity(new Intent(MenueActivity.this, UebersichtActivity.class));
        });
    }

    private void configureImpressumButton(){
        Button buttonImpressum = findViewById(R.id.button_impressum);
        buttonImpressum.setOnClickListener(v -> {
                startActivity(new Intent(MenueActivity.this, ImpressumActivity.class));
        });
    }

    private void configureKontaktButton(){
        Button buttonKontakt = findViewById(R.id.button_kontakt);
        buttonKontakt.setOnClickListener(v -> {
                startActivity(new Intent(MenueActivity.this, KontaktActivity.class));
        });
    }


    private void configureDatenschutzButton(){
        Button buttonDatenschutz = findViewById(R.id.button_datenschutz);
        buttonDatenschutz.setOnClickListener(v -> {
                startActivity(new Intent(MenueActivity.this, DatenschutzActivity.class));
        });
    }
}