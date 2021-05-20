package model.customers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Loan;
import model.bankaccounts.BankAccount;
import model.bankaccounts.CurrentAccount;
import model.bankaccounts.DepositAccount;
import model.banks.CentralBank;

import java.io.*;
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
    public static void read(String fileName){
        Gson gson = new Gson();
        try (BufferedReader br = new BufferedReader(new FileReader(fileName));) {
            customers = gson.fromJson(br, new TypeToken<ArrayList<Customer>>(){}.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void write(String fileName){
    Gson gson = new Gson();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(fileName));) {
            gson.toJson(customers,bw);
            bw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
