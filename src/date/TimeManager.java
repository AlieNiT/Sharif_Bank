package date;

import model.banks.CentralBank;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TimeManager {
    private static TimeManager timeManager;
    public static TimeManager getInstance(){
        if(timeManager == null)
            timeManager = new TimeManager();
        return timeManager;
    }
    private TimeManager(){

    }
    private LocalDate date = LocalDate.now();
    public LocalDate getDate() {
        return this.date;
    }
    private final HashMap<LocalDate, ArrayList<Action>> calender = new HashMap<>();
    public void getAction(HashMap<LocalDate, ArrayList<Action>> actions){
        for (Map.Entry<LocalDate, ArrayList<Action>> entry : actions.entrySet()) {
            calender.put(entry.getKey(),entry.getValue());
        }
    }
    public String goNextDay(){
        date.plusDays(1);
        int loansDone = 0;

    }
    public String goNextMonth(){

    }
    public String goNextYear(){

    }
    public String goForNDays(int N){

    }
    public String goForNMonths(int N){

    }
    public String goForNYears(int N){

    }
    public String goToDate(int day,int month,int year){

    }
}

