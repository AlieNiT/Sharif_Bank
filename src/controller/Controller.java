package controller;
import date.MyDate;
import date.TimeManager;
import model.banks.CentralBank;
import model.customers.Company;
import model.customers.Customer;
import model.customers.Person;
import java.util.Scanner;
import java.util.regex.Matcher;
import static controller.Command.commandMatcher;

public class Controller {
    public Scanner inputStream = new Scanner(System.in);

    public void run(){
        while (true){
            System.out.print("Enter your command:");
            String command = inputStream.nextLine().trim();
            String response = runCommand(command);
//            TimeManager.write("calender.json");
            if (response!= null &&response.equals("Exit command"))
                return;
            else System.out.println(response);
        }
    }

    private String runCommand(String input) {
        if (input.equalsIgnoreCase("exit"))
            return "Exit command";
        Matcher M;
//       try {
            switch (commandMatcher(input)) {
                case ADD_BANK:
                    M = Command.getMatcher(input, Command.ADD_BANK);
                    if (M.find())
                        return CentralBank.getInstance().addBank(M.group(1));
                    break;

                case ADD_BANK_WITH_INITIAL_AMOUNT:
                    M = Command.getMatcher(input, Command.ADD_BANK_WITH_INITIAL_AMOUNT);
                    if (M.find())
                        return CentralBank.getInstance().addBank(M.group(1),Integer.parseInt(M.group(2)));
                    break;

                case ADD_COMPANY:
                    M = Command.getMatcher(input, Command.ADD_COMPANY);
                    if (M.find()) {
                        Company company = new Company(M.group(1), M.group(2));
                        return "New company added.ID: "+company.getNationalCode();
                    }break;

                case ADD_PERSON:
                    M = Command.getMatcher(input, Command.ADD_PERSON);
                    if (M.find()) {
                        MyDate date = MyDate.of( Integer.parseInt(M.group(6)),
                                                        Integer.parseInt(M.group(5)),
                                                        Integer.parseInt(M.group(4)));
                        new Person(M.group(1),M.group(2),M.group(3),date);
                        return "New person added.";
                    }
                    break;

                case SET_BANK_INCOME_PERCENT:
                    M = Command.getMatcher(input, Command.SET_BANK_INCOME_PERCENT);
                    if (M.find())
                        return CentralBank.getInstance().setBankIncomePercent(M.group(1),Integer.parseInt(M.group(2)));
                    break;

                case SET_BANK_INTEREST_PERCENT:
                    M = Command.getMatcher(input, Command.SET_BANK_INTEREST_PERCENT);
                    if (M.find()) {
                        System.out.println("dfff");
                        return CentralBank.getInstance().setBankInterestPercent(M.group(1), M.group(2), Integer.parseInt(M.group(3)));
                    }
                    break;

                case INCREASE_BANK_BALANCE:
                    M = Command.getMatcher(input, Command.INCREASE_BANK_BALANCE);
                    if (M.find())
                        return CentralBank.getInstance().increaseBankBalance(M.group(1),Integer.parseInt(M.group(2)));
                    break;

                case OPEN_DEPOSIT_ACCOUNT:
                    M = Command.getMatcher(input, Command.OPEN_DEPOSIT_ACCOUNT);
                    if (M.find()) {
                        return CentralBank.getInstance().openDepositAccount(M.group(1), M.group(2), M.group(3), Integer.parseInt(M.group(4)));

                    }
                    break;

                case OPEN_CURRENT_ACCOUNT:
                    M = Command.getMatcher(input, Command.OPEN_CURRENT_ACCOUNT);
                    if (M.find())
                        return CentralBank.getInstance().openCurrentAccount(M.group(1), M.group(2), Integer.parseInt(M.group(3)));

                case CLOSE_ACCOUNT:
                    M = Command.getMatcher(input, Command.CLOSE_ACCOUNT);
                    if (M.find())
                        return CentralBank.getInstance().closeAccount(M.group(1),M.group(2),M.group(3));
                    break;

                case CHANGE_CARD_PASSWORD:
                    M = Command.getMatcher(input, Command.CHANGE_CARD_PASSWORD);
                    if (M.find())
                        return CentralBank.getInstance().changeCardPassword(M.group(1),M.group(2),M.group(3));
                    break;

                case SET_CARD_SECOND_PASSWORD:
                    M = Command.getMatcher(input, Command.SET_CARD_SECOND_PASSWORD);
                    if (M.find())
                        return CentralBank.getInstance().setCardSecondPassword(M.group(1),M.group(2),M.group(3));
                    break;

                case CHANGE_CARD_SECOND_PASSWORD:
                    M = Command.getMatcher(input, Command.CHANGE_CARD_SECOND_PASSWORD);
                    if (M.find())
                        return CentralBank.getInstance().changeCardSecondPassword(M.group(1),M.group(2),M.group(3));
                    break;

                case EXTEND_THE_EXPIRATION_DATE:
                    M = Command.getMatcher(input, Command.EXTEND_THE_EXPIRATION_DATE);
                    if (M.find())
                        return CentralBank.getInstance().extendTheExpirationDate(M.group(1),M.group(2),M.group(3));
                    break;

                case DEPOSIT_MONEY:
                    M = Command.getMatcher(input, Command.DEPOSIT_MONEY);
                    if (M.find())
                        return CentralBank.getInstance().depositMoney(M.group(1),M.group(2),Integer.parseInt(M.group(3)));
                    break;

                case WITHDRAW_MONEY_BY_CARD:
                    M = Command.getMatcher(input, Command.WITHDRAW_MONEY_BY_CARD);
                    if (M.find())
                        return CentralBank.getInstance().withdrawMoney(M.group(1),M.group(2),Integer.parseInt(M.group(3)));
                    break;

                case WITHDRAW_MONEY_BY_BANK:
                    M = Command.getMatcher(input, Command.WITHDRAW_MONEY_BY_BANK);
                    if (M.find())
                        return CentralBank.getInstance().withdrawMoney(M.group(1),M.group(2),M.group(3),Integer.parseInt(M.group(4)));
                    break;

                case GET_ACCOUNT_BALANCE:
                    M = Command.getMatcher(input, Command.GET_ACCOUNT_BALANCE);
                    if (M.find())
                        return CentralBank.getInstance().getAccountBalance(M.group(1),M.group(2));
                    break;

                case TRANSFER_MONEY_BY_BANK:
                    M = Command.getMatcher(input, Command.TRANSFER_MONEY_BY_BANK);
                    if (M.find())
                        return CentralBank.getInstance().transferMoneyByBank(M.group(1),M.group(2),M.group(3),M.group(4),Integer.parseInt(M.group(5)));
                    break;

                case TRANSFER_MONEY_BY_CARD:
                    M = Command.getMatcher(input, Command.TRANSFER_MONEY_BY_CARD);
                    if (M.find())
                        return CentralBank.getInstance().transferMoneyByCard(M.group(1),M.group(2),M.group(3),Integer.parseInt(M.group(4)));
                    break;

                case RECEIVE_LOAN:
                    M = Command.getMatcher(input, Command.RECEIVE_LOAN);
                    if (M.find())
                        return CentralBank.getInstance().receiveLoan(M.group(1),M.group(2),Integer.parseInt(M.group(3)));
                    break;

                case PAY_OFF_THE_LOAN:
                    M = Command.getMatcher(input, Command.PAY_OFF_THE_LOAN);
                    if (M.find())
                        return CentralBank.getInstance().payOffTheLoan(M.group(1),M.group(2),Integer.parseInt(M.group(3)));
                    break;

                case GO_FOR_N_DAYS:
                    M = Command.getMatcher(input, Command.GO_FOR_N_DAYS);
                    if (M.find())
                        return TimeManager.getInstance().goForNDays(Integer.parseInt(M.group(1)));
                    break;

                case GO_FOR_N_MONTHS:
                    M = Command.getMatcher(input, Command.GO_FOR_N_MONTHS);
                    if (M.find())
                        return TimeManager.getInstance().goForNMonths(Integer.parseInt(M.group(1)));
                    break;

                case GO_FOR_N_YEARS:
                    M = Command.getMatcher(input, Command.GO_FOR_N_YEARS);
                    if (M.find())
                        return TimeManager.getInstance().goForNYears(Integer.parseInt(M.group(1)));
                    break;

                case GO_NEXT_DAY:
                    return TimeManager.getInstance().goNextDay();

                case GO_NEXT_MONTH:
                    return TimeManager.getInstance().goNextMonth();

                case GO_NEXT_YEAR:
                    return TimeManager.getInstance().goNextYear();

                case GO_TO_DATE:
                    M = Command.getMatcher(input, Command.GO_TO_DATE);
                    if (M.find())
                        return TimeManager.getInstance().goToDate(Integer.parseInt(M.group(3)),Integer.parseInt(M.group(2)),Integer.parseInt(M.group(1)));

                case SHOW_DATE:
                    return TimeManager.getInstance().getDate().toString();

                case SHOW_ALL_BANKS:
                    return CentralBank.getInstance().showAllBanks();

                case SHOW_ALL_LOANS:
                    return CentralBank.getInstance().showAllLoans();

                case SHOW_ALL_PERSONS:
                    return Customer.showAllPersons();

                case SHOW_ALL_ACCOUNTS:
                    return CentralBank.getInstance().showAllAccounts();

                case SHOW_ALL_COMPANIES:
                    return Customer.showAllCompanies();

                case SHOW_ACCOUNTS_FOR:
                    M = Command.getMatcher(input, Command.SHOW_ACCOUNTS_FOR);
                    if (M.find())
                        return CentralBank.getInstance().showAccountsFor(M.group(1));

                case SHOW_DETAILS_OF_LOAN:
                    M = Command.getMatcher(input, Command.SHOW_DETAILS_OF_LOAN);
                    if (M.find())
                        return CentralBank.getInstance().showDetailsOfLoan(M.group(1));

                case SHOW_BANK_BALANCE:
                    M = Command.getMatcher(input, Command.SHOW_BANK_BALANCE);
                    if (M.find())
                        return CentralBank.getInstance().showBankBalance(M.group(1));

                case SHOW_BANK_INTEREST:
                    M = Command.getMatcher(input, Command.SHOW_BANK_INTEREST);
                    if (M.find())
                        return CentralBank.getInstance().showBankInterest(M.group(1));

                case SHOW_CENTRAL_BANK_BALANCE:
                    return CentralBank.getInstance().showCentralBankBalance();
            }
//        }catch(Exception e){
//            System.out.println(e);
//        }
        return "Wrong type of input.";
    }
}
