package edu.kit.informatik.codefight.model.round.aicommands;


import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;

/**
 * This command copies the content of the memory cell referenced by the first argument to the memory cell
 * referenced by the second argument. The positions are relative to the current memory cell.
 *
 * @author uexnb
 * @version 1.0
 */
public final class MovRCommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "MOV_R";
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
        model.getMemory().setMemoryCell(currentAi.getInstructionPointer() + (long) secondArgument, source);
        currentAi.setInstructionPointer(currentAi.getInstructionPointer() + 1);
        currentAi.increaseNumberOfExecutedCommands(1);

    }
}
