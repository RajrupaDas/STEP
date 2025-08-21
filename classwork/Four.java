import java.util.Scanner;

class Four {
    public static int findLength(String text) {
        int count = 0;
        try {
            while (true) {
                text.charAt(count++);
            }
        } catch (StringIndexOutOfBoundsException e) {
            return count - 1;
        }
    }

    public static String[] splitText(String text) {
        int wordCount = 0;
        boolean inWord = false;
        for (int i = 0; i < findLength(text); i++) {
            if (text.charAt(i) != ' ' && !inWord) {
                wordCount++;
                inWord = true;
            } else if (text.charAt(i) == ' ') {
                inWord = false;
            }
        }

        String[] words = new String[wordCount];
        int wordIndex = 0;
        int start = 0;
        for (int i = 0; i < findLength(text); i++) {
            if (text.charAt(i) == ' ') {
                if (i > start) {
                    words[wordIndex] = text.substring(start, i);
                    wordIndex++;
                }
                start = i + 1;
            }
        }
        if (start < findLength(text)) {
            words[wordIndex] = text.substring(start, findLength(text));
        }
        return words;
    }

    public static String[][] create2DArray(String[] words) {
        String[][] wordsWithLengths = new String[words.length][2];
        for (int i = 0; i < words.length; i++) {
            wordsWithLengths[i][0] = words[i];
            wordsWithLengths[i][1] = String.valueOf(findLength(words[i]));
        }
        return wordsWithLengths;
    }

    public static int[] findShortestAndLongest(String[][] wordsWithLengths) {
        int shortestLength = 999999;
        int longestLength = -1;
        
        for (String[] row : wordsWithLengths) {
            int currentLength = Integer.parseInt(row[1]);
            if (currentLength < shortestLength) {
                shortestLength = currentLength;
            }
            if (currentLength > longestLength) {
                longestLength = currentLength;
            }
        }
        
        return new int[]{shortestLength, longestLength};
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a sentence:");
        String text = scanner.nextLine();

        String[] words = splitText(text);
        String[][] result = create2DArray(words);
        int[] lengths = findShortestAndLongest(result);

        int shortestLength = lengths[0];
        int longestLength = lengths[1];
        String shortestWord = "";
        String longestWord = "";

        for (String[] row : result) {
            int currentLength = Integer.parseInt(row[1]);
            if (currentLength == shortestLength) {
                shortestWord = row[0];
            }
            if (currentLength == longestLength) {
                longestWord = row[0];
            }
        }

        System.out.println("Shortest word: " + shortestWord + " (Length: " + shortestLength + ")");
        System.out.println("Longest word: " + longestWord + " (Length: " + longestLength + ")");
    }
}

