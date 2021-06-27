package org.TxtToJsonXmlConverter.businessRule;

import lombok.Getter;
import lombok.Setter;
import org.TxtToJsonXmlConverter.dao.ReferenceDao;
import org.TxtToJsonXmlConverter.dto.ErrorDto;
import org.TxtToJsonXmlConverter.dto.ReferenceDto;
import org.TxtToJsonXmlConverter.enumeration.ColorType;
import org.TxtToJsonXmlConverter.enumeration.ErrorDataType;


import java.util.ArrayList;

public class DataTruster {

    private static final Integer NUMBER_OF_ELEMENT = 4;
    private static final Integer NUM_REF_LENGTH = 10;

    @Getter
    @Setter
    private ArrayList<ErrorDto> errors;
    @Getter
    @Setter
    private ReferenceDto referenceDto;
    @Getter
    private Boolean isDataFail;

    private Boolean refNumNotError = true;
    private Boolean typeNotError = true;
    private Boolean priceNotError = true;
    private Boolean sizeNotError = true;
    private Boolean allNotError = true;

    public DataTruster(){
        errors = new ArrayList<>();
    }

    public void checkData(String line, String separator, Integer lineNumber){
        String[] explodeLine = line.split(separator);

        // Vérification de l'éxistance de chaque élément
        ReferenceDao reference = checkExplodeStr(explodeLine, line, lineNumber);


        // Vérification unitaire sur chaque élément
        if(allNotError){
            if(refNumNotError){
                checkNumRef(reference.getNumReference(), lineNumber, line);
            }
            if(typeNotError){
                checkType(reference.getType(), lineNumber, line);
            }
            if(priceNotError){
                checkPrice(reference.getPrice(), lineNumber, line);
            }
            if(sizeNotError){
                checkSize(reference.getSize(), lineNumber, line);
            }
        }

        if(!allNotError && !refNumNotError && !typeNotError && !priceNotError && !sizeNotError){
            isDataFail = true;
        }
        else{
            referenceDto.setNumReference(reference.getNumReference());;
            referenceDto.setType(reference.getType());
            referenceDto.setPrice(Double.parseDouble(reference.getPrice()));
            referenceDto.setSize(Integer.parseInt(reference.getSize()));
        }

    }

    /**
     *
     * @param size
     * @param lineNumber
     * @param line
     *      Contrôle sur la taille
     */

    private void checkSize(String size, Integer lineNumber, String line) {
        try {
            Integer.parseInt(size);
        }
        catch(NumberFormatException e){
            addError(line, lineNumber, ErrorDataType.SIZE_NOT_NUM.getErrorDataType());
            sizeNotError = false;
        }
    }

    /**
     *
     * @param price
     * @param lineNumber
     * @param line
     *      Contrôle sur le prix
     */

    private void checkPrice(String price, Integer lineNumber, String line) {
        // Vérification que le prix est un nombre
        try {
            Double.parseDouble(price);
        }
        catch(NumberFormatException e){
            addError(line, lineNumber, ErrorDataType.PRICE_NOT_A_NUM.getErrorDataType());
            priceNotError = false;
        }

        // Vérification que le nombre àprès la virgule n'est pas supérieur à 2
        if(priceNotError && price.contains(".") && (price.split("\\.").length >2 ||
                (price.split("\\.").length == 2 && Integer.parseInt(price.split("\\.")[1]) > 2))){
            addError(line, lineNumber, ErrorDataType.NOT_A_PRICE.getErrorDataType());
            priceNotError = false;
        }
    }

    /**
     *
     * @param type
     * @param lineNumber
     * @param line
     *
     *      Contrôle sur la couleur
     */

    private void checkType(String type, Integer lineNumber, String line) {
        if(!ColorType.contains(type)){
            addError(line, lineNumber, ErrorDataType.WRONG_COLOR.getErrorDataType());
            typeNotError = false;
        }
    }

    /**
     *
     * @param numReference
     * @param lineNumber
     * @param line
     *
     *      Contrôle sur le numéro de référence
     */

    private void checkNumRef(String numReference, Integer lineNumber, String line) {
        // Vérification que le numéro de référence est un nombre
        try {
            Integer.parseInt(numReference);
        }
        catch(NumberFormatException e){
            addError(line, lineNumber, ErrorDataType.NUM_REF_NOT_NUM.getErrorDataType());
            refNumNotError = false;
        }

        // Contrôle sur la longueur
        if(numReference.length() != NUM_REF_LENGTH && refNumNotError){
            addError(line, lineNumber, ErrorDataType.WRONG_LENGTH.getErrorDataType() + NUM_REF_LENGTH);
            refNumNotError = false;
        }

    }

    /**
     *
     * @param explodeLine
     * @param line
     * @param lineNumber
     * @return
     *
     *      Vérification de l'existence des éléments
     */

    private ReferenceDao checkExplodeStr(String[] explodeLine, String line, Integer lineNumber) {
        ReferenceDao reference = new ReferenceDao();

        for(int i=0; i < explodeLine.length; i++){
            if(i > NUMBER_OF_ELEMENT){
                addError(line, lineNumber, ErrorDataType.TOO_MUCH_ELEMENT.getErrorDataType());
                allNotError = false;
                break;
            }
            if(explodeLine[i] != null && !explodeLine[i].isEmpty()){
                switch(i){
                    case 0:
                        reference.setNumReference(explodeLine[i]);
                        break;
                    case 1:
                        reference.setType(explodeLine[i]);
                        break;
                    case 2:
                        reference.setPrice(explodeLine[i]);
                        break;
                    default:
                        reference.setSize(explodeLine[i]);
                }
            }
            else{
                switch(i){
                    case 0:
                        addError(line,lineNumber ,ErrorDataType.NUM_REFERENCE_MISSING.getErrorDataType());
                        refNumNotError = false;
                        break;
                    case 1:
                        addError(line,lineNumber ,ErrorDataType.TYPE_MISSING.getErrorDataType());
                        typeNotError = false;
                        break;
                    case 2:
                        addError(line,lineNumber ,ErrorDataType.PRICE_MISSING.getErrorDataType());
                        priceNotError = false;
                        break;
                    default:
                        addError(line,lineNumber ,ErrorDataType.SIZE_MISSING.getErrorDataType());
                        sizeNotError = false;
                        break;
                }
            }
        }
        return reference;
    }

    /**
     * Alimentation du rapport d'erreur
     */

    private void addError(String line, Integer lineNumber, String errorType){

        ErrorDto error = new ErrorDto(line, errorType, lineNumber);
        errors.add(error);

    }
}
