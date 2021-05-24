package date;

import model.Loan;
import model.bankaccounts.DepositAccount;
import model.banks.Bank;
import model.customers.Customer;

public class Action {
    public boolean isValid = true;
    public DepositAccount depositAccount = null;
    public Loan loan = null;
    public Bank bank = null;
    Customer customer = null;
    public Action(Loan loan){
        this.loan = loan;
    }
    public Action(Bank bank, boolean isBankrupt){
        isValid = !isBankrupt;
        this.bank = bank;
    }
    public Action(DepositAccount depositAccount){
        this.depositAccount = depositAccount;
    }
    public void takeAction() {
        if (isValid) {
            if (loan != null)
                loan.paymentCheck();
            if (bank != null && !bank.isBankrupt())
                bank.getIncome();
            if (depositAccount != null)
                depositAccount.chargeCurrentAccount();
        }
    }
    public void invalidate(){
        isValid = false;
    }
}
