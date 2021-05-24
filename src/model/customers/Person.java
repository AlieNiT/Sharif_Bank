package model.customers;

import date.MyDate;
import date.TimeManager;
import model.bankaccounts.CurrentAccount;
import model.bankaccounts.DebitCard;

import java.util.ArrayList;

public class Person extends Customer {
    String firstName;
    String lastName;
    MyDate birthDate;//16 years
    ArrayList<DebitCard> debitCards;
    {
        debitCards = new ArrayList<>();
    }
    public Person(String firstName,String lastName,String nationalCode,MyDate birthDate) {
        super(nationalCode);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public boolean isOlderThan(int age){
        return birthDate.isBefore(TimeManager.getInstance().getDate().minusYears(16));
    }


    public void openCurrentAccount(CurrentAccount currentAccount) {
        accounts.add(currentAccount);
        debitCards.add(currentAccount.getDebitCard());
        currentAccount.getDebitCard().getAccountDetails(this);
    }
    public void closeCurrentAccount (CurrentAccount currentAccount) {
        accounts.remove(currentAccount);
        debitCards.remove(currentAccount.getDebitCard());
    }

    @Override
    public String getDetail() {
        return "name: "+ firstName+" "+lastName+" , national code: "+nationalCode;
    }

    ;
    //TODO Are these needed?
//    public void changeMainPassword() {
//    };
//    public void changeSecondPassword() { };
//    public void activateSecondPassword() { };

}
