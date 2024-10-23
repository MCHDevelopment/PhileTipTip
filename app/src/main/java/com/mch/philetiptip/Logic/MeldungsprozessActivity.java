package com.mch.philetiptip.Logic;

import android.content.DialogInterface;
import android.content.Intent;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MeldungsprozessActivity extends AppCompatActivity {

    protected void configureHomeButton(int homeButtonId, final Class<?> targetActivity) {
        ImageButton buttonHome = findViewById(homeButtonId);
        buttonHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // AlertDialog erstellen
                AlertDialog.Builder builder = new AlertDialog.Builder(MeldungsprozessActivity.this);
                builder.setTitle("Vorsicht");
                builder.setMessage("Wenn Sie zum Hauptmenü zurückkehren, werden sämtliche Eingaben der aktuellen Meldung gelöscht.");

                // "Eingaben verwerfen, zurück zum Hauptmenü" - führt zur Rückkehr ins Hauptmenü
                builder.setPositiveButton("Eingaben verwerfen, zurück zum Hauptmenü", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Starte die Ziel-Activity
                        startActivity(new Intent(MeldungsprozessActivity.this, targetActivity));
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
}
