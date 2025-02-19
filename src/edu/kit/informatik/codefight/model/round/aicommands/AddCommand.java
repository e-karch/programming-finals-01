package edu.kit.informatik.codefight.model.round.aicommands;


import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;

/**
 * This command adds the values of the first and second argument and saves the result in the second
 * argument.
 *
 * @author uexnb
 * @version 1.0
 */
public final class AddCommand implements AiCommand {
    /**
     * The name of the command.
     */
    public static final String COMMAND_NAME = "ADD";
    @Override
    public String getCommandName() {
        return COMMAND_NAME;
    }

    @Override
    public void execute(CodeFight model, MemoryCell currentCommand, ArtificialIntelligence currentAi) {
        String commandName = currentCommand.getCommandName();
        int firstArgument = currentCommand.getFirstArgument();
        int secondArgument = currentCommand.getSecondArgument();
        int result = firstArgument + secondArgument;
        model.getMemory().setMemoryCell(currentAi.getInstructionPointer(), new MemoryCell(commandName, firstArgument, result, currentAi));
        currentAi.setInstructionPointer(currentAi.getInstructionPointer() + 1);
        currentAi.increaseNumberOfExecutedCommands(1);
    }
}
