package com.mch.philetiptip;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.MeldungsprozessActivity;
import com.mch.philetiptip.Logic.Meldungsschirm;

public class MeldungMainActivity extends MeldungsprozessActivity {

    private MeldungDateneingabeFragment dateneingabeFragment;
    private AdresseingabeFragment adresseingabeFragment;
    private FotoFragment fotoFragment;
    private PruefenFragment pruefenFragment;

    private ImageButton buttonZurueck;
    private ImageButton buttonWeiter;

    private Meldungsschirm currentSchirm = Meldungsschirm.ArtUndText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meldung_main);

        configureButtons();

        //Fuer das erste Fragment findet kein Aufruf der Schirmwechsel Funktion statt, deswegen muss
        //setzeButtonSichtbarkeit hier losgetreten werden
        currentSchirm = Meldungsschirm.ArtUndText;
        setzeButtonSichtbarkeit();

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

    private void setzeButtonSichtbarkeit() {
        switch (currentSchirm){
            case ArtUndText:
                zurueckButtonAusblenden();
                weiterButtonEinblenden();
                break;

            case Adresse:
            case Foto:
                zurueckButtonEinblenden();
                weiterButtonEinblenden();
                break;

            case Pruefen:
                zurueckButtonEinblenden();
                weiterButtonAusblenden();
                break;
        }
    }

    private void zurueckButtonAusblenden()
    {
        buttonZurueck.setVisibility(View.GONE);
    }

    private void zurueckButtonEinblenden()
    {
        buttonZurueck.setVisibility(View.VISIBLE);
    }

    private void weiterButtonAusblenden()
    {
        buttonWeiter.setVisibility(View.GONE);
    }

    private void weiterButtonEinblenden()
    {
        buttonWeiter.setVisibility(View.VISIBLE);
    }

    private void configureWeiterButton() {
        buttonWeiter = findViewById(R.id.button_continue);
        buttonWeiter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechselZumNaechstenSchirm();
            }
        });
    }

    private void wechselZumNaechstenSchirm(){
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
                configureWeiterButton();
                currentSchirm = Meldungsschirm.Pruefen;
                break;
            case Pruefen:
                break;
        }

        schirmWechsel(tempFragment);
    }

    private void configureZurueckButton() {
        buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                wechselZumVorherigemSchirm();
            }
        });
    }

    private void wechselZumVorherigemSchirm(){
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

        setzeButtonSichtbarkeit();
    }

    private void configureHomeButton() {
        configureHomeButton(R.id.button_home, MenueActivity.class);
    }
}
