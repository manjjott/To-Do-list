package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WorkToDoTest {

    WorkToDo lists;
    Work physics;
    Work maths;
    Work chemistry;


    @BeforeEach

    public void runBefore() {
        physics = new Work("physics", 2);
        maths = new Work("maths", 3);
        chemistry = new Work("chemistry", 2);

        lists = new WorkToDo();
    }

    @Test

    public void testAddWork() {

        lists.addWork(physics);
        lists.addWork(chemistry);

        assertEquals(2, lists.sizeOfList());
    }

    @Test
    public void testDeleteWork() {
        lists.addWork(physics);
        lists.addWork(maths);
        lists.addWork(chemistry);
        assertEquals(3, lists.sizeOfList());
        lists.deleteWork("physics");
        assertEquals(2, lists.sizeOfList());
    }

    @Test

    public void testMarkComplete() {
        lists.addWork(physics);
        lists.addWork(chemistry);
        lists.addWork(maths);
        lists.markComplete("chemistry");

        assertTrue(lists.getWork(1).isCompleted());
    }

    @Test

    public void testGetWork() {
        lists.addWork(physics);
        lists.addWork(maths);
        lists.addWork(chemistry);
        assertEquals(3, lists.sizeOfList());
        assertEquals(chemistry, lists.getWork(2));
    }


    @Test

    public void testGetWorks() {
        lists.addWork(physics);
        lists.addWork(chemistry);
        lists.addWork(maths);
       // lists.getWorks();
        assertEquals(3, lists.getWorks().size());
    }

}












