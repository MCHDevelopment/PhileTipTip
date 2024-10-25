package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.MeldungsprozessActivity;

public class FotoFragment extends Fragment {

    private Meldung meldung;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate das Fragment-Layout
        View view = inflater.inflate(R.layout.fragment_foto, container, false);

        // Meldung aus dem Bundle holen
        if (getArguments() != null) {
            meldung = (Meldung) getArguments().getSerializable("meldung");
        }

        configureButtons(view);

        return view;
    }

    private void configureButtons(View view){
        configureFotoButton(view);
        configureGalleryButton(view);
    }


    private void configureFotoButton(View view){
                /*
                ImageButton buttonFoto = view.findViewById(R.id.button_camera);

        buttonFoto.setOnClickListener(v -> {
                //TODO: Foto machen

                // Toast
                Toast.makeText(FotoFragment.this, // Android Context
                                "onClick für Kamera-Button aufgerufen", // Toast-Nachricht
                                Toast.LENGTH_LONG) // Anzeigedauer
                        .show(); // Toast anzeigen
        });
         */
    }

    private void configureGalleryButton(View view){
        /*
        ImageButton buttonGallerie = view.findViewById(R.id.button_gallery);
        buttonGallerie.setOnClickListener(v -> {
                //TODO: Gallerie oeffnen Bild auswaehlen

                // Toast
                Toast.makeText(FotoFragment.this, // Android Context
                                "onClick für Gallerie-Button aufgerufen", // Toast-Nachricht
                                Toast.LENGTH_LONG) // Anzeigedauer
                        .show(); // Toast anzeigen
        });
        */
    }
}