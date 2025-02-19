package edu.kit.informatik.codefight.model.round.aicommands;


import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;

/**
 * This command copies the content of the memory referenced by the first argument to the memory cell referenced by the
 * second argument. The position of the first argument is relative to the current memory cell.
 * The position of the target memory cell is given indirectly. This means that
 * the memory cell (intermediate) is first determined relative to the current AI command by the second argument. Subsequently,
 * the second argument again determines a memory cell relative to intermediate. This is the
 * target memory cell.
 *
 * @author uexnb
 * @version 1.0
 */
public final class MovICommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "MOV_I";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi) {
        int firstArgument = currentCommand.getFirstArgument();
        int secondArgument = currentCommand.getSecondArgument();
        MemoryCell source = model.getMemory().getMemoryCell(currentAi.getInstructionPointer() + (long) firstArgument);
        source.setCurrentDisplaySymbol(currentAi);
        long intermediateAddress = (long) currentAi.getInstructionPointer() + secondArgument;
        long targetAddress = intermediateAddress + model.getMemory().getMemoryCell(intermediateAddress).getSecondArgument();
        model.getMemory().setMemoryCell(targetAddress, source);
        currentAi.setInstructionPointer(currentAi.getInstructionPointer() + 1);
        currentAi.increaseNumberOfExecutedCommands(1);
    }
}
