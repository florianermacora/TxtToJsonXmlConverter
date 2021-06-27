package org.txtToJsonXmlConverter.businessRule;

import org.junit.Before;
import org.junit.Test;
import org.txtToJsonXmlConverter.dto.ErrorDto;
import org.txtToJsonXmlConverter.dto.ReferenceDto;
import org.txtToJsonXmlConverter.enumeration.ErrorDataType;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class DataTrusterTest {

    DataTruster dataTrusterTest;
    String separator;

    @Before
    public void setUp() throws Exception {
        System.out.println("Setting it up!");
        dataTrusterTest = new DataTruster();
        separator = ";";

    }

    @Test
    public void casNominal() {
        dataTrusterTest = new DataTruster();
        separator = ";";
        System.out.println("Running : Cas Nominal");

        String line = "1460100040;R;45.12;27";
        Integer lineNumber = 1;

        dataTrusterTest.checkData(line, lineNumber);

        ReferenceDto reference = new ReferenceDto("1460100040", "R", 45.12, 27);
        ArrayList<ErrorDto> errors = new ArrayList<>();

        assertEquals(false, dataTrusterTest.getIsDataFail());
        assertEquals(errors, dataTrusterTest.getErrors());
        assertEquals(reference.getNumReference(), dataTrusterTest.getReferenceDto().getNumReference());
        assertEquals(reference.getType(), dataTrusterTest.getReferenceDto().getType());
        assertEquals(reference.getPrice(), dataTrusterTest.getReferenceDto().getPrice());
        assertEquals(reference.getSize(), dataTrusterTest.getReferenceDto().getSize());
    }

    @Test
    public void casRefNumLengthDiff10() {
        dataTrusterTest = new DataTruster();
        separator = ";";
        System.out.println("Running : length Ref Number different of 10");

        String line = "146010000;R;45.12;27";
        Integer lineNumber = 2;

        dataTrusterTest.checkData(line, lineNumber);

        ReferenceDto reference = new ReferenceDto();

        ArrayList<ErrorDto> errors = new ArrayList<>();
        ErrorDto error = new ErrorDto(line, ErrorDataType.WRONG_LENGTH.getErrorDataType() + 10, lineNumber);
        errors.add(error);


        assertEquals(true, dataTrusterTest.getIsDataFail());
        assertEquals(1, dataTrusterTest.getErrors().size());
        assertEquals(error.getLine(), dataTrusterTest.getErrors().get(0).getLine());
        assertEquals(ErrorDataType.WRONG_LENGTH.getErrorDataType() + 10, dataTrusterTest.getErrors().get(0).getMessage());
        assertEquals(line, dataTrusterTest.getErrors().get(0).getValue());
        assertEquals(null, dataTrusterTest.getReferenceDto());
    }

    @Test
    public void casRefNumNotANum() {
        dataTrusterTest = new DataTruster();
        separator = ";";
        System.out.println("Running : length Ref Number not a number");

        String line = "ABC;R;45.12;27";
        Integer lineNumber = 2;

        dataTrusterTest.checkData(line, lineNumber);

        ReferenceDto reference = new ReferenceDto();

        ArrayList<ErrorDto> errors = new ArrayList<>();
        ErrorDto error = new ErrorDto(line, ErrorDataType.WRONG_LENGTH.getErrorDataType() + 10, lineNumber);
        errors.add(error);


        assertEquals(true, dataTrusterTest.getIsDataFail());
        assertEquals(1, dataTrusterTest.getErrors().size());
        assertEquals(error.getLine(), dataTrusterTest.getErrors().get(0).getLine());
        assertEquals(ErrorDataType.NUM_REF_NOT_NUM.getErrorDataType(), dataTrusterTest.getErrors().get(0).getMessage());
        assertEquals(line, dataTrusterTest.getErrors().get(0).getValue());
        assertEquals(null, dataTrusterTest.getReferenceDto());
    }

    @Test
    public void casRefIsMissing() {
        dataTrusterTest = new DataTruster();
        separator = ";";
        System.out.println("Running : Ref Number is missing");

        String line = ";Z;45.12;27";
        Integer lineNumber = 2;

        dataTrusterTest.checkData(line, lineNumber);

        ReferenceDto reference = new ReferenceDto();

        ArrayList<ErrorDto> errors = new ArrayList<>();
        ErrorDto error = new ErrorDto(line, ErrorDataType.WRONG_LENGTH.getErrorDataType() + 10, lineNumber);
        errors.add(error);


        assertEquals(true, dataTrusterTest.getIsDataFail());
        assertEquals(1, dataTrusterTest.getErrors().size());
        assertEquals(error.getLine(), dataTrusterTest.getErrors().get(0).getLine());
        assertEquals(ErrorDataType.NUM_REFERENCE_MISSING.getErrorDataType(), dataTrusterTest.getErrors().get(0).getMessage());
        assertEquals(line, dataTrusterTest.getErrors().get(0).getValue());
        assertEquals(null, dataTrusterTest.getReferenceDto());
    }

    @Test
    public void casRefTypeWrongColor() {
        dataTrusterTest = new DataTruster();
        separator = ";";
        System.out.println("Running : Wrong Color");

        String line = "1460100040;Z;45.12;27";
        Integer lineNumber = 2;

        dataTrusterTest.checkData(line, lineNumber);

        ReferenceDto reference = new ReferenceDto();

        ArrayList<ErrorDto> errors = new ArrayList<>();
        ErrorDto error = new ErrorDto(line, ErrorDataType.WRONG_LENGTH.getErrorDataType() + 10, lineNumber);
        errors.add(error);


        assertEquals(true, dataTrusterTest.getIsDataFail());
        assertEquals(1, dataTrusterTest.getErrors().size());
        assertEquals(error.getLine(), dataTrusterTest.getErrors().get(0).getLine());
        assertEquals(ErrorDataType.WRONG_COLOR.getErrorDataType(), dataTrusterTest.getErrors().get(0).getMessage());
        assertEquals(line, dataTrusterTest.getErrors().get(0).getValue());
        assertEquals(null, dataTrusterTest.getReferenceDto());
    }
}
