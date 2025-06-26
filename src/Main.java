import command.*;
import enums.COMMAND;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 *
 */
public class Main {
    private static final Map<String, Command> commands = new HashMap<>();
    public static void main(String[] args) {
        initializeCommands();

        if (args.length > 0) {
            executeCommand(args);
        } else {
            runInteractiveShell();
        }
    }

    private static void initializeCommands() {
        commands.put(COMMAND.CASH_IN.name(), new CashInCommand());
        commands.put(COMMAND.LIST_BILL.name(), new ListBillCommand());
        commands.put(COMMAND.PAY.name(), new PayCommand());
        commands.put(COMMAND.DUE_DATE.name(), new DueDateCommand());
        commands.put(COMMAND.SCHEDULE.name(), new ScheduleCommand());
        commands.put(COMMAND.LIST_PAYMENT.name(), new ListPaymentCommand());
        commands.put(COMMAND.SEARCH_BILL_BY_PROVIDER.name(), new SearchBillByProviderCommand());
        commands.put(COMMAND.EXIT.name(), new ExitCommand());
    }

    private static void executeCommand(String[] args) {
        if (args.length == 0 || args[0].isEmpty()) {
            System.out.println("Please provide a valid command. Type 'HELP' for available commands.");
            return;
        }
        String commandName = args[0].toUpperCase();
        Command command = commands.get(commandName);
        if (command != null) {
            command.execute(Arrays.copyOfRange(args, 1, args.length));
        } else if (commandName.equals("HELP")) {
            printHelp();
        } else {
            System.out.println("Unknown command: " + commandName + ". Type 'HELP' for available commands.");
        }
    }

    private static void runInteractiveShell() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.print("> ");
            String[] input = scanner.nextLine().trim().split("\\s+");
            if (input.length == 0 || input[0].isEmpty()) {
                System.out.println("Please provide a valid command. Type 'HELP' for available commands.");
                continue;
            }
            executeCommand(input);
        }
    }

    private static void printHelp() {
        System.out.println("Available commands:");
        System.out.println("  CASH_IN <amount> - Add funds to your balance");
        System.out.println("  LIST_BILL - List all bills");
        System.out.println("  PAY <bill_id> - Pay a specific bill");
        System.out.println("  DUE_DATE - List bills with upcoming due dates");
        System.out.println("  SCHEDULE <bill_id> <dd/MM/yyyy> - Schedule a payment");
        System.out.println("  LIST_PAYMENT - List payment history");
        System.out.println("  SEARCH_BILL_BY_PROVIDER <provider> - Search bills by provider");
        System.out.println("  EXIT - Exit the program");
    }
}