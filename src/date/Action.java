package date;

import model.Loan;
import model.bankaccounts.DepositAccount;
import model.banks.Bank;
import model.customers.Customer;

public class Action {
    boolean isValid = true;
    DepositAccount depositAccount = null;
    Loan loan = null;
    Bank bank = null;

    public Action(Loan loan) {
        this.loan = loan;
    }

    public Action(Bank bank, boolean isBankrupt) {
        isValid = !isBankrupt;
        this.bank = bank;
    }

    public Action(DepositAccount depositAccount) {
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

    public void invalidate() {
        isValid = false;
    }
}
