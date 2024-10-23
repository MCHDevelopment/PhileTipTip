package com.mch.philetiptip;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.MeldungsprozessActivity;
import com.mch.philetiptip.Logic.Meldungsschirm;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class MeldungMainActivity extends MeldungsprozessActivity {
    private MeldungDateneingabeFragment dateneingabeFragment;
    private AdresseingabeFragment adresseingabeFragment;
    private FotoFragment fotoFragment;
    private PruefenFragment pruefenFragment;

    private Meldungsschirm currentSchirm = Meldungsschirm.ArtUndText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meldung_main);

        configureButtons();

        // Fragmente einmalig erstellen
        dateneingabeFragment = new MeldungDateneingabeFragment();
        adresseingabeFragment = new AdresseingabeFragment();
        fotoFragment = new FotoFragment();
        pruefenFragment = new PruefenFragment();

        // Initiales Fragment laden
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, dateneingabeFragment)
                    .commit();
        }
    }

    private void configureButtons() {
        configureHomeButton();
        configureWeiterButton();
        configureZurueckButton();
    }

    private void configureWeiterButton() {
        ImageButton buttonWeiter = findViewById(R.id.button_continue);
        buttonWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextSchirm();
            }
        });
    }

    private void nextSchirm(){
        Fragment tempFragment = null;
        switch (currentSchirm){
            case ArtUndText:
                dateneingabeFragment.fuelleMeldung();
                tempFragment = adresseingabeFragment;
                currentSchirm = Meldungsschirm.Adresse;
                break;
            case Adresse:
                adresseingabeFragment.fuelleAdresse();
                tempFragment = fotoFragment;
                currentSchirm = Meldungsschirm.Foto;
                break;
            case Foto:
                tempFragment = pruefenFragment;
                currentSchirm = Meldungsschirm.Pruefen;
                break;
            case Pruefen:
                break;
        }

        schirmWechsel(tempFragment);
    }

    private void configureZurueckButton() {
        ImageButton buttonWeiter = findViewById(R.id.button_back);
        buttonWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                previousSchirm();
            }
        });
    }

    private void previousSchirm(){
        Fragment tempFragment = null;
        switch (currentSchirm){
            case ArtUndText:
                break;
            case Adresse:
                tempFragment = dateneingabeFragment;
                currentSchirm = Meldungsschirm.ArtUndText;
                break;
            case Foto:
                tempFragment = adresseingabeFragment;
                currentSchirm = Meldungsschirm.Adresse;
                break;
            case Pruefen:
                tempFragment = fotoFragment;
                currentSchirm = Meldungsschirm.Foto;
                break;
        }

        schirmWechsel(tempFragment);
    }

    private void schirmWechsel(Fragment fragment){
        // Fragment-Wechsel mit gespeicherten Fragmenten
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void configureHomeButton() {
        configureHomeButton(R.id.button_home, MenueActivity.class);
    }
}
