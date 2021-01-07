package com.exalt.assignment;

import java.io.File;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * This is a main class that reads all files in a directory provided as a command line argument
 * and count the occurrences of lower-case latin characters [a-z] and print them in console.
 *
 * @author Malak
 */
public class CharsCount {
    private static final String INCORRECT_DIRECTORY_PATH_MESSAGE = "Please provide a correct directory path";
    private static final HashMap<Character, Long> allCharsCountMap = new HashMap<>();
    private static ExecutorService executorService;

    public static void main(String[] args) throws Exception {
        //Exit application if no directory path is passed.
        if (args.length == 0) {
            System.err.print(INCORRECT_DIRECTORY_PATH_MESSAGE);
            System.exit(0);
        }

        File file = new File(args[0]);

        //Exit application if the provided argument is a file path not directory.
        if (file == null || file.isFile()) {
            System.err.print(INCORRECT_DIRECTORY_PATH_MESSAGE);
            System.exit(0);
        }

        executorService = Executors.newFixedThreadPool(10);
        listFiles(file);

        executorService.shutdown();
        executorService.awaitTermination(1, TimeUnit.HOURS);

        printCharsCountResult();
    }

    /**
     * This method iterates over a directory and execute a thread for each directory file
     * to count chars occurrences.
     *
     * @param directory base or sub-directory
     */
    private static void listFiles(File directory) {
        if (directory != null) {
            for (File fileEntry : directory.listFiles()) {
                if (fileEntry.isDirectory()) {
                    listFiles(fileEntry);
                } else {
                    executorService.execute(new FileCharsCount(fileEntry.getAbsolutePath(), allCharsCountMap));
                }
            }
        }
    }

    /**
     * This method prints directory files lower-case chars occurrences on console.
     */
    private static void printCharsCountResult() {
        for (int i = 97; i <= 122; i++) {
            Character character = (char) (i);
            Long value = allCharsCountMap.get(character);
            System.out.print(character + "\t \t" + (value == null ? 0 : value) + "\n");
        }
    }
}
