package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.Meldungsschirm;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class PruefenFragment extends Fragment {

    private TextView textMeldung;
    private TextView textAdresse;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate das Fragment-Layout
        View view = inflater.inflate(R.layout.fragment_pruefen, container, false);

        configureButtons(view);
        configureTextviews(view);
        fuelleInhalte();

        return view;
    }

    private void configureTextviews(View view) {
        configureMeldungTextview(view);
        configureAdressTextview(view);
    }

    private void configureAdressTextview(View view) {
        textAdresse = view.findViewById(R.id.text_address);
    }

    private void configureMeldungTextview(View view) {
        textMeldung = view.findViewById(R.id.text_message);
    }

    private void configureButtons(View view){
        configureMeldungEditButton(view);
        configureImageEditButton(view);
        configureAdressEditButton(view);
    }

    private void configureMeldungEditButton(View view){
        ImageButton buttonMeldungEdit = view.findViewById(R.id.button_edit_message);

        buttonMeldungEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MeldungMainActivity) getActivity()).zeigeFragment(Meldungsschirm.ArtUndText);
            }
        });
    }

    private void configureAdressEditButton(View view){
        ImageButton buttonAdressEdit = view.findViewById(R.id.button_edit_address);

        buttonAdressEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MeldungMainActivity) getActivity()).zeigeFragment(Meldungsschirm.Adresse);
            }
        });
    }

    private void configureImageEditButton(View view){
        ImageButton buttonImageEdit = view.findViewById(R.id.button_edit_image);

        buttonImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MeldungMainActivity) getActivity()).zeigeFragment(Meldungsschirm.Foto);
            }
        });
    }

    private void fuelleInhalte(){

        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();

        Meldung tempMeldung = phileTipTipMain.getMeldung();
        Log.d("PruefenFragment", "Meldungstext: " + tempMeldung.getMeldungstext());
        Log.d("PruefenFragment", "Adresse: " + tempMeldung.getMeldungAdresse().getAdressString());

        fuelleMeldungsfeld();
        fuelleAdressfeld(phileTipTipMain);
    }

    private void fuelleAdressfeld(PhileTipTipMain phileTipTipMain) {
        Meldung tempMeldung = phileTipTipMain.getMeldung();
        textAdresse.setText(tempMeldung.getMeldungAdresse().getAdressString());
    }

    private void fuelleMeldungsfeld() {
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        Meldung tempMeldung = phileTipTipMain.getMeldung();
        textMeldung.setText(tempMeldung.getMeldungstext());
    }
}