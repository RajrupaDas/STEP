import java.util.Scanner;

public class Three {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Problem3 textCompressor = new Problem3();
        
        System.out.println("Enter text to compress:");
        String text = scanner.nextLine();
        
        System.out.println("\n--- Text Compression Report ---");
        textCompressor.compressAndDecompress(text);
    }
}

class Problem3 {
    private char[] uniqueChars = new char[256];
    private int[] frequencies = new int[256];
    private int uniqueCount = 0;

    private void countFrequencies(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            int index = -1;
            for (int j = 0; j < uniqueCount; j++) {
                if (uniqueChars[j] == c) {
                    index = j;
                    break;
                }
            }
            if (index == -1) {
                if (uniqueCount < 256) {
                    uniqueChars[uniqueCount] = c;
                    frequencies[uniqueCount] = 1;
                    uniqueCount++;
                }
            } else {
                frequencies[index]++;
            }
        }
    }

    private String[][] createCompressionCodes() {
        String[][] mapping = new String[uniqueCount][2];
        for (int i = 0; i < uniqueCount - 1; i++) {
            for (int j = i + 1; j < uniqueCount; j++) {
                if (frequencies[i] < frequencies[j]) {
                    int tempFreq = frequencies[i];
                    frequencies[i] = frequencies[j];
                    frequencies[j] = tempFreq;
                    char tempChar = uniqueChars[i];
                    uniqueChars[i] = uniqueChars[j];
                    uniqueChars[j] = tempChar;
                }
            }
        }
        
        for (int i = 0; i < uniqueCount; i++) {
            mapping[i][0] = String.valueOf(uniqueChars[i]);
            mapping[i][1] = String.valueOf(i);
        }
        return mapping;
    }

    private String compressText(String text, String[][] mapping) {
        StringBuilder compressed = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            for (String[] map : mapping) {
                if (map[0].charAt(0) == c) {
                    compressed.append(map[1]);
                    compressed.append(" ");
                    break;
                }
            }
        }
        return compressed.toString().trim();
    }

    private String decompressText(String compressed, String[][] mapping) {
        StringBuilder decompressed = new StringBuilder();
        String[] codes = new String[500];
        int codeCount = 0;
        int start = 0;
        for (int i = 0; i < compressed.length(); i++) {
            char c = compressed.charAt(i);
            if (c == ' ') {
                codes[codeCount++] = compressed.substring(start, i);
                start = i + 1;
            }
        }
        if (start < compressed.length()) {
            codes[codeCount++] = compressed.substring(start);
        }

        for (int i = 0; i < codeCount; i++) {
            String code = codes[i];
            for (String[] map : mapping) {
                if (map[1].equals(code)) {
                    decompressed.append(map[0]);
                    break;
                }
            }
        }
        return decompressed.toString();
    }
    
    public void compressAndDecompress(String originalText) {
        countFrequencies(originalText);
        String[][] mapping = createCompressionCodes();
        String compressedText = compressText(originalText, mapping);
        String decompressedText = decompressText(compressedText, mapping);

        System.out.println("Original Text: " + originalText);
        System.out.println("Compressed Text: " + compressedText);
        System.out.println("Decompressed Text: " + decompressedText);
        
        System.out.println("\n--- Analysis ---");
        System.out.println("Character Frequency Table:");
        for (int i = 0; i < uniqueCount; i++) {
            System.out.printf("'%s' : %d\n", uniqueChars[i], frequencies[i]);
        }
        
        System.out.println("\nCompression Mapping:");
        for (String[] map : mapping) {
            System.out.printf("'%s' -> '%s'\n", map[0], map[1]);
        }
        
        double originalSize = originalText.length();
        double compressedSize = compressedText.length();
        double compressionRatio = (originalSize - compressedSize) / originalSize * 100;
        System.out.printf("\nCompression Efficiency: %.2f%%\n", compressionRatio);
    }
}
