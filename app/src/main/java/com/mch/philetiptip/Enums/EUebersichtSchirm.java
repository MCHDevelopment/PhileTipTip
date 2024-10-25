package com.mch.philetiptip.Enums;

public enum EUebersichtSchirm {

    Uebersicht(0),
    Detailansicht(1);

    private final int index;

    EUebersichtSchirm(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
