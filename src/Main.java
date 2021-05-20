import controller.*;
import static database.Database.*;
public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        //read("central-bank.json","calender.json","customers.json");
        controller.run();
    }
}
