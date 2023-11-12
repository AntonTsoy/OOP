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
        ArrayList<Long> found = finder.find("actual.txt", "سلطانا", true);
        assertEquals("[10]", found.toString());
    }

    static void createBigFile() {
        try (FileWriter writer = new FileWriter("src/test/resources/Big10GB.txt")) {
            StringBuilder eightMbBuild = new StringBuilder("qWerTYkl");
            for (int i = 1024 * 1024; i > 1; i /= 2) {
                eightMbBuild.append(eightMbBuild);
            }
            String eightMbStr = eightMbBuild.toString();
            writer.write("qWerTYkl!");
            writer.flush();
            for (int i = 0; i < 1024; ++i) {
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
    void BigFileTest() {
        createBigFile();
        ArrayList<Long> found = finder.find("src/test/resources/Big10GB.txt", "v");
        assertEquals("[8]", found.toString());
        deleteBigFile();
    }
}