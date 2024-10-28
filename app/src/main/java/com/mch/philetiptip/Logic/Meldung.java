package com.mch.philetiptip.Logic;

import android.net.Uri;

import com.mch.philetiptip.Enums.EMeldungsart;
import com.mch.philetiptip.Logic.Data.Adresse;
import com.mch.philetiptip.Logic.Data.Bildquelle;

import java.io.Serializable;
import java.util.Date;

public class Meldung implements Serializable {

    private static final long serialVersionUID = 1L;

    private Adresse meldungsAdresse;
    private EMeldungsart meldungsart;
    private String meldungstext;

    private int meldungsId;

    private int melderId;
    private Date meldungsDatum;

    private Bildquelle bildquelle;

    private double latitude;
    private double longitude;

    public Meldung(int melderId) {
        meldungsAdresse = new Adresse();
        bildquelle = new Bildquelle();
        this.melderId = melderId;
        resetMeldung();
    }

    private void resetMeldung(){
        this.meldungsAdresse.resetAdresse();
        this.meldungsart = meldungsart.Sonstige;
        this.meldungstext = "";
        this.bildquelle.resetBildquelle();
        this.latitude = 0.0d;
        this.longitude = 0.0d;
    }

    public EMeldungsart getMeldungsart() {
        return meldungsart;
    }

    public void setMeldungsart(EMeldungsart meldungsart) {
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

    public Bildquelle getBildquelle() {
        return bildquelle;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
