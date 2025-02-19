package edu.kit.informatik.codefight.model.round.aicommands;

import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;

/**
 * This command swaps the first entry of the memory cell referenced by the first argument
 * with the second entry of the memory cell referenced by the second argument.
 * The positions of the memory cells are relative to the
 * current AI command.
 *
 * @author uexnb
 * @version 1.0
 */
public final class SwapCommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "SWAP";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi) {
        int firstArgument = currentCommand.getFirstArgument();
        int secondArgument = currentCommand.getSecondArgument();
        MemoryCell first = model.getMemory().getMemoryCell(currentAi.getInstructionPointer() + (long) firstArgument);
        first.setCurrentDisplaySymbol(currentAi);
        if (firstArgument == secondArgument) { //if the same memory cell is referenced
            int temp = first.getFirstArgument();
            first.setFirstArgument(first.getSecondArgument());
            first.setSecondArgument(temp);
            model.getMemory().setMemoryCell(currentAi.getInstructionPointer() + (long) firstArgument, first);
        } else { //if different memory cells are referenced
            MemoryCell second = model.getMemory().getMemoryCell(currentAi.getInstructionPointer() + (long) secondArgument);
            second.setCurrentDisplaySymbol(currentAi);
            int temp = first.getFirstArgument();
            first.setFirstArgument(second.getSecondArgument());
            second.setSecondArgument(temp);
            model.getMemory().setMemoryCell(currentAi.getInstructionPointer() + (long) firstArgument, first);
            model.getMemory().setMemoryCell(currentAi.getInstructionPointer() + (long) secondArgument, second);
        }
        currentAi.setInstructionPointer(currentAi.getInstructionPointer() + 1);
        currentAi.increaseNumberOfExecutedCommands(1);
    }
}
