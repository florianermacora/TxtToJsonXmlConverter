import org.txtToJsonXmlConverter.businessRule.DataTruster;
import org.txtToJsonXmlConverter.dto.OutputFileDto;
import org.txtToJsonXmlConverter.enumeration.ErrorFileType;
import org.txtToJsonXmlConverter.factory.JsonFactory;
import org.txtToJsonXmlConverter.factory.XmlFactory;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

@SpringBootApplication
public class Main {

    public static final Integer NUMBER_PARAMETER = 3;
    public static final String FORMAT_JSON = "JSON";
    public static final String FORMAT_XML = "XML";

    public static void main(String[] args) {
        // Contrôle sur les valeurs d'entrée
        if (args.length < NUMBER_PARAMETER) {
            System.out.println(ErrorFileType.TOO_LESS_ARG.getErrorFileType());
        } else if (args.length > NUMBER_PARAMETER) {
            System.out.println(ErrorFileType.TOO_MUCH_ARG.getErrorFileType());
        } else if (!args[1].equals(FORMAT_XML) && !args[1].equals(FORMAT_JSON)) {
            System.out.println(ErrorFileType.FORMAT_NOT_SUPPORTED.getErrorFileType());
        } else {
            try {

                // Lecture du fichier
                File file = new File(args[0]);
                String fileName = file.getName();
                Scanner myReader = new Scanner(file);

                OutputFileDto outputFileDto = new OutputFileDto(fileName);

                Integer lineNumber = 1;

                while (myReader.hasNextLine()) {
                    // Vérification sur la donnée
                    DataTruster dataTruster = new DataTruster();
                    String data = myReader.nextLine();
                    dataTruster.checkData(data, lineNumber);

                    // Créaation de la donnée final
                    if (dataTruster.getIsDataFail()) {
                        outputFileDto.getErrors().addAll(dataTruster.getErrors());
                    } else {
                        outputFileDto.getReferences().add(dataTruster.getReferenceDto());
                    }

                    lineNumber++;
                }
                myReader.close();

                if (args[1].equals(FORMAT_JSON)) {
                    try {
                        JsonFactory jsonFactory = new JsonFactory();
                        jsonFactory.createJsonFile(outputFileDto, args[2]);
                    } catch (IOException e) {
                        System.out.println(ErrorFileType.OUTPUT_PTOBLEM.getErrorFileType());
                    }
                } else {
                    try {
                        XmlFactory xmlFactory = new XmlFactory();
                        xmlFactory.createXmlFile(outputFileDto, args[2]);
                    } catch (IOException e) {
                        System.out.println(ErrorFileType.OUTPUT_PTOBLEM.getErrorFileType());
                    }
                }
            } catch (FileNotFoundException e) {
                System.out.println(ErrorFileType.INPUT_FILE_MISSING.getErrorFileType());
            }
        }
    }
}
