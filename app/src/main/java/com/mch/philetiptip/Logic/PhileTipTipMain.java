package com.mch.philetiptip.Logic;

import android.app.Application;

public class PhileTipTipMain extends Application {
    private Meldung meldung;

    public void CreateMeldung(){
        this.meldung = new Meldung();
    }

    public Meldung getMeldung() {
        return meldung;
    }
}
