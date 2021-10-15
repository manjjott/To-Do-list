package ui;

import model.Work;
import model.WorkToDo;

import java.util.Scanner;


public class ToDoApplication {

    private WorkToDo workToDo;
    Scanner myObj = new Scanner(System.in);


    public ToDoApplication() {
        workToDo = new WorkToDo();
        welcome();
        start();
    }

    private void welcome() {
        System.out.println("Welcome! to work manager TO-DO Application");
    }

    private void start() {
        menu();
        myObj = new Scanner(System.in);
        String decision = myObj.nextLine();
        whatToDo(decision);
    }

    private void whatToDo(String decision) {
        if (decision.equals("Add")) {
            addWork();
        } else if (decision.equals("Delete")) {
            deleteWork();
        } else if (decision.equals("Complete")) {
            completeWork();
        } else if (decision.equals("Check")) {
            checkWork();
        }

    }


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
    }

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

    private void menu() {
        System.out.println("Choose an option");
        System.out.println("To add a work enter : Add");
        System.out.println("To view To-Do enter : View");
        System.out.println("To delete work enter : Delete");
        System.out.println("To mark a work as complete : Complete ");
        System.out.println("To see number of incomplete and complete work on list : Check");

    }


}