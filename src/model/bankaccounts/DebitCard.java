package model.bankaccounts;

import date.MyDate;
import date.TimeManager;
import model.customers.Customer;

public class DebitCard {
    String CVV2;
    String password;
    String secondPassword;
    BankAccount account;
    String cardNumber;
    Customer owner;
    MyDate creationDate;
    MyDate expirationDate;
    int accountBalance;

    public DebitCard(String CVV2, String password, String cardNumber, Customer owner, int accountBalance, CurrentAccount currentAccount) {
        this.CVV2 = CVV2;
        this.password = password;
        secondPassword = null;
        this.cardNumber = cardNumber;
        this.owner = owner;
        this.account = currentAccount;
        creationDate = TimeManager.getInstance().getDate();
        expirationDate = creationDate.plusYears(4);
        this.accountBalance = accountBalance;
    }

    public String depositMoney(int amount) {
        accountBalance += amount;
        return "Your command is taken care of.";
    }

    public String withdrawMoney(int amount, String password, int maximumAmount) {
        if (!password.equals(this.password))
            return "Your password is wrong.";
        if (amount >= maximumAmount)
            return "For values more than " + maximumAmount + "$, you should visit the bank in person.";
        if (this.accountBalance >= amount) {
            accountBalance -= amount;
            account.changeBalance(-amount);
            return "The money is withdrawn successfully!";
        } else return "You don't have enough money in your account!";
    }

    public String transferMoney(CurrentAccount receiverAccount, int amount) {
        if (withdrawMoney(amount, this.password, 5000).equals("The money is withdrawn successfully!"))
            return (receiverAccount.depositMoney(amount));
        else return withdrawMoney(amount, this.password, 5000);
    }


    public String changeSecondPassword(String secondPassword, String newSecondPassword) {
        if (this.secondPassword == null)
            return "You should first activate your second password";
        if (secondPassword.equals(this.secondPassword)) {
            this.secondPassword = newSecondPassword;
            return "Your second password is successfully changed.";
        }
        return "Your second password is incorrect!";
    }

    public String activateSecondPassword(String password, String secondPassword) {
        if (!this.password.equals(password))
            return "Your password is incorrect!";
        if (this.secondPassword != null)
            return "You already have a second password!";
        this.secondPassword = secondPassword;
        return "Your second password is activated and set to " + secondPassword;

    }

    public boolean isActive() {
        return TimeManager.getInstance().getDate().isBefore(expirationDate);
    }

    public String extendExpirationDate() {
        expirationDate = expirationDate.plusYears(4);
        return "Your account is successfully extended(current expiration date: " + expirationDate.toString() + ")";
    }

    public String getAccountDetails(Customer customer) {
        if (customer == this.owner) {
            String[] tmp = new String[5];
            tmp[0] = "Card Number: " + cardNumber;
            tmp[1] = "Password: " + password;
            tmp[2] = "CVV2: " + CVV2;
            tmp[3] = "Creation Date: " + creationDate.toString();
            tmp[4] = "Card's expiration Date: " + expirationDate.toString();
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 5; i++)
                stringBuilder.append(tmp[i]).append((i != 4) ? "\n" : "");
            return stringBuilder.toString();
        }
        return "You don't own this account";
    }

    public String changePassword(String password, String newPassword) {
        if (this.password.equals(password)) {
            this.password = newPassword;
            return "Your password is successfully changed.";
        }
        return "Your password is incorrect!";
    }
}
