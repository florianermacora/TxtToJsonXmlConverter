package org.TxtToJsonXmlConverter.enumeration;

import jdk.nashorn.internal.runtime.ParserException;

public enum ErrorDataType {

    TOO_MUCH_ELEMENT("there are too much elements in this reference"),
    NUM_REFERENCE_MISSING("The reference number miss"),
    TYPE_MISSING("The type miss"),
    PRICE_MISSING("The price number miss"),
    SIZE_MISSING("The size number miss"),
    NUM_REF_NOT_NUM("The reference number is not a number"),
    WRONG_LENGTH("The reference number has a length different of "),
    WRONG_COLOR("Incorrect value for color"),
    PRICE_NOT_A_NUM("The price is not a number"),
    NOT_A_PRICE("The price is not a price"),
    SIZE_NOT_NUM("The size is not a number");

    private String errorDataType;

    private ErrorDataType(String errorDataType) {
        this.errorDataType = errorDataType;
    }

    public String getErrorDataType() {
        return errorDataType;
    }

}
