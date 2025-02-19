package edu.kit.informatik.codefight.model.round;


import edu.kit.informatik.codefight.exceptions.GameExecutionException;
import edu.kit.informatik.codefight.model.ArtificialIntelligence;
import edu.kit.informatik.codefight.model.CodeFight;
import edu.kit.informatik.codefight.model.MemoryCell;
import edu.kit.informatik.codefight.model.round.aicommands.AddCommand;
import edu.kit.informatik.codefight.model.round.aicommands.AddRCommand;
import edu.kit.informatik.codefight.model.round.aicommands.AiCommand;
import edu.kit.informatik.codefight.model.round.aicommands.CmpCommand;
import edu.kit.informatik.codefight.model.round.aicommands.JmpCommand;
import edu.kit.informatik.codefight.model.round.aicommands.JmzCommand;
import edu.kit.informatik.codefight.model.round.aicommands.MovICommand;
import edu.kit.informatik.codefight.model.round.aicommands.MovRCommand;
import edu.kit.informatik.codefight.model.round.aicommands.StopCommand;
import edu.kit.informatik.codefight.model.round.aicommands.SwapCommand;

import java.util.HashMap;
import java.util.Map;

import static edu.kit.informatik.codefight.command.CommandHandler.COMMAND_NOT_FOUND_FORMAT;
import static edu.kit.informatik.codefight.command.CommandHandler.ERROR_PREFIX;

/**
 * This class handles the execution of rounds.
 *
 * @author Programmieren-Team
 * @author uexnb
 * @version 1.0
 */
public class RoundHandler {
    private static final String AI_STOPPED_FORMAT = "%s executed %d steps until stopping.";
    private final CodeFight codeFight;
    private final Map<String, AiCommand> commands;
    private ArtificialIntelligence currentAi;
    private int nextAiIndex;
    /**
     * Creates a new round handler.
     *
     * @param codeFight the code fight game
     */
    public RoundHandler(CodeFight codeFight) {
        this.codeFight = codeFight;
        this.nextAiIndex = 0;
        this.currentAi = codeFight.getActiveAis().get(nextAiIndex);
        this.commands = new HashMap<>();
        initCommands();
        initInstructionPointers();
    }
    /**
     * Sets the index of the next AI. If no AI is alive, the value is set to -1.
     *
     */
    private void nextAiIndex() {
        //set the index to the next AI in the list of active AIs that is still alive
        for (int i = nextAiIndex + 1; i < nextAiIndex + 1 + codeFight.getActiveAis().size(); i++) {
            int nextIndex = i % codeFight.getActiveAis().size();
            if (codeFight.getActiveAis().get(nextIndex).getIsAlive()) {
                nextAiIndex = nextIndex;
                return;
            }
        }
        //set the current AI to -1 if no AI is alive
        nextAiIndex = -1;
    }
    /**
     * Executes the next command of the current AI.
     *
     * @return a string containing the name of the AI and the number of executed commands if the AI stopped
     *         otherwise null
     * @throws GameExecutionException if the command name is not valid
     */
    public String executeCommand() throws GameExecutionException {
        //check if there is still an AI alive because otherwise no command needs to be executed
        String resultMessage = "";
        if (nextAiIndex == -1) {
            return null;
        }
        currentAi = codeFight.getActiveAis().get(nextAiIndex);
        MemoryCell currentCommand = codeFight.getMemory().getMemoryCell(currentAi.getInstructionPointer());
        // check if the command name is valid
        if (!commands.containsKey(currentCommand.getCommandName())) {
            throw new GameExecutionException(ERROR_PREFIX + COMMAND_NOT_FOUND_FORMAT.formatted(currentCommand.getCommandName()));
        }
        // execute the command
        commands.get(currentCommand.getCommandName()).execute(codeFight, currentCommand, currentAi);
        //check if current AI executed a stop command
        if (!currentAi.getIsAlive()) {
            resultMessage += AI_STOPPED_FORMAT.formatted(currentAi.getName(), currentAi.getNumberOfExecutedCommands() - 1);
        }
        // set the next AI
        nextAiIndex();
        return resultMessage.isEmpty() ? null : resultMessage;
    }

    /**
     * Returns the index of the current AI.
     *
     * @return the index of the current AI
     */
    public int getNextAiIndex() {
        return nextAiIndex;
    }
    private void initCommands() {
        this.addCommand(StopCommand.COMMAND_NAME, new StopCommand());
        this.addCommand(MovRCommand.COMMAND_NAME, new MovRCommand());
        this.addCommand(MovICommand.COMMAND_NAME, new MovICommand());
        this.addCommand(AddCommand.COMMAND_NAME, new AddCommand());
        this.addCommand(AddRCommand.COMMAND_NAME, new AddRCommand());
        this.addCommand(JmpCommand.COMMAND_NAME, new JmpCommand());
        this.addCommand(JmzCommand.COMMAND_NAME, new JmzCommand());
        this.addCommand(CmpCommand.COMMAND_NAME, new CmpCommand());
        this.addCommand(SwapCommand.COMMAND_NAME, new SwapCommand());
    }

    private void addCommand(String commandName, AiCommand aiCommand) {
        this.commands.put(commandName, aiCommand);
    }

    /**
     * Checks for each AI if the first command is a stop command and if so, increments the instruction pointer until
     * the first command is not a stop command.
     */
    private void initInstructionPointers() {
        for (ArtificialIntelligence ai : codeFight.getActiveAis()) {
            while (codeFight.getMemory().getMemoryCell(ai.getInstructionPointer()).getCommandName().equals(StopCommand.COMMAND_NAME)) {
                ai.setInstructionPointer(ai.getInstructionPointer() + 1);
            }
        }
    }
}
