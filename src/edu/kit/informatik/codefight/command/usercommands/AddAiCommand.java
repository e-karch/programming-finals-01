package edu.kit.informatik.codefight.command.usercommands;

import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.Memory;
import edu.kit.informatik.codefight.model.GamePhase;


import java.util.Arrays;

import static edu.kit.informatik.codefight.Main.NUMBER_NOT_INT_FAILURE;


/**
 * This command registers an AI with the specified name and
 * the specified AI commands.
 *
 * @author uexnb
 * @version 1.0
 */
public final class AddAiCommand implements UserCommand {
    /**
     * The name of the add-ai command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "add-ai";
    /**
     * The error message if the AI commands would overlap in the memory.
     * Public because it is used in {@link CodeFight}.
     */
    public static final String AI_COMMANDS_OVERLAP_MESSAGE = "The AI commands overlap";
    /**
     * The number of arguments that make up one AI command.
     * Public because it is used in {@link ArtificialIntelligence}.
     */
    public static final int AI_COMMAND_LENGTH = 3;
    private static final int NUMBER_OF_ARGUMENTS = 2;
    private static final String AI_COMMANDS_SEPARATOR = ",";
    private static final String INVALID_AI_NAME_MESSAGE = "AI with this name is already registered.";
    private static final String COMMAND_NAME_NOT_FOUND_MESSAGE = "There is at least one name that does not correspond to a registered "
            + "AI command.";
    private static final String INVALID_AI_COMMANDS_MESSAGE = "The AI commands do not match the required format.";
    private static final GamePhase CORRECT_PHASE = GamePhase.SETUP;
    private static final String COMMAND_DESCRIPTION = "Registers an AI with the specified name and the specified AI commands.";



    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        //check if the AI name is already used
        if (model.getRegisteredAis().contains(new ArtificialIntelligence(commandArguments[0]))) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_AI_NAME_MESSAGE);
        }
        //split the AI commands
        String[] splittedAiCommands = commandArguments[1].trim().split(AI_COMMANDS_SEPARATOR);
        //check if the AI commands match the required format (name, number, number)
        if (splittedAiCommands.length % AI_COMMAND_LENGTH != 0) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_AI_COMMANDS_MESSAGE);
        }
        //check if there are too many commands for the memory so that you cannot play with this AI without an overlap
        if (splittedAiCommands.length / AI_COMMAND_LENGTH - 1 > (int) Math.floor(((double) model.getMemory().getSize()) / 2)) {
            return new CommandResult(CommandResultType.FAILURE, AI_COMMANDS_OVERLAP_MESSAGE);
        }
        //check if the AI commands are integers
        for (int i = 0; i < splittedAiCommands.length; i += AI_COMMAND_LENGTH) {
            try {
                Integer.parseInt(splittedAiCommands[i + 1]);
                Integer.parseInt(splittedAiCommands[i + 2]);
            } catch (NumberFormatException e) {
                return new CommandResult(CommandResultType.FAILURE, NUMBER_NOT_INT_FAILURE);
            }
        }
        //check if the command names correspond to the valid names of the AI commands
        for (int i = 0; i < splittedAiCommands.length; i += AI_COMMAND_LENGTH) {
            if (!Arrays.asList(Memory.getOrderedAiCommandNames()).contains(splittedAiCommands[i])) {
                return new CommandResult(CommandResultType.FAILURE, COMMAND_NAME_NOT_FOUND_MESSAGE);
            }
        }
        //register the AI with the specified name and AI commands
        ArtificialIntelligence newAi = new ArtificialIntelligence(commandArguments[0]);
        newAi.setAiCommands(splittedAiCommands);
        model.registerAi(newAi);
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
