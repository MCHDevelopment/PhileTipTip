package com.mch.philetiptip.Logic.Persistance;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.google.gson.Gson;
import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.PhileTipTipMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSonMeldungsLader {

    private static final String DIRECTORY_PATH = "Documents/PhileTipTip/";

    // Lädt die Meldung aus einer JSON-Datei basierend auf dem Index
    public Meldung ladeMeldung(int index) {
        Meldung meldung = null;
        Context context = PhileTipTipMain.getInstance().getContext();
        Uri collection = MediaStore.Files.getContentUri("external");

        String selection = MediaStore.Files.FileColumns.RELATIVE_PATH + "=? AND " +
                MediaStore.Files.FileColumns.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[]{
                DIRECTORY_PATH,
                "meldung_" + index + ".json"
        };

        try (Cursor cursor = context.getContentResolver().query(
                collection,
                new String[]{MediaStore.Files.FileColumns._ID},
                selection,
                selectionArgs,
                null)) {

            if (cursor != null && cursor.moveToFirst()) {
                Uri fileUri = Uri.withAppendedPath(collection, cursor.getString(0));

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        context.getContentResolver().openInputStream(fileUri)))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        sb.append(line);
                    }
                    Gson gson = new Gson();
                    meldung = gson.fromJson(sb.toString(), Meldung.class);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return meldung;
    }

    // Lädt den Counter-Wert aus der Datei "counter.txt" im Verzeichnis
    public int ladeAnzahlMeldungen() {
        int counter = 0;
        Context context = PhileTipTipMain.getInstance().getContext();
        Uri collection = MediaStore.Files.getContentUri("external");

        String selection = MediaStore.Files.FileColumns.RELATIVE_PATH + "=? AND " +
                MediaStore.Files.FileColumns.DISPLAY_NAME + "=?";
        String[] selectionArgs = new String[]{
                DIRECTORY_PATH,
                "counter.txt"
        };

        try (Cursor cursor = context.getContentResolver().query(
                collection,
                new String[]{MediaStore.Files.FileColumns._ID},
                selection,
                selectionArgs,
                null)) {

            if (cursor != null && cursor.moveToFirst()) {
                Uri fileUri = Uri.withAppendedPath(collection, cursor.getString(0));

                try (BufferedReader reader = new BufferedReader(new InputStreamReader(
                        context.getContentResolver().openInputStream(fileUri)))) {
                    String line = reader.readLine();
                    if (line != null) {
                        counter = Integer.parseInt(line.trim());
                    }
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }

        return counter;
    }
}