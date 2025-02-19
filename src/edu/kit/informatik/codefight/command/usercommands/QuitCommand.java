package edu.kit.informatik.codefight.command.usercommands;


import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;

/**
 * This command quits a {@link CommandHandler command handler}.
 *
 * @author uexnb
 * @author Programmieren-Team
 * @version 1.0
 */
public final class QuitCommand implements UserCommand {
    /**
     * The name of the command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "quit";
    private static final String COMMAND_DESCRIPTION = "Quits the game.";
    private final CommandHandler commandHandler;
    /**
     * Constructs a new QuitCommand.
     *
     * @param commandHandler the command handler to be quitted
     */
    public QuitCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }
    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        commandHandler.quit();
        return new CommandResult(CommandResultType.SUCCESS, null);
    }
    @Override
    public int getNumberOfArguments() {
        return 0;
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
