package com.paddy.moodle.moodleproject;

import com.paddy.moodle.moodleproject.revisionplan.RevisionPlanGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

@SpringBootTest
class MoodleprojectApplicationTests {

	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

	@Autowired
	CsvHandler csvHandler;

	@Autowired
	MoodleQuizHandler moodleQuizHandler;

	@Autowired
	RevisionPlanGenerator revisionPlanGenerator;

	@Test
	void contextLoads() {
	}

	@Test
	void printFileContent(){
		csvHandler.readDataLineByLine("QuestionBank-Class-8-Chapeter-2_questions.csv");
	}

	@Test
	void generateRevisionPlan(){
		try {
			revisionPlanGenerator.generateRevisionsPlan(6,21,dateFormat.parse("25/01/2022"),"1,2,4,6,7");
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
	void generateForGATECivil(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("GATE-CIVIL");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("CIVIL");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/GATE-Civil/IMAGES-GATE-2019-CE/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"GATE-2019-CE.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	@Test
	void generateForPGCETCivil2015(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("PGCET");
			questionMetadata.setPublisher("KarnatakaPGCET");
			questionMetadata.setSubject("CIVIL");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Engineering/PGCET/QuestionBank/PGCET-CIVIL-2015-IMAGES/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"PGCET-CIVIL-2015.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForMTGPhysicsClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("MTG");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Physics/MTG/IMAGES-MTG-Class-9-Physics/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"MTG-Class-9-Physics.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForMTGChemistryClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("MTG");
			questionMetadata.setSubject("CHEMISTRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Chemistry/MTG/IMAGES-MTG-Class-9-Chemistry/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"MTG-Class-9-Chemistry.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForMTGBiologyPracticeBookClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("MTGPRACTICEBOOK");
			questionMetadata.setSubject("BIOLOGY");
			questionMetadata.setImageType("PNG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Biology/MTG/IMAGES-MTG-CLASS-9-PRACTICE-BOOK-BIOLOGY/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"MTG-CLASS-9-PRACTICE-BOOK.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForMTGBiologyClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("MTG");
			questionMetadata.setSubject("BIOLOGY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Biology/MTG/IMAGES-MTG-CLASS-9-Biology/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"MTG-Class-9-Biology.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonChemistryClass10(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS10");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("CHEMISTRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class10/Chemistry/Pearson/IMAGES-PEARSON-CLASS-10-Chemistry/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-10-Chemistry.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonChemistryPracticeBookClass10(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS10");
			questionMetadata.setPublisher("PEARSONPRACTICEBOOK");
			questionMetadata.setSubject("CHEMISTRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class10/Chemistry/Pearson/IMAGES-PEARSON-CLASS-10-Chemistry-Practice-Book/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-10-Chemistry-Practice-Book.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonPracticeBookPhysicsClass10(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS10");
			questionMetadata.setPublisher("PEARSONPRACTICEBOOK");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class10/Physics/Pearson/IMAGES-PEARSON-CLASS-10-PHYSICS-Practice-Book/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-10-Physics-Practicebook.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForPearsonPhysicsClass10(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS10");
			questionMetadata.setPublisher("PEARSON");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class10/Physics/Pearson/IMAGES-PEARSON-CLASS-10-PHYSICS/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Pearson-Class-10-Physics.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForBrainMappingChemistryClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("BRAINMAPPING");
			questionMetadata.setSubject("CHEMISTRY");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Chemistry/Brain Mapping/IMAGES-Brain-Mapping-CLASS-9-CHEMISTRY/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Brain-Mapping-Class-9-CHEMISTRY.csv");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	void generateForBrainMappingPhysicsClass9(){
		try {
			QuestionMetadata questionMetadata = new QuestionMetadata();
			questionMetadata.setForClass("CLASS9");
			questionMetadata.setPublisher("BRAINMAPPING");
			questionMetadata.setSubject("PHYSICS");
			questionMetadata.setImageType("JPG");
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class 9/Physics/BrainMapping/IMAGES-Brain-Mapping-CLASS-9-PHYSICS/");
			moodleQuizHandler.getMoodleQuizBuilder().setQuestionMetadata(questionMetadata);
			moodleQuizHandler.generateMoodleFile(questionMetadata,"Brain-Mapping-Class-9-PHYSICS.csv");
		} catch (Exception e) {
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
			questionMetadata.setImagesPath("/home/pradeep/Documents/moodle/Class8/Biology/MTG/IMAGES-MTG-CLASS-8-BIOLOGY/");
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
			moodleQuizHandler.generateMoodleFileForChapterNumber(questionMetadata,"BeToppers-Physics-Class-7-30-11-2021.csv","3");
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
