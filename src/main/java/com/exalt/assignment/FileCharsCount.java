package com.exalt.assignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

/**
 * Runnable class that iterates over file content char by char
 * and count the number of occurrences of lower-case characters.
 *
 * @author Malak
 */
public class FileCharsCount implements Runnable {
    private final String filePath;
    private final HashMap<Character, Long> allCharsCountMap;

    public FileCharsCount(String filePath, HashMap<Character, Long> allCharsCountMap) {
        this.filePath = filePath;
        this.allCharsCountMap = allCharsCountMap;
    }

    @Override
    public void run() {
        HashMap<Character, Long> charsCountMap = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            int character;
            while ((character = br.read()) != -1) {
                if (character >= 'a' && character <= 'z') {
                    Long value = charsCountMap.get((char) character);
                    charsCountMap.put((char) character, value == null ? 1 : value + 1);
                }
            }
            synchronized (allCharsCountMap) {
                charsCountMap.keySet().forEach(key -> {
                    Long valueAll = allCharsCountMap.get(key);
                    Long value = charsCountMap.get(key);
                    allCharsCountMap.put(key, valueAll == null ? value : value + valueAll);
                });
            }
        } catch (IOException e) {
            System.err.print("Failed to read file: " + filePath);
        }
    }
}
