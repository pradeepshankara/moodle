package com.paddy.moodle.moodleproject;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import org.apache.tomcat.util.json.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static Logger logger = LoggerFactory.getLogger(MoodleQuizBuilder.class);
    List<Document> documentList;
    Document quizTemplateDocument = null;
    QuestionMetadata questionMetadata;

    public QuestionMetadata getQuestionMetadata() {
        return questionMetadata;
    }

    public void setQuestionMetadata(QuestionMetadata questionMetadata) {
        this.questionMetadata = questionMetadata;
    }

    public MoodleQuizBuilder() {
        try {
            documentList = new ArrayList<>();
            getQuestionTemplateNode();
        } catch (ParserConfigurationException | IOException | SAXException e) {
            e.printStackTrace();
        }
    }

    private Document getNewXMLDocument() throws ParserConfigurationException, IOException, SAXException {
            Document newDocument = getQuizDocument();
            return newDocument;
   }

    private List<String> parseAnswerOptions(String answers, MoodleQuiz moodleQuiz) throws Exception {
        List<String> answersList = null;
        String[] ans = answers.split(",");
        Arrays.stream(ans).forEach(String::trim);
        answersList = Arrays.stream(ans).collect(Collectors.toList());
        if(answersList.size() <= 0)
            throw new Exception("No answered specified for "+moodleQuiz.getChapterName() + " " +moodleQuiz.getQuestionNumber());
        return answersList;
    }

    private Document getNewDocument(Document newDocument,int i) throws Exception {
        if(i==0) {
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }

        if(i==200) {
            documentList.add(newDocument);
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }
        if(i==400) {
            documentList.add(newDocument);
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }
        if(i==600) {
            documentList.add(newDocument);
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }
        if(i==900) {
            documentList.add(newDocument);
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }
        if(i==1200) {
            documentList.add(newDocument);
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }
        if(i==1500) {
            documentList.add(newDocument);
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }
        if(i==1800) {
            documentList.add(newDocument);
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }
        if(i==2100) {
            documentList.add(newDocument);
            Document newDocument1 = getNewXMLDocument();
            return newDocument1;
        }
        if(i>=2400)
            throw new Exception("Too many questions in the file!!!");
        return newDocument;
    }

    public List<Document> buildQuestionsFromCsv(String questionsFileName) throws Exception {
        List<String[]> questionsStringArray = readDataLineByLine(questionsFileName);
        //List<String[]> answerRecords = readDataLineByLine(answersFileName);
        assert questionsStringArray != null;
        List<MoodleQuiz> questionsArray = buildQuestionsList(questionsStringArray);
        int i=0;
        Document newDocument = null;
        boolean isValid = validateFile(questionsArray);
        if(!isValid)
            throw new Exception("Errors found in input file!!! Check logs");
        for (MoodleQuiz question: questionsArray
             ) {
            newDocument = getNewDocument(newDocument,i);
            if(question.getImageFileName().equalsIgnoreCase("X")) continue;
            if(question.getAnswerOptions().size()<=0) continue;
            if(question.getAnswerOptions().get(0).equalsIgnoreCase("DUMMY")) continue;
            //if(question.getQuestionType().equalsIgnoreCase("FIB")) continue;
            System.out.println(question.getChapterName()+"----"+question.getQuestionNumber());
            Node newNode = setQuestionText(question,newDocument);
            setQuestionName(newNode,question);
            setAnswerNodes(newNode,question);
            setTags(newDocument,newNode,question,questionMetadata);
            setQuestionIdNumber(newNode,question,questionMetadata);
            newDocument.getElementsByTagName("quiz").item(0).appendChild(newNode);
            i++;
        }
        documentList.add(newDocument);
        return documentList;
        //new FileUtil().writeToFile(doc);
    }

    private boolean validateFile(List<MoodleQuiz> questionsArray) throws Exception {
        boolean isValid = true;
        for (MoodleQuiz question: questionsArray
        ) {
            if(question.getImageFileName().equalsIgnoreCase("X")) continue;
            if(question.getAnswerOptions().size()<=0) continue;
            if(question.getAnswerOptions().get(0).equalsIgnoreCase("DUMMY")) continue;
            boolean isTempValid = validateAnswerNodes(question);
            if(!isTempValid){
                isValid = false;
            }
        }
        return isValid;

    }

    public Document buildQuestionsFromCsvForChapterNumber(Document newDocument,QuestionMetadata questionMetadata,String questionsFileName,int chapterNumber) throws Exception {
        if(newDocument==null)
            newDocument = getNewXMLDocument();
        List<String[]> questionsStringArray = readDataLineByLine(questionsFileName);
        //List<String[]> answerRecords = readDataLineByLine(answersFileName);
        assert questionsStringArray != null;
        List<MoodleQuiz> questionsArray = buildQuestionsList(questionsStringArray);
        int i=0;
        boolean isValid = validateFile(questionsArray);
        for (MoodleQuiz question: questionsArray
        ) {
            if(i>300) break;
            if(chapterNumber!= question.chapterNumber) continue;
            if(question.getImageFileName().equalsIgnoreCase("X")) continue;
            //if(question.getAnswerOptions().size()<=0) continue;
            if(question.getAnswerOptions().get(0).equalsIgnoreCase("DUMMY")) continue;
            //if(question.getQuestionType().equalsIgnoreCase("FIB")) continue;

            Node newNode = setQuestionText(question,newDocument);
            setQuestionName(newNode,question);
            setAnswerNodes(newNode,question);
            setTags(newDocument,newNode,question,questionMetadata);
            setQuestionIdNumber(newNode,question,questionMetadata);
            newDocument.getElementsByTagName("quiz").item(0).appendChild(newNode);
            i++;
        }
        return newDocument;
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

    private void setTags(Document newDocument,Node newNode,MoodleQuiz question,QuestionMetadata questionMetadata) throws Exception {
        Node tagsChild = null;
        NodeList childNodeList = newNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeName().equalsIgnoreCase("tags")) {
                tagsChild = childNodeList.item(i);
            }
        }
        assert tagsChild != null;
        buildTags(newDocument, tagsChild, question,questionMetadata);
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
        if (question.getQuestionType().equalsIgnoreCase("TF")) {
            //answerNodes.get(0).getChildNodes().item(1).setTextContent(question.getOption1());
            NamedNodeMap namedNodeMap = null;
            if(question.getOption1().equalsIgnoreCase("true")||question.getOption1().equalsIgnoreCase("1")||
                    question.getAnswerOptions().get(0).equalsIgnoreCase("true")||question.getAnswerOptions().get(0).equalsIgnoreCase("1"))
                namedNodeMap = answerNodes.get(0).getAttributes();
            else if(question.getOption1().equalsIgnoreCase("false")||question.getOption1().equalsIgnoreCase("0")||
                    question.getAnswerOptions().get(0).equalsIgnoreCase("false")||question.getAnswerOptions().get(0).equalsIgnoreCase("0"))
                namedNodeMap = answerNodes.get(1).getAttributes();
            else throw new Exception("Wrong true/false option!!"+ question.getChapterName() + " - "+question.getQuestionText());
            namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            return;
        }
        if (question.getQuestionType().equalsIgnoreCase("FIB")) {
            String ans = null;
            if(!question.getOption1().isEmpty())
                ans = question.getOption1();
            if(!question.getAnswerOptions().get(0).isEmpty())
                ans = question.getAnswerOptions().get(0);
            if(ans.isEmpty() || ans.isBlank())
                throw new Exception("No answer given for the FIB question!!"+ question.getChapterName() + " - "+question.getQuestionText());
            answerNodes.get(0).getChildNodes().item(1).setTextContent(ans);
            NamedNodeMap namedNodeMap = answerNodes.get(0).getAttributes();
            namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            return;
        }
        if (answerNodes.size() > 0) {
            String newAns = answerNodes.get(0).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option1", question.getOption1());
            newAns = replaceSpecialCharacters(newAns);
            answerNodes.get(0).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

            if (question.getAnswerOptions().stream().filter(s -> s.equalsIgnoreCase("a")).count()>0) {
                NamedNodeMap namedNodeMap = answerNodes.get(0).getAttributes();
                namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            }

        }
        if (answerNodes.size() > 1) {
            String newAns = answerNodes.get(1).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option2", question.getOption2());
            newAns = replaceSpecialCharacters(newAns);
            answerNodes.get(1).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

            if (question.getAnswerOptions().stream().filter(s -> s.equalsIgnoreCase("b")).count()>0) {
                NamedNodeMap namedNodeMap = answerNodes.get(1).getAttributes();
                namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            }

        }
        if (answerNodes.size() > 2) {
            String newAns = answerNodes.get(2).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option3", question.getOption3());
            newAns = replaceSpecialCharacters(newAns);
            answerNodes.get(2).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

            if (question.getAnswerOptions().stream().filter(s -> s.equalsIgnoreCase("c")).count()>0) {
                NamedNodeMap namedNodeMap = answerNodes.get(2).getAttributes();
                namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            }

        }
        if (answerNodes.size() > 3) {
            String newAns = answerNodes.get(3).getChildNodes().item(1).getFirstChild().getNextSibling().getTextContent().replace("@option4", question.getOption4());
            newAns = replaceSpecialCharacters(newAns);
            answerNodes.get(3).getChildNodes().item(1).getFirstChild().getNextSibling().setTextContent(newAns);

            if (question.getAnswerOptions().stream().filter(s -> s.equalsIgnoreCase("d")).count()>0) {
                NamedNodeMap namedNodeMap = answerNodes.get(3).getAttributes();
                namedNodeMap.getNamedItem("fraction").setTextContent(getAnswerFraction(question));
            }

        }
    }

    private boolean validateAnswerNodes(MoodleQuiz question) throws Exception {
        Boolean error = false;
        if(!("AR,MCQ,FIB,TF,MAQ").contains(question.getQuestionType()))
        {
            logger.error("Wrong Question type!!"+ question.getQuestionType() + " - "+question.getQuestionText());
            return false;
        }

        if (question.getQuestionType().equalsIgnoreCase("TF")) {
            if(question.getOption1().equalsIgnoreCase("true")||question.getOption1().equalsIgnoreCase("1")||
                    question.getAnswerOptions().get(0).equalsIgnoreCase("true")||question.getAnswerOptions().get(0).equalsIgnoreCase("1"))
                return true;
            else if(question.getOption1().equalsIgnoreCase("false")||question.getOption1().equalsIgnoreCase("0")||
                    question.getAnswerOptions().get(0).equalsIgnoreCase("false")||question.getAnswerOptions().get(0).equalsIgnoreCase("0"))
                return true;
            else {
                logger.error("Wrong true/false option!!"+ question.getChapterName() + " - "+question.getQuestionText());
                return false;
            }
        }
        if (question.getQuestionType().equalsIgnoreCase("FIB")) {

            if(question.getAnswerOptions().size()>1) {
                logger.error("more than one FIB Answers!!" + question.getChapterName() + " - " + question.getQuestionText());
                return false;
            }
            if(question.getAnswerOptions().size()<=0) {
                logger.error("No answer given for the FIB question!!" + question.getChapterName() + " - " + question.getQuestionText());
                return false;
            }
            String ans = question.getOption1();
            if(!ans.isEmpty() && !ans.isBlank()) {
                return true;
            }
            ans = question.getAnswerOptions().get(0);
            if(ans.isEmpty() || ans.isBlank()) {
                logger.error("Answer is blank for the FIB question!!" + question.getChapterName() + " - " + question.getQuestionText());
                return false;
            }
            if(!question.getOption1().isEmpty())
                return true;
            if(!question.getAnswerOptions().get(0).isEmpty())
                return true;
        }
        if (question.getQuestionType().equalsIgnoreCase("MCQ")) {
            for (String x: question.getAnswerOptions()
                 ) {
                if(!(x.toLowerCase().equalsIgnoreCase("a")||x.toLowerCase().equalsIgnoreCase("b")||x.toLowerCase().equalsIgnoreCase("c")||x.toLowerCase().equalsIgnoreCase("d"))){
                    logger.error("Wrong options given for the MCQ question!!"+ question.getChapterName() + " - "+question.getQuestionText());
                    return false;
                }
                if((question.getOption1().isBlank()||question.getOption1().isEmpty()||
                        question.getOption2().isBlank()||question.getOption2().isEmpty()||
                        question.getOption3().isBlank()||question.getOption3().isEmpty()||
                        question.getOption4().isBlank()||question.getOption4().isEmpty()) && question.getImageFileName().isEmpty()
                ){
                    logger.error("Blank options given for the MCQ question!!"+ question.getChapterName() + " - "+question.getQuestionText());
                    return false;
                }
            }
        }
        return true;
    }

    private void setQuestionName(Node newNode,MoodleQuiz question){
        NodeList childNodeList = newNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeName().equalsIgnoreCase("name")) {
                childNodeList.item(i).getChildNodes().item(1).setTextContent("Chapter "+question.getChapterNumber() + "-" +question.getChapterName() + "-" + question.getSubChapterName()+ "-"  + question.getQuestionClassification() + "-" + question.getQuestionNumber());
            }
        }
    }

    private Node setQuestionText(MoodleQuiz question,Document newDocument) throws Exception {
        Node newNode = getQuestionTemplateNode(question,newDocument);
        NodeList childNodeList = newNode.getChildNodes();
        for (int i = 0; i < childNodeList.getLength(); i++) {
            if (childNodeList.item(i).getNodeName().equalsIgnoreCase("questiontext")) {
                Node questionTextNode = childNodeList.item(i);
                Node cdataChild = childNodeList.item(i).getChildNodes().item(1).getFirstChild();
                String questionText = childNodeList.item(i).getChildNodes().item(1).getTextContent();
                String temp1 = questionText.replace("@questiontext", question.getQuestionText());
                String modifiedQuestionText = temp1.replace("\n", " <br> ");
                modifiedQuestionText = replaceSpecialCharacters(modifiedQuestionText);

                if(!question.getImageFileName().isEmpty()){
                    modifiedQuestionText = appendImageFileTag(question, modifiedQuestionText,questionTextNode,newDocument);
                }
                //CDATASection cdata = doc.createCDATASection("data");
                //cdata.setTextContent(temp);
                cdataChild.setTextContent(modifiedQuestionText);
                break;
            }
        }
        return newNode;
    }

    public String replaceSpecialCharacters(String statement){
        String s1 = statement.replace("^","<sup>");
        String s2 = s1.replace("~","<sub>");
        String s3 = s2.replace("&","</sup>");
        return s3.replace("*","</sub>");
    }

    private String appendImageFileTag(MoodleQuiz question,String questionText,Node textNode,Document doc) throws IOException {
        File file = new FileUtil().readFileFromPath(questionMetadata.getImagesPath()+question.getImageFileName()+"."+questionMetadata.getImageType());
        String tag = "<p dir=\"ltr\" style=\"text-align: left;\"><img src=\"@@PLUGINFILE@@/"+question.getImageFileName()+"."+questionMetadata.getImageType()+"?time=1638678515074\" alt=\"\" width=\"503\" height=\"180\" role=\"presentation\" class=\"img-responsive atto_image_button_text-bottom\"><br></p>";
        Element fileElement = doc.createElement("file");
        fileElement.setAttribute("name",question.getImageFileName()+"."+questionMetadata.getImageType());
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
    private Node getQuestionTemplateNode(MoodleQuiz question,Document newDocument) throws Exception {
        Node templateNode = null;
        if((question.getQuestionType().equalsIgnoreCase("AR") || question.getQuestionType().equalsIgnoreCase("MCQ") || question.getQuestionType().isEmpty()) && question.getAnswerOptions().size()==1)
            return getMultiChoiceTemplateNode(newDocument);
        if(question.getQuestionType().equalsIgnoreCase("FIB"))
            return getShortAnswerTemplateNode(newDocument);
        if(question.getQuestionType().equalsIgnoreCase("TF"))
            return getTrueFalseTemplateNode(newDocument);
        if(question.getQuestionType().equalsIgnoreCase("MAQ") || question.getAnswerOptions().size()>1)
            return getMultiChoiceSetTemplateNode(newDocument);
        throw new Exception("Wrong Question Type");
    }
    private Node getShortAnswerTemplateNode(Document newDocument) throws Exception {

        System.out.println("Root Element :" + quizTemplateDocument.getDocumentElement().getNodeName());
        System.out.println("------");
        NodeList list = quizTemplateDocument.getElementsByTagName("question");
        Node templateNode = null;
        for (int temp = 0; temp < list.getLength(); temp++) {
            templateNode = list.item(temp);
            String type = templateNode.getAttributes().getNamedItem("type").getNodeValue();
            System.out.println("templateNode=>"+templateNode.getAttributes().getNamedItem("type"));
            if(type.equalsIgnoreCase("shortanswer"))
                return newDocument.importNode(templateNode,true);
        }
        throw new Exception("Error in Quiz template!!");
    }
    private Node getTrueFalseTemplateNode(Document newDocument) throws Exception {

        System.out.println("Root Element :" + quizTemplateDocument.getDocumentElement().getNodeName());
        System.out.println("------");
        NodeList list = quizTemplateDocument.getElementsByTagName("question");
        Node templateNode = null;
        for (int temp = 0; temp < list.getLength(); temp++) {
            templateNode = list.item(temp);
            String type = templateNode.getAttributes().getNamedItem("type").getNodeValue();
            System.out.println("templateNode=>"+templateNode.getAttributes().getNamedItem("type"));
            if(type.equalsIgnoreCase("truefalse"))
                return newDocument.importNode(templateNode,true);
        }
        throw new Exception("Error in Quiz template!!");
    }
    private Node getMultiChoiceTemplateNode(Document newDocument) throws Exception {
        System.out.println("Root Element :" + quizTemplateDocument.getDocumentElement().getNodeName());
        System.out.println("------");
        NodeList list = quizTemplateDocument.getElementsByTagName("question");
        Node templateNode = null;
        for (int temp = 0; temp < list.getLength(); temp++) {
            templateNode = list.item(temp);
            String type = templateNode.getAttributes().getNamedItem("type").getNodeValue();
            System.out.println("templateNode=>"+templateNode.getAttributes().getNamedItem("type"));
            if(type.equalsIgnoreCase("multichoice"))
                return newDocument.importNode(templateNode,true);
        }
        throw new Exception("Error in Quiz template!!");
    }
    private Node getMultiChoiceSetTemplateNode(Document newDocument) throws Exception {
        System.out.println("Root Element :" + quizTemplateDocument.getDocumentElement().getNodeName());
        System.out.println("------");
        NodeList list = quizTemplateDocument.getElementsByTagName("question");
        Node templateNode = null;
        for (int temp = 0; temp < list.getLength(); temp++) {
            templateNode = list.item(temp);
            String type = templateNode.getAttributes().getNamedItem("type").getNodeValue();
            System.out.println("templateNode=>"+templateNode.getAttributes().getNamedItem("type"));
            if(type.equalsIgnoreCase("multichoiceset"))
                return newDocument.importNode(templateNode,true);
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
        text.setTextContent("ChapterName:"+question.getChapterName());
        tag.appendChild(text);
        tagsNode.appendChild(tag);

        Element tag1 = doc.createElement("tag");
        Element text1 = doc.createElement("text");
        text1.setTextContent("Class:"+questionMetadata.getForClass());
        tag1.appendChild(text1);
        tagsNode.appendChild(tag1);

        Element tag2 = doc.createElement("tag");
        Element text2 = doc.createElement("text");
        text2.setTextContent("Publisher:"+questionMetadata.getPublisher());
        tag2.appendChild(text2);
        tagsNode.appendChild(tag2);

        Element tag3 = doc.createElement("tag");
        Element text3 = doc.createElement("text");
        text3.setTextContent("Complexity:"+question.getComplexity());
        tag3.appendChild(text3);
        tagsNode.appendChild(tag3);

        Element tag4 = doc.createElement("tag");
        Element text4 = doc.createElement("text");
        text4.setTextContent("QuestionType:"+question.getQuestionType());
        tag4.appendChild(text4);
        tagsNode.appendChild(tag4);

        Element tag5 = doc.createElement("tag");
        Element text5 = doc.createElement("text");
        text5.setTextContent("QuestionType:"+questionMetadata.getSubject());
        tag5.appendChild(text5);
        tagsNode.appendChild(tag5);

        Element tag6 = doc.createElement("tag");
        Element text6 = doc.createElement("text");
        text6.setTextContent("SubChapterName:"+question.getSubChapterName());
        tag6.appendChild(text6);
        tagsNode.appendChild(tag6);

        Element tag7 = doc.createElement("tag");
        Element text7 = doc.createElement("text");
        text7.setTextContent("QuestionClassification:"+question.getQuestionClassification());
        tag7.appendChild(text7);
        tagsNode.appendChild(tag7);

    }




}
