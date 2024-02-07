package kervyn.Tasks;

import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import kervyn.FXControls.DialogBox;

import java.util.ArrayList;


/**
 * Represents a list of tasks and provides operations for managing tasks.
 */
public class TaskList {
    // Contains task-related operations
    private ArrayList<Task> taskList;

    /**
     * Constructs an empty TaskList.
     */
    public TaskList() {
        this.taskList = new ArrayList<Task>();
    }


    /**
     * Constructs a TaskList with an existing list of tasks.
     *
     * @param taskList The ArrayList of Task objects to initialize the TaskList.
     */
    public TaskList (ArrayList<Task> taskList) {
        this.taskList = taskList;
    }

    /**
     * Gets the list of tasks.
     *
     * @return The ArrayList of Task objects.
     */
    public ArrayList<Task> getTaskList() {
        return this.taskList;
    }

    /**
     * Adds a task to the task list.
     *
     * @param task The Task object to be added.
     */
    public void addTask(Task task) {
        this.taskList.add(task);
    }

    /**
     * Lists all tasks in the task list.
     *
     * @param userTasks The ArrayList of Task objects to be listed.
     * @return Returns 1 if the list operation was successful, 0 otherwise.
     */
    public short listTasks(ArrayList<Task> userTasks, Image kervynImage, VBox dialogContainer) {
        dialogContainer.getChildren().add(
                DialogBox.getKervynDialog("\tHere are the tasks on your list:", kervynImage)
        );
        String textToOutput = "";
        for (int i = 0; i < userTasks.size(); i++) {
            Task task = userTasks.get(i);
            char check = task.getStatus() ? 'X' : ' ';
            char type = task.getCapitalType();
            switch (type) {
                case 'T':
                    textToOutput = "\t" + (i + 1) + "." + "[" + type + "] " +  "[" + check + "] " + task.getDescription();
                    dialogContainer.getChildren().add(
                            DialogBox.getKervynDialog(textToOutput, kervynImage)
                    );
                    break;
                case 'D':
                    Deadline deadlineTask = (Deadline) task;
                    if (deadlineTask == null) {
                        return 0;
                    }
                    textToOutput = "\t" + (i + 1) + "." + "[" + type + "] " + "[" + check + "] " + deadlineTask.getDescription() + " (by: " + deadlineTask.getDeadline() + ")";
                    dialogContainer.getChildren().add(
                            DialogBox.getKervynDialog(textToOutput, kervynImage)
                    );
                    break;
                case 'E':
                    Event eventTask = (Event) task;
                    if (eventTask == null) {
                        return 0;
                    }
                    textToOutput = "\t" + (i + 1) + "." + "[" + type + "] " + "[" + check + "] "  + eventTask.getDescription() + " (from: " + eventTask.getStartDate() + " to: " + eventTask.getEndDate() + ")";
                    dialogContainer.getChildren().add(
                            DialogBox.getKervynDialog(textToOutput, kervynImage)
                    );
                    break;
                default:
                    textToOutput = "\tNo tasks to display :(";
                    dialogContainer.getChildren().add(
                            DialogBox.getKervynDialog(textToOutput, kervynImage)
                    );
                    break;
            }
        }
        return 1;
    }

    /**
     * Marks a task as completed in the task list.
     *
     * @param userTasks          The ArrayList of Task objects.
     * @param processedUserInput The user input processed into an array of Strings.
     * @return Returns 1 if the mark operation was successful, 0 otherwise.
     */
    public String markTask(ArrayList<Task> userTasks, String[] processedUserInput, Image kervynImage) {
        try {
            Task task = userTasks.get(Integer.parseInt(processedUserInput[1]) - 1);
            if (task.getStatus()) {
                taskAlreadyMarked();
            } else {
                System.out.println("\tNice! I've marked this task as done:");
                task.updateStatus();
                System.out.println(task.toString());
                return task.toString();
            }

//            return 1;
        }
        catch (IndexOutOfBoundsException e) {
            // Need to account for trying to mark a task that doesn't exist
            System.out.println("\tTask number provided doesn't exist. Please try again.");
            return "\tTask number provided doesn't exist. Please try again.";
        }
//        return 0;
        return "";
    }

    /**
     * Unmarks a task as not completed in the task list.
     *
     * @param userTasks The ArrayList of Task objects.
     * @param processedUserInput The user input processed into an array of Strings.
     * @return Returns 1 if the unmark operation was successful, 0 otherwise.
     */
    public short unMarkTask(ArrayList<Task> userTasks, String[] processedUserInput, Image kervynImage) {
        try {
            Task task = userTasks.get(Integer.parseInt(processedUserInput[1]) - 1);
            if (!task.getStatus()) {
                taskAlreadyUnMarked();
            } else {
                System.out.println("\tOK, I've marked this task as not done yet:");
                task.updateStatus();
                System.out.println(task.toString());
            }

            return 1;
        }
        catch (IndexOutOfBoundsException e) {
            // Need to account for trying to unmark a task that doesn't exist
            System.out.println("\tTask number provided doesn't exist. Please try again.");
        }
        return 0;
    }

    /**
     * Private method to handle the scenario when a task is already marked.
     */
    private static void taskAlreadyMarked() {
        System.out.println("\tUh oh! It looks like this task is already marked as done, please try again with another task!");
    }

    /**
     * Private method to handle the scenario when a task is already unmarked.
     */
    private static void taskAlreadyUnMarked() {
        System.out.println("\tUh oh! It looks like this task is already marked as not done, please try again with another task!");
    }

    /**
     * Removes a task from the task list.
     *
     * @param userTasks The ArrayList of Task objects.
     * @param processedUserInput The user input processed into an array of Strings.
     */
    public void removeTask(ArrayList<Task> userTasks, String[] processedUserInput, Image kervynImage) {
        try {
            Task task = userTasks.get(Integer.parseInt(processedUserInput[1]) - 1);
            System.out.println("\tOK, I've removed this task as per your request:");
            System.out.println(task.toString());
            userTasks.remove(task);
        }
        catch (IndexOutOfBoundsException e) {
            // Need to account for trying to delete a task that doesn't exist
            System.out.println("\tTask number provided doesn't exist. Please try again.");
        }
    }

    public void findTask(ArrayList<Task> userTasks, String userInput, Image kervynImage) {
        String[] processedUserInput = userInput.split(" ");
        StringBuilder userKeywords = new StringBuilder();
        ArrayList<Task> results = new ArrayList<>();

        for (int i = 1; i < processedUserInput.length; i++) {
            userKeywords.append(processedUserInput[i]);
        }

        for (Task userTask : userTasks) {
            // Check the type of the tasks
            String task = "";
            task = userTask.toString();
            if (task.contains(userKeywords)) {
                results.add(userTask);
            }
        }

        if (results.isEmpty()) {
            System.out.println("\tThere are no tasks that match your keyword provided.");
        } else {
            System.out.println("\tHere are the matching tasks in your list:");
            for (int j = 0; j < results.size(); j++) {
                // Check the type of the tasks
                System.out.println("\t" + (j + 1) + ". " + results.get(j).toString());
            }
        }
    }
}
