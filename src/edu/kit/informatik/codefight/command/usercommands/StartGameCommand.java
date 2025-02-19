package edu.kit.informatik.codefight.command.usercommands;


import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.exceptions.GameInitialisationException;
import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;

import static edu.kit.informatik.codefight.command.CommandHandler.WRONG_ARGUMENTS_COUNT_FORMAT;


/**
 * This command starts the game. The game can only be started if at least two AIs are selected.
 * are selected. It is permitted to select the same AI more than once.
 *
 * @author uexnb
 * @version 1.0
 */
public final class StartGameCommand implements UserCommand {
    /**
     * The name of the command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "start-game";
    private static final GamePhase CORRECT_PHASE = GamePhase.SETUP;
    private static final String COMMAND_DESCRIPTION = "Starts the game. The game can only be started if at least two AIs are selected.";
    private static final String INVALID_AI_NAME_MESSAGE = "There is at least one name that does not correspond to a registered AI.";
    private static final String START_GAME_SUCCESS_MESSAGE = "Game started.";
    private final CommandHandler commandHandler;
    /**
     * Constructs a new StartGameCommand.
     *
     * @param commandHandler the command handler to be used to start the game
     */
    public StartGameCommand(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }
    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        //check if the number of selected AIs is valid
        if (commandArguments.length < 2 || commandArguments.length > model.getMaxAis()) {
            return new CommandResult(CommandResultType.FAILURE,
                    WRONG_ARGUMENTS_COUNT_FORMAT.formatted(COMMAND_NAME));
        }
        //check if the selected AIs are registered
        if (!areAisRegistered(model, commandArguments)) {
            return new CommandResult(CommandResultType.FAILURE, INVALID_AI_NAME_MESSAGE);
        }
        //initialise the list of AIs that will play the game
        model.setActiveAis(commandArguments);
        //start the game
        try {
            model.startGame();
        } catch (GameInitialisationException e) {
            return new CommandResult(CommandResultType.FAILURE, e.getMessage());
        }
        //change the phase of the game
        model.setPhase(GamePhase.FIGHT);
        return new CommandResult(CommandResultType.SUCCESS, START_GAME_SUCCESS_MESSAGE);
    }

    @Override
    public int getNumberOfArguments() {
        //just returns the number of arguments of the current user input because the
        //number of selected AIs is not fixed. There is a check in the execute method
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
    private boolean areAisRegistered(CodeFight model, String[] commandArguments) {
        boolean areRegistered = true;
        for (String commandArgument : commandArguments) {
            if (!model.getRegisteredAis().contains(new ArtificialIntelligence(commandArgument))) {
                areRegistered = false;
                break;
            }
        }
        return areRegistered;
    }
}
