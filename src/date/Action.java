package date;

import model.Loan;
import model.bankaccounts.DepositAccount;
import model.banks.Bank;
import model.customers.Customer;

public class Action {
    DepositAccount depositAccount = null;
    Loan loan = null;
    Bank bank = null;
    Customer customer = null;
    Action(Loan loan){
        this.loan = loan;
    }
    Action(Bank bank){
        this.bank = bank;
    }
    Action(DepositAccount depositAccount){
        this.depositAccount = depositAccount;
    }
    public String takeAction(){
        if(loan != null)
            loan.paymentCheck();
        if(bank != null&&!bank.isBankrupt())
            bank.getIncome();
        if (depositAccount != null)
            depositAccount.chargeCurrentAccount();
    }
}
