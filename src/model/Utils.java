package model;

import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    public static int numOfDigits(int n) {
        if (n == 0)
            return 1;
        int res = 0;
        while (n>0) {
            res += 1;
            n /= 10;
        }
        return res;
    }

    public static String NDigitNumber(int num, int N) {
        int n = numOfDigits(num);
        StringBuilder res = new StringBuilder(Integer.toString(num));
        for (int i = 0; i < N - numOfDigits(num); i++)
            res.insert(0, "0");
        return res.toString();
    }
    
    public static boolean isANumber(String number){
        for (char ch : number.toCharArray()) {
            if (!(ch <= 57 && ch >= 48))
                return false;
        }
        return true;
    }
    public static String toString(LocalDate localDateTime){
        Matcher matcher = Pattern.compile("(\\d+)-(\\d+)-(\\d+)").matcher(localDateTime.toString());
        if (matcher.find())
            return matcher.group(1)+matcher.group(2)+matcher.group(3);
        return null;
    }
    public static LocalDate getDate(String date){
        Matcher matcher = Pattern.compile("(\\d+)[-/ ]+(\\d+)[-/ ]+(\\d+)").matcher(date);
        if (matcher.find())
            return LocalDate.of(Integer.parseInt(matcher.group(1)),
                                    Integer.parseInt(matcher.group(2)),
                                    Integer.parseInt(matcher.group(3)));
        return null;
    }
}
