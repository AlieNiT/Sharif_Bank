package model.customers;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import model.Loan;
import model.bankaccounts.BankAccount;
import model.bankaccounts.CurrentAccount;
import model.bankaccounts.DepositAccount;

import java.io.*;
import java.util.ArrayList;

public abstract class Customer {
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

    public static String showAllPersons() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Customer customer :
                customers) {
            if (customer instanceof Person)
                stringBuilder.append(customer.getDetail());
        }
        return stringBuilder.toString();
    }

    public static String showAllCompanies() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Customer customer :
                customers) {
            if (customer instanceof Company)
                stringBuilder.append(customer.getDetail());
        }
        return stringBuilder.toString();
    }

    public String openDepositAccount(DepositAccount depositAccount) {
        accounts.add(depositAccount);
        return depositAccount.getAccountDetails(this);

    };
    public abstract void openCurrentAccount(CurrentAccount currentAccount);
    public void closeDepositAccount (DepositAccount depositAccount) {
        this.accounts.remove(depositAccount);
    };
    public abstract void closeCurrentAccount (CurrentAccount currentAccount);
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
    public abstract String getDetail();

}
