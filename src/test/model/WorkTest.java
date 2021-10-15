package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class WorkTest {

    Work physics;
    Work chemistry;
    Work maths;


    @BeforeEach

    public void runBefore() {
        physics = new Work("physics", 3);
        maths = new Work("maths", 2);
        chemistry = new Work("chemistry", 1);

    }

    @Test

    public void testGetTime() {
        physics.getTime();
        chemistry.getTime();
        maths.getTime();
        assertEquals(3, physics.getTime());
        assertEquals(2, maths.getTime());
        assertEquals(1, chemistry.getTime());
    }

}
