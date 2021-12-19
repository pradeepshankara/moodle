package com.paddy.moodle.moodleproject;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;

@SpringBootTest
class MoodleprojectApplicationTests {

	@Autowired
	CsvHandler csvHandler;

	@Autowired
	MoodleQuizHandler moodleQuizHandler;

	@Test
	void contextLoads() {
	}

	@Test
	void printFileContent(){
		csvHandler.readDataLineByLine("QuestionBank-Class-8-Chapeter-2_questions.csv");
	}

	@Test
	void printXmlContent(){
		try {
			//csvHandler.processXmlDocument("QuestionBank-Class-8-Chapeter-1_questions.csv","QuestionBank-Class-8-Chapeter-1_answers.csv","CLASS8","PRSN","EASY","multichoice","CHEMISTRY");
			csvHandler.processXmlDocument("QuestionBank-Class-8-Chapeter-2_questions.csv","QuestionBank-Class-8-Chapeter-2_answers.csv","CLASS8","PRSN","EASY","multichoice","CHEMISTRY");
			//csvHandler.processXmlDocument("QUESTIONBANK-Physics-Class8-PearsonPracticeBook_Questions.csv","QUESTIONBANK-Physics-Class8-PearsonPracticeBook_Answers.csv","CLASS8","PRSN","EASY","multichoice","PHYSICS");
			//csvHandler.processXmlDocument("test_questions.csv","test_answers.csv","CLASS8","PRSN","EASY","multichoice","PHYSICS");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForBeToppersPhysicsClass7(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS7");
			questionMetadata.setPublisher("BETOPPERS");
			questionMetadata.setSubject("PHYSICS");
			moodleQuizHandler.generateMoodleFileForChapterNumber(questionMetadata,"BeToppers-Physics-Class-7-30-11-2021.csv",3);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void printXmlContentCropProduction_V1(){
		try {
			csvHandler.processXmlDocument("CropProductionAndManagement-Questions.csv","CropProductionAndManagement-Answers.csv","CLASS8","PRSN","EASY","multichoice","BIOLOGY");
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (TransformerException e) {
			e.printStackTrace();
		}
	}

}
