package com.mch.philetiptip.Logic;

import android.util.Log;

import com.mch.philetiptip.Logic.Persistance.JSonMeldungsLader;

import java.util.ArrayList;
import java.util.List;

public class MeldungsHolder {
    private ArrayList<Meldung> meldungen = new ArrayList<Meldung>();

    public MeldungsHolder(){
        meldungen = new ArrayList<Meldung>();
    }

    public void addMeldung(Meldung meldung){
        //Nur für Testzwecke - TODO: Bessere UID Funktionalitaet
        meldung.setMeldungsId(meldungen.size() + 1);
        meldungen.add(meldung);
    }

    public List<Meldung> getMeldungen() {
        return meldungen;
    }

    public void syncMeldungen(){
        meldungen.clear();

        //TODO: Bessere Auswahl ob off- oder Online
        JSonMeldungsLader jSonMeldungsLader = new JSonMeldungsLader();
        int meldungsAnzahl = jSonMeldungsLader.ladeAnzahlMeldungen();

        Meldung tempMeldung = null;

        for(int i = 0; i < meldungsAnzahl; i++){
            tempMeldung = jSonMeldungsLader.ladeMeldung(i);
            if(tempMeldung != null){
                meldungen.add(tempMeldung);
            }
        }
    }
}
