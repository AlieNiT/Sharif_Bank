package controller;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum Command {

    ADD_BANK("Add bank (\\w+)"),
    ADD_BANK_WITH_INITIAL_AMOUNT("Add bank (\\w+) (\\d+)"),
    ADD_COMPANY("Add company (\\w+) (\\d+)"),
    ADD_PERSON("Add person ([A-Za-z]+) ([A-Za-z]+) (\\d+) (\\d{1,2})/(\\d{1,2})/(\\d{4})"),
    SET_BANK_INCOME_PERCENT("Set bank income percent (\\w+) (\\d+)"),
    SET_BANK_INTEREST_PERCENT("Set bank interest percent (\\w+) (long|short)-term (\\d+)"),
    INCREASE_BANK_BALANCE("increase bank balance (\\w+) (\\d+)"),
    OPEN_CURRENT_ACCOUNT("Open current account (\\w+) (\\d+) (\\d+)"),
    OPEN_DEPOSIT_ACCOUNT("Open deposit account (\\w+) (\\d+) (long|short)-term (\\d+)"),
    CLOSE_ACCOUNT("Close account (\\w+) (\\d+) (\\d+)"),
    CHANGE_CARD_PASSWORD("Change card password (\\d+) (\\d{4}) (\\d{4})"),
    SET_CARD_SECOND_PASSWORD("Set card second password (\\d+) (\\d{4}) (\\d{6})"),
    CHANGE_CARD_SECOND_PASSWORD("Change card second password (\\d+) (\\d{6}) (\\d{6})"),
    EXTEND_THE_EXPIRATION_DATE("Extend the expiration date (\\w+) (\\d+) (\\d+)"),
    DEPOSIT_MONEY("Deposit money (\\w+) (\\d+) (\\d+)"),
    WITHDRAW_MONEY_BY_BANK("Withdraw money (\\w+) (\\d+) (\\d+) (\\d+)"),
    WITHDRAW_MONEY_BY_CARD("Withdraw money (\\d+) (\\d{4}) (\\d+)"),
    GET_ACCOUNT_BALANCE("Get account balance (\\d+) (\\d{4})"),
    TRANSFER_MONEY_BY_BANK("Transfer money to another account (\\w+) (\\d+) (\\d+) (\\d+) (\\d+)"),
    TRANSFER_MONEY_BY_CARD("Transfer money to another account (\\d+) (\\d+) (\\d+) (\\d+)"),
    RECEIVE_LOAN("receive loan (\\w+) (\\d+) (\\d+)"),
    PAY_OFF_THE_LOAN("pay off the loan (\\w+) (\\d+) (\\d+)"),
    GO_FOR_N_DAYS("Go for (\\d+) days"),
    GO_FOR_N_MONTHS("Go for (\\d+) months"),
    GO_FOR_N_YEARS("Go for (\\d+) years"),
    GO_NEXT_DAY("Go next day"),
    GO_NEXT_MONTH("Go next month"),
    GO_NEXT_YEAR("Go next year"),
    GO_TO_DATE("Go to date (\\d{4})/(\\d{1,2})/(\\d{1,2})"),
    SHOW_ALL_BANKS("Show all banks"),
    SHOW_ALL_PERSONS("Show all persons"),
    SHOW_ALL_COMPANIES("Show all companies"),
    SHOW_ALL_LOANS("Show all loans"),
    SHOW_ALL_ACCOUNTS("Show all accounts"),
//    Show accounts for <nationalCode>
    SHOW_ACCOUNTS_FOR("Show accounts for (\\d+)"),
    //Show details of the loan for <nationalCode>
    SHOW_DETAILS_OF_LOAN("Show details of the loan for (\\d+)"),
    //Show bank interest <bankName>
    SHOW_BANK_INTEREST("Show bank interest (\\w+)"),
    //Show bank balance <bankName>
    SHOW_BANK_BALANCE("Show bank balance (\\w+)"),
    //Show central bank balance
    SHOW_CENTRAL_BANK_BALANCE("Show central bank balance"),
    SHOW_DATE("Show date");

    private final Pattern pattern;
    private final String regex;

    Command(String regex) {
        pattern = Pattern.compile(regex);
        this.regex = regex;
    }

    public static Matcher getMatcher(String input, Command command){
        return command.pattern.matcher(input);
    }

    public static Command commandMatcher(String input) {
        for (Command command : Command.values()) {
            if (Pattern.matches(command.regex, input))
                return command;
        }
        return null;
    }
}
