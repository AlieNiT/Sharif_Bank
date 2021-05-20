package model;

import date.Action;
import date.TimeManager;
import model.bankaccounts.CurrentAccount;
import model.banks.Bank;
import model.customers.Customer;

import java.time.LocalDate;
import java.util.ArrayList;

public class Loan {
    Customer receiver;
    Bank bank;
    Action action;
    int loanAmount;
    LocalDate startDate;
    LocalDate endDate;
    int returnedValue = 0;
    int interestPercentage = 120;
    int paybackValue;
    int delayMonths = 0;
    int penaltyPercentage = 2;
    CurrentAccount currentAccount;
    ArrayList<Integer> returnedValues;
    public Loan(Customer receiver, int loanAmount,CurrentAccount currentAccount) {
        this.receiver = receiver;
        this.loanAmount = loanAmount;
        this.bank = currentAccount.getBank();
        paybackValue = (interestPercentage)*loanAmount;
        startDate = TimeManager.getInstance().getDate();
        this.currentAccount = currentAccount;
        returnedValues = new ArrayList<>();
        endDate = startDate.plusYears(4);
        action = new Action(this);
        setDates();
    }
    public Customer getReceiver() {
        return receiver;
    }
    public boolean isPaidBack(){ return returnedValue==(interestPercentage)*loanAmount;}
    public String payOffTheLoan(int amount) {
        if(paybackValue<amount)
            amount = paybackValue;
        if(Integer.parseInt(currentAccount.getBalance(bank))<amount)
            return "You don't have enough money in your account";
        addPayment(amount);
        currentAccount.withdrawMoney(amount);
        return "Your payback is successfully done.";
    }
    private void addPayment(int amount){
        returnedValues.add(amount);
        returnedValue += amount;
        paybackValue -= amount;
    }
    public void paymentCheck(){
        int monthsPassed = -1;
        LocalDate tmp = startDate;
        while(startDate.isBefore(TimeManager.getInstance().getDate())){
            monthsPassed += 1;
            tmp = tmp.plusMonths(1);
        }
        int total = loanAmount * interestPercentage;
        if((total*monthsPassed)/48>returnedValue){
            String response;
            if(TimeManager.getInstance().getDate().isBefore(endDate))
                response = payOffTheLoan((total*monthsPassed)/48-returnedValue);
            else {
                response = payOffTheLoan(total-returnedValue);
                ArrayList<LocalDate> tmpList = new ArrayList();
                tmpList.add(TimeManager.getInstance().getDate().plusMonths(1));
                TimeManager.getInstance().getAction(tmpList,action);
            }
            if (response.equals("Your payback is successfully done."))
                delayMonths = 0;
            else
                if (delayMonths>3)
                    interestPercentage+=penaltyPercentage;
        }
    }
    private void setDates(){
        LocalDate tmp = startDate.plusMonths(1);
        ArrayList<LocalDate> actionDates = new ArrayList<>();
        while (!tmp.isAfter(endDate)){
            actionDates.add(tmp);
            tmp = tmp.plusMonths(1);
        }
        TimeManager.getInstance().getAction(actionDates,action);
    }
}
