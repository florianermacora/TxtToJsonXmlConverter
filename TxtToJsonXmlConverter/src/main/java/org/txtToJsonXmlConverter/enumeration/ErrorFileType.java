package org.txtToJsonXmlConverter.enumeration;

public enum ErrorFileType {

    TOO_LESS_ARG("Some arguments are missing"),
    TOO_MUCH_ARG("There are too much arguments"),
    FORMAT_NOT_SUPPORTED("The wanted format is not supported"),
    INPUT_FILE_MISSING("The input file is missing"),
    OUTPUT_PTOBLEM("The output file is not able to be created");

    private String errorFileType;

    private ErrorFileType(String errorFileType) {
        this.errorFileType = errorFileType;
    }

    public String getErrorFileType() {
        return this.errorFileType;
    }


}
