package com.mch.philetiptip.Enums;

public enum EMeldungsschirm {

    ArtUndText(0),
    Adresse(1),
    Foto(2),
    Pruefen(3);

    private final int index;

    EMeldungsschirm(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
