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
	void generateForPearsonIITFoundationChemistryPracticeBookClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("CHEMISTRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Chemistry/Pearson/IMAGES-PEARSON-CLASS-9-Chemistry-Practice-Book/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-9-Chemistry-Practice-Book.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonIITFoundationChemistryClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("CHEMISTRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Chemistry/Pearson/IMAGES-PEARSON-CLASS-9-Chemistry/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-9-Chemistry.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonIITFoundationPhysicsPracticeBookClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Pearson/IMAGES-PEARSON-CLASS-9-PHYSICS-Practice-Book/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-9-Physics-Practice-Book.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonIITFoundationPhysicsClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Pearson/IMAGES-PEARSON-CLASS-9-PHYSICS/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-9-Physics.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForMTGIITFoundationPhysicsClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("MTG");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Physics/MTG/IMAGES-MTG-CLASS-8-PHYSICS/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"MTG-Class-8-Physics.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForBrainMappingIITFoundationPhysicsClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("BRAINMAPPING");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Physics/BrainMapping/IMAGES-Brain-Mapping-CLASS-8-PHYSICS/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Brain-Mapping-Class-8-PHYSICS.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForBrainMappingIITFoundationChemistryClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("BRAINMAPPING");
			questionMetadata.setSubject("CHEMISTRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Chemistry/BrainMapping/IMAGES-Brain-Mapping-CLASS-8-CHEMISTRY/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Brain-Mapping-Class-8-CHEMISTRY.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonIITFoundationPhysicsClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Physics/Pearson/IMAGES-PEARSON-CLASS-8-PHYSICS/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-8-Physics.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonIITFoundationChemistryClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("CHEMISTRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Chemistry/Pearson/IMAGES-PEARSON-CLASS-8-Chemistry/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-8-Chemistry.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonNEETFoundationBiologyClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("BIOLOGY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Biology/Pearson/IMAGES-PEARSON-CLASS-8-BIOLOGY/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-8-BIOLOGY.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForDishaFoundationBiologyClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("DISHAFOUNDATION");
			questionMetadata.setSubject("BIOLOGY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Biology/DishaFoundation/IMAGES-DISHA-CLASS-8-BIOLOGY-1/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"DISHA-Class-8-BIOLOGY-1.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Test
	void generateForDishaFoundationChemistryClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("DISHAFOUNDATION");
			questionMetadata.setSubject("CHEMSITRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Chemistry/DishaFoundation/IMAGES-DISHA-CLASS-8-CHEMISTRY/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"DISHA-Class-8-CHEMISTRY.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForDishaFoundationPhysicsClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("DISHAFOUNDATION");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Physics/DishaFoundation/IMAGES-DISHA-CLASS-8-PHYSICS/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"DISHA-Class-8-PHYSICS-V1.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForMTGChemistryClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("MTG");
			questionMetadata.setSubject("BIOLOGY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Chemistry/MTG/IMAGES-MTG-CLASS-8-CHEMISTRY/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"MTG-Class-8-Chemistry-V1.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForMTGBiologyClass8(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS8");
			questionMetadata.setPublisher("MTG");
			questionMetadata.setSubject("BIOLOGY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Biology/IMAGES-MTG-CLASS-8-BIOLOGY/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"MTG-Class-8-Biology-V1.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void testReplaceSpecialCharacters(){
		System.out.println(moodleQuizHandler.getMoodleQuizBuilder().replaceSpecialCharacters("abcdx^2&zzz"));
		System.out.println(moodleQuizHandler.getMoodleQuizBuilder().replaceSpecialCharacters("abcdx~2*zzz"));
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
