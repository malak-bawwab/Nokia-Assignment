package com.exalt.assignment;

import org.junit.*;

import java.io.*;

/**
 * JUnit tests to verify logic implemented to count chars occurrences
 * in all directory files.
 */
public class CharsCounterTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private ClassLoader loader = CharsCounterTest.class.getClassLoader();

    /**
     * This setup reassign the standard output stream.
     */
    @Before
    public void setup() {
        System.setOut(new PrintStream(outContent));
    }

    /**
     * This test verifies the occurrences of lower-case latin chars in latin folder(in resources)
     * by comparing the actual,expected console output,latin folder contains 5 testcase
     * files and 2 subfolder(one of them has another subfolder inside it). Each subfolder
     * also contains 5 testcase files.The content of each testcase file is a string "abcd"
     * written 3 times across lines.
     *
     * @throws Exception
     */
    @Test
    public void testCountLowerCaseChars() throws Exception {
        String[] arguments = {loader.getResource("latin").getPath()};
        CharsCounterMain.main(arguments);

        StringBuilder expectedOutput = new StringBuilder();
        expectedOutput.append('a' + "\t \t" + "60" + "\n");
        expectedOutput.append('b' + "\t \t" + "60" + "\n");
        expectedOutput.append('c' + "\t \t" + "60" + "\n");
        expectedOutput.append('d' + "\t \t" + "60" + "\n");

        for (int i = 101; i <= 122; i++) {
            Character character = (char) (i);
            expectedOutput.append(character).append("\t \t").append("0").append("\n");
        }
        Assert.assertEquals(expectedOutput.toString(), outContent.toString());
    }

    /**
     * This test verifies the occurrences of Non_latin chars in the non_latin folder(in resources)
     * by comparing the actual, expected condole output, non_latin folder contains 5 testcase files
     * and 2 subfolder(one of them has another subfolder inside it).Each subfolder also contain 5
     * testcase files.The content of testcase file is non-latin characters written across multiple lines.
     *
     * @throws Exception
     */
    @Test
    public void testIgnoringCountNonLatinChars() throws Exception {
        String[] arguments = {loader.getResource("non_latin").getPath()};
        CharsCounterMain.main(arguments);

        StringBuilder expectedOutput = new StringBuilder();
        for (int i = 97; i <= 122; i++) {
            Character character = (char) (i);
            expectedOutput.append(character).append("\t \t").append("0").append("\n");
        }
        Assert.assertEquals(expectedOutput.toString(), outContent.toString());
    }

    /**
     * This test verifies that if a folder path is not passed
     * then unchecked exception is thrown.
     *
     * @throws Exception
     */
    @Test(expected = RuntimeException.class)
    public void testInvalidFolderPath() throws Exception {
        String[] arguments = {loader.getResource("/fake").getPath()};
        CharsCounterMain.main(arguments);
    }

    @After
    public void cleanUpStreams() {
        System.setOut(null);
    }
}


