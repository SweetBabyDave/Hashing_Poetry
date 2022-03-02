import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Scanner;

public class WritePoetry {

    public String writePoem(String file, String StartWord, int length, boolean printHashtable) {
        StringBuilder sb = new StringBuilder();
        HashTable<String, WordFreqInfo> hashTable = readFile(file);

        if (printHashtable) {
            System.out.println(hashTable.toString(hashTable.size()));
        }

        var currentWord = hashTable.find(StartWord);
        sb.append(StartWord).append(" ");
        // Ask Dean about punctuation at the end of the 20 words if you need only 1 period or if more are okay
        for (int i = 0; i < length - 1; i++) {
            String followWord = currentWord.getFollowWord(currentWord.getOccurCount());
            currentWord = hashTable.find(followWord);

            if (isPunctuation(followWord)) {
                String holder = sb.toString().trim();
                sb.setLength(0);
                sb.append(holder);
                sb.append(followWord).append("\n");
            } else if (isApostrophe(followWord)) {
                sb.append(followWord);
            } else {
                sb.append(followWord).append(" ");
            }
        }

        String holder = sb.toString().trim();
        sb.setLength(0);
        sb.append(holder);
        sb.append(".");

        return sb.toString();
    }

// Ask Dean a question about having the apostrophe in the array like I have it.
    private HashTable<String, WordFreqInfo> readFile(String file) {
        File poemFile = new File(file);
        ArrayList<String> holderArray = new ArrayList<>();
        ArrayList<String> wordArray = new ArrayList<>();

        try (Scanner input = new Scanner(poemFile)) {
            while (input.hasNextLine()) {
                String word = input.nextLine().toLowerCase();
                String[] wordList = word.split("\\b");
                if (wordList.length != 1) {
                    holderArray.addAll(Arrays.asList(wordList));
                }
            }

            holderArray.removeAll(Collections.singleton(" "));
            for (String word: holderArray) {
                word = word.trim();
                wordArray.add(word);
            }

        } catch (java.io.IOException ex) {
            System.out.println("An error occurred trying to read the poem: " + ex);
        }

        return buildHashTable(wordArray);
    }

    private HashTable<String, WordFreqInfo> buildHashTable(ArrayList<String> array) {
        HashTable<String, WordFreqInfo> hashTable = new HashTable<>();

        for (int i = 0; i < array.size() - 1; i++) {
            WordFreqInfo value = new WordFreqInfo(array.get(i), 0);
            for (int j = 0; j < array.size() - 1; j++) {
                if (array.get(i).equals(array.get(j))) {
                    value.updateFollows(array.get(j + 1));
                }
            }
            hashTable.insert(array.get(i), value);
        }

        return hashTable;
    }

    public static boolean isPunctuation(String c) {
        return c.equals(",")
                || c.equals(".")
                || c.equals("!")
                || c.equals("?");
    }

    public static boolean isApostrophe(String c) {
        return c.equals("'");
    }
}
