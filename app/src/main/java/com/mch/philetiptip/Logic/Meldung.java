package com.mch.philetiptip.Logic;

import android.util.Log;

public class Meldung {
    private Adresse meldungsAdresse;
    private Meldungsart meldungsart;
    private String meldungstext;

    public Meldung() {
        meldungsAdresse = new Adresse();
        resetMeldung();
    }

    private void resetMeldung(){
        this.meldungsAdresse.resetAdresse();
        this.meldungsart = meldungsart.Sonstige;
        this.meldungstext = "";

        Log.e("MeldungActivity", "Meldung Reset");
    }

    public Meldungsart getMeldungsart() {
        return meldungsart;
    }

    public void setMeldungsart(Meldungsart meldungsart) {
        this.meldungsart = meldungsart;
    }

    public String getMeldungstext() {
        return meldungstext;
    }

    public void setMeldungstext(String meldungstext) {
        this.meldungstext = meldungstext;
    }

    public Adresse getMeldungAdresse() {
        return meldungsAdresse;
    }

    public void setMeldungsAdresse(Adresse meldungsAdresse) {
        this.meldungsAdresse = meldungsAdresse;
    }
}
