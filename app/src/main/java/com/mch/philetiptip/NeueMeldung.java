package com.mch.philetiptip;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.mch.philetiptip.Logic.Adresse;
import com.mch.philetiptip.Logic.Meldungsart;
import com.mch.philetiptip.Logic.PhileTipTipMain;

public class NeueMeldung extends AppCompatActivity {

    private EditText editTextMeldung;
    private Spinner typeSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_neue_meldung);

        configureButtons();
        configureSpinner();
        configureTextFields();

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void configureSpinner() {
        typeSpinner = findViewById(R.id.type_spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.options_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(adapter);
    }

    private void configureTextFields() {
        configureMeldungsInput();
    }

    private void configureMeldungsInput() {
        editTextMeldung = findViewById(R.id.meldung_input);
    }

    private void configureButtons(){
        configureHomeButton();
        configureWeiterButton();
    }

    private void configureHomeButton(){
        ImageButton buttonHome = findViewById(R.id.button_home);
        buttonHome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                startActivity(new Intent(NeueMeldung.this, PhileMenue.class));
            }
        });
    }

    private void configureWeiterButton(){
        ImageButton buttonWeiter = findViewById(R.id.button_continue);
        buttonWeiter.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                fuelleMeldung();
                startActivity(new Intent(NeueMeldung.this, Adresseingabe.class));
            }
        });
    }

    private void fuelleMeldung(){
        PhileTipTipMain phileTipTipMain = (PhileTipTipMain) getApplicationContext();
        phileTipTipMain.getMeldung().setMeldungstext(editTextMeldung.getText().toString());
        phileTipTipMain.getMeldung().setMeldungsart(getSelectedMeldungsart());
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