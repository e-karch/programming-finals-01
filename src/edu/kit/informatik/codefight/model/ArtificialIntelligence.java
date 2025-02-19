package edu.kit.informatik.codefight.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static edu.kit.informatik.codefight.command.usercommands.AddAiCommand.AI_COMMAND_LENGTH;

/**
 * This class represents an AI of the game.
 *
 * @author uexnb
 * @version 1.0
 */
public class ArtificialIntelligence implements Copyable<ArtificialIntelligence> {
    private String name;
    private String standardAiCommand;
    private String aiBomb;
    private boolean isAlive;
    private final List<MemoryCell> aiCommands;
    private int instructionPointer;
    private int numberOfExecutedCommands;

    /**
     * Constructs a new AI with the specified name and the size of the memory of the game.
     *
     * @param name the name of the AI
     */
    public ArtificialIntelligence(String name) {
        this.name = name;
        isAlive = false;
        numberOfExecutedCommands = 0;
        aiCommands = new ArrayList<>();

    }

    /**
     * Sets the standard symbol for the AI commands.
     *
     * @param standardAiCommand the standard symbol for the AI commands
     */
    public void setStandardAiCommand(String standardAiCommand) {
        this.standardAiCommand = standardAiCommand;
    }

    /**
     * Sets the symbol for the AI bombs.
     *
     * @param aiBomb the symbol for the AI bombs
     */
    public void setAiBomb(String aiBomb) {
        this.aiBomb = aiBomb;
    }

    /**
     * Returns the standard symbol for the AI commands.
     *
     * @return the standard symbol for the AI commands
     */
    public String getStandardAiCommand() {
        return standardAiCommand;
    }

    /**
     * Returns the symbol for the AI bombs.
     *
     * @return the symbol for the AI bombs
     */
    public String getAiBomb() {
        return aiBomb;
    }

    /**
     * Returns the name of the AI.
     *
     * @return the name of the AI
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the AI.
     *
     * @param name the name of the AI
     */
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public ArtificialIntelligence copy() {
        ArtificialIntelligence copy = new ArtificialIntelligence(name);
        copy.setIsAlive(isAlive);
        for (MemoryCell aiCommand : aiCommands) {
            copy.addAiCommand(aiCommand.copy());
        }
        copy.setInstructionPointer(instructionPointer);
        copy.increaseNumberOfExecutedCommands(numberOfExecutedCommands);
        return copy;
    }

    /**
     * Returns the AI commands.
     *
     * @return the AI commands
     */
    public List<MemoryCell> getAiCommands() {
        return Collections.unmodifiableList(aiCommands);
    }

    /**
     * Sets the AI commands.
     *
     * @param aiCommands the AI commands
     */
    public void setAiCommands(String[] aiCommands) {
        for (int i = 0; i < aiCommands.length; i += AI_COMMAND_LENGTH) { //extract the AI commands from the string array
            this.aiCommands.add(new MemoryCell(aiCommands[i],
                    Integer.parseInt(aiCommands[i + 1]),
                    Integer.parseInt(aiCommands[i + 2]),
                    this));
        }
    }

    /**
     * Adds an AI command to the AI commands.
     *
     * @param aiCommand the AI command to be added
     */
    private void addAiCommand(MemoryCell aiCommand) {
        aiCommands.add(aiCommand);
    }

    /**
     * Sets the initial display symbols for the AI commands (no ai bombs).
     */
    public void setInitialDisplaySymbols() {
        for (MemoryCell aiCommand : aiCommands) {
            aiCommand.initDisplaySymbol(standardAiCommand);
        }
    }

    /**
     * Returns the current position in the memory of the instruction pointer of the AI.
     *
     * @return the instruction pointer of the AI
     */
    public int getInstructionPointer() {
        return instructionPointer;
    }

    /**
     * Sets the instruction pointer of the AI.
     * If the AI executed a stop command, the instruction pointer is set to -1.
     *
     * @param instructionPointer the instruction pointer of the AI
     */
    public void setInstructionPointer(int instructionPointer) {
        this.instructionPointer = instructionPointer;
    }

    /**
     * Returns whether the AI is alive.
     *
     * @return true if the AI is alive, false otherwise
     */
    public boolean getIsAlive() {
        return isAlive;
    }

    /**
     * Sets whether the AI is alive.
     *
     * @param isAlive true if the AI is alive, false otherwise
     */
    public void setIsAlive(boolean isAlive) {
        this.isAlive = isAlive;
    }

    /**
     * Returns the number of executed commands of the AI.
     *
     * @return the number of executed commands of the AI
     */
    public int getNumberOfExecutedCommands() {
        return numberOfExecutedCommands;
    }

    /**
     * Increases the number of executed commands of the AI by the specified number.
     *
     * @param numberOfExecutedCommands the additional number of executed commands of the AI
     */
    public void increaseNumberOfExecutedCommands(int numberOfExecutedCommands) {
        this.numberOfExecutedCommands += numberOfExecutedCommands;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        ArtificialIntelligence aiObject = (ArtificialIntelligence) object;
        return Objects.equals(getName(), aiObject.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }

    @Override
    public String toString() {
        return name;
    }
}
