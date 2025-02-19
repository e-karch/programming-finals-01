package edu.kit.informatik.codefight.command.usercommands;


import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;
import edu.kit.informatik.codefight.model.Memory;

import java.util.Arrays;
import java.util.Objects;


import static edu.kit.informatik.codefight.Main.NUMBER_NOT_INT_FAILURE;
import static edu.kit.informatik.codefight.command.CommandHandler.WRONG_ARGUMENTS_COUNT_FORMAT;


/**
 * This command sets the mode with which the memory is initialised.
 *
 * @author uexnb
 * @version 1.0
 */
public final class SetInitModeCommand implements UserCommand {
    /**
     * The name of the command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "set-init-mode";
    private static final int MIN_SEED = -1337;
    private static final int MAX_SEED = 1337;
    private static final GamePhase CORRECT_PHASE = GamePhase.SETUP;
    private static final String SEPARATOR = " ";
    private static final String COMMAND_DESCRIPTION = "Sets the mode with which the memory is initialised.";
    private static final String INVALID_SEED_MESSAGE = "The specified seed is invalid.";
    private static final String INVALID_INIT_MODE_MESSAGE = "The specified initialisation mode is invalid.";
    private static final String WRONG_ARGUMENTS_FOR_INIT_MODE_FORMAT = "Wrong number of arguments for initialisation mode '%s'!";
    private static final String INIT_MODE_SET_FORMAT = "Changed init mode from %s to %s";
    private final CommandHandler commandHandler;
    /**
     * Constructs a new SetInitModeCommand.
     *
     * @param commandHandler the command handler to be used to set the initialisation mode
     */
    public SetInitModeCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        String initMode;
        //check if the number of arguments is correct
        if (commandArguments.length > 0 && commandArguments.length <= 2) {
            //check if the name of the initialisation mode corresponds to one of the available ones
            if (!Arrays.asList(Memory.getInitModes()).contains(commandArguments[0])) {
                return new CommandResult(CommandResultType.FAILURE, INVALID_INIT_MODE_MESSAGE);
            }
            //check for the selected initialisation mode if the number of arguments is correct
            if (isNumberOfArgsForInitModeCorrect(commandArguments)) {
                return new CommandResult(CommandResultType.FAILURE, String.format(WRONG_ARGUMENTS_FOR_INIT_MODE_FORMAT,
                        commandArguments[0]));
            }
            initMode = commandArguments[0];
        } else {
            return new CommandResult(CommandResultType.FAILURE, WRONG_ARGUMENTS_COUNT_FORMAT.formatted(COMMAND_NAME));
        }
        //extract the seed from the command arguments if there is one
        int seed = 0;
        if (commandArguments.length == 2) {
            try {
                seed = Integer.parseInt(commandArguments[1]);
            } catch (NumberFormatException exception) {
                return new CommandResult(CommandResultType.FAILURE, NUMBER_NOT_INT_FAILURE);
            }
        }
        //check if the seed is in the valid range
        if (seed < MIN_SEED || seed > MAX_SEED) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_SEED_MESSAGE);
        }
        //set the initialisation mode of the memory
        String oldInitMode = model.getMemory().getInitMode();
        int oldSeed;
        if (model.getMemory().getSeed() != null) {
            oldSeed = Objects.requireNonNull(model.getMemory().getSeed());
        } else {
            oldSeed = 0;
        }
        boolean isDifferent = setInitMode(model, initMode, seed, oldInitMode, oldSeed);
        return new CommandResult(CommandResultType.SUCCESS,
                getCorrectSuccessMessage(isDifferent, initMode, seed, oldInitMode, oldSeed));
    }

    private static boolean isNumberOfArgsForInitModeCorrect(String[] commandArguments) {
        return commandArguments[0].equals(Memory.getInitModes()[0]) && commandArguments.length != 2
                || commandArguments[0].equals(Memory.getInitModes()[1]) && commandArguments.length != 1;
    }

    @Override
    public int getNumberOfArguments() {
        //returns the number of arguments of the current user input
        //as the arguments could be 1 or 2, both are excepted as valid at the moment because there is a check in the execute method
        return commandHandler.getCurrentNumberOfArguments();
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
    private boolean setInitMode(CodeFight model, String initMode, int seed, String oldInitMode, int oldSeed) {
        //check if the initialisation mode is different from the current one
        boolean isDifferent = false;
        if (!oldInitMode.equals(initMode)
                || oldSeed != seed) {
            isDifferent = true;
            //set the initialisation mode of the memory
            model.getMemory().setInitMode(initMode);
            model.getMemory().setSeed(seed);
        }
        return isDifferent;
    }
    private String getCorrectSuccessMessage(boolean isDifferent, String initMode, int seed, String oldInitMode, int oldSeed) {
        if (isDifferent) {
            //return the correct success message depending on the old and new initialisation mode and the seed
            if (oldInitMode.equals(Memory.getInitModes()[0]) && initMode.equals(Memory.getInitModes()[0])) {
                return String.format(INIT_MODE_SET_FORMAT, oldInitMode + SEPARATOR + oldSeed, initMode + SEPARATOR + seed);
            } else if (oldInitMode.equals(Memory.getInitModes()[0]) && initMode.equals(Memory.getInitModes()[1])) {
                return String.format(INIT_MODE_SET_FORMAT, oldInitMode + SEPARATOR + oldSeed, initMode);
            } else if (oldInitMode.equals(Memory.getInitModes()[1]) && initMode.equals(Memory.getInitModes()[0])) {
                return String.format(INIT_MODE_SET_FORMAT, oldInitMode, initMode + SEPARATOR + seed);
            } else {
                return String.format(INIT_MODE_SET_FORMAT, oldInitMode, initMode);
            }
        }
        return null;
    }

}
