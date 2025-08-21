import java.util.Scanner;

public class Two {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem2 converter = new Problem2();

        System.out.println("Enter a sentence to convert:");
        String input = scanner.nextLine();
        
        System.out.println("\n--- Case Conversion Report ---");
        converter.convertAndCompare(input);
    }
}

class Problem2 {
    public char toUpper(char ch) {
        if (ch >= 'a' && ch <= 'z') {
            return (char) (ch - 32);
        }
        return ch;
    }
    
    public char toLower(char ch) {
        if (ch >= 'A' && ch <= 'Z') {
            return (char) (ch + 32);
        }
        return ch;
    }

    public String toUpperCase(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toUpper(text.charAt(i)));
        }
        return result.toString();
    }
    
    public String toLowerCase(String text) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            result.append(toLower(text.charAt(i)));
        }
        return result.toString();
    }
    
    public String toTitleCase(String text) {
        StringBuilder result = new StringBuilder();
        boolean newWord = true;
        for (int i = 0; i < text.length(); i++) {
            char ch = text.charAt(i);
            if (ch == ' ') {
                result.append(ch);
                newWord = true;
            } else if (newWord) {
                result.append(toUpper(ch));
                newWord = false;
            } else {
                result.append(toLower(ch));
            }
        }
        return result.toString();
    }
    
    private boolean compare(String manual, String builtIn) {
        return manual.equals(builtIn);
    }

    public void convertAndCompare(String originalText) {
        String upperManual = toUpperCase(originalText);
        String lowerManual = toLowerCase(originalText);
        String titleManual = toTitleCase(originalText);

        String upperBuiltIn = originalText.toUpperCase();
        String lowerBuiltIn = originalText.toLowerCase();

        System.out.println("Original Text: " + originalText);
        System.out.println("\n--- Uppercase ---");
        System.out.println("Manual: " + upperManual);
        System.out.println("Built-in: " + upperBuiltIn);
        System.out.println("Match: " + compare(upperManual, upperBuiltIn));
        
        System.out.println("\n--- Lowercase ---");
        System.out.println("Manual: " + lowerManual);
        System.out.println("Built-in: " + lowerBuiltIn);
        System.out.println("Match: " + compare(lowerManual, lowerBuiltIn));
        
        System.out.println("\n--- Title Case ---");
        System.out.println("Manual: " + titleManual);
        // No built-in title case for direct comparison
        
        System.out.println("\n--- Character ASCII Values ---");
        System.out.printf("%-10s | %-10s | %-10s\n", "Char", "ASCII", "Converted");
        System.out.println("--------------------------------");
        for (char c : originalText.toCharArray()) {
            char converted = toUpper(c);
            System.out.printf("%-10s | %-10d | %-10d\n", c, (int) c, (int) converted);
        }
    }
}
