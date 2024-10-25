package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mch.philetiptip.Enums.EUebersichtSchirm;
import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.MeldungsprozessActivity;
import com.mch.philetiptip.Enums.EMeldungsschirm;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class UebersichtActivity extends MeldungsprozessActivity {

    private MeldungsUebersichtFragment meldungsUebersichtFragment;

    private EUebersichtSchirm currentSchirm = EUebersichtSchirm.Uebersicht;
    private ImageButton buttonMeldungSync;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_uebersicht);

        configureButtons();

        // Fragmente einmalig erstellen
        meldungsUebersichtFragment = new MeldungsUebersichtFragment();
        meldungsUebersichtFragment = new MeldungsUebersichtFragment();

        //Fuer das erste Fragment findet kein Aufruf der Schirmwechsel Funktion statt
        currentSchirm = EUebersichtSchirm.Uebersicht;

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, meldungsUebersichtFragment)
                    .commit();
        }
    }

    private void configureButtons(){
        configureHomeButton();
        configureZurueckButton();
        configureSyncButton();
    }

    private void configureSyncButton(){
        buttonMeldungSync = findViewById(R.id.button_sync);

        buttonMeldungSync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PhileTipTipMain.getInstance().getMeldungsHolder().syncMeldungen();
                meldungsUebersichtFragment.updateMeldungenListe();
            }
        });
    }

    private void configureHomeButton() {
        ImageButton buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(v -> navigateToMenu());
    }

    private void configureZurueckButton() {

        ImageButton buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(v -> {
            switch (currentSchirm) {
                case Uebersicht:
                    navigateToMenu();
                    break;

                case Detailansicht:
                    buttonMeldungSync.setVisibility(View.VISIBLE);
                    currentSchirm = EUebersichtSchirm.Uebersicht;

                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.fragment_container, meldungsUebersichtFragment)
                            .addToBackStack(null)
                            .commit();
                    break;
            }
        });


    }

    private void navigateToMenu() {
        startActivity(new Intent(UebersichtActivity.this, MenueActivity.class));
    }

    // Methode zum Wechseln zum DetailFragment
    public void zeigeDetailFragment(Meldung meldung) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment detailFragment = MeldungDetailFragment.newInstance(meldung);
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
        currentSchirm = EUebersichtSchirm.Detailansicht;
        buttonMeldungSync.setVisibility(View.GONE);
    }
}
