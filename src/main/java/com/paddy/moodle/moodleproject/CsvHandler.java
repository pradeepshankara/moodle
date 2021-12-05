package com.paddy.moodle.moodleproject;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
import javax.xml.XMLConstants;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class CsvHandler {

    public void getXmlDocument() throws IOException, XMLStreamException {
        File file = readFileFromResources("quiz.xml");
        XMLInputFactory f = XMLInputFactory.newFactory();
        XMLStreamReader sr = f.createXMLStreamReader(new FileInputStream(file));
        sr.next();
        XmlMapper xmlMapper = new XmlMapper();
        List<XmlElement> countries = xmlMapper.readValue(file, new TypeReference<List<XmlElement>>() {
        });
        System.out.println("End");
    }

    public void processXmlDocument(String questionsFileName,String answersFileName,String classTag,String publisherTag, String difficultyTag,String questionTypeTag,String subjectTag) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File file = readFileFromResources("quiz_template.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setCoalescing(false);
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        // parse XML file
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        //Document newDoc = db.newDocument();
        //newDoc.appendChild(doc.getFirstChild().cloneNode(true));
        doc.getDocumentElement().normalize();

        System.out.println("Root Element :" + doc.getDocumentElement().getNodeName());
        System.out.println("------");
        NodeList list = doc.getElementsByTagName("question");
        Node templateNode = null;
        for (int temp = 0; temp < list.getLength(); temp++) {
            templateNode = list.item(temp);
            System.out.println("templateNode=>"+templateNode.getAttributes().getNamedItem("type"));
        }
        List<String[]> allrecords = readDataLineByLine(questionsFileName);
        List<String[]> answerRecords = readDataLineByLine(answersFileName);
        Node finalTemplateNode = templateNode;

        int z=0;
        for (String[] x : allrecords) {
            if(z==2) break;
            Node newNode = finalTemplateNode.cloneNode(true);
            NodeList childNodeList = newNode.getChildNodes();
            List<Node> answerNodes = new ArrayList<>();
            Node tagsChild = null;


            for (int i = 0; i < childNodeList.getLength(); i++) {

                if (childNodeList.item(i).getNodeName().equalsIgnoreCase("name")) {
                    childNodeList.item(i).getChildNodes().item(1).setTextContent(x[0] + "-" + x[1] + "-" + x[2]);
                }
                if (childNodeList.item(i).getNodeName().equalsIgnoreCase("questiontext")) {
                    Node questionTextNode = childNodeList.item(i);
                    Node cdataChild = childNodeList.item(i).getChildNodes().item(1).getFirstChild();
                    String questionText = childNodeList.item(i).getChildNodes().item(1).getTextContent();
                    String temp1 = questionText.replace("@questiontext", x[3]);
                    String temp = temp1.replace("\n", " <br> ");

                    if(x[8].equalsIgnoreCase("X")){
                        temp = appendImageFileTag(temp,questionTextNode,doc);
                    }
                    //CDATASection cdata = doc.createCDATASection("data");
                    //cdata.setTextContent(temp);
                    cdataChild.setTextContent(temp);

                }
                if (childNodeList.item(i).getNodeName().equalsIgnoreCase("answer")) {
                    //Node cdataChild = childNodeList.item(i).getChildNodes().item(1).getFirstChild();
                    Node answerChild = childNodeList.item(i);
                    answerNodes.add(answerChild);
                }
                if (childNodeList.item(i).getNodeName().equalsIgnoreCase("tags")) {
                    tagsChild = childNodeList.item(i);
                }
            }
            assert tagsChild != null;
            buildTags(doc, tagsChild, x[0], classTag, publisherTag, difficultyTag, questionTypeTag, subjectTag);
            List<String[]> answerRecord = answerRecords.stream().filter(a -> (a[0].equalsIgnoreCase(x[0]) && a[1].equalsIgnoreCase(x[1]) && a[2].equalsIgnoreCase(x[2]))).collect(Collectors.toList());
            if (answerRecord.size() <= 0)
                throw new RuntimeException("No Answer Entry Available for " + x[0] + " - " + x[1] + " - " + x[2]);


            if (answerNodes.size() > 0) {
                String newAns = answerNodes.get(0).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option1", x[4]);
                answerNodes.get(0).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

                if (answerRecord.get(0)[3].equalsIgnoreCase("a")) {
                    NamedNodeMap namedNodeMap = answerNodes.get(0).getAttributes();
                    namedNodeMap.getNamedItem("fraction").setTextContent("100");
                }

            }
            if (answerNodes.size() > 1) {
                String newAns = answerNodes.get(1).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option2", x[5]);
                answerNodes.get(1).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

                if (answerRecord.get(0)[3].equalsIgnoreCase("b")) {
                    NamedNodeMap namedNodeMap = answerNodes.get(1).getAttributes();
                    namedNodeMap.getNamedItem("fraction").setTextContent("100");
                }

            }
            if (answerNodes.size() > 2) {
                String newAns = answerNodes.get(2).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option3", x[6]);
                answerNodes.get(2).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

                if (answerRecord.get(0)[3].equalsIgnoreCase("c")) {
                    NamedNodeMap namedNodeMap = answerNodes.get(2).getAttributes();
                    namedNodeMap.getNamedItem("fraction").setTextContent("100");
                }

            }
            if (answerNodes.size() > 3) {
                String newAns = answerNodes.get(3).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option4", x[7]);
                answerNodes.get(3).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

                if (answerRecord.get(0)[3].equalsIgnoreCase("d")) {
                    NamedNodeMap namedNodeMap = answerNodes.get(3).getAttributes();
                    namedNodeMap.getNamedItem("fraction").setTextContent("100");
                }
            }
            doc.getElementsByTagName("quiz").item(0).appendChild(newNode);
            z++;
        }
        writeToFile(doc);
    }

    private String appendImageFileTag(String questionText,Node textNode,Document doc){
        File file = readFileFromResources("PPBC4A1Q3.JPG");
        String tag = "<p dir=\"ltr\" style=\"text-align: left;\"><img src=\"@@PLUGINFILE@@/SOMERANDOMIMAGE.JPG?time=1638678515074\" alt=\"\" width=\"503\" height=\"180\" role=\"presentation\" class=\"img-responsive atto_image_button_text-bottom\"><br></p>";
        Element fileElement = doc.createElement("file");
        fileElement.setAttribute("name","SOMERANDOMIMAGE.JPG");
        fileElement.setAttribute("path","/");
        fileElement.setAttribute("encoding","base64");

        InputStream iSteamReader = null;
        try {
            iSteamReader = new FileInputStream(file);
            String base64 = Base64.getEncoder().encodeToString(iSteamReader.readAllBytes());
            fileElement.setTextContent(base64);
            textNode.appendChild(fileElement);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return questionText + tag;
    }

    private void buildTags(Document doc,Node tagsNode,String chapterTag,String classTag,String publisherTag, String difficultyTag, String questionTypeTag,String subjectTag){
        Element tag = doc.createElement("tag");
        Element text = doc.createElement("text");
        text.setTextContent(chapterTag);
        tag.appendChild(text);
        tagsNode.appendChild(tag);

        Element tag1 = doc.createElement("tag");
        Element text1 = doc.createElement("text");
        text1.setTextContent(classTag);
        tag1.appendChild(text1);
        tagsNode.appendChild(tag1);

        Element tag2 = doc.createElement("tag");
        Element text2 = doc.createElement("text");
        text2.setTextContent(publisherTag);
        tag2.appendChild(text2);
        tagsNode.appendChild(tag2);

        Element tag3 = doc.createElement("tag");
        Element text3 = doc.createElement("text");
        text3.setTextContent(difficultyTag);
        tag3.appendChild(text3);
        tagsNode.appendChild(tag3);

        Element tag4 = doc.createElement("tag");
        Element text4 = doc.createElement("text");
        text4.setTextContent(questionTypeTag);
        tag4.appendChild(text4);
        tagsNode.appendChild(tag4);

        Element tag5 = doc.createElement("tag");
        Element text5 = doc.createElement("text");
        text5.setTextContent(subjectTag);
        tag5.appendChild(text5);
        tagsNode.appendChild(tag5);

    }

    private void writeToFile(Document doc) throws IOException, TransformerException {
        DOMSource source = new DOMSource(doc);
        FileWriter writer = new FileWriter(new File("/home/pradeep/Documents/moodle/output.xml"));
        StreamResult result = new StreamResult(writer);

        TransformerFactory transformerFactory = TransformerFactory.newDefaultInstance();
        Transformer transformer = transformerFactory.newTransformer();
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "no");
        transformer.setOutputProperty("method", "html");
        transformer.transform(source, result);
    }




    public List<String[]> readDataLineByLine(String fileName)
    {
        try {
            FileReader filereader = new FileReader(readFileFromResources(fileName));
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
    public File readFileFromResources(String fileName){
        ClassLoader classLoader = getClass().getClassLoader();
        return new File(classLoader.getResource(fileName).getFile());
    }
}
