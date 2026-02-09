package edu.touro.las.mcon364.taskmanager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for Command implementations.
 * After sealing the Command interface and refactoring, these tests should still pass.
 */
class CommandTest {
    private TaskRegistry registry;

    @BeforeEach
    void setUp() {
        registry = new TaskRegistry();
    }

    @Test
    @DisplayName("AddTaskCommand should add task to registry")
    void testAddTaskCommand() {
        Task task = new Task("New task", Priority.MEDIUM);
        Command command = new AddTaskCommand(registry, task);

        command.execute();

        Optional<Task> retrieved = registry.get("New task");
        assertTrue(retrieved.isPresent(), "Task should be in registry after AddTaskCommand");
        assertEquals(task, retrieved.get(), "Added task should match");
    }

    @Test
    @DisplayName("AddTaskCommand should replace existing task with same name")
    void testAddTaskCommandReplacement() {
        Task originalTask = new Task("Task", Priority.LOW);
        Task replacementTask = new Task("Task", Priority.HIGH);

        new AddTaskCommand(registry, originalTask).execute();
        new AddTaskCommand(registry, replacementTask).execute();

        Optional<Task> updated = registry.get("Task");
        assertTrue(updated.isPresent(), "Task should exist");
        assertEquals(Priority.HIGH, updated.get().priority(),
                "Replacement task should have new priority");
    }

    @Test
    @DisplayName("RemoveTaskCommand should remove task from registry")
    void testRemoveTaskCommand() {
        registry.add(new Task("To be removed", Priority.HIGH));

        Command command = new RemoveTaskCommand(registry, "To be removed");
        command.execute();

        assertFalse(registry.get("To be removed").isPresent(), "Task should be removed from registry");
    }

    @Test
    @DisplayName("RemoveTaskCommand on non-existent task should not throw")
    void testRemoveTaskCommandNonExistent() {
        Command command = new RemoveTaskCommand(registry, "Non-existent");

        assertDoesNotThrow(command::execute,
                "Removing non-existent task should not throw exception");
    }

    @Test
    @DisplayName("UpdateTaskCommand should update existing task priority")
    void testUpdateTaskCommand() {
        registry.add(new Task("Update me", Priority.LOW));

        Command command = new UpdateTaskCommand(registry, "Update me", Priority.HIGH);
        command.execute();

        Optional<Task> updated = registry.get("Update me");
        assertTrue(updated.isPresent(), "Task should still exist after update");
        assertEquals(Priority.HIGH, updated.get().priority(), "Priority should be updated to HIGH");
    }

    @Test
    @DisplayName("UpdateTaskCommand should preserve task name")
    void testUpdateTaskCommandPreservesName() {
        registry.add(new Task("Important task", Priority.MEDIUM));

        Command command = new UpdateTaskCommand(registry, "Important task", Priority.LOW);
        command.execute();

        Optional<Task> updated = registry.get("Important task");
        assertTrue(updated.isPresent(), "Task should exist");
        assertEquals("Important task", updated.get().name(), "Task name should be preserved");
    }

    @Test
    @DisplayName("UpdateTaskCommand on non-existent task should throw TaskNotFoundException")
    void testUpdateTaskCommandNonExistent() {
        Command command = new UpdateTaskCommand(registry, "Non-existent", Priority.HIGH);

        // After refactoring: this should throw TaskNotFoundException
        assertThrows(TaskNotFoundException.class, command::execute,
                "Updating non-existent task should throw TaskNotFoundException");

        // Task should not be created
        assertFalse(registry.get("Non-existent").isPresent(),
                "Non-existent task should not be created by failed update");
    }
}
