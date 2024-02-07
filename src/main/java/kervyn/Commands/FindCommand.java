package kervyn.Commands;

import javafx.scene.image.Image;
import kervyn.Tasks.TaskList;

/**
 * Represents the "Find" command in the application, used to find specific tasks in the TaskList that match the user's input.
 */
public class FindCommand extends Command {

    private String userInput;
    private Image kervynImage;
    /**
     * Constructs a DeleteCommand with the specified TaskList and user input.
     *
     * @param taskList The TaskList associated with this command.
     * @param userInput The user input array containing details for the deletion command.
     */
    public FindCommand(TaskList taskList, String userInput, Image kervynImage) {
        super("Find", taskList, kervynImage);
        this.userInput = userInput;
        this.kervynImage = kervynImage;
    }


    /**
     * Executes the "Find" command.
     * This method finds tasks in the taskList that contains the keyword the user searched for
     */
    @Override
    public void executeCommand() {
        taskList.findTask(taskList.getTaskList(), this.userInput, kervynImage);
    }
}
