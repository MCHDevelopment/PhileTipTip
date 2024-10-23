package com.mch.philetiptip.Logic;

public enum Meldungsart {
    Sonstige(0),
    Schaedlingsbefall(1),
    Unkrautbewuchs(2);

    private final int index;

    Meldungsart(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
