package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.activity.EdgeToEdge;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.mch.philetiptip.Logic.MeldungsprozessActivity;

public class UebersichtActivity extends MeldungsprozessActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_uebersicht);

        configureHomeButton();
        configureZurueckButton();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new MeldungsUebersichtFragment())
                    .commit();
        }
    }

    private void configureHomeButton() {
        ImageButton buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(v -> startActivity(new Intent(UebersichtActivity.this, MenueActivity.class)));
    }

    private void configureZurueckButton() {
        ImageButton buttonZurueck = findViewById(R.id.button_back);
        buttonZurueck.setOnClickListener(v -> onBackPressed());
    }

    // Methode zum Wechseln zum DetailFragment
    public void zeigeDetailFragment(String meldungId) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment detailFragment = MeldungDetailFragment.newInstance(meldungId);
        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
