package ui;

import model.Work;
import model.WorkToDo;
import persistence.Reader;
import persistence.Writer;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

// This class is for the user interaction purposes
public class ToDoApplication {

    private static final String JSON_STORE = "./data/worktodo.json";
    private WorkToDo workToDo;
    private Scanner myObj  = new Scanner(System.in);
    private Writer jsonWriter;
    private Reader jsonReader;


    public ToDoApplication() throws FileNotFoundException {
        workToDo = new WorkToDo();
        jsonWriter = new Writer(JSON_STORE);
        jsonReader = new Reader(JSON_STORE);
        welcome();
        start();
    }

    //EFFECTS: prints out the welcome statement of user interaction menu
    private void welcome() {
        System.out.println("Welcome! to work manager TO-DO Application");
    }

    private void start() {
        menu();
        myObj = new Scanner(System.in);
        String decision = myObj.nextLine();
        whatToDo(decision);
    }

    // EFFECTS: gives user options, decides what is the next course of action
    private void whatToDo(String decision) {
        if (decision.equals("Add")) {
            addWork();
        } else if (decision.equals("Delete")) {
            deleteWork();
        } else if (decision.equals("Complete")) {
            completeWork();
        } else if (decision.equals("Check")) {
            checkWork();
        } else if (decision.equals("View")) {
            viewWork();
        } else if (decision.equals("Save")) {
            saveWork();
        } else if (decision.equals("Load")) {
            loadWork();
        } else if (decision.equals("Exit")) {
            System.out.println("Bye");
        }

    }

    //EFFECTS: lets the user check how many incomplete/complete works are there
    private void checkWork() {
        int completed = 0;
        int incompleted = 0;
        for (Work w : workToDo.getWorks()) {
            if (w.isCompleted()) {
                completed++;
            } else {
                incompleted++;
            }

        }

        System.out.println("Completed work - " + completed);
        System.out.println("Incompleted work -" + incompleted);

        start();


    }

    //EFFECTS: lets the user mark work as complete
    private void completeWork() {
        String name;
        System.out.println("Enter the name of work to be completed :");
        name = myObj.nextLine();

        boolean contains = false;

        Work toBeCompleted = new Work("name", 10);

        for (Work work : workToDo.getWorks()) {
            if (work.getName().equals(name)) {
                contains = true;
                toBeCompleted = work;
            }
        }
        if (!contains) {
            System.out.println("Error: Work entered not in the list");
        } else {
            workToDo.markComplete(toBeCompleted.getName());
        }
        start();
    }

    //EFFECTS: lets the user delete certain work from the list
    private void deleteWork() {
        String name;
        System.out.println("Enter name of work to be deleted :");
        name = myObj.nextLine();

        boolean contains = false;

        Work toBeRemoved = new Work("name", 10);

        for (Work work : workToDo.getWorks()) {
            if (work.getName().equals(name)) {
                contains = true;
                toBeRemoved = work;
            }
        }

        if (!contains) {
            System.out.println("Error : Work entered is not in the list");
        } else {
            workToDo.deleteWork(toBeRemoved.getName());
        }
        start();
    }

    //EFFECTS: lets the user load work to the list
    private void addWork() {
        String name;
        System.out.println("Enter name of work :");
        name = myObj.nextLine();
        int time;
        System.out.println("Enter time needed for work :");
        time = myObj.nextInt();
        Work w = new Work(name, time);
        workToDo.addWork(w);
        start();
    }


    private void viewWork() {
        for (Work work : workToDo.getWorks()) {
            System.out.println("Name: " + work.getName() + " Time: " + work.getTime()
                    + " Completed?: " + work.isCompleted());
        }
        start();
    }

    private void saveWork() {
        try {
            jsonWriter.open();
            jsonWriter.write(workToDo);
            jsonWriter.close();
            System.out.println("Saved " + workToDo + " to " + JSON_STORE);
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
        }
        start();
    }

    public void loadWork() {
        try {
            workToDo = jsonReader.read();
            System.out.println("Loaded " + workToDo + " from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
        }
        start();
    }

    //EFFECTS: displays the menu for the user
    private void menu() {
        System.out.println("Choose an option");
        System.out.println("Add: To add a work enter");
        System.out.println("Delete: To delete work enter");
        System.out.println("Complete: To mark a work as complete ");
        System.out.println("Check: To see number of incomplete and complete work on list");
        System.out.println("View: To view any work on the To-Do list");
        System.out.println("Save: Save file");
        System.out.println("Load: Load file");
        System.out.println("Exit");

    }


}
