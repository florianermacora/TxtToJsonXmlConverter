package org.txtToJsonXmlConverter.factory;

import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.txtToJsonXmlConverter.dto.OutputFileDto;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class XmlFactory {

    public void createXmlFile(OutputFileDto outputFileDto, String outputFilePath) throws IOException {
        JacksonXmlModule module = new JacksonXmlModule();
        XmlMapper objectMapper = new XmlMapper(module);

        String xmlString = objectMapper.writeValueAsString(outputFileDto);
        FileWriter fileWriter = new FileWriter(outputFilePath, false);
        BufferedWriter bw = new BufferedWriter(fileWriter);
        bw.write(xmlString);
        bw.close();
    }
}
