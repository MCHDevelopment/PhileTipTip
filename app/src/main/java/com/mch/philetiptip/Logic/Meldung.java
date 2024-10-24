package com.mch.philetiptip.Logic;

import android.util.Log;

import com.mch.philetiptip.Logic.Data.Adresse;

import java.util.Date;

public class Meldung {
    private Adresse meldungsAdresse;
    private Meldungsart meldungsart;
    private String meldungstext;

    private int meldungsId;

    private int melderId;
    private Date meldungsDatum;

    public Meldung() {
        meldungsAdresse = new Adresse();
        resetMeldung();
    }

    private void resetMeldung(){
        this.meldungsAdresse.resetAdresse();
        this.meldungsart = meldungsart.Sonstige;
        this.meldungstext = "";
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

    public int getId() {
        return meldungsId;
    }

    public void setMeldungsId(int meldungsId) {
        this.meldungsId = meldungsId;
    }
}
