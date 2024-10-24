package com.mch.philetiptip.Logic.Persistance;

import android.content.Context;

import com.google.gson.Gson;
import com.mch.philetiptip.Logic.Meldung;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class JSonMeldungsSpeicherer implements IMeldungsSpeicherer{
    @Override
    public void speichereMeldung(Meldung meldung, Context context) {
        Gson gson = new Gson();
        String jsonString = gson.toJson(meldung);
        try {
            // Counter aus Datei lesen
            File counterFile = new File(context.getFilesDir(), "counter.txt");
            int counter = 0;
            if (counterFile.exists()) {
                try (BufferedReader br = new BufferedReader(new FileReader(counterFile))) {
                    counter = Integer.parseInt(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            FileOutputStream fos = context.openFileOutput("meldung_" + counter + ".json", Context.MODE_PRIVATE);
            fos.write(jsonString.getBytes());
            fos.close();

            counter++;

            // Counter in Datei speichern
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(counterFile))) {
                bw.write(String.valueOf(counter));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
