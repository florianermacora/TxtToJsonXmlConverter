package org.txtToJsonXmlConverter.factory;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.txtToJsonXmlConverter.dto.OutputFileDto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class JsonFactory {

    public void createJsonFile(OutputFileDto outputFileDto, String outputFilePath) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(outputFileDto);
        FileWriter fileWriter = new FileWriter(outputFilePath, false);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(jsonString);
        bw.close();
    }
}
