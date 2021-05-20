package model.customers;

import date.TimeManager;
import model.Utils;
import model.bankaccounts.CurrentAccount;

import java.time.LocalDate;
public class Company extends Customer {
    String name;

    LocalDate establishmentDate;
    String CEONationalCode;//yyyymmdd_name(14000201_amazon)

    public Company(String name,String CEONationalCode) {
        super(new StringBuilder().append(Utils.toString(TimeManager.getInstance().getDate())).append("_").append(name).toString());
        this.establishmentDate = TimeManager.getInstance().getDate();
        this.name = name;
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
