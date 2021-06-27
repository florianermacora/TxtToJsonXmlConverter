package org.txtToJsonXmlConverter.businessRule;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.txtToJsonXmlConverter.dao.ReferenceDao;
import org.txtToJsonXmlConverter.dto.ErrorDto;
import org.txtToJsonXmlConverter.dto.ReferenceDto;
import org.txtToJsonXmlConverter.enumeration.ColorType;
import org.txtToJsonXmlConverter.enumeration.ErrorDataType;


import java.util.ArrayList;

public class DataTruster {

    private static final Integer NUMBER_OF_ELEMENT = 4;
    private static final Integer NUM_REF_LENGTH = 10;
    private static final String SEPARATOR = ";";

    @Getter
    @Setter
    private ArrayList<ErrorDto> errors;
    @Getter
    @Setter
    private ReferenceDto referenceDto;
    @Getter
    private Boolean isDataFail;

    private Boolean refNumNotError;
    private Boolean typeNotError;
    private Boolean priceNotError;
    private Boolean sizeNotError;
    private Boolean allNotError;

    public DataTruster() {
        errors = new ArrayList<>();
        refNumNotError = true;
        typeNotError = true;
        priceNotError = true;
        sizeNotError = true;
        allNotError = true;
        isDataFail = false;
    }

    public void checkData(String line, Integer lineNumber) {
        String[] explodeLine = line.split(SEPARATOR);

        // Vérification de l'éxistance de chaque élément
        ReferenceDao reference = checkExplodeStr(explodeLine, line, lineNumber);


        // Vérification unitaire sur chaque élément
        if (allNotError) {
            if (refNumNotError) {
                checkNumRef(reference.getNumReference(), lineNumber, line);
            }
            if (typeNotError) {
                checkType(reference.getType(), lineNumber, line);
            }
            if (priceNotError) {
                checkPrice(reference.getPrice(), lineNumber, line);
            }
            if (sizeNotError) {
                checkSize(reference.getSize(), lineNumber, line);
            }
        }

        // Alimentation de la donnée finale une fois vérifiée
        if (!allNotError || !refNumNotError || !typeNotError || !priceNotError || !sizeNotError) {
            isDataFail = true;
        } else {
            referenceDto = new ReferenceDto(reference.getNumReference(), reference.getType(),
                    Double.parseDouble(reference.getPrice()), Integer.parseInt(reference.getSize()));
        }

    }

    /**
     * @param size
     * @param lineNumber
     * @param line       Contrôle sur la taille
     */

    private void checkSize(String size, Integer lineNumber, String line) {
        try {
            Integer.parseInt(size);
        } catch (NumberFormatException e) {
            addError(line, lineNumber, ErrorDataType.SIZE_NOT_NUM.getErrorDataType());
            sizeNotError = false;
        }
    }

    /**
     * @param price
     * @param lineNumber
     * @param line       Contrôle sur le prix
     */

    private void checkPrice(String price, Integer lineNumber, String line) {
        // Vérification que le prix est un nombre
        try {
            Double.parseDouble(price);
        } catch (NumberFormatException e) {
            addError(line, lineNumber, ErrorDataType.PRICE_NOT_A_NUM.getErrorDataType());
            priceNotError = false;
        }

    }

    /**
     * @param type
     * @param lineNumber
     * @param line       Contrôle sur la couleur
     */

    private void checkType(String type, Integer lineNumber, String line) {
        if (!ColorType.contains(type)) {
            addError(line, lineNumber, ErrorDataType.WRONG_COLOR.getErrorDataType());
            typeNotError = false;
        }
    }

    /**
     * @param numReference
     * @param lineNumber
     * @param line         Contrôle sur le numéro de référence
     */

    private void checkNumRef(String numReference, Integer lineNumber, String line) {
        // Vérification que le numéro de référence est un nombre
        try {
            Integer.parseInt(numReference);
        } catch (NumberFormatException e) {
            addError(line, lineNumber, ErrorDataType.NUM_REF_NOT_NUM.getErrorDataType());
            refNumNotError = false;
        }

        // Contrôle sur la longueur
        if (numReference.length() != NUM_REF_LENGTH && refNumNotError) {
            addError(line, lineNumber, ErrorDataType.WRONG_LENGTH.getErrorDataType() + NUM_REF_LENGTH);
            refNumNotError = false;
        }

    }

    /**
     * @param explodeLine
     * @param line
     * @param lineNumber
     * @return Vérification de l'existence des éléments
     */

    private ReferenceDao checkExplodeStr(String[] explodeLine, String line, Integer lineNumber) {
        ReferenceDao reference = new ReferenceDao();

        for (int i = 0; i < explodeLine.length; i++) {
            if (i > NUMBER_OF_ELEMENT) {
                addError(line, lineNumber, ErrorDataType.TOO_MUCH_ELEMENT.getErrorDataType());
                allNotError = false;
                break;
            }
            if (explodeLine[i] != null && !explodeLine[i].isEmpty()) {
                switch (i) {
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
            } else {
                switch (i) {
                    case 0:
                        addError(line, lineNumber, ErrorDataType.NUM_REFERENCE_MISSING.getErrorDataType());
                        allNotError = false;
                        break;
                    case 1:
                        addError(line, lineNumber, ErrorDataType.TYPE_MISSING.getErrorDataType());
                        allNotError = false;
                        break;
                    case 2:
                        addError(line, lineNumber, ErrorDataType.PRICE_MISSING.getErrorDataType());
                        allNotError = false;
                        break;
                    default:
                        addError(line, lineNumber, ErrorDataType.SIZE_MISSING.getErrorDataType());
                        allNotError = false;
                        break;
                }
            }
        }
        return reference;
    }

    /**
     * Alimentation du rapport d'erreur
     */

    private void addError(String line, Integer lineNumber, String errorType) {

        ErrorDto error = new ErrorDto(line, errorType, lineNumber);
        errors.add(error);

    }
}
