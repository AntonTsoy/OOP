package network;

import java.util.ArrayList;
import java.util.List;


/**
 * Класс для парсинга и обработки строк/коллекций с числами.
 */
public class Parser {

    public static int optimalTaskLength = 6;
    

    /**
     * Из строки с числами получаем список чисел.
     *
     * @param numsString строка чисел.
     * @return коллекция чисел.
     */
    public static List<Integer> parseStrToIntegerList(String numsString) {
        String[] numStrings = numsString.split(" ");
        List<Integer> integerList = new ArrayList<Integer>();
        for (int i = 0; i < numStrings.length; i++) {
            integerList.add(Integer.parseInt(numStrings[i]));
        }
        return integerList;
    }

    /**
     * Из списка числе делает список строк этих чисел, разделенных пробелами.
     *
     * @param numbers коллекция чисел.
     * @return список строк. Мы делим колеекцию чисел.
     */
    public static List<String> makeTaskListFromIntegerList(List<Integer> numbers) {
        List<String> taskList = new ArrayList<>();
        int chunksNumber = numbers.size() / optimalTaskLength;
        int tailSize = numbers.size() % optimalTaskLength;
        int startId = 0;
        if (tailSize >= chunksNumber) {
            while (startId < chunksNumber * optimalTaskLength) {
                taskList.add(makeTask(numbers.subList(startId, startId + optimalTaskLength)));
                startId += optimalTaskLength;
            }
            taskList.add(makeTask(numbers.subList(startId, numbers.size())));
        } else {
            for (int i = 0; i < tailSize; i++) {
                taskList.add(makeTask(numbers.subList(startId, startId + optimalTaskLength + 1)));
                startId += optimalTaskLength + 1;
            }
            while (taskList.size() < chunksNumber) {
                taskList.add(makeTask(numbers.subList(startId, startId + optimalTaskLength)));
                startId += optimalTaskLength;
            }
        }
        return taskList;
    }

    private static String makeTask(List<Integer> subIntegerList) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < subIntegerList.size(); i++) {
            if (i == subIntegerList.size() - 1) {
                result.append(subIntegerList.get(i));
            } else {
                result.append(subIntegerList.get(i));
                result.append(" ");
            }
        }
        return new String(result);
    }
}
