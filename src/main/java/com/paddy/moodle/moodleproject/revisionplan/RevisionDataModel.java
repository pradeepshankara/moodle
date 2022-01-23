package com.paddy.moodle.moodleproject.revisionplan;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class RevisionDataModel {
    String chapterName;
    String grade;
    String subject;
    int duration;
    int priority;
    List<Date> revisionDates;
    public RevisionDataModel(){
        revisionDates = new ArrayList<>();
    }

    public static List<RevisionDataModel> buildList(List<String[]> inputRecords){
        List<RevisionDataModel> revisionDataModelList = new ArrayList<>();
        inputRecords.forEach(x->{
            RevisionDataModel revisionDataModel = new RevisionDataModel();
            revisionDataModel.setChapterName(x[0]);
            revisionDataModel.setPriority(Integer.parseInt(x[1]));
            revisionDataModel.setGrade(x[2]);
            revisionDataModel.setSubject(x[3]);
            revisionDataModel.setDuration(Integer.parseInt(x[4]));
            revisionDataModelList.add(revisionDataModel);
        });
        return revisionDataModelList;
    }

    public List<Date> getRevisionDates() {
        return revisionDates;
    }

    public void setRevisionDates(List<Date> revisionDates) {
        this.revisionDates = revisionDates;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
}
