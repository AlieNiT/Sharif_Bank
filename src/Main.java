import controller.*;
import date.MyDate;
import model.banks.Bank;
import model.banks.CentralBank;
import model.customers.Person;
import static database.Database.*;
public class Main {
    public static void main(String[] args) {
        Controller controller = new Controller();
        //read("central-bank.json","calender.json","customers.json");
        //CentralBank.getInstance().openCurrentAccount("tejarat", "0372206050", 20000);
//        MyDate date = MyDate.of( Integer.parseInt("12"),
//                Integer.parseInt("12"),
//                Integer.parseInt("2000"));
        //read("central-bank.json","calender.json","customers.json");
        controller.run();
    }
}
