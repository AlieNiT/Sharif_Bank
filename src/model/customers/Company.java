package model.customers;

import date.MyDate;
import date.TimeManager;
import model.bankaccounts.CurrentAccount;

import java.util.Random;

import static model.Utils.NDigitNumber;

public class Company extends Customer {
    String name;

    MyDate establishmentDate;
    String CEONationalCode;//yyyymmdd_name(14000201_amazon)

    public Company(String name,String CEONationalCode) {
        super(String.valueOf(new StringBuilder().
                append(String.join("", TimeManager.getInstance().getDate().toString().split(" "))).
                append(NDigitNumber(new Random().nextInt(100),2))));
        this.establishmentDate = TimeManager.getInstance().getDate();
        this.name = name;
        this.CEONationalCode = CEONationalCode;
    }

    public void openCurrentAccount(CurrentAccount currentAccount) {
        accounts.add(currentAccount);
        currentAccount.getDebitCard().getAccountDetails(this);
    };
    public void closeCurrentAccount (CurrentAccount currentAccount) {
        this.accounts.remove(currentAccount);
    }

    @Override
    public String getDetail() {
        return "name: "+name+" , national code: "+nationalCode;
    }

    ;

}
