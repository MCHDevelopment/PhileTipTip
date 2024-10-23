package com.mch.philetiptip;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog erstellen
                AlertDialog.Builder builder = new AlertDialog.Builder(NeueMeldung.this);
                builder.setTitle("Vorsicht");
                builder.setMessage("Wenn Sie zum Hauptmenü zurückkehren, werden sämtliche Eingaben der aktuellen Meldung gelöscht.");

                // "Eingaben verwerfen, zurück zum Hauptmenü" - führt zur Rückkehr ins Hauptmenü
                builder.setPositiveButton("Eingaben verwerfen, zurück zum Hauptmenü", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Starte die PhileMenue Activity
                        startActivity(new Intent(NeueMeldung.this, PhileMenue.class));
                    }
                });

                // "Weiter mit der Erfassung" - schließt den Dialog
                builder.setNegativeButton("Weiter mit der Erfassung", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Schließt den Dialog, es passiert nichts
                        dialog.dismiss();
                    }
                });

                // Zeige den Dialog
                AlertDialog dialog = builder.create();

                // Dialog-Buttons untereinander positionieren
                dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                    @Override
                    public void onShow(DialogInterface dialog) {
                        Button positiveButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                        Button negativeButton = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_NEGATIVE);

                        // Buttons untereinander setzen
                        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) positiveButton.getLayoutParams();
                        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
                        layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                        positiveButton.setLayoutParams(layoutParams);
                        negativeButton.setLayoutParams(layoutParams);
                    }
                });

                dialog.show();
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
        // Toast
        Toast.makeText(NeueMeldung.this, // Android Context
                        "Art der Meldung: " + phileTipTipMain.getMeldung().getMeldungsart().toString(), // Toast-Nachricht
                        Toast.LENGTH_LONG) // Anzeigedauer
                .show(); // Toast anzeigen
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