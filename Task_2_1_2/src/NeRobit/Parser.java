package network;


import java.util.ArrayList;
import java.util.List;

public class Parser {
    
    public static List<Integer> parseStrToIntegerList(String numsString) {
        String[] numStrings = numsString.split(" ");
        List<Integer> integerList = new ArrayList<Integer>();
        for (int i = 0; i < numStrings.length; i++) {
            integerList.add(Integer.parseInt(numStrings[i]));
        }
        return integerList;
    }

    
}
