package edu.kit.informatik.codefight.command.usercommands;


import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;


/**
 * This command removes an AI with the specified name.
 * It is not permitted to remove a non-existent AI. The successful removal of an AI is confirmed by outputting the
 * name of the AI.
 *
 * @author uexnb
 * @version 1.0
 */
public final class RemoveAiCommand implements UserCommand {
/**
     * The name of the command.
     * Public because it is used in {@link CommandHandler}.
     */
    public  static final String COMMAND_NAME = "remove-ai";
    private static final int NUMBER_OF_ARGUMENTS = 1;
    private static final GamePhase CORRECT_PHASE = GamePhase.SETUP;
    private static final String COMMAND_DESCRIPTION = "Removes the AI with the specified name.";
    private static final String INVALID_AI_NAME_MESSAGE = "AI with this name is not registered and therefore cannot be removed.";
    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        //check if the AI with the specified name is registered
        if (!model.getRegisteredAis().contains(new ArtificialIntelligence(commandArguments[0]))) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_AI_NAME_MESSAGE);
        }
        //remove the AI with the specified name
        model.removeAi(commandArguments[0]);
        return new CommandResult(CommandResultType.SUCCESS, commandArguments[0]);

    }

    @Override
    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
    }
    @Override
    public GamePhase getCorrectPhase() {
        return CORRECT_PHASE;
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
