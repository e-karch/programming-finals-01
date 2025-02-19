package edu.kit.informatik.codefight.command.usercommands;


import edu.kit.informatik.codefight.command.CommandResult;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.GamePhase;

/**
 * This interface represents a command that can be executed by a user.
 *
 *
 * @author uexnb
 * @version 1.0
 */
public interface UserCommand {
    /**
     * Returns the correct phase for this user command.
     *
     * @return the correct phase for this user command
     */
    GamePhase getCorrectPhase();
    /**
     * Returns the number of arguments that the command expects.
     *
     * @return the number of arguments that the command expects
     */
    int getNumberOfArguments();
    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    String getCommandName();
    /**
     * Returns the description of the command.
     *
     * @return the description of the command
     */
    String getCommandDescription();

    /**
     * Executes the command.
     *
     * @param model the {@link CodeFight Code Fight Game} to execute the command on
     * @param commandArguments the arguments of the command
     * @return the result of the command
     */
    CommandResult execute(CodeFight model, String[] commandArguments);
}
