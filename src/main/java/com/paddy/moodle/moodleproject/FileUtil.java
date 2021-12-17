package com.paddy.moodle.moodleproject;

import org.w3c.dom.Document;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileUtil {

    public File readFileFromResources(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }

    public File readFileFromPath(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(fileName);
    }

    public void writeToFile(Document doc) throws IOException, TransformerException {
        DOMSource source = new DOMSource(doc);
        FileWriter writer = new FileWriter(new File("/home/pradeep/Documents/moodle/output.xml"));
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty("method", "html");
        transformer.transform(source, result);
    }
}
