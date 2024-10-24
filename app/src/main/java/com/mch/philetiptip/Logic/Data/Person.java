package com.mch.philetiptip.Logic.Data;

import com.mch.philetiptip.Logic.Meldung;

public abstract class Person {
    private String vorName;
    private String nachName;

    private Kontaktdaten kontaktdaten;

    public Person(String vorName, String nachName) {
        this.vorName = vorName;
        this.nachName = nachName;
    }

    public String getVorName() {
        return vorName;
    }

    public String getNachName() {
        return nachName;
    }

	public Kontaktdaten getKontaktdaten() {
		return kontaktdaten;
	}

    public void setKontaktdaten(Kontaktdaten kontaktdaten) {
        this.kontaktdaten = kontaktdaten;
    }

    public abstract int getID();

    public Meldung meldungMachen() {
        Meldung tempMeldung = new Meldung(getID());
        return tempMeldung;
    }

}