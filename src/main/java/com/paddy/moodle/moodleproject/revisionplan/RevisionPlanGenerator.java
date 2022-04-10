package com.paddy.moodle.moodleproject.revisionplan;

import com.google.gson.Gson;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.paddy.moodle.moodleproject.FileUtil;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.io.FileReader;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class RevisionPlanGenerator {
    DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    String header = "BEGIN:VCALENDAR\n" +
            "METHOD:PUBLISH\n" +
            "PRODID:-//Moodle Pty Ltd//NONSGML Moodle Version 2020061511.07//EN\n" +
            "VERSION:2.0 \n";
    String footer = "END:VCALENDAR";

    //dayPattern = 1 = SUNDAY and 7 = SATURDAY Ex: 1,2,4,6,7
    public void generateRevisionsPlan(int numberOfInstances, int cycleTime, Date startDate, String dayPattern){
        List<String[]> inputRecords = new FileUtil().readCsvContent("RevisionPlan.csv");
        List<RevisionDataModel> revisionDataModelList = RevisionDataModel.buildList(inputRecords);
        List<String> daysFromPattern = Arrays.asList(dayPattern.split(","));
        List<Integer> daysFromPatternInt = new ArrayList<>();
        daysFromPattern.forEach(x->{
            daysFromPatternInt.add(Integer.parseInt(x));
        });
        revisionDataModelList.forEach(x->{
            generateDates(revisionDataModelList,x,numberOfInstances,cycleTime,startDate,daysFromPatternInt);
        });
        System.out.println(new Gson().toJson(revisionDataModelList));
        generateEvents(revisionDataModelList);
    }

    private void generateDates(List<RevisionDataModel> revisionDataModelList,RevisionDataModel revisionDataModel,int numberOfInstances, int cycleTime, Date startDate, List<Integer> daysFromPattern){
        Calendar actualStartDate = Calendar.getInstance();
        actualStartDate.setTime(startDate);
        actualStartDate.add(Calendar.DATE,revisionDataModel.getPriority());
        for (int i = 0; i < numberOfInstances; i++) {
            Calendar c = Calendar.getInstance();
            c.setTime(actualStartDate.getTime());
            c.add(Calendar.DATE, i*cycleTime);
            for (int j = 0; j < 7; j++) {
                if(daysFromPattern.contains(c.get(Calendar.DAY_OF_WEEK)) && !isRevisionEventExists(c,revisionDataModelList)){
                    break;
                }else
                    c.add(Calendar.DATE, 1);
            }
            revisionDataModel.getRevisionDates().add(c.getTime());
        }
    }

    private boolean isRevisionEventExists(Calendar c,List<RevisionDataModel> revisionDataModelList){
        for (int i = 0; i < revisionDataModelList.size(); i++) {
            RevisionDataModel x = revisionDataModelList.get(i);
            for (int j = 0; j < x.getRevisionDates().size(); j++) {
                if(!c.getTime().after(x.getRevisionDates().get(j)) && !c.getTime().before(x.getRevisionDates().get(j))){
                    return true;
                }
            }
        }
        return false;
    }
    private void generateEvents(List<RevisionDataModel> revisionDataModelList){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String eventsString = header;
        Random rand = new Random();
        for (int i = 0; i < revisionDataModelList.size(); i++) {
            RevisionDataModel x = revisionDataModelList.get(i);
            String summary = x.getGrade() +" "+x.getSubject()+" "+x.getChapterName();
            String description = "Complete revision of "+x.getGrade() +" "+x.getSubject()+" "+x.getChapterName() + " with in the duration of "+x.getDuration();
            for (Date y:x.getRevisionDates()
            ) {
                int num = rand.nextInt(9000000) + 1000000;
                eventsString += generateEvent(summary, description, dateFormat.format(y),dateFormat.format(y),dateFormat.format(y),num);
            }
        }
        eventsString += footer;
        System.out.println(eventsString);
    }

    private String generateEvent(String summary,String description, String eventDate,String eventDateStart, String eventDateEnd, long uniqueId){
        String str = "BEGIN:VEVENT\n" +
                "UID:"+uniqueId+"@myschool.gnaanakosha.com/moodle\n" +
                "SUMMARY:" +summary+ "\n"+
                "DESCRIPTION:" +description+"\n"+
                "CLASS:PUBLIC\n" +
                "LAST-MODIFIED:20220113T082713Z\n" +
                "LOCATION:Home\n" +
                "DTSTAMP:" + eventDate+"T000000Z\n"+
                "DTSTART:" + eventDateStart+"T000000Z\n"+
                "DTEND:" + eventDateEnd+"T000000Z\n"+
                "CATEGORIES:User events\n" +
                "END:VEVENT\n";
        return str;
    }





}
