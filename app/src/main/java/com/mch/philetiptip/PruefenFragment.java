package com.mch.philetiptip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Enums.EMeldungsschirm;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class PruefenFragment extends Fragment {

    private TextView textMeldung;
    private TextView textAdresse;

    private Meldung meldung;

    ImageView vorschauBild;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate das Fragment-Layout
        View view = inflater.inflate(R.layout.fragment_pruefen, container, false);

        // Meldung aus dem Bundle holen
        if (getArguments() != null) {
            meldung = (Meldung) getArguments().getSerializable("meldung");
        }

        configureButtons(view);
        configureTextviews(view);
        configureBild(view);

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

        buttonMeldungEdit.setOnClickListener(v -> {
                ((MeldungMainActivity) getActivity()).zeigeFragment(EMeldungsschirm.ArtUndText);
        });
    }

    private void configureAdressEditButton(View view){
        ImageButton buttonAdressEdit = view.findViewById(R.id.button_edit_address);

        buttonAdressEdit.setOnClickListener(v ->  {
                ((MeldungMainActivity) getActivity()).zeigeFragment(EMeldungsschirm.Adresse);
        });
    }

    private void configureImageEditButton(View view){
        ImageButton buttonImageEdit = view.findViewById(R.id.button_edit_image);

        buttonImageEdit.setOnClickListener(v ->  {
                ((MeldungMainActivity) getActivity()).zeigeFragment(EMeldungsschirm.Foto);
        });
    }

    private void configureBild(View view) {
        vorschauBild = view.findViewById(R.id.image_preview);
    }

    private void fuelleInhalte(){
        fuelleMeldungsfeld();
        fuelleAdressfeld();
        fuelleBild();
    }
    private void fuelleAdressfeld() {
        textAdresse.setText(meldung.getMeldungAdresse().getAdressString());
    }

    private void fuelleMeldungsfeld() {
        textMeldung.setText(meldung.getMeldungstext());
    }

    private void fuelleBild() {
        //TODO: Reihenfolge umdrehen, erst Online, dann Offline - Onlinechecking muss noch implementiert werden
        if (meldung.getBildquelle().hasLocalUri()) {
            vorschauBild.setImageURI(meldung.getBildquelle().getLocalUri());
        } else if (meldung.getBildquelle().hasRemoteUrl()) {
            //TODO: Bild von der Onlinequelle laden
            // Ladebild für eine URL, zum Beispiel mit Glide oder Picasso
        }
    }
}