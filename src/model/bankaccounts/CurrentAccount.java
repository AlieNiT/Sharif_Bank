package model.bankaccounts;

import model.banks.Bank;
import model.banks.CentralBank;
import model.customers.Customer;

import java.util.ArrayList;

public class CurrentAccount extends BankAccount {
    DebitCard debitCard;
    ArrayList<DepositAccount> depositAccounts = new ArrayList<>();

    public CurrentAccount(Customer owner, Bank bank, int accountBalance) {
        super(owner, bank, accountBalance);
        type = "current";
        String[] cardDetails = CentralBank.getInstance().accountRequest(this.bank);
        debitCard = new DebitCard(cardDetails[0], cardDetails[1], cardDetails[2], owner, accountBalance, this);
        this.accountNumber = cardDetails[2];
    }

    public String depositMoney(int amount) {
        this.accountBalance += amount;
        return debitCard.depositMoney(amount);
    }

    public String withdrawMoney(int amount) {
        if (this.accountBalance >= amount) {
            accountBalance -= amount;
            debitCard.accountBalance -= amount;
            return "The money is withdrawn successfully!";
        } else return "You don't have enough money in your account!";
    }

    public DebitCard getDebitCard() {
        return debitCard;
    }

    public String transferMoney(CurrentAccount receiver, int amount) {
        String withdrawResponse = this.withdrawMoney(amount);
        if (withdrawResponse.equals("You don't have enough money in your account!"))
            return withdrawResponse;
        return receiver.depositMoney(amount);
    }

    public boolean checkPassword(String password) {
        return password.equals(this.getDebitCard().password);
    }

    public void addDepositAccount(DepositAccount depositAccount) {
        depositAccounts.add(depositAccount);
    }

    public boolean hasDepositAccount() {
        return (!depositAccounts.isEmpty());
    }

    public void removeDepositAccount(DepositAccount depositAccount) {
        depositAccounts.remove(depositAccount);
    }
}
