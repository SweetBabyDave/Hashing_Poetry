import java.io.File;
import java.util.Scanner;

public class WritePoetry {

    public String writePoem(String file, String StartWord, int length, boolean printHashtable) {
        StringBuilder sb = new StringBuilder();
        sb = readFile(file);

        if (printHashtable) {
            // Print out hashtable here
            System.out.println();
        }

        return sb.toString();
    }

    private StringBuilder readFile(String file) {
        File poemFile = new File(file);
        StringBuilder sb = new StringBuilder();

        try (Scanner input = new Scanner(poemFile)) {
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                sb.append(word);
            }
        } catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the poem: " + ex);
        }

        return sb;
    }

    private void buildHashTable() {

    }
}
