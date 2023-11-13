package substring;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


/**
 * Class for testing substring search.
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
    void actualTest() {
        ArrayList<Long> found = finder.find("actual.txt", "سلطانا", true);
        assertEquals("[10]", found.toString());
    }

    static void createBigFile() {
        try (FileWriter writer = new FileWriter("src/test/resources/Big10GB.txt")) {
            // At the beginning, the size of this string is 8 bytes
            StringBuilder eightMbBuild = new StringBuilder("qWerTYkl");  
            // Then we will increase this size by 20 times. We will get string with 8 Mb size
            int degreeOfTwo = 20;
            while (degreeOfTwo > 0) {
                eightMbBuild.append(eightMbBuild);
                --degreeOfTwo;
            }
            String eightMbStr = eightMbBuild.toString();
            writer.write("qWerTYkl!");
            writer.flush();
            // Write into the file 8 Gb
            for (int i = 0; i < 1024; i++) {
                writer.write(eightMbStr);
                writer.flush();
            }
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
    void bigFileTest() {
        createBigFile();
        ArrayList<Long> found = finder.find("src/test/resources/Big10GB.txt", "!");
        assertEquals("[8]", found.toString());
        deleteBigFile();
    }
}