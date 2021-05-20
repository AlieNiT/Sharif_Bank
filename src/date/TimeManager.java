package date;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.banks.CentralBank;
import model.customers.Customer;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeManager {
    private static TimeManager timeManager;
    private HashMap<LocalDate, ArrayList<Action>> calender;
    public static TimeManager getInstance(HashMap<LocalDate, ArrayList<Action>> calender){
        if(timeManager == null) {
            timeManager = new TimeManager();
            timeManager.calender = calender;
        }
        return timeManager;
    }
    public static TimeManager getInstance(){
        if(timeManager == null) {
            timeManager = new TimeManager();
            timeManager.calender = new HashMap<>();
        }
        return timeManager;
    }
    private LocalDate date = LocalDate.now();
    public LocalDate getDate() {
        return this.date;
    }

    public static void read(String fileName){
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
            timeManager = gson.fromJson(br, TimeManager.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void write(String fileName){
        Gson gson = new Gson();
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            gson.toJson(timeManager,fileWriter);
            fileWriter.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void getAction(ArrayList<LocalDate> dates, Action action){
        for (LocalDate date:dates)  {
            ArrayList<Action> tmp = calender.get(date);
            tmp.add(action);
            calender.put(date,tmp);
        }
    }
    public String goNextDay(){
        date = date.plusDays(1);
        ArrayList<Action> actions = calender.get(date);
        for (Action action : actions) {
            action.takeAction();
        }
        return "1 day passed. Current date: "+ date.toString();
    }
    public String goNextMonth(){
        return goForNMonths(1);
    }
    public String goNextYear(){
        return goForNYears(1);
    }
    public String goForNDays(int N){
        for (int i = 0; i < N; i++) {
            goNextDay();
        }
        return N+" days passed. Current date: "+date.toString();
    }
    public String goForNMonths(int N){
        LocalDate destination = date.plusMonths(N);
        while (date.isBefore(destination))
            goNextDay();
        return N+" month(s) passed. Current date: "+date.toString();
    }
    public String goForNYears(int N){
        LocalDate destination = date.plusYears(N);
        while (date.isBefore(destination))
            goNextDay();
        return N+" year(s) passed. Current date: "+date.toString();
    }
    public String goToDate(int day,int month,int year){
        LocalDate destination = LocalDate.of(year,month,day);
        if (!destination.isAfter(date))
            return "Destination date should be after the current date.";
        int daysPassed = 0;
        while (date.isBefore(destination)) {
            daysPassed += 1;
            goNextDay();
        }
        return daysPassed+" days passed. Current date: "+date.toString();
    }
}

