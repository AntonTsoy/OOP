package substring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Test class for substring searching.
 */
public class TestSubStringSearch {
    SubStringSearch finder;

    @BeforeEach
    void setUp() {
        finder = new SubStringSearch();
    }

    @Test
    void smallTest() {
        ArrayList<Long> found = finder.find("small.txt", "Z", true);
        assertEquals("[1]", found.toString());
    }

    @Test
    void middleTest() {
        ArrayList<Long> found = finder.find("src/test/resources/middle.txt", "bo");
        assertEquals("[14, 39]", found.toString());
    }

    @Test 
    void frenchTest() {
        ArrayList<Long> found = finder.find("french.txt", "飘", true);
        assertEquals("[25]", found.toString());
    }
/* 
    static void createBigFile() {
        try (FileWriter writer = new FileWriter("src/test/resources/Big10GB.txt")) {
            int twoMbSize = 1048576 * 2;
            StringBuilder twoMbString = new StringBuilder("b");
            for (int i = twoMbSize; i > 1; i /= 2) {
                twoMbString.append(twoMbString);
            }
            String twoMbBs = twoMbString.toString();
            twoMbString.replace(1048575, 1048577, "aa");
            writer.write(twoMbString.toString());
            writer.flush();
            for (int i = 0; i < 8190; ++i) {
                writer.write(twoMbBs);
                writer.flush();
            }
            writer.write(twoMbString.toString());
            writer.flush();
        } catch (Exception e) {
            System.err.println("Problems with opening Big10GB.txt");
            throw new RuntimeException(e);
        }
    }

    static void deleteBigFile() {
        try {
            Files.delete(Paths.get("src/test/resources/Big10GB.txt"));
        } catch (IOException e) {
            System.err.println("Problems with closing Big10GB.txt");
            throw new RuntimeException(e);
        }
    }

    @Test
    void BigFileTest() {
        createBigFile();
        ArrayList<Long> predictedList = new ArrayList<>();
        predictedList.add((long) 1048575);
        predictedList.add((long) 16 * 1024 * 1024 * 1024 - 1048577);
        assertEquals(predictedList, finder.find("src/test/resources/Big10GB.txt", "v"));
        deleteBigFile();
    }

    @Test 
    void BigResourceTest() {

    }
*/
}