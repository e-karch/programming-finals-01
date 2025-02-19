package edu.kit.informatik.codefight.exceptions;

import java.io.Serial;

/**
 * Exception that is thrown when the game cannot be executed.
 *
 * @author uexnb
 * @version 1.0
 */
public class GameExecutionException extends Exception {
    /**
     * Unique serialVersionUID.
     */
    @Serial
    private static final long serialVersionUID = 124567557825L;

    /**
     * Constructs a new {@code GameExecutionException} with an error message.
     *
     * @param message detailed error message
     */
    public GameExecutionException(final String message) {
        super(message);
    }

}
