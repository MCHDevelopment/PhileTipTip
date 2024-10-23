package com.mch.philetiptip;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.fragment.app.Fragment;

import com.mch.philetiptip.Logic.Meldungsart;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class MeldungDateneingabeFragment extends Fragment {

    private EditText editTextMeldung;
    private Spinner typeSpinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate das Fragment-Layout
        View view = inflater.inflate(R.layout.fragment_neue_meldung, container, false);

        configureSpinner(view);
        configureTextFields(view);

        // Jetzt kannst du mit editTextHausnummer arbeiten
        return view;
    }

    private void configureSpinner(View view) {
        typeSpinner = view.findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(view.getContext(),
                R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        Meldungsart tempMeldungsart = phileTipTipMain.getMeldung().getMeldungsart();
        typeSpinner.setSelection(tempMeldungsart.getIndex());
    }

    private void configureTextFields(View view) {
        configureMeldungsInput(view);
    }

    private void configureMeldungsInput(View view) {
        editTextMeldung = view.findViewById(R.id.meldung_input);

        //TODO: Vorbelegung besser
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        String tempMeldungsText = phileTipTipMain.getMeldung().getMeldungstext();
        if(tempMeldungsText.isEmpty())
        {
            editTextMeldung.setText("");
            editTextMeldung.setHint("Bitte Ihre Meldung eingeben...");
        }else{
            editTextMeldung.setText(tempMeldungsText);
        }
    }

    public void fuelleMeldung(){
        PhileTipTipMain phileTipTipMain = PhileTipTipMain.getInstance();
        phileTipTipMain.getMeldung().setMeldungstext(editTextMeldung.getText().toString());
        phileTipTipMain.getMeldung().setMeldungsart(getSelectedMeldungsart());
        // Toast
        /*
        Toast.makeText(MeldungActivity.this, // Android Context
                        "Art der Meldung: " + phileTipTipMain.getMeldung().getMeldungsart().toString(), // Toast-Nachricht
                        Toast.LENGTH_LONG) // Anzeigedauer
                .show(); // Toast anzeigen*/
    }

    private Meldungsart getSelectedMeldungsart() {
        // Hole die Position der aktuellen Auswahl
        int selectedPosition = typeSpinner.getSelectedItemPosition();

        // Mapping der Spinner-Position auf die Enum-Werte (Positionen sind sprachunabhängig)
        switch (selectedPosition) {
            case 0:
                return Meldungsart.Sonstige;
            case 1:
                return Meldungsart.Schaedlingsbefall;
            case 2:
                return Meldungsart.Unkrautbewuchs;
            default:
                throw new IllegalArgumentException("Unbekannte Meldungsart an Position: " + selectedPosition);
        }
    }
}