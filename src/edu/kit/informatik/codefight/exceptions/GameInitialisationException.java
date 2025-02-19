package edu.kit.informatik.codefight.exceptions;

import java.io.Serial;

/**
 * Exception that is thrown when the game cannot be initialised.
 *
 * @author uexnb
 * @version 1.0
 */
public class GameInitialisationException extends Exception {
    /**
     * Unique serialVersionUID.
     */
    @Serial
    private static final long serialVersionUID = 3223457825L;

    /**
     * Constructs a new {@code GameInitialisationException} with an error message.
     *
     * @param message detailed error message
     */
    public GameInitialisationException(final String message) {
        super(message);
    }

}
