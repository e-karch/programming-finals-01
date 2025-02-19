package edu.kit.informatik.codefight.model;

/**
 * Interface for classes that can be copied.
 *
 * @param <T> the type of the class that is copied
 * @author uexnb
 * @author Programmieren-Team
 * @version 1.0
 */
public interface Copyable<T> {

    /**
     * Creates a copy of the object.
     * @return the copy
     */
    T copy();
}

