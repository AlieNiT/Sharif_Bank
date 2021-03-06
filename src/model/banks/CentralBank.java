package model.banks;

import model.bankaccounts.BankAccount;
import model.bankaccounts.CurrentAccount;
import model.bankaccounts.DebitCard;
import model.bankaccounts.DepositAccount;
import model.customers.Customer;
import model.Loan;
import model.customers.Person;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static model.Utils.NDigitNumber;


public class CentralBank {

    private static CentralBank centralBank;
    private final ArrayList<Bank> banks;
    private final HashMap<Bank, ArrayList<BankAccount>> bankAccounts;
    private final HashMap<Customer, ArrayList<BankAccount>> customersAccounts;
    private final ArrayList<Loan> loans;
    private final HashMap<Customer, Loan> customersLoans;

    {
        banks = new ArrayList<>();
        bankAccounts = new HashMap<>();
        customersAccounts = new HashMap<>();
        loans = new ArrayList<>();
        customersLoans = new HashMap<>();
    }

    private CentralBank() {
    }

    public static CentralBank getInstance() {
        if (centralBank == null) {
            centralBank = new CentralBank();

        }

        return centralBank;
    }

    private String CVV2Maker() {
        Random random = new Random();
        int CVV2 = random.nextInt(900) + 100;
        return Integer.toString(CVV2);
    }

    private String passMaker() {
        Random random = new Random();
        int pass = random.nextInt(1000);
        return NDigitNumber(pass, 4);
    }

    static private int controlNumberMaker(String accountNumber) {
        int sum = 0;
        for (int i = 0; i < accountNumber.length(); i++) {
            int temp = ((i + 1) % 2 + 1) * Integer.parseInt(accountNumber.substring(i, i + 1));
            sum += (temp >= 10) ? temp - 9 : temp;
        }
        return 10 - sum % 10;
    }

    public String[] accountRequest(Bank bank) {
        String[] accountDetails = new String[3];
        if (banks.contains(bank)) {
            boolean tmp;
            int i;
            for (i = 1; ; i++) {
                tmp = true;
                for (BankAccount account :
                        bankAccounts.get(bank)) {
                    if (i == Integer.parseInt(account.getAccountNumber().substring(8, 15))) {
                        tmp = false;
                        break;
                    }
                }
                if (tmp)
                    break;
            }
            Random random = new Random();

            int numOfAccounts = i;
            accountDetails[0] = CVV2Maker();
            accountDetails[1] = passMaker();
            accountDetails[2] = bank.getNum()
                    + NDigitNumber(random.nextInt(10), 2)
                    + NDigitNumber(numOfAccounts, 7);
            accountDetails[2] = accountDetails[2] + controlNumberMaker(accountDetails[2]) % 10;
        }
        return accountDetails;
    }

    public boolean openAccountRequest(Customer customer) {
        if (customer instanceof Person)
            return ((Person) customer).isOlderThan(16);
        return true;
    }

    private BankAccount getBankAccount(String accountNum, String nationalCodeORId) {
        for (Map.Entry<Customer, ArrayList<BankAccount>> entry : customersAccounts.entrySet()) {
            if (entry.getKey().getNationalCode().equals(nationalCodeORId)) {
                for (BankAccount account :
                        entry.getValue()) {
                    if ((account.getAccountNumber()).equals(accountNum))
                        return account;
                }
            }
        }
        return null;
    }

    private BankAccount getBankAccount(String accountNum, Bank bank) {
        for (Map.Entry<Bank, ArrayList<BankAccount>> entry : bankAccounts.entrySet()) {
            if (entry.getKey().equals(bank)) {
                for (BankAccount account :
                        entry.getValue()) {
                    if (account.getAccountNumber().equals(accountNum))
                        return account;
                }
            }
        }
        return null;
    }


    private Bank getBankFromBankName(String bankName) {
        for (Bank bank : banks) {
            if (bank.name.equals(bankName))
                return bank;
        }
        return null;
    }

    private Bank getBankFromBankNum(String bankNum) {
        for (Bank bank : banks) {
            if (bank.num.equals(bankNum))
                return bank;
        }
        return null;
    }

    private DebitCard getDebitCardFromCardNum(String cardNum) {
        String bankNum = cardNum.substring(0, 6);
        for (Map.Entry<Bank, ArrayList<BankAccount>> entry : bankAccounts.entrySet()) {
            if (entry.getKey().getNum().equals(bankNum)) {
                for (BankAccount account : entry.getValue()) {
                    if (account.getAccountNumber().equals(cardNum) && account instanceof CurrentAccount) {
                        return ((CurrentAccount) account).getDebitCard();
                    }
                }
            }
        }
        return null;
    }

    //COMMAND HANDLING:
    public String addBank(String bankName) {
        for (KnownBank bank : KnownBank.values()) {
            if (bank.getName().equals(bankName)) {
                Bank bank1 = new Bank(bank.getName(), bank.getCode());
                banks.add(bank1);
                bankAccounts.put(bank1, new ArrayList<>());
                return "Bank is added successfully:\nName: " + bank.getName() + "\nCode: " + bank.getCode();
            }
        }
        return "This bank doesn't exist.";
    }

    public String addBank(String bankName, long initialAmount) {
        if (getBankFromBankName(bankName) != null)
            return "This bank already exists.";
        for (KnownBank bank : KnownBank.values()) {
            if (bank.getName().equals(bankName)) {
                banks.add(new Bank(bank.getName(), bank.getCode(), initialAmount));
                return "Bank is added successfully:\nName: " + bank.getName() + "\nCode: " + bank.getCode();
            }
        }
        return "This bank doesn't exist.";
    }

    public void addCustomerAccount(Customer customer, BankAccount bankAccount) {
        ArrayList<BankAccount> list1 = bankAccounts.get(bankAccount.getBank());
        if (list1 == null)
            list1 = new ArrayList<>();
        list1.add(bankAccount);
        bankAccounts.put(bankAccount.getBank(), list1);
        ArrayList<BankAccount> list = customersAccounts.get(customer);
        if (list == null)
            list = new ArrayList<>();
        list.add(bankAccount);
        customersAccounts.put(customer, list);
    }

    public String setBankIncomePercent(String bankName, int incomePercent) {
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        return bank.setIncomePercent(incomePercent);
    }

    public String setBankInterestPercent(String bankName, String type, int incomePercent) {
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        if (type.equals("short"))
            return bank.setShortTermInterestPercent(incomePercent);
        return bank.setLongTermInterestPercent(incomePercent);
    }

    public String increaseBankBalance(String bankName, int amount) {
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        return bank.changeBalance(amount);
    }

    public String openCurrentAccount(String bankName, String nationalCode, int initialAmount) {
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        Customer customer = Customer.getCustomer(nationalCode);
        if (customer == null)
            return "No one exists with this national code";
        return bank.openCurrentAccount(customer, initialAmount);
    }

    public String openDepositAccount(String bankName, String nationalCode, String type, int initialAmount) {
        if ((type.equals("short") && initialAmount < 5000))
            return "for short term deposit account,the initial amount should be at least 5000$";
        if ((type.equals("long") && initialAmount < 10000))
            return "for long term deposit account,the initial amount should be at least 10000$";
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        Customer customer = Customer.getCustomer(nationalCode);
        if (customer == null)
            return "No one exists with this national code";
        return bank.openDepositAccount(customer, type, initialAmount);
    }

    public String closeAccount(String bankName, String nationalCode, String accountNum) {
        BankAccount account = getBankAccount(accountNum, nationalCode);
        Customer owner = Customer.getCustomer(nationalCode);
        if (account == null)
            return "Bank account doesn't exist.";
        if (!account.getBank().getName().equals(bankName))
            return "Name of the bank is wrong.";
        if (account instanceof CurrentAccount && ((CurrentAccount) account).hasDepositAccount())
            return "There are deposit accounts attached to your current account.You should close them or change their current account.";
        ArrayList<BankAccount> bankAcc = bankAccounts.get(account.getBank());
        bankAcc.remove(account);
        bankAccounts.put(account.getBank(), bankAcc);
        return account.getBank().closeAccount(account, owner);
    }

    public String changeCardPassword(String cardNum, String password, String newPassword) {
        DebitCard debitCard = getDebitCardFromCardNum(cardNum);
        if (debitCard == null)
            return "Your card number is wrong.";
        if (!debitCard.isActive())
            return "Your card has expired.";
        return debitCard.changePassword(password, newPassword);
    }

    public String changeCardSecondPassword(String cardNum, String password, String newPassword) {
        DebitCard debitCard = getDebitCardFromCardNum(cardNum);
        if (debitCard == null)
            return "Your card number is wrong.";
        if (!debitCard.isActive()) {
            return "Your card has expired.";
        }
        return debitCard.changeSecondPassword(password, newPassword);

    }

    public String setCardSecondPassword(String cardNum, String password, String secondPassword) {
        DebitCard debitCard = getDebitCardFromCardNum(cardNum);
        if (debitCard == null)
            return "Your card number is wrong.";
        if (!debitCard.isActive())
            return "Your card has expired.";

        return debitCard.activateSecondPassword(password, secondPassword);

    }

    public String extendTheExpirationDate(String bankName, String cardNum, String nationalCode) {
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        Customer customer = Customer.getCustomer(nationalCode);
        if (customer == null)
            return "No one exists with this national code";
        DebitCard debitCard = getDebitCardFromCardNum(cardNum);
        if (debitCard == null)
            return "Your card number is wrong.";
        return debitCard.extendExpirationDate();
    }

    public String depositMoney(String bankName, String accountNum, int amount) {
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        bank.changeBalance(amount);
        BankAccount account = getBankAccount(accountNum, bank);
        if (account == null)
            return "This account doesn't exist.";
        if (account instanceof DepositAccount)
            return "You can't deposit money with a deposit account";
        return ((CurrentAccount) account).depositMoney(amount);
    }

    public String withdrawMoney(String cardNum, String password, int amount) {
        DebitCard debitCard = getDebitCardFromCardNum(cardNum);
        if (debitCard == null)
            return "Your card number is wrong.";
        if (!debitCard.isActive())
            return "Your card has expired.";
        String response = debitCard.withdrawMoney(amount, password, 2000);
        if (response.equals("The money is withdrawn successfully!")) {
            getBankFromBankNum(cardNum.substring(0, 6)).changeBalance(-amount);
        }
        return response;
    }

    public String withdrawMoney(String bankName, String accountNum, String nationalCode, int amount) {
        if (Customer.getCustomer(nationalCode) == null)
            return "Wrong national code";
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        bank.changeBalance(amount);
        BankAccount account = getBankAccount(accountNum, bank);
        if (account == null)
            return "This account doesn't exist.";
        if (account instanceof DepositAccount)
            return "You can't withdraw money with a deposit account";
        return ((CurrentAccount) account).withdrawMoney(amount);
    }

    public String getAccountBalance(String cardNum, String password) {
        Bank bank = getBankFromBankNum(cardNum.substring(0, 6));
        if (bank == null)
            return "This bank doesn't exist.";
        BankAccount account = getBankAccount(cardNum, bank);
        if (!(account instanceof CurrentAccount))
            return "Your card umber is wrong.";
        if (!((CurrentAccount) account).checkPassword(password))
            return "Your password is wrong.";
        if (!((CurrentAccount) account).getDebitCard().isActive())
            return "Your card isn't active.";
        return account.getBalance(bank);
    }

    public String transferMoneyByCard(String cardNum, String password, String receiverCardNum, int amount) {
        BankAccount receiver = getBankAccount(receiverCardNum, getBankFromBankNum(receiverCardNum.substring(0, 6)));
        if (receiver == null)
            return "Receiver card number is wrong.";

        BankAccount sender = getBankAccount(cardNum, getBankFromBankNum(cardNum.substring(0, 6)));
        if (sender == null)
            return "Receiver card number is wrong.";
        if (receiver instanceof DepositAccount || sender instanceof DepositAccount)
            return "You can't transfer money to or from a deposit account.";
        if (!((CurrentAccount) sender).checkPassword(password))
            return "Your password is wrong";
        return ((CurrentAccount) sender).getDebitCard().transferMoney((CurrentAccount) receiver, amount);
    }

    public String transferMoneyByBank(String bankName, String accountNum, String nationalCode, String receiverAccountNum, int amount) {
        BankAccount sender = getBankAccount(accountNum, getBankFromBankNum(accountNum.substring(0, 6)));
        if (sender == null)
            return "Receiver card number is wrong.";
        if (getBankFromBankName(bankName) != sender.getBank())
            return "You can transfer money only from your bank!";
        if (getBankAccount(accountNum, nationalCode) != sender)
            return "Your national code is wrong.";
        BankAccount receiver = getBankAccount(receiverAccountNum, getBankFromBankNum(receiverAccountNum.substring(0, 6)));
        if (receiver == null)
            return "Sender card number is wrong.";
        return ((CurrentAccount) sender).transferMoney((CurrentAccount) receiver, amount);
    }

    public String receiveLoan(String bankName, String nationalCode, int amount) {
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        Customer customer = Customer.getCustomer(nationalCode);
        if (customer == null)
            return "Your national code is wrong";
        if (customer instanceof Person && !((Person) customer).isOlderThan(18))
            return "You should be 18 years old in order to be able to get a loan.";
        if (bank.hasLoan(customer))
            return "You already have a loan in this bank.";
        CurrentAccount currentAccount = null;
        for (BankAccount account : customersAccounts.get(customer)) {
            if (account instanceof CurrentAccount)
                currentAccount = (CurrentAccount) account;
        }
        Loan loan = bank.getLoan(customer, amount, currentAccount);
        if (loan == null)
            return "You don't have enough money in your account.";
        loans.add(loan);
        customersLoans.put(customer, loan);
        if (currentAccount != null)
            currentAccount.depositMoney(amount);
        return "Your account(card number:" + currentAccount.getAccountNumber() + ")is charged for " + amount + "$.";
    }

    public String payOffTheLoan(String bankName, String nationalCode, int amount) {
        Bank bank = getBankFromBankName(bankName);
        if (bank == null)
            return "This bank doesn't exist.";
        Customer customer = Customer.getCustomer(nationalCode);
        if (customer == null)
            return "Your national code is wrong";
        Loan loan = customer.getCurrentLoan();
        String response = loan.payOffTheLoan(amount);
        if (loan.isPaidBack()) {
            loans.remove(loan);
            customersLoans.remove(customer);
            bank.removeLoan(loan);
            customer.paidLoan();
        }
        return response;
    }

    public String showAllBanks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Bank bank : banks) {
            stringBuilder.append(bank.getName()).append("\n");
        }
        return stringBuilder.toString();
    }

    public String showAllLoans() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Loan loan : loans) {
            stringBuilder.append(loan.getDetail());
        }
        return stringBuilder.toString();
    }

    public String showAllAccounts() {
        StringBuilder stringBuilder = new StringBuilder();
        for (Bank bank : banks) {
            ArrayList<BankAccount> accounts = bankAccounts.get(bank);
            for (BankAccount account : accounts) {
                String s = "- number:" + account.getAccountNumber() + " type:" + account.getType() + " customer ID:" + account.getOwner();
                stringBuilder.append(s).append("\n");
            }
        }
        return stringBuilder.toString();
    }

    public String showAccountsFor(String group) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Map.Entry<Customer, ArrayList<BankAccount>> entry : customersAccounts.entrySet()) {
            if (entry.getKey().getNationalCode().equals(group)) {
                for (BankAccount account : entry.getValue()) {
                    String s = "- number:" + account.getAccountNumber() + " type:" + account.getType() + " customer ID:" + account.getOwner();
                    stringBuilder.append(s).append("\n");
                }
                return stringBuilder.toString();
            }
        }
        return "Wrong national code";
    }

    public String showDetailsOfLoan(String group) {
        for (Loan loan : loans) {
            if (loan.getNationalCode().equals(group)) {
                return loan.getDetail();
            }
        }
        return "No loan for this national code";
    }

    public String showBankBalance(String group) {
        Bank bank = getBankFromBankName(group);
        if (bank == null)
            return "No bank with this name.";
        return String.valueOf(bank.getBalance());
    }

    public String showBankInterest(String group) {
        Bank bank = getBankFromBankName(group);
        if (bank == null)
            return "No bank with this name.";
        return bank.getInterestPercent();
    }

    public String showCentralBankBalance() {
        long sum = 0;
        for (Bank bank :
                banks) {
            sum += bank.getBalance();
        }
        return String.valueOf(sum);
    }

    public void clearLoan(Loan loan) {
        loans.remove(loan);
        for (Map.Entry<Customer, Loan> entry :
                customersLoans.entrySet()) {
            if (entry.getValue() == loan)
                customersLoans.remove(entry.getKey(), entry.getValue());
        }
    }

    public void resetBankActions() {
        for (Bank bank :
                banks) {
            bank.resetDates();
        }
    }

    public void addAdditionalAccount(Customer owner, CurrentAccount currentAccount) {
        customersAccounts.computeIfAbsent(owner, k -> new ArrayList<>());
        ArrayList<BankAccount> accounts = customersAccounts.get(owner);
        accounts.add(currentAccount);
        customersAccounts.put(owner, accounts);
        bankAccounts.computeIfAbsent(currentAccount.getBank(), k -> new ArrayList<>());
        accounts = bankAccounts.get(currentAccount.getBank());
        accounts.add(currentAccount);
        bankAccounts.put(currentAccount.getBank(), accounts);
    }
}


