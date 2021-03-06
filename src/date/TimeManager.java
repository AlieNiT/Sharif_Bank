package date;

import model.banks.CentralBank;

import java.util.ArrayList;
import java.util.HashMap;

public class TimeManager {
    static TimeManager timeManager;
    HashMap<String, ArrayList<Action>> calender;

    public static TimeManager getInstance(HashMap<String, ArrayList<Action>> calender) {
        if (timeManager == null) {
            timeManager = new TimeManager();
            timeManager.calender = calender;
        }
        return timeManager;
    }

    public static TimeManager getInstance() {
        if (timeManager == null) {
            timeManager = new TimeManager();
            timeManager.calender = new HashMap<>();
        }
        return timeManager;
    }

    MyDate date = MyDate.now();

    public MyDate getDate() {
        return date;
    }

    public void getAction(ArrayList<MyDate> dates, Action action) {
        for (MyDate date : dates) {
            ArrayList<Action> tmp = calender.get(date.toString());
            if (tmp == null)
                tmp = new ArrayList<>();
            tmp.add(action);
            calender.put(date.toString(), tmp);
        }
    }

    public String goNextDay() {
        date = date.plusDays(1);
        calender.computeIfAbsent(date.toString(), k -> new ArrayList<>());
        ArrayList<Action> actions = calender.get(date.toString());
        for (Action action : actions) {
            action.takeAction();
        }
        calender.remove(date.toString());
        CentralBank.getInstance().resetBankActions();
        return "1 day passed. Current date: " + date.toString();
    }

    public String goNextMonth() {
        return goForNMonths(1);
    }

    public String goNextYear() {
        return goForNYears(1);
    }

    public String goForNDays(int N) {
        for (int i = 0; i < N; i++) {
            goNextDay();
        }
        return N + " days passed. Current date: " + date.toString();
    }

    public String goForNMonths(int N) {
        MyDate destination = date.plusMonths(N);
        while (date.isBefore(destination))
            goNextDay();
        return N + " month(s) passed. Current date: " + date.toString();
    }

    public String goForNYears(int N) {
        MyDate destination = date.plusYears(N);
        while (date.isBefore(destination))
            goNextDay();
        return N + " year(s) passed. Current date: " + date.toString();
    }

    public String goToDate(int day, int month, int year) {
        MyDate destination = MyDate.of(year, month, day);
        if (!destination.isAfter(date))
            return "Destination date should be after the current date.";
        int daysPassed = 0;
        while (date.isBefore(destination)) {
            daysPassed += 1;
            goNextDay();
        }
        return daysPassed + " days passed. Current date: " + date.toString();
    }
}

