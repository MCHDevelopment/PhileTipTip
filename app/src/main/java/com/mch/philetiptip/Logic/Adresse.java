package com.mch.philetiptip.Logic;

public class Adresse {
    private String strasse;
    private int hausNummer;
    private int postleitzahl;

    public Adresse() {
        resetAdresse();
    }

    public void resetAdresse(){
        this.strasse = "";
        this.hausNummer = 0;
        this.postleitzahl = 0;
    }

    public String getStrasse() {
        String tempStrasse = "";
        if(strasse.isEmpty()){
            tempStrasse = "keine Eingabe";
        }
        else{
            tempStrasse = strasse;
        }
        return strasse;
    }

    public void setStrasse(String strasse) {
        this.strasse = strasse;
    }

    public int getHausNummer() {
        return hausNummer;
    }

    public void setHausNummer(int hausNummer) {
        this.hausNummer = hausNummer;
    }

    public int getPostleitzahl() {
        return postleitzahl;
    }

    public void setPostleitzahl(int postleitzahl) {
        this.postleitzahl = postleitzahl;
    }
}
