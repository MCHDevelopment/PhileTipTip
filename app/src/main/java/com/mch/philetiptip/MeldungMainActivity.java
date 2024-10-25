package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.MeldungsprozessActivity;
import com.mch.philetiptip.Enums.EMeldungsschirm;
import com.mch.philetiptip.Logic.Persistance.JSonMeldungsSpeicherer;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class MeldungMainActivity extends MeldungsprozessActivity {

    private MeldungDateneingabeFragment dateneingabeFragment;
    private AdresseingabeFragment adresseingabeFragment;
    private FotoFragment fotoFragment;
    private PruefenFragment pruefenFragment;

    private ImageButton buttonZurueck;
    private ImageButton buttonWeiter;
    private ImageButton buttonSubmit;

    private EMeldungsschirm currentSchirm = EMeldungsschirm.ArtUndText;

    private Meldung meldung;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_meldung_main);

        meldung = PhileTipTipMain.getInstance().getNutzer().meldungMachen();

        configureButtons();

        //Fuer das erste Fragment findet kein Aufruf der Schirmwechsel Funktion statt, deswegen muss
        //setzeButtonSichtbarkeit hier losgetreten werden
        currentSchirm = EMeldungsschirm.ArtUndText;
        setzeButtonSichtbarkeit();

        // Fragmente einmalig erstellen und eine Referenz auf die Meldung uebergeben
        Bundle bundle = new Bundle();
        bundle.putSerializable("meldung", meldung);

        dateneingabeFragment = new MeldungDateneingabeFragment();
        dateneingabeFragment.setArguments(bundle);

        adresseingabeFragment = new AdresseingabeFragment();
        adresseingabeFragment.setArguments(bundle);

        fotoFragment = new FotoFragment();
        fotoFragment.setArguments(bundle);

        pruefenFragment = new PruefenFragment();
        pruefenFragment.setArguments(bundle);

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
        buttonWeiter.setOnClickListener(v ->  {
                wechselZumNaechstenSchirm();
        });
    }

    private void wechselZumNaechstenSchirm(){
        EMeldungsschirm zielSchirm = currentSchirm;
        switch (currentSchirm){
            case ArtUndText:
                dateneingabeFragment.fuelleMeldung();
                zielSchirm = EMeldungsschirm.Adresse;
                break;
            case Adresse:
                adresseingabeFragment.fuelleAdresse();
                zielSchirm = EMeldungsschirm.Foto;
                break;
            case Foto:
                zielSchirm = EMeldungsschirm.Pruefen;
                break;
            case Pruefen:
                break;
        }

        zeigeFragment(zielSchirm);
    }

    private void configureZurueckButton() {
        buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(v ->  {
                wechselZumVorherigemSchirm();
        });
    }

    private void wechselZumVorherigemSchirm(){
        EMeldungsschirm zielSchirm = currentSchirm;
        switch (currentSchirm){
            case ArtUndText:
                break;
            case Adresse:
                zielSchirm = EMeldungsschirm.ArtUndText;
                break;
            case Foto:
                zielSchirm = EMeldungsschirm.Adresse;
                break;
            case Pruefen:
                zielSchirm = EMeldungsschirm.Foto;
                break;
        }

        zeigeFragment(zielSchirm);
    }

    //public damit auch das PruefenFragment darauf zugreifen kann um einen Rueckweg zu erlauben
    public void zeigeFragment(EMeldungsschirm zielSchirm){
        Fragment tempFragment = null;
        switch (zielSchirm){
            case ArtUndText:
                tempFragment = dateneingabeFragment;
                break;
            case Adresse:
                tempFragment = adresseingabeFragment;
                break;
            case Foto:
                tempFragment = fotoFragment;
                break;
            case Pruefen:
                tempFragment = pruefenFragment;
                break;
        }

        currentSchirm = zielSchirm;
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
        buttonSubmit.setOnClickListener(v -> {
                projektEintragen();
                //TODO: Meldung in Datenbank anlegen
                //TODO: Neue Methode zum Schirmwechsel
        });
    }

    private void projektEintragen(){
        //TODO: Meldung in Datenbank anlegen

        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        phileTipTipMain.getMeldungsHolder().addMeldung(meldung);

        //TODO: Bessere Auswahl ob off- oder Online
        JSonMeldungsSpeicherer jSonMeldungsSpeicherermeldungsSpeicherer = new JSonMeldungsSpeicherer();
        jSonMeldungsSpeicherermeldungsSpeicherer.speichereMeldung(meldung);

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

        // Simuliere den Fortschritt über 2 Sekunden
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
                        Thread.sleep(20);  // Simuliert eine 2-Sekunden-Ladezeit
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
