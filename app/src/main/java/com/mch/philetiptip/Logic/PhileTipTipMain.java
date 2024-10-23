package com.mch.philetiptip.Logic;

import android.util.Log;

public class PhileTipTipMain {
    private static PhileTipTipMain instance;

    private Meldung meldung;
    private MeldungsHolder meldungsHolder;

    private PhileTipTipMain(){
        meldungsHolder = new MeldungsHolder();
    }

    // Methode, um die einzige Instanz zu erhalten
    public static synchronized PhileTipTipMain getInstance() {
        if (instance == null) {
            instance = new PhileTipTipMain();
        }
        return instance;
    }

    public void CreateMeldung(){
        this.meldung = new Meldung();
    }

    public Meldung getMeldung() {
        if (meldung == null) {
            Log.e("MeldungActivity", "Meldung ist null");
        }

        return meldung;
    }

    public MeldungsHolder getMeldungsHolder() {
        return meldungsHolder;
    }
}
