package com.paddy.moodle.moodleproject;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.springframework.stereotype.Component;
import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MoodleQuizBuilder {

    Document doc;
    Document quizTemplateDocument = null;

    public MoodleQuizBuilder() {
        try {
            this.doc = getQuizDocument();
            getQuestionTemplateNode();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private List<String> parseAnswerOptions(String answers, MoodleQuiz moodleQuiz) throws Exception {
        List<String> answersList = null;
        String[] ans = answers.split(",");
        answersList = Arrays.stream(ans).collect(Collectors.toList());
        if(answersList.size() <= 0)
            throw new Exception("No answered specified for "+moodleQuiz.getChapterName() + " " +moodleQuiz.getQuestionNumber());
        return answersList;
    }

    public Document buildQuestionsFromCsv(QuestionMetadata questionMetadata,String questionsFileName) throws Exception {
        List<String[]> questionsStringArray = readDataLineByLine(questionsFileName);
        //List<String[]> answerRecords = readDataLineByLine(answersFileName);
        assert questionsStringArray != null;
        List<MoodleQuiz> questionsArray = buildQuestionsList(questionsStringArray);
        int i=0;
        for (MoodleQuiz question: questionsArray
             ) {
            if(i>400) break;
            if(question.getImageFileName().equalsIgnoreCase("X")) continue;
            if(question.getAnswerOptions().size()<=0) continue;
            if(question.getAnswerOptions().get(0).equalsIgnoreCase("DUMMY")) continue;
            //if(question.getQuestionType().equalsIgnoreCase("FIB")) continue;

            Node newNode = setQuestionText(question);
            setQuestionName(newNode,question);
            setAnswerNodes(newNode,question);
            setTags(newNode,question,questionMetadata);
            setQuestionIdNumber(newNode,question,questionMetadata);
            doc.getElementsByTagName("quiz").item(0).appendChild(newNode);
            i++;
        }
        return doc;
        //new FileUtil().writeToFile(doc);
    }

    public Document buildQuestionsFromCsvForChapterNumber(QuestionMetadata questionMetadata,String questionsFileName,int chapterNumber) throws Exception {
        List<String[]> questionsStringArray = readDataLineByLine(questionsFileName);
        //List<String[]> answerRecords = readDataLineByLine(answersFileName);
        assert questionsStringArray != null;
        List<MoodleQuiz> questionsArray = buildQuestionsList(questionsStringArray);
        int i=0;
        for (MoodleQuiz question: questionsArray
        ) {
            if(i>400) break;
            if(chapterNumber!= question.chapterNumber) continue;
            if(question.getImageFileName().equalsIgnoreCase("X")) continue;
            if(question.getAnswerOptions().size()<=0) continue;
            if(question.getAnswerOptions().get(0).equalsIgnoreCase("DUMMY")) continue;
            //if(question.getQuestionType().equalsIgnoreCase("FIB")) continue;

            Node newNode = setQuestionText(question);
            setQuestionName(newNode,question);
            setAnswerNodes(newNode,question);
            setTags(newNode,question,questionMetadata);
            setQuestionIdNumber(newNode,question,questionMetadata);
            doc.getElementsByTagName("quiz").item(0).appendChild(newNode);
            i++;
        }
        return doc;
        //new FileUtil().writeToFile(doc);
    }

    private String getAnswerFraction(MoodleQuiz question) throws Exception {
        if(question.getAnswerOptions().size()==1) return String.valueOf("100.0");
        if(question.getAnswerOptions().size()==2) return String.valueOf("50.0");
        if(question.getAnswerOptions().size()==3) return String.valueOf("33.33333");
        if(question.getAnswerOptions().size()==4) return String.valueOf("25.0");
        throw new Exception("No answered specified for "+question.getChapterName() + " " +question.getQuestionNumber());
    }

    private void setQuestionIdNumber(Node newNode,MoodleQuiz question,QuestionMetadata questionMetadata) throws Exception {
        Node idChild = null;
        NodeList childNodeList = newNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeName().equalsIgnoreCase("idnumber")) {
                idChild = childNodeList.item(i);
            }
        }
        assert idChild != null;
        idChild.setTextContent(question.getChapterName()+"-"+question.getSubChapterName()+"-"+question.getQuestionClassification()+"-"+question.getQuestionNumber()+"-"+questionMetadata.getPublisher());
    }

    private void setTags(Node newNode,MoodleQuiz question,QuestionMetadata questionMetadata) throws Exception {
        Node tagsChild = null;
        NodeList childNodeList = newNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeName().equalsIgnoreCase("tags")) {
                tagsChild = childNodeList.item(i);
            }
        }
        assert tagsChild != null;
        buildTags(doc, tagsChild, question,questionMetadata);
    }

    private void setAnswerNodes(Node newNode,MoodleQuiz question) throws Exception {
        NodeList childNodeList = newNode.getChildNodes();
        List<Node> answerNodes = new ArrayList<>();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeName().equalsIgnoreCase("answer")) {
                //Node cdataChild = childNodeList.item(i).getChildNodes().item(1).getFirstChild();
                Node answerChild = childNodeList.item(i);
                answerNodes.add(answerChild);
            }
        }
        if (question.getQuestionType().equalsIgnoreCase("FIB")) {
            answerNodes.get(0).getChildNodes().item(1).setTextContent(question.getOption1());
            NamedNodeMap namedNodeMap = answerNodes.get(0).getAttributes();
            namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            return;
        }
        if (answerNodes.size() > 0) {
            String newAns = answerNodes.get(0).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option1", question.getOption1());
            answerNodes.get(0).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

            if (question.getAnswerOptions().stream().filter(s -> s.equalsIgnoreCase("a")).count()>0) {
                NamedNodeMap namedNodeMap = answerNodes.get(0).getAttributes();
                namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            }

        }
        if (answerNodes.size() > 1) {
            String newAns = answerNodes.get(1).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option2", question.getOption2());
            answerNodes.get(1).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

            if (question.getAnswerOptions().stream().filter(s -> s.equalsIgnoreCase("b")).count()>0) {
                NamedNodeMap namedNodeMap = answerNodes.get(1).getAttributes();
                namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            }

        }
        if (answerNodes.size() > 2) {
            String newAns = answerNodes.get(2).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option3", question.getOption3());
            answerNodes.get(2).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

            if (question.getAnswerOptions().stream().filter(s -> s.equalsIgnoreCase("c")).count()>0) {
                NamedNodeMap namedNodeMap = answerNodes.get(2).getAttributes();
                namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            }

        }
        if (answerNodes.size() > 3) {
            String newAns = answerNodes.get(3).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option4", question.getOption4());
            answerNodes.get(3).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

            if (question.getAnswerOptions().stream().filter(s -> s.equalsIgnoreCase("d")).count()>0) {
                NamedNodeMap namedNodeMap = answerNodes.get(3).getAttributes();
                namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            }

        }
    }

    private void setQuestionName(Node newNode,MoodleQuiz question){
        NodeList childNodeList = newNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeName().equalsIgnoreCase("name")) {
                childNodeList.item(i).getChildNodes().item(1).setTextContent("Chapter "+question.getChapterNumber() + "-" +question.getChapterName() + "-" + question.getSubChapterName()+ "-"  + question.getQuestionClassification() + "-" + question.getQuestionNumber());
            }
        }
    }

    private Node setQuestionText(MoodleQuiz question) throws Exception {
        Node newNode = getQuestionTemplateNode(question);
        NodeList childNodeList = newNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeName().equalsIgnoreCase("questiontext")) {
                Node questionTextNode = childNodeList.item(i);
                Node cdataChild = childNodeList.item(i).getChildNodes().item(1).getFirstChild();
                String questionText = childNodeList.item(i).getChildNodes().item(1).getTextContent();
                String temp1 = questionText.replace("@questiontext", question.getQuestionText());
                String modifiedQuestionText = temp1.replace("\n", " <br> ");

                if(!question.getImageFileName().isEmpty()){
                    modifiedQuestionText = appendImageFileTag(question, modifiedQuestionText,questionTextNode,doc);
                }
                //CDATASection cdata = doc.createCDATASection("data");
                //cdata.setTextContent(temp);
                cdataChild.setTextContent(modifiedQuestionText);
                break;
            }
        }
        return newNode;
    }

    private String appendImageFileTag(MoodleQuiz question,String questionText,Node textNode,Document doc) throws IOException {
        File file = new FileUtil().readFileFromPath("/home/pradeep/Documents/moodle/Class7/BeToppers-Physics-Class-7/"+question.getImageFileName()+".png");
        String tag = "<p dir=\"ltr\" style=\"text-align: left;\"><img src=\"@@PLUGINFILE@@/"+question.getImageFileName()+".png"+"?time=1638678515074\" alt=\"\" width=\"503\" height=\"180\" role=\"presentation\" class=\"img-responsive atto_image_button_text-bottom\"><br></p>";
        Element fileElement = doc.createElement("file");
        fileElement.setAttribute("name",question.getImageFileName()+".png");
        fileElement.setAttribute("path","/");
        fileElement.setAttribute("encoding","base64");

        InputStream iSteamReader = null;
        iSteamReader = new FileInputStream(file);
        String base64 = Base64.getEncoder().encodeToString(iSteamReader.readAllBytes());
        fileElement.setTextContent(base64);
        textNode.appendChild(fileElement);
        return questionText + tag;
    }

    private void getQuestionTemplateNode() throws ParserConfigurationException, IOException, SAXException {
        File file = new FileUtil().readFileFromResources("quiz_template.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setCoalescing(false);
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        DocumentBuilder db = dbf.newDocumentBuilder();
        quizTemplateDocument = db.parse(file);
    }
    private Node getQuestionTemplateNode(MoodleQuiz question) throws Exception {
        Node templateNode = null;
        if((question.getQuestionType().equalsIgnoreCase("MCQ") || question.getQuestionType().isEmpty()) && question.getAnswerOptions().size()==1)
            return getMultiChoiceTemplateNode();
        if(question.getQuestionType().equalsIgnoreCase("FIB"))
            return getShortAnswerTemplateNode();
        if(question.getQuestionType().equalsIgnoreCase("MAQ") || question.getAnswerOptions().size()>1)
            return getMultiChoiceSetTemplateNode();
        throw new Exception("Wring Question Type");
    }
    private Node getShortAnswerTemplateNode() throws Exception {

        System.out.println("Root Element :" + quizTemplateDocument.getDocumentElement().getNodeName());
        System.out.println("------");
        NodeList list = quizTemplateDocument.getElementsByTagName("question");
        Node templateNode = null;
        for (int temp = 0; temp < list.getLength(); temp++) {
            templateNode = list.item(temp);
            String type = templateNode.getAttributes().getNamedItem("type").getNodeValue();
            System.out.println("templateNode=>"+templateNode.getAttributes().getNamedItem("type"));
            if(type.equalsIgnoreCase("shortanswer"))
                return doc.importNode(templateNode,true);
        }
        throw new Exception("Error in Quiz template!!");
    }
    private Node getMultiChoiceTemplateNode() throws Exception {
        System.out.println("Root Element :" + quizTemplateDocument.getDocumentElement().getNodeName());
        System.out.println("------");
        NodeList list = quizTemplateDocument.getElementsByTagName("question");
        Node templateNode = null;
        for (int temp = 0; temp < list.getLength(); temp++) {
            templateNode = list.item(temp);
            String type = templateNode.getAttributes().getNamedItem("type").getNodeValue();
            System.out.println("templateNode=>"+templateNode.getAttributes().getNamedItem("type"));
            if(type.equalsIgnoreCase("multichoice"))
                return doc.importNode(templateNode,true);
        }
        throw new Exception("Error in Quiz template!!");
    }
    private Node getMultiChoiceSetTemplateNode() throws Exception {
        System.out.println("Root Element :" + quizTemplateDocument.getDocumentElement().getNodeName());
        System.out.println("------");
        NodeList list = quizTemplateDocument.getElementsByTagName("question");
        Node templateNode = null;
        for (int temp = 0; temp < list.getLength(); temp++) {
            templateNode = list.item(temp);
            String type = templateNode.getAttributes().getNamedItem("type").getNodeValue();
            System.out.println("templateNode=>"+templateNode.getAttributes().getNamedItem("type"));
            if(type.equalsIgnoreCase("multichoiceset"))
                return doc.importNode(templateNode,true);
        }
        throw new Exception("Error in Quiz template!!");
    }

    private Document getQuizDocument() throws ParserConfigurationException, IOException, SAXException {
        //File file = new FileUtil().readFileFromResources("quiz_template.xml");
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setCoalescing(false);
        dbf.setFeature(XMLConstants.FEATURE_SECURE_PROCESSING, true);
        // parse XML file
        DocumentBuilder db = dbf.newDocumentBuilder();
        //Document doc = db.parse(file);
        Document newDoc = db.newDocument();
        Element root = newDoc.createElement("quiz");
        newDoc.appendChild(root);
        newDoc.getDocumentElement().normalize();
        return newDoc;
    }

    private List<MoodleQuiz> buildQuestionsList(List<String[]> questionsStringArray) throws Exception {
        List<MoodleQuiz> questionsArray = new ArrayList<>();
        for (String[] x : questionsStringArray) {
            MoodleQuiz moodleQuiz = new MoodleQuiz();
            moodleQuiz.setChapterNumber(Integer.parseInt(x[0]));
            moodleQuiz.setChapterName(x[1]);
            moodleQuiz.setSubChapterName(x[2]);
            moodleQuiz.setQuestionClassification(x[3]);
            moodleQuiz.setQuestionNumber(Integer.parseInt(x[4]));
            moodleQuiz.setQuestionText(x[5]);
            moodleQuiz.setOption1(x[6]);
            moodleQuiz.setOption2(x[7]);
            moodleQuiz.setOption3(x[8]);
            moodleQuiz.setOption4(x[9]);
            moodleQuiz.setImageFileName(x[10]);
            moodleQuiz.setAnswerOptions(parseAnswerOptions(x[11],moodleQuiz));
            moodleQuiz.setAnswerDescription(x[12]);
            moodleQuiz.setQuestionType(x[13]);
            questionsArray.add(moodleQuiz);
        }
        return questionsArray;
    }


    private List<String[]> readDataLineByLine(String fileName)
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


    private void buildTags(Document doc,Node tagsNode,MoodleQuiz question,QuestionMetadata questionMetadata){
        Element tag = doc.createElement("tag");
        Element text = doc.createElement("text");
        text.setTextContent(question.getChapterName());
        tag.appendChild(text);
        tagsNode.appendChild(tag);

        Element tag1 = doc.createElement("tag");
        Element text1 = doc.createElement("text");
        text1.setTextContent(questionMetadata.getForClass());
        tag1.appendChild(text1);
        tagsNode.appendChild(tag1);

        Element tag2 = doc.createElement("tag");
        Element text2 = doc.createElement("text");
        text2.setTextContent(questionMetadata.getPublisher());
        tag2.appendChild(text2);
        tagsNode.appendChild(tag2);

        Element tag3 = doc.createElement("tag");
        Element text3 = doc.createElement("text");
        text3.setTextContent(question.getComplexity());
        tag3.appendChild(text3);
        tagsNode.appendChild(tag3);

        Element tag4 = doc.createElement("tag");
        Element text4 = doc.createElement("text");
        text4.setTextContent(question.getQuestionType());
        tag4.appendChild(text4);
        tagsNode.appendChild(tag4);

        Element tag5 = doc.createElement("tag");
        Element text5 = doc.createElement("text");
        text5.setTextContent(questionMetadata.getSubject());
        tag5.appendChild(text5);
        tagsNode.appendChild(tag5);

        Element tag6 = doc.createElement("tag");
        Element text6 = doc.createElement("text");
        text5.setTextContent(question.getSubChapterName());
        tag5.appendChild(text6);
        tagsNode.appendChild(tag6);

        Element tag7 = doc.createElement("tag");
        Element text7 = doc.createElement("text");
        text5.setTextContent(question.getQuestionClassification());
        tag5.appendChild(text7);
        tagsNode.appendChild(tag7);

    }




}
