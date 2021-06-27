import org.TxtToJsonXmlConverter.businessRule.DataTruster;
import org.TxtToJsonXmlConverter.enumeration.ErrorFileType;
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
    private static final String SEPARATOR = ";";

    public static void main(String[] args) {
        // Contrôle sur les valeurs d'entrée
        if (args.length < NUMBER_PARAMETER){
            System.out.println(ErrorFileType.TOO_LESS_ARG.getErrorFileType());
        }
        else if (args.length > NUMBER_PARAMETER){
            System.out.println(ErrorFileType.TOO_MUCH_ARG.getErrorFileType());
        }
        else if (!args[1].equals(FORMAT_XML) && !args[1].equals(FORMAT_JSON)){
            System.out.println(ErrorFileType.FORMAT_NOT_SUPPORTED.getErrorFileType());
        }
        else{
            try {

                // Lecture du fichier
                File file = new File(args[0]);
                String fileName = file.getName();
                Scanner myReader = new Scanner(file);

                Integer lineNumber = 1;

                while(myReader.hasNextLine()){
                    // Vérification sur la donnée
                    DataTruster dataTruster = new DataTruster();
                    String data = myReader.nextLine();
                    dataTruster.checkData(data, SEPARATOR, lineNumber);

                    lineNumber ++;
                }
            }
            catch (FileNotFoundException e){
                System.out.println(ErrorFileType.INPUT_FILE_MISSING.getErrorFileType());
            }
        }
    }
}
