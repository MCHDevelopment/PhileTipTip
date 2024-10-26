package com.mch.philetiptip.Logic.Persistance;

import android.content.ContentValues;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.PhileTipTipMain;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

public class JSonMeldungsSpeicherer implements IMeldungsSpeicherer {

    @Override
    public void speichereMeldung(Meldung meldung) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(meldung);

        try {
            Context context = PhileTipTipMain.getInstance().getContext();
            // Counter-Datei einlesen
            int counter = leseCounter(context);

            // Dateiname festlegen
            String dateiname = "meldung_" + counter + ".json";

            // ContentValues für MediaStore setzen
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, dateiname);
            values.put(MediaStore.MediaColumns.MIME_TYPE, "application/json");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/PhileTipTip");

            Uri fileUri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

            if (fileUri != null) {
                try (OutputStream outputStream = context.getContentResolver().openOutputStream(fileUri);
                     OutputStreamWriter writer = new OutputStreamWriter(outputStream)) {
                    writer.write(jsonString);
                }
                counter++;
                speichereCounter(context, counter);
            } else {
                throw new IOException("MediaStore konnte URI nicht erstellen");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private int leseCounter(Context context) {
        int counter = 0;
        Uri collection = MediaStore.Files.getContentUri("external");

        // Definiere den Abfragefilter für die Datei "counter.txt" im Ordner "Documents/PhileTipTip"
        String selection = MediaStore.Files.FileColumns.RELATIVE_PATH + "=? AND " +
                MediaStore.Files.FileColumns.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[] {
                "Documents/PhileTipTip/",   // Ordnername
                "counter.txt"               // Dateiname
        };

        try (Cursor cursor = context.getContentResolver().query(
                collection,
                new String[]{MediaStore.Files.FileColumns._ID},
                selection,
                selectionArgs,
                null)) {

            if (cursor != null && cursor.moveToFirst()) {
                // Hole die URI der Datei anhand der ID
                Uri fileUri = Uri.withAppendedPath(collection, cursor.getString(0));

                // Lese den Inhalt der Datei
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        context.getContentResolver().openInputStream(fileUri)))) {
                    counter = Integer.parseInt(reader.readLine());
                } catch (IOException | NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return counter;
    }


    private void speichereCounter(Context context, int counter) {
        try {
            ContentValues values = new ContentValues();
            values.put(MediaStore.MediaColumns.DISPLAY_NAME, "counter.txt");
            values.put(MediaStore.MediaColumns.MIME_TYPE, "text/plain");
            values.put(MediaStore.MediaColumns.RELATIVE_PATH, "Documents/PhileTipTip");

            Uri counterUri = context.getContentResolver().insert(MediaStore.Files.getContentUri("external"), values);

            if (counterUri != null) {
                try (OutputStream outputStream = context.getContentResolver().openOutputStream(counterUri);
                     BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream))) {
                    writer.write(String.valueOf(counter));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}