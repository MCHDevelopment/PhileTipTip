package com.mch.philetiptip.Logic.Data;

public class Kontaktdaten {
    private Adresse adresse;
    private String emailAdresse;
    private String telefonNummer;
    private String handyNummer;

    public void umzug(Adresse neueAdresse) {
        adresse = neueAdresse;
    }

    public void telefonAenderung(String neueTelefonnummer) {
        this.telefonNummer = neueTelefonnummer;
    }
}