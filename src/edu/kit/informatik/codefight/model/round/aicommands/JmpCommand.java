package edu.kit.informatik.codefight.model.round.aicommands;


import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;


/**
 * This command jumps to the memory cell given by the first argument.
 * The position is relative to the current AI command.
 *
 * @author uexnb
 * @version 1.0
 */
public final class JmpCommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "JMP";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi) {
        // set the new address for the instruction pointer, the new address is calculated the same way
        //as in the getMemoryCell method of the Memory class
        long targetAddress = currentAi.getInstructionPointer() + (long) currentCommand.getFirstArgument();
        if (targetAddress < 0) {
            targetAddress = model.getMemory().getSize() + targetAddress % model.getMemory().getSize();
        } else {
            targetAddress %= model.getMemory().getSize();
        }
        currentAi.setInstructionPointer((int) targetAddress);
        currentAi.increaseNumberOfExecutedCommands(1);
    }
}
