import java.util.Scanner;

public class Six {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem6 formatter = new Problem6();

        System.out.println("Enter the text to format:");
        String text = scanner.nextLine();
        System.out.println("Enter the desired line width:");
        int width = scanner.nextInt();
        
        System.out.println("\n--- Text Formatter Report ---");
        formatter.formatAndAnalyze(text, width);
    }
}

class Problem6 {

    private String[] splitWords(String text) {
        String[] words = new String[text.length()];
        int wordCount = 0;
        int start = 0;
        
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == ' ' || i == text.length() - 1) {
                String word;
                if (i == text.length() - 1) {
                    word = text.substring(start);
                } else {
                    word = text.substring(start, i);
                }
                if (word.length() > 0) {
                    words[wordCount++] = word;
                }
                start = i + 1;
            }
        }
        
        String[] result = new String[wordCount];
        for (int i = 0; i < wordCount; i++) {
            result[i] = words[i];
        }
        return result;
    }
    
    public String justify(String text, int width) {
        String[] words = splitWords(text);
        if (words.length == 0) return "";
        
        StringBuilder justified = new StringBuilder();
        int wordIndex = 0;
        
        while (wordIndex < words.length) {
            StringBuilder line = new StringBuilder();
            int lineLength = 0;
            int wordsInLine = 0;
            
            while (wordIndex < words.length) {
                int nextWordLength = words[wordIndex].length();
                if (lineLength + (wordsInLine > 0 ? 1 : 0) + nextWordLength <= width) {
                    lineLength += (wordsInLine > 0 ? 1 : 0) + nextWordLength;
                    wordsInLine++;
                    wordIndex++;
                } else {
                    break;
                }
            }
            
            int spacesNeeded = width - (lineLength - (wordsInLine - 1));
            int gaps = wordsInLine - 1;
            
            for (int i = 0; i < wordsInLine; i++) {
                line.append(words[wordIndex - wordsInLine + i]);
                if (i < gaps) {
                    int spacesToAdd = spacesNeeded / gaps;
                    if (i < spacesNeeded % gaps) {
                        spacesToAdd++;
                    }
                    line.append(" ".repeat(spacesToAdd));
                }
            }
            
            if (wordIndex == words.length) {
                justified.append(line.toString().trim());
            } else {
                justified.append(line.toString());
            }
            justified.append("\n");
        }
        return justified.toString();
    }
    
    public String centerAlign(String text, int width) {
        String[] words = splitWords(text);
        if (words.length == 0) return "";

        StringBuilder centered = new StringBuilder();
        StringBuilder currentLine = new StringBuilder();
        int lineLength = 0;

        for (int i = 0; i < words.length; i++) {
            if (lineLength + words[i].length() + (lineLength > 0 ? 1 : 0) <= width) {
                if (lineLength > 0) {
                    currentLine.append(" ");
                }
                currentLine.append(words[i]);
                lineLength += words[i].length() + (lineLength > 0 ? 1 : 0);
            } else {
                int padding = (width - currentLine.length()) / 2;
                centered.append(" ".repeat(padding)).append(currentLine).append("\n");
                currentLine.setLength(0);
                lineLength = words[i].length();
                currentLine.append(words[i]);
            }
        }
        if (currentLine.length() > 0) {
            int padding = (width - currentLine.length()) / 2;
            centered.append(" ".repeat(padding)).append(currentLine).append("\n");
        }
        return centered.toString();
    }
    
    public void formatAndAnalyze(String text, int width) {
        System.out.println("Original Text:\n" + text);
        System.out.println("\n------------------------------------");
        System.out.println("Left-Justified Text (Width: " + width + ")\n");
        System.out.println(justify(text, width));
        
        System.out.println("\n------------------------------------");
        System.out.println("Center-Aligned Text (Width: " + width + ")\n");
        System.out.println(centerAlign(text, width));
        
        System.out.println("\n------------------------------------");
        System.out.println("Performance Comparison");
        
        long start, end;
        
        // StringBuilder performance
        start = System.nanoTime();
        justify(text, width);
        end = System.nanoTime();
        long sbTime = end - start;
        
        // String concatenation performance (simplified for example)
        start = System.nanoTime();
        String result = "";
        String[] words = splitWords(text);
        String currentLine = "";
        for (String word : words) {
            if (currentLine.length() + word.length() + (currentLine.length() > 0 ? 1 : 0) <= width) {
                if (currentLine.length() > 0) currentLine += " ";
                currentLine += word;
            } else {
                result += currentLine + "\n";
                currentLine = word;
            }
        }
        if (currentLine.length() > 0) result += currentLine + "\n";
        end = System.nanoTime();
        long stringTime = end - start;
        
        System.out.printf("StringBuilder time: %d ns\n", sbTime);
        System.out.printf("String (+) time:    %d ns\n", stringTime);
        System.out.println("StringBuilder is faster for this task due to less object creation.");
    }
}
