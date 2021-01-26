package controller.command;

public class CommandParser {

    public static void parse(String commandLine) throws UnknownCommandException {
        String[] words = commandLine.split(" ");
        switch (words[0]) {
            case "createCustomer":
                Command.create_customer.execute(words);
                break;
            case "createEmployee":
                Command.create_customer.execute(words);
                break;
        }

        throw new UnknownCommandException("Command " + words[0] + " doesn't exist");
    }
}
