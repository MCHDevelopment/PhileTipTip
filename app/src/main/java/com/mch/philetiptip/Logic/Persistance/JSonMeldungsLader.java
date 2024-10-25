package com.mch.philetiptip.Logic.Persistance;

import android.content.Context;

import com.google.gson.Gson;
import com.mch.philetiptip.Logic.Meldung;
import com.mch.philetiptip.Logic.PhileTipTipMain;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class JSonMeldungsLader {

    public Meldung ladeMeldung(int index) {
        Meldung meldung = null;
        try {
            //TODO: Nullabprüfung
            FileInputStream fis = PhileTipTipMain.getInstance().getContext().openFileInput("meldung_"+index+".json");
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader reader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            Gson gson = new Gson();
            meldung = gson.fromJson(sb.toString(), Meldung.class);
            reader.close();
            isr.close();
            fis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return meldung;
    }

    public int ladeAnzahlMeldungen() {
        int counter = 0;
        //TODO: Nullabprüfung
        File file = new File(PhileTipTipMain.getInstance().getContext().getFilesDir(), "counter.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            if (line != null) {
                counter = Integer.parseInt(line.trim());
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
            // Optional: Fehlerbehandlung, wenn die Datei nicht existiert oder nicht lesbar ist
        }
        return counter;
    }
}
