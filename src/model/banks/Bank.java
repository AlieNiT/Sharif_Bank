package model.banks;

import date.Action;
import date.MyDate;
import model.Loan;
import date.TimeManager;
import model.bankaccounts.BankAccount;
import model.bankaccounts.CurrentAccount;
import model.bankaccounts.DepositAccount;
import model.customers.Customer;

import java.util.ArrayList;
import java.util.HashMap;

public class Bank {
    String name;
    String num;
    boolean isBankrupt = false;
    long balance = 3000000000L;
    MyDate establishmentDate;
    MyDate lastActionSet;
    int incomePercent = 20;
    int shortTermInterestPercent = 10;
    int longTermInterestPercent = 15;
    ArrayList<BankAccount> accounts;
    HashMap<Customer, ArrayList<BankAccount>> customersAccounts;
    ArrayList<Loan> loans;
    HashMap<Customer, Loan> customersLoans;

    {
        accounts = new ArrayList<>();
        loans = new ArrayList<>();
        customersAccounts = new HashMap<>();
        customersLoans = new HashMap<>();
    }

    public Bank(String name, String num) {
        this.name = name;
        this.num = num;
        establishmentDate = TimeManager.getInstance().getDate();
        setDates();
    }

    public Bank(String name, String num, long initialAmount) {
        this.name = name;
        this.num = num;
        this.balance = initialAmount;
        establishmentDate = TimeManager.getInstance().getDate();
        setDates();
    }

    private Action getAction() {
        return new Action(this, isBankrupt);
    }

    public String openDepositAccount(Customer owner, String type, int initialAmount) {
        if (CentralBank.getInstance().openAccountRequest(owner)) {
            CurrentAccount currentAccount = null;
            customersAccounts.computeIfAbsent(owner, k -> new ArrayList<>());
            for (BankAccount account : this.customersAccounts.get(owner)) {
                if (account.getType().equals("current")) {
                    currentAccount = (CurrentAccount) account;
                }
            }
            String openCurrentAccountResponce = "";
            if (currentAccount == null) {
                currentAccount = this.openAdditionalCurrentAccount(owner, 0);
                CentralBank.getInstance().addAdditionalAccount(owner, currentAccount);
                openCurrentAccountResponce = "We made an additional current account for your deposit account:\n" + currentAccount.getDebitCard().getAccountDetails(owner) + "\n";
            }
            String[] details = CentralBank.getInstance().accountRequest(this);
            DepositAccount depositAccount = new DepositAccount(owner, this, initialAmount, type, currentAccount, details[2]);
            String depositAccountCreationResponse = owner.openDepositAccount(depositAccount);
            currentAccount.addDepositAccount(depositAccount);
            CentralBank.getInstance().addCustomerAccount(owner, depositAccount);
            this.addBankAccount(depositAccount, owner);
            changeBalance(initialAmount);
            return openCurrentAccountResponce.concat("Your deposit account is created successfully:\n" + depositAccountCreationResponse);
        }
        return "Your request is rejected by the central bank.";
    }

    public CurrentAccount openAdditionalCurrentAccount(Customer owner, int initialAmount) {
        if (CentralBank.getInstance().openAccountRequest(owner)) {
            CurrentAccount currentAccount = new CurrentAccount(owner, this, initialAmount);
            this.addBankAccount(currentAccount, owner);
            owner.openCurrentAccount(currentAccount);
            return currentAccount;
        } else return null;
    }

    private void addBankAccount(BankAccount bankAccount, Customer owner) {
        bankAccount.getBalance(this);
        this.accounts.add(bankAccount);
        if (this.customersAccounts.get(owner) == null)
            customersAccounts.put(owner, new ArrayList<>());
        ArrayList<BankAccount> customersAccounts = this.customersAccounts.get(owner);
        customersAccounts.add(bankAccount);
        this.customersAccounts.put(owner, customersAccounts);
    }

    public String getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String closeAccount(BankAccount account, Customer owner) {
        if (!accounts.contains(account))
            return "This account doesn't exist.";
        accounts.remove(account);
        ArrayList<BankAccount> customerAccounts = customersAccounts.get(owner);
        customerAccounts.remove(account);
        customersAccounts.put(owner, customerAccounts);
        if (account instanceof CurrentAccount) {
            owner.closeCurrentAccount((CurrentAccount) account);
            changeBalance(-Integer.parseInt(account.getBalance(this)));
        } else owner.closeDepositAccount((DepositAccount) account);

        return account.closeAccount();
    }

    //COMMAND HANDLING
    public String openCurrentAccount(Customer owner, int initialAmount) {
        CurrentAccount currentAccount = openAdditionalCurrentAccount(owner, initialAmount);
        if (currentAccount == null)
            return "Your request is rejected by the central bank.";
        CentralBank.getInstance().addCustomerAccount(owner, currentAccount);
        changeBalance(initialAmount);
        return "Your current account is created successfully:\n" + currentAccount.getDebitCard().getAccountDetails(owner);
    }

    protected String setIncomePercent(int newPercent) {
        this.incomePercent = newPercent;
        return "Your command is taken care of.";
    }

    protected String setLongTermInterestPercent(int newPercent) {
        this.longTermInterestPercent = newPercent;
        return "Your command is taken care of.";
    }

    protected String setShortTermInterestPercent(int newPercent) {
        this.shortTermInterestPercent = newPercent;
        return "Your command is taken care of.";
    }

    protected String changeBalance(int amount) {
        balance += amount;
        if (balance < 0)
            isBankrupt = true;
        return "Your command is taken care of.";
    }

    protected boolean hasLoan(Customer customer) {
        for (Loan loan :
                loans) {
            if (loan.getReceiver().equals(customer))
                return true;
        }
        return false;
    }

    public Loan getLoan(Customer customer, int amount, CurrentAccount account) {
        if (customersAccounts.get(customer) == null || (5 * totalBalance(customer) < amount))
            return null;
        Loan loan = new Loan(customer, amount, account);
        loans.add(loan);
        customersLoans.put(customer, loan);
        customer.getLoan(loan);
        return loan;
    }

    public void removeLoan(Loan loan) {
        for (Loan loan1 : loans) {
            if (loan1 == loan) {
                loans.remove(loan1);
                break;
            }
        }
        customersLoans.remove(loan.getReceiver());
    }

    private int totalBalance(Customer customer) {
        int totalBalance = 0;
        for (BankAccount account : customersAccounts.get(customer)) {
            totalBalance += Integer.parseInt(account.getBalance(this));
        }
        return totalBalance;
    }

    public boolean isBankrupt() {
        return isBankrupt;
    }

    public void getIncome() {
        balance += (balance * incomePercent) / 100;
    }

    public int getInterestPercent(String type) {
        if (type.equals("long"))
            return longTermInterestPercent;
        return shortTermInterestPercent;
    }

    private void setDates() {
        MyDate tmp = establishmentDate.plusMonths(1);

        ArrayList<MyDate> actionDates = new ArrayList<>();
        while (tmp.isBefore(establishmentDate.plusYears(10))) {
            actionDates.add(tmp);
            lastActionSet = tmp;
            tmp = tmp.plusMonths(1);
        }
        TimeManager.getInstance().getAction(actionDates, getAction());
    }

    public void resetDates() {
        if (TimeManager.getInstance().getDate().plusYears(10).isAfter(lastActionSet)) {
            MyDate tmp = lastActionSet.plusMonths(1);
            ArrayList<MyDate> actionDates = new ArrayList<>();
            while (tmp.isBefore(TimeManager.getInstance().getDate().plusYears(11))) {
                actionDates.add(tmp);
                lastActionSet = tmp;
                tmp = tmp.plusMonths(1);
            }
            TimeManager.getInstance().getAction(actionDates, getAction());
        }
    }

    public long getBalance() {
        return balance;
    }

    public String getInterestPercent() {
        return "long-term: " + longTermInterestPercent + ", short-term: " + shortTermInterestPercent;
    }

    public void clearLoan(Loan loan) {
        loans.remove(loan);
    }
}

