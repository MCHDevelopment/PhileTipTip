package com.mch.philetiptip.Logic;

public enum Meldungsschirm {

    ArtUndText(0),
    Adresse(1),
    Foto(2),
    Pruefen(3);

    private final int index;

    Meldungsschirm(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
