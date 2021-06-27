package org.txtToJsonXmlConverter.dto;

import lombok.Getter;
import lombok.Setter;

public class ErrorDto {

    @Getter
    @Setter
    private Integer line;
    @Getter
    @Setter
    private String message;
    @Getter
    @Setter
    private String value;

    public ErrorDto(String line, String errorType, Integer lineNumber) {
        this.line = lineNumber;
        this.message = errorType;
        this.value = line;
    }
}
