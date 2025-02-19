package edu.kit.informatik.codefight.model;


import edu.kit.informatik.codefight.exceptions.GameExecutionException;
import edu.kit.informatik.codefight.exceptions.GameInitialisationException;
import edu.kit.informatik.codefight.model.round.RoundHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static edu.kit.informatik.codefight.command.usercommands.AddAiCommand.AI_COMMANDS_OVERLAP_MESSAGE;
import static java.lang.Math.floor;
import static java.lang.Math.min;

/**
 * This class represents the code fight game.
 *
 * @author uexnb
 * @version 1.0
 */
public class CodeFight {
    private static final String DUPLICATE_AI_NAME_FORMAT = "%s#%d";
    private static final int INDEX_OF_LAST_AI_INDEPENDENT_SYMBOL = 3;
    private final Memory memory;
    private final int maxAis;
    private final String[][] aiDependentSymbols;
    private final List<ArtificialIntelligence> registeredAis;
    private final List<ArtificialIntelligence> activeAis = new ArrayList<>();
    private RoundHandler roundHandler;
    private GamePhase phase;

    /**
     * Constructs a new CodeFight game.
     *
     * @param maxAis the maximum number of AIs
     * @param memorySize the size of the memory
     * @param aiIndependentSymbols the symbols for displaying the AI independent information of the memory
     *                             (unchanged AI commands, range limits of range display, next AI command of next AI,
     *                             next AI commands of other AIs)
     * @param aiDependentSymbols the symbols for displaying the AI dependent information of the memory
     */
    public CodeFight(int memorySize, String[] aiIndependentSymbols, String[][] aiDependentSymbols,
                     int maxAis) {
        this.maxAis = maxAis;
        this.memory = new Memory(memorySize, aiIndependentSymbols[0], aiIndependentSymbols[1], aiIndependentSymbols[2],
                aiIndependentSymbols[INDEX_OF_LAST_AI_INDEPENDENT_SYMBOL]);
        this.aiDependentSymbols = aiDependentSymbols.clone();
        this.phase = GamePhase.SETUP;
        this.registeredAis = new ArrayList<>();
    }
    /**
     * Returns the memory of the game.
     *
     * @return the memory of the game
     */
    public Memory getMemory() {
        return memory;
    }

    /**
     * Returns the current phase of the game.
     *
     * @return the phase of the game
     */
    public GamePhase getPhase() {
        return phase;
    }
    /**
     * Sets the phase of the game.
     *
     * @param phase the phase of the game
     */
    public void setPhase(GamePhase phase) {
        this.phase = phase;
    }
    /**
     * Returns the maximum number of AIs.
     *
     * @return the maximum number of AIs
     */
    public int getMaxAis() {
        return maxAis;
    }
    /**
     * Returns the list of registered AIs.
     *
     * @return the list of potential AIs
     */
    public List<ArtificialIntelligence> getRegisteredAis() {
        return Collections.unmodifiableList(registeredAis);
    }
    /**
     * Adds a new AI to the list of registered AIs.
     *
     * @param ai the AI to be added
     */
    public void registerAi(ArtificialIntelligence ai) {
        registeredAis.add(ai);
    }
    /**
     * Removes the AI with the specified name from the list of registered AIs.
     *
     * @param name the name of the AI to be removed
     */
    public void removeAi(String name) {
        for (int i = 0; i < registeredAis.size(); i++) {
            if (registeredAis.get(i).getName().equals(name)) {
                registeredAis.remove(i);
                break;
            }
        }
    }

    /**
     * Returns the list of active AIs.
     *
     * @return the list of active AIs
     */
    public List<ArtificialIntelligence> getActiveAis() {
        return Collections.unmodifiableList(activeAis);
    }

    /**
     * Returns the AI from the list of active AIs with the specified name.
     *
     * @param name the name of the AI to be returned
     * @return the AI with the specified name
     */
    public ArtificialIntelligence getActiveAi(String name) {
        for (ArtificialIntelligence ai : activeAis) {
            if (ai.getName().equals(name)) {
                return ai.copy();
            }
        }
        return null;
    }

    /**
     * Initialise the list of active AIs.
     *
     * @param aiNames the names of the registered AIs to be set as active
     */
    public void setActiveAis(String[] aiNames) {
        activeAis.clear(); //clear the current list of active AIs
        String[] copyOfAiNames = aiNames.clone(); //copy because the names are changed in the determineActiveAisNames method
        String[] correctedAiNames = determineActiveAisNames(copyOfAiNames);
        for (int i = 0; i < aiNames.length; i++) {
            for (ArtificialIntelligence ai : registeredAis) { //check for the registered AI with the same name
                if (ai.getName().equals(aiNames[i])) {
                    //add a copy of the AI with the corrected name to the list of active AIs
                    ArtificialIntelligence aiWithCorrectedName = ai.copy();
                    //set the AI to alive
                    aiWithCorrectedName.setIsAlive(true);
                    //set the standard AI command and the AI bomb for the AI
                    aiWithCorrectedName.setStandardAiCommand(aiDependentSymbols[i][0]);
                    aiWithCorrectedName.setAiBomb(aiDependentSymbols[i][1]);
                    //set the initial symbols for displaying the memory cells of the AI (in show memory command)
                    aiWithCorrectedName.setInitialDisplaySymbols();
                    //change the name of the AI to the correct name
                    aiWithCorrectedName.setName(correctedAiNames[i]);
                    activeAis.add(aiWithCorrectedName);
                    break;
                }
            }
        }
    }
    /**
     * Initialise the memory and load the AI commands into the memory. Set the instruction pointer of the AIs to their
     * first command and initialise the round handler.
     *
     * @throws GameInitialisationException if the AI commands overlap
     */
    public void startGame() throws GameInitialisationException {
        memory.initialiseMemory();
        for (int i = 0; i < activeAis.size(); i++) {
            int firstIndexOfCurrentAi = calculateFirstIndexOfCurrentAi(i);
            ArtificialIntelligence currentAi = activeAis.get(i);
            //put the AI commands in the memory
            for (int j = firstIndexOfCurrentAi;
                //AI commands exceeding memory size are ignored
                 j < min(firstIndexOfCurrentAi + currentAi.getAiCommands().size(), memory.getSize());
                 j++) {
                memory.setMemoryCell(j, currentAi.getAiCommands().get(j - firstIndexOfCurrentAi));
            }
            //set the instruction pointer of the AI to their first command
            currentAi.setInstructionPointer(firstIndexOfCurrentAi);
        }
        this.roundHandler = new RoundHandler(this);
    }

    private int calculateFirstIndexOfCurrentAi(int numberOfCurrentAi) throws GameInitialisationException {
        //calculate the first index of the current AI using the provided formula
        int firstIndexOfCurrentAi = (int) floor(numberOfCurrentAi * (double) memory.getSize() / activeAis.size());
        if (numberOfCurrentAi != activeAis.size() - 1) {
            int firstIndexOfNextAi = (int) floor((numberOfCurrentAi + 1) * (double) memory.getSize() / activeAis.size());
            //check if the AI commands overlap, if so throw an exception
            if (firstIndexOfCurrentAi + activeAis.get(numberOfCurrentAi).getAiCommands().size() > firstIndexOfNextAi) {
                throw new GameInitialisationException(AI_COMMANDS_OVERLAP_MESSAGE);
            }
        }
        return firstIndexOfCurrentAi;
    }
    /**
     * Execute the next commands of the AIs.
     *
     * @param numberOfCommands the number of commands to execute
     * @return an optional string containing the messages for all the stopped AIs
     * @throws GameExecutionException if the command name is not valid
     */
    public String executeNextCommands(int numberOfCommands) throws GameExecutionException {
        StringBuilder resultMessage = new StringBuilder();
        for (int i = 0; i < numberOfCommands; i++) {
            String result = roundHandler.executeCommand();
            if (result != null) {
                if (!resultMessage.isEmpty()) {
                    resultMessage.append(System.lineSeparator());
                }
                resultMessage.append(result);
            }
        }
        //check if no AI has been stopped, in that case the stringbuilder would be empty
        if (resultMessage.isEmpty()) {
            return null;
        }
        return resultMessage.toString();
    }
    /**
     * Returns the display of the memory. If an index of a memory cell is provided, a range
     * display is returned. Otherwise, a memory display is returned.
     *
     * @param indexOfMemoryCell the index of the memory cell from which the range display starts
     * @return the display of the memory
     */
    public String displayMemory(Integer indexOfMemoryCell) {
        if (indexOfMemoryCell == null) {
            return memory.getMemoryDisplay(activeAis, roundHandler.getNextAiIndex(), null);
        } else {
            return memory.getRangeDisplay(activeAis, roundHandler.getNextAiIndex(), indexOfMemoryCell);
        }
    }

    /**
     * Check for duplicate AI names in the list of AIs to be set as active and change the names to the
     * expected format if necessary.
     *
     * @param aiNames the names of the AIs to check
     * @return the revised names of the AIs to be set as active. If an AI is instantiated multiple times
     *         the name of the respective AI is supplemented by a number (starting from 0).
     */
    private String[] determineActiveAisNames(String[] aiNames) {
        String[] correctedAiNames = new String[aiNames.length];
        for (int i = 0; i < aiNames.length; i++) {
            boolean duplicate = false;
            int count = 0;
            for (int j = i + 1; j < aiNames.length; j++) {
                if (aiNames[i].equals(aiNames[j])) {
                    duplicate = true;
                    //add to the first instance of the AI name the number 0 (in if statement after inner for-loop)
                    //and to the all other instances the numbers 1, 2, 3, ...
                    count++;
                    aiNames[j] = String.format(DUPLICATE_AI_NAME_FORMAT, aiNames[j], count);
                }
            }
            if (!duplicate) {
                correctedAiNames[i] = aiNames[i];
            } else {
                correctedAiNames[i] = String.format(DUPLICATE_AI_NAME_FORMAT, aiNames[i], 0);
            }
        }
        return correctedAiNames;
    }
    /**
     * Reset the important game components so that a new game can be started.
     */
    public void reset() {
        activeAis.clear();
        phase = GamePhase.SETUP;
    }
}
