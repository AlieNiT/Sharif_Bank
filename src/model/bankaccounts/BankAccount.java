package model.bankaccounts;
import java.time.LocalDate;

import date.TimeManager;
import model.banks.Bank;
import model.customers.Customer;

public class BankAccount {
    protected Customer owner;
    protected Bank bank;
    protected String accountNumber;
    protected int accountBalance;
    protected LocalDate creationDate;
    protected String type;
    public BankAccount(Customer owner, Bank bank, int accountBalance) {
        this.owner = owner;
        this.bank = bank;
        this.accountBalance = accountBalance;
        this.creationDate = TimeManager.getInstance().getDate();
    }
    public String getBalance(Bank bank){
        if (this.bank == bank)
            return Integer.toString(accountBalance);
        return null;
    }
    public String getType(){
        return type;
    }
    public String getAccountNumber(){ return accountNumber;}
    public Bank getBank() { return bank;}
    public String closeAccount() {return "Your account is successfully closed.";}

}
