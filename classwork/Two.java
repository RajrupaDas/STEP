import java.util.Scanner;

class Two {
    public static int getStringLength(String text) {
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
        for (int i = 0; i < getStringLength(text); i++) {
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
        for (int i = 0; i < getStringLength(text); i++) {
            if (text.charAt(i) == ' ') {
                if (i > start) {
                    words[wordIndex] = text.substring(start, i);
                    wordIndex++;
                }
                start = i + 1;
            }
        }
        if (start < getStringLength(text)) {
            words[wordIndex] = text.substring(start, getStringLength(text));
        }
        return words;
    }

    public static boolean compareStringArrays(String[] arr1, String[] arr2) {
        if (arr1.length != arr2.length) {
            return false;
        }
        for (int i = 0; i < arr1.length; i++) {
            if (!arr1[i].equals(arr2[i])) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter a sentence:");
        String text = scanner.nextLine();

        String[] customSplit = splitText(text);
        String[] builtInSplit = text.split(" ");

        System.out.print("Custom split result: ");
        for (String word : customSplit) {
            System.out.print("[" + word + "] ");
        }
        System.out.println();
        System.out.print("Built-in split result: ");
        for (String word : builtInSplit) {
            System.out.print("[" + word + "] ");
        }
        System.out.println();

        System.out.println("Are the results the same? " + compareStringArrays(customSplit, builtInSplit));
    }
}

