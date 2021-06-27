package org.TxtToJsonXmlConverter.enumeration;

public enum ColorType {

    ROUGE("R"),
    VERT("G"),
    BLEU("B");

    private String color;

    private ColorType(String color) {
        this.color = color;
    }

    public String getColor() {
        return this.color;
    }

    public static boolean contains(String colorTest) {

        for (ColorType c : ColorType.values()) {
            if (c.getColor().equals(colorTest)) {
                return true;
            }
        }

        return false;
    }
}
