package model.bankaccounts;
import date.Action;
import date.MyDate;
import date.TimeManager;
import model.Utils;
import model.banks.Bank;
import model.customers.Customer;
import java.util.ArrayList;

public class DepositAccount extends BankAccount {
    public MyDate expirationDate;
    public CurrentAccount currentAccount;
    public Action action;
    public DepositAccount(Customer owner, Bank bank, int accountBalance, String type, CurrentAccount currentAccount, String detail) {
        super(owner, bank, accountBalance);
        this.accountNumber = detail;
        this.type = type;
        if (type.equals("short"))
            expirationDate = creationDate.plusMonths(6);
        else expirationDate = creationDate.plusYears(1);
        this.currentAccount = currentAccount;
        action = new Action(this);
        setDates();
    }
    public String closeAccount() {
        double penaltyPercentage = 0;
        MyDate now = TimeManager.getInstance().getDate();
        while (now.isBefore(expirationDate)){
            penaltyPercentage += 0.5;
            expirationDate = expirationDate.minusMonths(1);
        }
        int payment = (int) (accountBalance*(100 - penaltyPercentage))/100;
        currentAccount.depositMoney(payment);
        currentAccount.removeDepositAccount(this);
        action.invalidate();

        return "Your account is successfully closed. Considering the expiration date, your current account is charged by "+payment+"$.";
        //happens when the time jumps, therefore no response is needed.
    };
    public static CurrentAccount getCurrentForDepositAccount(Customer customer,DepositAccount depositAccount){
        if(depositAccount.owner == customer)
            return depositAccount.currentAccount;
        return null;
    }
    public String getAccountDetails(Customer customer){
        if(customer == this.owner){
            String[] tmp = new String[3];
            tmp[0] = "Account Number: "+accountNumber;
            tmp[1] = "Creation Date " + creationDate.toString();
            tmp[2] = "Expiration Date " + expirationDate.toString();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 3; i++)
                stringBuilder.append(tmp[i]).append((i != 2) ? "\n" : "");
            return stringBuilder.toString();
        }
        return "You don't own this account";    }
    public void chargeCurrentAccount(){
        if(TimeManager.getInstance().getDate().equals(expirationDate)) {
            closeAccount();
            return;
        }
        currentAccount.depositMoney((bank.getInterestPercent(type)*accountBalance)/100);
    }
    private void setDates(){
        MyDate tmp = creationDate.plusMonths(1);
        ArrayList<MyDate> actionDates = new ArrayList<>();
        while (tmp.isBefore(expirationDate)){
            actionDates.add(tmp);
            tmp = tmp.plusMonths(1);
        }
        TimeManager.getInstance().getAction(actionDates,action);
    }
}
