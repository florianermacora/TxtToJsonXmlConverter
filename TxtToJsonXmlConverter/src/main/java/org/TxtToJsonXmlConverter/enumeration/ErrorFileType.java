package org.TxtToJsonXmlConverter;

public enum ErrorFileType {

    TOO_LESS_ARG("Some Arguments are missing"),
    TOO_MUCH_ARG("There are too much arguments"),
    FORMAT_NOT_SUPPORTED("The wanted format is not supported"),
    INPUT_FILE_MISSING("The input file is missing"),
    OUTPUT_PROBLEM("The input file is missing");

    private String errorFileType;

    private ErrorFileType(String errorFileType) {
        this.errorFileType = errorFileType ;
    }

    public String getErrorFileType() {
        return  this.errorFileType ;
    }


}
