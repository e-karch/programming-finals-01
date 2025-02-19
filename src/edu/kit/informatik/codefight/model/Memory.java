package edu.kit.informatik.codefight.model;


import edu.kit.informatik.codefight.model.round.aicommands.AddCommand;
import edu.kit.informatik.codefight.model.round.aicommands.AddRCommand;
import edu.kit.informatik.codefight.model.round.aicommands.CmpCommand;
import edu.kit.informatik.codefight.model.round.aicommands.JmpCommand;
import edu.kit.informatik.codefight.model.round.aicommands.JmzCommand;
import edu.kit.informatik.codefight.model.round.aicommands.MovICommand;
import edu.kit.informatik.codefight.model.round.aicommands.MovRCommand;
import edu.kit.informatik.codefight.model.round.aicommands.StopCommand;
import edu.kit.informatik.codefight.model.round.aicommands.SwapCommand;

import java.util.List;
import java.util.Random;

/**
 * This class represents the memory of the game.
 *
 * @author uexnb
 * @version 1.0
 */
public class Memory {
    /**
     * The names of the AI commands in the order needed for the random initialisation of the memory.
     */
    private static final String[] ORDERED_AI_COMMAND_NAMES = {StopCommand.COMMAND_NAME,
                                                              MovRCommand.COMMAND_NAME,
                                                              MovICommand.COMMAND_NAME,
                                                              AddCommand.COMMAND_NAME,
                                                              AddRCommand.COMMAND_NAME,
                                                              JmpCommand.COMMAND_NAME,
                                                              JmzCommand.COMMAND_NAME,
                                                              CmpCommand.COMMAND_NAME,
                                                              SwapCommand.COMMAND_NAME};
    private static final String[] INIT_MODES = {"INIT_MODE_RANDOM", "INIT_MODE_STOP"};
    private static final int LENGTH_OF_RANGE_DISPLAY = 10;
    private static final String FORMAT_SPECIFIER_SYMBOL = "%";
    private static final String FORMAT_SPECIFIER_TYPE_STRING = "s";
    private static final String FORMAT_SPECIFIER_STRING = FORMAT_SPECIFIER_SYMBOL + FORMAT_SPECIFIER_TYPE_STRING + " ";
    private static final String FORMAT_SPECIFIER_TYPE_DECIMAL = "d";
    private static final String ARGUMENT_SEPARATOR = " | ";
    private static final String MEMORY_CELL_INDEX_SEPARATOR = ": ";
    private final int size;
    private final MemoryCell[] memory;
    private final String unchangedAiCommand;
    private final String rangeLimitsOfRangeDisplay;
    private final String nextAiCommandOfNextAi;
    private final String nextAiCommandsOfOtherAis;
    private String initMode;
    private int seed;
    /**
     * Constructs a new Memory.
     *
     * @param size the size of the memory
     * @param unchangedAiCommand the symbol for unchanged AI commands
     * @param rangeLimitsOfRangeDisplay the symbol for the range limits of the range display
     * @param nextAiCommandOfNextAi the symbol for the next AI command of the next AI
     * @param nextAiCommandsOfOtherAis the symbol for the next AI commands of the other AIs
     */
    public Memory(int size, String unchangedAiCommand, String rangeLimitsOfRangeDisplay, String nextAiCommandOfNextAi,
                  String nextAiCommandsOfOtherAis) {
        this.size = size;
        this.unchangedAiCommand = unchangedAiCommand;
        this.rangeLimitsOfRangeDisplay = rangeLimitsOfRangeDisplay;
        this.nextAiCommandOfNextAi = nextAiCommandOfNextAi;
        this.nextAiCommandsOfOtherAis = nextAiCommandsOfOtherAis;
        initMode = INIT_MODES[1];
        seed = 0;
        memory = new MemoryCell[size];
    }

    /**
     * Initialises the memory depending on the chosen init mode.
     */
    public void initialiseMemory() {
        if (initMode.equals(INIT_MODES[1])) {
            for (int i = 0; i < size; i++) {
                memory[i] = new MemoryCell(ORDERED_AI_COMMAND_NAMES[0], 0, 0, unchangedAiCommand);
            }
        } else {
            Random random = new Random(seed);
            for (int i = 0; i < size; i++) {
                memory[i] = new MemoryCell(ORDERED_AI_COMMAND_NAMES[random.nextInt(ORDERED_AI_COMMAND_NAMES.length)], random.nextInt(),
                        random.nextInt(), unchangedAiCommand);
            }


        }
    }
    /**
     * Returns all the AI command names in the order needed for the random initialisation of the memory.
     *
     * @return all the AI command names in the order needed for the random initialisation of the memory
     */
    public static String[] getOrderedAiCommandNames() {
        return ORDERED_AI_COMMAND_NAMES.clone();
    }
    /**
     * Returns the size of the memory.
     *
     * @return the size of the memory
     */
    public int getSize() {
        return size;
    }

    /**
     * Returns the possible init modes.
     *
     * @return the possible init modes
     */
    public static String[] getInitModes() {
        return INIT_MODES.clone();
    }
    /**
     * Returns the current init mode of the memory instance.
     *
     * @return the current init mode of the memory instance
     */
    public String getInitMode() {
        return initMode;
    }
    /**
     * Sets the init mode of the memory instance.
     *
     * @param initMode the init mode of the memory instance
     */
    public void setInitMode(String initMode) {
        this.initMode = initMode;
    }
    /**
     * Returns the seed of the memory instance.
     * If the init mode is INIT_MODE_STOP, null is returned as there is no seed required.
     *
     * @return the seed of the memory instance
     */
    public Integer getSeed() {
        if (initMode.equals(INIT_MODES[0])) {
            return seed;
        }
        return null;
    }
    /**
     * Sets the seed of the memory instance.
     *
     * @param seed the seed of the memory instance
     */
    public void setSeed(int seed) {
        this.seed = seed;
    }

    /**
     * Returns a copy of the memory cell at the given index. If the index exceeds the size of the memory,
     * it starts again from the beginning as the memory is circular. This is done by using the modulo operator.
     *
     * @param index the index of the memory cell, long accepted to prevent unintended integer overflow
     * @return memory cell at the given index
     */
    public MemoryCell getMemoryCell(long index) {
        if (index < 0) {
            return memory[(int) (size - (Math.abs(index) % size))].copy();
        } else {
            return memory[(int) (index % size)].copy();
        }
    }

    /**
     * Sets the memory cell at the given index. If the index exceeds the size of the memory,
     * it starts again from the beginning as the memory is circular. This is done by using the modulo operator.
     *
     * @param index the index of the memory cell, long accepted to prevent unintended integer overflow
     * @param memoryCell the memory cell to set
     */
    public void setMemoryCell(long index, MemoryCell memoryCell) {
        if (index < 0) {
            memory[(int) (size - (Math.abs(index) % size))] = memoryCell.copy();
        } else {
            memory[(int) (index % size)] = memoryCell.copy();
        }
    }
    /**
     * Returns the memory display for the current state of the game.
     *
     * @param activeAis the list of active AIs with their symbols
     * @param indexOfNextAi the index of the next AI in the list of active AIs
     * @param indexOfMemoryCell the index of the memory cell from which the range display starts
     *                          or null if the range display is not needed
     * @return the memory display for the current state of the game
     */
    public String getMemoryDisplay(List<ArtificialIntelligence> activeAis, int indexOfNextAi, Integer indexOfMemoryCell) {
        StringBuilder memoryDisplay = new StringBuilder();
        for (int i = 0; i < size; i++) {
            if (indexOfMemoryCell != null && LENGTH_OF_RANGE_DISPLAY > size //if the range display is greater than the size of the memory
                    && limitsNeedToBeInserted(indexOfMemoryCell, i, size)) {
                memoryDisplay.append(rangeLimitsOfRangeDisplay);
                if (indexOfMemoryCell != 0) {
                    //if the range display starts at a memory cell other than the first one, the
                    //range limit symbols are directly after each other
                    memoryDisplay.append(rangeLimitsOfRangeDisplay);
                }
            } else if (indexOfMemoryCell != null && LENGTH_OF_RANGE_DISPLAY <= size
                    && limitsNeedToBeInserted(indexOfMemoryCell, i, LENGTH_OF_RANGE_DISPLAY)) {
                memoryDisplay.append(rangeLimitsOfRangeDisplay);
            }
            generateSymbolsForMemoryCells(activeAis, indexOfNextAi, i, memoryDisplay);
        }
        //if the range display is greater than the size of the memory and the range display starts at the first memory cell
        //there needs to be a range limit at the end of the memory display
        if (indexOfMemoryCell != null && LENGTH_OF_RANGE_DISPLAY > size && indexOfMemoryCell == 0) {
            memoryDisplay.append(rangeLimitsOfRangeDisplay);
        }
        return memoryDisplay.toString();
    }

    private void generateSymbolsForMemoryCells(List<ArtificialIntelligence> activeAis, int indexOfNextAi,
                                               int indexInMemory, StringBuilder memoryDisplay) {
        //check for the memory cell in the correct order of priority which symbol to append
        if (isNextAiCommandOfNextAi(indexInMemory, activeAis, indexOfNextAi)) {
            memoryDisplay.append(nextAiCommandOfNextAi);
        } else if (isNextAiCommandOfOtherAis(indexInMemory, activeAis, indexOfNextAi)) {
            memoryDisplay.append(nextAiCommandsOfOtherAis);
        } else {
            if (memory[indexInMemory].getCurrentDisplaySymbol() != null) {
                memoryDisplay.append(memory[indexInMemory].getCurrentDisplaySymbol());
            } else {
                memoryDisplay.append(unchangedAiCommand);
            }
        }
    }

    private boolean limitsNeedToBeInserted(Integer indexOfMemoryCell, int index, int sizeOfRangeDisplay) {
        return index == indexOfMemoryCell % this.size || index == (indexOfMemoryCell + sizeOfRangeDisplay) % this.size;
    }

    private boolean isNextAiCommandOfNextAi(int index, List<ArtificialIntelligence> activeAis, int indexOfNextAi) {
        return indexOfNextAi != -1 && activeAis.get(indexOfNextAi).getInstructionPointer() % size == index;
    }
    private boolean isNextAiCommandOfOtherAis(int index, List<ArtificialIntelligence> activeAis, int indexOfNextAi) {
        if (indexOfNextAi == -1) {
            return false;
        }
        for (ArtificialIntelligence currentAi : activeAis) {
            if (activeAis.indexOf(currentAi) != indexOfNextAi && currentAi.getInstructionPointer() % size == index) {
                return true;
            }
        }
        return false;
    }
    /**
     * Returns the range display for the current state of the game.
     *
     * @param activeAis the list of active AIs with their symbols
     * @param indexOfCurrentAi the index of the next AI in the list of active AIs
     * @param indexOfMemoryCell the index of the memory cell from which the range display starts
     * @return the range display for the current state of the game
     */
    public String getRangeDisplay(List<ArtificialIntelligence> activeAis, int indexOfCurrentAi, int indexOfMemoryCell) {
        StringBuilder rangeDisplay = new StringBuilder();
        rangeDisplay.append(getMemoryDisplay(activeAis, indexOfCurrentAi, indexOfMemoryCell));
        rangeDisplay.append(System.lineSeparator());
        //get the format string for one memory cell by using the maximum length of the entries
        int maxMemoryCellIndexLength = calculateMaxMemoryIndexLength(indexOfMemoryCell);
        int maxCommandNameLength = calculateMaxCommandNameLength(indexOfMemoryCell);
        int maxFirstArgumentLength = calculateMaxFirstArgumentLength(indexOfMemoryCell);
        int maxSecondArgumentLength = calculateMaxSecondArgumentLength(indexOfMemoryCell);
        //get the right format string for the range display according to the maximum lengths
        String formatString = getFormatString(maxMemoryCellIndexLength, maxCommandNameLength, maxFirstArgumentLength,
                maxSecondArgumentLength);
        for (int i = indexOfMemoryCell;
             i < Math.min(indexOfMemoryCell + size, indexOfMemoryCell + LENGTH_OF_RANGE_DISPLAY); //if the range is greater than
             //the size of the memory, the range display should be stopped after showing every memory cell once
             i++) {
            if (i != indexOfMemoryCell) {
                rangeDisplay.append(System.lineSeparator());
            }
            if (isNextAiCommandOfNextAi(i % size, activeAis, indexOfCurrentAi)) { //if the current command is the next AI command
                rangeDisplay.append(formatString.formatted(nextAiCommandOfNextAi, i % size, memory[i % size].getCommandName(),
                        memory[i % size].getFirstArgument(), memory[i % size].getSecondArgument()));
            } else if (isNextAiCommandOfOtherAis(i % size, activeAis, indexOfCurrentAi)) {
                //if the current command is the next AI command of another AI
                rangeDisplay.append(formatString.formatted(nextAiCommandsOfOtherAis, i % size, memory[i % size].getCommandName(),
                        memory[i % size].getFirstArgument(), memory[i % size].getSecondArgument()));
            } else { //if the current command is no next AI command
                rangeDisplay.append(formatString.formatted(memory[i % size].getCurrentDisplaySymbol(),
                        i % size, memory[i % size].getCommandName(), memory[i % size].getFirstArgument(),
                        memory[i % size].getSecondArgument()));
            }
        }
        return rangeDisplay.toString();
    }

    private static String getFormatString(int maxMemoryCellIndexLength, int maxCommandNameLength, int maxFirstArgumentLength,
                                          int maxSecondArgumentLength) {
        return FORMAT_SPECIFIER_STRING
                + FORMAT_SPECIFIER_SYMBOL
                + maxMemoryCellIndexLength
                + FORMAT_SPECIFIER_TYPE_DECIMAL
                + MEMORY_CELL_INDEX_SEPARATOR
                + FORMAT_SPECIFIER_SYMBOL
                + maxCommandNameLength
                + FORMAT_SPECIFIER_TYPE_STRING
                + ARGUMENT_SEPARATOR
                + FORMAT_SPECIFIER_SYMBOL
                + maxFirstArgumentLength
                + FORMAT_SPECIFIER_TYPE_DECIMAL
                + ARGUMENT_SEPARATOR
                + FORMAT_SPECIFIER_SYMBOL
                + maxSecondArgumentLength
                + FORMAT_SPECIFIER_TYPE_DECIMAL;
    }

    private int calculateMaxMemoryIndexLength(int indexOfMemoryCell) {
        int maxMemoryCellIndexLength = 0;
        for (int i = indexOfMemoryCell; i < indexOfMemoryCell + LENGTH_OF_RANGE_DISPLAY; i++) {
            maxMemoryCellIndexLength = Math.max(maxMemoryCellIndexLength, String.valueOf(i % size).length());
        }
        return maxMemoryCellIndexLength;
    }
    private int calculateMaxCommandNameLength(int indexOfMemoryCell) {
        int maxCommandNameLength = 0;
        for (int i = indexOfMemoryCell; i < indexOfMemoryCell + LENGTH_OF_RANGE_DISPLAY; i++) {
            maxCommandNameLength = Math.max(maxCommandNameLength, memory[i % size].getCommandName().length());
        }
        return maxCommandNameLength;
    }
    private int calculateMaxFirstArgumentLength(int indexOfMemoryCell) {
        int maxFirstArgumentLength = 0;
        for (int i = indexOfMemoryCell; i < indexOfMemoryCell + LENGTH_OF_RANGE_DISPLAY; i++) {
            maxFirstArgumentLength = Math.max(maxFirstArgumentLength, String.valueOf(memory[i % size].getFirstArgument()).length());
        }
        return maxFirstArgumentLength;
    }
    private int calculateMaxSecondArgumentLength(int indexOfMemoryCell) {
        int maxSecondArgumentLength = 0;
        for (int i = indexOfMemoryCell; i < indexOfMemoryCell + LENGTH_OF_RANGE_DISPLAY; i++) {
            maxSecondArgumentLength = Math.max(maxSecondArgumentLength, String.valueOf(memory[i % size].getSecondArgument()).length());
        }
        return maxSecondArgumentLength;
    }
}
