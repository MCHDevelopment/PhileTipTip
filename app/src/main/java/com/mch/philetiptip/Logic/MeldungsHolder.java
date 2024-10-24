package com.mch.philetiptip.Logic;

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
}
