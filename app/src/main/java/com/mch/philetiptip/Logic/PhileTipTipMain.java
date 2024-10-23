package com.mch.philetiptip.Logic;

import android.app.Application;

public class PhileTipTipMain extends Application {
    private Meldung meldung;
    private MeldungsHolder meldungsHolder;

    public PhileTipTipMain(){
        meldungsHolder = new MeldungsHolder();
    }

    public void CreateMeldung(){
        this.meldung = new Meldung();
    }

    public Meldung getMeldung() {
        return meldung;
    }

    public MeldungsHolder getMeldungsHolder() {
        return meldungsHolder;
    }
}
