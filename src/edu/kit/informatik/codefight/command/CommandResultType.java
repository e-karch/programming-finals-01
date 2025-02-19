package edu.kit.informatik.codefight.command;

/**
 * This enum represents the types that a result of a command can be.
 *
 * @author uexnb
 * @author Programmieren-Team
 * @version 1.0
 */
public enum CommandResultType {
    /**
     * The command was executed successfully.
     */
    SUCCESS,

    /**
     * An error occured during processing the command.
     */
    FAILURE;
}
