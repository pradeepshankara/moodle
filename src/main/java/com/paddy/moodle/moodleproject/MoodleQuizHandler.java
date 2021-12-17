package com.paddy.moodle.moodleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.IOException;

@Component
public class MoodleQuizHandler {

    @Autowired
    MoodleQuizBuilder moodleQuizBuilder;

    public void generateMoodleFile(QuestionMetadata questionMetadata,String questionsFileName) throws Exception {

            Document doc = moodleQuizBuilder.buildQuestionsFromCsv(questionMetadata,questionsFileName);
            new FileUtil().writeToFile(doc);

    }

    public void parseCSVFile(){

    }

}
