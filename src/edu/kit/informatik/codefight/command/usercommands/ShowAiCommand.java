package edu.kit.informatik.codefight.command.usercommands;

import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;

import java.util.Objects;

/**
 * This command displays the current status of an AI.
 *
 * @author uexnb
 * @version 1.0
 */
public final class ShowAiCommand implements UserCommand {
    /**
     * The name of the command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "show-ai";
    private static final int NUMBER_OF_ARGUMENTS = 1;
    private static final GamePhase CORRECT_PHASE = GamePhase.FIGHT;
    private static final String COMMAND_DESCRIPTION = "Displays the current status of an AI.";
    private static final String INVALID_AI_NAME_MESSAGE = "AI with this name is not active and therefore the status cannot be displayed.";
    private static final String AI_STOPPED_FORMAT = "%s (STOPPED@%d)";
    private static final String AI_RUNNING_FORMAT = "%s (RUNNING@%d)" + System.lineSeparator() + "Next Command: %s @%d";
    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        //check if the AI is active
        if (model.getActiveAi(commandArguments[0]) == null) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_AI_NAME_MESSAGE);
        }
        //get a copy of the AI with the given name
        ArtificialIntelligence aiToShow = Objects.requireNonNull(model.getActiveAi(commandArguments[0]));
        StringBuilder resultMessage = new StringBuilder();
        if (!aiToShow.getIsAlive()) {
            resultMessage.append(AI_STOPPED_FORMAT.formatted(aiToShow.getName(), aiToShow.getNumberOfExecutedCommands()));
        } else {
            resultMessage.append(AI_RUNNING_FORMAT.formatted(aiToShow.getName(), aiToShow.getNumberOfExecutedCommands(),
                    model.getMemory().getMemoryCell(aiToShow.getInstructionPointer()).toString(),
                    aiToShow.getInstructionPointer() % model.getMemory().getSize())); //get the current command, modulo because
                                                                                      // memory is circular
        }
        return new CommandResult(CommandResultType.SUCCESS, resultMessage.toString());
    }

    @Override
    public GamePhase getCorrectPhase() {
        return CORRECT_PHASE;
    }

    @Override
    public int getNumberOfArguments() {
        return NUMBER_OF_ARGUMENTS;
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
