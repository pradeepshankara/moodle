package com.paddy.moodle.moodleproject;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileUtil {

    public File readFileFromResources(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    public File readFileFromPath(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(fileName);
    }

    public void writeToFile(Document doc,String outputFileName,int index) throws IOException, TransformerException {
        DOMSource source = new DOMSource(doc);
        FileWriter writer = new FileWriter(new File("/home/pradeep/Documents/moodle/"+outputFileName+"-"+index+".xml"));
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty("method", "html");
        transformer.transform(source, result);
    }

    public List<String[]> readCsvContent(String fileName)
    {
        try {
            FileReader filereader = new FileReader(new FileUtil().readFileFromResources(fileName));
            CSVReader csvReader = new CSVReaderBuilder(filereader)
                    //.withFieldAsNull(CSVReaderNullFieldIndicator.EMPTY_SEPARATORS)
                    // Skip the header
                    .withSkipLines(1)
                    .build();
            String[] nextRecord;
            List<String[]> allRecords = new ArrayList<>();
            while ((nextRecord = csvReader.readNext()) != null) {
                allRecords.add(nextRecord);
            }
            return allRecords;
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
