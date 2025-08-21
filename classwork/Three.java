import java.util.Scanner;

class Three {
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

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a sentence:");
        String text = scanner.nextLine();

        String[] words = splitText(text);
        String[][] result = create2DArray(words);

        System.out.println("Word\t\tLength");
        System.out.println("--------------------");
        for (String[] row : result) {
            System.out.println(row[0] + "\t\t" + Integer.parseInt(row[1]));
        }
    }
}

