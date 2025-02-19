package edu.kit.informatik.codefight.model.round.aicommands;


import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;

/**
 * This interface represents a command that can be executed by an AI.
 *
 * @author uexnb
 * @version 1.0
 */
public interface AiCommand {
    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    String getCommandName();
    /**
     * Executes the command.
     *
     * @param model the {@link CodeFight Code Fight Game} to execute the command on
     * @param currentCommand the current command to execute
     * @param currentAi the current AI which executes the command
     */
    void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi);

}
