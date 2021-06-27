package org.txtToJsonXmlConverter.dto;

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


    public ReferenceDto(){}

    public ReferenceDto(String numReference, String type, double price, int size) {
        this.numReference = numReference;
        this.type = type;
        this.price= price;
        this.size= size;
    }
}
