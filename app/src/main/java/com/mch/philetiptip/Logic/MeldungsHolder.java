package com.mch.philetiptip.Logic;

import java.util.ArrayList;

public class MeldungsHolder {
    private ArrayList<Meldung> meldungen = new ArrayList<Meldung>();

    public MeldungsHolder(){
        meldungen = new ArrayList<Meldung>();
    }

    public void addMeldung(Meldung meldung){
        meldungen.add(meldung);
    }
}
