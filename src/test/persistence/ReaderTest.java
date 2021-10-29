package persistence;

import model.Work;
import model.WorkToDo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ReaderTest extends JsonTest{

    @Test
    void testReaderNonExistentFile() {
        Reader reader = new Reader("./data/noSuchFile.json");
        try {
            WorkToDo wtd = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyWorkRoom() {
        Reader reader = new Reader("./data/testReaderEmpty.json");
        try {
            WorkToDo wtd = reader.read();
            assertEquals(0, wtd.sizeOfList());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralWorkRoom() {
        Reader reader = new Reader("./data/testReaderGeneral.json");
        try {
            WorkToDo wr = reader.read();
            List<Work> wl = wr.getWorks();
            assertEquals(2, wl.size());
            checkWork("a", 9, wl.get(0));
            assertTrue(wl.get(0).isCompleted());
            checkWork("b", 1, wl.get(1));
            assertFalse(wl.get(1).isCompleted());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}

