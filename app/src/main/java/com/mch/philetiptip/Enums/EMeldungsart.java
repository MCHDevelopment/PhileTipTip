package com.mch.philetiptip.Enums;

public enum EMeldungsart {
    Sonstige(0),
    Schaedlingsbefall(1),
    Unkrautbewuchs(2);

    private final int index;

    EMeldungsart(int index) {
        this.index = index;
    }

    public int getIndex() {
        return index;
    }
}
