package model.customers;

import model.Loan;
import model.bankaccounts.BankAccount;
import model.bankaccounts.CurrentAccount;
import model.bankaccounts.DepositAccount;

import java.util.ArrayList;

public class Customer {
    private static ArrayList<Customer> customers = new ArrayList<>();
    protected ArrayList<BankAccount> accounts;
    boolean isAble = true;
    Loan currentLoan = null;
    protected int loanAmount;
    protected int numOfLoans;
    protected int totalBalance;
    protected String nationalCode;
    {
        accounts = new ArrayList<>();
    }
    public Customer(String nationalCode) {
        this.nationalCode = nationalCode;
        loanAmount = 0;
        numOfLoans = 0;
        totalBalance = 0;
        customers.add(this);
    }

    public String openDepositAccount(DepositAccount depositAccount) {
        accounts.add(depositAccount);
        return "Your deposit account is successfully created.\n"+depositAccount.getAccountDetails(this);

    };
    public String openCurrentAccount(CurrentAccount currentAccount) {
        return null;
    }
    public void closeDepositAccount (DepositAccount depositAccount) {
        this.accounts.remove(depositAccount);
        return;
    };
    public void closeCurrentAccount (CurrentAccount currentAccount) { return; }

    public void loanRepayment() { }
    public String getNationalCode(){ return nationalCode;}
    public static ArrayList<Customer> getCustomers() {
        return customers;
    }
    public static Customer getCustomer(String nationalCode){
        for (Customer customer :
                customers) {
            if (customer.nationalCode.equals(nationalCode))
                return customer;
        }
        return null;
    }
    public void getLoan(Loan loan){
        this.currentLoan = loan;
    }

    public Loan getCurrentLoan() {
        return currentLoan;
    }
    public void paidLoan(){
        if (currentLoan.isPaidBack())
            currentLoan = null;
    }
}
