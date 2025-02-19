package edu.kit.informatik.codefight.command.usercommands;


import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.exceptions.GameExecutionException;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;

import static edu.kit.informatik.codefight.Main.NUMBER_NOT_INT_FAILURE;
import static edu.kit.informatik.codefight.command.CommandHandler.WRONG_ARGUMENTS_COUNT_FORMAT;


/**
 * This command executes the next AI commands of the AIs.
 *
 * @author uexnb
 * @version 1.0
 */
public final class NextCommand implements UserCommand {
    /**
     * The name of the command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "next";
    private static final GamePhase CORRECT_PHASE = GamePhase.FIGHT;
    private static final String COMMAND_DESCRIPTION = "Executes the next commands of the AIS. The number of commands is specified by the "
            + "given value.";
    private final CommandHandler commandHandler;
    /**
     * Constructs a new NextCommand.
     *
     * @param commandHandler the command handler to be used to execute the next command
     */
    public NextCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        //check if the number of arguments is correct
        if (commandHandler.getCurrentNumberOfArguments() > 1) {
            return new CommandResult(CommandResultType.FAILURE,
                    WRONG_ARGUMENTS_COUNT_FORMAT.formatted(COMMAND_NAME));
        }
        //check if the number is an integer
        int numberOfCommands = 1;
        if (commandHandler.getCurrentNumberOfArguments() == 1) {
            try {
                numberOfCommands = Integer.parseInt(commandArguments[0]);
            } catch (NumberFormatException e) {
                return new CommandResult(CommandResultType.FAILURE, NUMBER_NOT_INT_FAILURE);
            }
        }
        //execute the next commands
        String resultMessage;
        try {
            resultMessage = model.executeNextCommands(numberOfCommands);
        } catch (GameExecutionException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
        return new CommandResult(CommandResultType.SUCCESS, resultMessage);
    }

    @Override
    public int getNumberOfArguments() {
        //just returns the current input arguments as the number of arguments is either 0 or 1
        //there is a check for the correct number of arguments in the execute method
        return commandHandler.getCurrentNumberOfArguments();
    }

    @Override
    public GamePhase getCorrectPhase() {
        return CORRECT_PHASE;
    }

    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
}
