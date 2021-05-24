package model;

public class Utils {
    public static int numOfDigits(int n) {
        if (n == 0)
            return 1;
        int res = 0;
        while (n > 0) {
            res += 1;
            n /= 10;
        }
        return res;
    }

    public static String NDigitNumber(int num, int N) {
        StringBuilder res = new StringBuilder(Integer.toString(num));
        for (int i = 0; i < N - numOfDigits(num); i++)
            res.insert(0, "0");
        return res.toString();
    }
}
