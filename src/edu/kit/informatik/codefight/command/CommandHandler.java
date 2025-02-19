package edu.kit.informatik.codefight.command;

import edu.kit.informatik.codefight.command.usercommands.AddAiCommand;
import edu.kit.informatik.codefight.command.usercommands.EndGameCommand;
import edu.kit.informatik.codefight.command.usercommands.HelpCommand;
import edu.kit.informatik.codefight.command.usercommands.NextCommand;
import edu.kit.informatik.codefight.command.usercommands.QuitCommand;
import edu.kit.informatik.codefight.command.usercommands.RemoveAiCommand;
import edu.kit.informatik.codefight.command.usercommands.SetInitModeCommand;
import edu.kit.informatik.codefight.command.usercommands.ShowAiCommand;
import edu.kit.informatik.codefight.command.usercommands.ShowMemoryCommand;
import edu.kit.informatik.codefight.command.usercommands.StartGameCommand;
import edu.kit.informatik.codefight.command.usercommands.UserCommand;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;
import edu.kit.informatik.codefight.model.round.RoundHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * This class handles the user input and executes the commands.
 *
 * @author uexnb
 * @author Programmieren-Team
 * @version 1.0
 */
public class CommandHandler {

    /**
     * The prefix for error messages.
     * Public because it is used in {@link GameInitialiser} and {@link CodeFight}.
     */
    public static final String ERROR_PREFIX = "Error, ";
    /**
     * The format for the error message when a command is not found.
     * Public because it is used in {@link RoundHandler}.
     */
    public static final String COMMAND_NOT_FOUND_FORMAT = "Command '%s' not found";
    /**
     * The format for the error message when the number of arguments is wrong.
     * Public because it is used in {@link StartGameCommand}, {@link NextCommand} and {@link ShowMemoryCommand}.
     */
    public static final String WRONG_ARGUMENTS_COUNT_FORMAT = "wrong number of arguments for command '%s'!";
    private static final String WRONG_PHASE_FORMAT = "The game is not in the %s phase";
    private static final String INVALID_RESULT_TYPE_FORMAT = "Unexpected value: %s";
    private static final String COMMAND_SEPARATOR_REGEX = " +";
    private final CodeFight codeFight;
    private final Map<String, UserCommand> commands;
    private boolean running = false;
    private int currentNumberOfArguments;
    private GamePhase currentPhase;
    /**
     * Constructs a new CommandHandler.
     *
     * @param codeFight the code fight game that this instance should handle
     */
    public CommandHandler(CodeFight codeFight) {
        this.codeFight = Objects.requireNonNull(codeFight);
        this.commands = new HashMap<>();
        this.initCommands();
    }
    /**
     * Starts the interaction with the user.
     */
    public void handleUserInput() {
        this.running = true;
        try (Scanner scanner = new Scanner(System.in)) {
            while (running && scanner.hasNextLine()) {
                executeCommand(scanner.nextLine());
            }
        }
    }
    /**
     * Quits the interaction with the user.
     */
    public void quit() {
        this.running = false;
    }
    private void executeCommand(String commandWithArguments) {
        String[] splittedCommand = commandWithArguments.trim().split(COMMAND_SEPARATOR_REGEX);
        String commandName = splittedCommand[0];
        String[] commandArguments = Arrays.copyOfRange(splittedCommand, 1, splittedCommand.length);
        setCurrentNumberOfArguments(commandArguments.length);
        //set current phase
        setCurrentPhase(codeFight.getPhase());
        executeCommand(commandName, commandArguments);
    }
    private void executeCommand(String commandName, String[] commandArguments) {
        if (!commands.containsKey(commandName)) {
            System.err.println(ERROR_PREFIX + COMMAND_NOT_FOUND_FORMAT.formatted(commandName));
        } else if (commands.get(commandName).getNumberOfArguments() != commandArguments.length) {
            System.err.println(ERROR_PREFIX + WRONG_ARGUMENTS_COUNT_FORMAT.formatted(commandName));
        } else if (commands.get(commandName).getCorrectPhase() != getCurrentPhase()) { //check if the game is in the right phase
            System.err.println(ERROR_PREFIX + String.format(WRONG_PHASE_FORMAT, commands.get(commandName).getCorrectPhase()));
        } else {
            CommandResult result = commands.get(commandName).execute(codeFight, commandArguments);
            String output = switch (result.getType()) {
                case SUCCESS -> result.getMessage();
                case FAILURE -> ERROR_PREFIX + result.getMessage();
            };
            if (output != null) {
                switch (result.getType()) {
                    case SUCCESS -> System.out.println(output);
                    case FAILURE -> System.err.println(output);
                    default -> throw new IllegalStateException(INVALID_RESULT_TYPE_FORMAT.formatted(result.getType()));
                }
            }
        }
    }
    private void initCommands() {
        this.addCommand(QuitCommand.COMMAND_NAME, new QuitCommand(this));
        this.addCommand(HelpCommand.COMMAND_NAME, new HelpCommand(this));
        this.addCommand(AddAiCommand.COMMAND_NAME, new AddAiCommand());
        this.addCommand(RemoveAiCommand.COMMAND_NAME, new RemoveAiCommand());
        this.addCommand(SetInitModeCommand.COMMAND_NAME, new SetInitModeCommand(this));
        this.addCommand(StartGameCommand.COMMAND_NAME, new StartGameCommand(this));
        this.addCommand(NextCommand.COMMAND_NAME, new NextCommand(this));
        this.addCommand(ShowMemoryCommand.COMMAND_NAME, new ShowMemoryCommand(this));
        this.addCommand(ShowAiCommand.COMMAND_NAME, new ShowAiCommand());
        this.addCommand(EndGameCommand.COMMAND_NAME, new EndGameCommand());

    }
    private void addCommand(String commandName, UserCommand userCommand) {
        this.commands.put(commandName, userCommand);
    }
    /**
     * Returns the number of arguments of the current user input.
     *
     * @return the current number of arguments
     */
    public int getCurrentNumberOfArguments() {
        return currentNumberOfArguments;
    }
    /**
     * Sets the number of arguments of the current user input.
     *
     * @param currentNumberOfArguments the new number of arguments
     */
    private void setCurrentNumberOfArguments(int currentNumberOfArguments) {
        this.currentNumberOfArguments = currentNumberOfArguments;
    }
    /**
     * Returns the current phase of the game.
     *
     * @return the current phase of the game
     */
    public GamePhase getCurrentPhase() {
        return currentPhase;
    }
    /**
     * Sets the current phase of the game.
     *
     * @param currentPhase the new phase of the game
     */
    private void setCurrentPhase(GamePhase currentPhase) {
        this.currentPhase = currentPhase;
    }
    /**
     * Returns the commands of this command handler.
     *
     * @return the commands of this command handler
     */
    public Map<String, UserCommand> getCommands() {
        return Collections.unmodifiableMap(commands);
    }

}
