package model.customers;

import model.Utils;
import model.bankaccounts.CurrentAccount;

import java.time.LocalDate;
public class Company extends Customer {
    String name;
    Person manager;
    LocalDate establishmentDate;
    String CEONationalCode;//yyyymmdd_name(14000201_amazon)

    public Company(String CEONationalCode,String name, Person manager, LocalDate establishmentDate) {
        super(Utils.toString(establishmentDate)+"_"+name);
        this.name = name;
        this.manager = manager;
        this.establishmentDate = establishmentDate;
        this.CEONationalCode = CEONationalCode;
    }

    public String openCurrentAccount(CurrentAccount currentAccount) {
        accounts.add(currentAccount);
        return "Your account is successfully created.\n"+currentAccount.getDebitCard().getAccountDetails(this);
    };
    public void closeCurrentAccount (CurrentAccount currentAccount) {
        this.accounts.remove(currentAccount);
    };
}
