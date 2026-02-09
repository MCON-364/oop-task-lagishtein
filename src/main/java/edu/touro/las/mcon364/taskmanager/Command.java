package edu.touro.las.mcon364.taskmanager;

/**
 * Command interface for task management operations.
 * This is a sealed interface that allows only specific command implementations.
 * Each command must implement the execute() method to perform its action.
 * This design follows the Command design pattern, enabling encapsulation of request parameters and decoupling of command execution from the invoker.
 * The permitted command types are:
 * - AddTaskCommand: Command to add a new task to the registry.
 * - RemoveTaskCommand: Command to remove an existing task from the registry.
 * - UpdateTaskCommand: Command to update the priority of an existing task in the registry.
 * This interface ensures that only valid command types can be executed by the TaskManager, providing a clear contract for command implementations and enhancing type safety.
 * Note: The actual command classes (AddTaskCommand, RemoveTaskCommand, UpdateTaskCommand) must be defined separately and implement the Command interface, providing specific logic for each operation.
 * They are declared as final classes that implement the Command interface, ensuring that they cannot be subclassed further, which maintains the integrity of the command structure and prevents unintended modifications to the command behavior.
 */
public sealed interface Command permits AddTaskCommand, RemoveTaskCommand, UpdateTaskCommand { void execute(); }
