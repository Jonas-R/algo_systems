package utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class FileUtils {

    /**
     * Splits a file into lines and returns them as an ArrayList.
     *
     * @param path The path of the file to be read
     * @return A list containing the lines of the file
     */
    public static List<String> readLines(String path) {
        ArrayList<String> lines = new ArrayList<>();

        //open file
        File file = new File(path);

        //Error if not readable
        if (!file.canRead()) {
            System.err.println("File " + file.getAbsolutePath() + " could not be read!");
            System.exit(1);
        }

        //Return lines
        try {
            BufferedReader inputStream;
            inputStream = new BufferedReader(new FileReader(file));
            String line;
            while ((line = inputStream.readLine()) != null) {
                lines.add(line);
            }

            inputStream.close();
        } catch (FileNotFoundException ex) {
            System.err.println(file.getAbsolutePath() + " not found!");
            System.exit(1);
        } catch (IOException ex) {
            System.err.println(ex);
            System.exit(1);
        }

        return lines;
    }
    
    public static String readFileAsString(String path) {
    	StringBuilder output = new StringBuilder();
    	for (String line : readLines(path)) {
    		output.append(line);
    	}
    	return output.toString();
    }

    /**
     * Same as readLines except as a LinkedList (useful if .addFirst() is
     * needed).
     *
     * @param path see readLines
     * @return see readLines
     */
    public static LinkedList<String> readLinesAsLinkedList(String path) {
        return new LinkedList<>(readLines(path));
    }

    /**
     * Writes a String to a file. Overwrites previous contents of the file!
     *
     * @param path Path of the file to write to.
     * @param content The String to write to the file.
     */
    public static void writeString(String path, String content) {
        //Open file
        Path jPath = Paths.get(path);

        //Create file if necessary
        if (!Files.exists(jPath, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createFile(jPath);
            } catch (IOException ex) {
                System.out.print(ex);
                System.exit(1);
            }
        }

        //Error if not writable
        if (!Files.isWritable(jPath)) {
            System.out.println("File " + jPath + " could not be written!");
            System.exit(1);
        }
        //Write lines
        try {
            Files.write(jPath, content.getBytes(Charset.forName("UTF-8")));
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }

    /**
     * Appends a String to a file.
     *
     * @param path Path of the file to write to.
     * @param content The String to write to the file.
     */
    public static void appendString(String path, String content) {
        //Open file
        Path jPath = Paths.get(path);

        //Create file if necessary
        if (!Files.exists(jPath, LinkOption.NOFOLLOW_LINKS)) {
            try {
                Files.createFile(jPath);
            } catch (IOException ex) {
                System.out.print(ex);
                System.exit(1);
            }
        }

        //Error if not writable
        if (!Files.isWritable(jPath)) {
            System.out.println("File " + jPath + " could not be written!");
            System.exit(1);
        }
        //Write lines
        try {
            FileWriter fw = new FileWriter(path, true);
            fw.write(content);//appends the string to the file
            fw.close();
        } catch (IOException ex) {
            System.out.println(ex);
        }
    }
    
    public static void deleteFile(String filename) {
        try {
            if (Files.exists(Paths.get(filename))) {
                Files.delete(Paths.get(filename));
            }
        } catch (IOException ex) {
            System.err.println("File " + filename + " could not be deleted!");
        }
    }
}
