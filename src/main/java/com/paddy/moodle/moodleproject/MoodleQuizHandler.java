package com.paddy.moodle.moodleproject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

@Component
public class MoodleQuizHandler {

    @Autowired
    MoodleQuizBuilder moodleQuizBuilder;

    public MoodleQuizBuilder getMoodleQuizBuilder() {
        return moodleQuizBuilder;
    }

    public void setMoodleQuizBuilder(MoodleQuizBuilder moodleQuizBuilder) {
        this.moodleQuizBuilder = moodleQuizBuilder;
    }

    public void generateMoodleFile(QuestionMetadata questionMetadata, String questionsFileName) throws Exception {
        moodleQuizBuilder.setQuestionMetadata(questionMetadata);
        List<Document> documentList = moodleQuizBuilder.buildQuestionsFromCsv(questionsFileName);
        int i=0;
        for (Document doc:documentList
             ) {
            new FileUtil().writeToFile(doc,questionsFileName,i);
            i++;
        }

    }

    public void generateMoodleFileForChapterNumber(QuestionMetadata questionMetadata,String questionsFileName,String chapterNumber) throws Exception {
        Document doc = moodleQuizBuilder.buildQuestionsFromCsvForChapterNumber(null,questionMetadata,questionsFileName,chapterNumber);
        new FileUtil().writeToFile(doc,questionsFileName+"-"+String.valueOf(chapterNumber),1);
    }

    public void parseCSVFile(){

    }

}
