package model.customers;

import model.bankaccounts.CurrentAccount;
import model.bankaccounts.DebitCard;
import model.bankaccounts.DepositAccount;

import java.time.LocalDate;
import java.util.ArrayList;

public class Person extends Customer {
    String firstName;
    String lastName;
    LocalDate birthDate;//16 years
    ArrayList<DebitCard> debitCards;
    {
        debitCards = new ArrayList<>();
    }
    public Person(String firstName,String lastName,String nationalCode,LocalDate birthDate) {
        super(nationalCode);
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
    }

    public boolean isOlderThan(int age){
        return birthDate.isBefore(LocalDate.now().minusYears(16));
    }


    public String openCurrentAccount(CurrentAccount currentAccount) {
        accounts.add(currentAccount);
        debitCards.add(currentAccount.getDebitCard());
        return "Your account is successfully created.\n"+currentAccount.getDebitCard().getAccountDetails(this);
    }
    public void closeCurrentAccount (CurrentAccount currentAccount) {
        accounts.remove(currentAccount);
        debitCards.remove(currentAccount.getDebitCard());
    };
    //TODO Are these needed?
//    public void changeMainPassword() {
//    };
//    public void changeSecondPassword() { };
//    public void activateSecondPassword() { };

}
