package org.TxtToJsonXmlConverter.dto;

import lombok.Getter;
import lombok.Setter;

public class ReferenceDto {

    @Getter
    @Setter
    private String numReference;
    @Getter
    @Setter
    private String type;
    @Getter
    @Setter
    private Double price;
    @Getter
    @Setter
    private Integer size;


}
