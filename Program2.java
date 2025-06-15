/*
Program2.java  –  OOAD Project 1  (Part 2 / Program 2)
Author: Andrew Wood
University of Colorado Boulder
2025/6/15

Builds a word-token puzzle from six input words and their six clues:
1. Reads the words and clues, converting words to UPPERCASE.
2. Splits each word into 2-letter chunks (final may be 3) and shuffles.
3. Prints Tokens, Clues, and Answer Key

Compile: javac Program2.java
Run: java Program2
*/

import java.util.*;

// Reads six words followed by six clues from standard input.
class Reader {
    private static final int COUNT = 6;

    // Reads words then clues; returns a pair (String[] words, String[] clues).
    public Pair readAll(Scanner sc) {
        String[] words = new String[COUNT];
        String[] clues = new String[COUNT];

        System.out.println("Enter 6 words (press <Enter> after each):");
        for (int i = 0; i < COUNT; i++) {
            words[i] = sc.nextLine().trim().toUpperCase(Locale.ENGLISH);
            if (words[i].isEmpty()) { i--; continue; }
        }
        System.out.println("\nEnter 6 clues (press <Enter> after each):");
        for (int i = 0; i < COUNT; i++) {
            clues[i] = sc.nextLine().trim();
            if (clues[i].isEmpty()) { i--; continue; }
        }
        return new Pair(words, clues);
    }

    // Simple tuple
    static record Pair(String[] words, String[] clues) {}
}

// Splits words into tokens and shuffles them.
class Tokenizer {

    public List<String> tokenizeAll(String[] words) {
        List<String> tokens = new ArrayList<>();
        for (String w : words) tokens.addAll(tokenize(w));
        Collections.shuffle(tokens);
        return tokens;
    }

    // Tokenize a single word.
    private List<String> tokenize(String word) {
        List<String> t = new ArrayList<>();
        int i = 0, n = word.length();
        while (n - i > 3) {
            t.add(word.substring(i, i + 2));
            i += 2;
        }
        t.add(word.substring(i)); // last 2 or 3 letters
        return t;
    }
}

// Handles output.
class PuzzlePrinter {
    // Prints the three required blocks.
    public void print(List<String> tokens, String[] clues, String[] words) {
        printTokens(tokens);
        printClues(clues);
        printAnswers(words);
    }

    private void printTokens(List<String> tokens) {
        System.out.println("\n=== Tokens ===");
        for (int i = 0; i < tokens.size(); i++) {
            System.out.printf("%-4s", tokens.get(i));
            if ((i + 1) % 4 == 0 || i == tokens.size() - 1) System.out.println();
        }
    }

    private void printClues(String[] clues) {
        System.out.println("\n=== Clues ===");
        for (int i = 0; i < clues.length; i++)
            System.out.printf("%d. %s%n", i + 1, clues[i]);
    }

    private void printAnswers(String[] words) {
        System.out.println("\n=== Answer Key ===");
        for (String w : words) System.out.println(w);
    }
}


// Public driver
public class Program2 {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Reader reader = new Reader();
        Tokenizer tokenizer = new Tokenizer();
        PuzzlePrinter printer = new PuzzlePrinter();

        // 1. input
        Reader.Pair data = reader.readAll(sc);

        // 2. process
        List<String> tokens = tokenizer.tokenizeAll(data.words());

        // 3. output
        printer.print(tokens, data.clues(), data.words());

        sc.close();
    }
}
