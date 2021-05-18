import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String regex = "I'm (\\w+). I'm (\\d+) years old.";
        Pattern pattern = Pattern.compile(regex);
        String input = scanner.nextLine();
        Matcher matcher = pattern.matcher(input);
        if(matcher.find()){
            //System.out.println(matcher.group(0));
            doSomething(matcher);
        }
    }
    public static void doSomething(Matcher matcher){
        System.out.println("Hi "+matcher.group(1));
        if(Integer.parseInt(matcher.group(2))>20){
            System.out.println("You are so old!");
        }
        else System.out.println("You are a child.");
    }
}