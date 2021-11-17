package ui;

import java.io.FileNotFoundException;


// runs the WorkToDoTest
public class Main {
    public static void main(String[] args) {
        try {
            new ToDoApplication();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: no file found");
        }
    }
}
