package com.paddy.moodle.moodleproject;

import java.util.List;

public class MoodleQuiz {
    String questionId;
    String versionNumber;
    String categoryText;
    String chapterName;
    String subChapterName;
    String idNumber;
    String questionText;
    int questionNumber;
    int chapterNumber;
    String questionType;
    String questionClassification;
    String option1;
    String option2;
    String option3;
    String option4;
    List<String> answerOptions;
    String answerDescription;
    String answerForFillInTheBlanks;
    String imageFileName;
    String isImage;
    String complexity;
    String tag1,tag2,tag3,tag4,tag5,tag6,tag7,tag8,tag9,tag10;

    String publisher;
    String forClass;
    String subject;

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getForClass() {
        return forClass;
    }

    public void setForClass(String forClass) {
        this.forClass = forClass;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getQuestionClassification() {
        return questionClassification;
    }

    public void setQuestionClassification(String questionClassification) {
        this.questionClassification = questionClassification;
    }

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getVersionNumber() {
        return versionNumber;
    }

    public void setVersionNumber(String versionNumber) {
        this.versionNumber = versionNumber;
    }

    public int getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(int chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getCategoryText() {
        return categoryText;
    }

    public void setCategoryText(String categoryText) {
        this.categoryText = categoryText;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getSubChapterName() {
        return subChapterName;
    }

    public void setSubChapterName(String subChapterName) {
        this.subChapterName = subChapterName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getQuestionText() {
        return questionText;
    }

    public void setQuestionText(String questionText) {
        this.questionText = questionText;
    }

    public int getQuestionNumber() {
        return questionNumber;
    }

    public void setQuestionNumber(int questionNumber) {
        this.questionNumber = questionNumber;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        if(questionType.isEmpty())
            this.questionType = "MCQ";
        else
            this.questionType = questionType;
    }

    public String getOption1() {
        return option1;
    }

    public void setOption1(String option1) {
        this.option1 = option1;
    }

    public String getOption2() {
        return option2;
    }

    public void setOption2(String option2) {
        this.option2 = option2;
    }

    public String getOption3() {
        return option3;
    }

    public void setOption3(String option3) {
        this.option3 = option3;
    }

    public String getOption4() {
        return option4;
    }

    public void setOption4(String option4) {
        this.option4 = option4;
    }

    public List<String> getAnswerOptions() {
        return answerOptions;
    }

    public void setAnswerOptions(List<String> answerOptions) {
        this.answerOptions = answerOptions;
    }

    public String getAnswerDescription() {
        return answerDescription;
    }

    public void setAnswerDescription(String answerDescription) {
        this.answerDescription = answerDescription;
    }

    public String getAnswerForFillInTheBlanks() {
        return answerForFillInTheBlanks;
    }

    public void setAnswerForFillInTheBlanks(String answerForFillInTheBlanks) {
        this.answerForFillInTheBlanks = answerForFillInTheBlanks;
    }

    public String getImageFileName() {
        return imageFileName;
    }

    public void setImageFileName(String imageFileName) {
        this.imageFileName = imageFileName;
    }

    public String getIsImage() {
        return isImage;
    }

    public void setIsImage(String isImage) {
        this.isImage = isImage;
    }

    public String getComplexity() {
        return complexity;
    }

    public void setComplexity(String complexity) {
        this.complexity = complexity;
    }

    public String getTag1() {
        return tag1;
    }

    public void setTag1(String tag1) {
        this.tag1 = tag1;
    }

    public String getTag2() {
        return tag2;
    }

    public void setTag2(String tag2) {
        this.tag2 = tag2;
    }

    public String getTag3() {
        return tag3;
    }

    public void setTag3(String tag3) {
        this.tag3 = tag3;
    }

    public String getTag4() {
        return tag4;
    }

    public void setTag4(String tag4) {
        this.tag4 = tag4;
    }

    public String getTag5() {
        return tag5;
    }

    public void setTag5(String tag5) {
        this.tag5 = tag5;
    }

    public String getTag6() {
        return tag6;
    }

    public void setTag6(String tag6) {
        this.tag6 = tag6;
    }

    public String getTag7() {
        return tag7;
    }

    public void setTag7(String tag7) {
        this.tag7 = tag7;
    }

    public String getTag8() {
        return tag8;
    }

    public void setTag8(String tag8) {
        this.tag8 = tag8;
    }

    public String getTag9() {
        return tag9;
    }

    public void setTag9(String tag9) {
        this.tag9 = tag9;
    }

    public String getTag10() {
        return tag10;
    }

    public void setTag10(String tag10) {
        this.tag10 = tag10;
    }
}
