package model.bankaccounts;

import date.TimeManager;
import model.Utils;
import model.banks.Bank;
import model.customers.Customer;

import java.time.LocalDate;

public class DepositAccount extends BankAccount {
    LocalDate expirationDate;
    CurrentAccount currentAccount;
    public DepositAccount(Customer owner, Bank bank, int accountBalance, String type,CurrentAccount currentAccount) {
        super(owner, bank, accountBalance);
        this.type = type;
        expirationDate = creationDate.plusYears(1);
        this.currentAccount = currentAccount;
    }
    public String closeAccount() {
        double penaltyPercentage = 0;
        LocalDate now = TimeManager.getInstance().getDate();
        while (now.isAfter(expirationDate)){
            penaltyPercentage += 0.5;
            now = now.minusMonths(1);
        }
        int payment = (int) (accountBalance*(100 - penaltyPercentage));
        currentAccount.depositMoney(payment);

        if (penaltyPercentage>0)return "Your account is successfully closed. Considering the expiration date, your current account is charged by "+payment+"$.";
        else return null;//happens when the time jumps, therefore no response is needed.
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
            tmp[1] = "Creation Date" + Utils.toString(creationDate);
            tmp[2] = "Expiration Date" + Utils.toString(expirationDate);
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
}
