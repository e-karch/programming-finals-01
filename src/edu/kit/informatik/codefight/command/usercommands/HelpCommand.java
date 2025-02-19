package edu.kit.informatik.codefight.command.usercommands;


import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * This command displays a short line-by-line description of the commands available in the current
 * phase of the game.
 *
 * @author uexnb
 * @version 1.0
 */
public final class HelpCommand implements UserCommand {
    /**
     * The name of the command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "help";
    private static final int NUMBER_OF_ARGUMENTS = 0;
    private static final String COMMAND_DESCRIPTION_SEPARATOR = System.lineSeparator();
    private static final String COMMAND_DESCRIPTION_FORMAT = "%s: %s";
    private static final String COMMAND_DESCRIPTION = "Displays a short line-by-line description of the commands available "
            + "in the current phase of the game.";
    private final CommandHandler commandHandler;
    /**
     * Constructs a new HelpCommand.
     *
     * @param commandHandler the command handler to be used to execute the help command
     */
    public HelpCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }
    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        //convert map for commands to list
        List<UserCommand> commands = new ArrayList<>(commandHandler.getCommands().values());
        //sort the commands lexicographically by overriding the compare method of the comparator
        commands.sort(new Comparator<>() {
            @Override
            public int compare(final UserCommand command1, final UserCommand command2) {
                return command1.getCommandName().compareTo(command2.getCommandName());
            }
        });

        String resultMessage = getResultMessage(model, commands);
        return new CommandResult(CommandResultType.SUCCESS, resultMessage);

    }

    private String getResultMessage(CodeFight model, List<UserCommand> commands) {
        StringBuilder resultMessage = new StringBuilder();
        for (UserCommand command : commands) {
            //check if the command can be executed in the current phase
            if (command.getCorrectPhase() == model.getPhase()) {
                //construct the result message
                if (!resultMessage.isEmpty()) {
                    resultMessage.append(COMMAND_DESCRIPTION_SEPARATOR);
                }
                resultMessage.append(COMMAND_DESCRIPTION_FORMAT.formatted(command.getCommandName(),
                        command.getCommandDescription()));
            }
        }
        return resultMessage.toString();
    }

    @Override
    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
    @Override
    public GamePhase getCorrectPhase() {
        //just returns current phase because this command can be executed in any phase
        return commandHandler.getCurrentPhase();
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
