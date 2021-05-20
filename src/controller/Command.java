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
    OPEN_DEPOSIT_ACCOUNT("Open deposit account (\\w+) (\\d+) (Long|short)-term (\\d+)"),
    CLOSE_ACCOUNT("Close account (\\w+) (\\d+) (\\d+)"),
    CHANGE_CARD_PASSWORD("Change card password (\\d+) (\\d{4}) (\\d{4})"),
    SET_CARD_SECOND_PASSWORD("Set card second password (\\d+) (\\d{4}) (\\d{6})"),
    CHANGE_CARD_SECOND_PASSWORD("Change card second password (\\d+) (\\d{6}) (\\d{6})"),
    EXTEND_THE_EXPIRATION_DATE("Extend the expiration date (\\w+) (\\d+) (\\d+)"),
    DEPOSIT_MONEY("Deposit money (\\w+) (\\d+) (\\d+)"),
    WITHDRAW_MONEY_BY_BANK("Withdraw money (\\d+) (\\d+) (\\d+) (\\d+)"),
    WITHDRAW_MONEY_BY_CARD("Withdraw money (\\d+) (\\d{4}) (\\d+)"),
    GET_ACCOUNT_BALANCE("Get account balance (\\d+) (\\d{4})"),
    TRANSFER_MONEY_BY_BANK("Transfer money to another account (\\w+) (\\d+) (\\d+) (\\d+) (\\d+)"),
    TRANSFER_MONEY_BY_CARD("Transfer money to another account (\\d+) (\\d+) (\\d+) (\\d+)"),
    RECEIVE_LOAN("receive loan (\\w+) (\\d+) (\\d+)"),
    PAY_OFF_THE_LOAN("pay off the loan (\\w+) (\\d+) (\\d+)"),
    GO_FOR_N_DAYS("go for (\\d+) days"),
    GO_FOR_N_MONTHS("go for (\\d+) months"),
    GO_FOR_N_YEARS("go for (\\d+) years"),
    GO_NEXT_DAY("go next day"),
    GO_NEXT_MONTH("go next month"),
    GO_NEXT_YEAR("go next year"),
    GO_TO_DATE("go to date (\\d{4})/(\\d{1,2})/(\\d{1,2})");

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
