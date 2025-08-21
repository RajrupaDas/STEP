import java.util.Scanner;

public class One {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem1 spellChecker = new Problem1();
        String dictionary[] = {"hello", "world", "programming", "java", "code", "learn"};
        spellChecker.setDictionary(dictionary);

        System.out.println("Enter a sentence to spell check:");
        String sentence = scanner.nextLine();
        
        System.out.println("\n--- Spell Checker Report ---");
        spellChecker.processSentence(sentence);
    }
}

class Problem1 {
    private String[] dictionary;

    public void setDictionary(String[] dictionary) {
        this.dictionary = dictionary;
    }

    private String[] splitSentence(String sentence) {
        String[] words = new String[50];
        int wordCount = 0;
        int start = 0;
        for (int i = 0; i < sentence.length(); i++) {
            char c = sentence.charAt(i);
            if (c == ' ' || c == ',' || c == '.' || c == '!' || c == '?') {
                if (i > start) {
                    words[wordCount++] = sentence.substring(start, i);
                }
                start = i + 1;
            }
        }
        if (start < sentence.length()) {
            words[wordCount++] = sentence.substring(start);
        }
        String[] result = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            result[i] = words[i];
        }
        return result;
    }

    private int calculateStringDistance(String s1, String s2) {
        int len1 = s1.length();
        int len2 = s2.length();
        int diff = 0;

        if (len1 == len2) {
            for (int i = 0; i < len1; i++) {
                if (s1.charAt(i) != s2.charAt(i)) {
                    diff++;
                }
            }
        } else {
            diff = Math.abs(len1 - len2);
        }
        return diff;
    }

    private String findClosestMatch(String word) {
        String suggestion = "---";
        int minDistance = 3;
        for (String dictWord : dictionary) {
            int distance = calculateStringDistance(word, dictWord);
            if (distance < minDistance) {
                minDistance = distance;
                suggestion = dictWord;
            }
        }
        return (minDistance <= 2) ? suggestion : "---";
    }

    public void displayResults(String originalWord, String suggestedWord, int distance, String status) {
        System.out.printf("%-20s | %-20s | %-10s | %-10s\n", originalWord, suggestedWord, distance, status);
    }

    public void processSentence(String sentence) {
        String[] words = splitSentence(sentence);
        System.out.println("-----------------------------------------------------");
        System.out.printf("%-20s | %-20s | %-10s | %-10s\n", "Original Word", "Correction", "Distance", "Status");
        System.out.println("-----------------------------------------------------");

        for (String word : words) {
            if (word.length() == 0) continue;
            
            String normalizedWord = word.replaceAll("[.,?!;]", "").toLowerCase();
            String suggestion = findClosestMatch(normalizedWord);
            
            if (suggestion.equals(normalizedWord)) {
                displayResults(word, "---", 0, "Correct");
            } else if (!suggestion.equals("---")) {
                int distance = calculateStringDistance(normalizedWord, suggestion);
                displayResults(word, suggestion, distance, "Misspelled");
            } else {
                displayResults(word, "---", 99, "Misspelled");
            }
        }
        System.out.println("-----------------------------------------------------");
    }
}
