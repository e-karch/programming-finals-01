package edu.kit.informatik.codefight;

import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.GameInitialiser;
import edu.kit.informatik.codefight.exceptions.GameInitialisationException;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.command.usercommands.SetInitModeCommand;

import static edu.kit.informatik.codefight.command.CommandHandler.ERROR_PREFIX;
import static java.util.Objects.requireNonNull;

/**
 * This class is the entry point of the program.
 *
 * @author uexnb
 * @author Programmieren-Team
 * @version 1.0
 */
public final class Main {
    /**
     * The error message for when the given number is not an integer.
     * Public because it is used in {@link SetInitModeCommand}
     */
    public static final String NUMBER_NOT_INT_FAILURE = "The specified number must be an integer.";
    private static final String UTILITY_CLASS_FAILURE = "Utility classes cannot be instantiated";
    private Main() {
        throw new UnsupportedOperationException(UTILITY_CLASS_FAILURE);
    }

    /**
     * Starts the program.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Initialize the game with the command line arguments
        try {
            Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.err.println(ERROR_PREFIX + NUMBER_NOT_INT_FAILURE);
            return;
        }
        CodeFight codeFight;
        try {
            codeFight = new GameInitialiser(args).getGame();
        } catch (GameInitialisationException e) {
            System.err.println(ERROR_PREFIX + e.getMessage());
            return;
        }
        //Start interaction with the user
        CommandHandler commandHandler = new CommandHandler(requireNonNull(codeFight));
        commandHandler.handleUserInput();
    }
}
