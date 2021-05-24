package model.bankaccounts;

import date.MyDate;
import date.TimeManager;
import model.banks.Bank;
import model.customers.Customer;

public class BankAccount {
    Customer owner;
    Bank bank;
    String accountNumber;
    int accountBalance;
    MyDate creationDate;
    String type;

    BankAccount(Customer owner, Bank bank, int accountBalance) {
        this.owner = owner;
        this.bank = bank;
        this.accountBalance = accountBalance;
        this.creationDate = TimeManager.getInstance().getDate();
    }

    public String getBalance(Bank bank) {
        if (this.bank == bank)
            return Integer.toString(accountBalance);
        return null;
    }

    public String getType() {
        return type;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public Bank getBank() {
        return bank;
    }

    public String getOwner() {
        return owner.getNationalCode();
    }

    public String closeAccount() {
        return "Your account is successfully closed.";
    }

    public void changeBalance(int i) {
        accountBalance += i;
    }
}
