package com.mch.philetiptip;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.MeldungsprozessActivity;
import com.mch.philetiptip.Logic.Meldungsschirm;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class MeldungMainActivity extends MeldungsprozessActivity {

    private MeldungDateneingabeFragment dateneingabeFragment;
    private AdresseingabeFragment adresseingabeFragment;
    private FotoFragment fotoFragment;
    private PruefenFragment pruefenFragment;

    private ImageButton buttonZurueck;
    private ImageButton buttonWeiter;
    private ImageButton buttonSubmit;

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
        configureSubmitButton();
    }

    private void setzeButtonSichtbarkeit() {
        switch (currentSchirm){
            case ArtUndText:
                zurueckButtonAusblenden();
                weiterButtonEinblenden();
                submitButtonAusblenden();
                break;

            case Adresse:
            case Foto:
                zurueckButtonEinblenden();
                weiterButtonEinblenden();
                submitButtonAusblenden();
                break;

            case Pruefen:
                zurueckButtonEinblenden();
                weiterButtonAusblenden();
                submitButtonEinblenden();
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

    private void submitButtonAusblenden(){
        buttonSubmit.setVisibility(View.GONE);
    }

    private void submitButtonEinblenden(){
        buttonSubmit.setVisibility(View.VISIBLE);
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

    private void configureSubmitButton(){
        buttonSubmit = findViewById(R.id.button_submit);
        buttonSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                projektEintragen();
                //TODO: Meldung in Datenbank anlegen
                //TODO: Neue Methode zum Schirmwechsel
            }
        });
    }

    private void projektEintragen(){
        //TODO: Meldung in Datenbank anlegen

        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        phileTipTipMain.getMeldungsHolder().addMeldung(phileTipTipMain.getMeldung());

        ProgressBar progressBar;
        final int[] progressStatus = {0};
        Handler handler = new Handler();

        // ProgressBar erstellen
        progressBar = new ProgressBar(this);
        progressBar.setIndeterminate(false);  // für echten Fortschritt
        progressBar.setMax(100);  // Maximaler Wert (100%)

        // AlertDialog erstellen
        AlertDialog progressDialog = new AlertDialog.Builder(this)
                .setTitle("Lädt...")
                .setView(progressBar)  // ProgressBar im Dialog anzeigen
                .setCancelable(false)
                .create();

        progressDialog.show();

        // Simuliere den Fortschritt über 3 Sekunden
        new Thread(new Runnable() {
            public void run() {
                while (progressStatus[0] < 100) {
                    progressStatus[0] += 1;  // Fortschritt erhöhen
                    handler.post(new Runnable() {
                        public void run() {
                            progressBar.setProgress(progressStatus[0]);
                        }
                    });
                    try {
                        Thread.sleep(30);  // Simuliert eine 3-Sekunden-Ladezeit
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                // Wenn der Fortschritt 100% erreicht hat, Dialog schließen und Activity wechseln
                progressDialog.dismiss();
                startActivity(new Intent(MeldungMainActivity.this, MenueActivity.class));
            }
        }).start();
    }
}
