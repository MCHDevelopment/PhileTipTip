package com.mch.philetiptip.Logic.Data;

public class Angestellter extends Person
{
    private int angestelltenId;

    public Angestellter(String vorName, String nachName, int angestelltenId)
    {
        super(vorName, nachName);
        this.angestelltenId = angestelltenId;
    }

    @Override
    public int getID() {
        return angestelltenId;
    }
}