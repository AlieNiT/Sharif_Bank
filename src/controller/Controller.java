//package controller;
//
//import java.util.Locale;
//import java.util.Objects;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//
//import static controller.Command.commandMatcher;
//import static model.banks.Bank.openCurrentAccount;
//import static model.banks.Bank.openDepositAccount;
//
//public class Controller {
//    Scanner inputStream = new Scanner(System.in);
//    String input;
//
//    public void run(){
//        boolean EXIT = false;
//        while (!EXIT){
//            System.out.print("Enter your command:");
//            EXIT = runCommand(inputStream.nextLine().trim());
//        }
//    }
//
//    private boolean runCommand(String input){
//
//        if(input.toLowerCase(Locale.ROOT).equals("exit"))
//            return false;
//        switch (commandMatcher(input)) {
//            case ADD_BANK:
//                addBank(Command.getMatcher(input, Command.ADD_BANK));
//                break;
//
//            case ADD_COMPANY:
//                addCompany(Command.getMatcher(input, Command.ADD_COMPANY));
//                break;
//
//            case ADD_PERSON:
//                addPerson(Command.getMatcher(input, Command.ADD_PERSON));
//                break;
//
//            case SET_BANK_INCOME_PERCENT:
//                setBankIncomePercent(Command.getMatcher(input, Command.SET_BANK_INCOME_PERCENT));
//                break;
//
//            case SET_BANK_INTEREST_PERCENT:
//                setBankInterestPercent(Command.getMatcher(input, Command.SET_BANK_INTEREST_PERCENT));
//                break;
//
//            case INCREASE_BANK_BALANCE:
//                increaseBankBalance(Command.getMatcher(input, Command.INCREASE_BANK_BALANCE));
//                break;
//
//            case OPEN_DEPOSIT_ACCOUNT:
//                openDepositAccount(Command.getMatcher(input, Command.OPEN_DEPOSIT_ACCOUNT));
//                break;
//
//            case OPEN_CURRENT_ACCOUNT:
//                openCurrentAccount(Command.getMatcher(input, Command.OPEN_CURRENT_ACCOUNT));
//
//            case CLOSE_ACCOUNT:
//                closeAccount(Command.getMatcher(input, Command.CLOSE_ACCOUNT));
//                break;
//
//            case CHANGE_CARD_PASSWORD:
//                changeCardPassword(Command.getMatcher(input, Command.CHANGE_CARD_PASSWORD));
//                break;
//
//            case SET_CARD_SECOND_PASSWORD:
//                setCardSecondPassword(Command.getMatcher(input, Command.SET_CARD_SECOND_PASSWORD));
//                break;
//
//            case CHANGE_CARD_SECOND_PASSWORD:
//                chageCardSecondPassword(Command.getMatcher(input, Command.CHANGE_CARD_SECOND_PASSWORD));
//                break;
//
//            case EXTEND_THE_EXPIRATION_DATE:
//                extendTheExpirationDate(Command.getMatcher(input, Command.EXTEND_THE_EXPIRATION_DATE));
//                break;
//
//            case DEPOSIT_MONEY:
//                depositMoney(Command.getMatcher(input, Command.DEPOSIT_MONEY));
//                break;
//
//            case WITHDRAW_MONEY:
//                withdrawMoney(Command.getMatcher(input, Command.WITHDRAW_MONEY));
//                break;
//
//            case GET_ACCOUNT_BALANCE:
//                getAccountBalance(Command.getMatcher(input, Command.GET_ACCOUNT_BALANCE));
//                break;
//
//            case TRANSFER_MONEY_TO_ANOTHER_ACCOUNT:
//                transferMoneyToAnotherAccount(Command.getMatcher(input, Command.TRANSFER_MONEY_TO_ANOTHER_ACCOUNT));
//                break;
//
//            case RECEIVE_LOAN:
//                receiveLoan(Command.getMatcher(input, Command.RECEIVE_LOAN));
//                break;
//
//            case PAY_OFF_THE_LOAN:
//                payOffTheLoan(Command.getMatcher(input, Command.PAY_OFF_THE_LOAN));
//                break;
//
//            case ADD_BANK:
//                addBank(Command.getMatcher(input, Command.ADD_BANK));
//                break;
//
//            case GO_FOR_N_DAYS:
//                goForNDays(Command.getMatcher(input, Command.GO_FOR_N_DAYS));
//                break;
//
//            case GO_FOR_N_MONTHS:
//                goForNMonths(Command.getMatcher(input, Command.GO_FOR_N_MONTHS));
//                break;
//
//            case GO_FOR_N_YEARS:
//                goForNYears(Command.getMatcher(input, Command.GO_FOR_N_YEARS));
//                break;
//
//            case GO_NEXT_DAY:
//                goNextDay();
//                break;
//
//            case GO_NEXT_MONTH:
//                goNextMonth();
//                break;
//
//            case GO_NEXT_YEAR:
//                goNextYear();
//                break;
//
//            case GO_TO_DATE:
//                goToDate(Command.getMatcher(input, Command.GO_TO_DATE));
//
//        }
//        return true;
//    }
//}
