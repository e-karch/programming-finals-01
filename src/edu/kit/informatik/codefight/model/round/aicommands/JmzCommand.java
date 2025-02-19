package edu.kit.informatik.codefight.model.round.aicommands;

import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;

/**
 * This command jumps to the memory cell given by the first argument
 * if the value of the second entry of the memory cell
 * given by the second argument is 0. The positions of the memory cells are
 * relative to the current AI command.
 *
 * @author uexnb
 * @version 1.0
 */
public final class JmzCommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "JMZ";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi) {
        int firstArgument = currentCommand.getFirstArgument();
        int secondArgument = currentCommand.getSecondArgument();
        if (model.getMemory().getMemoryCell(currentAi.getInstructionPointer() + (long) secondArgument).getSecondArgument() == 0) {
            currentAi.setInstructionPointer(currentAi.getInstructionPointer() + firstArgument);
        } else {
            currentAi.setInstructionPointer(currentAi.getInstructionPointer() + 1);
        }
        currentAi.increaseNumberOfExecutedCommands(1);
    }
}
