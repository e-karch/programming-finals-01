package edu.kit.informatik.codefight.model.round.aicommands;


import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;

/**
 * This command adds the value of the first argument to the second entry of the
 * memory cell referenced by the second argument and saves the result in this memory cell.
 * The position of the target memory cell is relative.
 *
 * @author uexnb
 * @version 1.0
 */
public final class AddRCommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "ADD_R";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi) {
        int firstArgument = currentCommand.getFirstArgument();
        int secondArgument = currentCommand.getSecondArgument();
        MemoryCell target = model.getMemory().getMemoryCell(currentAi.getInstructionPointer()
                + (long) secondArgument);
        target.setSecondArgument(target.getSecondArgument() + firstArgument);
        target.setCurrentDisplaySymbol(currentAi);
        model.getMemory().setMemoryCell(currentAi.getInstructionPointer() + (long) secondArgument, target);
        currentAi.setInstructionPointer(currentAi.getInstructionPointer() + 1);
        currentAi.increaseNumberOfExecutedCommands(1);
    }
}
