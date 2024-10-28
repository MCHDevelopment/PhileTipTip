package com.mch.philetiptip.Logic.Persistance;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mch.philetiptip.Logic.Data.Bildquelle;
import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.PhileTipTipMain;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;

public class JSonMeldungsLader {

    private static final String DIRECTORY_PATH = "Documents/PhileTipTip/";

    // Lädt die Meldung aus einer JSON-Datei basierend auf dem Index
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

                    // Gson-Instanz mit benutzerdefinierter Deserialisierung für Bildquelle
                    Gson gson = new GsonBuilder()
                            .registerTypeAdapter(Bildquelle.class, new JsonDeserializer<Bildquelle>() {
                                @Override
                                public Bildquelle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                                    JsonObject jsonObject = json.getAsJsonObject();
                                    String uriString = null;

                                    if (jsonObject.has("localUriString") && !jsonObject.get("localUriString").isJsonNull()) {
                                        uriString = jsonObject.get("localUriString").getAsString();
                                    }

                                    Bildquelle bildquelle = new Bildquelle();
                                    bildquelle.setLocalUriString(uriString);  // Falls es einen Setter gibt; alternativ direkt setzen
                                    return bildquelle;
                                }
                            })
                            .create();

                    // Deserialisierung der JSON-Datei in ein Meldung-Objekt
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