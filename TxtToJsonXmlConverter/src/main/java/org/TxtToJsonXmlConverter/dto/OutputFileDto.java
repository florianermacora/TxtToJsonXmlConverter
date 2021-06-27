package org.TxtToJsonXmlConverter.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

public class OutputFileDto {

    @Getter
    @Setter
    private String fileName;
    @Getter
    @Setter
    private ArrayList<ReferenceDto> references;
    @Getter
    @Setter
    private ArrayList<ErrorDto> errors;
}
