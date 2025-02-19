package edu.kit.informatik.codefight.command;

import edu.kit.informatik.codefight.exceptions.GameInitialisationException;
import edu.kit.informatik.codefight.model.CodeFight;
import java.util.Arrays;


/**
 * This class initialises the code fight game.
 * It is responsible for parsing the command line arguments and setting up the game.
 * It also checks if the command line arguments are valid.
 * If the command line arguments are not valid, a GameInitializationException is thrown.
 * If the command line arguments are valid, the game is set up.
 * The game is then ready to be started and handle user input.
 *
 * @author uexnb
 * @version 1.0
 */
public class GameInitialiser {

    private static final int MIN_MEMORY_SIZE = 7;
    private static final int MAX_MEMORY_SIZE = 1337;
    private static final int MIN_NUMBER_OF_COMMAND_LINE_ARGUMENTS = 9;
    private static final int NUMBER_OF_AI_INDEPENDENT_SYMBOLS = 4;
    private static final int NUMBER_OF_AI_DEPENDENT_SYMBOLS = 2;
    private static final String INITIALIZATION_SUCCESS_MESSAGE = "Welcome to CodeFight 2024. Enter 'help' for more details.";
    private static final String MEMORY_SIZE_INVALID = "The size of the memory must be in the inclusive range of [7, 1337].";
    private static final String NUMBER_OF_ARGUMENTS_INVALID = "The number of command line arguments is invalid.";
    private static final String SYMBOLS_NOT_UNIQUE = "Symbols must be unique.";
    private final CodeFight game;
    /**
     * Constructs a new game initialiser with the specified command line arguments.
     * This game initialiser is responsible for setting up the game. If the game is set up properly,
     * an initialisation message is printed.
     *
     * @param commandLineArguments the command line arguments
     * @throws GameInitialisationException if the game cannot be initialised
     */
    public GameInitialiser(String[] commandLineArguments) throws GameInitialisationException {
        //check if there are enough command line arguments
        if (commandLineArguments.length < MIN_NUMBER_OF_COMMAND_LINE_ARGUMENTS || commandLineArguments.length % 2 == 0) {
            throw new GameInitialisationException(NUMBER_OF_ARGUMENTS_INVALID);
        }
        //check if the memory size is within the valid range
        int memorySize = Integer.parseInt(commandLineArguments[0]);
        if (memorySize < MIN_MEMORY_SIZE || memorySize > MAX_MEMORY_SIZE) {
            throw new GameInitialisationException(MEMORY_SIZE_INVALID);
        }
        //check if the symbols for the representation of the memory are unique
        if (containsSymbolMoreThanOnce(commandLineArguments)) {
            throw new GameInitialisationException(SYMBOLS_NOT_UNIQUE);
        }
        //extract the ai independent and dependent symbols
        String[] aiIndependentSymbols = Arrays.copyOfRange(commandLineArguments, 1, NUMBER_OF_AI_INDEPENDENT_SYMBOLS + 1);
        //calculate the maximum number of AIs by dividing the remaining command line arguments by the number of symbols per AI
        int maxAis = (commandLineArguments.length - NUMBER_OF_AI_INDEPENDENT_SYMBOLS - 1) / 2;
        String[][] aiDependentSymbols = new String[maxAis][2];
        for (int i = 0; i < maxAis; i++) {
            System.arraycopy(commandLineArguments, NUMBER_OF_AI_INDEPENDENT_SYMBOLS + 1 + NUMBER_OF_AI_DEPENDENT_SYMBOLS * i,
                    aiDependentSymbols[i], 0, 2);
        }
        //set up the game
        game = new CodeFight(memorySize, aiIndependentSymbols, aiDependentSymbols, maxAis);
        //print initialization message
        System.out.println(INITIALIZATION_SUCCESS_MESSAGE);

    }
    private boolean containsSymbolMoreThanOnce(String[] commandLineArguments) {
        for (int i = 1; i < commandLineArguments.length; i++) {
            for (int j = i + 1; j < commandLineArguments.length; j++) {
                if (commandLineArguments[i].equals(commandLineArguments[j])) {
                    return true;
                }
            }
        }
        return false;
    }
    /**
     * Returns the game that was set up.
     *
     * @return the game that was set up
     */
    public CodeFight getGame() {
        return game;
    }
}
