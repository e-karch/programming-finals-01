package edu.kit.informatik.codefight.command.usercommands;

import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.command.CommandHandler;
import edu.kit.informatik.codefight.command.CommandResultType;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;

/**
 * This command ends the game. At the end of the game, the lists of running and stopped
 * AIs are displayed.
 *
 * @author uexnb
 * @version 1.0
 */
public final class EndGameCommand implements UserCommand {
    /**
     * The name of the end game command.
     * Public because it is used in {@link CommandHandler}.
     */
    public static final String COMMAND_NAME = "end-game";
    private static final String COMMAND_DESCRIPTION = "Ends the game.";
    private static final int NUMBER_OF_ARGUMENTS = 0;
    private static final GamePhase CORRECT_PHASE = GamePhase.FIGHT;
    private static final String AI_SEPARATOR = ", ";
    private static final String RUNNING_AIS_FORMAT = "Running AIs: %s";
    private static final String STOPPED_AIS_FORMAT = "Stopped AIs: %s";
    @Override
    public CommandResult execute(CodeFight model, String[] commandArguments) {
        StringBuilder resultMessage = new StringBuilder();
        if (getRunningAis(model) != null && getStoppedAis(model) != null) {
            resultMessage.append(String.format(RUNNING_AIS_FORMAT, getRunningAis(model)));
            resultMessage.append(System.lineSeparator());
            resultMessage.append(String.format(STOPPED_AIS_FORMAT, getStoppedAis(model)));
        } else if (getRunningAis(model) != null) {
            resultMessage.append(String.format(RUNNING_AIS_FORMAT, getRunningAis(model)));
        } else {
            resultMessage.append(String.format(STOPPED_AIS_FORMAT, getStoppedAis(model)));
        }
        //reset the state of the game
        model.reset();
        return new CommandResult(CommandResultType.SUCCESS, resultMessage.toString());
    }

    private String getRunningAis(CodeFight model) {
        StringBuilder runningAis = new StringBuilder();
        for (int i = 0; i < model.getActiveAis().size(); i++) {
            if (model.getActiveAis().get(i).getIsAlive()) {
                if (!runningAis.isEmpty()) {
                    runningAis.append(AI_SEPARATOR);
                }
                runningAis.append(model.getActiveAis().get(i).getName());
            }
        }
        if (runningAis.isEmpty()) {
            return null;
        }
        return runningAis.toString();
    }
    private String getStoppedAis(CodeFight model) {
        StringBuilder stoppedAis = new StringBuilder();
        for (int i = 0; i < model.getActiveAis().size(); i++) {
            if (!model.getActiveAis().get(i).getIsAlive()) {
                if (!stoppedAis.isEmpty()) {
                    stoppedAis.append(AI_SEPARATOR);
                }
                stoppedAis.append(model.getActiveAis().get(i).getName());
            }
        }
        if (stoppedAis.isEmpty()) {
            return null;
        }
        return stoppedAis.toString();
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
