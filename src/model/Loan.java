package model;

import date.Action;
import date.MyDate;
import date.TimeManager;
import model.bankaccounts.CurrentAccount;
import model.banks.Bank;
import model.banks.CentralBank;
import model.customers.Customer;
import java.util.ArrayList;

public class Loan {
    public Customer receiver;
    public Bank bank;
    public Action action;
    public int loanAmount;
    public MyDate startDate;
    public MyDate endDate;
    public int returnedValue = 0;
    public int interestPercentage = 120;
    public int paybackValue;
    public int delayMonths = 0;
    public int penaltyPercentage = 2;
    public CurrentAccount currentAccount;
    public ArrayList<Integer> returnedValues;
    public Loan(Customer receiver, int loanAmount,CurrentAccount currentAccount) {
        this.receiver = receiver;
        this.loanAmount = loanAmount;
        this.bank = currentAccount.getBank();
        paybackValue = ((interestPercentage)*loanAmount)/100;
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
        if(paybackValue<=amount)
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
        if (paybackValue == 0){
            receiver.paidLoan();
            CentralBank.getInstance().clearLoan(this);
            bank.clearLoan(this);
        }
    }
    public void paymentCheck(){
        int monthsPassed = -1;
        MyDate tmp = startDate;
        while(tmp.isBefore(TimeManager.getInstance().getDate())){
            monthsPassed += 1;
            tmp = tmp.plusMonths(1);
        }
        int total=((interestPercentage)*loanAmount)/100;
        if((total*monthsPassed)/48>returnedValue){
            String response;
            if(TimeManager.getInstance().getDate().isBefore(endDate))
                response = payOffTheLoan((total*monthsPassed)/48-returnedValue);
            else {
                response = payOffTheLoan(total-returnedValue);
                ArrayList<MyDate> tmpList = new ArrayList();
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
        MyDate tmp = startDate.plusMonths(1);
        ArrayList<MyDate> actionDates = new ArrayList<>();
        while (!tmp.isAfter(endDate)){
            actionDates.add(tmp);
            tmp = tmp.plusMonths(1);
        }
        TimeManager.getInstance().getAction(actionDates,action);
    }
    public String getDetail(){
        StringBuilder stringBuilder = new StringBuilder();
        String s1 = "Bank: "+ bank.getName()+" , ";
        String s2 = "Receiver: "+ receiver.getNationalCode() +" , ";
        String s3 = "Amount: "+ loanAmount +" , ";
        String s4 = "The value left:"+ paybackValue;
        StringBuilder sb = new StringBuilder().append(s1).append(s2).append(s3).append(s4).append("\n");
        return sb.toString();
    }
    public String getNationalCode(){return receiver.getNationalCode();}
}
