package persistence;

import model.Work;
import model.WorkToDo;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class WriterTest extends JsonTest{

    @Test
    void testWriterInvalidFile() {
        try {
            WorkToDo wtd = new WorkToDo();
            Writer writer = new Writer("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyWorkroom() {
        try {
            WorkToDo wr = new WorkToDo();
            Writer writer = new Writer("./data/testWriterEmpty.json");
            writer.open();
            writer.write(wr);
            writer.close();

            Reader reader = new Reader("./data/testWriterEmpty.json");
            wr = reader.read();
            assertEquals(0, wr.sizeOfList());
        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralWorkroom() {
        try {
            WorkToDo wr = new WorkToDo();
            wr.addWork(new Work("ghussa", 100));
            wr.addWork(new Work("fudu", 69));
            Writer writer = new Writer("./data/testWriterGeneral.json");
            writer.open();
            writer.write(wr);
            writer.close();

            Reader reader = new Reader("./data/testWriterGeneral.json");
            wr = reader.read();
            List<Work> wl = wr.getWorks();
            assertEquals(2, wl.size());
            checkWork("ghussa", 100, wl.get(0));
            assertFalse(wl.get(0).isCompleted());
            checkWork("fudu", 69, wl.get(1));
            wl.get(1).completeWork();
            assertTrue(wl.get(1).isCompleted());

        } catch (IOException e) {
            fail("Exception should not have been thrown");
        }
    }
}
