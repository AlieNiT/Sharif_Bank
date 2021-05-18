import date.TimeManager;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        LocalDate date = TimeManager.getInstance().getDate();
        System.out.println(date);
        System.out.println(TimeManager.getInstance().getDate());
        date = date.plusYears(1);
        System.out.println(date);
        System.out.println(TimeManager.getInstance().getDate());
    }
}
