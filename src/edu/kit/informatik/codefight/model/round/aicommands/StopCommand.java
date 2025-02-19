package edu.kit.informatik.codefight.model.round.aicommands;


import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;

/**
 * This command stops the current AI. The AI has lost.
 *
 * @author uexnb
 * @version 1.0
 */
public final class StopCommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "STOP";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi) {
        currentAi.setIsAlive(false);
        currentAi.setInstructionPointer(-1);
        currentAi.increaseNumberOfExecutedCommands(1);
    }
}
