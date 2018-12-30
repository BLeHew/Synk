package tableobjects;

import org.junit.jupiter.api.Test;

import java.util.logging.Logger;

import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    private static final Logger log = Logger.getLogger(TaskTest.class.getName());
    Task task = new Task();

    @Test
    void verifyDefaultTaskPercentage() {
        assertEquals("0",task.getPctComplete());
    }
    @Test
    void emptyStringForPercentage() {
        task.setPctComplete("");
        verifyDefaultTaskPercentage();
    }
    @Test
    void nullStringInPercentage(){
        String s = null;
        assertThrows(NullPointerException.class, () -> {
           task.setPctComplete(s);
        });
    }
    @Test
    void checkIfPassedStringIsTooBigForPercentage(){
        task.setPctComplete("1234");
        verifyDefaultTaskPercentage();
    }
    @Test
    void checkNegativePercentage(){
        task.setPctComplete("-1");
        verifyDefaultTaskPercentage();
    }
}