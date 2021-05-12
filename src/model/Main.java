package model;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Pattern addPerson = Pattern.compile("Add person ([A-Za-z]+) ([A-Za-z]+) (0\\d+) (\\d{1,2}/\\d{1,2}/\\d{4})");
        String input = scanner.nextLine();
        Matcher matcher = addPerson.matcher(input);
        System.out.println(Pattern.matches(addPerson.toString(), input));
        for (int i = 0; i < matcher.groupCount(); i++) {
            System.out.println(matcher.group(i));
        }
    }
}