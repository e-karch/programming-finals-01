package edu.kit.informatik.codefight.model;

import edu.kit.informatik.codefight.model.round.aicommands.JmpCommand;
import edu.kit.informatik.codefight.model.round.aicommands.JmzCommand;
import edu.kit.informatik.codefight.model.round.aicommands.StopCommand;

/**
 * This class represents a memory cell of the memory with a command name and two arguments.
 *
 * @author uexnb
 * @version 1.0
 */
public class MemoryCell implements Copyable<MemoryCell> {
    private static final String MEMORY_CELL_FORMAT = "%s|%d|%d";
    private final String commandName;
    private int firstArgument;
    private int secondArgument;
    private String currentDisplaySymbol;

    /**
     * Creates a new memory cell.
     *
     * @param commandName the name of the command
     * @param firstArgument the first argument of the command
     * @param secondArgument the second argument of the command
     * @param lastAiToChange the last AI to change this memory cell
     */
    public MemoryCell(String commandName, int firstArgument, int secondArgument, ArtificialIntelligence lastAiToChange) {
        this.commandName = commandName;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        if (lastAiToChange != null) {
            setCurrentDisplaySymbol(lastAiToChange);
        } else {
            currentDisplaySymbol = null;
        }
    }
    /**
     * Creates a new memory cell.
     *
     * @param commandName the name of the command
     * @param firstArgument the first argument of the command
     * @param secondArgument the second argument of the command
     * @param currentDisplaySymbol the current display symbol of the memory cell
     */
    public MemoryCell(String commandName, int firstArgument, int secondArgument, String currentDisplaySymbol) {
        this.commandName = commandName;
        this.firstArgument = firstArgument;
        this.secondArgument = secondArgument;
        this.currentDisplaySymbol = currentDisplaySymbol;
    }

    /**
     * Returns the name of the command.
     *
     * @return the name of the command
     */
    public String getCommandName() {
        return commandName;
    }

    /**
     * Returns the first argument of the command.
     *
     * @return the first argument of the command
     */
    public int getFirstArgument() {
        return firstArgument;
    }

    /**
     * Returns the second argument of the command.
     *
     * @return the second argument of the command
     */
    public int getSecondArgument() {
        return secondArgument;
    }


    /**
     * Sets the first argument of the command.
     *
     * @param firstArgument the first argument of the command
     */
    public void setFirstArgument(int firstArgument) {
        this.firstArgument = firstArgument;
    }

    /**
     * Sets the second argument of the command.
     *
     * @param secondArgument the second argument of the command
     */
    public void setSecondArgument(int secondArgument) {
        this.secondArgument = secondArgument;
    }
    /**
     * Returns the current display symbol of the memory cell.
     *
     * @return the current display symbol of the memory cell
     */
    public String getCurrentDisplaySymbol() {
        return currentDisplaySymbol;
    }
    /**
     * Sets the current display symbol of the memory cell.
     *
     * @param lastAiToChange the last AI to change this memory cell
     */
    public void setCurrentDisplaySymbol(ArtificialIntelligence lastAiToChange) {
        if (lastAiToChange == null) {
            return;
        }
        if (commandName.equals(StopCommand.COMMAND_NAME)) {
            currentDisplaySymbol = lastAiToChange.getAiBomb();
        } else if (commandName.equals(JmpCommand.COMMAND_NAME) && firstArgument == 0) {
            currentDisplaySymbol = lastAiToChange.getAiBomb();
        } else if (commandName.equals(JmzCommand.COMMAND_NAME) && firstArgument == 0 && secondArgument == 0) {
            currentDisplaySymbol = lastAiToChange.getAiBomb();
        } else {
            currentDisplaySymbol = lastAiToChange.getStandardAiCommand();
        }
    }
    /**
     * Sets the initial display symbol of the memory cell.
     * Before the first AI has changed the memory cell, the display symbol is
     * always the standard AI command symbol of the AI this memory cell belongs to.
     *
     * @param standardAiCommand the standard AI command symbol
     */
    public void initDisplaySymbol(String standardAiCommand) {
        currentDisplaySymbol = standardAiCommand;
    }

    @Override
    public MemoryCell copy() {
        return new MemoryCell(commandName, firstArgument, secondArgument, currentDisplaySymbol);
    }

    @Override
    public String toString() {
        return MEMORY_CELL_FORMAT.formatted(commandName, firstArgument, secondArgument);
    }

}
