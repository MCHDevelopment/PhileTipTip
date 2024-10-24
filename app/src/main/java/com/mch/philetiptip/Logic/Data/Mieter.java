package com.mch.philetiptip.Logic.Data;

public class Mieter extends Person
{
    private int mieterID;
    public Mieter(String firstName, String lastName, int mieterID)
    {
        super(firstName, lastName);
        this.mieterID = mieterID;
    }

    @Override
    public int getID() {
        return mieterID;
    }
}
