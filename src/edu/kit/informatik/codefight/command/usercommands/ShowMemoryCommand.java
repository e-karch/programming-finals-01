package edu.kit.informatik.codefight.command.usercommands;

import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;

import static edu.kit.informatik.codefight.Main.NUMBER_NOT_INT_FAILURE;
import static edu.kit.informatik.codefight.command.CommandHandler.WRONG_ARGUMENTS_COUNT_FORMAT;

/**
 * This command shows the current state of the memory.
 *
 * @author uexnb
 * @version 1.0
 */
public final class ShowMemoryCommand implements UserCommand {
    /**
     * The name of the command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "show-memory";
    private static final String INVALID_MEMORY_CELL_INDEX_MESSAGE = "The given memory cell index either exceeds the memory size"
            + " or is negative.";
    private static final GamePhase CORRECT_PHASE = GamePhase.FIGHT;
    private static final String COMMAND_DESCRIPTION = "Shows the current state of the memory.";

    private final CommandHandler commandHandler;
    /**
     * Constructs a new ShowMemoryCommand.
     *
     * @param commandHandler the command handler the command is executed on
     */
    public ShowMemoryCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }
    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        //check if there are too many arguments
        if (commandArguments.length > 1) {
            return new CommandResult(CommandResultType.FAILURE, WRONG_ARGUMENTS_COUNT_FORMAT.formatted(COMMAND_NAME));
        }
        //if there is one argument, check if it is a valid memory cell index
        if (commandArguments.length == 1) {
            try {
                int index = Integer.parseInt(commandArguments[0]);
                if (index < 0 || index >= model.getMemory().getSize()) {
                    return new CommandResult(CommandResultType.FAILURE, INVALID_MEMORY_CELL_INDEX_MESSAGE);
                }
            } catch (NumberFormatException e) {
                return new CommandResult(CommandResultType.FAILURE, NUMBER_NOT_INT_FAILURE);
            }
        }
        //get the display of the memory as a string
        String memoryDisplay;
        if (commandArguments.length == 1) {
            memoryDisplay = model.displayMemory(Integer.parseInt(commandArguments[0]));
        } else {
            memoryDisplay = model.displayMemory(null);
        }
        return new CommandResult(CommandResultType.SUCCESS, memoryDisplay);
    }

    @Override
    public GamePhase getCorrectPhase() {
        return CORRECT_PHASE;
    }

    @Override
    public int getNumberOfArguments() {
        //just returns the current number of arguments inputted by the user as either 0 or 1 could be correct
        //there is a check in the execute method
        return commandHandler.getCurrentNumberOfArguments();
    }
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }
    @Override
    public String getCommandDescription() {
        return COMMAND_DESCRIPTION;
    }

}
