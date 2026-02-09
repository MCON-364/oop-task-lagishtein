package edu.touro.las.mcon364.taskmanager;

/**
 * Exception thrown when a task cannot be found in the registry.
 * This is a runtime exception to simplify usage in modern Java code.
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }

    public TaskNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}

