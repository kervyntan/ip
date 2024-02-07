package kervyn.Commands;

import javafx.scene.image.Image;
import kervyn.Tasks.TaskList;

/**
 * Represents the "Mark" command in the application, used to mark a task as completed in the TaskList.
 */
public class MarkCommand extends Command {
    private String[] userInput;

    /**
     * Constructs a MarkCommand with the specified TaskList and user input.
     *
     * @param taskList The TaskList associated with this command.
     * @param userInput The user input array containing the index of the task to be marked.
     */
    public MarkCommand(TaskList taskList, String[] userInput, Image kervynImage) {
        super("Mark", taskList, kervynImage);
        this.userInput = userInput;
    }

    /**
     * Executes the "Mark" command.
     * This method marks a task as completed in the task list based on the index provided in the user input.
     */
    @Override
    public void executeCommand() {
        taskList.markTask(taskList.getTaskList(), this.userInput);
    }

    public String execute() {
        return taskList.markTask(taskList.getTaskList(), this.userInput);
    }
}
