package substring;


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Class for searching substring.
 */
public class SubStringSearch {
    private String subString;
    private StringBuffer strBuffer = new StringBuffer();
    private BufferedReader bufReader;
    private ArrayList<Long> subStringOccurs = new ArrayList<>();


    private void open(String file, boolean resourcesDir) {
        try {
            InputStream stream;
            if (resourcesDir) {
                stream = getClass().getClassLoader().getResourceAsStream(file);
            } else {
                stream = new FileInputStream(file);
            }

            InputStreamReader inputReader = new InputStreamReader(stream, StandardCharsets.UTF_8);
            bufReader = new BufferedReader(inputReader);  // Maybe need to define Size
        } catch (IOException e) {
            System.err.println(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private boolean takePatch() {
        int charsCounter = 0;
        char[] charsBuffer = new char[10 * 1024 * 1024];
        try {
            charsCounter = bufReader.read(charsBuffer);
            if (charsCounter <= 0) {
                return false;
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
            return false;
        }
        
        if (strBuffer.length() > 0) {
            strBuffer.delete(0, strBuffer.length());
        }
        strBuffer.append(charsBuffer, strBuffer.length(), charsCounter);

        return true;
    }

    private void findPatchOccurs() {
        long readChars = 0;
        int occurIdx = 0;
        String prevPatch = "";
        while (takePatch()) {
            strBuffer.insert(0, prevPatch);

            if (strBuffer.length() < subString.length()) {
                break;
            }

            occurIdx = strBuffer.indexOf(subString, occurIdx);
            while (occurIdx >= 0) {
                subStringOccurs.add(readChars + (long) occurIdx);
                occurIdx = strBuffer.indexOf(subString, occurIdx + 1);
            }

            prevPatch = strBuffer.substring(
                strBuffer.length() - subString.length() + 1, strBuffer.length());
            readChars += strBuffer.length() - prevPatch.length();
        }
    }

    private void close() {
        try {
            bufReader.close();
            bufReader = null;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A function that reads a file in chunks. 
     * And returns an array of indexes of the occurrence of the substring.
     *
     * @param file file.
     *
     * @param findStr findStr.
     */
    public ArrayList<Long> find(String file, String findStr) {
        subStringOccurs.clear();

        ByteBuffer utf8Buffer = StandardCharsets.UTF_8.encode(findStr);
        subString = StandardCharsets.UTF_8.decode(utf8Buffer).toString();

        open(file, false);

        findPatchOccurs();
        close();
        return subStringOccurs;
    }

    /**
     * A function that reads a file or resource (it depends on the parameter) in chunks. 
     * And returns an array of indexes of the occurrence of the substring.
     *
     * @param file file.
     *
     * @param findStr findStr.
     *
     * @param resourcesDir flag of resource. 
     */
    public ArrayList<Long> find(String file, String findStr, boolean resourcesDir) {
        subStringOccurs.clear();

        ByteBuffer utf8Buffer = StandardCharsets.UTF_8.encode(findStr);
        subString = StandardCharsets.UTF_8.decode(utf8Buffer).toString();

        open(file, resourcesDir);

        findPatchOccurs();
        close();
        return subStringOccurs;
    }
}