package com.mch.philetiptip.Logic;

public class Adresse {
    private String strasse;
    private int hausNummer;
    private int postleitzahl;
    private String ort;

    public Adresse() {
        resetAdresse();
    }

    public void resetAdresse(){
        this.strasse = "";
        this.ort = "";
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
        return tempStrasse;
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

    public String getOrt() {
        String tempOrt = "";
        if(ort.isEmpty()){
            tempOrt = "keine Eingabe";
        }
        else{
            tempOrt = ort;
        }

        return tempOrt;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    public String getAdressString() {
        String tempAdressString = "";
        tempAdressString += getStrasse() + " ";
        if(getHausNummer() > 0){
            tempAdressString += getHausNummer() + "\n";
        }else{
            tempAdressString += "-" + "\n";
        }

        if(getPostleitzahl() > 0){
            tempAdressString += getPostleitzahl() + "\n";
        }else{
            tempAdressString += "-" + "\n";
        }

        tempAdressString += getOrt() + "\n";

        return tempAdressString;
    }
}
