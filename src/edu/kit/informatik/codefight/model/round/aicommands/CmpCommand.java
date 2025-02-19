package edu.kit.informatik.codefight.model.round.aicommands;


import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;


/**
 * This command compares the first entry of the memory cell given by the first argument
 * with the second entry of the memory cell given by the second argument. If the values are not equal
 * the next AI command is skipped. The positions
 * are relative to the current AI command.
 *
 * @author uexnb
 * @version 1.0
 */
public final class CmpCommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "CMP";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi) {
        int firstArgument = currentCommand.getFirstArgument();
        int secondArgument = currentCommand.getSecondArgument();
        if (model.getMemory().getMemoryCell(currentAi.getInstructionPointer() + (long) firstArgument).getFirstArgument()
                != model.getMemory().getMemoryCell(currentAi.getInstructionPointer() + (long) secondArgument).getSecondArgument()) {
            currentAi.setInstructionPointer(currentAi.getInstructionPointer() + 2);
        } else {
            currentAi.setInstructionPointer(currentAi.getInstructionPointer() + 1);
        }
        currentAi.increaseNumberOfExecutedCommands(1);
    }
}
