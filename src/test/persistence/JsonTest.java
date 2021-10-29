package persistence;

import model.Work;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class JsonTest {
    protected void checkWork(String name, int time, Work w) {
        assertEquals(name, w.getName());
        assertEquals(time, w.getTime());
    }
}
