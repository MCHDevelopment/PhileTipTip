package com.mch.philetiptip.Logic;

import android.content.Context;
import android.util.Log;

import com.mch.philetiptip.Logic.Data.Angestellter;
import com.mch.philetiptip.Logic.Data.Mieter;
import com.mch.philetiptip.Logic.Data.Person;
import com.mch.philetiptip.Logic.Data.TeamLeiter;

public class PhileTipTipMain {
    private static PhileTipTipMain instance;

    private MeldungsHolder meldungsHolder;
    private Context context;
    private Person nutzer;

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

    public void initUserData(int idNumber) {

        //TODO: Aus Datenbank auslesen
        instance.nutzer = new Mieter("Max", "Mietermann", 123);
        //instance.nutzer = new Angestellter("Arnold", "Angestellter", 456);
        //instance.nutzer = new TeamLeiter("Thea", "Teamleiterin", 789);
    }

    public void initApplicationContext(Context context){
        this.context = context;
    }

    public MeldungsHolder getMeldungsHolder() {
        return meldungsHolder;
    }

    public Person getNutzer() {
        return nutzer;
    }

    public Context getContext() {
        return context;
    }
}
