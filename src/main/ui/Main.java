package ui;

import java.io.FileNotFoundException;
import java.nio.file.FileAlreadyExistsException;

// runs the WorkToDoTest
public class Main {
    public static void main(String[] args) {
        try {
            new ToDoApplication();
        } catch (FileNotFoundException e) {
            System.out.println("Unable to run application: file not found");
        }
    }
}
