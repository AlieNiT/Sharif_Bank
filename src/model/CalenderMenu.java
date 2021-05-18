//package sharifbank;
//
//import java.time.LocalDate;
//import java.time.LocalTime;
//import java.time.format.DateTimeFormatter;
//import java.util.Scanner;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//public class CalenderMenu extends Menu {
//
//    private final Calendar calendar;
//    private final Menu mainMenu;
//    private final Database database;
//    private final Account account;
//
//    enum EditField {
//        TITLE,
//        MEET,
//        REPEAT,
//        KIND_OF_REPEAT,
//        START_TIME,
//        END_TIME
//    }
//
//    public CalenderMenu(Calendar calendar, Menu mainMenu, Database database, Account account) {
//        this.calendar = calendar;
//        this.mainMenu = mainMenu;
//        this.database = database;
//        this.account = account;
//    }
//
//    @Override
//    public Menu giveCommand(Scanner input) {
//        String command = input.nextLine().trim();
//        String word = "\\w+";
//        String addEvent = "^Add\\s+Event\\s+(\\S+)\\s+(\\d{4}_\\d{2}_\\d{2})\\s+(\\S+)\\s+([DWM])?\\s+([TF])";
//        String addTask = "^Add\\s+Task\\s+(\\S+)\\s+(\\d{2}_\\d{2})\\s+(\\d{2}_\\d{2})\\s+(\\d{4}_\\d{2}_\\d{2})\\s+" +
//                "(\\S+)\\s+([DWM])?\\s+([TF])$";
//        String editEvent = "^Edit\\s+Event\\s+(\\S+)\\s+(.+)$";
//        String editTask = "^Edit\\s+Task\\s+(\\S+)\\s+(.+)$";
//        String deleteEvent = "^Delete\\s+Event\\s+(\\S+)$";
//        String deleteTask = "^Delete\\s+Task\\s+(\\S+)$";
//        String showEvents = "Show\\s+Events";
//        String showTasks = "Show\\s+tasks";
//        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy_MM_dd");
//        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("HH_mm");
//
//        if (Pattern.matches(addEvent, command)) {
//            Matcher matcher = commandMatcher(command, addEvent);
//            Case.Frequency frequency = checkFrequency(matcher.group(3));
//            if (frequency == Case.Frequency.NONE) {
//                if (matcher.group(4).matches("[DWM]")) {
//                    System.out.println("invalid command!");
//                    return this;
//                }
//            }
//            if (!calendar.getOwner().equals(account.getUsername())) {
//                System.out.println("you don't have access to do this!");
//                return this;
//            }
//            if (!matcher.group(1).matches(word)) {
//                System.out.println("invalid title!");
//                return this;
//            }
//            if (isDateInvalid(matcher.group(2))) {
//                System.out.println("invalid start date!");
//                return this;
//            }
//            Case.Repeat repeat = checkRepeat(matcher.group(4));
//            if (frequency == Case.Frequency.UNTIL_END_DATE) {
//                if (isDateInvalid(matcher.group(3))) {
//                    System.out.println("invalid end date!");
//                    return this;
//                }
//            }
//            if (database.hasEventTitle(matcher.group(1))) {
//                System.out.println("there is another event with this title!");
//                return this;
//            }
//            boolean hasMeeting = false;
//            if (matcher.group(5).equals("T")) {
//                hasMeeting = true;
//                input.nextLine();
//            }
//            System.out.println("event added successfully!");
//            switch (frequency) {
//                case UNTIL_END_DATE:
//                    database.addEvent(new Event(matcher.group(1), LocalDate.parse(matcher.group(2), dateFormat),
//                                    LocalDate.parse(matcher.group(3), dateFormat), frequency, repeat, -1, hasMeeting),
//                            calendar);
//                    break;
//
//                case REPEAT:
//                    database.addEvent(new Event(matcher.group(1), LocalDate.parse(matcher.group(2), dateFormat),
//                            null, frequency, repeat, Integer.parseInt(matcher.group(3)), hasMeeting), calendar);
//                    break;
//
//                case NONE:
//                    database.addEvent(new Event(matcher.group(1), LocalDate.parse(matcher.group(2), dateFormat),
//                            null, frequency, null, -1, hasMeeting), calendar);
//                    break;
//            }
//            return this;
//
//        } else if (Pattern.matches(addTask, command)) {
//            Matcher matcher = commandMatcher(command, addTask);
//            Case.Frequency frequency = checkFrequency(matcher.group(5));
//            if (frequency == Case.Frequency.NONE) {
//                if (matcher.group(4).matches("[DWM]")) {
//                    System.out.println("invalid command!");
//                    return this;
//                }
//            }
//            if (!calendar.getOwner().equals(account.getUsername())) {
//                System.out.println("you don't have access to do this!");
//                return this;
//            }
//            if (!matcher.group(1).matches(word)) {
//                System.out.println("invalid title!");
//                return this;
//            }
//            if (isDateInvalid(matcher.group(4))) {
//                System.out.println("invalid start date!");
//                return this;
//            }
//            Case.Repeat repeat = checkRepeat(matcher.group(6));
//            if (frequency == Case.Frequency.UNTIL_END_DATE) {
//                if (isDateInvalid(matcher.group(5))) {
//                    System.out.println("invalid end date!");
//                    return this;
//                }
//            }
//            if (!isTimeValid(matcher.group(2)) || !isTimeValid(matcher.group(3))) {
//                System.out.println("invalid command!");
//                return this;
//            }
//            if (database.hasTaskTitle(matcher.group(1))) {
//                System.out.println("there is another task with this title!");
//                return this;
//            }
//            boolean hasMeeting = false;
//            if (matcher.group(7).equals("T")) {
//                hasMeeting = true;
//                input.nextLine();
//            }
//            System.out.println("task added successfully!");
//            switch (frequency) {
//                case UNTIL_END_DATE:
//                    database.addTask(new Task(matcher.group(1), LocalTime.parse(matcher.group(2), timeFormat),
//                                    LocalTime.parse(matcher.group(3), timeFormat), LocalDate.parse(matcher.group(4), dateFormat),
//                                    LocalDate.parse(matcher.group(5), dateFormat), frequency, repeat, -1, hasMeeting),
//                            calendar);
//                    break;
//
//                case REPEAT:
//                    database.addTask(new Task(matcher.group(1), LocalTime.parse(matcher.group(2), timeFormat),
//                            LocalTime.parse(matcher.group(3), timeFormat), LocalDate.parse(matcher.group(4), dateFormat),
//                            null, frequency, repeat, Integer.parseInt(matcher.group(5)), hasMeeting), calendar);
//                    break;
//
//                case NONE:
//                    database.addTask(new Task(matcher.group(1), LocalTime.parse(matcher.group(2), timeFormat),
//                            LocalTime.parse(matcher.group(3), timeFormat), LocalDate.parse(matcher.group(4), dateFormat),
//                            null, frequency, null, -1, hasMeeting), calendar);
//                    break;
//            }
//            return this;
//
//        } else if (Pattern.matches(editEvent, command)) {
//            Matcher matcher = commandMatcher(command, editEvent);
//            EditField field = checkField(matcher.group(2));
//            if (field == null) {
//                System.out.println("invalid command!");
//                return this;
//            }
//            if (!calendar.getOwner().equals(account.getUsername())) {
//                System.out.println("you don't have access to do this!");
//                return this;
//            }
//            if (!matcher.group(1).matches(word)) {
//                System.out.println("invalid title!");
//                return this;
//            }
//            if (!database.hasEventTitle(matcher.group(1))) {
//                System.out.println("there is no event with this title!");
//                return this;
//            }
//            switch (field) {
//                case TITLE:
//                    Matcher titleMatcher = Pattern.compile("title\\s+(\\S+)$").matcher(matcher.group(2));
//                    if (titleMatcher.find()) {
//                        if (!titleMatcher.group(1).matches(word)) {
//                            System.out.println("invalid title!");
//                            return this;
//                        }
//                        if (database.hasEventTitle(titleMatcher.group(1))) {
//                            System.out.println("there is another event with this title!");
//                            return this;
//                        }
//                        calendar.changeEventTitle(matcher.group(1), titleMatcher.group(1));
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//
//                case REPEAT:
//                    Matcher repeatMatcher = Pattern.compile("repeat\\s+(\\d+)$").matcher(matcher.group(2));
//                    if (repeatMatcher.find()) {
//                        calendar.changeEventRepeatCount(matcher.group(1), Integer.parseInt(repeatMatcher.group(1)));
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//
//                case KIND_OF_REPEAT:
//                    Matcher repeatTypeMatcher = Pattern.compile("kind\\s+of\\s+repeat\\s+([DWM]|None)$")
//                            .matcher(matcher.group(2));
//                    if (repeatTypeMatcher.find()) {
//                        calendar.changeEventRepeat(matcher.group(1), checkRepeat(repeatTypeMatcher.group(1)));
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//
//                case MEET:
//                    Matcher linkMatcher = Pattern.compile("meet\\s+([TF])$").matcher(matcher.group(2));
//                    if (linkMatcher.find()) {
//                        if (linkMatcher.group(1).equals("T")) {
//                            calendar.changeEventMeeting(matcher.group(1), true);
//                            input.nextLine();
//                            return this;
//                        }
//                        if (linkMatcher.group(1).equals("F")) {
//                            calendar.changeEventMeeting(matcher.group(1), false);
//                            return this;
//                        }
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//            }
//            System.out.println("event edited!");
//            return this;
//
//        } else if (Pattern.matches(editTask, command)) {
//            Matcher matcher = commandMatcher(command, editTask);
//            EditField field = checkField(matcher.group(2));
//            if (field == null) {
//                System.out.println("invalid command!");
//                return this;
//            }
//            if (!calendar.getOwner().equals(account.getUsername())) {
//                System.out.println("you don't have access to do this!");
//                return this;
//            }
//            if (!matcher.group(1).matches(word)) {
//                System.out.println("invalid title!");
//                return this;
//            }
//            if (!database.hasTaskTitle(matcher.group(1))) {
//                System.out.println("there is no task with this title!");
//                return this;
//            }
//            switch (field) {
//                case TITLE:
//                    Matcher titleMatcher = Pattern.compile("title\\s+(\\S+)$").matcher(matcher.group(2));
//                    if (titleMatcher.find()) {
//                        if (!titleMatcher.group(1).matches(word)) {
//                            System.out.println("invalid title!");
//                            return this;
//                        }
//                        if (database.hasTaskTitle(titleMatcher.group(1))) {
//                            System.out.println("there is another task with this title!");
//                            return this;
//                        }
//                        calendar.changeTaskTitle(matcher.group(1), titleMatcher.group(1));
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//
//                case REPEAT:
//                    Matcher repeatMatcher = Pattern.compile("repeat\\s+(\\d+)$").matcher(matcher.group(2));
//                    if (repeatMatcher.find()) {
//                        calendar.changeTaskRepeatCount(matcher.group(1), Integer.parseInt(repeatMatcher.group(1)));
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//
//                case KIND_OF_REPEAT:
//                    Matcher repeatTypeMatcher = Pattern.compile("kind\\s+of\\s+repeat\\s+([DWM]|None)$")
//                            .matcher(matcher.group(2));
//                    if (repeatTypeMatcher.find()) {
//                        calendar.changeTaskRepeat(matcher.group(1), checkRepeat(repeatTypeMatcher.group(1)));
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//
//                case MEET:
//                    Matcher linkMatcher = Pattern.compile("meet\\s+([TF])$").matcher(matcher.group(2));
//                    if (linkMatcher.find()) {
//                        if (linkMatcher.group(1).equals("T")) {
//                            calendar.changeTaskMeeting(matcher.group(1), true);
//                            input.nextLine();
//                            return this;
//                        }
//                        if (linkMatcher.group(1).equals("F")) {
//                            calendar.changeTaskMeeting(matcher.group(1), false);
//                            return this;
//                        }
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//
//                case START_TIME:
//                    Matcher startTimeMatcher = Pattern.compile("start\\s+time\\s+(\\S+)$").matcher(matcher.group(2));
//                    if (startTimeMatcher.find() && isTimeValid(startTimeMatcher.group(1))) {
//                        calendar.changeTaskStartTime(matcher.group(1), LocalTime.parse(startTimeMatcher.group(1), timeFormat));
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//
//                case END_TIME:
//                    Matcher endTimeMatcher = Pattern.compile("end\\s+time\\s+(\\S+)$").matcher(matcher.group(2));
//                    if (endTimeMatcher.find() && isTimeValid(endTimeMatcher.group(1))) {
//                        calendar.changeTaskEndTime(matcher.group(1), LocalTime.parse(endTimeMatcher.group(1), timeFormat));
//                    } else {
//                        System.out.println("invalid command!");
//                        return this;
//                    }
//                    break;
//            }
//            System.out.println("task edited!");
//            return this;
//
//        } else if (Pattern.matches(deleteEvent, command)) {
//            Matcher matcher = commandMatcher(command, deleteEvent);
//            if (!calendar.getOwner().equals(account.getUsername())) {
//                System.out.println("you don't have access to do this!");
//                return this;
//            }
//            if (!matcher.group(1).matches(word)) {
//                System.out.println("invalid title!");
//                return this;
//            }
//            if (!database.hasEventTitle(matcher.group(1))) {
//                System.out.println("there is no event with this title!");
//                return this;
//            }
//            System.out.println("event deleted successfully!");
//            database.removeEvent(matcher.group(1), calendar);
//            return this;
//
//        } else if (Pattern.matches(deleteTask, command)) {
//            Matcher matcher = commandMatcher(command, deleteTask);
//            if (!calendar.getOwner().equals(account.getUsername())) {
//                System.out.println("you don't have access to do this!");
//                return this;
//            }
//            if (!matcher.group(1).matches(word)) {
//                System.out.println("invalid title!");
//                return this;
//            }
//            if (!database.hasTaskTitle(matcher.group(1))) {
//                System.out.println("there is no task with this title!");
//                return this;
//            }
//            System.out.println("task deleted successfully!");
//            database.removeTask(matcher.group(1), calendar);
//            return this;
//
//        } else if (Pattern.matches(showEvents, command)) {
//            System.out.print(calendar.showEvents());
//            return this;
//
//        } else if (Pattern.matches(showTasks, command)) {
//            System.out.print(calendar.showTasks());
//            return this;
//
//        } else if (command.equals("Back")) {
//            return mainMenu;
//        }
//        System.out.println("invalid command!");
//        return this;
//    }
//
//    private Case.Frequency checkFrequency(String frequency) {
//        if (frequency.matches("\\d{4}_\\d{2}_\\d{2}")) {
//            return Case.Frequency.UNTIL_END_DATE;
//        } else if (frequency.matches("\\d+")) {
//            return Case.Frequency.REPEAT;
//        } else {
//            return Case.Frequency.NONE;
//        }
//    }
//
//    private Case.Repeat checkRepeat(String repeat) {
//        switch (repeat) {
//            case "D":
//                return Case.Repeat.DAILY;
//            case "W":
//                return Case.Repeat.WEEKLY;
//            case "M":
//                return Case.Repeat.MONTHLY;
//            default:
//                return null;
//        }
//    }
//
//    private EditField checkField(String field) {
//        if (field.matches("^title.+")) {
//            return EditField.TITLE;
//        } else if (field.matches("^kind\\s+of\\s+repeat.+")) {
//            return EditField.KIND_OF_REPEAT;
//        } else if (field.matches("^repeat.+")) {
//            return EditField.REPEAT;
//        } else if (field.matches("^start\\s+time.+")) {
//            return EditField.START_TIME;
//        } else if (field.matches("^end\\s+time.+")) {
//            return EditField.END_TIME;
//        } else if (field.matches("^meet.+")) {
//            return EditField.MEET;
//        } else {
//            return null;
//        }
//    }
//}